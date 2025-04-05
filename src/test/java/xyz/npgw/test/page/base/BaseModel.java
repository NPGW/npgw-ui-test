package xyz.npgw.test.page.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public abstract class BaseModel {

    private final Page page;

    public BaseModel(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    protected Locator getPlaceholderByText(String text) {
        return getPage().getByPlaceholder(text);
    }

    protected Locator getButtonByText(String text) {
        return getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator getCheckboxByText(String text) {
        return getPage().getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator getLocatorBySelector(String selector) {
        return getPage().locator(selector);
    }

    protected Locator getDialog() {
        return getPage().getByRole(AriaRole.DIALOG);
    }

    protected Locator getTabByText(String text) {
        return getPage().getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator getLabelByExactText(String text) {
        return getPage().getByLabel(text, new Page.GetByLabelOptions().setExact(true));
    }

    protected Locator getLocatorByExactText(String text) {
        return getPage().getByText(text, new Page.GetByTextOptions().setExact(true));
    }

    protected Locator getGroupByText(String text) {
        return getPage().getByRole(AriaRole.GROUP, new Page.GetByRoleOptions().setName(text));
    }

    protected Locator getLinkByText(String text) {
        return getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }
}
