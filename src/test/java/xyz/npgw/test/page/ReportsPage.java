package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BasePage;

public class ReportsPage extends BasePage {
    private final Header header;

    public ReportsPage(Page page) {
        super(page);
        this.header = new Header(getPage());
        header.clickReports();
    }

    public Header getHeader() {
        return header;
    }
}
