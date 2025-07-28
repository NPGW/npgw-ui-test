package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.UserHeaderComponent;

@SuppressWarnings("unchecked")
public interface UserHeaderTrait<CurrentPageT> extends BaseTrait {

    default UserHeaderComponent<CurrentPageT> getHeader() {
        return new UserHeaderComponent<>(getPage(), (CurrentPageT) this);
    }
}
