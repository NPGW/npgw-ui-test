package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.system.AcquirersPage;

public class AcquirersTableComponent extends BaseTableComponent<AcquirersPage> {
    public AcquirersTableComponent(Page page) {
        super(page);
    }

    @Override
    protected AcquirersPage getCurrentPage() {

        return new AcquirersPage(getPage());
    }
}
