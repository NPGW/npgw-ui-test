package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.GatewayPage;

@Getter
public class MerchantAcquirerDialog extends BaseDialog<GatewayPage, MerchantAcquirerDialog> {

    private final Locator acquirerNameField = getByPlaceholder("Enter acquirer name");

    public MerchantAcquirerDialog(Page page) {
        super(page);
    }

    @Override
    protected GatewayPage getReturnPage() {
        return new GatewayPage(getPage());
    }
}
