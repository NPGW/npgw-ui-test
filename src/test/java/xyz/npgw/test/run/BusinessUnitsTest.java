package xyz.npgw.test.run;

import org.testng.Assert;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.SystemAdministrationPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BusinessUnitsTest extends BaseTest {

    @Test
    public void testNavigateToCompaniesAndBusinessUnitsTab() {
        new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .clickLoginButton();

        SystemAdministrationPage sysPage = new SystemAdministrationPage(getPage())
                .goToMerchantsTab();

        assertThat(sysPage.getBusinessUnitName()).hasValue("Business unit name");
    }
}
