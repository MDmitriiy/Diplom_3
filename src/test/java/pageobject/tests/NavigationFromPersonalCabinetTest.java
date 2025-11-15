package pageobject.tests;

import org.junit.jupiter.api.*;
import pageobject.ApiConf;
import pageobject.BrowserConfig;
import pageobject.UserData;
import pageobject.pages.MainPage;
import pageobject.pages.LoginPage;
import pageobject.pages.ProfilePage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationFromPersonalCabinetTest extends ApiConf {

    private UserData userData;
    private MainPage mainPage;
    private ProfilePage profilePage;

    @BeforeEach
    public void setUp() {
        setupTest(BrowserConfig.getBrowserFromConfig());
        userData = new UserData("Тестовый Пользователь", testEmail, testPassword);
        createTestUser(userData);

        open(BASE_URL);
        mainPage = new MainPage();
        mainPage.waitForMainPageToLoad();

        LoginPage loginPage = mainPage.clickPersonalCabinet();
        loginPage.waitForLoginPageToLoad();
        loginPage.login(testEmail, testPassword).statusCode(200);

        mainPage.waitForPersonalCabinetButton();
        profilePage = mainPage.goToProfile();
        profilePage.waitForProfilePageToLoad();
        assertTrue(profilePage.isProfilePageVisible(), "Страница профиля не отображается");
    }

    @AfterEach
    public void tearDown() {
        cleanupTest();
        closeWebDriver();
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор")
    public void navigateToConstructorFromPersonalCabinet() {
        mainPage = profilePage.clickConstructorLink();
        mainPage.waitForMainPageToLoad();
        assertTrue(mainPage.isMainPageVisible(), "Переход в конструктор не состоялся");
    }

    @Test
    @DisplayName("Переход из личного кабинета в главную страницу через логотип")
    public void navigateToMainPageViaLogoFromPersonalCabinet() {
        mainPage = profilePage.clickLogo();
        mainPage.waitForMainPageToLoad();
        assertTrue(mainPage.isMainPageVisible(), "Переход по логотипу не состоялся");
    }
}
