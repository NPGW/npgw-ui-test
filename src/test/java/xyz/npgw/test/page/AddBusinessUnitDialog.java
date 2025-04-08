package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.base.BasePage;
import xyz.npgw.test.page.systemadministration.CompaniesAndBusinessUnitsPage;

public class AddBusinessUnitDialog extends BasePage {

    @Getter
    private final Locator companyNameField = locator("input[aria-label='Company name']");
    private final Locator closeButton = textExact("Close");

    public AddBusinessUnitDialog(Page page) {
        super(page);
    }

    public CompaniesAndBusinessUnitsPage clickOnCloseButton() {
        closeButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
