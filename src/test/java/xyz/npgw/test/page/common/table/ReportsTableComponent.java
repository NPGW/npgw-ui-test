package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Page;

public class ReportsTableComponent<CurrentPageT> extends BaseTableComponent<CurrentPageT> {

    private final CurrentPageT currentPageT;

    public ReportsTableComponent(Page page, CurrentPageT currentPageT) {
        super(page);
        this.currentPageT = currentPageT;
    }

    @Override
    protected CurrentPageT getCurrentPage() {
        return currentPageT;
    }
}
