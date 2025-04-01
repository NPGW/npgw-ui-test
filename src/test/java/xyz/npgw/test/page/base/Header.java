package xyz.npgw.test.page.base;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.ReportsPage;
import xyz.npgw.test.page.SystemAdministrationPage;
import xyz.npgw.test.page.TransactionsPage;

public interface Header extends PageElements {

    Page getPage();

    @Step("Click on 'Dashboard' menu in Header")
    default DashboardPage clickDashboardLink() {
        linkByName("Dashboard").click();

        return new DashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    default TransactionsPage clickTransactionsLink() {
        linkByName("Transactions").click();

        return new TransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    default ReportsPage clickReportsLink() {
        linkByName("Reports").click();

        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    default SystemAdministrationPage clickSystemAdministrationLink() {
        linkByName("System administration").click();
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new SystemAdministrationPage(getPage());
    }

    @Step("Press 'Log out' button")
    default LoginPage clickLogOutButton() {
        button("Log out").click();

        return new LoginPage(getPage());
    }
}

