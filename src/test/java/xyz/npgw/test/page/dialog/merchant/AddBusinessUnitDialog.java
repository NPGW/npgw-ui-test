package xyz.npgw.test.page.dialog.merchant;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

public class AddBusinessUnitDialog extends BaseDialog {

    @Getter
    private final Locator companyNameField = locator("input[aria-label='Company name']");
    private final Locator closeButton = textExact("Close");
    private final Locator addMerchantDialog = dialog();
    private final Locator createButton = buttonByName("Create");
    private final Locator merchantNameField = placeholder("Enter merchant name");
    private final Locator usdCheckbox = labelExact("USD");
    private final Locator eurCheckbox = labelExact("EUR");
    private final Locator activeRadioInput = locator("[type='radio'][value='ACTIVE']");
    private final Locator inactiveRadioInput = locator("[type='radio'][value='INACTIVE']");


    @Getter
    private final Locator getAddMerchantDialogHeader = addMerchantDialog.locator("header");
    @Getter
    private final Locator alertMessage = locator("[role='alert']");

    public AddBusinessUnitDialog(Page page) {
        super(page);
    }

    public CompaniesAndBusinessUnitsPage clickOnCloseButton() {
        closeButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }

    @Step("Click on the 'Create' button and trigger an error")
    public AddBusinessUnitDialog clickCreateButtonAndTriggerError() {
        createButton.click();

        return this;
    }

    @Step("Fill in merchant name: {merchantName}")
    public AddBusinessUnitDialog fillMerchantNameField(String merchantName) {
        merchantNameField.fill(merchantName);

        return this;
    }

    @Step("Set USD checkbox to: {isUsd}")
    public AddBusinessUnitDialog setUsdCheckbox(boolean isUsd) {
        if (isUsd) {
            usdCheckbox.check();
        } else {
            usdCheckbox.uncheck();
        }

        return this;
    }

    @Step("Set EUR checkbox to: {isEul}")
    public AddBusinessUnitDialog setEurCheckbox(boolean isEul) {
        if (isEul) {
            eurCheckbox.check();
        } else {
            eurCheckbox.uncheck();
        }

        return this;
    }

    @Step("Select merchant status")
    public AddBusinessUnitDialog selectStatus(boolean isActive) {
        if (isActive) {
            activeRadioInput.check();
        } else {
            inactiveRadioInput.check();
        }

        return this;
    }

    @Step("Click on the 'Create' button")
    public CompaniesAndBusinessUnitsPage clickCreateButton() {
        createButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
