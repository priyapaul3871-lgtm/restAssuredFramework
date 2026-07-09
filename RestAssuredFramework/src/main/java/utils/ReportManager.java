package utils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportManager {
    private static ExtentReports extent;
    private static ExtentTest test;

    public static void initReport()
    {
        ExtentSparkReporter spark =
                new ExtentSparkReporter("test-output/APIAutomationReport.html");

        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    public static void createTest(String testCaseId, String testName) {
        test = extent.createTest(testCaseId + " - " + testName);
    }

    public static void info(String message) {
        test.info(message);
    }

    public static void pass(String message) {
        test.pass(message);
    }

    public static void fail(String message) {
        test.fail(message);
    }

    public static void reportRequest(String requestBody) {

        if (requestBody != null && !requestBody.isEmpty()) {
            test.info("Request Body:");
            test.info("<pre>" + requestBody + "</pre>");
        }
    }

    public static void reportResponse(String responseBody) {
        test.info("Response Body:");
        test.info("<pre>" + responseBody + "</pre>");
    }

    public static void flushReport() {
        extent.flush();
    }
}
