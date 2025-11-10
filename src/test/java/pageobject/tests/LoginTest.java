package pageobject.tests;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.UserData;
import pageobject.pages.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends ApiConf {

    @Test
    @DisplayName("Вход через главную страницу")
    public void loginViaMainPageButton() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        UserData userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
        open(BASE_URL);
        MainPage mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.waitForLoginPageToLoad();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        mainPage.waitForPersonalCabinetButton();
        assertTrue(mainPage.isPersonalCabinetButtonVisible(), "Вход через главную страницу не удался");
        cleanupTest();
    }

    @Test
    @DisplayName("Вход через личный кабинет")
    public void loginViaPersonalCabinetButton() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        UserData userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
        open(BASE_URL);
        MainPage headerPage = new MainPage();
        headerPage.waitForMainPageToLoad();
        LoginPage loginPage = headerPage.clickPersonalCabinet();
        loginPage.waitForLoginPageToLoad();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        headerPage.waitForPersonalCabinetButton();
        assertTrue(headerPage.isPersonalCabinetButtonVisible(), "Вход через личный кабинет не удался");
        cleanupTest();
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    public void loginViaRegistrationForm() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        UserData userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
        open(BASE_URL + "/register");
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.waitForRegistrationPageToLoad();
        LoginPage loginPage = registrationPage.clickLoginLink();
        loginPage.waitForLoginPageToLoad();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        MainPage headerPage = new MainPage();
        headerPage.waitForPersonalCabinetButton();
        assertTrue(headerPage.isPersonalCabinetButtonVisible(), "Вход через форму регистрации не удался");
        cleanupTest();
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    public void loginViaPasswordRecoveryForm() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        UserData userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
        open(BASE_URL + "/forgot-password");
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();
        forgotPasswordPage.waitForForgotPasswordPageToLoad();
        LoginPage loginPage = forgotPasswordPage.clickLoginLink();
        loginPage.waitForLoginPageToLoad();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        MainPage headerPage = new MainPage();
        headerPage.waitForPersonalCabinetButton();
        assertTrue(headerPage.isPersonalCabinetButtonVisible(), "Вход через форму восстановления пароля не удался");
        cleanupTest();
    }
}
