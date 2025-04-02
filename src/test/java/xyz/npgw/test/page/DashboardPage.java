package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BaseHeaderPage;
import xyz.npgw.test.page.component.FilterBlock;

public final class DashboardPage extends BaseHeaderPage {

    private final FilterBlock filterBlock;

    public DashboardPage(Page page) {
        super(page);
        filterBlock = new FilterBlock(page);
    }
}
