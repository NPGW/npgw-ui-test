package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.acquirer.ChangeActivityButton;
import xyz.npgw.test.page.dialog.acquirer.EditAcquirerDialog;
import xyz.npgw.test.page.system.AcquirersPage;

@Getter
public class AcquirersTableComponent extends BaseTableComponent<AcquirersPage> {

    private final Locator editAcquirerButton = getByTestId("EditAcquirerButton");
    private final Locator changeActivityButton = getByTestId("ChangeBusinessUnitActivityButton");

    public AcquirersTableComponent(Page page) {
        super(page);
    }

    @Override
    protected AcquirersPage getCurrentPage() {

        return new AcquirersPage(getPage());
    }

    @Step("Click 'Edit' button to edit acquirer")
    public EditAcquirerDialog clickEditAcquirerButton() {
        editAcquirerButton.click();

        return new EditAcquirerDialog(getPage());
    }

    @Step("Click Activate/Deactivate acquirer button")
    public ChangeActivityButton clickChangeActivityButton() {
        changeActivityButton.click();

        return new ChangeActivityButton(getPage());
    }
}
