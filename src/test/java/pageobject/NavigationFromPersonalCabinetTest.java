package pageobject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;

import pageobject.pages.MainPage;
import pageobject.pages.LoginPage;
import pageobject.pages.ProfilePage;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationFromPersonalCabinetTest {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private static final String API_BASE_URL = "https://stellarburgers.education-services.ru/api";
    private String testEmail;
    private String testPassword = "password123";

    @AfterEach
    @Step("Завершение теста")
    public void tearDown() {
        closeWebDriver();
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Переход из личного кабинета в конструктор")
    public void navigateToConstructorFromPersonalCabinet(BrowserSetup.Browser browserType) {
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        createTestUser(testEmail, testPassword);
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        open(BASE_URL);
        sleep(3000);
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickPersonalCabinet();
        ValidatableResponse loginResponse = loginPage.login(testEmail, testPassword);
        loginResponse.statusCode(200);
        ProfilePage profilePage = mainPage.goToProfile();
        assertTrue(profilePage.isProfilePageVisible(), "Страница профиля не отображается");
        mainPage = profilePage.clickConstructorLink();
        assertTrue(mainPage.isMainPageVisible(), "Переход в конструктор не состоялся");
        String authToken = getUserToken(testEmail, testPassword);
        if (authToken != null) {
            try {
                deleteUserViaApi(authToken);
            } catch (Exception ignored) {
            }
        }
    }

    @ParameterizedTest
    @EnumSource(BrowserSetup.Browser.class)
    @Step("Переход из личного кабинета в главную страницу через логотип")
    public void navigateToMainPageViaLogoFromPersonalCabinet(BrowserSetup.Browser browserType) {
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        createTestUser(testEmail, testPassword);
        WebDriver driver = BrowserSetup.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        open(BASE_URL);
        sleep(3000);
        MainPage mainPage = new MainPage();
        LoginPage loginPage = mainPage.clickPersonalCabinet();
        ValidatableResponse loginResponse = loginPage.login(testEmail, testPassword);
        loginResponse.statusCode(200);
        ProfilePage profilePage = mainPage.goToProfile();
        assertTrue(profilePage.isProfilePageVisible(), "Страница профиля не отображается");
        mainPage = profilePage.clickLogo();
        assertTrue(mainPage.isMainPageVisible(), "Переход по логотипу не состоялся");
        String authToken = getUserToken(testEmail, testPassword);
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

    @Step("Удаление тестового пользователя")
    private void deleteUserViaApi(String authToken) {
        given()
                .header("Authorization", authToken)
                .delete(API_BASE_URL + "/auth/user");
    }
}
