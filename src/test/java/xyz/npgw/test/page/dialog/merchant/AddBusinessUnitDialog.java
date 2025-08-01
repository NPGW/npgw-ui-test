package xyz.npgw.test.page.dialog.merchant;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

public class AddBusinessUnitDialog extends BusinessUnitDialog<AddBusinessUnitDialog>
        implements AlertTrait<AddBusinessUnitDialog> {

    @Getter
    private final Locator addMerchantDialog = getByRole(AriaRole.DIALOG);
    private final Locator createButton = getByRole(AriaRole.BUTTON, "Create");

    @Getter
    private final Locator getAddMerchantDialogHeader = addMerchantDialog.locator("header");

    public AddBusinessUnitDialog(Page page) {
        super(page);
    }

    @Step("Click on the 'Create' button and trigger an error")
    public AddBusinessUnitDialog clickCreateButtonAndTriggerError() {
        createButton.click();

        return this;
    }

    @Step("Click on the 'Create' button")
    public CompaniesAndBusinessUnitsPage clickCreateButton() {
        createButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
