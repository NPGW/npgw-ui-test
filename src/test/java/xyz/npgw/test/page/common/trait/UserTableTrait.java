package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.common.UserTableComponent;

public interface UserTableTrait extends TableTrait {

    default UserTableComponent getTable() {
        return new UserTableComponent(getPage());
    }
}
