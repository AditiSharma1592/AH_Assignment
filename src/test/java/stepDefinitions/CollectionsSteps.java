package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;
import org.junit.Assert;

import java.util.List;

import static net.serenitybdd.rest.SerenityRest.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CollectionsSteps {
    private Response response;

    @Steps
    BaseClass Base;

    @When("I request the collections page {int} with page size {int} for query {string}")
    public void requestCollectionsBasic(int page, int pageSize, String query) {
        response = given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .queryParam("key", BaseClass.apiKey())
                .queryParam("format", "json")
                .queryParam("p", page)
                .queryParam("ps", pageSize)
                .queryParam("q", query)
                .log().all()
                .when()
                .get("/{lang}/collection", Base.LANGUAGE);
        
        Serenity.setSessionVariable("currentResponse").to(response);
    }

    @When("I request the collections page {int} with page size {int} for query {string} and images only")
    public void requestCollectionsImgOnly(int page, int pageSize, String query) {
        response = given()
                .queryParam("key", BaseClass.apiKey())
                .queryParam("format", "json")
                .queryParam("p", page)
                .queryParam("ps", pageSize)
                .queryParam("imgonly", true)
                .queryParam("q", query)
                .log().all()
                .when().get("/{lang}/collection", Base.LANGUAGE);
        
        Serenity.setSessionVariable("currentResponse").to(response);
    }

    @And("the result should contain at most {int} art objects")
    public void resultShouldContainAtMostObject(int maxCount) {
        List<?> artObjects = response.jsonPath().getList("artObjects");
        assertThat(artObjects.size(), is(lessThanOrEqualTo(maxCount)));
    }

    @Then("every returned object should have an image")
    public void verifyEveryObjectHasImage() {
        List<Boolean> hasImages = response.jsonPath().getList("artObjects.hasImage", Boolean.class);
        assertThat(hasImages, everyItem(is(true)));
    }

    @Given("I call the collections API without API key")
    public void callCollectionsApiWithoutKey() {
        response = RestAssured.given()
                .relaxedHTTPSValidation()
                .log().all()
                .when()
                .get("/{lang}/collection", Base.LANGUAGE);

        Serenity.setSessionVariable("currentResponse").to(response);

        Serenity.recordReportData().withTitle("Malformed URL Response").andContents(response.getBody().asPrettyString());
    }

    @And("the response should contain error message")
    public void verifyResponseContainsErrorMessage() {
        String responseBody = response.getBody().asString();
        Assert.assertTrue("Response should contain error message",
                responseBody.contains("Invalid key"));
    }

}
