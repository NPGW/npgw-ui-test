package xyz.npgw.test.page.common;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import xyz.npgw.test.page.AdminDashboardPage;
import xyz.npgw.test.page.AdminReportsPage;
import xyz.npgw.test.page.AdminTransactionsPage;
import xyz.npgw.test.page.system.AdminTeamPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
        getDashboardButton().click();
        assertThat(getDashboardButton().locator("..")).hasAttribute("data-active", "true");

        return new AdminDashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    public AdminTransactionsPage clickTransactionsLink() {
        getTransactionsButton().click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();
        assertThat(getTransactionsButton().locator("..")).hasAttribute("data-active", "true");

        return new AdminTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public AdminReportsPage clickReportsLink() {
        getReportsButton().click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();

        return new AdminReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public AdminTeamPage clickSystemAdministrationLink() {
        getByRole(AriaRole.LINK, "System administration").click();

        //        getPage().waitForCondition(() -> LocalTime.now().isAfter(THREAD_LAST_ACTIVITY.get()));
        assertThat(getByRole(AriaRole.LINK, "System administration").locator("..")).hasAttribute("data-active", "true");
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new AdminTeamPage(getPage());
    }
}
