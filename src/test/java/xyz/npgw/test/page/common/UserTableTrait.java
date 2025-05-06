package xyz.npgw.test.page.common;

public interface UserTableTrait extends TableTrait {

    default UserTableComponent getTable() {
        return new UserTableComponent(getPage());
    }
}
