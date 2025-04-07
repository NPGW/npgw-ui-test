package xyz.npgw.test.page.component;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.ReportsPage;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.base.BaseComponent;
import xyz.npgw.test.page.systemadministration.TeamPage;

public class HeaderComponent extends BaseComponent {

    public HeaderComponent(Page page) {
        super(page);
    }

    @Step("Click on 'Transactions' in the Header navigation menu")
    public TransactionsPage clickTransactionsLink() {
        getLinkByText("Transactions").click();

        return new TransactionsPage(getPage());
    }

    @Step("Click on 'Reports' in the Header navigation menu")
    public ReportsPage clickReportsLink() {
        getLinkByText("Reports").click();

        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' in the Header navigation menu")
    public TeamPage clickSystemAdministrationLink() {
        getLinkByText("System administration").click();
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new TeamPage(getPage());
    }

    @Step("Press 'Log out' button on the Header")
    public LoginPage clickLogOutButton() {
        getButtonByName("Log out").click();

        return new LoginPage(getPage());
    }
}
