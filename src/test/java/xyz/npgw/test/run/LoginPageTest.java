package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.SaAcquirersTab;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPageTest extends BaseTest {

    @Test
    public void testNavigateToLoginPage() {

        LoginPage loginPage = new LoginPage(getPage());

        Allure.step("Verify: Login Page URL");
        assertThat(loginPage.getPage()).hasURL(Constants.LOGIN_PAGE_URL);

        Allure.step("Verify: Login Page Title");
        assertThat(loginPage.getPage()).hasTitle(Constants.BASE_URL_TITLE);
    }

    @Test
    public void testLogin() {
        DashboardPage dashboardPage = new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .clickLoginButton();

        Allure.step("Verify: Dashboard Page URL");
        assertThat(dashboardPage.getPage()).hasURL(Constants.DASHBOARD_PAGE_URL);

        Allure.step("Verify: Dashboard Page Title");
        assertThat(dashboardPage.getPage()).hasTitle(Constants.DASHBOARD_URL_TITLE);
    }

    @Test
    @TmsLink("81")
    @Epic("Login")
    @Feature("Remember me")
    @Description("User email is remembered after first successful login with checked 'Remember me'")
    public void testRememberMeCheckedSavesUserEmail() {
        LoginPage loginPage = new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .clickRememberMeCheckbox(true)
                .clickLoginButton()
                .getHeader()
                .clickLogOutButton();

        Allure.step("The user's email is in the email field");
        assertThat(loginPage.getEmailField()).hasValue(Constants.USER_EMAIL);
    }

    @Test
    @TmsLink("82")
    @Epic("Login")
    @Feature("Remember me")
    @Description("User email is NOT remembered after first successful login with unchecked 'Remember me'")
    public void testRememberMeUncheckedDontSaveUserEmail() {
        LoginPage loginPage = new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .clickRememberMeCheckbox(false)
                .clickLoginButton()
                .getHeader()
                .clickLogOutButton();

        Allure.step("The user's email is not in the email field");
        assertThat(loginPage.getEmailField()).hasValue("");
    }

    public static class SaAcquirersTabTest extends BaseTest {

        @Test
        @TmsLink("134")
        @Epic("SA/Acquirers")
        @Feature("Acquirers list")
        @Description("Verify: The visibility of elements in the 'Acquirers List' control panel")
        public void testVisibilityAcquirersListControlTab() {
            SaAcquirersTab saAcquirersTab = new DashboardPage(getPage())
                    .getHeader()
                    .clickSystemAdministrationLink()
                    .clickAcquirersButton();

            Allure.step("Verify: Add Acquirer Img is visible");
            assertThat(saAcquirersTab.getAddAcquirerImg()).isVisible();

            Allure.step("Verify: Select Acquirer placeholder is visible");
            assertThat(saAcquirersTab.getSelectAcquirerPlaceholder()).isVisible();

            Allure.step("Verify: Status placeholder is visible");
            assertThat(saAcquirersTab.getStatusPlaceholder()).isVisible();

            Allure.step("Verify: Reset Filter Img is visible");
            assertThat(saAcquirersTab.getResetFilterImg()).isVisible();

            Allure.step("Verify: Apply Filter Img is visible");
            assertThat(saAcquirersTab.getApplyFilterImg()).isVisible();
        }
    }
}
