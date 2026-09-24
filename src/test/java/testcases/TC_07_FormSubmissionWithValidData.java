package testcases;

import basetest.BaseTest;
import dataproviders.FormDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.BusinessFormPage;
import pageObjects.HomePage;

public class TC_07_FormSubmissionWithValidData extends BaseTest {
    HomePage homePage;
    BusinessFormPage businessFormPage;

    @Test(
            priority = 11,
            dataProvider = "validFormData",
            dataProviderClass = FormDataProviders.class
    )
    public void formSubmissionWithValidData(
            String firstName,
            String lastName,
            String email,
            String phone,
            String expYearsStr,
            String jobTitle,
            String countryIndexStr,
            String stateIndexStr,
            String country,
            String state,
            String company
    ) {
        homePage = new HomePage(driver, wait);
        businessFormPage = new BusinessFormPage(driver, wait);

        businessFormPage.moveToFormArea();

        int expYears = parseIntOrDefault(expYearsStr, 0);
        int countryIndex = parseIntOrDefault(countryIndexStr, 0);
        int stateIndex = parseIntOrDefault(stateIndexStr, 0);

        businessFormPage.formFilling(
                firstName,
                lastName,
                email,
                phone,
                expYears,
                jobTitle,
                countryIndex,
                stateIndex,
                country,
                state,
                company
        );

        Assert.assertFalse(businessFormPage.emailCheck(), "No email error expected for valid email.");
        Assert.assertTrue(businessFormPage.formSubmissionStatus(), "Form should submit successfully for valid data.");
    }

    private int parseIntOrDefault(String value, int defaultValue) {
        try { return Integer.parseInt(value.trim()); } catch (Exception e) { return defaultValue; }
    }
}
