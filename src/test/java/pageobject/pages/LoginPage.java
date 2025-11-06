package pageobject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static io.restassured.RestAssured.given;

public class LoginPage {
    private static final String API_BASE_URL = "https://stellarburgers.education-services.ru/api";
    private final SelenideElement registrationLink = $(By.xpath("//a[@href='/register']"));
    private final SelenideElement forgotPasswordLink = $(By.xpath("//a[@href='/forgot-password']"));
    private final SelenideElement emailField = $(By.xpath("//label[text()='Email']/following-sibling::input"));
    private final SelenideElement passwordField = $(By.xpath("//label[text()='Пароль']/following-sibling::input"));
    private final SelenideElement loginButton = $(By.xpath("//button[text()='Войти']"));

    @Step("Клик по ссылке 'Зарегистрироваться'")
    public RegistrationPage clickRegisterButton() {
        registrationLink.click();
        return page(RegistrationPage.class);
    }

    @Step("Клик по ссылке 'Восстановить пароль'")
    public ForgotPasswordPage clickForgotPasswordLink() {
        forgotPasswordLink.click();
        return page(ForgotPasswordPage.class);
    }

    @Step("Вход в аккаунт")
    public ValidatableResponse login(String email, String password) {
        emailField.setValue(email);
        passwordField.setValue(password);
        loginButton.click();

        return given()
                .header("Content-type", "application/json")
                .body(String.format("{\"email\":\"%s\",\"password\":\"%s\"}", email, password))
                .post(API_BASE_URL + "/auth/login")
                .then();
    }
    @Step("Проверка видимости формы входа")
    public boolean isLoginFormVisible() {
        return emailField.isDisplayed() && passwordField.isDisplayed() && loginButton.isDisplayed();
    }
    @Step("Проверка видимости страницы входа")
    public boolean isLoginPageVisible() {
        return $(By.xpath("//h2[text()='Вход']")).isDisplayed();
    }
}