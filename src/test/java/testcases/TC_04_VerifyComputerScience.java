package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.ResultsPage;

public class TC_04_VerifyComputerScience extends BaseTest {

    HomePage homePage;
    @Test(priority = 7)
    public void credentialsCheck() throws Exception {
        homePage = new HomePage(driver, wait);
        Assert.assertTrue(homePage.isComputerScienceClickable(), "Computer Science category is NOT clickable on the homepage");
        homePage.getComputerScienceCredentials();
    }
}
