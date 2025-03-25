package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BasePage;

public class TransactionsPage extends BasePage {
    private final Header header;

    public TransactionsPage(Page page) {
        super(page);
        this.header = new Header(getPage());
    }

    public Header getHeader() {
        return header;
    }
}
