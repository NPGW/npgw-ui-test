package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
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
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.dialog.ProfileSettingsDialog;
import xyz.npgw.test.page.system.TeamPage;

@Getter
public class HeaderComponent<CurrentPageT extends HeaderPage<CurrentPageT>> extends BaseComponent {

    private final CurrentPageT currentPage;

    private final Locator img = getPage().getByAltText("logo");
    private final Locator logo = getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHas(img));
    private final Locator transactionsButton = getByRole(AriaRole.LINK, "Transactions");
    private final Locator reportsButton = getByRole(AriaRole.LINK, "Reports");
    private final Locator systemAdministrationButton = getByRole(AriaRole.LINK, "System administration");
    private final Locator logOutButton = getByTextExact("Log out");
    private final Locator userMenuButton = getByTestId("userMenuToggle");
    private final Locator profileSettingsButton = getByTextExact("Profile Settings");
    private final Locator logOutButtonUserMenu = getByRole(AriaRole.MENUITEM, "Log Out");
    private final Locator lightRadioButtonInUserMenu = getByRoleExact(AriaRole.RADIO, "Light");
    private final Locator darkRadioButtonInUserMenu = getByRoleExact(AriaRole.RADIO, "Dark");

    public HeaderComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.currentPage = currentPage;
    }

    @Step("Click on 'Transactions' menu in Header")
    public TransactionsPage clickTransactionsLink() {
        ResponseUtils.clickAndWaitForResponse(getPage(), transactionsButton, Constants.TRANSACTION_HISTORY_ENDPOINT);

        return new TransactionsPage(getPage());
    }

    @Step("Click on 'Reports' menu in Header")
    public ReportsPage clickReportsLink() {
        reportsButton.click();

        return new ReportsPage(getPage());
    }

    @Step("Click on 'System administration' menu in Header")
    public TeamPage clickSystemAdministrationLink() {
        ResponseUtils.clickAndWaitForResponse(getPage(), systemAdministrationButton, Constants.ASSETS_TEAM);

        return new TeamPage(getPage());
    }

    @Step("Press 'Log out' button")
    public LoginPage clickLogOutButton() {
        logOutButton.click();

        return new LoginPage(getPage());
    }

    @Step("Press 'Logo' button")
    public DashboardPage clickLogoButton() {
        logo.click();

        return new DashboardPage(getPage());
    }

    @Step("Press 'User menu' button")
    public CurrentPageT clickUserMenuButton() {
        getPage().waitForLoadState(LoadState.NETWORKIDLE);
        userMenuButton.click();

        return currentPage;
    }

    @Step("Press 'Profile Settings' button")
    public ProfileSettingsDialog<CurrentPageT> clickProfileSettingsButton() {
        profileSettingsButton.click();

        return new ProfileSettingsDialog<>(getPage(), currentPage);
    }

    @Step("Press 'Log out' button in User menu")
    public LoginPage clickLogOutButtonUserMenu() {
        getPage().waitForLoadState(LoadState.NETWORKIDLE);
        logOutButtonUserMenu.click();
        getPage().waitForURL("**/");

        return new LoginPage(getPage());
    }

    @Step("Click the 'Light' radio button in the user menu")
    public CurrentPageT clickLightRadioButton() {
        lightRadioButtonInUserMenu.click();

        return currentPage;
    }

    @Step ("Click the 'Dark' radio button in the user menu")
    public CurrentPageT clickDarkRadioButton() {
        darkRadioButtonInUserMenu.click();

        return currentPage;
    }

    public boolean isLogoImageLoaded() {
        return (boolean) getImg().evaluate(
                "img => img.complete && img.naturalWidth > 0 && img.naturalHeight > 0"
                        + " && !img.src.includes('base64') && !img.src.endsWith('.svg') && !img.src.endsWith('.ico')");
    }
}
