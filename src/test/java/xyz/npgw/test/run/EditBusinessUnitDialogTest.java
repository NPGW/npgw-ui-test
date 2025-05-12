package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import xyz.npgw.test.common.UserRole;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.AboutBlankPage;
import xyz.npgw.test.page.dialog.merchant.EditBusinessUnitDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EditBusinessUnitDialogTest extends BaseTest {
    private final String companyName = "CompanyForBuEdit";
    private final String buName = "NewBUForEdit";

    @Test
    @TmsLink("387")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that the title of the 'Edit Business Unit' dialog matches the expected result")
    public void testVerifyTitleEditBusinessUnitDialog(@Optional("UNAUTHORISED") String userRole) {
        TestUtils.createCompanyIfNeeded(getApiRequestContext(), companyName);
        TestUtils.createMerchantIfNeeded(getApiRequestContext(), companyName, buName);

        Locator dialogTitle = new AboutBlankPage(getPage())
                .navigate("/")
                .loginAs(UserRole.SUPER)
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .clickEditBusinessUnitButton()
                .getDialogHeader();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(dialogTitle).hasText("Edit business unit");
    }

    @Test
    @TmsLink("501")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that the label of each field is correct")
    public void testVerifyLabelOfEachField(@Optional("UNAUTHORISED") String userRole) {
        TestUtils.createCompanyIfNeeded(getApiRequestContext(), companyName);
        TestUtils.createMerchantIfNeeded(getApiRequestContext(), companyName, buName);

        EditBusinessUnitDialog editBusinessUnitDialog = new AboutBlankPage(getPage())
                .navigate("/")
                .loginAs(UserRole.SUPER)
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .clickEditBusinessUnitButton();

        Allure.step("Verify: all labels are correct for each field");
        assertThat(editBusinessUnitDialog.getFieldLabel()).hasText(new String[]{"Company name", "Business unit name"});
    }

    @Test
    @TmsLink("515")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that company name is pre-filled correctly and read-only")
    public void testVerifyFieldCompanyNamePreFilledAndReadOnly(@Optional("UNAUTHORISED") String userRole) {
        TestUtils.createCompanyIfNeeded(getApiRequestContext(), companyName);
        TestUtils.createMerchantIfNeeded(getApiRequestContext(), companyName, buName);

        EditBusinessUnitDialog editBusinessUnitDialog = new AboutBlankPage(getPage())
                .navigate("/")
                .loginAs(UserRole.SUPER)
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .clickEditBusinessUnitButton();

        Allure.step("Verify: Company name is pre-filled correctly");
        assertThat(editBusinessUnitDialog.getCompanyNameField()).hasValue(companyName);
        Allure.step("Verify: Company name field is read-only");
        assertThat(editBusinessUnitDialog.getCompanyNameField()).hasAttribute("aria-readonly", "true");
    }

    @Test
    @TmsLink("528")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that the dialog is closed by clicking on the 'Close' button")
    public void testVerifyDialogClosedByClickButtonClose(@Optional("UNAUTHORISED") String userRole) {
        TestUtils.createCompanyIfNeeded(getApiRequestContext(), companyName);
        TestUtils.createMerchantIfNeeded(getApiRequestContext(), companyName, buName);

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new AboutBlankPage(getPage())
                .navigate("/")
                .loginAs(UserRole.SUPER)
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .clickEditBusinessUnitButton()
                .clickCloseButton();

        assertThat(companiesAndBusinessUnitsPage.getEditBusinessUnitDialog()).isHidden();
    }
}
