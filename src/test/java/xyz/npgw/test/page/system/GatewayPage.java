package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.Getter;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.common.trait.GatewayTableTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.dialog.merchant.AddMerchantAcquirerDialog;

@Getter
public class GatewayPage extends BaseSystemPage<GatewayPage> implements SelectCompanyTrait<GatewayPage>,
        SelectBusinessUnitTrait<GatewayPage>, AlertTrait<GatewayPage>, GatewayTableTrait {

    private final Locator currencyValue = locator("div[data-slot='innerWrapper'] span");
    @Getter(AccessLevel.NONE)
    private final Locator currencyDropdown = locator("div[data-slot='listbox']");
    private final Locator currencyOptions = currencyDropdown.getByRole(AriaRole.OPTION);
    private final Locator resetFilterButton = locator("[data-icon='xmark']");
    private final Locator addMerchantAcquirerButton = getByTestId("AddMerchantAcquirerButton");

    public GatewayPage(Page page) {
        super(page);
    }

    @Step("Click Currency value")
    public GatewayPage clickCurrencyValue() {
        currencyValue.click();

        return this;
    }

    @Step("Select Currency '{currency}'")
    public GatewayPage selectCurrency(String currency) {
        currencyOptions.filter(new Locator.FilterOptions().setHasText(currency)).click();
        currencyDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        return this;
    }

    @Step("Click 'Reset filter' button")
    public GatewayPage clickResetFilterButton() {
        resetFilterButton.click();

        return this;
    }

    @Step("Click 'Add merchant acquirer button'")
    public AddMerchantAcquirerDialog clickAddMerchantAcquirerButton(){
        addMerchantAcquirerButton.click();

        return new AddMerchantAcquirerDialog(getPage());
    }
}
