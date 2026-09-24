package basetest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import utilities.ConfigReader;

import java.io.IOException;
import java.time.Duration;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    public ConfigReader configReader;
    @BeforeClass
    public void getDriver() throws IOException {
        configReader = new ConfigReader();
        String browser = configReader.getProp("browser");
        if (browser != null) {
            if (browser.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();
            } else {
                driver = new ChromeDriver();
            }
            driver.manage().window().maximize();
            driver.get(configReader.getProp("URL"));
            wait=new WebDriverWait(driver, Duration.ofSeconds(20));
        }
    }
    @AfterClass
    public void tearDown(){
        if(driver!=null){
            driver.quit();
            driver=null;
        }
    }
}
