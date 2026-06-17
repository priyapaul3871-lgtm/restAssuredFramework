package tests;

import assertions.APIAssertions;
import client.ApiClient;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ExcelDataProvider;

import java.util.Map;

public class CreateUserTest {
    //Before running this test method, get test data from the DataProvider method named createUserData present inside ExcelDataProvider class.
    @Test(dataProvider = "createUserData",
            dataProviderClass = ExcelDataProvider.class)  //The DataProvider method is not inside the current test class. It is inside the ExcelDataProvider class. Without this line of code, TestNG will search for the DataProvider inside the current class (CreateUserTest). Since it doesn't exist there, TestNG throws an error.
                                                              //ExcelDataProvider.class returns the Class object of ExcelDataProvider.
    public void validateCreateUser(Map<String,String> row) {  //This test method needs only one parameter i.e one entire Excel row, not one column value..

        /*List<Map<String,String>> testData = ExcelUtil.readSheet("CreateUser");  //Contains all excel rows.
        // Map<String,String> row = testData.get(0);   //In case we need only 1st row values.
        for(Map<String,String> row : testData)
        {*/
            String tcid = row.get("TCID");
            System.out.println("\n=================================");
            System.out.println("Executing Test Case : " + tcid);
            System.out.println("=================================");
            String endpoint = row.get("Endpoint");
            String requestBody = row.get("RequestBody");
            int expectedStatus = Integer.parseInt(row.get("ExpectedStatus"));
            Response response = ApiClient.post(endpoint, requestBody);
            APIAssertions.verifyStatusCode(response, expectedStatus, tcid);
            //System.out.println("Status Code : " + response.getStatusCode());
            //System.out.println(response.asPrettyString());  //To print response use asPrettyString(). System.out.println(response) will print object reference something like io.restassured.internal.RestAssuredResponseImpl@5d3a4


            String expectedName = row.get("ExpectedName");
            String expectedJob = row.get("ExpectedJob");
            APIAssertions.verifyResponsefieldValue(response, "name", expectedName, tcid);
            APIAssertions.verifyResponsefieldValue(response, "job", expectedJob, tcid);

        }
    }



