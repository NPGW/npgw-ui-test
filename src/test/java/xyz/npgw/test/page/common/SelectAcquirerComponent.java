package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.Getter;
import xyz.npgw.test.page.base.BaseComponent;
import xyz.npgw.test.page.system.AcquirersPage;

public class SelectAcquirerComponent<CurrentPageT> extends BaseComponent {

    @Getter
    private final Locator selectAcquirerLabel = getByLabelExact("Select acquirer");
    @Getter(AccessLevel.NONE)
    private final Locator selectAcquirerPlaceholder = getByPlaceholder("Search");
    @Getter(AccessLevel.NONE)
    private final Locator dropdownAcquirerList = locator("div[data-slot='content'] li");

    private final CurrentPageT page;
    public SelectAcquirerComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.page = currentPage;
    }

    @Step("Click 'Select acquirer' placeholder")
    public CurrentPageT clickSelectAcquirerPlaceholder() {
        selectAcquirerPlaceholder.click();

        return page;
    }

    @Step("Enter '{acquirerName}' into select acquirer placeholder")
    public CurrentPageT typeAcquirerNameToSelectAcquirerInputField(String acquirerName) {
        selectAcquirerPlaceholder.pressSequentially(acquirerName, new Locator.PressSequentiallyOptions().setDelay(100));

        return page;
    }


    @Step("Click '{acquirerName}' in dropdown")
    public CurrentPageT clickAcquirerInDropdown(String acquirerName) {
        dropdownAcquirerList.getByText(acquirerName, new Locator.GetByTextOptions().setExact(true)).click();

        return page;
    }

    public Locator getSelectAcquirersDropdownItems() {
        dropdownAcquirerList.last().waitFor();

        return dropdownAcquirerList;
    }
}
