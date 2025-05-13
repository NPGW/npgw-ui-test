package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.UserRole;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.User;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.AboutBlankPage;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.common.HeaderComponent;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Slf4j
public class HeaderTest extends BaseTest {

    @Test
    @TmsLink("209")
    @Epic("Header")
    @Feature("Logo")
    @Description("Check that Logo in header contains text 'NPGW' and image")
    public void testLogoContainsTextAndImage() {
        HeaderComponent headerComponent = new DashboardPage(getPage()).getHeader();

        Allure.step("Verify: Logo contains text 'NPGW'");
        assertThat(headerComponent.getLogo()).hasText("NPGW");

        Allure.step("Verify: Logo contains image");
        Assert.assertTrue(headerComponent.getImg().isVisible(), "Image inside logo should be visible");
        Assert.assertNotNull(headerComponent.getImg().getAttribute("src"), "Image should have a 'src' attribute");

        Allure.step("Verify: Image inside logo is fully loaded");
        Assert.assertTrue(headerComponent.isLogoImageLoaded(), "Image inside logo should be fully loaded");
    }

    @Test
    @TmsLink("211")
    @Epic("Header")
    @Feature("Transactions menu item")
    @Description("Check after clicking on Transactions user redirected to Transactions page")
    public void testTransactionsLink() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .getHeader().clickTransactionsLink();

