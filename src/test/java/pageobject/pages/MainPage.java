package pageobject.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class MainPage {
    private final SelenideElement personalCabinetButton = $(By.xpath("//*[@id='root']/div/header/nav/a/p"));
    private final SelenideElement loginButton = $(By.xpath("//button[text()='Войти в аккаунт']"));
    private final SelenideElement bunsTab = $(By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Булки']/ancestor::div[contains(@class, 'tab_tab__')]"));
    private final SelenideElement saucesTab = $(By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Соусы']/ancestor::div[contains(@class, 'tab_tab__')]"));
    private final SelenideElement fillingsTab = $(By.xpath("//div[contains(@class, 'tab_tab__')]//span[text()='Начинки']/ancestor::div[contains(@class, 'tab_tab__')]"));

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

    @Step("Проверка переключения до нужной записи '{sectionText}'")
    public boolean isSectionInViewport(String sectionText) {
        SelenideElement section = $(By.xpath("//h2[text()='" + sectionText + "']"));

        if (!section.is(visible)) {
            return false;
        }

        Boolean inViewport = Selenide.executeJavaScript(
                "var rect = arguments[0].getBoundingClientRect();" +
                        "return (rect.top >= 0 && rect.left >= 0 && " +
                        "rect.bottom <= (window.innerHeight || document.documentElement.clientHeight) && " +
                        "rect.right <= (window.innerWidth || document.documentElement.clientWidth));",
                section
        );

        return inViewport != null && inViewport;
    }

    @Step("Прокрутка к секции '{sectionText}'")
    public void scrollToSection(String sectionText) {
        SelenideElement section = $(By.xpath("//h2[text()='" + sectionText + "']"));
        section.scrollIntoView(true);

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

    public void waitForPersonalCabinetButton() {
        personalCabinetButton.shouldBe(Condition.visible);
    }

    @Step("Ожидание загрузки главной страницы")
    public void waitForMainPageToLoad() {
        $(By.xpath("//h1[text()='Соберите бургер']")).shouldBe(Condition.visible);
    }

    @Step("Проверка видимости главной страницы")
    public boolean isMainPageVisible() {
        SelenideElement header = $(By.xpath("//h1[text()='Соберите бургер']"));
        return header.exists() && header.isDisplayed();
    }
}
