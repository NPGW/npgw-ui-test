package xyz.npgw.test.page;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePageWithHeader;
import xyz.npgw.test.page.component.FilterBlock;

public final class DashboardPage extends BasePageWithHeader {

    private final FilterBlock filterBlock;

    public DashboardPage(Page page) {
        super(page);
        filterBlock = new FilterBlock(page);
    }

    @Step("Press 'Log out' button")
    public LoginPage clickLogOutButton() {
        return getHeader().clickLogOutButton();
    }

    @Step("Click Transactions Link")
    public TransactionsPage clickTransactionsLink() {
        linkByName("Transactions").click();
        return new TransactionsPage(getPage());
    }
}
