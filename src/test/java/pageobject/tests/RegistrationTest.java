package pageobject.tests;

import org.junit.jupiter.api.*;
import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.UserData;
import pageobject.pages.MainPage;
import pageobject.pages.RegistrationPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest extends ApiConf {

    private MainPage mainPage;
    private RegistrationPage registrationPage;

    @BeforeEach
    public void setUp() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        open(BASE_URL);
        mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();

        registrationPage = mainPage.clickPersonalCabinet()
                .clickRegisterButton();
        registrationPage.waitForRegistrationPageToLoad();
    }

    @AfterEach
    public void tearDown() {
        authToken = null;
        cleanupTest();
        closeWebDriver();
    }

    @Test
    @DisplayName("Регистрация с коротким паролем")
    public void registerWithShortPasswordShowsError() {
        String shortPassword = "12345";
        String testEmail = "test" + System.currentTimeMillis() + "@example.com";

        registrationPage.register(
                "Тестовый Пользователь",
                testEmail,
                shortPassword
        );

        assertTrue(registrationPage.isPasswordErrorVisible(),
                "Сообщение об ошибке 'Некорректный пароль' не отображается");

        assertEquals("Некорректный пароль",
                registrationPage.getPasswordErrorMessage(),
                "Текст ошибки не соответствует ожидаемому");
    }

    @Test
    @DisplayName("Регистрация пользователя")
    public void registerUser() {
        String registeredEmail = "test" + System.currentTimeMillis() + "@example.com";
        String registeredPassword = "password123";

        UserData userData = new UserData("Тестовый Пользователь", registeredEmail, registeredPassword);
        registrationPage.register(userData);

        authToken = getUserToken(registeredEmail, registeredPassword);
    }
}
