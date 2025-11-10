package pageobject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ForgotPasswordPage {

    private final SelenideElement loginLink = $(By.xpath("//a[@href='/login']"));

    @Step("Переход на страницу логина")
    public LoginPage clickLoginLink() {
        loginLink.click();
        return page(LoginPage.class);
    }

    @Step("Ожидание загрузки страницы восстановления пароля")
    public void waitForForgotPasswordPageToLoad() {
        $(By.xpath("//h2[text()='Восстановление пароля']")).shouldBe(Condition.visible);
    }
}

