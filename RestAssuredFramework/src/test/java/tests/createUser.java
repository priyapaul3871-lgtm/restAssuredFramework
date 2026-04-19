package tests;

import assertions.APIAssertions;
import client.ApiClient;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;


public class createUser {
    @Test
    public void validatePostUser() {

        Response postres = ApiClient.post("https://reqres.in/api/users", "{ \"name\": \"Tanveer\", \"job\": \"QA Engineer\" }");
        APIAssertions.verifyStatusCode(postres, 201);
        postres.then().body("name",equalTo("Tanveer"));  //As we know ValidatableResponse is for asserting/validating it. But here we are using Response to hold API response (1st line of code) so, used then() as it gives ValidatableResponse which is required for assertion.

    }

    @Test
    public void validateGetUser() {

        Response getres = ApiClient.get("https://reqres.in/api/users");
        APIAssertions.verifyStatusCode(getres, 200);
        getres.then().body("total",equalTo(12));
        getres.then().body("data[0].first_name", equalTo("Michael"));

    }
}
