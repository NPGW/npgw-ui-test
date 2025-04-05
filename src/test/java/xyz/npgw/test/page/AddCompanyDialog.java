package xyz.npgw.test.page;

import io.qameta.allure.Step;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import xyz.npgw.test.page.base.BaseDialog;
import xyz.npgw.test.page.systemadministration.CompaniesAndBusinessUnitsPage;

public class AddCompanyDialog extends BaseDialog {

    private final Locator addCompanyDialogHeader = locator("section header");
    private final Locator closeButton = textExact("Close");

    public AddCompanyDialog(Page page) {
        super(page);
    }

    public Locator getAddCompanyDialogHeader() {
        return addCompanyDialogHeader;
    }

    @Step("Click 'Close' button")
    public CompaniesAndBusinessUnitsPage clickCloseButton() {
        closeButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
