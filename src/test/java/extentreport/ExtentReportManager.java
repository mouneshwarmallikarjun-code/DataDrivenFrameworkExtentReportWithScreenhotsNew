package extentreport;

import basetest.BaseTest;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ExtentReportManager implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;

    // Physical folder on disk where screenshot files are saved
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "/reports/screenshots/";
    // Path used INSIDE the HTML report — relative to reports/ExtentReport.html
    private static final String SCREENSHOT_DIR_RELATIVE = "screenshots/";

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

        // Ensure the screenshots folder exists before any test tries to write into it
        File screenshotFolder = new File(SCREENSHOT_DIR);
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test = createExtentTest(result);
        test.log(Status.PASS, "Test case PASSED is: " + result.getName());
        attachScreenshot(result, "Passed Screenshot");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test = createExtentTest(result);
        test.log(Status.FAIL, "Test case FAILED is: " + result.getName());
        test.log(Status.FAIL, "Test Case FAILED cause is: " + result.getThrowable());
        attachScreenshot(result, "Failure Screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test = createExtentTest(result);
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
        if (driver == null) {
            test.log(Status.WARNING, "Screenshot not captured - test class does not extend BaseTest or driver was null");
            return;
        }

        try {
            // Build a meaningful, unique file name: testName_Label_timestamp.png
            String safeLabel = label.replaceAll("\\s+", "_");
            String screenshotName = result.getName() + "_" + safeLabel + "_" + System.currentTimeMillis() + ".png";

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(SCREENSHOT_DIR + screenshotName);
            FileUtils.copyFile(srcFile, destFile);

            // The report only needs the relative path, since it's relative to ExtentReport.html
            String relativePathForReport = SCREENSHOT_DIR_RELATIVE + screenshotName;

            test.addScreenCaptureFromPath(relativePathForReport, label);
        } catch (IOException e) {
            test.log(Status.WARNING, "Failed to capture/save screenshot: " + e.getMessage());
        }
    }

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