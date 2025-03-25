package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePage;

public class Header extends BasePage {

    private final Locator transaction = link("Transactions");
    private final Locator dashboard = link("Dashboard");
    private final Locator reports = link("Reports");
    private final Locator systemAdministration = link("System administration");

    public Header(Page page) {
        super(page);
    }

    @Step("Click on 'Dashboard' menu in Header")
    public void clickDashboard() {
        dashboard.click();
    }

    @Step("Click on 'Transactions' menu in Header")
    public TransactionsPage clickTransaction() {
        transaction.click();
        return new TransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public ReportsPage clickReports() {
        reports.click();
        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public void clickSystemAdministration() {
        systemAdministration.click();
    }
}
