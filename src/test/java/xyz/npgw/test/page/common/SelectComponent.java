package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import xyz.npgw.test.page.base.BaseComponent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class SelectComponent<CurrentPageT> extends BaseComponent {

    protected final CurrentPageT currentPage;
    //    private final Locator dropdownOptionList = getByLabelExact("Suggestions").getByRole(AriaRole.OPTION);
    private final Locator dropdownOptionList = getByRole(AriaRole.OPTION);

    public SelectComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.currentPage = currentPage;
    }

    private Locator getOptionInDropdown(String name) {
        return dropdownOptionList.filter(new Locator.FilterOptions().setHas(getByTextExact(name)));
    }

    protected List<String> getAllOptions(Locator selectInputField, String name) {
        String lastName;

        selectInputField.clear();
        selectInputField.fill(name);

        if (getByRoleExact(AriaRole.LISTBOX, "Suggestions").innerText().equals("No results found.")) {
            return Collections.emptyList();
        }

        HashSet<String> allOptions = new HashSet<>();
        do {
            allOptions.addAll(dropdownOptionList.allInnerTexts());

            lastName = dropdownOptionList.last().innerText();
            dropdownOptionList.last().scrollIntoViewIfNeeded();
        } while (!dropdownOptionList.last().innerText().equals(lastName));

        return allOptions.stream().toList();
    }

    protected CurrentPageT select(Locator selectInputField, String name) {
        String lastName = "";

//        Locator dropdownChevron = selectInputField.locator("..//button[last()]");
//        dropdownChevron.click();
//        assertThat(dropdownChevron).hasAttribute("data-open", "true");
//
//        assertThat(locator("div[data-slot='content']")).hasAttribute("data-open", "true");

        selectInputField.fill(name);

        assertThat(getByRoleExact(AriaRole.LISTBOX, "Suggestions")).not().hasText("No results found.");

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
