package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

public final class SystemAdministrationPage extends BasePage {

    private final Locator merchants = getPage().locator("//*[@id='react-aria5710153641-:ruj:-tabpanel-merchants']/div/div[1]/div/button[2]/svg");
    private final Locator resetFilter = getPage().locator("button:has(svg[data-icon='xmark'])");
    private final Locator refreshData = getPage().locator("button:has(svg[data-icon='arrows-rotate'])");
    private final Locator selectCompanyField = placeholder("Select company");
    private final Locator selectStatusField = placeholder("Status");
    private final Locator merchantName = placeholder("Merchant name");
    private final Locator checkBoxUSD = checkbox("USD");
    private final Locator checkBoxEUR = checkbox("EUR");
    private final Locator createMerchantButton = button("Create");
    private final Locator closeMerchantCreationWindowButton = button("Close");

    public SystemAdministrationPage(Page page) {
        super(page);
    }

    public SystemAdministrationPage goToMerchants() {
        merchants.click();
        return this;
    }

    public SystemAdministrationPage resetFilters() {
        resetFilter.click();
        return this;
    }

    public SystemAdministrationPage refreshData() {
        refreshData.click();
        return this;
    }

    public SystemAdministrationPage selectFiltersByCompany(String companyName) {
        selectCompanyField.click();
        getPage().locator("text=" + companyName).click();
        return this;
    }

    public SystemAdministrationPage selectCompanyStatus(String status) {
        List<String> validStatuses = List.of("All", "Active", "Inactive");

        if (!validStatuses.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status +
                    ". Allowed values: " + validStatuses);
        }

        selectStatusField.click();
        getPage().locator("text=" + status).click();
        return this;
    }

    public SystemAdministrationPage setMerchantName(String name) {
        merchantName.fill(name);
        return this;
    }

    public SystemAdministrationPage checkUSD() {
        checkBoxUSD.click();
        return this;
    }

    public SystemAdministrationPage checkEUR() {
        checkBoxEUR.click();
        return this;
    }

    public SystemAdministrationPage chooseMerchantStatus(String status) {
        List<String> validStatuses = List.of("Active", "Inactive");

        if (!validStatuses.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status +
                    ". Allowed values: " + validStatuses);
        }

        getPage().getByPlaceholder(status).click();
        return this;
    }

    public SystemAdministrationPage clickOnCreateButton() {
        createMerchantButton.click();
        return this;
    }

    public SystemAdministrationPage clickOnCloseButton() {
        closeMerchantCreationWindowButton.click();
        return this;
    }

    public String getPrefilledCompanyName() {
        return getPage().locator("//*[@id=':r11e:']/div[1]/div/div/div[1]/div/div").getAttribute("value");
    }
}
