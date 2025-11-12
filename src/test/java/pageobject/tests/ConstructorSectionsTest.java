package pageobject.tests;

import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.pages.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstructorSectionsTest extends ApiConf {

    private MainPage mainPage;

    @BeforeEach
    @Step("Настройка драйвера и открытие главной страницы")
    public void setupAndOpenMainPage() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        open(BASE_URL);
        mainPage = new MainPage();
    }

    @AfterEach
    public void tearDown() {
        authToken = null;
        closeWebDriver();
    }

    @Test
    @DisplayName("Проверка навигации к секции булок")
    public void testNavigateToBunsSection() {
        mainPage.clickSaucesTab();
        mainPage.scrollToSection("Соусы");
        mainPage.clickBunsTab();
        mainPage.scrollToSection("Булки");
        assertTrue(mainPage.isSectionInViewport("Булки"), "Секция 'Булки' не в области просмотра");
    }

    @Test
    @DisplayName("Проверка навигации к секции соусов")
    public void testNavigateToSaucesSection() {
        mainPage.clickSaucesTab();
        mainPage.scrollToSection("Соусы");
        assertTrue(mainPage.isSectionInViewport("Соусы"), "Секция 'Соусы' не в области просмотра");
    }

    @Test
    @DisplayName("Проверка навигации к секции начинок")
    public void testNavigateToFillingsSection() {
        mainPage.clickFillingsTab();
        mainPage.scrollToSection("Начинки");
        assertTrue(mainPage.isSectionInViewport("Начинки"), "Секция 'Начинки' не в области просмотра");
    }
}
