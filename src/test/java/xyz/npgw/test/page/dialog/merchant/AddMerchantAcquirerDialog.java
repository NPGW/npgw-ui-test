package xyz.npgw.test.page.dialog.merchant;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.GatewayPage;

public class AddMerchantAcquirerDialog extends BaseDialog<GatewayPage, AddMerchantAcquirerDialog> {

    private final Locator selectAcquirerDropdown = getByLabelExact("Select acquirer");
    private final Locator selectAcquirerDropdownOptions = locator("//li[@role='option']");
    private final Locator createButton = getByRole(AriaRole.BUTTON, "Create");


    public AddMerchantAcquirerDialog(Page page) {
        super(page);
    }

    @Override
    protected GatewayPage getReturnPage() {

        return new GatewayPage(getPage());
    }

    @Step("Click the 'Select acquirer' dropdown")
    public AddMerchantAcquirerDialog clickSelectAcquirerDropdown() {
        selectAcquirerDropdown.click();

        return this;
    }

    @Step("Select Acquirer from the 'Select acquirer' dropdown")
    public AddMerchantAcquirerDialog selectAcquirerFromDropdown(String optionName) {
        selectAcquirerDropdown.click();
        selectAcquirerDropdownOptions.filter(new Locator.FilterOptions().setHasText(optionName)).first().click();

        return this;
    }

    public GatewayPage clickCreateButton() {
        createButton.click();

        return new GatewayPage(getPage());
    }

}
