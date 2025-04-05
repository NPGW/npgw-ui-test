package xyz.npgw.test.page.systemadministration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.SystemAdministrationBasePage;

public class AcquirersPage extends SystemAdministrationBasePage {

    private final Locator addAcquirerButton = getLocatorBySelector("svg[data-icon='circle-plus']");
    private final Locator resetFilterButton = getLocatorBySelector("svg[data-icon='xmark']");
    private final Locator applyFilterButton = getLocatorBySelector("svg[data-icon='filter']");
    private final Locator selectAcquirerLabel = getLabelByExactText("Select acquirer");
    private final Locator statusLabel = getLabelByExactText("Status");
    private final Locator acquirersListHeader = getLocatorByExactText("Acquirers list");
    private final Locator acquirersList = getLocatorBySelector("div[data-slot='base'] li");
    private final Locator selectAcquirerPlaceholder = getPlaceholderByText("Search");
    private final Locator dropdownAcquirerList = getLocatorBySelector("div[data-slot='content'] li");

    public AcquirersPage(Page page) {
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
