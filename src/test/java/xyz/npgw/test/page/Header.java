package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePage;

public class Header extends BasePage {

    private final Locator transaction = link("Transactions");
    private final Locator Dashboard = link("Transactions");


    public Header(Page page) {
        super(page);
    }

    @Step("Click on 'Dashboard' menu in Header")
    public void clickDashboard(Page page) {
        transaction.click();
    }

    @Step("Click on 'Transactions' menu in Header")
    public void clickTransaction(Page page) {
        transaction.click();
    }

    @Step("Click on 'Reports' menu in Header")
    public void clickReports(Page page) {
        transaction.click();
    }

    @Step("Click on 'System administration' menu in Header")
    public void clickSystemAdministration(Page page) {
        transaction.click();
    }
}