        Allure.step("Verify: Transactions Page URL");
        assertThat(transactionsPage.getPage()).hasURL(Constants.TRANSACTIONS_PAGE_URL);
    }

    @Test
    @TmsLink("242")
    @Epic("Header")
    @Feature("Logo")
    @Description("Check that click on Logo return user to the dashboard page from other pages")
    public void testClickLogoReturnToDashboardPage() {
        DashboardPage dashboardPage = new DashboardPage(getPage())
                .getHeader().clickTransactionsLink()
                .getHeader().clickLogoButton();

        Allure.step("Verify: Dashboard Page URL");
        assertThat(dashboardPage.getPage()).hasURL(Constants.DASHBOARD_PAGE_URL);
    }

    @Test
    @TmsLink("289")
    @Epic("Header")
    @Feature("User menu")
    @Description("Check if the user can change the password through the profile settings in the user menu")
    public void testChangePassword(@Optional("UNAUTHORISED") String userRole) {
        String company = "Change 'CompanyAdmin' user password from menu";
        String email = "company.admin@email.com";
        String password = "CompanyAdmin123!";
        TestUtils.createCompanyIfNeeded(getApiRequestContext(), company);
        TestUtils.createCompanyAdmin(getApiRequestContext(), company, email, password);
        String newPassword = "QWEdsa123@";

        DashboardPage dashboardPage = new AboutBlankPage(getPage())
                .navigate("/login")
                .fillEmailField(email)
                .fillPasswordField(password)
                .clickLoginButtonToChangePassword()
                .fillNewPasswordField(password)
                .fillRepeatNewPasswordField(password)
                .clickSaveButton()
                .fillEmailField(email)
                .fillPasswordField(password)
                .clickLoginButton()
                .getAlert().waitUntilSuccessAlertIsGone()
                .getHeader().clickUserMenuButton()
                .getHeader().clickProfileSettingsButton()
                .getHeader().fillPasswordField(newPassword)
                .getHeader().fillRepeatPasswordField(newPassword)
                .getHeader().clickSaveButton()
                .getHeader().clickLogOutButton()
                .fillEmailField(email)
                .fillPasswordField(newPassword)
                .clickLoginButton();

        Allure.step("Verify: Dashboard Page URL");
        assertThat(dashboardPage.getPage()).hasURL(Constants.DASHBOARD_PAGE_URL);
    }

    @Test
    @TmsLink("300")
    @Epic("Header")
    @Feature("User menu")
    @Description("Log out via button in the user menu")
    public void testLogOutViaButtonInUserMenu() {

        LoginPage loginPage = new DashboardPage(getPage())
                .getHeader().clickUserMenuButton()
                .getHeader().clickLogOutButtonUserMenu();

        Allure.step("Verify: Login Page URL");
        assertThat(loginPage.getPage()).hasURL(Constants.LOGIN_PAGE_URL);
    }

    @Test
    @TmsLink("308")
    @Epic("Header")
    @Feature("Log Out")
    @Description("Log out via button in the Header")
    public void testLogOutViaButtonInHeader() {

        LoginPage loginPage = new DashboardPage(getPage())
                .getHeader().clickLogOutButton();

        Allure.step("Verify: Login Page URL");
        assertThat(loginPage.getPage()).hasURL(Constants.LOGIN_PAGE_URL);
    }

    @Test
    @TmsLink("437")
    @Epic("Header")
    @Feature("User menu")
    @Description("Verify that the user can switch to the dark theme")
    public void testDarkColorThemeSwitch() {
        new DashboardPage(getPage())
                .getHeader().clickUserMenuButton()
                .getHeader().clickDarkRadioButton();

        Allure.step("Verify that the dark color theme is selected");
        assertThat(getPage().locator("html.dark")).isVisible();
    }

    @Test
    @TmsLink("440")
    @Epic("Header")
    @Feature("User menu")
    @Description("Verify that the user can switch to the light theme")
    public void testLightColorThemeSwitch() {
        new DashboardPage(getPage())
                .getHeader().clickUserMenuButton()
                .getHeader().clickLightRadioButton();

        Allure.step("Verify that the light color theme is selected");
        assertThat(getPage().locator("html.light")).isVisible();
    }

    @Test
    @TmsLink("494")
    @Epic("Header")
    @Feature("User menu")
    @Description("Verify that the color theme matching with the default browser theme")
    public void testDefaultThemeMatching() {
        Allure.step("Verify that the current color theme matches the default browser theme");
        assertThat(getPage().locator("html")).hasClass(ProjectProperties.getColorScheme().name().toLowerCase());
    }

    @Test(dataProvider = "passwordValidationData")
    @TmsLink("540")
    @Epic("Header")
    @Feature("User menu")
    @Description("Check validation error messages when changing password")
    public void testCheckChangePasswordValidationMessages(@Optional("UNAUTHORISED") String userRole,
                                                          String newPassword, String expectedMessage) {
        User user = new User(
                "framework",
                true,
                UserRole.ADMIN,
                new String[]{},
                "admintest008@example.com",
                "Qwerty1!"
        );

        TestUtils.deleteUser(getApiRequestContext(), user.email());
        TestUtils.createUser(getApiRequestContext(), user);

        new LoginPage(getPage())
                .loginAndChangePassword(user.email(), user.password(), "Qwerty1!")
                .getHeader().clickUserMenuButton()
                .getHeader().clickProfileSettingsButton()
                .getHeader().fillPasswordField(newPassword)
                .getHeader().fillRepeatPasswordField(newPassword);

        if (expectedMessage == null) {
            Allure.step("Verify Save button is disabled");
            assertThat(getPage().locator("//button[text()='Save']")).isDisabled();
        } else {
            new LoginPage(getPage())
                    .clickSaveButton();
            Allure.step("Verify error message: \"" + expectedMessage + "\" presence");
            assertThat(getPage().getByText(expectedMessage)).isVisible();
        }
    }

    @DataProvider(name = "passwordValidationData")
    public Object[][] passwordValidationData() {
        return new Object[][]{
                {"UNAUTHORISED", "Qwert1!", null},
                {"UNAUTHORISED", "QWERTY1!",
                        "Password does not conform to policy: Password must have lowercase characters"},
                {"UNAUTHORISED", "qwerty1!",
                        "Password does not conform to policy: Password must have uppercase characters"},
                {"UNAUTHORISED", "Qwertyu!",
                        "Password does not conform to policy: Password must have numeric characters"},
                {"UNAUTHORISED", "Qwertyu1",
                        "Password does not conform to policy: Password must have symbol characters"}
        };
    }
}
