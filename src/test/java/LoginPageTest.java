import org.testng.annotations.Test;
import page.DashboardPage;
import page.LoginPage;
import runner.BaseTest;
import testdata.Constants;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginPageTest extends BaseTest {

    @Test
    public void testNavigateToLoginPage() {

        LoginPage loginPage = new LoginPage(getPage());

        assertThat(loginPage.getPage()).hasURL(Constants.LOGIN_PAGE_URL);
        assertThat(loginPage.getPage()).hasTitle(Constants.BASE_URL_TITLE);
    }

    @Test
    public void testLogin() {
        DashboardPage dashboardPage = new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .clickLoginButton();

        assertThat(dashboardPage.getPage()).hasURL(Constants.DASHBOARD_PAGE_URL);
        assertThat(dashboardPage.getPage()).hasTitle(Constants.DASHBOARD_URL_TITLE);
    }

    @Test
    public void testRememberMeCheckedSavesUserEmail() {
        LoginPage loginPage = new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .checkRememberMeCheckbox()
                .clickLoginButton()
                .clickLogOutButton();

        assertThat(loginPage.getEmailField()).hasValue(Constants.USER_EMAIL);
    }
}
