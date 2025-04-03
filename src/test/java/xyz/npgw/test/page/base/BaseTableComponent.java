package xyz.npgw.test.page.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import java.util.NoSuchElementException;

public abstract class BaseTableComponent<T extends BasePage<T>> extends BaseComponent<T> {

    private final Locator header = getPage().getByRole(AriaRole.COLUMNHEADER);
    private final Locator rows = getPage().getByRole(AriaRole.ROW);

    public BaseTableComponent(Page page, T owner) {
        super(page, owner);
    }

    public Locator getTableHeader() {
        return header;
    }

    public Locator getRowsWithoutHeader() {
        return rows.filter(new Locator.FilterOptions().setHasNot(header));
    }

    public Locator getColumnBySelector(String selector) {
        int index = -1;
        for (int i = 0; i < header.count(); i++) {
            if (header.nth(i).innerText().equals(selector)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            throw new NoSuchElementException("Column with selector '" + selector + "' not found.");
        }

        return header.nth(index);
    }
}
