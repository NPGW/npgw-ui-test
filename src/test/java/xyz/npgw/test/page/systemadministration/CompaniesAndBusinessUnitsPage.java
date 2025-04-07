package xyz.npgw.test.page.systemadministration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.AddCompanyDialog;
import xyz.npgw.test.page.base.SystemAdministrationWithTableBasePage;

public class CompaniesAndBusinessUnitsPage extends SystemAdministrationWithTableBasePage {

    private final Locator addCompanyButton = locator("svg[data-icon='circle-plus']").first();
    @Getter
    private final Locator addBusinessUnitButton = locator("svg[data-icon='circle-plus']").nth(1);
    private final Locator companyDropdown = locator("[role='combobox'][aria-label='Select company']");
    @Getter
    private final Locator addCompanyDialog = dialog();

    public CompaniesAndBusinessUnitsPage(Page page) {
        super(page);
    }

    @Step("Click 'Add company' button")
    public AddCompanyDialog clickAddCompanyButton() {
        addCompanyButton.click();

        return new AddCompanyDialog(getPage());
    }

    @Step("Select a company into 'Select company' filter field")
    public CompaniesAndBusinessUnitsPage selectCompany(String name) {
        Locator alert = getPage().locator("[role='alert']");
        alert.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        alert.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        companyDropdown.click();
        companyDropdown.fill(name);
        Locator firstOption = getPage().locator("li[role='option']:has-text('%s')".formatted(name)).first();
        firstOption.scrollIntoViewIfNeeded();
        firstOption.click();

        addBusinessUnitButton.waitFor(new Locator.WaitForOptions()
                .setState(WaitForSelectorState.ATTACHED));
        getPage().waitForCondition(addBusinessUnitButton::isEnabled);

        return this;
    }
}
