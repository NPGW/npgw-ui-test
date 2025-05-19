package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.acquirer.ChangeAcquirerActivityDialog;
import xyz.npgw.test.page.dialog.acquirer.EditAcquirerDialog;
import xyz.npgw.test.page.system.AcquirersPage;

@Getter
public class AcquirersTableComponent extends BaseTableComponent<AcquirersPage> {

    private final Locator editAcquirerButton = getByTestId("EditAcquirerButton");
    private final Locator changeAcquirerActivityButton = getByTestId("ChangeBusinessUnitActivityButton");

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
    public ChangeAcquirerActivityDialog clickChangeActivityButton() {
        changeAcquirerActivityButton.click();

        return new ChangeAcquirerActivityDialog(getPage());
    }
}
