package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;
import xyz.npgw.test.page.common.trait.SelectStatusTrait;
import xyz.npgw.test.page.common.trait.TransactionsTableTrait;
import xyz.npgw.test.page.common.trait.UserHeaderTrait;

public final class UserTransactionsPage extends TransactionsPage<UserTransactionsPage> implements
        UserHeaderTrait<UserTransactionsPage>,
        SelectBusinessUnitTrait<UserTransactionsPage>,
        SelectDateRangeTrait<UserTransactionsPage>,
        SelectStatusTrait<UserTransactionsPage>,
        TransactionsTableTrait<UserTransactionsPage> {

    public UserTransactionsPage(Page page) {
        super(page);
    }

    @Override
    protected UserTransactionsPage getReturnPage() {
        return this;
    }
}
