package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertTrue;

public class CompaniesAndBusinessUnitsTest extends BaseTest {

    @Test
    @TmsLink("691")
    @Epic("System/Companies and business units")
    @Feature("Settings")
    @Description("The company info block can be hidden and shown via settings.")
    public void testToggleCompanyInfoVisibilityViaSettings() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany("super")
                .clickSettings()
                .checkHideCompanyInfo();

        Allure.step("Verify: company info block is hidden after selecting 'Hide' in settings");
        assertThat(companiesAndBusinessUnitsPage.getCompanyInfoBlock()).isHidden();

        companiesAndBusinessUnitsPage
                .checkShowCompanyInfo();

        Allure.step("Verify: company info block is visible again after selecting 'Show' in settings");
        assertThat(companiesAndBusinessUnitsPage.getCompanyInfoBlock()).isVisible();
    }


    @Test(priority = 1)
    @TmsLink("723")
    @Epic("System/Companies and business units")
    @Feature("Delete Company")
    @Description("Verify that company can be deleted")
    public void testDeleteCompany() {
        String companyName = getCompanyName();

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .clickDeleteSelectedCompany()
                .clickDeleteButton();

        Allure.step("Verify: the success alert appears after deleting the company");
        assertThat(companiesAndBusinessUnitsPage.getAlert().getMessage())
                .hasText("SUCCESSCompany was deleted successfully");

        Allure.step("Verify: the deleted company is no longer present in the dropdown list");
        assertTrue(companiesAndBusinessUnitsPage.getSelectCompany().isCompanyAbsentInDropdown(companyName));
    }
}
