package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;

public class GatewayPage extends BaseSystemPage {

    private final Locator currencyLabel = labelExact("Currency");
    private final Locator currencyPlaceholder = locator("div[data-slot='innerWrapper'] span");
    private final Locator dropdownCurrencyList = locator("div[data-slot='listbox']");
    @Getter
    private final Locator currencyOptions = option(dropdownCurrencyList);


    public GatewayPage(Page page) {
        super(page);
    }

    @Step("Click Currency placeholder")
    public GatewayPage clickCurrencyPlaceholder() {
        currencyLabel.waitFor();
        currencyPlaceholder.click();

        return this;
    }
}
