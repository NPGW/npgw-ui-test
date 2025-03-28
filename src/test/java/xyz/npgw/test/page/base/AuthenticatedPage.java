package xyz.npgw.test.page.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.ReportsPage;
import xyz.npgw.test.page.SystemAdministrationPage;
import xyz.npgw.test.page.TransactionsPage;

public abstract class AuthenticatedPage extends BasePage {

    private final Locator dashboardLink = link("Dashboard");
    private final Locator transactionLink = link("Transactions");
    private final Locator reportsLink = link("Reports");
    private final Locator systemAdministrationLink = link("System administration");
    private final Locator logOutButton = button("Log out");

    public AuthenticatedPage(Page page) {
        super(page);
    }

    @Step("Click on 'Dashboard' menu in Header")
    public DashboardPage clickDashboardLink() {
        dashboardLink.click();

        return new DashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    public TransactionsPage clickTransactionsLink() {
        transactionLink.click();

        return new TransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public ReportsPage clickReportsLink() {
        reportsLink.click();

        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public SystemAdministrationPage clickSystemAdministrationLink() {
        systemAdministrationLink.click();

        return new SystemAdministrationPage(getPage());
    }

    @Step("Press 'Log out' button")
    public LoginPage clickLogOutButton() {
        logOutButton.click();

        return new LoginPage(getPage());
    }
}
