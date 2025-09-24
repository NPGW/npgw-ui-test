package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BaseModel;
import xyz.npgw.test.page.dialog.BaseDialog;

public class EditAcquirerConfigDialog<ReturnPageT extends BaseModel>
        extends BaseDialog<ReturnPageT, EditAcquirerConfigDialog<ReturnPageT>> {

    private final Locator saveButton = getByRole(AriaRole.BUTTON, "Save");
    private final Locator enterConfigArea = getByLabelExact("Enter the config");

    public EditAcquirerConfigDialog(Page page) {
        super(page);
    }

    @Override
    protected ReturnPageT getReturnPage() {
        return (ReturnPageT) new SetupAcquirerMidDialog(getPage());
    }

    @Step("Click on the 'Save' button")
    public ReturnPageT clickSaveButton() {
        saveButton.click();

        return getReturnPage();
    }

    @Step("Fill acquirer config area")
    public EditAcquirerConfigDialog<ReturnPageT> fillAcquirerConfigArea(String acquirerConfig) {
        enterConfigArea.fill(acquirerConfig);

        return this;
    }
}
