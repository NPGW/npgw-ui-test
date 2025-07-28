package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BaseComponent;
import xyz.npgw.test.page.system.AdminBusinessUnitsPage;
import xyz.npgw.test.page.system.AdminTeamPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AdminMenuComponent extends BaseComponent {

    private final Locator teamTab = getByRole(AriaRole.TAB, "Team");
    private final Locator businessUnitsTab = getByRole(AriaRole.TAB, "Business units");

    public AdminMenuComponent(Page page) {
        super(page);
    }

    @Step("Click 'Team' tab")
    public AdminTeamPage clickTeamTab() {
        teamTab.click();
        assertThat(teamTab).hasAttribute("data-selected", "true");

        return new AdminTeamPage(getPage());
    }

    @Step("Click 'Business units' tab")
    public AdminBusinessUnitsPage clickBusinessUnitsTab() {
        businessUnitsTab.click();
        assertThat(businessUnitsTab).hasAttribute("data-selected", "true");

        return new AdminBusinessUnitsPage(getPage());
    }
}
