package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.table.UsersTableComponent;

public interface UsersTableTrait extends BaseTrait {

    default UsersTableComponent getTable() {
        return new UsersTableComponent(getPage());
    }
}
