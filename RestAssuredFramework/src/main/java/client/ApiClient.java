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
    public static Response get(String endpoint)
    {
        RequestSpecification reqspec = Builder.getRequestSpec();
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
        RequestSpecification reqspec = Builder.getRequestSpec();  //Calls a method named getRequestSpec() from the Builder class. getRequestSpec() method returns a RequestSpecification object which get is stored in the variable reqspec
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
}
