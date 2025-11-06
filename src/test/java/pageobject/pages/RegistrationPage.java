package pageobject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class RegistrationPage {
    private final SelenideElement nameField = $(By.xpath("//label[text()='Имя']/following-sibling::input"));
    private final SelenideElement emailField = $(By.xpath("//label[text()='Email']/following-sibling::input"));
    private final SelenideElement passwordField = $(By.xpath("//label[text()='Пароль']/following-sibling::input"));
    private final SelenideElement registerButton = $(By.xpath("//button[text()='Зарегистрироваться']"));
    private final SelenideElement loginLink = $(By.xpath("//a[@href='/login']"));
    private final SelenideElement passwordError = $(By.xpath("//p[text()='Некорректный пароль']"));

    @Step("Ввод имени")
    public RegistrationPage setName(String name) {
        nameField.setValue(name);
        return this;
    }

    @Step("Ввод email")
    public RegistrationPage setEmail(String email) {
        emailField.setValue(email);
        return this;
    }

    @Step("Ввод пароля")
    public RegistrationPage setPassword(String password) {
        passwordField.setValue(password);
        return this;
    }

    @Step("Нажатие на кнопку регистрации")
    public void clickRegisterButton() {
        registerButton.click();
    }

    @Step("Регистрация")
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickRegisterButton();
    }

    @Step("Переход на страницу логина")
    public LoginPage clickLoginLink() {
        loginLink.click();
        return page(LoginPage.class);
    }

    @Step("Проверка видимости сообщения об ошибке")
    public boolean isPasswordErrorVisible() {
        return passwordError.isDisplayed();
    }

    @Step("Получение сообщения об ошибке")
    public String getPasswordErrorMessage() {
        return passwordError.getText();
    }
}
