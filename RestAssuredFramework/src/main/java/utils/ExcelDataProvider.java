package utils;

import org.testng.annotations.DataProvider;

import java.util.List;
import java.util.Map;

//This class supplies that data to TestNG @Test methods. DataProvider is separate class so that other test classes (GetUserTest, CreateUserTest, UpdateUserTest and DeleteUserTest) can reuse it easily in future.
public class ExcelDataProvider {

    //@DataProvider is a TestNG annotation that provides multiple sets of test data to a test method.
    @DataProvider(name = "createUserData")  //Assigns a name (createUserData) to the Data Provider. Hence, we can say createUserData is a Data Provider name that TestNG uses to connect the test method to the Data Provider.
    public Object[][] getCreateUserData() throws Exception {   // Here, getCreateUserData() is a method and Object[][] is a two-dimensional array containing test data.

        List<Map<String, String>> excelData  = ExcelUtil.readSheet("CreateUser");     //Calls ExcelsUtil class for reading Excel sheet "CreateUser"

        //Create two-dimensional Object Array e.g. Object[][] obj = new Object[][];
        // Immediately after creation, Java initializes every element to its default value. For objects, the default value is null.
        Object[][] data = new Object[excelData.size()][1];  //Here, excelData.size() returns total rows count and [1] because each test execution (Map<String,String> row) receives only one parameter(i.e one entire row), check validateCreateUser() in CreateUserTest class.

        for(int i = 0; i < excelData.size(); i++) {
            data[i][0] = excelData.get(i);  //Here, data[0][0] contains one complete row e.g. {TCID=TC01, Name=Tanveer, Job=QA Engineer}. Similarly, data[1][0] contains next entire row value and so on.
        }
        //After the loop finishes, above data variable contains all Excel rows, but each row is stored as a Map object.

        return data;
    }
}