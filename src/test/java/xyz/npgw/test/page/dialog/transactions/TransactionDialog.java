package xyz.npgw.test.page.dialog.transactions;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.dialog.BaseDialog;

public abstract class TransactionDialog<CurrentDialogT extends TransactionDialog<CurrentDialogT>>
        extends BaseDialog<TransactionsPage, CurrentDialogT> {

    public TransactionDialog(Page page) {
        super(page);
    }

    @Override
    protected TransactionsPage getReturnPage() {

        return new TransactionsPage(getPage());
    }

}


