package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.ReportsTableTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;
import xyz.npgw.test.page.common.trait.UserHeaderTrait;

public final class UserReportsPage extends ReportsPage<UserReportsPage> implements
        UserHeaderTrait<UserReportsPage>,
        SelectCompanyTrait<UserReportsPage>,
        SelectBusinessUnitTrait<UserReportsPage>,
        SelectDateRangeTrait<UserReportsPage>,
        ReportsTableTrait<UserReportsPage> {

    public UserReportsPage(Page page) {
        super(page);
    }

    @Override
    protected UserReportsPage getReturnPage() {
        return this;
    }
}
