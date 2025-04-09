package xyz.npgw.test.page.systemadministration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.AddBusinessUnitDialog;
import xyz.npgw.test.page.AddCompanyDialog;
import xyz.npgw.test.page.base.SystemAdministrationWithTableBasePage;

public class CompaniesAndBusinessUnitsPage extends SystemAdministrationWithTableBasePage {

    private final Locator addCompanyButton = locator("button[data-testid='AddCompanyButton']");
    @Getter
    private final Locator addBusinessUnitButton = testId("ButtonAddMerchant");
    @Getter
    private final Locator editCompanyButton = testId("EditCompanyButton");
    private final Locator companyDropdown = labelExact("Select company");
    @Getter
    private final Locator businessUnitEmptyList = locator("[role='gridcell']");
    private final Locator successAlert = alert("SUCCESS");
    @Getter
    private final Locator addCompanyDialog = dialog();
    private final Locator alertMessage = locator("[role='alert']");
    private final Locator companyNameDropdownList = locator("[role='option']");
    private final Locator selectCompanyDropdown = locator("[aria-label='Show suggestions']:nth-child(2)");
    private final Locator lastDropdownOption = locator("[role='option']:last-child");

    public CompaniesAndBusinessUnitsPage(Page page) {
        super(page);
    }

    protected Locator testId(String text) {
        return getPage().getByTestId(text);
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

    @Step("Select a company into 'Select company' filter field")
    public CompaniesAndBusinessUnitsPage selectCompanyInTheFilter(String name) {
        companyDropdown.click();
        companyDropdown.fill(name);
        getPage().locator("li[role='option']:has-text('%s')".formatted(name)).first().click();

        return this;
    }

    public Locator getAlertMessage () {
        alertMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return alertMessage;
    }

    @Step("Click 'Select company' dropdown")
    public CompaniesAndBusinessUnitsPage clickSelectCompanyDropdown () {
        getPage().waitForTimeout(1000);
        selectCompanyDropdown.click();

        return this;
    }

    public AddBusinessUnitDialog clickOnAddBusinessUnitButton () {
        addBusinessUnitButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED));
        getPage().waitForCondition(addBusinessUnitButton::isEnabled);

        addBusinessUnitButton.click();

        return new AddBusinessUnitDialog(getPage());
    }

    public boolean isCompanyInDropdown(String companyName) {
        String lastSeenText = "";

        while (true) {
            Locator options = companyNameDropdownList;
            int count = options.count();

            for (int i = 0; i < count; i++) {
                String text = options.nth(i).innerText().trim();
                if (text.equals(companyName)) {
                    return true;
                }
            }

            String currentLastText = options.nth(count - 1).innerText().trim();
            if (currentLastText.equals(lastSeenText)) {
                break;
            }

            lastSeenText = currentLastText;

            lastDropdownOption.scrollIntoViewIfNeeded();
        }

        return false;
    }
}