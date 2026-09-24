package testcases;

import basetest.BaseTest;
import extentreport.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import utilities.Log;

import java.io.IOException;

public class TC_01_PageIdentityVerification extends BaseTest {

    HomePage homePage;
    @Test(priority = 1)
    public void verifyURl() throws IOException {
        homePage =new HomePage(driver,wait);
        Log.info("Getting the URL of the Page");
        Assert.assertEquals(homePage.getURL(),configReader.getProp("URL"));
        Log.info("URL verification completed");
    }

    @Test(priority = 2)
    public void verifyTitle() {
        homePage =new HomePage(driver,wait);
        String actualTitle = homePage.getTitle();
        Assert.assertTrue(actualTitle.contains("Coursera"), "Page title does not contain expected text. Actual title: " + actualTitle);
        Log.info("Title verification completed");
    }

    @Test(priority = 3)
    public void testWebsiteLogo(){
        homePage =new HomePage(driver,wait);
        Assert.assertTrue(homePage.checkLogo());
    }
}
