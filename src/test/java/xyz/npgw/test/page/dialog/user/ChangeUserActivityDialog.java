package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.TeamPage;

public class ChangeUserActivityDialog extends BaseDialog<TeamPage> {

    private final Locator activateButton = buttonByName("Activate");

    public ChangeUserActivityDialog(Page page) {
        super(page);
    }

    @Override
    protected TeamPage getReturnPage() {
        return new TeamPage(getPage());
    }

    @Step("Click 'Activate' button")
    public TeamPage clickActivateButton() {
        activateButton.click();

        return new TeamPage(getPage());
    }
}
