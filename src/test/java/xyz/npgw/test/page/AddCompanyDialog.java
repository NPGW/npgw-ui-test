package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BasePage;

public class AddCompanyDialog extends BasePage {

    private final Locator addCompanyDialogHeader = locator("section header");
    private final Locator fieldPlaceholderList = locator("[data-slot='input']:not([placeholder='Search...'])");
//    private final Locator companyNameField = locator("input[aria-label='Company name']");
//    private final Locator companyTypeField = locator("input[aria-label='Company type']");
//    private final Locator descriptionField = locator("input[aria-label='Description']");
//    private final Locator websiteField = locator("input[aria-label='Website']");
//    private final Locator primaryContactField = locator("input[aria-label='Primary contact']");
//    private final Locator companyEmailField = locator("input[aria-label='Company email']");
//    private final Locator countryField = locator("input[aria-label='Country']");
//    private final Locator stateField = locator("input[aria-label='State']");
//    private final Locator zipField = locator("input[aria-label='ZIP']");
//    private final Locator cityField = locator("input[aria-label='City']");
//    private final Locator phoneField = locator("input[aria-label='Phone']");
//    private final Locator mobileField = locator("input[aria-label='Mobile']");
//    private final Locator faxField = locator("input[aria-label='Fax']");

    public AddCompanyDialog(Page page) {
        super(page);
    }

    public Locator getAddCompanyDialogHeader() {
        return addCompanyDialogHeader;
    }

    public Locator getFieldPlaceholderList() {
        getPage().waitForTimeout(2000);
        return fieldPlaceholderList;
    }



//    public Locator getCompanyNameField() {
//        return companyNameField;
//    }
//
//    public Locator getCompanyTypeField() {
//        return companyTypeField;
//    }
//
//    public Locator getDescriptionField() {
//        return descriptionField;
//    }
//
//    public Locator getWebsiteField() {
//        return websiteField;
//    }
//
//    public Locator getPrimaryContactField() {
//        return primaryContactField;
//    }
//
//    public Locator getCompanyEmailField() {
//        return companyEmailField;
//    }
//
//    public Locator getCountryField() {
//        return countryField;
//    }
//
//    public Locator getStateField() {
//        return stateField;
//    }
//
//    public Locator getZipField() {
//        return zipField;
//    }
//
//    public Locator getCityField() {
//        return cityField;
//    }
//
//    public Locator getPhoneField() {
//        return phoneField;
//    }
//
//    public Locator getMobileField() {
//        return mobileField;
//    }
//
//    public Locator getFaxField() {
//        return faxField;
//    }
}
