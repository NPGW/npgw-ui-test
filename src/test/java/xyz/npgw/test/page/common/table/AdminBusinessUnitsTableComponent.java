package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.merchant.SecretTokenDialog;
import xyz.npgw.test.page.system.AdminBusinessUnitsPage;

public class AdminBusinessUnitsTableComponent extends BaseTableComponent<AdminBusinessUnitsPage> {

    public AdminBusinessUnitsTableComponent(Page page) {
        super(page);
    }

    @Override
    protected AdminBusinessUnitsPage getCurrentPage() {
        return new AdminBusinessUnitsPage(getPage());
    }

    @Step("Click 'Copy Business unit ID to clipboard' button")
    public AdminBusinessUnitsPage clickCopyBusinessUnitIdToClipboardButton(String businessUnitName) {
        getRow(businessUnitName).getByTestId("CopyBusinessUnitIDToClipboardButton").click();

        return getCurrentPage();
    }

    @Step("Click 'View secret token' button")
    public SecretTokenDialog clickViewSecretTokenButton(String businessUnitName) {
        getRow(businessUnitName).getByTestId("ViewSecretTokenButton").click();

        return new SecretTokenDialog(getPage());
    }

}
