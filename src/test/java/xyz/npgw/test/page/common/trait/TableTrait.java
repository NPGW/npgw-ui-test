package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.TableComponent;

public interface TableTrait extends BaseTrait {

    default TableComponent getTable() {
        return new TableComponent(getPage());
    }
}
