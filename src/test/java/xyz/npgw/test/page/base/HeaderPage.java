package xyz.npgw.test.page.base;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.HeaderTrait;

public abstract class HeaderPage<T extends HeaderPage<T>> extends BasePage implements HeaderTrait<T> {

    public HeaderPage(Page page) {
        super(page);
    }

    @SuppressWarnings("unchecked")
    public T self() {
        return (T) this;
    }
}
