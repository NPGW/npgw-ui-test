package xyz.npgw.test.page.base;

import com.microsoft.playwright.Page;

public abstract class BasePage extends BaseModel {

    public BasePage(Page page) {
        super(page);
    }
}
