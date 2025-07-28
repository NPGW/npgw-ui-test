package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.SuperDashboardPage;
import xyz.npgw.test.page.SuperReportsPage;
import xyz.npgw.test.page.SuperTransactionsPage;
import xyz.npgw.test.page.base.BaseComponent;
import xyz.npgw.test.page.system.SuperTeamPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SuperHeaderComponent<CurrentPageT> extends BaseComponent {

    @Getter
    private final Locator logoImg = getPage().getByAltText("logo");
    private final Locator logo = getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHas(logoImg));
    private final Locator dashboardButton = getByRole(AriaRole.LINK, "Dashboard");
    private final Locator transactionsButton = getByRole(AriaRole.LINK, "Transactions");
    private final Locator reportsButton = getByRole(AriaRole.LINK, "Reports");
    private final Locator logOutButton = getByRole(AriaRole.BUTTON, "Log out");
    private final Locator userMenuButton = getByTestId("userMenuToggle");
    private final Locator profileSettingsButton = getByTextExact("Profile Settings");
    private final Locator logOutButtonInUserMenu = getByRole(AriaRole.MENUITEM, "Log Out");
    private final Locator lightRadioButtonInUserMenu = getByRoleExact(AriaRole.RADIO, "Light");
    private final Locator darkRadioButtonInUserMenu = getByRoleExact(AriaRole.RADIO, "Dark");
    private final CurrentPageT currentPage;

    public SuperHeaderComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.currentPage = currentPage;
    }

    @Step("Click on 'Transactions' menu in Header")
    public SuperTransactionsPage clickTransactionsLink() {
        transactionsButton.click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();
        assertThat(transactionsButton.locator("..")).hasAttribute("data-active", "true");

        return new SuperTransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public SuperReportsPage clickReportsLink() {
        reportsButton.click();
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

        return currentPage;
    }

//    @Step("Click 'Profile Settings' button")
//    public CurrentPageT clickProfileSettingsButton() {
//        profileSettingsButton.click();
//
//        return new ProfileSettingsDialog<>(getPage(), (ReturnPageT) currentPage);
//    }

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

        return currentPage;
    }

    @Step("Click the 'Dark' radio button in the user menu")
    public CurrentPageT clickDarkRadioButton() {
        darkRadioButtonInUserMenu.click();

        return currentPage;
    }

    public boolean isLogoImageLoaded() {
        return (boolean) getLogoImg().evaluate(
                "img => img.complete && img.naturalWidth > 0 && img.naturalHeight > 0"
                        + " && !img.src.includes('base64') && !img.src.endsWith('.svg') && !img.src.endsWith('.ico')");
    }
}
