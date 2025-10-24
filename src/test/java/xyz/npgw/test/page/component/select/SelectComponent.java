package xyz.npgw.test.page.component.select;

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

    private static final String NO_RESULTS_FOUND = "No results found.";

    protected final CurrentPageT currentPage;

    private final Locator dropdown = getByRoleExact(AriaRole.LISTBOX, "Suggestions");
    private final Locator dropdownOptions = dropdown.getByRole(AriaRole.OPTION);

    public SelectComponent(Page page, CurrentPageT currentPage) {
        super(page);
        this.currentPage = currentPage;
    }

    protected List<String> getAllOptions(Locator selectInputField, String name) {
        String lastName;

        selectInputField.clear();
        selectInputField.fill(name);

        if (dropdown.innerText().equals(NO_RESULTS_FOUND)) {
            return Collections.emptyList();
        }

        HashSet<String> allOptions = new HashSet<>();
        do {
            allOptions.addAll(dropdownOptions.allInnerTexts());

            lastName = dropdownOptions.last().innerText();
            dropdownOptions.last().scrollIntoViewIfNeeded();
        } while (!dropdownOptions.last().innerText().equals(lastName));

        return allOptions.stream().toList();
    }

    protected CurrentPageT select(Locator selectInputField, String name) {
        selectInputField.fill(name);

        try {
            assertThat(dropdown).not().hasText(NO_RESULTS_FOUND);
        } catch (AssertionError e) {
            throw new AssertionError("Dropdown is empty. Option '%s' not found".formatted(name));
        }

        Locator option = dropdownOptions.getByText(name, new Locator.GetByTextOptions().setExact(true));
        String lastOption = "";

        while (option.count() == 0) {
            String currentLastOption = dropdownOptions.last().innerText();
            if (currentLastOption.equals(lastOption)) {
                throw new NoSuchElementException("Option '%s' not found in dropdown.".formatted(name));
            }
            dropdownOptions.last().scrollIntoViewIfNeeded();
            lastOption = currentLastOption;
        }
        option.click();

        return currentPage;
    }
}
