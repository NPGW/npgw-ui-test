package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.Address;
import xyz.npgw.test.common.entity.Company;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.dialog.company.AddCompanyDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static xyz.npgw.test.common.Constants.COMPANY_NAME_FOR_TEST_RUN;

public class CompaniesAndBusinessUnitsTest extends BaseTest {

    private static final String COMPANY_NAME_TEST = "%s company name test".formatted(RUN_ID);
    private static final String COMPANY_DELETION_BLOCKED_NAME = "%s deletion-blocked company".formatted(RUN_ID);
    private static final String COMPANY_NAME_REQUIRED_FIELD = "%s company required field".formatted(RUN_ID);
    private static final String COMPANY_TYPE = "CompanyType";

    Company company = new Company(
            COMPANY_NAME_TEST, "Company Type Test",
            new Address("Warwick", "PA",
                    "19876", "US",
                    "+1234567", "+1234567", "+1234567"),
            "Description Test",
            "https://www.test.com", "James Smith", "test@yahoo.com",
            true, true
    );

    Company editedCompany = new Company(
            COMPANY_NAME_TEST, "Edited company type",
            new Address("Delmor", "CA",
                    "19000", "AL",
                    "+2222222", "+2222222", "+2222222"),
            "Edited Description Test",
            "https://www.editedtest.com", "Catty Smith", "editedtest@yahoo.com",
            false, false
    );


    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        TestUtils.createCompany(getApiRequestContext(), COMPANY_DELETION_BLOCKED_NAME);
        TestUtils.createBusinessUnit(getApiRequestContext(), COMPANY_DELETION_BLOCKED_NAME, "Business unit 1");
    }

    @Test
    @TmsLink("691")
    @Epic("System/Companies and business units")
    @Feature("Settings")
    @Description("The company info block can be hidden and shown via settings.")
    public void testToggleCompanyInfoVisibilityViaSettings() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .clickSettings()
                .checkHideCompanyInfo();

        Allure.step("Verify: company info block is hidden after selecting 'Hide' in settings");
        assertThat(companiesAndBusinessUnitsPage.getCompanyInfoBlock()).isHidden();

        companiesAndBusinessUnitsPage
                .checkShowCompanyInfo();

        Allure.step("Verify: company info block is visible again after selecting 'Show' in settings");
        assertThat(companiesAndBusinessUnitsPage.getCompanyInfoBlock()).isVisible();
    }

    @Test
    @TmsLink("246")
    @Epic("System/Companies and business units")
    @Feature("Add company")
    @Description("Validates successful company creation and correct field persistence.")
    public void testAddCompany() {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton();

        Allure.step("Verify: 'Create' button is disabled before filling required fields");
        assertThat(addCompanyDialog.getCreateButton()).isDisabled();

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = addCompanyDialog
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .fillCompanyDescriptionField(company.description())
                .fillCompanyWebsiteField(company.website())
                .fillCompanyPrimaryContactField(company.primaryContact())
                .fillCompanyEmailField(company.email())
                .setApiActiveCheckbox(company.isApiActive())
                .setPortalActiveCheckbox(company.isPortalActive())
                .fillCompanyCountryField(company.companyAddress().country())
                .fillCompanyStateField(company.companyAddress().state())
                .fillCompanyZipField(company.companyAddress().zip())
                .fillCompanyCityField(company.companyAddress().city())
                .fillCompanyPhoneField(company.companyAddress().phone())
                .fillCompanyMobileField(company.companyAddress().mobile())
                .fillCompanyFaxField(company.companyAddress().fax())
                .clickCreateButton()
                .getAlert().waitUntilSuccessAlertIsGone()
                .clickOnResetFilterButton()
                .getSelectCompany().selectCompany(company.companyName());

        Allure.step("Verify: selected company is shown in the input field");
        assertThat(companiesAndBusinessUnitsPage.getSelectCompany().getSelectCompanyField())
                .hasValue(company.companyName());

        Allure.step("Verify: name field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getName()).hasValue(company.companyName());

        Allure.step("Verify: type field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getType()).hasValue(company.companyType());

        Allure.step("Verify: description field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getDescription()).hasValue(company.description());

        Allure.step("Verify: website field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getWebsite()).hasValue(company.website());

        Allure.step("Verify: primary contact field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getPrimaryContact()).hasValue(company.primaryContact());

        Allure.step("Verify: email field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getEmail()).hasValue(company.email());

        Allure.step("Verify: 'API active' checkbox is checked");
        assertThat(companiesAndBusinessUnitsPage.getApiActive()).isChecked();

        Allure.step("Verify: 'Portal active' checkbox is checked");
        assertThat(companiesAndBusinessUnitsPage.getPortalActive()).isChecked();

        Allure.step("Verify: phone field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getPhone()).hasValue(company.companyAddress().phone());

        Allure.step("Verify: mobile field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getMobile()).hasValue(company.companyAddress().mobile());

        Allure.step("Verify: fax field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getFax()).hasValue(company.companyAddress().fax());

        Allure.step("Verify: country field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getCountry()).hasValue(company.companyAddress().country());

        Allure.step("Verify: state field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getState()).hasValue(company.companyAddress().state());

        Allure.step("Verify: ZIP code field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getZip()).hasValue(company.companyAddress().zip());

        Allure.step("Verify: city field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getCity()).hasValue(company.companyAddress().city());
    }

    @Test(dependsOnMethods = "testAddCompany")
    @TmsLink("266")
    @Epic("System/Companies and business units")
    @Feature("Edit company")
    @Description("Edit company info and save")
    public void testEditCompanyInfoAndSave() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(company.companyName())
                .clickEditCompanyButton()
                .fillCompanyTypeField(editedCompany.companyType())
                .fillCompanyDescriptionField(editedCompany.description())
                .fillCompanyWebsiteField(editedCompany.website())
                .fillCompanyPrimaryContactField(editedCompany.primaryContact())
                .fillCompanyEmailField(editedCompany.email())
                .setApiActiveCheckbox(editedCompany.isApiActive())
                .setPortalActiveCheckbox(editedCompany.isPortalActive())
                .fillCompanyCountryField(editedCompany.companyAddress().country())
                .fillCompanyStateField(editedCompany.companyAddress().state())
                .fillCompanyZipField(editedCompany.companyAddress().zip())
                .fillCompanyCityField(editedCompany.companyAddress().city())
                .fillCompanyPhoneField(editedCompany.companyAddress().phone())
                .fillCompanyMobileField(editedCompany.companyAddress().mobile())
                .fillCompanyFaxField(editedCompany.companyAddress().fax())
                .clickSaveChangesButton();

        Allure.step("Verify: success message is displayed");
        assertThat(companiesAndBusinessUnitsPage.getAlert().getMessage())
                .hasText("SUCCESSCompany was updated successfully");

        companiesAndBusinessUnitsPage
                .getAlert().waitUntilSuccessAlertIsGone();

        Allure.step("Verify: name field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getName()).hasValue(editedCompany.companyName());

        Allure.step("Verify: type field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getType()).hasValue(editedCompany.companyType());

        Allure.step("Verify: description field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getDescription()).hasValue(editedCompany.description());

        Allure.step("Verify: website field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getWebsite()).hasValue(editedCompany.website());

        Allure.step("Verify: primary contact field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getPrimaryContact()).hasValue(editedCompany.primaryContact());

        Allure.step("Verify: email field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getEmail()).hasValue(editedCompany.email());

        Allure.step("Verify: 'API active' checkbox is checked");
        assertThat(companiesAndBusinessUnitsPage.getApiActive()).not().isChecked();

        Allure.step("Verify: 'Portal active' checkbox is checked");
        assertThat(companiesAndBusinessUnitsPage.getPortalActive()).not().isChecked();

        Allure.step("Verify: phone field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getPhone()).hasValue(editedCompany.companyAddress().phone());

        Allure.step("Verify: mobile field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getMobile()).hasValue(editedCompany.companyAddress().mobile());

        Allure.step("Verify: fax field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getFax()).hasValue(editedCompany.companyAddress().fax());

        Allure.step("Verify: country field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getCountry()).hasValue(editedCompany.companyAddress().country());

        Allure.step("Verify: state field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getState()).hasValue(editedCompany.companyAddress().state());

        Allure.step("Verify: ZIP code field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getZip()).hasValue(editedCompany.companyAddress().zip());

        Allure.step("Verify: city field is correctly filled");
        assertThat(companiesAndBusinessUnitsPage.getCity()).hasValue(editedCompany.companyAddress().city());
    }

    @Test(dependsOnMethods = "testEditCompanyInfoAndSave")
    @TmsLink("723")
    @Epic("System/Companies and business units")
    @Feature("Delete Company")
    @Description("Verify that company can be deleted")
    public void testDeleteCompany() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(company.companyName())
                .clickDeleteSelectedCompany()
                .clickDeleteButton();

        Allure.step("Verify: the success alert appears after deleting the company");
        assertThat(companiesAndBusinessUnitsPage.getAlert().getMessage())
                .hasText("SUCCESSCompany was deleted successfully");

        companiesAndBusinessUnitsPage
                .getAlert().clickCloseButton()
                .waitForCompanyAbsence(getApiRequestContext(), company.companyName());

        Allure.step("Verify: the deleted company is no longer present on the page");
        assertThat(companiesAndBusinessUnitsPage.getPageContent())
                .hasText("Select company name to view merchants");

        getPage().waitForTimeout(2000);

        Allure.step("Verify: the deleted company is no longer present in the dropdown list");
        assertFalse(companiesAndBusinessUnitsPage.getSelectCompany().isCompanyPresent(company.companyName()));
    }

    @Test
    @TmsLink("232")
    @Epic("System/Companies and business units")
    @Feature("Add company")
    @Description("Error is displayed when trying to create a company with an already existing name")
    public void testAddCompanyWithSameName() {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(COMPANY_NAME_FOR_TEST_RUN)
                .fillCompanyTypeField(COMPANY_TYPE)
                .clickCreateButtonAndTriggerError();

        Allure.step("Verify: error message is displayed for duplicate company name");
        assertThat(addCompanyDialog.getAlert().getMessage())
                .containsText("Company with name {%s} already exists.".formatted(COMPANY_NAME_FOR_TEST_RUN));
    }

    @Test
    @TmsLink("728")
    @Epic("System/Companies and business units")
    @Feature("Delete Company")
    @Description("Verify that company cannot be deleted if there are associated business units")
    public void testCannotDeleteCompanyWithAssociatedBusinessUnit() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(COMPANY_DELETION_BLOCKED_NAME)
                .clickDeleteSelectedCompany()
                .clickDeleteButton();

        Allure.step("Verify: error message is shown when trying to delete a company with business unit");
        assertThat(companiesAndBusinessUnitsPage.getAlert().getMessage())
                .hasText("ERRORCompany could not be deleted: there are still merchants associated with it");

    }

    @Test(dependsOnMethods = "testCannotDeleteCompanyWithAssociatedBusinessUnit")
    @TmsLink("743")
    @Epic("System/Companies and business units")
    @Feature("Delete Company")
    @Description("Verify that company cannot be deleted if there are users assigned to it")
    public void testCannotDeleteCompanyWithAssignedUser() {
        String email = "%s.admin123@email.com".formatted(TestUtils.now());

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(COMPANY_DELETION_BLOCKED_NAME)
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Qwerty123!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton()
                .getAlert().waitUntilSuccessAlertIsGone()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(COMPANY_DELETION_BLOCKED_NAME)
                .clickDeleteSelectedCompany()
                .clickDeleteButton();

        Allure.step("Verify: error message is shown when trying to delete a company with users");
        assertThat(companiesAndBusinessUnitsPage.getAlert().getMessage())
                .hasText("ERRORCompany could not be deleted: there are still users associated with it");
    }

    @Test
    @TmsLink("480")
    @Epic("Companies and business units")
    @Feature("Reset filter")
    @Description("Verify default filter state was applied once reset")
    public void testResetAppliedFilter() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .clickOnResetFilterButton();

        Allure.step("Verify: Ensure the prompt appears when no company is selected");
        assertThat(companiesAndBusinessUnitsPage.getPageContent())
                .hasText("Select company name to view merchants");

        Allure.step("Verify: the 'Company' input field is empty after reset");
        assertThat(companiesAndBusinessUnitsPage.getSelectCompany().getSelectCompanyField()).isEmpty();
    }

    @Test(dataProvider = "getInvalidCompanyNameLengths", dataProviderClass = TestDataProvider.class)
    @TmsLink("191")
    @Epic("System/Companies and business units")
    @Feature("Add company")
    @Description("Error message is shown for company name is shorter than 4 or longer than 100 characters.")
    public void testVerifyErrorMessageForInvalidCompanyNameLength(String name) {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(name)
                .fillCompanyTypeField(COMPANY_TYPE)
                .clickCreateButtonAndTriggerError();

        Allure.step("Verify: error message for invalid company name is displayed");
        assertThat(addCompanyDialog.getAlert().getMessage()).containsText(
                "Invalid companyName: '%s'. It must contain between 4 and 100 characters".formatted(name));
    }

    @Test
    @TmsLink("206")
    @Epic("System/Companies and business units")
    @Feature("Add company")
    @Description("Verify required field states, placeholders, and button state in empty 'Add Company' form.")
    public void testAddCompanyFormValidationWhenEmpty() {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton();

        Allure.step("Verify: 'Add company' dialog is displayed");
        assertThat(addCompanyDialog.getDialogHeader()).hasText("Add company");

        Allure.step("Verify: 'Company name' field is marked invalid");
        assertThat(addCompanyDialog.getCompanyNameField()).hasAttribute("aria-invalid", "true");

        Allure.step("Verify: all placeholders are correct for each field");
        assertEquals(addCompanyDialog.getAllPlaceholders(), List.of(
                "Enter name",
                "Enter type",
                "Enter company description",
                "Enter website",
                "Enter primary contact",
                "Enter email",
                "Enter country",
                "Enter state",
                "Enter ZIP",
                "Enter city",
                "Enter phone",
                "Enter mobile",
                "Enter fax"
        ));

        Allure.step("Verify: 'Create' button is disabled when required fields are not filled.");
        assertThat(addCompanyDialog.getCreateButton()).isDisabled();

        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = addCompanyDialog
                .clickCloseButton();

        Allure.step("Verify: the 'Add Company' dialog is no longer visible");
        assertThat(companiesAndBusinessUnitsPage.getAddCompanyDialog()).isHidden();
    }

    @Test(dataProvider = "getCompanyNameInvalidSpecialCharacters", dataProviderClass = TestDataProvider.class)
    @TmsLink("215")
    @Epic("System/Companies and business units")
    @Feature("Add company")
    @Description("Error is displayed when trying to create a company with special characters in the name.")
    public void testErrorIsDisplayedWhenCreatingCompanyWithSpecialCharacters(String character) {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField("Company" + character)
                .fillCompanyTypeField(COMPANY_TYPE);

        Allure.step("Verify: 'Create' button is disabled");
        assertThat(addCompanyDialog.getCreateButton()).isDisabled();

        Allure.step("Verify: 'Company name' field is marked invalid");
        assertThat(addCompanyDialog.getCompanyNameField()).hasAttribute("aria-invalid", "true");
    }

    @Test(dataProvider = "getInvalidCompanyNamesByLengthAndChar", dataProviderClass = TestDataProvider.class)
    @TmsLink("261")
    @Epic("System/Companies and business units")
    @Feature("Add company")
    @Description("Error message when trying to create a company with invalid length and special characters.")
    public void testErrorForInvalidCompanyNameLengthAndCharacters(String name, String character) {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(name + character)
                .fillCompanyTypeField(COMPANY_TYPE);

        Allure.step("Verify: 'Create' button is disabled");
        assertThat(addCompanyDialog.getCreateButton()).isDisabled();

        Allure.step("Verify: 'Company name' field is marked invalid");
        assertThat(addCompanyDialog.getCompanyNameField()).hasAttribute("aria-invalid", "true");
    }

    @Test
    @TmsLink("223")
    @Epic("System/Companies and business units")
    @Feature("Add company")
    @Description("Company can be added by filling out required fields")
    public void testAddCompanyByFillRequiredFields() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(COMPANY_NAME_REQUIRED_FIELD)
                .fillCompanyTypeField(COMPANY_TYPE)
                .clickCreateButton();

        Allure.step("Verify: company creation success message is displayed");
        assertThat(companiesAndBusinessUnitsPage.getAlert().getMessage())
                .hasText("SUCCESSCompany was created successfully");
    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteCompany(getApiRequestContext(), COMPANY_DELETION_BLOCKED_NAME);
        super.afterClass();
    }
}
