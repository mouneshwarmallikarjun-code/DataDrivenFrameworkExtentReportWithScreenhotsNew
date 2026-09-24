package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;

public class TC_02_VerifyLoginAndEmail extends BaseTest {

    HomePage homePage;
    @Test(priority = 4)
    public void testLogInAndEmailLabel() throws Exception {
        homePage = new HomePage(driver,wait);
        Assert.assertTrue(homePage.isLogInButtonClickable(), "Log In button is NOT clickable on the homepage");
        homePage.clickLogInButton();
        Assert.assertTrue(homePage.isEmailLabelVisible(), "Email label is NOT visible after clicking Log In button");
        homePage.closeLoginForm();
    }
}
