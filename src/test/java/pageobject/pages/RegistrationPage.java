package pageobject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import pageobject.UserData;

import static com.codeborne.selenide.Selenide.$x;

public class RegistrationPage {
    private SelenideElement nameInput = $x("//label[text()='Имя']/following-sibling::input");
    private SelenideElement emailInput = $x("//label[text()='Email']/following-sibling::input");
    private SelenideElement passwordInput = $x("//label[text()='Пароль']/following-sibling::input");
    private SelenideElement registerButton = $x("//button[text()='Зарегистрироваться']");
    private SelenideElement passwordError = $x("//p[text()='Некорректный пароль']");

    @Step("Ожидание загрузки страницы регистрации")
    public void waitForRegistrationPageToLoad() {
        nameInput.shouldBe(com.codeborne.selenide.Condition.visible);
    }
@Step("Регистрация пользователя")
    public void register(String name, String email, String password) {
        nameInput.setValue(name);
        emailInput.setValue(email);
        passwordInput.setValue(password);
        registerButton.click();
    }
@Step("Регистрация пользователя")
    public void register(UserData userData) {
        nameInput.setValue(userData.getName());
        emailInput.setValue(userData.getEmail());
        passwordInput.setValue(userData.getPassword());
        registerButton.click();
    }
@Step("Проверка отображения ошибки пароля")
    public boolean isPasswordErrorVisible() {
        return passwordError.isDisplayed();
    }
@Step("Получение сообщения об ошибке пароля")
    public String getPasswordErrorMessage() {
        return passwordError.getText();
    }
@Step("Переход на страницу логина")
    public LoginPage clickLoginLink() {
        $x("//a[@href='/login']").click();
        return new LoginPage();
    }
}
