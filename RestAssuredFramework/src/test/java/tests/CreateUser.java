package tests;

import assertions.APIAssertions;
import base.BaseTest;
import client.ApiClient;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.Helper;
import utils.ReportManager;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CreateUser extends BaseTest
{

    //Here, UserId is declared at class level so that we can use userId value in any method of the class.
    String userId;
   // String[] ignorepaths = {"id","createdAt"};
    @BeforeClass        //@BeforeClass runs once before tests inside CreateUser class only.
   // @BeforeTest(validatePutUser())
    public void setupId() {
        Response res = ApiClient.post("/api/users", "{ \"name\": \"Tanveer\", \"job\": \"QA Engineer\" }");    // Here, res variable datatype is Response because ApiClient.post() returns a Response object.
        userId = res.jsonPath().getString("id");
        APIAssertions.verifyStatusCode(res,201, "Setup Method: ");  //In setupId(), validate POST success too. This ensures setup itself succeeded.
    }

    @Test(priority = 1)
    public void validateGetUser() {
        ReportManager.createTest("TEST CASE 1","validateGetUser" );
        Response getRes = ApiClient.get("/api/users"); // Calls get() method from ApiClient class which return get Response which stored in variable getres
        ReportManager.info("/api/users");
        APIAssertions.verifyStatusCode(getRes, 200, "Get User: "); //Calls verifyStatusCode() method from APIAssertions class
        getRes.then().body("total", equalTo(12));
        getRes.then().body("data[0].first_name", equalTo("George"));
        ReportManager.reportResponse(getRes.asPrettyString());
        ReportManager.pass("Validation Passed");

    }

    @Test(priority = 2)
    public void validatePostUser() {
        ReportManager.createTest("TEST CASE 2","validatePostUserx`" );
        //These will test all the scenarios for Post API.
        Response postRes = ApiClient.post("/api/users", "{ \"name\": \"Priya\", \"job\": \"QA Engineer\" }");  //passing the request body as a JSON string. It works in all Java versions.
       //Response postRes = ApiClient.post("/api/users","""{"name": "Priya", "job": "QA Engineer"}""");   //""" is called a Text Block, introduced in Java 15.

        APIAssertions.verifyStatusCode(postRes, 201, "Post User: ");

        postRes.then().body(matchesJsonSchemaInClasspath("schema/CreateUserSchema.json"));   //No conversion to a string is required because REST Assured handles the response internally.
        ReportManager.info("URI : /api/users");

        String actual = postRes.asString();  // converts the entire HTTP response body into a String because REST Assured stores the API response in a Response object and json-unit-assertj compares JSON strings, not Response objects.
        String expected = Helper.readJsonResponseFile("userResponse.json");
        assertThatJson(actual)
                .whenIgnoringPaths(                   //Ignore specific fields like id and createdAt as these are dynamic fields
                        "id",
                        "createdAt"
                )
                .isEqualTo(expected);

        //other way to ignorePaths that are configurable or reused across multiple test cases.
        /* String[] ignorePaths = {“id”, createdAt, “address.pin”};
           assertThatJson(actual).whenIgnoringPaths(ignorePaths).isEqualTo(expected); */

        APIAssertions.verifyResponsefieldValue(postRes, "name","Priya", "Post User: ");
        APIAssertions.verifyResponsefieldValue(postRes, "job", "QA Engineer", "Post User: ");

        //or

        //No conversion to a string is required if you're using REST Assured's built-in assertions, you can work directly with the Response
        //postRes.then().body("name", equalTo("Tanveer"));  //As we know ValidatableResponse is for asserting/validating it. But here we are using Response to hold API response (1st line of code) so, used then() as it gives ValidatableResponse which is required for assertion.
        //postRes.then().body("job", equalTo("QA Engineer"));
        postRes.then().body("id", notNullValue());
        postRes.then().body("createdAt", notNullValue());


    }

    @Test(priority = 3)
    public void validatePutUser() {
        Response putRes = ApiClient.put("/api/users/" +userId, "{\"name\" : \"Gaurav\", \"job\" : \"Business Analyst\"}");
        APIAssertions.verifyStatusCode(putRes, 200, "Put User: ");
        APIAssertions.verifyResponsefieldValue(putRes, "name", "Gaurav", "Put User: ");
        APIAssertions.verifyResponsefieldValue(putRes, "job", "Business Analyst", "Put User: ");
        putRes.then().body("updatedAt", notNullValue());
    }
}

