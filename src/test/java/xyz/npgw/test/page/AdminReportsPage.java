package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.AdminHeaderTrait;
import xyz.npgw.test.page.common.trait.ReportsTableTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;

public final class AdminReportsPage extends ReportsPage<AdminReportsPage> implements
        AdminHeaderTrait<AdminReportsPage>,
        SelectCompanyTrait<AdminReportsPage>,
        SelectBusinessUnitTrait<AdminReportsPage>,
        SelectDateRangeTrait<AdminReportsPage>,
        ReportsTableTrait<AdminReportsPage> {

    public AdminReportsPage(Page page) {
        super(page);
    }

    @Override
    protected AdminReportsPage getReturnPage() {
        return this;
    }
}
