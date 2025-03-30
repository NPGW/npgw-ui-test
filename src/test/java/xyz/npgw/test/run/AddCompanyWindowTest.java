package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.AddCompanyWindow;
import xyz.npgw.test.page.DashboardPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddCompanyWindowTest extends BaseTest {

    @Test
    @TmsLink("160")
    @Epic("Companies and business units")
    @Feature("Title Verification")
    @Description("Verify that the 'Add Company' window displays the correct title in the header.")
    public void testVerifyAddCompanyWindowTitle() {
        AddCompanyWindow addCompanyPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnits()
                .clickAddCompany();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(addCompanyPage.getHeader()).isVisible();
    }
}
