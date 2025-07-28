package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.table.UsersTableComponent;

@SuppressWarnings("unchecked")
public interface UsersTableTrait<CurrentPageT> extends BaseTrait {

    default UsersTableComponent<CurrentPageT> getTable() {
        return new UsersTableComponent<>(getPage(), (CurrentPageT) this);
    }
}
