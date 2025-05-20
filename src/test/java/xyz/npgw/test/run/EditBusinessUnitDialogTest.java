package xyz.npgw.test.run;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Ignore;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.UserRole;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.AboutBlankPage;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.dialog.merchant.EditBusinessUnitDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class EditBusinessUnitDialogTest extends BaseTest {
    private final String companyName = "CompanyForBuEdit";
    private final String buName = "NewBUForEdit";

    @Ignore
    @Test
    @TmsLink("387")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that the title of the 'Edit Business Unit' dialog matches the expected result")
    public void testVerifyTitleEditBusinessUnitDialog(@Optional("UNAUTHORISED") String userRole) {
        TestUtils.createCompany(getApiRequestContext(), companyName);
        TestUtils.createBusinessUnit(getApiRequestContext(), companyName, buName);

        Locator dialogTitle = new AboutBlankPage(getPage())
                .navigate("/login")
                .loginAs(UserRole.SUPER)
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .getTable().clickEditBusinessUnitButton(buName)
                .getDialogHeader();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(dialogTitle).hasText("Edit business unit");

        TestUtils.deleteCompany(getApiRequestContext(), companyName);
    }

    @Ignore
    @Test
    @TmsLink("501")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that the label of each field is correct")
    public void testVerifyLabelOfEachField(@Optional("UNAUTHORISED") String userRole) {
        TestUtils.createCompany(getApiRequestContext(), companyName);
        TestUtils.createBusinessUnit(getApiRequestContext(), companyName, buName);

        EditBusinessUnitDialog editBusinessUnitDialog = new AboutBlankPage(getPage())
                .navigate("/login")
                .loginAs(UserRole.SUPER)
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .getTable().clickEditBusinessUnitButton(buName);

        Allure.step("Verify: all labels are correct for each field");
        assertThat(editBusinessUnitDialog.getFieldLabel()).hasText(new String[]{"Company name", "Business unit name"});

        TestUtils.deleteCompany(getApiRequestContext(), companyName);
    }

    @Ignore
    @Test
    @TmsLink("515")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that company name is pre-filled correctly and read-only")
    public void testVerifyFieldCompanyNamePreFilledAndReadOnly(@Optional("UNAUTHORISED") String userRole) {
        TestUtils.createCompany(getApiRequestContext(), companyName);
        TestUtils.createBusinessUnit(getApiRequestContext(), companyName, buName);

        EditBusinessUnitDialog editBusinessUnitDialog = new AboutBlankPage(getPage())
                .navigate("/login")
                .loginAs(UserRole.SUPER)
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .getTable().clickEditBusinessUnitButton(buName);

        Allure.step("Verify: Company name is pre-filled correctly");
        assertThat(editBusinessUnitDialog.getCompanyNameField()).hasValue(companyName);
        Allure.step("Verify: Company name field is read-only");
        assertThat(editBusinessUnitDialog.getCompanyNameField()).hasAttribute("aria-readonly", "true");

        TestUtils.deleteCompany(getApiRequestContext(), companyName);
    }

    @Test(invocationCount = 20)
    @TmsLink("528")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that the dialog is closed by clicking on the 'Close' button")
    public void testVerifyDialogClosedByClickClose(){
        TestUtils.createCompany(getApiRequestContext(), companyName);
        TestUtils.createMerchantTitleIfNeeded(getApiRequestContext(), companyName, buName);

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(companyName)
                .getTable().clickEditBusinessUnitButton(buName)
                .clickCloseButton();

        Allure.step("Verify: Dialog 'Edit business unit' is not displayed after clicking on the 'Close' button");
        assertThat(companiesAndBusinessUnitsPage.getEditBusinessUnitDialog()).isHidden();

        companiesAndBusinessUnitsPage
                .getTable().clickEditBusinessUnitButton(buName)
                .clickCloseIcon();

        Allure.step("Verify: Dialog 'Edit business unit' is not displayed after clicking on the 'Close' icon");
        assertThat(companiesAndBusinessUnitsPage.getEditBusinessUnitDialog()).isHidden();

        TestUtils.deleteAllByMerchantTitle(getApiRequestContext(), companyName, buName);
        TestUtils.deleteCompany(getApiRequestContext(), companyName);
    }
}
