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
                return new ChromeDriver(new ChromeOptions());
            case YANDEX:
                // Путь передается извне (через системные свойства)
                //-Dwebdriver.chrome.driver="path/to/yandexdriver.exe"
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.addArguments("--no-sandbox");
                yandexOptions.addArguments("--disable-dev-shm-usage");
                return new ChromeDriver(yandexOptions);
            default:
                return new ChromeDriver(new ChromeOptions());
        }
    }
}