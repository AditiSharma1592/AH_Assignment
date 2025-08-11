package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ObjectDetailsSteps {
    private String objectNumber;
    private Response response;

    @Steps
    BaseClass Base;

    @Given("I have fetched the object number from collection api with query {string}")
    public void retrieveObjectNumber(String query) {
        var json = given()
                .queryParam("key", BaseClass.apiKey())
                .queryParam("format", "json")
                .queryParam("p", 1)
                .queryParam("ps", 5)
                .queryParam("imgonly", true)
                .queryParam("q", query)
                .log().all()
                .when().get("/{lang}/collection", Base.LANGUAGE)
                .then().statusCode(200).extract().jsonPath();
        objectNumber = json.getString("artObjects[0].objectNumber");
        assertThat("Expected sample object number", objectNumber, not(blankOrNullString()));
    }

    @When("I request details for that object")
    public void requestDetailsForThatObject() {
        response = given()
                .queryParam("key", BaseClass.apiKey())
                .queryParam("format", "json")
                .log().all()
                .when().get("/{lang}/collection/{objectNumber}", Base.LANGUAGE, objectNumber);
        
        Serenity.setSessionVariable("currentResponse").to(response);
    }

    @Then("the response should contain object with fetched object number")
    public void objectDetailsContainCoreFields() {
        var json = response.then().statusCode(200).extract().jsonPath();
        assertThat(json.getString("artObject.objectNumber"), is(objectNumber));
    }

    @When("I request a collections page with an invalid API key")
    public void requestWithInvalidKey() {
        response = given().queryParam("key", "INVALID")
                .queryParam("format", "json")
                .queryParam("p", 1)
                .queryParam("ps", 1)
                .log().all()
                .when().get("/{lang}/collection", Base.LANGUAGE);
        
        Serenity.setSessionVariable("currentResponse").to(response);
    }
}
