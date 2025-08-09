package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.dashboard.SuperDashboardPage;
import xyz.npgw.test.page.dialog.user.AddUserDialog;
import xyz.npgw.test.page.dialog.user.EditUserDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;
import xyz.npgw.test.page.system.SuperTeamPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static xyz.npgw.test.common.Constants.COMPANY_NAME_FOR_TEST_RUN;

public class TeamPageTest extends BaseTest {

    private static final String MERCHANT_TITLE = "Business unit 1";
    private static final String SUCCESS_MESSAGE_USER_CREATED = "SUCCESSUser was created successfully";
    private static final String SUCCESS_MESSAGE_USER_UPDATED = "SUCCESSUser was updated successfully";
    private static final String SUCCESS_MESSAGE_USER_DELETED = "SUCCESSUser was deleted successfully";
    private static String systemAdminEmail;
    private static String companyAdminEmail;
    private static String companyAnalystEmail;

    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        TestUtils.createBusinessUnit(getApiRequestContext(), getCompanyName(), MERCHANT_TITLE);
    }

    @Test
    @TmsLink("154")
    @Epic("System/Team")
    @Feature("Navigation")
    @Description("User navigate to 'System administration page'")
    public void testNavigateToSystemAdministrationPage() {
        SuperTeamPage systemAdministrationPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink();

        Allure.step("Verify: System administration Page URL");
        assertThat(systemAdministrationPage.getPage()).hasURL(Constants.SYSTEM_PAGE_URL);

        Allure.step("Verify: System administration Page Title");
        assertThat(systemAdministrationPage.getPage()).hasTitle(Constants.SYSTEM_URL_TITLE);
    }

    @Test
    @TmsLink("298")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Add new system admin under super admin")
    public void testAddSystemAdmin() {
        systemAdminEmail = "%s.newsuper@email.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .clickAddUserButton()
                .fillEmailField(systemAdminEmail)
                .fillPasswordField("Qwerty123!")
                .checkSystemAdminRadiobutton()
                .clickCreateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_CREATED);
    }

    @Test(dependsOnMethods = "testAddSystemAdmin")
    @TmsLink("745")
    @Epic("System/Team")
    @Feature("Delete user")
    @Description("Verify system admin can be deleted")
    public void testDeleteSystemAdmin() {
        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany("super")
                .getTable().clickDeleteUserIcon(systemAdminEmail)
                .clickDeleteButton();

        Allure.step("Verify: success alert appears after deleting the system admin");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_DELETED);

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserAbsence(getApiRequestContext(), systemAdminEmail, "super");

        Allure.step("Verify: deleted system admin is no longer present in the users table");
        assertFalse(teamPage.getTable().getColumnValuesFromAllPages("Username").contains(systemAdminEmail));
    }

    @Test
    @TmsLink("298")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Add new company admin under super admin")
    public void testAddCompanyAdmin() {
        companyAdminEmail = "%s.newadmin@email.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .clickAddUserButton()
                .fillEmailField(companyAdminEmail)
                .fillPasswordField("Qwerty123!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_CREATED);
    }

    @Test(dependsOnMethods = "testAddCompanyAdmin")
    @TmsLink("747")
    @Epic("System/Team")
    @Feature("Delete user")
    @Description("Verify company admin can be deleted")
    public void testDeleteCompanyAdmin() {
        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .getTable().clickDeleteUserIcon(companyAdminEmail)
                .clickDeleteButton();

        Allure.step("Verify: success alert appears after deleting the company admin");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_DELETED);

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserAbsence(getApiRequestContext(), companyAdminEmail, getCompanyName());

        Allure.step("Verify: deleted company admin is no longer present in the users table");
        assertFalse(teamPage.getTable().getColumnValuesFromAllPages("Username").contains(companyAdminEmail));
    }

    @Test
    @TmsLink("330")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Add a new user and verify that all fields, statuses, and icons are correctly displayed.")
    public void testAddCompanyAnalyst() {
        companyAnalystEmail = "%s.newuser@email.com".formatted(TestUtils.now());

        AddUserDialog addUserDialog = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .clickAddUserButton();

        Allure.step("Verify: 'Add user' header is displayed");
        assertThat(addUserDialog.getDialogHeader()).hasText("Add user");

        Allure.step("Verify: company name is pre-filled correctly ");
        assertThat(addUserDialog.getCompanyNameField()).hasValue(getCompanyName());

        Allure.step("Verify: company name field is not editable");
        assertThat(addUserDialog.getCompanyNameField()).isDisabled();

        SuperTeamPage teamPage = addUserDialog
                .fillEmailField(companyAnalystEmail)
                .fillPasswordField("Qwerty123!")
                .checkCompanyAnalystRadiobutton()
                .checkAllowedBusinessUnitCheckbox(MERCHANT_TITLE)
                .clickCreateButton();

        Allure.step("Verify: a success alert appears after user creation");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_CREATED);

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserPresence(getApiRequestContext(), companyAnalystEmail, getCompanyName());

        Allure.step("Verify: selected company is displayed in the 'Select company' field");
        assertThat(teamPage.getSelectCompany().getSelectCompanyField()).hasValue(getCompanyName());

        Allure.step("Verify: new user has the role 'USER'");
        assertThat(teamPage.getTable().getCell(companyAnalystEmail, "User role")).hasText("USER");

        Allure.step("Verify: new user has status 'Active'");
        assertThat(teamPage.getTable().getCell(companyAnalystEmail, "Status")).hasText("Active");

        Allure.step("Verify: 'Deactivate' icon is shown for the new user");
        assertEquals(teamPage.getTable().getUserActivityIcon(companyAnalystEmail).getAttribute("data-icon"), "ban");
    }

    @Test(dependsOnMethods = "testAddCompanyAnalyst")
    @TmsLink("908")
    @Epic("System/Companies and business units")
    @Feature("Delete Business unit")
    @Description("Verify that business unit cannot be deleted if there are users associated with it")
    public void testDeletingBusinessUnitWithUsersFailsWithError() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .getSelectCompany().selectCompany(getCompanyName())
                .getTable().clickDeleteBusinessUnitButton(MERCHANT_TITLE)
                .clickDeleteButton();

        Allure.step("Verify: business unit deletion fails with expected error message");
        assertThat(companiesAndBusinessUnitsPage.getAlert().getMessage())
                .hasText("ERRORMerchant could not be deleted: there are still users associated with it.");
    }

    @Test(dependsOnMethods = "testDeletingBusinessUnitWithUsersFailsWithError")
    @TmsLink("748")
    @Epic("System/Team")
    @Feature("Delete user")
    @Description("Verify company analyst can be deleted")
    public void testDeleteCompanyAnalyst() {
        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .getTable().clickDeleteUserIcon(companyAnalystEmail)
                .clickDeleteButton();

        Allure.step("Verify: success alert appears after deleting the company analyst");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_DELETED);

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserAbsence(getApiRequestContext(), companyAnalystEmail, getCompanyName());

        Allure.step("Verify: deleted company analyst is no longer present in the users table");
        assertFalse(teamPage.getTable().getColumnValuesFromAllPages("Username").contains(companyAnalystEmail));
    }

    @Test
    @TmsLink("331")
    @Epic("System/Team")
    @Feature("Edit user")
    @Description("Edits the user's role and status, verifies the updates, and reactivates the user.")
    public void testEditUser() {
        String email = "%s.edit.analyst@email.com".formatted(TestUtils.now());

        EditUserDialog editUserDialog = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Qwerty123!")
                .checkCompanyAnalystRadiobutton()
                .checkAllowedBusinessUnitCheckbox(MERCHANT_TITLE)
                .clickCreateButton()
                .waitForUserPresence(getApiRequestContext(), email, getCompanyName())
                .getTable().clickEditUserButton(email);

        Allure.step("Verify: 'Edit user' header is displayed");
        assertThat(editUserDialog.getDialogHeader()).hasText("Edit user");

        Allure.step("Verify: company name is pre-filled correctly ");
        assertThat(editUserDialog.getCompanyNameField()).hasValue(getCompanyName());

        Allure.step("Verify: company name field is not editable");
        assertThat(editUserDialog.getCompanyNameField()).isDisabled();

        SuperTeamPage teamPage = editUserDialog
                .checkInactiveRadiobutton()
                .uncheckAllowedBusinessUnitCheckbox(MERCHANT_TITLE)
                .checkCompanyAdminRadiobutton()
                .clickSaveChangesButton();

        Allure.step("Verify: success alert appears after user update");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_UPDATED);

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserDeactivation(getApiRequestContext(), email, getCompanyName());

        Allure.step("Verify: selected company is displayed in the 'Select company' field");
        assertThat(teamPage.getSelectCompany().getSelectCompanyField()).hasValue(getCompanyName());

        Allure.step("Verify: user role was updated to 'ADMIN'");
        assertThat(teamPage.getTable().getCell(email, "User role")).hasText("ADMIN");

        Allure.step("Verify: Verify that user status was updated to 'Inactive'");
        assertThat(teamPage.getTable().getCell(email, "Status")).hasText("Inactive");

        Allure.step("Verify: 'Activate' icon is shown for the user");
        assertThat(teamPage.getTable().getUserActivityIcon(email)).hasAttribute("data-icon", "check");
    }

    @Test
    @TmsLink("474")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Create new company admin user under admin")
    public void testCreateCompanyAdminUser(@Optional("ADMIN") String userRole) {
        String email = "%s.email@gmail.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Password1!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_CREATED);
    }

    @Test
    @TmsLink("471")
    @Epic("System/Team")
    @Feature("Edit user")
    @Description("Deactivate user by 'Change user activity button' and verify status change")
    public void testDeactivateUserViaChangeUserActivityButton() {
        String email = "%s.change@gmail.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Qwerty123!")
                .checkCompanyAnalystRadiobutton()
                .checkAllowedBusinessUnitCheckbox(MERCHANT_TITLE)
                .clickCreateButton()
                .waitForUserPresence(getApiRequestContext(), email, getCompanyName())
                .getTable().clickDeactivateUserButton(email)
                .clickDeactivateButton()
                .waitForUserDeactivation(getApiRequestContext(), email, getCompanyName());

        Allure.step("Verify: selected company is displayed in the 'Select company' field");
        assertThat(teamPage.getSelectCompany().getSelectCompanyField()).hasValue(getCompanyName());

        Allure.step("Verify: user status becomes 'Inactive' in the table");
        assertThat(teamPage.getTable().getCell(email, "Status")).hasText("Inactive");

        Allure.step("Verify: 'Activate user' icon is shown for the user");
        assertThat(teamPage.getTable().getUserActivityIcon(email)).hasAttribute("data-icon", "check");
    }

    @Test
    @TmsLink("475")
    @Epic("System/Team")
    @Feature("Edit user")
    @Description("Edit user under company admin")
    public void testEditCompanyUser(@Optional("ADMIN") String userRole) {
        String email = "%s.edit@gmail.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Password1!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton()
                .waitForUserPresence(getApiRequestContext(), email, getCompanyName())
                .getTable().clickEditUserButton(email)
                .checkInactiveRadiobutton()
                .clickSaveChangesButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_UPDATED);

        teamPage
                .waitForUserDeactivation(getApiRequestContext(), email, getCompanyName());

        Allure.step("Verify: status of the user was changed");
        assertThat(teamPage.getTable().getCell(email, "Status")).hasText("Inactive");
    }

    @Test
    @TmsLink("476")
    @Epic("System/Team")
    @Feature("Edit user")
    @Description("Deactivate and activate user under company admin")
    public void testDeactivateAndActivateCompanyUser(@Optional("ADMIN") String userRole) {
        String email = "%s.deactivate.and.activate@gmail.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Password1!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton()
                .waitForUserPresence(getApiRequestContext(), email, getCompanyName())
                .getTable().clickDeactivateUserButton(email)
                .clickDeactivateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText("SUCCESSUser was deactivated successfully");

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserDeactivation(getApiRequestContext(), email, getCompanyName());

        Allure.step("Verify: status of the user was changed");
        assertThat(teamPage.getTable().getCell(email, "Status")).hasText("Inactive");

        Allure.step("Verify: deactivate user icon appears");
        assertThat(teamPage.getTable().getUserActivityIcon(email)).hasAttribute("data-icon", "check");

        teamPage
                .getTable().clickActivateUserButton(email)
                .clickActivateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText("SUCCESSUser was activated successfully");

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserActivation(getApiRequestContext(), email, getCompanyName());

        Allure.step("Verify: status of the user was changed");
        assertThat(teamPage.getTable().getCell(email, "Status")).hasText("Active");

        Allure.step("Verify: activate user icon appears");
        assertThat(teamPage.getTable().getUserActivityIcon(email)).hasAttribute("data-icon", "ban");
    }

    @Test
    @TmsLink("554")
    @Epic("System/Team")
    @Feature("Edit user")
    @Description("Reset company analyst password under company admin")
    public void testResetPasswordForCompanyAnalyst(@Optional("ADMIN") String userRole) {
        String email = "%s.reset.password@gmail.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Password1!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton()
                .waitForUserPresence(getApiRequestContext(), email, getCompanyName())
                .getTable().clickResetUserPasswordIcon(email)
                .fillPasswordField("NewPassword1!")
                .clickResetButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText("SUCCESSPassword was reset successfully");

        teamPage.clickLogOutButton()
                .fillEmailField(email)
                .fillPasswordField("NewPassword1!")
                .clickLoginButtonToChangePassword()
                .fillNewPasswordField("ChangedNewPassword1!")
                .fillRepeatNewPasswordField("ChangedNewPassword1!")
                .clickSaveButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText("SUCCESSPassword is changed successfully");
    }

    @Test
    @TmsLink("492")
    @Epic("System/Team")
    @Feature("Edit user")
    @Description("Create company analyst under admin")
    public void testCreateCompanyAnalystAndDeactivate(@Optional("ADMIN") String userRole) {
        String analystEmail = "%s.company.analyst@gmail.com".formatted(TestUtils.now());
        String analystPassword = "CompanyAnalyst123!";

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .clickAddUserButton()
                .fillEmailField(analystEmail)
                .fillPasswordField(analystPassword)
                .checkAllowedBusinessUnitCheckbox(MERCHANT_TITLE)
                .clickCreateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText(SUCCESS_MESSAGE_USER_CREATED);

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserPresence(getApiRequestContext(), analystEmail, getCompanyName());

        Allure.step("Verify: status of the user was changed");
        assertThat(teamPage.getTable().getCell(analystEmail, "User role")).hasText("USER");

        Allure.step("Verify: status of the user");
        assertThat(teamPage.getTable().getCell(analystEmail, "Status")).hasText("Active");

        Allure.step("Verify: deactivate user icon appears");
        assertThat(teamPage.getTable().getUserActivityIcon(analystEmail)).hasAttribute("data-icon", "ban");

        teamPage
                .getTable().clickDeactivateUserButton(analystEmail)
                .clickDeactivateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText("SUCCESSUser was deactivated successfully");

        teamPage
                .getAlert().clickCloseButton()
                .waitForUserDeactivation(getApiRequestContext(), analystEmail, getCompanyName());

        Allure.step("Verify: status of the user was changed");
        assertThat(teamPage.getTable().getCell(analystEmail, "Status")).hasText("Inactive");

        Allure.step("Verify: deactivate user icon appears");
        assertThat(teamPage.getTable().getUserActivityIcon(analystEmail)).hasAttribute("data-icon", "check");

        LoginPage loginPage = teamPage
                .clickLogOutButton()
                .loginAsDisabledUser(analystEmail, analystPassword);

        Allure.step("Verify: error message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText("ERRORUser is disabled.");

        loginPage
                .loginAsAdmin("%s.admin@email.com".formatted(getUid()), ProjectProperties.getPassword())
                .getHeader().clickSystemAdministrationLink()
                .getTable().clickEditUserButton(analystEmail)
                .checkActiveRadiobutton()
                .clickSaveChangesButton()
                .clickLogOutButton()
                .fillEmailField(analystEmail)
                .fillPasswordField(analystPassword)
                .clickLoginButtonToChangePassword()
                .fillNewPasswordField(analystPassword)
                .fillRepeatNewPasswordField(analystPassword)
                .clickSaveButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlert().getMessage()).hasText("SUCCESSPassword is changed successfully");
    }

    @Test
    @TmsLink("531")
    @Epic("System/Team")
    @Feature("Status")
    @Description("Status filter correctly displays users with 'Active' or 'Inactive' status")
    public void testStatusFilterDisplaysCorrectUsers() {
        final String statusColumnName = "Status";
        final String email = "%s.filter@email.com".formatted(TestUtils.now());

        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Password1!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton()
                .waitForUserPresence(getApiRequestContext(), email, getCompanyName())
                .getTable().clickDeactivateUserButton(email)
                .clickDeactivateButton()
                .waitForUserDeactivation(getApiRequestContext(), email, getCompanyName())
                .getSelectStatus().select("Active");

        Allure.step("Verify: All visible users are 'Active' after applying 'Active' filter");
        assertTrue(teamPage.getTable().getColumnValues(statusColumnName)
                .stream().allMatch(value -> value.equals("Active")));

        teamPage
                .getSelectStatus().select("Inactive");

        Allure.step("Verify: All visible users are 'Inactive' after applying Inactive filter");
        assertTrue(teamPage.getTable().getColumnValues(statusColumnName)
                .stream().allMatch(value -> value.equals("Inactive")));
    }

    @Test
    @TmsLink("551")
    @Epic("System/Team")
    @Feature("Sorting in table")
    @Description("Verify that users can be sorted alphabetically")
    public void testCheckSortingListOfUsersAlphabetically() {
        List<String> sortedUsersAlphabetically = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getTable().clickSortIcon("Username")
                .getTable().getColumnValues("Username");

        List<String> expectedSortedList = new ArrayList<>(sortedUsersAlphabetically);
        Collections.sort(expectedSortedList);

        Assert.assertEquals(sortedUsersAlphabetically, expectedSortedList,
                "The list of users is not sorted alphabetically.");
    }

    @Test
    @TmsLink("552")
    @Epic("System/Team")
    @Feature("Sorting in table")
    @Description("Verify that users can be sorted in reverse alphabetical order")
    public void testCheckSortingListOfUsersReverse() {
        List<String> sortedUsersReverseAlphabetically = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getTable().clickSortIcon("Username")
                .getTable().clickSortIcon("Username")
                .getTable().getColumnValues("Username");

        List<String> expectedSortedList = new ArrayList<>(sortedUsersReverseAlphabetically);
        expectedSortedList.sort(Collections.reverseOrder());

        Assert.assertEquals(sortedUsersReverseAlphabetically, expectedSortedList,
                "The list of users is not sorted in reverse alphabetical order.");
    }

    @Test
    @TmsLink("612")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Adding a user with an existing email address results in an error message.")
    public void testAddUserWithExistingEmail() {
        final String companyAdmin = "%s.companydmin@email.com".formatted(TestUtils.now());

        AddUserDialog addUserDialog = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .clickAddUserButton()
                .fillEmailField(companyAdmin)
                .fillPasswordField("Qwerty123!")
                .checkActiveRadiobutton()
                .checkCompanyAdminRadiobutton()
                .clickCreateButton()
//                .getAlert().clickCloseButton() TODO bug - if closed on second time alert is closing immediately
                .getAlert().waitUntilAlertIsHidden()
                .waitForUserPresence(getApiRequestContext(), companyAdmin, getCompanyName())
                .clickAddUserButton()
                .fillEmailField(companyAdmin)
                .fillPasswordField("Qwerty123!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButtonAndTriggerError();

        Allure.step("Verify: Error message is displayed for existing user");
        assertThat(addUserDialog.getAlert().getMessage()).hasText("ERRORUser account already exists");
    }

    @Test
    @TmsLink("683")
    @Epic("System/Team")
    @Feature("Reset filter")
    @Description("'Reset filter' button resets the 'Status' filter to 'All' and clears the selected company")
    public void testResetFilter() {
        SuperTeamPage teamPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink();

        Allure.step("Verify: 'Status' filter displays 'All' by default");
        assertThat(teamPage.getSelectStatus().getStatusValue()).hasText("All");

        Allure.step("Verify: 'Select company' filter is empty by default");
        assertThat(teamPage.getSelectCompany().getSelectCompanyField()).isEmpty();

        List.of("Active", "Inactive").forEach(status -> {
            teamPage
                    .getSelectCompany().selectCompany(getCompanyName())
                    .getSelectStatus().selectTransactionStatuses(status)
                    .clickResetFilterButton();

            Allure.step("Verify: 'Status' filter displays 'All' after reset");
            assertThat(teamPage.getSelectStatus().getStatusValue()).hasText("All");

            Allure.step("Verify: 'Select company' filter is empty after reset");
            assertThat(teamPage.getSelectCompany().getSelectCompanyField()).isEmpty();
        });
    }
}
