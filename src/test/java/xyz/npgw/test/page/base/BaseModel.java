package xyz.npgw.test.page.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;

@Getter
public abstract class BaseModel {

    private final Page page;

    public BaseModel(Page page) {
        this.page = page;
    }

    protected Locator getByRole(AriaRole ariaRole) {
        return page.getByRole(ariaRole);
    }

    protected Locator getByRole(AriaRole ariaRole, String name) {
        return page.getByRole(ariaRole, new Page.GetByRoleOptions().setName(name));
    }

    protected Locator getByRoleExact(AriaRole ariaRole, String name) {
        return page.getByRole(ariaRole, new Page.GetByRoleOptions().setName(name).setExact(true));
    }

    protected Locator getByTestId(String testId) {
        return page.getByTestId(testId);
    }

    protected Locator getByPlaceholder(String placeholder) {
        return page.getByPlaceholder(placeholder);
    }

    protected Locator labelExact(String text) {
        return getPage().getByLabel(text, new Page.GetByLabelOptions().setExact(true));
    }

    protected Locator textExact(String text) {
        return getPage().getByText(text, new Page.GetByTextOptions().setExact(true));
    }

    protected Locator radioButton(String name) {
        return getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName(name).setExact(true));
    }

    protected Locator locator(String selector) {
        return getPage().locator(selector);
    }

    protected Locator dialog() {
        return getPage().getByRole(AriaRole.DIALOG);
    }

    protected Locator option(Locator locator) {
        return locator.getByRole(AriaRole.OPTION);
    }

    protected Locator listboxByRole() {
        return getPage().getByRole(AriaRole.LISTBOX);
    }
}
