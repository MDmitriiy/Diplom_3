package pageobject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;

import pageobject.pages.*;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private static final String API_BASE_URL = "https://stellarburgers.education-services.ru/api";
    private String testEmail;
    private String testPassword = "password123";
    private String authToken;

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
    @Step("Вход через главную страницу")
    public void loginViaMainPageButton(BrowserSetup.Browser browserType) {
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        createTestUser(testEmail, testPassword);
        open(BASE_URL);
        sleep(3000);
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickLoginButton();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        assertTrue(mainPage.isPersonalCabinetButtonVisible(), "Вход через главную страницу не удался");
        authToken = getUserToken(testEmail, testPassword);
        if (authToken != null) {
            try {
                deleteUserViaApi(authToken);
            } catch (Exception ignored) {
            }
        }
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Вход через личный кабинет")
    public void loginViaPersonalCabinetButton(BrowserSetup.Browser browserType) {
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        createTestUser(testEmail, testPassword);
        open(BASE_URL);
        sleep(3000);
        MainPage headerPage = new MainPage();
        LoginPage loginPage = headerPage.clickPersonalCabinet();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        assertTrue(headerPage.isPersonalCabinetButtonVisible(), "Вход через личный кабинет не удался");
        authToken = getUserToken(testEmail, testPassword);
        if (authToken != null) {
            try {
                deleteUserViaApi(authToken);
            } catch (Exception ignored) {
            }
        }
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Вход через форму регистрации")
    public void loginViaRegistrationForm(BrowserSetup.Browser browserType) {
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        createTestUser(testEmail, testPassword);
        open(BASE_URL + "/register");
        sleep(3000);
        RegistrationPage registrationPage = new RegistrationPage();
        LoginPage loginPage = registrationPage.clickLoginLink();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        MainPage headerPage = new MainPage();
        assertTrue(headerPage.isPersonalCabinetButtonVisible(), "Вход через форму регистрации не удался");
        authToken = getUserToken(testEmail, testPassword);
        if (authToken != null) {
            try {
                deleteUserViaApi(authToken);
            } catch (Exception ignored) {
            }
        }
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Вход через форму восстановления пароля")
    public void loginViaPasswordRecoveryForm(BrowserSetup.Browser browserType) {
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        createTestUser(testEmail, testPassword);
        open(BASE_URL + "/forgot-password");
        sleep(3000);
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();
        LoginPage loginPage = forgotPasswordPage.clickLoginLink();
        ValidatableResponse response = loginPage.login(testEmail, testPassword);
        response.statusCode(200);
        MainPage headerPage = new MainPage();
        assertTrue(headerPage.isPersonalCabinetButtonVisible(), "Вход через форму восстановления пароля не удался");
        authToken = getUserToken(testEmail, testPassword);
        if (authToken != null) {
            try {
                deleteUserViaApi(authToken);
            } catch (Exception ignored) {
            }
        }
    }

    @Step("Создание тестового пользователя")
    private void createTestUser(String email, String password) {
        given()
                .header("Content-type", "application/json")
                .body(String.format("{\"name\":\"Тестовый Пользователь\",\"email\":\"%s\",\"password\":\"%s\"}",
                        email, password))
                .post(API_BASE_URL + "/auth/register");
    }

    @Step("Получение токена пользователя")
    private String getUserToken(String email, String password) {
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
                .post(API_BASE_URL + "/auth/login")
                .then();

        if (response.extract().statusCode() == 200) {
            return response.extract().jsonPath().getString("accessToken");
        }
        return null;
    }

    private void deleteUserViaApi(String authToken) {
        given()
                .header("Authorization", authToken)
                .delete(API_BASE_URL + "/auth/user");
    }
}
