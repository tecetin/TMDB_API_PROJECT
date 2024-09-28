package utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class Driver {

    private Driver(){}

    public static WebDriver driver;

    public static WebDriver getDriver(){

        String browser = ConfigReader.getProperty("browser");
        if (driver == null){
            switch (browser){
                case "firefox" :
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
                case "safari" :
                    driver = new SafariDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
                case "edge" :
                    driver = new EdgeDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
                default :
                    driver = new ChromeDriver();
                    driver.manage().window().maximize();
                    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                    break;
            }
        }
        return driver;
    }

    public static void quitDriver(){
        if(driver!=null){
            driver.quit();
            driver = null;
        }
    }
}
