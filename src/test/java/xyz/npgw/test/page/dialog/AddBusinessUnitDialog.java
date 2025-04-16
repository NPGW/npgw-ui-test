package xyz.npgw.test.page.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

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
        if (!usdCheckbox.isChecked()) {
            usdCheckbox.click();
        }
    }

    public void checkEurCurrency() {
        if (!eurCheckbox.isChecked()) {
            eurCheckbox.click();
        }
    }

    public void selectActiveState() {
        activeRadioButton.click();
    }

    public void selectInactiveState() {
        inactiveRadioButton.click();

    }

    public CompaniesAndBusinessUnitsPage clickOnCreateButton() {
        createButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
