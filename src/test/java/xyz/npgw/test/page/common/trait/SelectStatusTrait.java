package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.SelectStatusComponent;

@SuppressWarnings("unchecked")
public interface SelectStatusTrait<CurrentPageT> extends BaseTrait {

    default SelectStatusComponent<CurrentPageT> getSelectStatus() {
        return new SelectStatusComponent<>(getPage(), (CurrentPageT) this);
    }

}
