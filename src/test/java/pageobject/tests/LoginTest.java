package pageobject.tests;

import org.junit.jupiter.api.*;
import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.UserData;
import pageobject.pages.*;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends ApiConf {

    private UserData userData;
    private MainPage mainPage;

    @BeforeEach
    public void setUp() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);
    }

    @AfterEach
    public void tearDown() {
        cleanupTest();
        closeWebDriver();
    }

    @Test
    @DisplayName("Вход через главную страницу")
    public void loginViaMainPageButton() {
        open(BASE_URL);
        mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();
        LoginPage loginPage = mainPage.clickLoginButton();
        loginPage.waitForLoginPageToLoad();
        loginPage.login(testEmail, testPassword).statusCode(200);
        mainPage.waitForPersonalCabinetButton();
        assertTrue(mainPage.isPersonalCabinetButtonVisible(), "Вход через главную страницу не удался");
    }

    @Test
    @DisplayName("Вход через личный кабинет")
    public void loginViaPersonalCabinetButton() {
        open(BASE_URL);
        mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();
        LoginPage loginPage = mainPage.clickPersonalCabinet();
        loginPage.waitForLoginPageToLoad();
        loginPage.login(testEmail, testPassword).statusCode(200);
        mainPage.waitForPersonalCabinetButton();
        assertTrue(mainPage.isPersonalCabinetButtonVisible(), "Вход через личный кабинет не удался");
    }

    @Test
    @DisplayName("Вход через форму регистрации")
    public void loginViaRegistrationForm() {
        open(BASE_URL + "/register");
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.waitForRegistrationPageToLoad();
        LoginPage loginPage = registrationPage.clickLoginLink();
        loginPage.waitForLoginPageToLoad();
        loginPage.login(testEmail, testPassword).statusCode(200);
        mainPage = new MainPage();
        mainPage.waitForPersonalCabinetButton();
        assertTrue(mainPage.isPersonalCabinetButtonVisible(), "Вход через форму регистрации не удался");
    }

    @Test
    @DisplayName("Вход через форму восстановления пароля")
    public void loginViaPasswordRecoveryForm() {
        open(BASE_URL + "/forgot-password");
        ForgotPasswordPage forgotPasswordPage = new ForgotPasswordPage();
        forgotPasswordPage.waitForForgotPasswordPageToLoad();
        LoginPage loginPage = forgotPasswordPage.clickLoginLink();
        loginPage.waitForLoginPageToLoad();
        loginPage.login(testEmail, testPassword).statusCode(200);
        mainPage = new MainPage();
        mainPage.waitForPersonalCabinetButton();
        assertTrue(mainPage.isPersonalCabinetButtonVisible(), "Вход через форму восстановления пароля не удался");
    }
}
