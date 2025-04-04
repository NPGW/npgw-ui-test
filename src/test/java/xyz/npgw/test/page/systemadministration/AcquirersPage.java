package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePageWithHeader;

public class AcquirersPage extends BasePageWithHeader {

    private final Locator addAcquirerButton = locator("svg[data-icon='circle-plus']");
    private final Locator resetFilterButton = locator("svg[data-icon='xmark']");
    private final Locator applyFilterButton = locator("svg[data-icon='filter']");

    private final Locator acquirersListHeader = textExact("Acquirers list");
    private final Locator acquirersList = locator("div[data-slot='base'] li");

    private final Locator selectAcquirerLabel = labelExact("Select acquirer");
    private final Locator selectAcquirerPlaceholder = placeholder("Search");
    private final Locator dropdownAcquirerList = locator("div[data-slot='content'] li");

    private final Locator acquirerStatusLabel = labelExact("Status");
    private final Locator acquirerStatusPlaceholder = locator("div[data-slot='innerWrapper'] span");
    private final Locator dropdownAcquirerStatusList = locator("div[data-slot='listbox']");
    private final Locator acquirerStatusOptions = option(dropdownAcquirerStatusList);


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
        return acquirerStatusLabel;
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

    @Step("Click Status placeholder")
    public AcquirersPage clickAcquirerStatusPlaceholder() {
        selectAcquirerLabel.waitFor();
        acquirerStatusPlaceholder.click();

        return this;
    }

    public Locator getDropdownAcquirerStatusList() {

        return dropdownAcquirerStatusList;
    }

    public Locator getAcquirerStatusOptions() {

        return acquirerStatusOptions;
    }

}
