package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePageWithHeader;

public class SaAcquirersTab extends BasePageWithHeader {

    private final Locator addAcquirerButton  = locator("svg[data-icon='circle-plus']");
    private final Locator resetFilterButton  = locator("svg[data-icon='xmark']");
    private final Locator applyFilterButton  = locator("svg[data-icon='filter']");
    private final Locator selectAcquirerLabel  = labelExact("Select acquirer");
    private final Locator statusLabel  = labelExact("Status");
    private final Locator acquirersListHeader = textExact("Acquirers list");
    private final Locator acquirersList = locator("div[data-slot='base'] li");
    private final Locator selectAcquirerPlaceholder = placeholder("Search");
    private final Locator dropdownAcquirerList = locator("div[data-slot='content'] li");

    public SaAcquirersTab(Page page) {
        super(page);
    }

    public Locator getAddAcquirerButton() {
        return addAcquirerButton;
    }

    public Locator getResetFilterButton() {
        return resetFilterButton;
    }

    public Locator getApplyFilterButton() {
        return applyFilterButton;
    }

    public Locator getSelectAcquirerLabel() {
        return selectAcquirerLabel;
    }

    public Locator getStatusLabel() {
        return statusLabel;
    }

    public Locator getAcquirersListHeader() {
        return acquirersListHeader;
    }

    public Locator getAcquirersList() {
        getPage().waitForTimeout(1000);

        return acquirersList;
    }

    @Step("Click 'Select acquirer' placeholder")
    public SaAcquirersTab clickSelectAcquirerPlaceholder() {
        selectAcquirerPlaceholder.click();

        return this;
    }

    public Locator getSelectAcquirersDropdownItems() {
        getPage().waitForTimeout(1000);

        return dropdownAcquirerList;
    }
}
