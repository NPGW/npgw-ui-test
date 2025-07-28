package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.ReportsTableTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;
import xyz.npgw.test.page.common.trait.SuperHeaderTrait;

public final class SuperReportsPage extends ReportsPage<SuperReportsPage> implements
        SuperHeaderTrait<SuperReportsPage>,
        SelectCompanyTrait<SuperReportsPage>,
        SelectBusinessUnitTrait<SuperReportsPage>,
        SelectDateRangeTrait<SuperReportsPage>,
        ReportsTableTrait<SuperReportsPage> {

    public SuperReportsPage(Page page) {
        super(page);
    }

    @Override
    protected SuperReportsPage getReturnPage() {
        return this;
    }
}
