package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.AboutBlankPage;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.common.HeaderComponent;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HeaderComponentTest extends BaseTest {

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
    }

    @Test
    @TmsLink("211")
    @Epic("Header")
    @Feature("Transactions menu item")
    @Description("Check after clicking on Transactions user redirected to Transactions page")
    public void testTransactionsLink() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .getHeader()
                .clickTransactionsLink();

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
                .getHeader()
                .clickTransactionsLink()
                .getHeader()
                .clickLogoButton();

        Allure.step("Verify: Dashboard Page URL");
        assertThat(dashboardPage.getPage()).hasURL(Constants.DASHBOARD_PAGE_URL);
    }

    @Test()
    @TmsLink("289")
    @Epic("Header")
    @Feature("User menu")
    @Description("Check if the user can change the password through the profile settings in the user menu")
    public void testChangePassword(@Optional("UNAUTHORISED") String userRole) {

        String newPassword = "QWEdsa123@";

        DashboardPage dashboardPage = new AboutBlankPage(getPage())
                .navigate("/login")
                .fillEmailField(ProjectProperties.getUserEmail())
                .fillPasswordField(ProjectProperties.getUserPassword())
                .clickLoginButton()
                .getHeader().clickUserMenuButton()
                .getHeader().clickProfileSettingsButton()
                .getHeader().fillPasswordField(newPassword)
                .getHeader().fillRepeatPasswordField(newPassword)
                .getHeader().clickSaveButton()
                .getHeader().clickLogOutButton()
                .fillEmailField(ProjectProperties.getUserEmail())
                .fillPasswordField(newPassword)
                .clickLoginButton()
                .getHeader().clickUserMenuButton()
                .getHeader().clickProfileSettingsButton()
                .getHeader().fillPasswordField(ProjectProperties.getUserPassword())
                .getHeader().fillRepeatPasswordField(ProjectProperties.getUserPassword())
                .getHeader().clickSaveButton()
                .getHeader().clickLogOutButton()
                .fillEmailField(ProjectProperties.getUserEmail())
                .fillPasswordField(ProjectProperties.getUserPassword())
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
                .getHeader()
                .clickUserMenuButton()
                .getHeader()
                .clickLogOutButtonUserMenu();

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
                .getHeader()
                .clickLogOutButton();

        Allure.step("Verify: Login Page URL");
        assertThat(loginPage.getPage()).hasURL(Constants.LOGIN_PAGE_URL);
    }
}
