package xyz.npgw.test.page.base;

import com.microsoft.playwright.Page;

public abstract class BasePageWithHeader extends BasePage implements Header {

    public BasePageWithHeader(Page page) {
        super(page);
    }

//    @Step("Click Dashboard Link")
//    public DashboardPage clickDashboardLink() {
//        return header.clickDashboardLink();
//    }
//
//    @Step("Click System Administration Link")
//    public SystemAdministrationPage clickSystemAdministrationLink() {
//        return header.clickSystemAdministrationLink();
//    }
//
//    @Step("Click Reports Link")
//    public ReportsPage clickReportsLink() {
//        return header.clickReportsLink();
//    }
//
//    @Step("Click Transactions Link")
//    public TransactionsPage clickTransactionsLink() {
//        return header.clickTransactionsLink();
//    }
//
//    @Step("Click Logout Link")
//    public LoginPage clickLogOutButton() {
//        return header.clickLogOutButton();
//    }
}
