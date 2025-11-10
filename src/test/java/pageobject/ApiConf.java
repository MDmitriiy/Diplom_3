package pageobject;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static io.restassured.RestAssured.given;

public class ApiConf {
    public static final String BASE_URL = "https://stellarburgers.education-services.ru";
    public static final String API_BASE_URL = "https://stellarburgers.education-services.ru/api";
    protected String testEmail;
    protected String testPassword = "password123";
    protected String authToken;

    @BeforeEach
    public void setUp() {
        Configuration.timeout = 10000;
        Configuration.browserSize = "1920x1080";
    }

    @AfterEach
    public void tearDown() {
        closeWebDriver();
    }

    @Step("Создание тестового пользователя")
    public void createTestUser(UserData userData) {
        given()
                .header("Content-type", "application/json")
                .body(userData)
                .post(API_BASE_URL + "/auth/register");
    }

    @Step("Получение токена пользователя")
    public String getUserToken(String email, String password) {
        UserData loginData = new UserData(null, email, password);
        ValidatableResponse response = given()
                .header("Content-type", "application/json")
                .body(loginData)
                .post(API_BASE_URL + "/auth/login")
                .then();

        if (response.extract().statusCode() == 200) {
            return response.extract().jsonPath().getString("accessToken");
        }
        return null;
    }

    @Step("Удаление тестового пользователя")
    public void deleteUserViaApi(String authToken) {
        if (authToken != null) {
            try {
                given()
                        .header("Authorization", authToken)
                        .delete(API_BASE_URL + "/auth/user");
            } catch (Exception ignored) {
            }
        }
    }

    public WebDriver setupDriver(BrowserConfig.BrowserType browserType) {
        WebDriver driver = BrowserConfig.createDriver(browserType);
        WebDriverRunner.setWebDriver(driver);
        return driver;
    }

    protected void setupTest(BrowserConfig.BrowserType browserType) {
        testEmail = "test" + System.currentTimeMillis() + "@example.com";
        setupDriver(browserType);
    }

    protected void cleanupTest() {
        authToken = getUserToken(testEmail, testPassword);
        deleteUserViaApi(authToken);
    }
}
