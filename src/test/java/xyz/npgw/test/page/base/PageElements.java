package xyz.npgw.test.page.base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public interface PageElements {
    Page getPage();

    default Locator placeholder(String text) {
        return getPage().getByPlaceholder(text);
    }

    default Locator button(String text) {
        return getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text));
    }

    default Locator checkbox(String text) {
        return getPage().getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(text));
    }

    default Locator linkByName(String text) {
        return getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }

    default Locator optionByExactName(String text) {
        return getPage().getByLabel(text, new Page.GetByLabelOptions().setExact(true)).getByText(text);
    }

    default Locator locator(String selector) {
        return getPage().locator(selector);
    }

    default Locator columnHeader(String name) {
        return getPage().getByRole(AriaRole.COLUMNHEADER, new Page.GetByRoleOptions().setName(name));
    }

    default Locator dialog() {
        return getPage().getByRole(AriaRole.DIALOG);
    }

    default Locator tab(String text) {
        return getPage().getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName(text));
    }

    default Locator labelExact(String text) {
        return getPage().getByLabel(text, new Page.GetByLabelOptions().setExact(true));
    }

    default Locator textExact(String text) {
        return getPage().getByText(text, new Page.GetByTextOptions().setExact(true));
    }
}

