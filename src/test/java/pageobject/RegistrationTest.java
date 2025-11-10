package pageobject;

import org.junit.jupiter.api.*;
import pageobject.pages.MainPage;
import pageobject.pages.RegistrationPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest extends ApiConf {
    private String registeredEmail;

    @Test
    @DisplayName("Регистрация с коротким паролем")
    public void registerWithShortPasswordShowsError() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        open(BASE_URL);
        MainPage headerPage = new MainPage();
        headerPage.waitForMainPageToLoad();

        RegistrationPage registrationPage = headerPage.clickPersonalCabinet()
                .clickRegisterButton();
        registrationPage.waitForRegistrationPageToLoad();

        String shortPassword = "12345"; // Меньше 6 символов
        registeredEmail = "test" + System.currentTimeMillis() + "@example.com";

        registrationPage.register(
                "Тестовый Пользователь",
                registeredEmail,
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
        setupTest(BrowserConfig.getBrowserFromConfig());
        open(BASE_URL);
        MainPage headerPage = new MainPage();
        headerPage.waitForMainPageToLoad();

        RegistrationPage registrationPage = headerPage.clickPersonalCabinet()
                .clickRegisterButton();
        registrationPage.waitForRegistrationPageToLoad();

        registeredEmail = "test" + System.currentTimeMillis() + "@example.com";
        String registeredPassword = "password123";

        UserData userData = new UserData("Тестовый Пользователь", registeredEmail, registeredPassword);
        registrationPage.register(userData);

        authToken = getUserToken(registeredEmail, registeredPassword);
        cleanupTest();
    }
}
