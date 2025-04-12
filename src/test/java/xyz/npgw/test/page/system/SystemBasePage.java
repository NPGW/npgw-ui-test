package xyz.npgw.test.page.system;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BaseHeaderPage;
import xyz.npgw.test.page.base.trait.SystemMenuTrait;

public abstract class SystemBasePage extends BaseHeaderPage implements SystemMenuTrait {

    public SystemBasePage(Page page) {
        super(page);
    }
}
