package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import net.datafaker.Faker;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.Company;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.dialog.merchant.AddBusinessUnitDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddBusinessUnitTest extends BaseTest {

    @Test
    @TmsLink("213")
    @Epic("System/Companies and business units")
    @Feature("Add merchant")
    @Description("Verify 'Add business unit' button activation once some company is selected")
    public void testVerifyAvailabilityOfBusinessUnitButton() {
        Company company = new Company(new Faker());
        TestUtils.deleteCompany(getApiRequestContext(), company.companyName());

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .getSelectCompany().selectCompany(company.companyName());

        Allure.step("'Add business unit' button is available");
        assertThat(companiesAndBusinessUnitsPage.getAddBusinessUnitButton()).isEnabled();
        Allure.step("'Edit selected company' button is available");
        assertThat(companiesAndBusinessUnitsPage.getEditCompanyButton()).isEnabled();
    }

    @Test
    @TmsLink("214")
    @Epic("System/Companies and business units")
    @Feature("Add merchant")
    @Description("Verify 'Add business unit' button is disabled if 'Select company' filter's field is cleaned")
    public void testVerifyAddBusinessUnitButtonDefaultState() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab();

        Allure.step("'Add business unit' button is disabled once no destination company is selected");
        assertThat(companiesAndBusinessUnitsPage.getAddBusinessUnitButton()).isDisabled();
    }

    @Test
    @TmsLink("238")
    @Epic("System/Companies and business units")
    @Feature("Add merchant")
    @Description("Verify that 'Company name' field is prefilled and impossible to change")
    public void testCompanyNameFieldDefaultState() {
        Company company = new Company(new Faker());
        TestUtils.deleteCompany(getApiRequestContext(), company.companyName());

        AddBusinessUnitDialog addBusinessUnitDialog = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .getSelectCompany().selectCompany(company.companyName())
                .clickOnAddBusinessUnitButton();

        Allure.step("Verify that Company name field is read-only and prefilled created company");
        assertThat(addBusinessUnitDialog.getCompanyNameField()).hasValue(company.companyName());
        assertThat(addBusinessUnitDialog.getCompanyNameField()).hasAttribute("aria-readonly", "true");
    }

    @Test
    @TmsLink("241")
    @Epic("System/Companies and business units")
    @Feature("Add merchant")
    @Description("Verify that a new Merchant wasn't added once click 'Close' button")
    public void testCloseButtonAndDiscardChanges() {
        Company company = new Company(new Faker());
        TestUtils.deleteCompany(getApiRequestContext(), company.companyName());

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .getSelectCompany().selectCompany(company.companyName())
                .clickOnAddBusinessUnitButton()
                .clickCloseButton();

        Allure.step("The table is empty and 'No rows to display.' is displayed");
        assertThat(companiesAndBusinessUnitsPage.getMerchantsTable()).containsText("No rows to display.");
    }

    @Test
    @TmsLink("218")
    @Epic("Companies and business units")
    @Feature("Add merchant")
    @Description("Add a new Merchant with 'Add business unit' button")
    public void testAddNewMerchants() {
        Company company = new Company(new Faker());
        Locator createdBusinessUnitRow = getPage().locator("td").locator("xpath=..");

        new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .getSelectCompany().selectCompany(company.companyName())
                .clickOnAddBusinessUnitButton()
                .fillBusinessUnitNameField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone();

        assertThat(createdBusinessUnitRow).containsText(company.companyType());
        assertThat(createdBusinessUnitRow).containsText("id.merchant.");
    }

    @Test
    @TmsLink("479")
    @Epic("Companies and business units")
    @Feature("Add merchant")
    @Description("Verify default filter state was applied once reset")
    public void testResetAppliedFilter() {
        Company company = new Company(new Faker());
        TestUtils.deleteCompany(getApiRequestContext(), company.companyName());

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .getSelectCompany().selectCompany(company.companyName())
                .clickOnResetFilterButton();

        assertThat(companiesAndBusinessUnitsPage.getSelectCompanyWarning())
                .containsText("Select company name to view merchants");
    }
}
