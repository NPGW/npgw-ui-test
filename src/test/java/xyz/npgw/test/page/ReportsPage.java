package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BaseHeaderPage;
import xyz.npgw.test.page.component.ContentBlock;

public class ReportsPage extends BaseHeaderPage {

    private final ContentBlock table;

    public ReportsPage(Page page) {
        super(page);
        table = new ContentBlock(page);
    }
}
