package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import utilities.CommonCode;

import java.io.IOException;

public class TC_06_SearchBarFunctionality extends BaseTest {

    HomePage homePage;
    @Test(priority = 9)
    void searchBarFunctionalityWithValidInput() throws IOException {
        homePage =new HomePage(driver,wait);
        Assert.assertTrue(homePage.searchBarVisibility());
        homePage.sendInputToSearchBar("Web Development");
    }
}
