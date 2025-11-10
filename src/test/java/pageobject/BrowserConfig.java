package pageobject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BrowserConfig {
    public enum BrowserType {
        CHROME,
        YANDEX
    }

    public static BrowserType getBrowserFromConfig() {
        String browser = System.getProperty("browser", "chrome");
        try {
            return BrowserType.valueOf(browser.toUpperCase());
        } catch (IllegalArgumentException e) {
            return BrowserType.CHROME;
        }
    }

    public static WebDriver createDriver(BrowserType browserType) {
        switch (browserType) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                return new ChromeDriver(chromeOptions);
            case YANDEX:
                // Установка пути к YandexDriver
                System.setProperty("webdriver.chrome.driver",
                        "C:\\Users\\dima1\\OneDrive\\Desktop\\yandexdriver.exe");
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.addArguments("--no-sandbox");
                yandexOptions.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(yandexOptions);
            default:
                ChromeOptions defaultOptions = new ChromeOptions();
                return new ChromeDriver(defaultOptions);
        }
    }
}
