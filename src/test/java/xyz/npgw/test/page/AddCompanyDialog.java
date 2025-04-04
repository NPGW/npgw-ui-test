package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BaseDialog;

public class AddCompanyDialog extends BaseDialog {

    private final Locator addCompanyDialogHeader = locator("section header");
    private final Locator companyNameField = placeholder("Enter company name");
    private final Locator companyTypeField = placeholder("Enter type");
    private final Locator createButton = button("Create");
    private final Locator errorMessage = locator("[role='alert']");

    public AddCompanyDialog(Page page) {
        super(page);
    }

    public Locator getAddCompanyDialogHeader() {
        return addCompanyDialogHeader;
    }

    @Step("Fill company name field with the name: {companyName}")
    public AddCompanyDialog fillCompanyNameField(String companyName) {
        companyNameField.fill(companyName);

        return this;
    }

    @Step("Fill company type field with the type: {companyType}")
    public AddCompanyDialog fillCompanyTypeField(String companyType) {
        companyTypeField.fill(companyType);

        return this;
    }

    @Step("Click on the 'Create' button and trigger an error")
    public AddCompanyDialog clickCreateButtonAndTriggerError() {
        createButton.click();

        return this;
    }

    public Locator getErrorMessage() {
        errorMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return errorMessage;
    }
}
