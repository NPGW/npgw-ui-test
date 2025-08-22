package xyz.npgw.test.page.system;

import lombok.Getter;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.page.component.header.SuperHeaderMenuTrait;
import xyz.npgw.test.page.component.select.SelectCompanyTrait;
import xyz.npgw.test.page.component.system.SuperSystemMenuTrait;
import xyz.npgw.test.page.component.table.SuperUsersTableTrait;
import xyz.npgw.test.page.dialog.user.SuperAddUserDialog;

@Getter
@Log4j2
public class SuperTeamPage extends BaseTeamPage<SuperTeamPage>
        implements SuperHeaderMenuTrait<SuperTeamPage>,
                   SuperSystemMenuTrait,
                   SelectCompanyTrait<SuperTeamPage>,
                   SuperUsersTableTrait {

    private final Locator commonIconBtn = locator("[role='tabpanel']>div>div:first-child button[data-testid] svg");
    private final Locator iconBtnModal = locator("[data-slot='content']");

    public SuperTeamPage(Page page) {
        super(page);
    }

    public SuperAddUserDialog clickAddUserButton() {
        clickAddUser();

        return new SuperAddUserDialog(getPage());
    }
}
