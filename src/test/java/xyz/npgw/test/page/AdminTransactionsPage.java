package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.AdminHeaderTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;
import xyz.npgw.test.page.common.trait.SelectStatusTrait;
import xyz.npgw.test.page.common.trait.TransactionsTableTrait;

public final class AdminTransactionsPage extends TransactionsPage<AdminTransactionsPage> implements
        AdminHeaderTrait<AdminTransactionsPage>,
        SelectBusinessUnitTrait<AdminTransactionsPage>,
        SelectDateRangeTrait<AdminTransactionsPage>,
        SelectStatusTrait<AdminTransactionsPage>,
        TransactionsTableTrait<AdminTransactionsPage> {

    public AdminTransactionsPage(Page page) {
        super(page);
    }

    @Override
    protected AdminTransactionsPage getReturnPage() {
        return this;
    }
}
