package pageobject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ForgotPasswordPage {
    private final SelenideElement loginLink = $(By.xpath("//a[@href='/login']"));

    @Step("Клик по ссылке 'Войти'")
    public LoginPage clickLoginLink() {
        loginLink.click();
        return page(LoginPage.class);
    }
}
