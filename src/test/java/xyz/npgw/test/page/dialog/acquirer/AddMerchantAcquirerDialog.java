package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.common.trait.SelectAcquirerTrait;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.GatewayPage;

@Getter
public class AddMerchantAcquirerDialog extends BaseDialog<GatewayPage, AddMerchantAcquirerDialog>
        implements SelectAcquirerTrait<AddMerchantAcquirerDialog> {

    private final Locator acquirerNameField = getByPlaceholder("Enter acquirer name");

    public AddMerchantAcquirerDialog(Page page) {
        super(page);
    }

    @Override
    protected GatewayPage getReturnPage() {
        return new GatewayPage(getPage());
    }
}
