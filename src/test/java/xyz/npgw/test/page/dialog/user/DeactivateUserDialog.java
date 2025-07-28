package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.TeamPage;

public final class DeactivateUserDialog<ReturnPageT>
        extends BaseDialog<ReturnPageT, DeactivateUserDialog<ReturnPageT>> {

    private final ReturnPageT returnPageT;

    public DeactivateUserDialog(Page page, ReturnPageT returnPageT) {
        super(page);
        this.returnPageT = returnPageT;
    }

    @Override
    protected ReturnPageT getReturnPage() {
        return returnPageT;
    }

    @Step("Click 'Deactivate' button")
    public ReturnPageT clickDeactivateButton() {
        getByRole(AriaRole.BUTTON, "Deactivate").click();

        return getReturnPage();
    }
}
