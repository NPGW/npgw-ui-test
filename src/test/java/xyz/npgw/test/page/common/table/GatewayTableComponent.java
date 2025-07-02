package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.acquirer.DeleteMerchantAcquirerDialog;
import xyz.npgw.test.page.system.GatewayPage;

public class GatewayTableComponent extends BaseTableComponent<GatewayPage> {

    public GatewayTableComponent(Page page) {
        super(page);
    }

    @Override
    protected GatewayPage getCurrentPage() {
        return new GatewayPage(getPage());
    }

    @Step("Click 'Delete merchant acquirer' for merchant acquirer with name: {acquirerDisplayName}")
    public DeleteMerchantAcquirerDialog clickDeleteMerchantAcquirer(String acquirerDisplayName) {
        Locator row = getRowByText(acquirerDisplayName);
        row.locator(getByTestId("DeleteMerchantAcquirerButton")).click();

        return new DeleteMerchantAcquirerDialog(getPage());
    }
}
