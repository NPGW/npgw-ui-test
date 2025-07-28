package xyz.npgw.test.page.dialog.transactions;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.dialog.BaseDialog;

@Getter
public final class RefundTransactionDialog<CurrentPageT>
        extends BaseDialog<CurrentPageT, RefundTransactionDialog<CurrentPageT>> {

    private final Locator amountToRefundInput = locator("[aria-roledescription='Number field']");
    private final Locator increaseAmountToRefundButton = getByLabelExact("Increase Amount to refund");
    private final CurrentPageT currentPageT;

    public RefundTransactionDialog(Page page, CurrentPageT currentPageT) {
        super(page);
        this.currentPageT = currentPageT;
    }

    @Override
    protected CurrentPageT getReturnPage() {
        return currentPageT;
    }

    public Locator getRefundMessage(String expectedText) {
        return getPage().locator("p:has-text('" + expectedText + "')");
    }
}
