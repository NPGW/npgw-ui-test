package xyz.npgw.test.page.systemadministration;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.AddCompanyDialog;
import xyz.npgw.test.page.base.SystemAdministrationWithTableBasePage;

public class CompaniesAndBusinessUnitsPage extends SystemAdministrationWithTableBasePage {

    private final Locator addCompanyButton = locator("button[data-testid='AddCompanyButton']");
    @Getter
    private final Locator addCompanyDialog = dialog();
    private final Locator alertMessage = locator("[role='alert']");
    private final Locator companyNameDropdownList = locator("[role='option']");
    private final Locator selectCompanyDropdown = locator("[aria-label='Show suggestions']:nth-child(2)");
    private final Locator lastDropdownOption = locator("[role='option']:last-child");
    @Getter
    private final Locator selectCompanyInput = placeholder("Search...");
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
    private final Locator zipFromCompanyInfoSection = labelExact("ZIP");
    @Getter
    private final Locator cityFromCompanyInfoSection = labelExact("City");
    @Getter
    private final Locator apiActiveCheckboxFromCompanyInfoSection = labelExact("API active");
    @Getter
    private final Locator portalActiveCheckboxFromCompanyInfoSection = labelExact("Portal active");


    public CompaniesAndBusinessUnitsPage(Page page) {
        super(page);
    }

    @Step("Click 'Add company' button")
    public AddCompanyDialog clickAddCompanyButton() {
        addCompanyButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        addCompanyButton.click();

        return new AddCompanyDialog(getPage());
    }

    public Locator getAlertMessage() {
        alertMessage.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return alertMessage;
    }

    @Step("Click 'Select company' dropdown")
    public CompaniesAndBusinessUnitsPage clickSelectCompanyDropdown() {
        getPage().waitForTimeout(1000);
        selectCompanyDropdown.click();

        return this;
    }

    public CompaniesAndBusinessUnitsPage clickCompanyInDropdown(String companyName) {
        String lastSeenText = "";

        while (true) {
            Locator options = companyNameDropdownList;
            int count = options.count();

            for (int i = 0; i < count; i++) {
                String text = options.nth(i).innerText().trim();
                if (text.equals(companyName)) {
                    options.nth(i).click();
                    return this;
                }
            }

            String currentLastText = options.nth(count - 1).innerText().trim();
            if (currentLastText.equals(lastSeenText)) {
                break;
            }

            lastSeenText = currentLastText;

            lastDropdownOption.scrollIntoViewIfNeeded();
        }

        throw new RuntimeException("Company '" + companyName + "' not found in dropdown.");
    }
}
