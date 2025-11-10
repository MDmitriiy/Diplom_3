package pageobject.tests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.UserData;
import pageobject.pages.MainPage;
import pageobject.pages.LoginPage;
import pageobject.pages.ProfilePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationFromPersonalCabinetTest extends ApiConf {
    private String testEmail;
    private String testPassword = "password123";

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void navigateToConstructorFromPersonalCabinet() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        UserData userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
        open(BASE_URL);
        MainPage mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();
        LoginPage loginPage = mainPage.clickPersonalCabinet();
        loginPage.waitForLoginPageToLoad();
        ValidatableResponse loginResponse = loginPage.login(testEmail, testPassword);
        loginResponse.statusCode(200);
        mainPage.waitForPersonalCabinetButton();
        ProfilePage profilePage = mainPage.goToProfile();
        profilePage.waitForProfilePageToLoad(); // Ожидаем загрузку страницы
        assertTrue(profilePage.isProfilePageVisible(), "Страница профиля не отображается"); // Проверяем видимость
        mainPage = profilePage.clickConstructorLink();
        mainPage.waitForMainPageToLoad();
        assertTrue(mainPage.isMainPageVisible(), "Переход в конструктор не состоялся");
        cleanupTest();
    }

    @Test
    @DisplayName("Переход из личного кабинета в главную страницу через логотип")
    public void navigateToMainPageViaLogoFromPersonalCabinet() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        UserData userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
        open(BASE_URL);
        MainPage mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();
        LoginPage loginPage = mainPage.clickPersonalCabinet();
        loginPage.waitForLoginPageToLoad();
        ValidatableResponse loginResponse = loginPage.login(testEmail, testPassword);
        loginResponse.statusCode(200);
        mainPage.waitForPersonalCabinetButton();
        ProfilePage profilePage = mainPage.goToProfile();
        profilePage.waitForProfilePageToLoad(); // Ожидаем загрузку страницы
        assertTrue(profilePage.isProfilePageVisible(), "Страница профиля не отображается"); // Проверяем видимость
        mainPage = profilePage.clickLogo();
        mainPage.waitForMainPageToLoad();
        assertTrue(mainPage.isMainPageVisible(), "Переход по логотипу не состоялся");
        cleanupTest();
    }
}
