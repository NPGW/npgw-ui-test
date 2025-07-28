package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;

public final class EditUserDialog<ReturnPageT>
        extends UserDialog<ReturnPageT, EditUserDialog<ReturnPageT>> {

    private final ReturnPageT returnPageT;

    public EditUserDialog(Page page, ReturnPageT returnPageT) {
        super(page);
        this.returnPageT = returnPageT;
    }

    @Override
    protected ReturnPageT getReturnPage() {
        return returnPageT;
    }

    @Step("Click 'Save changes' button")
    public ReturnPageT clickSaveChangesButton() {
        getByRole(AriaRole.BUTTON, "Save changes").click();

        return getReturnPage();
    }
}
