package assertions;

import io.restassured.response.Response;
import org.testng.Assert;

import java.sql.SQLOutput;

public class APIAssertions {
        public static void verifyStatusCode(Response response, int expectedStatusCode, String apiName) {    //Response is a class or more specifically, an interface in Rest Assured and response is an object which contains the API response returned by Rest Assured.
            try{
                int actualStatusCode = response.getStatusCode();
                Assert.assertEquals(actualStatusCode, expectedStatusCode);
                //or
                //response.then().statusCode(expectedStatusCode);
                System.out.println(apiName+ " Status code validation passed");
            }
            catch (AssertionError  e)   // RestAssured throws AssertionError Exception. Here, e is the exception object.
            {
                System.out.println(apiName+ " Expected status code: " + expectedStatusCode);  //e.getMessage() is used to get the error message from an exception object. It retrieves the actual reason why the assertion failed.
                System.out.println(apiName+ " Actual status code: " +response.getStatusCode());
                Assert.fail(apiName+ " Status code validation failed: " + e.getMessage());  //Assert.fail() is used to explicitly fail the test case. It forces the test to fail immediately so that framework/report clearly marks the test case as failed.

                //throw  e;     //throw e sends exception back. Test stops and fails. It works similar way as Assert.fail()
                            }
        }

        /*public static void verifyPostResponsefieldValue(Response response, String expectedName) {
            //String expectedName = "Tanveer";
            //String expectedJob = "QA Engineer";
            try {
              String actualName = response.jsonPath().getString("name");
              Assert.assertEquals(actualName, expectedName);
                System.out.println("Response name field validation passed");
            }

            catch (AssertionError e) {
                Assert.fail("Response name field validation failed: " +e.getMessage());
            }
        }*/


    //Object is the parent class of all wrapper classes (String, Integer, Boolean, Double) in Java.
    public static void verifyResponsefieldValue(Response response, String resJsonPath, Object expectedValue, String apiName)
    {
        //Variables declared inside try cannot be accessed outside it. So, actualValue variable is declared outside try block so that catch block can also access it
        Object actualValue = null;  //Java requires local variables to be initialized before use. So, actualValue variable is assigned with null. actualValue does NOT permanently keep value null. It only sets the initial value. Later assignments overwrite it.
        try {

            actualValue =  response.jsonPath().get(resJsonPath);  //This line of code dynamically fetches value from JSON. Now the actualValue variable gets a real value from API response. So it is no longer null.
            Assert.assertEquals(actualValue, expectedValue);
            System.out.println(apiName+ " " +resJsonPath + " field validation passed");
        }

        catch(AssertionError e) {
            System.out.println(apiName+ " " + "Expected Value: " +expectedValue);
            System.out.println(apiName+ " " + "Actual value: " +actualValue);
            Assert.fail(apiName+ " " + resJsonPath +" field validation failed" +e.getMessage());
        }
    }



}
