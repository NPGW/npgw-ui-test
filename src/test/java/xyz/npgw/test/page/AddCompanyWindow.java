package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BasePage;

public class AddCompanyWindow extends BasePage {

    private final Locator headerAddCompany = getPage().locator("text=Add company");

    public AddCompanyWindow(Page page) {
        super(page);
    }

    public Locator getHeader() {
        return headerAddCompany;
    }
}
