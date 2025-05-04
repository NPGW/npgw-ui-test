package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.UserRole;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.User;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.AboutBlankPage;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.dialog.user.AddUserDialog;
import xyz.npgw.test.page.dialog.user.EditUserDialog;
import xyz.npgw.test.page.system.TeamPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

public class TeamPageTest extends BaseTest {

    private static final String COMPANY_NAME = "Smitham-Johnson";

    User user = new User(
            COMPANY_NAME,
            true,
            UserRole.USER,
            new String[]{"MerchantNameTest"},
            "dummy@email.com",
            ProjectProperties.getUserPassword());

    User updatedUser = new User(
            COMPANY_NAME,
            false,
            UserRole.ADMIN,
            new String[]{},
            "dummy@email.com",
            ProjectProperties.getAdminPassword()
    );

    @Test
    @TmsLink("154")
    @Epic("System/Team")
    @Feature("Navigation")
    @Description("User navigate to 'System administration page' after clicking "
            + "on 'System administration' link on the header")
    public void testNavigateToSystemAdministrationPage() {
        TeamPage systemAdministrationPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink();

        Allure.step("Verify: System administration Page URL");
        assertThat(systemAdministrationPage.getPage()).hasURL(Constants.SYSTEM_PAGE_URL);

        Allure.step("Verify: System administration Page Title");
        assertThat(systemAdministrationPage.getPage()).hasTitle(Constants.SYSTEM_URL_TITLE);
    }

    @Test(dataProvider = "getUsers", dataProviderClass = TestDataProvider.class)
    @TmsLink("298")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Add users with roles [SUPER, ADMIN, USER] as super admin")
    public void testAddUser(User user) {
        TestUtils.createBusinessUnitsIfNeeded(getApiRequestContext(), user);
        TestUtils.deleteUser(getApiRequestContext(), user);

        TeamPage teamPage = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(user.companyName())
                .clickAddUserButton()
                .fillEmailField(user.email())
                .fillPasswordField(user.password())
                .setStatusRadiobutton(user.enabled())
                .setUserRoleRadiobutton(user.userRole())
                .setAllowedBusinessUnits(user.merchantIds())
                .clickCreateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlertMessage()).hasText("SUCCESSUser was created successfully");
    }

    @Test
    @TmsLink("330")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Add a new user and verify that all fields, statuses, and icons are correctly displayed(e2e).")
    public void testAddCompanyAnalyst() {
        TestUtils.createBusinessUnitsIfNeeded(getApiRequestContext(), user);
        TestUtils.deleteUser(getApiRequestContext(), user);

        AddUserDialog addUserDialog = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(user.companyName())
                .clickAddUserButton();

        Allure.step("Verify: 'Add user' header is displayed");
        assertThat(addUserDialog.getDialogHeader()).hasText("Add user");

        TeamPage teamPage = addUserDialog
                .fillEmailField(user.email())
                .fillPasswordField(user.password())
                .setStatusRadiobutton(user.enabled())
                .setUserRoleRadiobutton(user.userRole())
                .setAllowedBusinessUnits(user.merchantIds())
                .clickCreateButton()
                .clickApplyFilter();

        Allure.step("Verify: a success alert appears after user creation");
        assertThat(teamPage.getAlertMessage()).hasText("SUCCESSUser was created successfully");

        Allure.step("Verify: selected company is displayed in the 'Select company' field");
        assertThat(teamPage.getSelectCompany().getSelectCompanyField()).hasValue(user.companyName());

        Allure.step("Verify: new user's email is displayed in the table");
        assertThat(teamPage.getUsernameByEmail(user.email())).hasText(user.email());

        Allure.step("Verify: new user has the role 'USER'");
        assertThat(teamPage.getUserRoleByEmail(user.email())).hasText("USER");

        Allure.step("Verify: new user has status 'Active'");
        assertThat(teamPage.getUserStatusByEmail(user.email())).hasText("Active");

        Allure.step("Verify: 'Deactivate' icon is shown for the new user");
        assertEquals(teamPage.getChangeUserActivityButton(user.email()).getAttribute("data-icon"), "ban");
    }

    @Test
    @TmsLink("331")
    @Epic("System/Team")
    @Feature("Edit user")
    @Description("Edits the user's role and status, verifies the updates, and reactivates the user(e2e).")
    public void testEditUser() {
        TestUtils.createCompanyIfNeeded(getApiRequestContext(), user);
        TestUtils.deleteUser(getApiRequestContext(), user);
        TestUtils.createUser(getApiRequestContext(), user);

        EditUserDialog editUserDialog = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSelectCompany().selectCompany(user.companyName())
                .clickEditUser(user.email());

        Allure.step("Verify: 'Edit user' header is displayed");
        assertThat(editUserDialog.getDialogHeader()).hasText("Edit user");

        TeamPage teamPage = editUserDialog
                .setStatusRadiobutton(updatedUser.enabled())
                .unsetAllowedBusinessUnits(user.merchantIds())
                .setUserRoleRadiobutton(updatedUser.userRole())
                .clickSaveChangesButton()
                .clickApplyFilter(); // Шаг добавлен т.к. обновленные данные не каждый раз появляются на ui

        Allure.step("Verify: success alert appears after user update");
        assertThat(teamPage.getAlertMessage()).hasText("SUCCESSUser was updated successfully");

        Allure.step("Verify: selected company is displayed in the 'Select company' field");
        assertThat(teamPage.getSelectCompany().getSelectCompanyField()).hasValue(user.companyName());

        Allure.step("Verify: updated user's email is still displayed correctly");
        assertThat(teamPage.getUsernameByEmail(user.email())).hasText(user.email());

        Allure.step("Verify: user role was updated to 'ADMIN'");
        assertThat(teamPage.getUserRoleByEmail(user.email())).hasText("ADMIN");

        Allure.step("Verify: Verify that user status was updated to 'Inactive'");
        assertThat(teamPage.getUserStatusByEmail(user.email())).hasText("Inactive");

        Allure.step("Verify: 'Activate' icon is shown for the user");
        assertEquals(teamPage.getChangeUserActivityButton(user.email()).getAttribute("data-icon"), "check");
    }

    @Test
    @TmsLink("")
    @Epic("System/Team")
    @Feature("Add user")
    @Description("Create new company admin user")
    public void testCreateCompanyAdminUser(@Optional("UNAUTHORISED") String userRole) {
        String email = "email@gmail.com";
        TestUtils.deleteUser(getApiRequestContext(), email);
        TestUtils.createCompanyAdmin(getApiRequestContext(), "amazon3@gmail.com");

        TeamPage teamPage = new AboutBlankPage(getPage())
                .navigate("/login")
                .fillEmailField("amazon3@gmail.com")
                .fillPasswordField("Amazon1!")
                .clickLoginButtonToChangePassword()
                .fillNewPasswordField("Amazon1!")
                .fillRepeatNewPasswordField("Amazon1!")
                .clickSaveButton()
                .fillEmailField("amazon3@gmail.com")
                .fillPasswordField("Amazon1!")
                .clickLoginButton()
                .waitUntilAlertIsGone()
                .getHeader().clickSystemAdministrationLink()
                .clickAddUserButton()
                .fillEmailField(email)
                .fillPasswordField("Password1!")
                .checkCompanyAdminRadiobutton()
                .clickCreateButton();

        Allure.step("Verify: success message is displayed");
        assertThat(teamPage.getAlertMessage()).hasText("SUCCESSUser was created successfully");
    }
}
