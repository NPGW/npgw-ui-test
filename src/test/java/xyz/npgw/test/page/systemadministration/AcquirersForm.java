package xyz.npgw.test.page.systemadministration;

import io.qameta.allure.Step;
import xyz.npgw.test.page.component.FormComponent;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class AcquirersForm extends FormComponent {

    private final Locator countryLabel = dialog().getByLabel("Select country");

    public AcquirersForm(Page page) {
        super(page);
    }

    @Step("Click 'Select Country' dropDownList")
    public AcquirersForm clickSelectCountry(String name) {
        countryLabel.locator("..")
                .locator("svg[role=presentation]")
                .last()
                .click();
        return this;
    }

}
