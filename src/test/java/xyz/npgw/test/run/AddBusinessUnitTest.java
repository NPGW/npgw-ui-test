package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import net.datafaker.Faker;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.util.Company;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.dialog.AddBusinessUnitDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddBusinessUnitTest extends BaseTest {

    @Test
    @TmsLink("213")
    @Epic("System/Companies and business units")
    @Feature("Add merchant")
    @Description("Verify 'Add business unit' button activation once some company is selected")
    public void testVerifyAvailabilityOfBusinessUnitButton() {
        Company company = new Company(new Faker());

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .selectCompanyInTheFilter(company.companyName());

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
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton();

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

        AddBusinessUnitDialog addBusinessUnitDialog = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .selectCompanyInTheFilter(company.companyName())
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

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .selectCompanyInTheFilter(company.companyName())
                .clickOnAddBusinessUnitButton()
                .clickOnCloseButton();

        Allure.step("The table is empty and 'No rows to display.' is displayed");
        assertThat(companiesAndBusinessUnitsPage.getBusinessUnitEmptyList()).hasText("No rows to display.");
    }

    @Ignore
    @Test(dataProvider = "merchantFormData", dataProviderClass = TestDataProvider.class)
    @TmsLink("218")
    @Epic("Companies and business units")
    @Feature("Add merchant")
    @Description("Add a new Merchant with 'Add business unit' button")
    public void testAddNewMerchants(String status, List<String> currencies) {
        Company company = new Company(new Faker());

        CompaniesAndBusinessUnitsPage companiesPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .waitUntilAlertIsGone()
                .selectCompanyInTheFilter(company.companyName());

        AddBusinessUnitDialog dialog = companiesPage.clickOnAddBusinessUnitButton()
                .setBusinessUnitName(company.companyType());

        if (status.equalsIgnoreCase("Inactive")) {
            dialog.selectInactiveState();
        } else {
            dialog.selectActiveState();
        }

        if (currencies.contains("USD")) {
            dialog.checkUsdCurrency();
        }
        if (currencies.contains("EUR")) {
            dialog.checkEurCurrency();
        }

        dialog.clickOnCreateButton().waitUntilAlertIsGone();

        Locator row = getPage().locator("td").locator("xpath=..");
        assertThat(row).containsText(company.companyType());
        assertThat(row).containsText(status);
        assertThat(row).containsText("id.merchant.");

        for (String currency : currencies) {
            assertThat(row).containsText(currency);
        }
    }
}
