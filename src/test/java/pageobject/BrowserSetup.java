package pageobject;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.io.File;
import java.time.Duration;

public class BrowserSetup {
    public enum Browser {
        CHROME("119.0.6045.105"),
        YANDEX("138.0.7204.983");

        private final String version;

        Browser(String version) {
            this.version = version;
        }
    }

    public static WebDriver createDriver(Browser browser) {
        switch (browser) {
            case CHROME:
                return createChromeDriver(browser.version);
            case YANDEX:
                return createYandexDriver(browser.version);
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }

    private static WebDriver createChromeDriver(String version) {
        WebDriverManager.chromedriver()
                .browserVersion(version)
                .setup();

        ChromeOptions options = new ChromeOptions()
                .addArguments(
                        "--no-sandbox",
                        "--disable-dev-shm-usage",
                        "--window-size=1920,1080"
                );

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    private static WebDriver createFirefoxDriver(String version) {
        WebDriverManager.firefoxdriver()
                .browserVersion(version)
                .setup();

        FirefoxOptions options = new FirefoxOptions()
                .addArguments(
                        "--width=1920",
                        "--height=1080"
                );

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }

    private static WebDriver createYandexDriver(String version) {
        //Местоположение локального Yandex Driver
        String yandexDriverPath = "C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\yandexdriver.exe";

        File yandexDriver = new File(yandexDriverPath);
        if (!yandexDriver.exists()) {
            throw new RuntimeException("YandexDriver not found at: " + yandexDriverPath);
        }

        System.setProperty("webdriver.chrome.driver", yandexDriverPath);

        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files (x86)\\Yandex\\YandexBrowser\\Application\\browser.exe");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
