package xyz.npgw.test.page.common;

import xyz.npgw.test.page.base.BaseTrait;

@SuppressWarnings("unchecked")
public interface SelectAcquirerTrait<CurrentPageT> extends BaseTrait {

    default SelectAcquirerComponent<CurrentPageT> getSelectAcquirer() {
        return new SelectAcquirerComponent<>(getPage(), (CurrentPageT) this);
    }
}
