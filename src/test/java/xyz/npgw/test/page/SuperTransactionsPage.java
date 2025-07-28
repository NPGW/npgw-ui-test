package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;
import xyz.npgw.test.page.common.trait.SelectStatusTrait;
import xyz.npgw.test.page.common.trait.SuperHeaderTrait;
import xyz.npgw.test.page.common.trait.TransactionsTableTrait;

public final class SuperTransactionsPage extends TransactionsPage<SuperTransactionsPage> implements
        SuperHeaderTrait<SuperTransactionsPage>,
        SelectCompanyTrait<SuperTransactionsPage>,
        SelectBusinessUnitTrait<SuperTransactionsPage>,
        SelectDateRangeTrait<SuperTransactionsPage>,
        SelectStatusTrait<SuperTransactionsPage>,
        TransactionsTableTrait<SuperTransactionsPage> {

    public SuperTransactionsPage(Page page) {
        super(page);
    }

    @Override
    protected SuperTransactionsPage getReturnPage() {
        return this;
    }
}
