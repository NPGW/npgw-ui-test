package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.AdminHeaderTrait;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;

public final class AdminDashboardPage extends DashboardPage<AdminDashboardPage> implements
        AdminHeaderTrait<AdminDashboardPage>,
        SelectBusinessUnitTrait<AdminDashboardPage>,
        SelectDateRangeTrait<AdminDashboardPage>,
        AlertTrait<AdminDashboardPage> {

    public AdminDashboardPage(Page page) {
        super(page);
    }

    @Override
    protected AdminDashboardPage getReturnPage() {
        return this;
    }
}
