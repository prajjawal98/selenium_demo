package Tests;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BasicSeleniumTest {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(BasicSeleniumTest.class);


    @BeforeClass
    public void setup() {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "chromedriver");

        // Initialize ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Enable headless mode
        options.addArguments("--disable-gpu"); // Optional: Disables GPU acceleration

        // Initialize the ChromeDriver with ChromeOptions
        driver = new ChromeDriver(options);
        logger.info("ChromeDriver initialized successfully.");
    }

    @Test
    public void testGoogleTitle() {
        // Navigate to Google
        driver.get("http://www.google.com");

        // Verify the title of the page
        String title = driver.getTitle();
        logger.info("Page title: {}", title);

        Assert.assertEquals(title, "Google", "Title is not as expected");
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
