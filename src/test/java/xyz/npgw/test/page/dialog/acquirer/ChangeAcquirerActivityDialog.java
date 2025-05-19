package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.AcquirersPage;

@Getter
public class ChangeAcquirerActivityDialog<CurrentDialogT extends AcquirerDialog<CurrentDialogT>>
        extends BaseDialog<AcquirersPage, CurrentDialogT> {

    private final Locator deactivateButton = getByRole(AriaRole.BUTTON, "Deactivate");
    private final Locator activateButton = getByRole(AriaRole.BUTTON, "Activate");

    public ChangeAcquirerActivityDialog(Page page) {
        super(page);
    }

    @Override
    protected AcquirersPage getReturnPage() {
        return null;
    }

    @Step("Click 'Deactivate' button")
    public AcquirersPage clickDeactivateButton() {
        deactivateButton.click();

        return new AcquirersPage(getPage());
    }

    @Step("Click 'Activate' button")
    public AcquirersPage clickActivateButton() {
        activateButton.click();

        return new AcquirersPage(getPage());
    }
}
