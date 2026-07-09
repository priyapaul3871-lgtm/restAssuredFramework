package config;
import constants.Endpoints;
import constants.Headers;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class Builder {

    /*
    RequestSpecBuilder is a class in Rest Assured used to create reusable request specifications for API testing.
    Instead of writing the same configuration (base URI, headers, auth, content type, etc.) in every API test, you define it once using RequestSpecBuilder and reuse it across tests.
    new RequestSpecBuilder() → create builder
    .setBaseUri() → set base URL
    .addHeader() → add request header
    .build() → create RequestSpecification object
    return → send it back to caller
    RequestSpecification is an interface in Rest Assured that represents the complete HTTP request configuration before the API call is sent. It stores all request details such as Base URI, Headers, Query parameters, Path parameters, Authentication, Request body, Content type and Cookies.
   */
    public static RequestSpecification getRequestSpec() {
        RequestSpecBuilder req = new RequestSpecBuilder();

        //Fetching baseUrl from getConfigFromMVN() under configReader class
        req.setBaseUri(ConfigReader.getConfigFromMVN("url"));

        //Fetching baseUrl from getConfig() under configReader class
        //req.setBaseUri(ConfigReader.readPropertyFile("url"));

        //Fetching baseUrl from Endpoint class
        //req.setBaseUri(Endpoints.baseUrl);

        req.addHeader("Content-Type", Headers.contentType);
        req.addHeader("x-api-key", Headers.apikey);
        RequestSpecification spec = req.build(); //After build(), the spec object contains Base URI and Headers.
        return spec;
    }
}

    //We can also write the below way in one line without closing every line of code with semicolon.

   /* public static RequestSpecification getRequestSpec1() {
        RequestSpecification spec1 = new RequestSpecBuilder()
        .setBaseUri(Endpoints.baseUrl)
        .addHeader("Content-Type", Headers.contentType)
        .addHeader("x-api-key", Headers.apikey)
        .build();
         return  spec1;
    }*/


