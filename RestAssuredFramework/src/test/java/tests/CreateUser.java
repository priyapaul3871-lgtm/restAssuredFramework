package tests;

import assertions.APIAssertions;
import client.ApiClient;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CreateUser {

    //Here, UserId is declared at class level so that we can use userId value in any method of the class.
    String userId;

    @BeforeClass
   // @BeforeTest(validatePutUser())
    public void setupId() {
        Response res = ApiClient.post("/api/users", "{ \"name\": \"Tanveer\", \"job\": \"QA Engineer\" }");
        userId = res.jsonPath().getString("id");
        APIAssertions.verifyStatusCode(res,201, "Setup Method: ");  //In setupId(), validate POST success too. This ensures setup itself succeeded.
    }

    @Test
    public void validateGetUser() {

        Response getRes = ApiClient.get("/api/users"); // Calls get() method from ApiClient class which return get Response which stored in variable getres
        APIAssertions.verifyStatusCode(getRes, 200, "Get User: "); //Calls verifyStatusCode() method from APIAssertions class
        getRes.then().body("total", equalTo(12));
        getRes.then().body("data[0].first_name", equalTo("George"));

    }

    @Test
    public void validatePostUser() {
        //These will test all the scenarios for Post API.
        Response postRes = ApiClient.post("/api/users", "{ \"name\": \"Tanveer\", \"job\": \"QA Engineer\" }");  //passing the request body as a JSON string.
        APIAssertions.verifyStatusCode(postRes, 201, "Post User: ");

        APIAssertions.verifyResponsefieldValue(postRes, "name","Tanveer", "Post User: ");
        APIAssertions.verifyResponsefieldValue(postRes, "job", "QA Engineer", "Post User: ");
        //or
        //postRes.then().body("name", equalTo("Tanveer"));  //As we know ValidatableResponse is for asserting/validating it. But here we are using Response to hold API response (1st line of code) so, used then() as it gives ValidatableResponse which is required for assertion.
        //postRes.then().body("job", equalTo("QA Engineer"));
        postRes.then().body("id", notNullValue());
        postRes.then().body("createdAt", notNullValue());

    }

    @Test
    public void validatePutUser() {
        Response putRes = ApiClient.put("/api/users/" +userId, "{\"name\" : \"Gaurav\", \"job\" : \"Business Analyst\"}");
        APIAssertions.verifyStatusCode(putRes, 200, "Put User: ");
        APIAssertions.verifyResponsefieldValue(putRes, "name", "Gaurav", "Put User: ");
        APIAssertions.verifyResponsefieldValue(putRes, "job", "Business Analyst", "Put User: ");
        putRes.then().body("updatedAt", notNullValue());
    }
}

