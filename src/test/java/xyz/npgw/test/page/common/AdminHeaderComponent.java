package xyz.npgw.test.page.common;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.AdminDashboardPage;
import xyz.npgw.test.page.AdminReportsPage;
import xyz.npgw.test.page.AdminTransactionsPage;
import xyz.npgw.test.page.system.AdminTeamPage;

public class AdminHeaderComponent<CurrentPageT> extends HeaderComponent<CurrentPageT> {

    public AdminHeaderComponent(Page page, CurrentPageT currentPage) {
        super(page, currentPage);
    }

    @Step("Click 'Logo' button")
    public AdminDashboardPage clickLogoButton() {
        getLogo().click();

        return new AdminDashboardPage(getPage());
    }

    @Step("Click on 'Dashboard' menu in Header")
    public AdminDashboardPage clickDashboardLink() {
        clickDashboard();

        return new AdminDashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    public AdminTransactionsPage clickTransactionsLink() {
        clickTransactions();

        return new AdminTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public AdminReportsPage clickReportsLink() {
        clickReports();

        return new AdminReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public AdminTeamPage clickSystemAdministrationLink() {
        clickSystemAdministration();

        return new AdminTeamPage(getPage());
    }
}
