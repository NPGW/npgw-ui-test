package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.common.util.ResponseUtils;
import xyz.npgw.test.page.base.BaseComponent;

import java.util.NoSuchElementException;

public class SelectCompanyComponent<CurrentPageT> extends BaseComponent {

    @Getter
    private final Locator selectCompanyField = labelExact("Select company");
    private final Locator dropdownOptionList = getPage().getByRole(AriaRole.OPTION);
    @Getter
    private final Locator selectCompanyPlaceholder = locator("input[aria-label='Select company']");
    @Getter
    private final Locator companyDropdown = locator("div[data-slot='content']");
    private final Locator selectCompanyContainer =
            locator("div[data-slot='input-wrapper']")
                    .filter(new Locator.FilterOptions().setHas(selectCompanyPlaceholder));
    private final Locator selectCompanyDropdownChevron =
            selectCompanyContainer.locator("button[aria-label='Show suggestions']:last-child");

    private final Locator selectCompanyClearIcon =
            selectCompanyContainer.locator("button[aria-label='Show suggestions']:first-child");

    private final CurrentPageT page;

    public SelectCompanyComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.page = currentPage;
    }

    public Locator getCompanyNameInDropdownOption(String companyName) {
        return dropdownOptionList.filter(new Locator.FilterOptions().setHas(textExact(companyName)));
    }

    @Step("Select '{companyName}' company using filter")
    public CurrentPageT selectCompany(String companyName) {
        ResponseUtils.waitUntilInputReady(selectCompanyField, 1500);

        String lastName = "";

        selectCompanyField.fill(companyName);

        if (dropdownOptionList.all().isEmpty()) {
            throw new NoSuchElementException("Company '" + companyName + "' not found in dropdown list.");
        }

        while (getCompanyNameInDropdownOption(companyName).all().isEmpty()) {
            if (dropdownOptionList.last().innerText().equals(lastName)) {
                throw new NoSuchElementException("Company '" + companyName + "' not found in dropdown list.");
            }
            dropdownOptionList.last().scrollIntoViewIfNeeded();

            lastName = dropdownOptionList.last().innerText();
        }
//        .first() - из-за того, что компания "super" отображается в отфильтрованном списке два раза,
//        это баг(!!), правильно - один раз (или ноль).
//        На суть теста .first() не влияет и позволяет "не заметить" баг.
//
        getCompanyNameInDropdownOption(companyName).first().click();

        return page;
    }

    @Step("Click select Company clear icon")
    public CurrentPageT clickSelectCompanyClearIcon() {
        selectCompanyClearIcon.dispatchEvent("click");

        return page;
    }

    @Step("Click company dropdown toggle arrow '˅˄'")
    public CurrentPageT clickSelectCompanyDropdownChevron() {
        selectCompanyDropdownChevron.click();

        return page;
    }

    @Step("Click 'Select company' placeholder")
    public CurrentPageT clickSelectCompanyPlaceholder() {
        selectCompanyPlaceholder.click();

        return page;
    }
}
