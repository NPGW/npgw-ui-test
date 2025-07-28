package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;
import xyz.npgw.test.page.common.trait.SuperHeaderTrait;

public final class SuperDashboardPage extends DashboardPage<SuperDashboardPage> implements
        SuperHeaderTrait<SuperDashboardPage>,
        SelectCompanyTrait<SuperDashboardPage>,
        SelectBusinessUnitTrait<SuperDashboardPage>,
        SelectDateRangeTrait<SuperDashboardPage>,
        AlertTrait<SuperDashboardPage> {

    public SuperDashboardPage(Page page) {
        super(page);
    }

    @Override
    protected SuperDashboardPage getReturnPage() {
        return this;
    }
}
