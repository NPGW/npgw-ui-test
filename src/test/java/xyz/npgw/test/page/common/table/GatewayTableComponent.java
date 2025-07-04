package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.acquirer.DeleteBusinessUnitAcquirerDialog;
import xyz.npgw.test.page.system.GatewayPage;
import com.microsoft.playwright.options.AriaRole;

public class GatewayTableComponent extends BaseTableComponent<GatewayPage> {

    private final Locator acquirerColumnHeader = getByRole(AriaRole.COLUMNHEADER, "Acquirer");

    public GatewayTableComponent(Page page) {
        super(page);
    }

    @Override
    protected GatewayPage getCurrentPage() {
        return new GatewayPage(getPage());
    }

    @Step("Click 'Delete business unit acquirer' for merchant acquirer with name: {acquirerDisplayName}")
    public DeleteBusinessUnitAcquirerDialog clickDeleteBusinessUnitAcquirer(String acquirerDisplayName) {
        getRowByText(acquirerDisplayName).locator(getByTestId("DeleteMerchantAcquirerButton")).click();

        return new DeleteBusinessUnitAcquirerDialog(getPage());
    }

    @Step("Click 'Acquirer' column header")
    public GatewayPage clickAcquirerColumnHeader() {
        acquirerColumnHeader.click();

        return new GatewayPage(getPage());
    }
}
