package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.HeaderComponent;

public interface HeaderTrait<T extends HeaderPage<T>> extends BaseTrait {

    T self();

    default HeaderComponent<T> getHeader() {

        return new HeaderComponent<>(getPage(), self());
    }
}
