package xyz.npgw.test.page.common;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BaseComponent;

public abstract class SelectComponent<CurrentPageT> extends BaseComponent {

    protected final CurrentPageT currentPage;

    public SelectComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.currentPage = currentPage;
    }

//    @Step("Select '{companyName}' company using filter")
//    public CurrentPageT select(String name) {
//
//        String lastName = "";
//
//        selectCompanyDropdownChevron.click();
//        assertThat(selectCompanyDropdownChevron).hasAttribute("data-open","true");
//
//        companyDropdown.waitFor();
//
//        selectCompanyField.fill(name);
//
//        if (locator("div[data-slot='empty-content']").isVisible()) {
//            throw new NoSuchElementException("Company '" + name + "' not found. Dropdown list is empty.");
//        }
//
//        while (getCompanyInDropdown(name).all().isEmpty()) {
//            if (dropdownOptionList.last().innerText().equals(lastName)) {
//                throw new NoSuchElementException("Company '" + name + "' not found in dropdown list.");
//            }
//            dropdownOptionList.last().scrollIntoViewIfNeeded();
//            lastName = dropdownOptionList.last().innerText();
//        }
//        getCompanyInDropdown(name).click();
//
//        return currentPage;
//    }
}
