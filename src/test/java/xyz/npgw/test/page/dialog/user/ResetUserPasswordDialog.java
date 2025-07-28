package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.TeamPage;

public final class ResetUserPasswordDialog<CurrentPageT>
        extends BaseDialog<CurrentPageT, ResetUserPasswordDialog<CurrentPageT>> {

    private final CurrentPageT currentPageT;

    public ResetUserPasswordDialog(Page page, CurrentPageT currentPageT) {
        super(page);
        this.currentPageT = currentPageT;
    }

    @Override
    protected CurrentPageT getReturnPage() {
        return currentPageT;
    }

    @Step("Enter new password in the 'New password' field")
    public ResetUserPasswordDialog<CurrentPageT> fillPasswordField(String newPassword) {
        getByPlaceholder("Enter new password").fill(newPassword);

        return this;
    }

    @Step("Click 'Reset' button")
    public CurrentPageT clickResetButton() {
        getByRole(AriaRole.BUTTON, "Reset").click();

        return getReturnPage();
    }
}
