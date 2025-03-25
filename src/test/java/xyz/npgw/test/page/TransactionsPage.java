package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BasePage;

public class TransactionsPage extends BasePage {
    Header header;

    public TransactionsPage(Page page) {
        super(page);
        this.header = new Header(getPage());
        header.clickTransaction(getPage());
    }
}
