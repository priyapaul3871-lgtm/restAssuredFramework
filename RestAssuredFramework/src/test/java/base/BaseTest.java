package base;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utils.ReportManager;

public class BaseTest {
    @BeforeSuite
    public void beforeSuite()
    {
        ReportManager.initReport();
    }

    @AfterSuite
    public void afterSuite() {
        ReportManager.flushReport();
    }
}
