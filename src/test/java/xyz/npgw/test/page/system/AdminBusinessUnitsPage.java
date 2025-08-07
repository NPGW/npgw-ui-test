package xyz.npgw.test.page.system;

import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.system.AdminSystemMenuTrait;
import xyz.npgw.test.page.common.trait.BusinessUnitsTableTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;

@Getter
public class AdminBusinessUnitsPage extends HeaderPage<AdminBusinessUnitsPage>
        implements AdminSystemMenuTrait,
                   SelectCompanyTrait<AdminBusinessUnitsPage>,
                   BusinessUnitsTableTrait {

    public AdminBusinessUnitsPage(Page page) {
        super(page);
    }
}
