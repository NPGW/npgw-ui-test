package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.table.ReportsTableComponent;

@SuppressWarnings("unchecked")
public interface ReportsTableTrait<CurrentPageT> extends BaseTrait {

    default ReportsTableComponent<CurrentPageT> getTable() {
        return new ReportsTableComponent<>(getPage(), (CurrentPageT) this);
    }
}
