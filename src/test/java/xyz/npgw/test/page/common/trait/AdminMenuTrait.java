package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.AdminMenuComponent;

public interface AdminMenuTrait extends BaseTrait {

    default AdminMenuComponent getSystemMenu() {
        return new AdminMenuComponent(getPage());
    }
}
