package xyz.npgw.test.page.system;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.entity.User;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.header.SuperHeaderMenuTrait;
import xyz.npgw.test.page.common.system.SuperSystemMenuTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.common.trait.SelectStatusTrait;
import xyz.npgw.test.page.common.trait.UsersTableTrait;
import xyz.npgw.test.page.dialog.user.AddUserDialog;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Log4j2
public class SuperTeamPage extends BaseTeamPage<SuperTeamPage>
        implements SelectCompanyTrait<SuperTeamPage> {

    public SuperTeamPage(Page page) {
        super(page);
    }
}
