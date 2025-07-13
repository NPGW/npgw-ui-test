package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.NoSuchElementException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Log4j2
public class SelectAcquirerComponent<CurrentPageT> extends SelectComponent<CurrentPageT> {

    @Getter
    private final Locator selectAcquirerField = locator("input[aria-label='Select acquirer']");
    private final Locator dropdownOptionList = getByRole(AriaRole.OPTION);
    private final Locator selectAcquirerContainer = locator("div[data-slot='input-wrapper']");
    private final Locator selectAcquirerDropdownChevron = selectAcquirerContainer
            .locator("button[aria-label='Show suggestions']:last-child");
    private final Locator selectAcquirerClearIcon = selectAcquirerContainer
            .locator("button[aria-label='Show suggestions']:first-child");

//    private final CurrentPageT page;

    public SelectAcquirerComponent(Page page, CurrentPageT currentPage) {
        super(page, currentPage);
//        this.page = currentPage;
    }

    @Step("Click 'Select acquirer' field")
    public CurrentPageT clickSelectAcquirerField() {
        selectAcquirerField.click();

        return currentPage;
    }

    @Step("Type '{acquirerName}' into 'Select acquirer' field")
    public CurrentPageT typeName(String acquirerName) {
        selectAcquirerField.pressSequentially(acquirerName, new Locator.PressSequentiallyOptions().setDelay(1));

        return currentPage;
    }


    @Step("Click '{acquirerName}' in dropdown")
    public CurrentPageT clickAcquirerInDropdown(String acquirerName) {
        dropdownOptionList.getByText(acquirerName, new Locator.GetByTextOptions().setExact(true)).click();

        return currentPage;
    }

    public Locator getSelectAcquirersDropdownItems() {
        dropdownOptionList.last().waitFor();

        return dropdownOptionList;
    }

    public Locator getAcquirerInDropdownOption(String acquirerName) {
        return dropdownOptionList.filter(new Locator.FilterOptions().setHas(getByTextExact(acquirerName)));
    }

    @Step("Select '{acquirerName}' acquirer using filter")
    public CurrentPageT selectAcquirer(String acquirerName) {
        select(selectAcquirerField, acquirerName);

        return currentPage;
    }

    @Step("Select '{acquirerName}' acquirer using filter")
    public CurrentPageT oldselectAcquirer(String acquirerName) {
//        getPage().waitForCondition(() -> LocalTime.now().isAfter(THREAD_LAST_ACTIVITY.get()));

        String lastName = "";

        Locator dropdownChevron = getByLabelExact("Select acquirer").locator("..//button[last()]");
        dropdownChevron.click();
        assertThat(dropdownChevron).hasAttribute("data-open", "true");

        typeName(acquirerName);

        if (dropdownOptionList.all().isEmpty()) {
            throw new NoSuchElementException("Acquirer dropdown list is empty.");
        }

        while (getAcquirerInDropdownOption(acquirerName).all().isEmpty()) {
            if (dropdownOptionList.last().innerText().equals(lastName)) {
                throw new NoSuchElementException("Acquirer '" + acquirerName + "' not found in dropdown.");
            }
            dropdownOptionList.last().scrollIntoViewIfNeeded();

            lastName = dropdownOptionList.last().innerText();
        }

        clickAcquirerInDropdown(acquirerName);

        return currentPage;
    }

    @Step("Click acquirer dropdown toggle arrow '˅˄'")
    public CurrentPageT clickAcquirerDropdownChevron() {
        selectAcquirerDropdownChevron.click();

        return currentPage;
    }

    @Step("Click select Acquirer unit clear icon")
    public CurrentPageT clickSelectAcquirerClearIcon() {
        selectAcquirerClearIcon.dispatchEvent("click");

        return currentPage;
    }

    public boolean isAcquirerAbsent(String acquirerName) {
        return !getAllOptions(selectAcquirerField, acquirerName).contains(acquirerName);
    }
}
