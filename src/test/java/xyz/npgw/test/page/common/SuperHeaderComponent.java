package xyz.npgw.test.page.common;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import xyz.npgw.test.page.SuperDashboardPage;
import xyz.npgw.test.page.SuperReportsPage;
import xyz.npgw.test.page.SuperTransactionsPage;
import xyz.npgw.test.page.system.SuperTeamPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
        getDashboardButton().click();
        assertThat(getDashboardButton().locator("..")).hasAttribute("data-active", "true");

        return new SuperDashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    public SuperTransactionsPage clickTransactionsLink() {
        getTransactionsButton().click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();
        assertThat(getTransactionsButton().locator("..")).hasAttribute("data-active", "true");

        return new SuperTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public SuperReportsPage clickReportsLink() {
        getReportsButton().click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();

        return new SuperReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public SuperTeamPage clickSystemAdministrationLink() {
        getByRole(AriaRole.LINK, "System administration").click();

        //        getPage().waitForCondition(() -> LocalTime.now().isAfter(THREAD_LAST_ACTIVITY.get()));
        assertThat(getByRole(AriaRole.LINK, "System administration").locator("..")).hasAttribute("data-active", "true");
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new SuperTeamPage(getPage());
    }
}
