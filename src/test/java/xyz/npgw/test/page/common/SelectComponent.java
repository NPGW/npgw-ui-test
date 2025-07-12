package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import xyz.npgw.test.page.base.BaseComponent;

import java.util.NoSuchElementException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SelectComponent<CurrentPageT> extends BaseComponent {

//    private final Locator dropdownOptionList = getByLabelExact("Suggestions").getByRole(AriaRole.OPTION);
    private final Locator dropdownOptionList = getByRole(AriaRole.OPTION);


    protected final CurrentPageT currentPage;

    public SelectComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.currentPage = currentPage;
    }

    private Locator getOptionInDropdown(String name) {
        return dropdownOptionList.filter(new Locator.FilterOptions().setHas(getByTextExact(name)));
    }

    protected CurrentPageT select(Locator selectInputField, String name) {
        String lastName = "";

        Locator dropdownChevron = selectInputField.locator("..//button[last()]");
        dropdownChevron.click();
        assertThat(dropdownChevron).hasAttribute("data-open", "true");

        selectInputField.fill(name);

        if (locator("div[data-slot='empty-content']").isVisible()) {
            throw new NoSuchElementException("Option '%s' not found. Dropdown list is empty.".formatted(name));
        }

        while (getOptionInDropdown(name).all().isEmpty()) {
            if (dropdownOptionList.last().innerText().equals(lastName)) {
                throw new NoSuchElementException("Option '%s' not found in dropdown list.".formatted(name));
            }
            dropdownOptionList.last().scrollIntoViewIfNeeded();
            lastName = dropdownOptionList.last().innerText();
        }
        dropdownOptionList.getByText(name, new Locator.GetByTextOptions().setExact(true)).click();
//        getCompanyInDropdown(name).click();

        return currentPage;
    }
}
