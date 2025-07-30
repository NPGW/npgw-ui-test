package xyz.npgw.test.page.common;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.SuperDashboardPage;
import xyz.npgw.test.page.SuperReportsPage;
import xyz.npgw.test.page.SuperTransactionsPage;
import xyz.npgw.test.page.system.SuperTeamPage;

public class SuperHeaderComponent<CurrentPageT> extends HeaderComponent<CurrentPageT> {

    public SuperHeaderComponent(Page page, CurrentPageT currentPage) {
        super(page, currentPage);
    }

    @Step("Click 'Logo' button")
    public SuperDashboardPage clickLogoButton() {
        getLogo().click();

        return new SuperDashboardPage(getPage());
    }

    @Step("Click on 'Dashboard' menu in Header")
    public SuperDashboardPage clickDashboardLink() {
        clickDashboard();

        return new SuperDashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    public SuperTransactionsPage clickTransactionsLink() {
        clickTransactions();

        return new SuperTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public SuperReportsPage clickReportsLink() {
        clickReports();

        return new SuperReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public SuperTeamPage clickSystemAdministrationLink() {
        clickSystemAdministration();

        return new SuperTeamPage(getPage());
    }
}
