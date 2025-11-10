package pageobject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class ProfilePage {
    private final SelenideElement constructorLink = $(By.xpath("//p[text()='Конструктор']"));
    private final SelenideElement logo = $(By.xpath("//*[@id='root']/div/header/nav/div/a"));
    private final SelenideElement nameField = $(By.xpath("//label[text()='Имя']/following-sibling::input"));
    private final SelenideElement emailField = $(By.xpath("//label[text()='Логин']/following-sibling::input"));
    private final SelenideElement exitButton = $(By.xpath("//button[text()='Выход']"));

    @Step("Клик по ссылке 'Конструктор'")
    public MainPage clickConstructorLink() {
        constructorLink.click();
        return page(MainPage.class);
    }

    @Step("Клик по логотипу")
    public MainPage clickLogo() {
        logo.click();
        return page(MainPage.class);
    }

    @Step("Ожидание загрузки страницы профиля")
    public void waitForProfilePageToLoad() {
        nameField.shouldBe(Condition.visible);
        emailField.shouldBe(Condition.visible);
        exitButton.shouldBe(Condition.visible);
    }

    @Step("Проверка видимости страницы профиля")
    public boolean isProfilePageVisible() {
        try {
            waitForProfilePageToLoad();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Клик по кнопке 'Выход'")
    public LoginPage clickLogoutButton() {
        exitButton.click();
        return page(LoginPage.class);
    }
}
