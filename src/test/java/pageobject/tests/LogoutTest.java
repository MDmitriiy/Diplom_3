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

public class LogoutTest extends ApiConf {

    @Test
    @DisplayName("Выход из аккаунта через личный кабинет")
    public void logoutViaPersonalCabinet() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        String testEmail = "test" + System.currentTimeMillis() + "@example.com";
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

        LoginPage logoutPage = profilePage.clickLogoutButton();
        logoutPage.waitForLoginPageToLoad();
        assertTrue(logoutPage.isLoginPageVisible(), "Выход из аккаунта не удался");

        cleanupTest();
    }
}
