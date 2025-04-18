package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.AcquirersPage;

@Getter
public abstract class AcquirerDialog<CurrentDialogT extends AcquirerDialog<CurrentDialogT>>
        extends BaseDialog<AcquirersPage,CurrentDialogT> {

    private final Locator selectCountryLabel = labelExact("Select country");
    private final Locator statusSwitch = locator("div[role='radiogroup']");
    private final Locator allowedCurrenciesCheckboxes = locator("div[role='group']");
    private final Locator selectTimezone = textExact("Select timezone");

    public AcquirerDialog(Page page) {
        super(page);
    }

    @Override
    protected AcquirersPage getReturnPage() {

        return new AcquirersPage(getPage());
    }

    @Step("Click on the 'Select country' dropdownList")
    public CurrentDialogT clickSelectCountry(String name) {
        selectCountryLabel.locator("..")
                .locator("svg[role=presentation]")
                .last()
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

}
