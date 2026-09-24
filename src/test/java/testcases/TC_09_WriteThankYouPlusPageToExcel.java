package testcases;

import basetest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.ThankYouPlusPage;

import java.io.IOException;
import java.util.List;

public class TC_09_WriteThankYouPlusPageToExcel extends BaseTest {
    ThankYouPlusPage thankYouPlusPage;

    @Test(priority = 13)
    public void writeThankYouPlusPageDetailsToExcel() throws IOException {
        thankYouPlusPage = new ThankYouPlusPage(driver, wait);

        thankYouPlusPage.open();
        Assert.assertTrue(thankYouPlusPage.isAt(), "Thank You Plus page should open successfully.");

        List<String> pageDetails = thankYouPlusPage.getPageDetails();
        Assert.assertFalse(pageDetails.isEmpty(), "Thank You Plus page details should be collected.");
        Assert.assertFalse(pageDetails.get(0).endsWith(": "), "Page title should not be empty.");

        thankYouPlusPage.writePageDetailsToExcel();
    }
}

