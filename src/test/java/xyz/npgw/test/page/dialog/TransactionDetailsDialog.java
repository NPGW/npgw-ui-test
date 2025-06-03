package xyz.npgw.test.page.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.TransactionsPage;

@Getter
public class TransactionDetailsDialog extends BaseDialog<TransactionsPage, TransactionDetailsDialog> {

    private final Locator statusField = getDialog().getByText("Status");
    private final Locator amountField = getDialog().getByText("Amount");
    private final Locator merchantReferenceField = getDialog().getByText("Merchant reference");
    private final Locator cardDetailsField = getDialog().locator("//div[@aria-label='Card details']");
    @Getter
    private final Locator chevron = locator("span[data-slot='indicator']");
    @Getter
    private final Locator paymentMethodParameter = getByLabelExact("Payment method");
    @Getter
    private final Locator cardTypeParameter = locator("//div[@aria-label='Card details']//div[@class='flex-row'][2]/div[2]");
    @Getter
    private final Locator cardHolderParameter = getByLabelExact("Card holder");
    @Getter
    private final Locator cardNumberParameter = getByLabelExact("Card number");
    @Getter
    private final Locator expiryDateParameter = getByLabelExact("Expiry date");

    public TransactionDetailsDialog(Page page) {
        super(page);
    }

    @Override
    protected TransactionsPage getReturnPage() {
        return new TransactionsPage(getPage());
    }

    @Step("Click on chevron in Card details field'")
    public TransactionDetailsDialog clickChevronInCardDetailsField() {
        getChevron().click();
        return new TransactionDetailsDialog(getPage());
    }
}
