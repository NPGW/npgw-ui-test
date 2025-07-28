package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.SuperHeaderComponent;

@SuppressWarnings("unchecked")
public interface SuperHeaderTrait<CurrentPageT> extends BaseTrait {

    default SuperHeaderComponent<CurrentPageT> getHeader() {
        return new SuperHeaderComponent<>(getPage(), (CurrentPageT) this);
    }
}
