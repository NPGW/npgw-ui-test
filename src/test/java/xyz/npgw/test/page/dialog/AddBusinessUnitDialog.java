package xyz.npgw.test.page.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import java.util.List;

public class AddBusinessUnitDialog extends BaseDialog {

    @Getter
    private final Locator companyNameField = locator("input[aria-label='Company name']");
    private final Locator closeButton = textExact("Close");
    private final Locator merchantNameField = locator("input[aria-label='Merchant name']");
    private final Locator usdCheckbox = locator("input[aria-label='USD']");
    private final Locator eurCheckbox = locator("input[aria-label='EUR']");
    private final Locator activeRadioButton = locator("input[type='radio'][value='ACTIVE']");
    private final Locator inactiveRadioButton = locator("input[type='radio'][value='INACTIVE']");
    private final Locator createButton = textExact("Create");

    public AddBusinessUnitDialog(Page page) {
        super(page);
    }

    public CompaniesAndBusinessUnitsPage clickOnCloseButton() {
        closeButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }

    public AddBusinessUnitDialog setBusinessUnitName(String name) {
        merchantNameField.fill(name);

        return this;
    }

    public void checkUsdCurrency() {
        usdCheckbox.setChecked(true);
    }

    public void checkEurCurrency() {
        eurCheckbox.setChecked(true);
    }

    public AddBusinessUnitDialog selectActiveState() {
        activeRadioButton.click();

        return this;
    }

    public AddBusinessUnitDialog selectInactiveState() {
        inactiveRadioButton.click();

        return this;
    }

    public AddBusinessUnitDialog selectState(String status) {
        if (status.equalsIgnoreCase("Inactive")) {
            return selectInactiveState();
        } else {
            return selectActiveState();
        }
    }

    public AddBusinessUnitDialog selectCurrencies(List<String> currencies) {
        if (currencies.contains("USD")) {
            checkUsdCurrency();
        }
        if (currencies.contains("EUR")) {
            checkEurCurrency();
        }
        return this;
    }

    public CompaniesAndBusinessUnitsPage clickOnCreateButton() {
        createButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
