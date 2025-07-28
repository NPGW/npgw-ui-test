package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.table.AdminBusinessUnitsTableComponent;

public interface AdminBusinessUnitsTableTrait extends BaseTrait {

    default AdminBusinessUnitsTableComponent getTable() {
        return new AdminBusinessUnitsTableComponent(getPage());
    }
}
