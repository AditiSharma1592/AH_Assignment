package stepDefinitions;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;

public class RestAssuredConfig {

    public static void configure() {
        RestAssured.config = io.restassured.config.RestAssuredConfig.config()
            .sslConfig(SSLConfig.sslConfig()
                .relaxedHTTPSValidation()
                .allowAllHostnames());
    }
}
