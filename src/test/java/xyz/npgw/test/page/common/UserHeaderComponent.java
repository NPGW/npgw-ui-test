package xyz.npgw.test.page.common;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.UserDashboardPage;
import xyz.npgw.test.page.UserReportsPage;
import xyz.npgw.test.page.UserTransactionsPage;

public class UserHeaderComponent<CurrentPageT> extends HeaderComponent<CurrentPageT> {

    public UserHeaderComponent(Page page, CurrentPageT currentPage) {
        super(page, currentPage);
    }

    @Step("Click 'Logo' button")
    public UserDashboardPage clickLogoButton() {
        getLogo().click();

        return new UserDashboardPage(getPage());
    }

    @Step("Click on 'Dashboard' menu in Header")
    public UserDashboardPage clickDashboardLink() {
        clickDashboard();

        return new UserDashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    public UserTransactionsPage clickTransactionsLink() {
        clickTransactions();

        return new UserTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public UserReportsPage clickReportsLink() {
        clickReports();

        return new UserReportsPage(getPage());
    }
}
