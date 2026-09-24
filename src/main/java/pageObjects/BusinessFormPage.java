package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonCode;

import java.util.List;

public class BusinessFormPage {
    private static final By CHATBOT_LAUNCHER = By.cssSelector("button[data-testid='WESAskCourseraLauncher-pill']");
    WebDriver driver;
    WebDriverWait wait;
    CommonCode commonCode;

    @FindBy(xpath = "//span[text()=' Businesses']")
    WebElement businessTab;

    @FindBy(xpath = "//form[contains(@id,'mktoForm')]")
    WebElement form;

    @FindBy(id="FirstName")
    WebElement firstName;

    @FindBy(id="LastName")
    WebElement lastName;

    @FindBy(id="Email")
    WebElement emailAddress;

    @FindBy(id="Phone")
    WebElement phone;

    @FindBy(id = "Company")
    WebElement company;

    @FindBy(id="rentalField9")
    WebElement organizationType;

    @FindBy(id="Title")
    WebElement jobTitle;

    @FindBy(id="Self_reported_employees_to_buy_for__c")
    WebElement employeeRange;

    @FindBy(id="Employee_Range__c")
    WebElement employeeRangeForPersonalMail;

    @FindBy(id="Self_Reported_Needs__c")
    WebElement needs;

    @FindBy(id="Country")
    WebElement country;

    @FindBy(id="State")
    WebElement state;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement submitBtn;

    @FindBy(className="mktoError")
    WebElement invalidEId;

    @FindBy(xpath = "//h1[text()='Coursera for Teams is your best next step']")
    WebElement formStatusDisplay;

    public BusinessFormPage(WebDriver driver, WebDriverWait wait){
        this.driver=driver;
        this.wait=wait;
        PageFactory.initElements(driver,this);
        commonCode=new CommonCode(driver,wait);
    }

    public void moveToFormArea(){
        commonCode.visibilityElementFunc(businessTab);
        businessTab.click();
        commonCode.scrollIntoViewer(form);
    }

    public void formFilling(
            String firstNameInp, String lastNameInp, String mailIdInp, String phoneInp, int organizationTypeIndex,
            String jobTitleInp, int needDescInp, int employeeRangeInp, String countryInp, String stateInp,
    String companyName) {
        firstName.sendKeys(firstNameInp);
        lastName.sendKeys(lastNameInp);
        emailAddress.sendKeys(mailIdInp);
        phone.sendKeys(phoneInp);
        Select organization= new Select(organizationType);
        organization.selectByIndex(organizationTypeIndex);
        jobTitle.sendKeys(jobTitleInp);
        try{
            company.sendKeys(companyName);
            Select employeeRangeForPEmail=new Select(employeeRangeForPersonalMail);
            employeeRangeForPEmail.selectByIndex(1);
        } catch (Exception ignore) {
        }

        Select needsDescription=new Select(needs);
        needsDescription.selectByIndex(needDescInp);
        Select rangeOfEmployee=new Select(employeeRange);
        rangeOfEmployee.selectByIndex(employeeRangeInp);
        Select countries=new Select(country);
        countries.selectByValue(countryInp);
        Select states=new Select(state);
        states.selectByValue(stateInp);
        submitFormSafely();
    }

    private void submitFormSafely() {
        commonCode.scrollIntoViewer(submitBtn);
        hideChatbotLauncherIfPresent();
        try {
            commonCode.elementClickableFunc(submitBtn).click();
        } catch (ElementClickInterceptedException e) {
            hideChatbotLauncherIfPresent();
            commonCode.jsClick(submitBtn);
        }
    }

    private void hideChatbotLauncherIfPresent() {
        List<WebElement> chatbotLaunchers = driver.findElements(CHATBOT_LAUNCHER);
        for (WebElement chatbotLauncher : chatbotLaunchers) {
            try {
                if (chatbotLauncher.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].style.display='none';", chatbotLauncher);
                }
            } catch (Exception ignore) {
            }
        }
    }

    public boolean emailCheck(){
        try {
            boolean check=commonCode.visibilityElementFunc(invalidEId).isDisplayed();
            commonCode.scrollIntoViewer(invalidEId);
            return check;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean formSubmissionStatus(){
        try{
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.urlContains("thank_you_plus"),
                    ExpectedConditions.visibilityOf(formStatusDisplay)
            ));
            return driver.getCurrentUrl().contains("thank_you_plus") || formStatusDisplay.isDisplayed();
        }
        catch (Exception e){
            return false;
        }
    }
}
