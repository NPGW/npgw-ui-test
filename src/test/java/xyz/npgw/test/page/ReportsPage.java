package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.AuthenticatedPage;
import xyz.npgw.test.page.component.ContentBlock;

public class ReportsPage extends AuthenticatedPage {

    private final ContentBlock table;

    public ReportsPage(Page page) {
        super(page);
        table = new ContentBlock(page);
    }
}
