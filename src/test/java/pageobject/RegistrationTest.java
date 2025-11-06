package pageobject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;

import pageobject.pages.MainPage;
import pageobject.pages.RegistrationPage;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationTest {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private static final String API_BASE_URL = "https://stellarburgers.education-services.ru/api";
    private String registeredEmail;
    private String authToken;

    @BeforeEach
    @Step("Настройка тестовых данных")
    public void setUp() {
        Configuration.timeout = 100000;
        Configuration.browserSize = "1920x1080";
    }

    @AfterEach
    @Step("Очистка тестовых данных")
    public void tearDown() {
        if (authToken != null && registeredEmail != null) {
            try {
                deleteUserViaApi();
            } catch (Exception ignored) {
            }
        }
        closeWebDriver();
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Регистрация с коротким паролем")
    public void registerWithShortPasswordShowsError(BrowserSetup.Browser browserType) {
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);

        open(BASE_URL);
        sleep(3000);

        MainPage headerPage = new MainPage();
        RegistrationPage registrationPage = headerPage.clickPersonalCabinet()
                .clickRegisterButton();

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



    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Регистрация пользователя в браузерах")
    public void registerUserInBrowsers(BrowserSetup.Browser browserType) {
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);

        open(BASE_URL);
        sleep(3000);

        MainPage headerPage = new MainPage();
        RegistrationPage registrationPage = headerPage.clickPersonalCabinet()
                .clickRegisterButton();

        registeredEmail = "test" + System.currentTimeMillis() + "@example.com";
        String registeredPassword = "password123";
        registrationPage.register(
                "Тестовый Пользователь",
                registeredEmail,
                registeredPassword
        );

        authToken = getUserToken(registeredEmail, registeredPassword);
    }

    @Step("Получение токена пользователя")
    private String getUserToken(String email, String password) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
                .post(API_BASE_URL + "/auth/login");

        if (response.statusCode() == 200) {
            return response.jsonPath().getString("accessToken");
        }
        return null;
    }

    @Step("Удаление пользователя")
    private void deleteUserViaApi() {
        if (authToken != null) {
            given()
                    .header("Authorization", authToken)
                    .delete(API_BASE_URL + "/auth/user");
        }
    }
}
