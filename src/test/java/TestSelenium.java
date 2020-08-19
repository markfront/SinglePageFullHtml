import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestSelenium {
    public static void main(String[] args) {
        String gecko_driver_path = "/home/bgu/Packages/geckodriver-v0.27.0-linux64/geckodriver";
        System.setProperty("webdriver.gecko.driver", gecko_driver_path); // Setting system properties of FirefoxDriver
        WebDriver driver = new FirefoxDriver(); //Creating an object of FirefoxDriver
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get("https://www.google.com/");
        driver.quit();
    }
}
