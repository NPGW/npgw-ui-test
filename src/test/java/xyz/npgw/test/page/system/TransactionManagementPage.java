package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.common.trait.MenuTrait;
import xyz.npgw.test.page.dialog.adjustment.AddAdjustmentDialog;

@Getter
public final class TransactionManagementPage extends HeaderPage<TransactionManagementPage> implements
        MenuTrait,
        AlertTrait<TransactionManagementPage> {

    private final Locator addAdjustmentButton = getByTestId("AddAdjustmentButtonTransactionManagementPage");
    private final Locator transactionsTable = getByLabelExact("transactions table");

    public TransactionManagementPage(Page page) {
        super(page);
    }

    public AddAdjustmentDialog clickAddAdjustmentButton() {
        addAdjustmentButton.click();

        return new AddAdjustmentDialog(getPage());
    }
}
