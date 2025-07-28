package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;

public final class DeleteUserDialog<CurrentPageT>
        extends BaseDialog<CurrentPageT, DeleteUserDialog<CurrentPageT>> {

    private final CurrentPageT currentPageT;

    public DeleteUserDialog(Page page, CurrentPageT currentPageT) {
        super(page);
        this.currentPageT = currentPageT;
    }

    @Override
    protected CurrentPageT getReturnPage() {
        return currentPageT;
    }

    @Step("Click 'Delete' button")
    public CurrentPageT clickDeleteButton() {
        getByRole(AriaRole.BUTTON, "Delete").click();

        return getReturnPage();
    }
}
