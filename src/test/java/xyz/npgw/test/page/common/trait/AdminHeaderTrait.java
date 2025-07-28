package xyz.npgw.test.page.common.trait;

import xyz.npgw.test.page.base.BaseTrait;
import xyz.npgw.test.page.common.AdminHeaderComponent;

@SuppressWarnings("unchecked")
public interface AdminHeaderTrait<CurrentPageT> extends BaseTrait {

    default AdminHeaderComponent<CurrentPageT> getHeader() {
        return new AdminHeaderComponent<>(getPage(), (CurrentPageT) this);
    }
}
