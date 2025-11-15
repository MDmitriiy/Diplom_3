package pageobject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static io.restassured.RestAssured.given;

public class LoginPage {
    private static final String API_BASE_URL = "https://stellarburgers.education-services.ru/api";

    private final SelenideElement emailField = $(By.xpath("//label[text()='Email']/following-sibling::input"));
    private final SelenideElement passwordField = $(By.xpath("//label[text()='Пароль']/following-sibling::input"));
    private final SelenideElement loginButton = $(By.xpath("//button[text()='Войти']"));
    private final SelenideElement registerLink = $(By.xpath("//a[@href='/register']"));

    @Step("Ввод email")
    public LoginPage setEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    @Step("Ввод пароля")
    public LoginPage setPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    @Step("Нажатие на кнопку входа")
    public void clickLoginButton() {
        loginButton.click();
    }

    @Step("Вход в систему")
    public ValidatableResponse login(String email, String password) {
        setEmail(email);
        setPassword(password);
        clickLoginButton();

        return given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
                .post(API_BASE_URL + "/auth/login")
                .then();
    }

    @Step("Переход на страницу регистрации")
    public RegistrationPage clickRegisterButton() {
        registerLink.click();
        return page(RegistrationPage.class);
    }

    @Step("Ожидание загрузки страницы входа")
    public void waitForLoginPageToLoad() {
        $(By.xpath("//h2[text()='Вход']")).shouldBe(Condition.visible);
    }

    @Step("Проверка видимости страницы входа")
    public boolean isLoginPageVisible() {
        return $(By.xpath("//h2[text()='Вход']")).is(Condition.visible);
    }
}
