package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.TeamPage;

public final class ActivateUserDialog<ReturnPageT>
        extends BaseDialog<ReturnPageT, ActivateUserDialog<ReturnPageT>> {

    private final ReturnPageT returnPageT;

    public ActivateUserDialog(Page page, ReturnPageT returnPageT) {
        super(page);
        this.returnPageT = returnPageT;
    }

    @Override
    protected ReturnPageT getReturnPage() {
        return returnPageT;
    }

    @Step("Click 'Activate' button")
    public ReturnPageT clickActivateButton() {
        getByRole(AriaRole.BUTTON, "Activate").click();

        return getReturnPage();
    }
}
