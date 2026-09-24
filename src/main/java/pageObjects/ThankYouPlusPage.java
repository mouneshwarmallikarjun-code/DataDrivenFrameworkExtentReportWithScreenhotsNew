package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonCode;
import utilities.ExcelWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThankYouPlusPage {
    private static final String THANK_YOU_PLUS_URL = "https://www.coursera.org/business/thank_you_plus";

    WebDriver driver;
    WebDriverWait wait;
    CommonCode commonCode;

    public ThankYouPlusPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        this.commonCode = new CommonCode(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(THANK_YOU_PLUS_URL);
        wait.until(ExpectedConditions.urlContains("thank_you_plus"));
        commonCode.visibilityElementLocatedFunc(By.tagName("body"));
    }

    public boolean isAt() {
        return driver.getCurrentUrl().contains("thank_you_plus");
    }

    public List<String> getPageDetails() {
        List<String> details = new ArrayList<>();
        details.add("Title: " + driver.getTitle());
        details.add("URL: " + driver.getCurrentUrl());

        String headingText = getFirstNonEmptyText(By.tagName("h1"));
        if (headingText.isEmpty()) {
            headingText = getFirstNonEmptyText(By.tagName("h2"));
        }
        if (headingText.isEmpty()) {
            headingText = "Heading not found";
        }
        details.add("Heading: " + headingText);
        return details;
    }

    public void writePageDetailsToExcel() throws IOException {
        ExcelWriter.writeList("Thank You Plus", getPageDetails(), "Page Details");
    }

    private String getFirstNonEmptyText(By locator) {
        for (WebElement element : driver.findElements(locator)) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                return text;
            }
        }
        return "";
    }
}

