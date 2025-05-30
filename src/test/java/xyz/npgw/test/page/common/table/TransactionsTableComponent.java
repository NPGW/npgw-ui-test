package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.dialog.TransactionDetailsDialog;

public class TransactionsTableComponent extends BaseTableComponent<TransactionsPage> {

    public TransactionsTableComponent(Page page) {
        super(page);
    }

    @Override
    protected TransactionsPage getCurrentPage() {
        return new TransactionsPage(getPage());
    }
    @Step("Click on transaction in column 'NPGW reference'")
    public TransactionDetailsDialog clickOnTransaction() {
        getFirstRowCell("NPGW reference").click();

        return new TransactionDetailsDialog(getPage());
    }
}
