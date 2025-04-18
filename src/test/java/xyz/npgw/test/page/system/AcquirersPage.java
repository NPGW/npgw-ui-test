package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.acquirer.AddAcquirerDialog;
import xyz.npgw.test.page.dialog.acquirer.EditAcquirerDialog;

public class AcquirersPage extends BaseSystemPage {

    @Getter
    private final Locator addAcquirerButton = locator("svg[data-icon='circle-plus']");
    @Getter
    private final Locator addAcquirerDialog = dialog();
    @Getter
    private final Locator resetFilterButton = locator("svg[data-icon='xmark']");
    @Getter
    private final Locator applyFilterButton = locator("svg[data-icon='filter']");

    @Getter
    private final Locator acquirersListHeader = textExact("Acquirers list");
    private final Locator acquirersList = locator("div[data-slot='base'] li");

    @Getter
    private final Locator selectAcquirerLabel = labelExact("Select acquirer");
    private final Locator selectAcquirerPlaceholder = placeholder("Search");
    private final Locator dropdownAcquirerList = locator("div[data-slot='content'] li");

    @Getter
    private final Locator statusLabel = labelExact("Status");
    @Getter
    private final Locator acquirerStatusValue = locator("div[data-slot='innerWrapper'] span");
    @Getter
    private final Locator acquirerStatusDropdown = locator("div[data-slot='listbox']");
    @Getter
    private final Locator acquirerStatusOptions = option(acquirerStatusDropdown);

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

    @Step("Click Status placeholder")
    public AcquirersPage clickAcquirerStatusPlaceholder() {
        selectAcquirerLabel.waitFor();
        acquirerStatusValue.click();

        return this;
    }

    @Step("Select Acquirer Status '{status}'")
    public AcquirersPage selectAcquirerStatus(String status) {
        acquirerStatusOptions
                .filter(new Locator.FilterOptions().setHasText(status))
                .click();
        acquirerStatusDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        return this;
    }

    @Step("Click Add Acquirer")
    public AddAcquirerDialog clickAddAcquirer() {
        addAcquirerButton.click();

        return new AddAcquirerDialog(getPage());
    }

    @Step("Click 'Edit' button to edit acquirer")
    public EditAcquirerDialog clickEditButtonForAcquirer(String name) {
        optionByName(name).getByText("Edit").click();

        return new EditAcquirerDialog(getPage());
    }
}
