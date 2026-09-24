package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;

public class TC_05_FooterCheck extends BaseTest {

    HomePage homePage;
    @Test(priority = 8)
    public void checkFooter(){
        homePage =new HomePage(driver,wait);
        Assert.assertTrue(homePage.footerCheck());
    }
}
