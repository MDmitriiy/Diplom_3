package pageobject.tests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.UserData;
import pageobject.pages.MainPage;
import pageobject.pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PersonalCabinetTest extends ApiConf {

    @Test
    @DisplayName("Переход в профиль через личный кабинет")
    public void navigateToProfileViaPersonalCabinet() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        String testEmail = "test" + System.currentTimeMillis() + "@example.com";
        UserData userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
        open(BASE_URL);
        MainPage mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();
        LoginPage loginPage = mainPage.clickPersonalCabinet();
        loginPage.waitForLoginPageToLoad();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        mainPage.waitForPersonalCabinetButton();
        mainPage.clickPersonalCabinet();
        assertTrue(mainPage.isPersonalCabinetButtonVisible(), "Страница профиля не отображается");
        cleanupTest();
    }
}
