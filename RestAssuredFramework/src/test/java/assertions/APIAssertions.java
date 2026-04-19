package assertions;

import io.restassured.response.Response;

public class APIAssertions {

        public static void verifyStatusCode(Response response, int expected) {
            response.then().statusCode(expected);
        }

}
