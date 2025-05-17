package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.user.ChangeUserActivityDialog;
import xyz.npgw.test.page.dialog.user.EditUserDialog;
import xyz.npgw.test.page.dialog.user.ResetUserPasswordDialog;
import xyz.npgw.test.page.system.TeamPage;

public class UserTableComponent extends BaseTableComponent {

    public UserTableComponent(Page page) {
        super(page);
    }

    @Step("Click 'Edit user'")
    public EditUserDialog clickEditUserButton(String email) {
        getTableRow(email).getByTestId("EditUserButton").click();

        return new EditUserDialog(getPage());
    }

    @Step("Click 'Deactivate user' button")
    public ChangeUserActivityDialog clickDeactivateUserButton(String email) {
        getTableRow(email).locator("//*[@data-icon='ban']/..").click();

        return new ChangeUserActivityDialog(getPage());
    }

    @Step("Click 'Activate user' button")
    public ChangeUserActivityDialog clickActivateUserButton(String email) {
        getTableRow(email).locator("//*[@data-icon='check']/..").click();

        return new ChangeUserActivityDialog(getPage());
    }

    @Step("Click 'Reset user password' button")
    public ResetUserPasswordDialog clickResetUserPasswordButton(String email) {
        getTableRow(email).getByTestId("ResetUserPasswordButton").click();

        return new ResetUserPasswordDialog(getPage());
    }

    public Locator getUserRole(String email) {
        int columnIndex = getColumnHeaderIndexByName("User role");
        Locator row = getTableRow(email);

        return row.getByRole(AriaRole.GRIDCELL).or(row.getByRole(AriaRole.ROWHEADER)).nth(columnIndex);
    }

    public Locator getUserStatus(String email) {
        int columnIndex = getColumnHeaderIndexByName("Status");
        Locator row = getTableRow(email);

        return row.getByRole(AriaRole.GRIDCELL).or(row.getByRole(AriaRole.ROWHEADER)).nth(columnIndex);
    }

    public Locator getUserActivityIcon(String email) {
        return getTableRow(email).getByTestId("ChangeUserActivityButton").locator("svg");
    }

    @Step("Deactivate user")
    public TeamPage deactivateUser(String email) {
        return clickDeactivateUserButton(email)
                .clickDeactivateButton()
                .getAlert().waitUntilSuccessAlertIsGone()
                .clickRefreshDataButton();
    }

    @Step("@Step(Click sort icon in '{columnName}' column)")
    public TeamPage clickSortIcon(String columnName) {
        getHeaderByName(columnName).locator("svg").click();
        getPage().waitForTimeout(500);

        return new TeamPage(getPage());
    }
}
