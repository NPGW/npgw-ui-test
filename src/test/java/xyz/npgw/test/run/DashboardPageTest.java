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

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DashboardPageTest extends BaseTest {

    @Test
    @TmsLink("151")
    @Epic("Dashboard")
    @Feature("Navigation")
    @Description("User navigate to 'Dashboard page' after login")
    public void testNavigateToDashboardAfterLogin() {
        new DashboardPage(getPage());

        Allure.step("Verify: Dashboard Page URL");
        assertThat(getPage()).hasURL(Constants.DASHBOARD_PAGE_URL);

        Allure.step("Verify: Dashboard Page Title");
        assertThat(getPage()).hasTitle(Constants.DASHBOARD_URL_TITLE);
    }

    @Test
    @TmsLink("150")
    @Epic("Dashboard")
    @Feature("Navigation")
    @Description("User navigate to 'Login page' after logout")
    public void testNavigateToLoginPageAfterLogout() {
        new DashboardPage(getPage())
                .clickLogOutButton();

        Allure.step("Verify: Login Page URL");
        assertThat(getPage()).hasURL(Constants.LOGIN_PAGE_URL);

        Allure.step("Verify: Login Page Title");
        assertThat(getPage()).hasTitle(Constants.LOGIN_URL_TITLE);
    }
}
