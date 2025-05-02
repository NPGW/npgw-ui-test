package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.common.TableTrait;
import xyz.npgw.test.page.dialog.acquirer.AddAcquirerDialog;
import xyz.npgw.test.page.dialog.acquirer.EditAcquirerDialog;

import java.util.List;
import java.util.Objects;

public class AcquirersPage extends BaseSystemPage<AcquirersPage> implements TableTrait {

    @Getter
    private final Locator addAcquirerButton = locator("svg[data-icon='circle-plus']");
    @Getter
    private final Locator addAcquirerDialog = dialog();
    @Getter
    private final Locator resetFilterButton = locator("svg[data-icon='xmark']");
    @Getter
    private final Locator refreshDataButton = getByTestId("ApplyFilterButtonAcquirersPage");
    @Getter
    private final Locator acquirerNameHeader = textExact("Acquirer name");
    private final Locator acquirersList = locator("div[data-slot='base'] li");
    private final Locator acquirersStatus = locator("span.flex-1.text-inherit");
    @Getter
    private final Locator rowsPerPage = locator("button[aria-label='Rows Per Page']");
    @Getter
    private final Locator rowsPerPageDropdown = locator("div[data-slot='listbox']");
    @Getter
    private final Locator paginationItems = label("pagination item");
    @Getter
    private final Locator paginationNext = labelExact("next page button");
    @Getter
    private final Locator selectAcquirerLabel = labelExact("Select acquirer");
    private final Locator selectAcquirerPlaceholder = placeholder("Search");
    private final Locator dropdownAcquirerList = locator("div[data-slot='content'] li");
    @Getter
    private final Locator statusLabel = labelExact("Status");
    @Getter
    private final Locator acquirerStatusValue = locator("div[data-slot='innerWrapper'] span").first();
    @Getter
    private final Locator acquirerStatusDropdown = locator("div[data-slot='listbox']");
    @Getter
    private final Locator acquirerStatusOptions = option(acquirerStatusDropdown);

    public AcquirersPage(Page page) {
        super(page);
        selectAcquirerLabel.waitFor();
    }

    public Locator getAcquirersList() {
        getPage().waitForTimeout(1000);

        return acquirersList;
    }

    public List<Locator> getAcquirersStatus() {

        return acquirersStatus.all();
    }

    @Step("Click 'Select acquirer' placeholder")
    public AcquirersPage clickSelectAcquirerPlaceholder() {
        selectAcquirerLabel.waitFor();
        selectAcquirerPlaceholder.click();

        return this;
    }

    @Step("Enter '{acquirerName}' into select acquirer placeholder")
    public AcquirersPage enterAcquirerName(String acquirerName) {
        selectAcquirerLabel.waitFor();
        selectAcquirerPlaceholder.pressSequentially(acquirerName, new Locator.PressSequentiallyOptions().setDelay(100));

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
                .getByText(status, new Locator.GetByTextOptions().setExact(true))
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

    @Step("Click the 'Rows Per Page' dropdown Chevron")
    public AcquirersPage clickRowsPerPageChevron() {
        rowsPerPage.locator("svg").click();

        return this;
    }

    public Locator getRowsPerPageOptions() {
        return rowsPerPageDropdown.locator("li");
    }

    @Step("Select Rows Per Page '{option}'")
    public AcquirersPage selectRowsPerPageOption(String option) {
        rowsPerPageDropdown.getByText(option, new Locator.GetByTextOptions().setExact(true)).click();
        getTable().getTableRows().last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return this;
    }

    @Step("Click on page '{pageNumber}'")
    public AcquirersPage clickOnPaginationPage(String pageNumber) {
        label("pagination item " + pageNumber).click();

        return this;
    }

    @Step("Click next page")
    public AcquirersPage clickNextPage() {
        paginationNext.click();

        return this;
    }

    public boolean isLastPage() {
        return Objects.equals(paginationNext.getAttribute("tabindex"), "-1");
    }
}
