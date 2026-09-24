package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;

public class TC_03_VerifyExploreCategories extends BaseTest {

    HomePage homePage;
    @Test(priority = 5)
    public void checkExplore() throws Exception {
        homePage =new HomePage(driver,wait);
        Assert.assertTrue(homePage.exploreCategoriesTitle());
    }

    @Test(priority = 6)
    public void checkAllCategories(){
    Assert.assertTrue(homePage.categoriesNames());
    }
}
