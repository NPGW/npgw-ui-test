package xyz.npgw.test.page.system;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.system.AdminSystemMenuTrait;
import xyz.npgw.test.page.common.trait.SelectStatusTrait;
import xyz.npgw.test.page.common.trait.UsersTableTrait;

public class AdminTeamPage extends BaseTeamPage<AdminTeamPage> {

    public AdminTeamPage(Page page) {
        super(page);
    }
}
