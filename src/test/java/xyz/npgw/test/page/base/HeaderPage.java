package xyz.npgw.test.page.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.ReportsPage;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.dashboard.AdminDashboardPage;
import xyz.npgw.test.page.dashboard.SuperDashboardPage;
import xyz.npgw.test.page.dashboard.UserDashboardPage;
import xyz.npgw.test.page.dialog.ProfileSettingsDialog;
import xyz.npgw.test.page.system.TeamPage;
import xyz.npgw.test.page.transactions.AdminTransactionsPage;
import xyz.npgw.test.page.transactions.SuperTransactionsPage;
import xyz.npgw.test.page.transactions.UserTransactionsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
@SuppressWarnings("unchecked")
public abstract class HeaderPage<CurrentPageT extends HeaderPage<CurrentPageT>> extends BasePage
        implements AlertTrait<CurrentPageT> {

    private final Locator logoImg = getPage().getByAltText("logo");
    private final Locator logo = getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHas(logoImg));
    private final Locator dashboardButton = getByRole(AriaRole.LINK, "Dashboard");
    private final Locator transactionsButton = getByRole(AriaRole.LINK, "Transactions");
    private final Locator reportsButton = getByRole(AriaRole.LINK, "Reports");
    private final Locator systemAdministrationButton = getByRole(AriaRole.LINK, "System administration");
    private final Locator logOutButton = getByRole(AriaRole.BUTTON, "Log out");
    private final Locator userMenuButton = getByTestId("userMenuToggle");
    private final Locator profileSettingsButton = getByTextExact("Profile Settings");
    private final Locator logOutButtonInUserMenu = getByRole(AriaRole.MENUITEM, "Log Out");
    private final Locator lightRadioButtonInUserMenu = getByRoleExact(AriaRole.RADIO, "Light");
    private final Locator darkRadioButtonInUserMenu = getByRoleExact(AriaRole.RADIO, "Dark");

    public HeaderPage(Page page) {
        super(page);
    }

    protected CurrentPageT self() {
        return (CurrentPageT) this;
    }

    @Step("Click on 'Dashboard' in the Header")
    public SuperDashboardPage clickDashboardLinkAsSuper() {
        dashboardButton.click();

        return new SuperDashboardPage(getPage());
    }

    @Step("Click on 'Dashboard' in the Header")
    public AdminDashboardPage clickDashboardLinkAsAdmin() {
        dashboardButton.click();

        return new AdminDashboardPage(getPage());
    }

    @Step("Click on 'Dashboard' in the Header")
    public UserDashboardPage clickDashboardLinkAsUser() {
        dashboardButton.click();

        return new UserDashboardPage(getPage());
    }

    @Step("Click on 'Transactions' in the Header")
    public SuperTransactionsPage clickTransactionsLinkAsSuper() {
        clickAndWaitForTable(transactionsButton);

        return new SuperTransactionsPage(getPage());
    }

    @Step("Click on 'Transactions' in the Header")
    public AdminTransactionsPage clickTransactionsLinkAsAdmin() {
        clickAndWaitForTable(transactionsButton);

        return new AdminTransactionsPage(getPage());
    }

    @Step("Click on 'Transactions' in the Header")
    public UserTransactionsPage clickTransactionsLinkAsUser() {
        clickAndWaitForTable(transactionsButton);

        return new UserTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' in the Header")
    public ReportsPage clickReportsLink() {
        clickAndWaitForTable(reportsButton);

        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' in the Header")
    public TeamPage clickSystemAdministrationLinkAsSuper() {
        clickAndWaitForTable(systemAdministrationButton);
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new TeamPage(getPage());
    }

    @Step("Click on 'System administration' in the Header")
    public TeamPage clickSystemAdministrationLinkAsAdmin() {
        clickAndWaitForTable(systemAdministrationButton);
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        return new TeamPage(getPage());
    }

    @Step("Click 'Log out' button")
    public LoginPage clickLogOutButton() {
        logOutButton.click();

        return new LoginPage(getPage());
    }

    @Step("Click 'Logo' button")
    public SuperDashboardPage clickLogoButton() {
        logo.click();

        return new SuperDashboardPage(getPage());
    }

    @Step("Click 'User menu' button")
    public CurrentPageT clickUserMenuButton() {
        getPage().waitForLoadState(LoadState.NETWORKIDLE);
        userMenuButton.click();

        return self();
    }

    @Step("Click 'Profile Settings' button")
    public ProfileSettingsDialog<CurrentPageT> clickProfileSettingsButton() {
        profileSettingsButton.click();

        return new ProfileSettingsDialog<>(getPage(), (CurrentPageT) this);
    }

    @Step("Click 'Log out' button in User menu")
    public LoginPage clickLogOutButtonUserMenu() {
        getPage().waitForLoadState(LoadState.NETWORKIDLE);
        logOutButtonInUserMenu.click();
        getPage().waitForURL("**/");

        return new LoginPage(getPage());
    }

    @Step("Click the 'Light' radio button in the user menu")
    public CurrentPageT clickLightRadioButton() {
        lightRadioButtonInUserMenu.click();

        return self();
    }

    @Step("Click the 'Dark' radio button in the user menu")
    public CurrentPageT clickDarkRadioButton() {
        darkRadioButtonInUserMenu.click();

        return self();
    }

    public boolean isLogoImageLoaded() {
        return (boolean) getLogoImg().evaluate(
                "img => img.complete && img.naturalWidth > 0 && img.naturalHeight > 0"
                + " && !img.src.includes('base64') && !img.src.endsWith('.svg') && !img.src.endsWith('.ico')");
    }

    private void clickAndWaitForTable(Locator button) {
        button.click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();

        assertThat(button.locator("..")).hasAttribute("data-active", "true");
    }
}
