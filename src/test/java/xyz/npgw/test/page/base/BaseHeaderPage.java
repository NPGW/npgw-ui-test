package xyz.npgw.test.page.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.ReportsPage;
import xyz.npgw.test.page.SystemAdministrationPage;
import xyz.npgw.test.page.TransactionsPage;

public abstract class BaseHeaderPage extends BasePage {

    public BaseHeaderPage(Page page) {
        super(page);
    }

    @Step("Click on 'Dashboard' in the Navigation menu")
    public DashboardPage clickOnDashboard() {
        linkByName("Dashboard").click();

        return new DashboardPage(getPage());
    }

    @Step("Click on 'Transactions' in the Navigation menu")
    public TransactionsPage clickOnTransactions() {
        linkByName("Transactions").click();

        return new TransactionsPage(getPage());
    }

    @Step("Click on 'Reports' in the Navigation menu")
    public ReportsPage clickOnReports() {
        linkByName("Reports").click();

        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' in the Navigation menu")
    public SystemAdministrationPage clickOnSystemAdministration() {
        linkByName("System administration").click();
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new SystemAdministrationPage(getPage());
    }

    @Step("Press 'Log out' button")
    public LoginPage clickOnLogOutButton() {
        button("Log out").click();

        return new LoginPage(getPage());
    }


}
