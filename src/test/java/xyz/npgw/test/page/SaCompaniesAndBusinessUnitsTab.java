package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePageWithHeader;

public class SaCompaniesAndBusinessUnitsTab extends BasePageWithHeader {

    private final Locator addCompanyButton = locator("svg[data-icon='circle-plus']").first();
    private final Locator addCompanyDialog = dialog();

    public SaCompaniesAndBusinessUnitsTab(Page page) {
        super(page);
    }

    @Step("Click 'Add company' button")
    public AddCompanyDialog clickAddCompanyButton() {
        addCompanyButton.click();

        return new AddCompanyDialog(getPage());
    }

    public Locator getAddCompanyDialog() {
        addCompanyDialog.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        return addCompanyDialog;
    }

}
