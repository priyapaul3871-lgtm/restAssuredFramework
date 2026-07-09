package client;
import config.Builder;
import constants.Endpoints;
import constants.Headers;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class ApiClient {
    //Here, in this class we will create different CRUD client methods which will return API responses.6Hence, its return type will be in "Response".
    //This method is a generic API get client which will be used in all API Tests.
    /* without using RequestSpecBuilder class
    public static Response get(String endpoint)
    {
        // "+" operator in String handling used to concat two different strings.
        String serverName = Endpoints.baseUrl;
        endpoint = serverName+endpoint;
        or we can also write
        endpoint = Endpoints.baseUrl+endpoint;     // Here, in endpoint variable, we are overriding original endpoint value by concatenating with baseUrl value

        Response getResponse = RestAssured
            .given()
            .header("Content-Type", Headers.contentType)    // As contentType is a static variable declared in Headers class so, used this variable using classname.variablename in this class.
            .header("x-api-key", Headers.apikey)            // As apikey is a static variable declared in Headers class so, used this variable using classname.variablename in this class.
            .when()
            .get(endpoint);
    return getResponse;
    }*/

     //Using RequestSpecBuilder class
    // RestAssured internally handles the concatenation of the baseUri and the endpoint path.
    //spec(reqspec) contains the base URI (i.e. "https://reqres.in") and .get(endpoint) contains the resource path (i.e. "/api/users"). So, RestAssured automatically combines them internally like baseUri + endpoint (i.e. "https://reqres.in/api/users")
    //.spec(reqspec) is used to apply a predefined Request Specification to the request.
    public static Response get(String endpoint)
    {
        RequestSpecification reqspec = Builder.getRequestSpec();   //Calls a method named getRequestSpec() from the Builder class. getRequestSpec() method returns a RequestSpecification object which get is stored in the variable reqspec
        Response getResponse = RestAssured
                .given()
                .spec(reqspec)      //Start building the API request and apply all configurations stored inside reqspec
                .when()
                .get(endpoint);
        return getResponse;
    }

    public static Response post(String endpoint, String payload)
    {
        RequestSpecification reqspec = Builder.getRequestSpec();  //we can use same variable name (reqspec) in all the methods (get, post, put and delete) because variables are local to each method.
        Response postResponse = RestAssured
                .given()
                .spec(reqspec)
                .body(payload)
                .when()
                .post(endpoint);
        return postResponse;
    }

    public static Response put(String endpoint, String payload)
    {
        RequestSpecification reqspec = Builder.getRequestSpec();
        Response putResponse;
        putResponse = RestAssured
                .given()
                .spec(reqspec)
                .body(payload)
                .when()
                .put(endpoint);
        return putResponse;
    }

    public static Response delete(String endpoint)
    {
        RequestSpecification reqspec = Builder.getRequestSpec();
        Response deleteResponse = RestAssured
                .given()
                .spec(reqspec)
                .when()
                .delete(endpoint);
        return deleteResponse;
    }

    public static String getFirstNameWithPolling(String endpoint, int maxRetries) {
        for (int i = 1; i <= maxRetries; i++) {
            Response res = get(endpoint);         // Calling existing get() method to get the response to avoid code duplication and keep the polling method cleaner.
            if (res.getStatusCode() == 200) {     // check status code should be 200
                String firstName = res.jsonPath().getString("data[0].first_name");
                if (firstName != null && !firstName.isEmpty()) {   //Check first_name should not be null and empty then only return first_name value.
                    return firstName;
                }
            }
            try {
                //Thread.sleep(1000) tells the current thread to pause (sleep) for 1000 milliseconds (1 second).
                Thread.sleep(1000);     //Thread.sleep() can throw an InterruptedException, so Java requires it to be inside a try-catch block (or the method must declare throws InterruptedException).
            }
            catch (InterruptedException e) {      //InterruptedException is a checked exception that is thrown when a thread that is waiting, sleeping, or blocked is interrupted by another thread. In simple terms, it tells the thread to stop waiting because someone has requested that you wake up.
                Thread.currentThread().interrupt();    //To restore the interrupt flag to true so that higher-level code can still detect that the thread was interrupted and handle it appropriately.
            }
        }

        // Since the getFirstNameWithPolling() method returns a String, every execution path must either return a String or terminate by throwing an exception.
        // If the polling loop completes without finding first_name, there is no valid String to return. Therefore, we throw a RuntimeException.
        // This prevents a 'Missing return statement' compilation error and clearly indicates that the polling failed after all retry attempts.
        // Remember: throw is not returning a value. It is terminating the method by raising an exception.
        throw new RuntimeException("first_name not found after " + maxRetries + " attempts.");
    }
    }
