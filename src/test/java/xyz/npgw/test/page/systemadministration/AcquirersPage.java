package xyz.npgw.test.page.systemadministration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.base.SystemAdministrationBasePage;

public class AcquirersPage extends SystemAdministrationBasePage {

    @Getter
    private final Locator addAcquirerButton = getLocatorBySelector("svg[data-icon='circle-plus']");
    @Getter
    private final Locator resetFilterButton = getLocatorBySelector("svg[data-icon='xmark']");
    @Getter
    private final Locator applyFilterButton = getLocatorBySelector("svg[data-icon='filter']");
    @Getter
    private final Locator selectAcquirerLabel = getLabelByExactText("Select acquirer");
    @Getter
    private final Locator statusLabel = getLabelByExactText("Status");
    @Getter
    private final Locator acquirersListHeader = getLocatorByExactText("Acquirers list");
    private final Locator acquirersList = getLocatorBySelector("div[data-slot='base'] li");
    private final Locator selectAcquirerPlaceholder = getPlaceholderByText("Search");
    private final Locator dropdownAcquirerList = getLocatorBySelector("div[data-slot='content'] li");

    public AcquirersPage(Page page) {
        super(page);
    }

    public Locator getAcquirersList() {
        getPage().waitForTimeout(1000);

        return acquirersList;
    }

    @Step("Click 'Select acquirer' placeholder")
    public AcquirersPage clickSelectAcquirerPlaceholder() {
        selectAcquirerLabel.waitFor();
        selectAcquirerPlaceholder.click();

        return this;
    }

    public Locator getSelectAcquirersDropdownItems() {
        dropdownAcquirerList.last().waitFor();

        return dropdownAcquirerList;
    }
}
