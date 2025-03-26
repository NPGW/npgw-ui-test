package xyz.npgw.test.page.base;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.Header;

public class AuthenticatedPage extends BasePage{

    private final Header header;
    public AuthenticatedPage(Page page) {
        super(page);
        this.header = new Header(page);
    }
    public Header getHeader() {
        return header;
    }
}
