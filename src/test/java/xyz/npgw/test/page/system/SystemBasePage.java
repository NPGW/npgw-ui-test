package xyz.npgw.test.page.system;

import com.microsoft.playwright.Page;
import xyz.npgw.test.component.HeaderComponent;
import xyz.npgw.test.component.SystemMenuComponent;
import xyz.npgw.test.page.base.BaseHeaderPage;
import xyz.npgw.test.page.base.BasePage;
import xyz.npgw.test.page.base.trait.HeaderTrait;
import xyz.npgw.test.page.base.trait.SystemMenuTrait;

public abstract class SystemBasePage extends BaseHeaderPage implements SystemMenuTrait {

    public SystemBasePage(Page page) {
        super(page);
    }
}
