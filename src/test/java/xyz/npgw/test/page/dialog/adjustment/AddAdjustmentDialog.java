package xyz.npgw.test.page.dialog.adjustment;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.TransactionManagementPage;

public class AddAdjustmentDialog extends BaseDialog<TransactionManagementPage, AddAdjustmentDialog> {
    @Getter
    private final Locator npgwReferenceInput = locator("input[aria-label='NPGW reference']");
    private final Locator transactionRow = locator("[aria-label='transactions table'] tr[data-first]");
    @Getter
    private final Locator reference = locator(".trxId");

    private final Locator createButton = getByRole(AriaRole.BUTTON, "Create");
    private final Locator closeButton = getByRole(AriaRole.BUTTON)
            .filter(new Locator.FilterOptions().setHasText("Close"));

    public AddAdjustmentDialog(Page page) {
        super(page);
    }

    @Override
    protected TransactionManagementPage getReturnPage() {
        return new TransactionManagementPage(getPage());
    }

    @Step("Click on the NPGW reference input field")
    public AddAdjustmentDialog clickOnNpgwReferenceInput() {
        npgwReferenceInput.click();

        return this;
    }

    @Step("Paste from clipboard into NPGW reference input field using Ctrl+V")
    public AddAdjustmentDialog pasteIntoNpgwReferenceInputUsingCtrlV() {
        npgwReferenceInput.press("Control+V");

        return this;
    }

    public AddAdjustmentDialog clickOnTheTransaction() {
        transactionRow.click();

        return this;
    }

    public TransactionManagementPage clickOnCreateButton() {
        createButton.click();

        return new TransactionManagementPage(getPage());
    }

    public TransactionManagementPage clickOnCloseButton() {
        closeButton.click();
        getDialog().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        return new TransactionManagementPage(getPage());
    }

    public String getNpgwReferenceInputValue() {
        return npgwReferenceInput.inputValue();
    }

}
