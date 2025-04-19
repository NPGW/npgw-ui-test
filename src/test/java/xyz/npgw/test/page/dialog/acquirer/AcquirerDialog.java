package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Keyboard;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.AcquirersPage;

@Getter
@SuppressWarnings("unchecked")
public abstract class AcquirerDialog<CurrentDialogT extends AcquirerDialog<CurrentDialogT>>
        extends BaseDialog<AcquirersPage, CurrentDialogT> {

    private final Locator statusSwitch = locator("div[role='radiogroup']");
    private final Locator allowedCurrenciesCheckboxes = locator("div[role='group']");

    private final Locator selectCountryLabel = labelExact("Select country");
    private final Locator selectCountry = labelExact("Select country");

    private final Locator selectTimezoneLabel = buttonByName("Timezone");
    private final Locator selectTimezone = labelExact("Timezone").locator("span[data-slot='value']");

    private final Locator selectDropdown = dialog();

    public AcquirerDialog(Page page) {
        super(page);
    }

    @Override
    protected AcquirersPage getReturnPage() {

        return new AcquirersPage(getPage());
    }

    @Step("Click on the 'Select country' chevron")
    public CurrentDialogT clickSelectCountry() {
        selectCountryLabel.locator("..")
                .locator("svg[role=presentation]")
                .last()
                .click();
        return (CurrentDialogT) this;
    }

    @Step("Click 'select country' clear icon")
    public CurrentDialogT clickSelectCountryClearIcon() {
        selectCountryLabel.locator("..")
                .locator("svg[role=presentation]")
                .first()
                .dispatchEvent("click");

        return (CurrentDialogT) this;
    }

    @Step("Click on the 'Select timezone' chevron")
    public CurrentDialogT clickSelectTimezone() {
        selectTimezoneLabel
                .locator("svg[role=presentation]")
                .click();

        return (CurrentDialogT) this;
    }

    @Step("Click on the '{option}' radiobutton")
    public CurrentDialogT clickStatusRadiobutton(String option) {
        labelExact(option).click();

        return (CurrentDialogT) this;
    }

    public Locator getStatusRadiobutton(String value) {
        return statusSwitch.locator("label:has(input[value='" + value.toUpperCase() + "'])");
    }

    @Step("Click on the '{country}' from dropdown")
    public CurrentDialogT clickCountryInDropdown(String country) {
        getPage().keyboard().type(country, new Keyboard.TypeOptions().setDelay(100));
        selectDropdown.locator("li").first().click();

        return (CurrentDialogT) this;
    }

    @Step("Click on the '{timezone}' from dropdown")
    public CurrentDialogT clickTimezoneInDropdown(String timezone) {
        selectDropdown
                .locator("li")
                .filter(new Locator.FilterOptions().setHasText(timezone))
                .click();

        return (CurrentDialogT) this;
    }
}
