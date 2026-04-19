package client;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiClient {
    //Here, in this class we will create different CRUD client methods which will return API responses.
    // Hence, its return type will be in "Response".
    //This method is a generic API get client which will be used in all API Tests.
    public static Response get(String endpoint)
    {
        Response getResponse;
             getResponse = RestAssured
            .given()
            .header("Content-Type", "application/json")
            .header("x-api-key", "reqres_1e4abb4fd5cf48898049ef9abfcaba9b")
            .when()
            .get(endpoint);
    return getResponse;

    }
    public static Response post(String endpoint, String payload)
    {
        Response postResponse;
        postResponse = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres_1e4abb4fd5cf48898049ef9abfcaba9b")
                .body(payload)
                .when()
                .post(endpoint);
        return postResponse;
    }

    public static Response put(String endpoint, String payload)
    {
        Response putResponse;
        putResponse = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres_1e4abb4fd5cf48898049ef9abfcaba9b")
                .body(payload)
                .when()
                .put(endpoint);
        return putResponse;
    }

    public static Response delete(String endpoint)
    {
        Response deleteResponse;
        deleteResponse = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres_1e4abb4fd5cf48898049ef9abfcaba9b")
                .when()
                .delete(endpoint);
        return deleteResponse;
    }
}
