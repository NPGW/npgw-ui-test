package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.table.ReportsTableComponent;
import xyz.npgw.test.page.common.table.TransactionsTableComponent;

public interface ReportsTableTrait extends BaseTrait {

    default ReportsTableComponent getTable() {
        return new ReportsTableComponent(getPage());
    }

}
