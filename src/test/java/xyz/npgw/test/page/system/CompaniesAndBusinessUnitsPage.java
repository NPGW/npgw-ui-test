package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.common.SelectCompanyTrait;
import xyz.npgw.test.page.dialog.company.AddCompanyDialog;
import xyz.npgw.test.page.dialog.company.EditCompanyDialog;
import xyz.npgw.test.page.dialog.merchant.AddBusinessUnitDialog;

public class CompaniesAndBusinessUnitsPage extends BaseSystemPage<CompaniesAndBusinessUnitsPage>
        implements SelectCompanyTrait<CompaniesAndBusinessUnitsPage> {

    private final Locator addCompanyButton = locator("button[data-testid='AddCompanyButton']");
    @Getter
    private final Locator addBusinessUnitButton = getByTestId("ButtonAddMerchant");
    @Getter
    private final Locator editCompanyButton = getByTestId("EditCompanyButton");
    @Getter
    private final Locator businessUnitEmptyList = locator("[role='gridcell']");
    private final Locator successAlert = alert("SUCCESS");
    @Getter
    private final Locator addCompanyDialog = dialog();
    @Getter
    private final Locator descriptionFromCompanyInfoSection = labelExact("Description");
    @Getter
    private final Locator websiteFromCompanyInfoSection = labelExact("Website");
    @Getter
    private final Locator primaryContactFromCompanyInfoSection = labelExact("Primary contact");
    @Getter
    private final Locator emailFromCompanyInfoSection = labelExact("Email");
    @Getter
    private final Locator phoneFromCompanyInfoSection = labelExact("Phone");
    @Getter
    private final Locator mobileFromCompanyInfoSection = labelExact("Mobile");
    @Getter
    private final Locator faxFromCompanyInfoSection = labelExact("Fax");
    @Getter
    private final Locator countryFromCompanyInfoSection = labelExact("Country");
    @Getter
    private final Locator stateFromCompanyInfoSection = labelExact("State");
    @Getter
    private final Locator zipFromCompanyInfoSection = labelExact("ZIP");
    @Getter
    private final Locator cityFromCompanyInfoSection = labelExact("City");
    @Getter
    private final Locator apiActiveCheckboxFromCompanyInfoSection = labelExact("API active");
    @Getter
    private final Locator portalActiveCheckboxFromCompanyInfoSection = labelExact("Portal active");
    @Getter
    private final Locator businessUnitNameData = locator("[role='row'] span").first();
    @Getter
    private final Locator merchantIdData = locator("[role='row'] span").nth(1);

    public CompaniesAndBusinessUnitsPage(Page page) {
        super(page);
    }

    protected Locator alert(String text) {
        return getPage().getByRole(AriaRole.ALERT, new Page.GetByRoleOptions().setName(text));
    }

    @Step("Click 'Add company' button")
    public AddCompanyDialog clickAddCompanyButton() {
        addCompanyButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        addCompanyButton.click();

        return new AddCompanyDialog(getPage());
    }

    public CompaniesAndBusinessUnitsPage waitUntilAlertIsGone() {
        successAlert.waitFor();
        successAlert.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        return this;
    }

//    @Step("Select a company into 'Select company' filter field")
//    public CompaniesAndBusinessUnitsPage selectCompanyInTheFilter(String name) {
//        companyDropdown.click();
//        companyDropdown.fill(name);
//        getPage().locator("li[role='option']:has-text('%s')".formatted(name)).first().click();
//
//        return this;
//    }


    @Step("Click 'Add business unit' button (+)")
    public AddBusinessUnitDialog clickOnAddBusinessUnitButton() {
        addBusinessUnitButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED));
        getPage().waitForCondition(addBusinessUnitButton::isEnabled);

        addBusinessUnitButton.click();

        return new AddBusinessUnitDialog(getPage());
    }

    @Step("Click 'Edit company' button")
    public EditCompanyDialog clickEditCompanyButton() {
        //getPage().waitForLoadState(LoadState.NETWORKIDLE);
        getPage().getByText("Loading").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        getPage().getByText("Loading").waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
        editCompanyButton.click();

        return new EditCompanyDialog(getPage());
    }
}
