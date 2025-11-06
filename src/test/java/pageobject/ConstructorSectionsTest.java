package pageobject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;

import pageobject.pages.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorSectionsTest {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";

    @BeforeEach
    @Step("Настройка теста")
    public void setUp() {
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
    }

    @AfterEach
    @Step("Завершение теста")
    public void tearDown() {
        closeWebDriver();
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Проверка навигации к секции булок в браузере {browserType}")
    public void testNavigateToBunsSection(BrowserSetup.Browser browserType) {
        setupAndOpenMainPage(browserType);
        MainPage mainPage = new MainPage();
        mainPage.clickFillingsTab();
        sleep(1000);
        assertTrue(mainPage.isFillingsTabActive(), "Вкладка 'Начинки' не активна после клика");
        mainPage.clickBunsTab();
        sleep(1000);
        assertTrue(mainPage.isBunsTabActive(), "Вкладка 'Булки' не активна после клика");
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Проверка навигации к секции соусов в браузере {browserType}")
    public void testNavigateToSaucesSection(BrowserSetup.Browser browserType) {
        setupAndOpenMainPage(browserType);
        MainPage mainPage = new MainPage();
        mainPage.clickSaucesTab();
        sleep(1000);
        assertTrue(mainPage.isSaucesTabActive(), "Вкладка 'Соусы' не активна после клика");
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Проверка навигации к секции начинок в браузере {browserType}")
    public void testNavigateToFillingsSection(BrowserSetup.Browser browserType) {
        setupAndOpenMainPage(browserType);
        MainPage mainPage = new MainPage();
        mainPage.clickFillingsTab();
        sleep(1000);
        assertTrue(mainPage.isFillingsTabActive(), "Вкладка 'Начинки' не активна после клика");
    }

    @Step("Настройка драйвера и открытие главной страницы в браузере {browserType}")
    private void setupAndOpenMainPage(BrowserSetup.Browser browserType) {
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        open(BASE_URL);
        sleep(3000);
    }
}
