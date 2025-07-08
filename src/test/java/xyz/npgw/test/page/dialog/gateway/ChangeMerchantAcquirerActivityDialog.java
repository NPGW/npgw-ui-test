package xyz.npgw.test.page.dialog.gateway;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.GatewayPage;

public class ChangeMerchantAcquirerActivityDialog extends BaseDialog<GatewayPage, ChangeMerchantAcquirerActivityDialog>
        implements AlertTrait<ChangeMerchantAcquirerActivityDialog> {

    private final Locator submitButton = getDialog().locator("button.bg-primary");
    private final Locator cancelButton = getDialog().getByText("Cancel");
    private final Locator closeButton = getDialog().locator("button[role=button]");

    public ChangeMerchantAcquirerActivityDialog(Page page) {
        super(page);
    }

    @Override
    protected GatewayPage getReturnPage() {
        return new GatewayPage(getPage());
    }

    @Step("Click on submit deactivate button ")
    public GatewayPage clickSubmitButton() {
        submitButton.click();

        return new GatewayPage(getPage());
    }

    @Step("Click on submit Cancel button ")
    public GatewayPage clickCancelButton() {
        cancelButton.click();

        return new GatewayPage(getPage());
    }

    @Step("Click on submit close button ")
    public GatewayPage clickCloseButton() {
        closeButton.click();

        return new GatewayPage(getPage());
    }
}
