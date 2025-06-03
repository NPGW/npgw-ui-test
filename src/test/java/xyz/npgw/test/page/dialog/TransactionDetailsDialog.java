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
    private final Locator chevron = locator("span[data-slot='indicator']");

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
        getPage().waitForTimeout(500);
        return new TransactionDetailsDialog(getPage());
    }
    public Locator cardDetailsParameter(String parameter) {
        return cardDetailsSection.locator("//div[.='" + parameter + "']/following-sibling::div[1]");
    }
}
