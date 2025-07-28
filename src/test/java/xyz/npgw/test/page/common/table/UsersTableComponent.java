package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.user.ActivateUserDialog;
import xyz.npgw.test.page.dialog.user.DeactivateUserDialog;
import xyz.npgw.test.page.dialog.user.DeleteUserDialog;
import xyz.npgw.test.page.dialog.user.EditUserDialog;
import xyz.npgw.test.page.dialog.user.ResetUserPasswordDialog;

public class UsersTableComponent<CurrentPageT> extends BaseTableComponent<CurrentPageT> {

    private final CurrentPageT currentPageT;

    public UsersTableComponent(Page page, CurrentPageT currentPageT) {
        super(page);
        this.currentPageT = currentPageT;
    }

    @Override
    protected CurrentPageT getCurrentPage() {
        return currentPageT;
    }

    public Locator getUserActivityIcon(String userEmail) {
        return getRow(userEmail).getByTestId("ChangeUserActivityButton").locator("svg");
    }

    @Step("Click 'Edit user' button")
    public EditUserDialog<CurrentPageT> clickEditUserButton(String userEmail) {
        getRow(userEmail).getByTestId("EditUserButton").click();

        return new EditUserDialog<>(getPage(), currentPageT);
    }

    @Step("Click 'Activate user' button")
    public ActivateUserDialog<CurrentPageT> clickActivateUserButton(String userEmail) {
        getRow(userEmail).locator("//*[@data-icon='check']/..").click();

        return new ActivateUserDialog<>(getPage(), currentPageT);
    }

    @Step("Click 'Deactivate user' button")
    public DeactivateUserDialog<CurrentPageT> clickDeactivateUserButton(String userEmail) {
        getRow(userEmail).locator("//*[@data-icon='ban']/..").click();

        return new DeactivateUserDialog<>(getPage(), currentPageT);
    }

    @Step("Click 'Reset user password' button")
    public ResetUserPasswordDialog<CurrentPageT> clickResetUserPasswordIcon(String email) {
        getRow(email).getByTestId("ResetUserPasswordButton").click();

        return new ResetUserPasswordDialog<>(getPage(), currentPageT);
    }

    @Step("Click 'Delete user' button")
    public DeleteUserDialog<CurrentPageT> clickDeleteUserIcon(String userEmail) {
        getRow(userEmail).getByTestId("DeleteUserButton").click();

        return new DeleteUserDialog<>(getPage(), currentPageT);
    }
}
