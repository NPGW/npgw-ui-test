package xyz.npgw.test.page.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.TransactionsPage;

@Getter
public class TransactionDetailsDialog extends BaseDialog<TransactionsPage, TransactionDetailsDialog> {

    private final Locator statusField = getDialog().getByText("Status");
    private final Locator amountField = getDialog().getByText("Amount");
    private final Locator merchantReferenceField = getDialog().getByText("Merchant reference");
    private final Locator cardDetailsSection = getByRole(AriaRole.REGION, "Card details");
    @Getter
    private final Locator chevron = locator("span[data-slot='indicator']");
    @Getter
    private final Locator paymentMethodParameter = cardDetailsSection.locator("//div[.='Payment method']/following-sibling::div[1]");
    @Getter
    private final Locator cardTypeParameter = cardDetailsSection.locator("//div[.='Card type']/following-sibling::div[1]");
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

    @Step("Click on chevron in Card details section")
    public TransactionDetailsDialog clickChevronInCardDetailsSection() {
        chevron.click();
        return new TransactionDetailsDialog(getPage());
    }
}
