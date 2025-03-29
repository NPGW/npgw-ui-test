package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.AuthenticatedPage;
import xyz.npgw.test.page.component.ContentBlock;

public class SystemAdministrationPage extends AuthenticatedPage {
    private final ContentBlock table;

    public SystemAdministrationPage(Page page) {
        super(page);
        table = new ContentBlock(page);
    }
}
