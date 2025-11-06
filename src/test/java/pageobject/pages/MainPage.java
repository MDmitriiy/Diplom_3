package pageobject.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    private final SelenideElement personalCabinetButton = $(By.xpath("//*[@id='root']/div/header/nav/a/p"));
    private final SelenideElement loginButton = $(By.xpath("//button[text()='Войти в аккаунт']"));
    private final SelenideElement constructorLink = $(By.xpath("//p[text()='Конструктор']"));
    private final SelenideElement logo = $(By.xpath("//header//div[@class='AppHeader_header__logo__2D0X2']"));
    private final SelenideElement bunsTab = $(By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Булки']/ancestor::div[contains(@class, 'tab_tab__')]"));
    private final SelenideElement saucesTab = $(By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Соусы']/ancestor::div[contains(@class, 'tab_tab__')]"));
    private final SelenideElement fillingsTab = $(By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Начинки']/ancestor::div[contains(@class, 'tab_tab__')]"));
    private final SelenideElement ingredientsSection = $(By.xpath("//section[contains(@class, 'BurgerIngredients_ingredients')]"));

    @Step("Переход к конструктору")
    public MainPage goToConstructor() {
        constructorLink.click();
        return this;
    }

    @Step("Переход на главную через логотип")
    public MainPage goToMainViaLogo() {
        logo.click();
        return this;
    }

    @Step("Клик по вкладке 'Булки'")
    public MainPage clickBunsTab() {
        bunsTab.click();
        return this;
    }

    @Step("Клик по вкладке 'Соусы'")
    public MainPage clickSaucesTab() {
        saucesTab.click();
        return this;
    }

    @Step("Клик по вкладке 'Начинки'")
    public MainPage clickFillingsTab() {
        fillingsTab.click();
        return this;
    }

    @Step("Проверка активности вкладки 'Булки'")
    public boolean isBunsTabActive() {
        return bunsTab.getAttribute("class").contains("tab_tab_type_current");
    }

    @Step("Проверка активности вкладки 'Соусы'")
    public boolean isSaucesTabActive() {
        return saucesTab.getAttribute("class").contains("tab_tab_type_current");
    }

    @Step("Проверка активности вкладки 'Начинки'")
    public boolean isFillingsTabActive() {
        return fillingsTab.getAttribute("class").contains("tab_tab_type_current");
    }

    @Step("Проверка скроллинга секции '{sectionText}'")
    public boolean isSectionScrolledTo(String sectionText) {
        SelenideElement section = $(By.xpath("//h2[text()='" + sectionText + "']"));
        return section.is(visible);
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public LoginPage clickPersonalCabinet() {
        personalCabinetButton.click();
        return page(LoginPage.class);
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public LoginPage clickLoginButton() {
        loginButton.click();
        return page(LoginPage.class);
    }

    @Step("Переход в профиль")
    public ProfilePage goToProfile() {
        personalCabinetButton.click();
        return page(ProfilePage.class);
    }

    @Step("Проверка видимости кнопки 'Личный кабинет'")
    public boolean isPersonalCabinetButtonVisible() {
        return personalCabinetButton.isDisplayed();
    }

    @Step("Клик по ссылке 'Конструктор'")
    public MainPage clickConstructorLink() {
        constructorLink.click();
        return this;
    }

    @Step("Клик по логотипу")
    public MainPage clickLogo() {
        logo.click();
        return this;
    }

    @Step("Проверка видимости главной страницы")
    public boolean isMainPageVisible() {
        SelenideElement header = $(By.xpath("//h1[text()='Соберите бургер']"));
        return header.exists() && header.isDisplayed();
    }
}
