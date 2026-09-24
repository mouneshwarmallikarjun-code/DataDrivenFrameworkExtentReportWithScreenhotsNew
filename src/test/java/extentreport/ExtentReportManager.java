package extentreport;

import basetest.BaseTest;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.util.Arrays;

public class ExtentReportManager implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/reports/ExtentReport.html");
        sparkReporter.config().setDocumentTitle("Automation Report");
        sparkReporter.config().setReportName("Functional Testing");
        sparkReporter.config().setTheme(Theme.STANDARD);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester 1", "Mallikarjuna");
        extent.setSystemInfo("Tester 2", "Mouneshwar");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = createExtentTest(result);   // <-- replaced here
        test.log(Status.PASS, "Test case PASSED is: " + result.getName());
        attachScreenshot(result, "Passed State");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = createExtentTest(result);   // <-- replaced here
        test.log(Status.FAIL, "Test case FAILED is: " + result.getName());
        test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable());
        attachScreenshot(result, "Failure Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = createExtentTest(result);   // <-- replaced here
        test.log(Status.SKIP, "Test case SKIPPED: " + result.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private WebDriver getDriver(ITestResult result) {
        Object instance = result.getInstance();
        if (instance instanceof BaseTest) {
            return ((BaseTest) instance).driver;
        }
        return null;
    }

    private void attachScreenshot(ITestResult result, String label) {
        WebDriver driver = getDriver(result);
        if (driver != null) {
            String base64Screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
            test.addScreenCaptureFromBase64String(base64Screenshot, label);
        } else {
            test.log(Status.WARNING, "Screenshot not captured - test class does not extend BaseTest or driver was null");
        }
    }

    // NEW METHOD — replaces the old getTestName()
    private ExtentTest createExtentTest(ITestResult result) {
        Object[] params = result.getParameters();
        String shortName = params.length > 0
                ? result.getName() + " - " + params[0]
                : result.getName();

        ExtentTest t = extent.createTest(shortName);
        if (params.length > 0) {
            t.log(Status.INFO, "Test Data: " + Arrays.toString(params));
        }
        return t;
    }
}