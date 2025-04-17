package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.util.ResponseUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.ReportsPage;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.base.BaseComponent;
import xyz.npgw.test.page.system.TeamPage;

@Getter
public class HeaderComponent extends BaseComponent {

    private final Locator img = altText("logo");
    private final Locator logo = link().filter(new Locator.FilterOptions().setHas(img));
    private final Locator transactionsLink = linkByName("Transactions");

    public HeaderComponent(Page page) {
        super(page);
    }

    @Step("Click on 'Transactions' menu in Header")
    public TransactionsPage clickTransactionsLink() {
        ResponseUtils.clickAndWaitForResponse(getPage(), transactionsLink, Constants.TRANSACTION_HISTORY_ENDPOINT);

        return new TransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public ReportsPage clickReportsLink() {
        linkByName("Reports").click();

        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public TeamPage clickSystemAdministrationLink() {
        linkByName("System administration").click();
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new TeamPage(getPage());
    }

    @Step("Press 'Log out' button")
    public LoginPage clickLogOutButton() {
        buttonByName("Log out").click();

        return new LoginPage(getPage());
    }

    public DashboardPage clickLogoButton() {
        logo.click();

        return new DashboardPage(getPage());
    }
}
