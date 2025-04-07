package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.base.BasePage;
import xyz.npgw.test.page.systemadministration.CompaniesAndBusinessUnitsPage;

import java.util.List;

public class AddCompanyDialog extends BasePage {

    @Getter
    private final Locator addCompanyDialogHeader = getLocatorBySelector("section header");
    private final Locator companyNameField = getPlaceholderByText("Enter company name");
    private final Locator companyTypeField = getPlaceholderByText("Enter type");
    @Getter
    private final Locator createButton = getButtonByName("Create");
    private final Locator errorMessage = getLocatorBySelector("[role='alert']");
    private final Locator alertMessage = getLocatorBySelector("[role='alert']");
    private final Locator allFieldPlaceholders = getLocatorBySelector("[data-slot='input']:not([placeholder='Search...'])");
    private final Locator closeButton = getLocatorByExactText("Close");
    private final Locator companyDescriptionField = getPlaceholderByText("Enter company description");
    private final Locator companyWebsiteField = getPlaceholderByText("Enter company website");
    private final Locator companyPrimaryContactField = getPlaceholderByText("Enter company primary contact");
    private final Locator companyEmailField = getPlaceholderByText("Enter company email");
    private final Locator companyCountryField = getPlaceholderByText("Enter country");
    private final Locator companyStateField = getPlaceholderByText("Enter state");
    private final Locator companyZipField = getPlaceholderByText("Enter ZIP");
    private final Locator companyCityField = getPlaceholderByText("Enter city");
    private final Locator companyPhoneField = getPlaceholderByText("Enter phone");
    private final Locator companyMobileField = getPlaceholderByText("Enter mobile");
    private final Locator companyFaxField = getPlaceholderByText("Enter fax");

    public AddCompanyDialog(Page page) {
        super(page);
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

    @Step("Fill company description field")
    public AddCompanyDialog fillCompanyDescriptionField(String companyDescription) {
        companyDescriptionField.fill(companyDescription);

        return this;
    }

    @Step("Fill company website field")
    public AddCompanyDialog fillCompanyWebsiteField(String companyWebsite) {
        companyWebsiteField.fill(companyWebsite);

        return this;
    }

    @Step("Fill company primary contact field")
    public AddCompanyDialog fillCompanyPrimaryContactField(String companyPrimaryContact) {
        companyPrimaryContactField.fill(companyPrimaryContact);

        return this;
    }

    @Step("Fill company email field")
    public AddCompanyDialog fillCompanyEmailField(String companyEmail) {
        companyEmailField.fill(companyEmail);

        return this;
    }

    @Step("Fill company country field")
    public AddCompanyDialog fillCompanyCountryField(String companyCountry) {
        companyCountryField.fill(companyCountry);

        return this;
    }

    @Step("Fill company state field")
    public AddCompanyDialog fillCompanyStateField(String companyState) {
        companyStateField.fill(companyState);

        return this;
    }

    @Step("Fill company ZIP field")
    public AddCompanyDialog fillCompanyZipField(String companyZip) {
        companyZipField.fill(companyZip);

        return this;
    }

    @Step("Fill company city field")
    public AddCompanyDialog fillCompanyCityField(String companyCity) {
        companyCityField.fill(companyCity);

        return this;
    }

    @Step("Fill company phone field")
    public AddCompanyDialog fillCompanyPhoneField(String companyPhone) {
        companyPhoneField.fill(companyPhone);

        return this;
    }

    @Step("Fill company mobile field")
    public AddCompanyDialog fillCompanyMobileField(String companyMobile) {
        companyMobileField.fill(companyMobile);

        return this;
    }

    @Step("Fill company fax field")
    public AddCompanyDialog fillCompanyFaxField(String companyFax) {
        companyFaxField.fill(companyFax);

        return this;
    }

    @Step("Click on the 'Create' button and trigger an error")
    public AddCompanyDialog clickCreateButtonAndTriggerError() {
        createButton.click();

        return this;
    }

    @Step("Click on the 'Create' button")
    public AddCompanyDialog clickCreateButton() {
        createButton.click();

        return this;
    }

    public Locator getErrorMessage() {
        errorMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return errorMessage;
    }

    public Locator getAlertMessage() {
        alertMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return alertMessage;
    }

    public List<String> getAllFieldPlaceholders() {
        allFieldPlaceholders.first().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return allFieldPlaceholders.all().stream().map(l -> l.getAttribute("placeholder")).toList();
    }

    @Step("Click 'Close' button")
    public CompaniesAndBusinessUnitsPage clickCloseButton() {
        closeButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
