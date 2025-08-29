package xyz.npgw.test.page.component.header;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import lombok.Getter;
import xyz.npgw.test.page.base.BaseComponent;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public abstract class BaseHeaderMenuComponent<CurrentPageT> extends BaseComponent {

    protected final CurrentPageT currentPage;

    private final Locator logoImg = getPage().getByAltText("logo");
    private final Locator logo = getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHas(logoImg));
    private final Locator dashboardButton = getByRole(AriaRole.LINK, "Dashboard");
    private final Locator transactionsButton = getByRole(AriaRole.LINK, "Transactions");

    public BaseHeaderMenuComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.currentPage = currentPage;
    }

    protected void clickAndWaitForTable(Locator button) {
        button.click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button")).waitFor();

        assertThat(button.locator("..")).hasAttribute("data-active", "true");
    }

    protected void clickTeamTabAndWaitForTable() {
        Locator button = getByRole(AriaRole.TAB, "Team");
        button.click();
        getByRole(AriaRole.GRIDCELL, "No rows to display.")
                .or(getByRole(AriaRole.BUTTON, "next page button"))
                .waitFor();
        getPage().waitForLoadState(LoadState.NETWORKIDLE);

        assertThat(button).hasAttribute("data-selected", "true");
    }
    public boolean isLogoImageLoaded() {
        return (boolean) getLogoImg().evaluate(
                "img => img.complete && img.naturalWidth > 0 && img.naturalHeight > 0"
                        + " && !img.src.includes('base64') && !img.src.endsWith('.svg') && !img.src.endsWith('.ico')");
    }
}
