package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.core.Serenity;

public class CommonSteps {

    @Steps
    BaseClass Base;

    @Given("the Rijksmuseum API is available")
    public void the_api_is_available() {
        RestAssuredConfig.configure();

        RestAssured.baseURI = Base.BASE_URL;
    }

    @Then("the response status should be {int}")
    public void verifyResponseStatus(int expectedStatus) {
        Response response = Serenity.sessionVariableCalled("currentResponse");
        if (response != null) {
            response.then().statusCode(expectedStatus);
        } else {
            throw new IllegalStateException("No response found in current test context");
        }
    }
}

