package xyz.npgw.test.page.dialog.control;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.FraudControlPage;

public class DeleteControlDialog extends BaseDialog<FraudControlPage, DeleteControlDialog> {

    private final Locator cancelButton = getDialog().getByText("Cancel");

    public DeleteControlDialog(Page page) {
        super(page);
    }

    @Override
    protected FraudControlPage getReturnPage() {
        return new FraudControlPage(getPage());
    }

    @Step("Click 'Delete' button")
    public FraudControlPage clickDeleteButton() {
        getByRole(AriaRole.BUTTON, "Delete").click();

        return getReturnPage();
    }

    @Step("Click on the 'Cancel' button to close form")
    public FraudControlPage clickCancelButton() {
        cancelButton.click();

        return getReturnPage();
    }
}
