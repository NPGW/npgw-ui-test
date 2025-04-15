package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;

public class GatewayPage extends BaseSystemPage {

    private final Locator selectAcquirerLabel = labelExact("Select acquirer");
    @Getter
    private final Locator currencyLabel = labelExact("Currency");
    @Getter
    private final Locator gatewayCurrencyPlaceholder = locator("div[data-slot='innerWrapper'] span");
    @Getter
    private final Locator dropdownGatewayCurrencyList = locator("div[data-slot='listbox']");
    @Getter
    private final Locator gatewayCurrencyOptions = option(dropdownGatewayCurrencyList);


    public GatewayPage(Page page) {
        super(page);
    }

    @Step("Click Currency placeholder")
    public GatewayPage clickGatewayCurrencyPlaceholder() {
        currencyLabel.waitFor();
        gatewayCurrencyPlaceholder.click();

        return this;
    }
}
