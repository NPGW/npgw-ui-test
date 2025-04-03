package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePage;

public class AddCompanyDialog extends BasePage {

    private final Locator addCompanyDialogHeader = locator("section header");
    private final Locator closeButton = button("Close").last();

    public AddCompanyDialog(Page page) {
        super(page);
    }

    public Locator getAddCompanyDialogHeader() {
        return addCompanyDialogHeader;
    }

    @Step("Click 'Close' button")
    public SaCompaniesAndBusinessUnitsTab clickCloseButton() {
        closeButton.click();

        return new SaCompaniesAndBusinessUnitsTab(getPage());
    }
}
