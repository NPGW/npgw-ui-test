package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.AddCompanyDialog;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.systemadministration.CompaniesAndBusinessUnitsPage;
import xyz.npgw.test.testdata.TestDataProvider;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

public class AddCompanyDialogTest extends BaseTest {

    @Test
    @TmsLink("160")
    @Epic("Companies and business units")
    @Feature("Title Verification")
    @Description("Verify that the 'Add Company' window displays the correct title in the header.")
    public void testVerifyAddCompanyWindowTitle() {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(addCompanyDialog.getAddCompanyDialogHeader()).hasText("Add company");
    }

    @Test
    @TmsLink("189")
    @Epic("Companies and business units")
    @Feature("Placeholders Verification")
    @Description("Verify that the placeholder text for each field is correct.")
    public void testVerifyPlaceholders() {
        List<String> expectedPlaceholders = List.of(
                "Enter company name",
                "Enter type",
                "Enter company description",
                "Enter company website",
                "Enter company primary contact",
                "Enter company email",
                "Enter country",
                "Enter state",
                "Enter ZIP",
                "Enter city",
                "Enter phone",
                "Enter mobile",
                "Enter fax"
        );

        AddCompanyDialog addCompanyPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton();

        Allure.step("Verify: all placeholders are correct for each field");
        assertEquals(addCompanyPage.getAllFieldPlaceholders(), expectedPlaceholders);
    }

    @Test(dataProvider = "getInvalidCompanyNameLengths", dataProviderClass = TestDataProvider.class)
    @TmsLink("191")
    @Epic("Companies and business units")
    @Feature("Company Name Length Validation")
    @Description("Error message is shown for company name is shorter than 4 or longer than 100 characters.")
    public void testVerifyErrorMessageForInvalidCompanyNameLength(String name) {
        AddCompanyDialog addCompanyPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .fillCompanyNameField(name)
                .fillCompanyTypeField("Company type")
                .clickCreateButtonAndTriggerError();

        Allure.step("Verify: error message for invalid company name: '{name}' is displayed");
        assertThat(addCompanyPage.getErrorMessage()).containsText(
                "Invalid companyName: '" + name + "'. It must contain between 4 and 100 characters");
    }

    @Test(dataProvider = "getEmptyRequiredFields", dataProviderClass = TestDataProvider.class)
    @TmsLink("206")
    @Epic("Companies and business units")
    @Feature("Validation of Required Fields")
    @Description("'Create' button is disabled when required fields are not filled.")
    public void testCreateButtonDisabledWhenRequiredFieldsAreEmpty(String name, String type) {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .fillCompanyNameField(name)
                .fillCompanyTypeField(type);

        Allure.step("Verify: 'Create' button is disabled when required fields are not filled.");
        assertThat(addCompanyDialog.getCreateButton()).isDisabled();
    }

    @Test
    @TmsLink("184")
    @Feature("Close Button Functionality")
    @Description("Verify that clicking the Close button successfully closes the 'Add Company' dialog.")
    public void testVerifyCloseAddCompanyDialogWhenCloseButtonIsClicked() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .clickCloseButton();

        Allure.step("Verify: the 'Add Company' dialog is no longer visible");
        assertThat(companiesAndBusinessUnitsPage.getAddCompanyDialog()).isHidden();
    }
}
