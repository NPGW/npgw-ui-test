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

    protected Locator buttonByName(String name) {
        return getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(name));
    }

    protected Locator checkbox(String text) {
        return getPage().getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator linkByName(String text) {
        return getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator menuItemByName(String text) {
        return getPage().getByRole(AriaRole.MENUITEM, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator radioButton(String name) {
        return getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName(name).setExact(true));
    }

    protected Locator tab(String text) {
        return getPage().getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator placeholder(String text) {
        return getPage().getByPlaceholder(text);
    }

    protected Locator locator(String selector) {
        return getPage().locator(selector);
    }

    protected Locator dialog() {
        return getPage().getByRole(AriaRole.DIALOG);
    }

    protected Locator labelExact(String text) {
        return getPage().getByLabel(text, new Page.GetByLabelOptions().setExact(true));
    }

    protected Locator textExact(String text) {
        return getPage().getByText(text, new Page.GetByTextOptions().setExact(true));
    }

    protected Locator option(Locator locator) {
        return locator.getByRole(AriaRole.OPTION);
    }

    protected Locator getByTestId(String testId) {
        return getPage().getByTestId(testId);
    }

    protected Locator listboxByRole() {
        return getPage().getByRole(AriaRole.LISTBOX);
    }
}
