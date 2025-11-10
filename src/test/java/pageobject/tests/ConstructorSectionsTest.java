package pageobject.tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.pages.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorSectionsTest extends ApiConf {

    @Test
    @DisplayName("Проверка навигации к секции булок")
    public void testNavigateToBunsSection() {
        setupAndOpenMainPage();
        MainPage mainPage = new MainPage();
        mainPage.clickSaucesTab();
        mainPage.scrollToSection("Соусы");
        mainPage.clickBunsTab();
        mainPage.scrollToSection("Булки");
        assertTrue(mainPage.isSectionInViewport("Булки"), "Секция 'Булки' не в области просмотра");
    }

    @Test
    @DisplayName("Проверка навигации к секции соусов")
    public void testNavigateToSaucesSection() {
        setupAndOpenMainPage();
        MainPage mainPage = new MainPage();
        mainPage.clickSaucesTab();
        mainPage.scrollToSection("Соусы");
        assertTrue(mainPage.isSectionInViewport("Соусы"), "Секция 'Соусы' не в области просмотра");
    }

    @Test
    @DisplayName("Проверка навигации к секции начинок")
    public void testNavigateToFillingsSection() {
        setupAndOpenMainPage();
        MainPage mainPage = new MainPage();
        mainPage.clickFillingsTab();
        mainPage.scrollToSection("Начинки");
        assertTrue(mainPage.isSectionInViewport("Начинки"), "Секция 'Начинки' не в области просмотра");
    }

    @Step("Настройка драйвера и открытие главной страницы")
    private void setupAndOpenMainPage() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        open(BASE_URL);
    }
}
