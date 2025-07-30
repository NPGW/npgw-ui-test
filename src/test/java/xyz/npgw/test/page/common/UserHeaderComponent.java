package xyz.npgw.test.page.common;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.UserDashboardPage;
import xyz.npgw.test.page.UserReportsPage;
import xyz.npgw.test.page.UserTransactionsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

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
        getDashboardButton().click();
        assertThat(getDashboardButton().locator("..")).hasAttribute("data-active", "true");

        return new UserDashboardPage(getPage());
    }

    @Step("Click on 'Transactions' menu in Header")
    public UserTransactionsPage clickTransactionsLink() {
        getTransactionsButton().click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();
        assertThat(getTransactionsButton().locator("..")).hasAttribute("data-active", "true");

        return new UserTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public UserReportsPage clickReportsLink() {
        getReportsButton().click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();

        return new UserReportsPage(getPage());
    }
}
