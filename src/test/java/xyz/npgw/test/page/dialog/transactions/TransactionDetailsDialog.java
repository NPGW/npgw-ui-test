package xyz.npgw.test.page.dialog.transactions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.common.trait.AlertTrait;


public class TransactionDetailsDialog extends TransactionDialog<TransactionDetailsDialog>
        implements AlertTrait<TransactionDetailsDialog> {

    @Getter
    private final Locator statusField = getDialog().getByText("Status");
    @Getter
    private final Locator amountField = getDialog().getByText("Amount");
    @Getter
    private final Locator merchantReferenceField = getDialog().getByText("Merchant reference");
    @Getter
    private final Locator cardDetailsField = getDialog().locator("//div[@aria-label='Card details']");


    public TransactionDetailsDialog(Page page) {
        super(page);
    }
}
