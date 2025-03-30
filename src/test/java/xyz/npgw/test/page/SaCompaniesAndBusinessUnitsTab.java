package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePageWithHeader;

public class SaCompaniesAndBusinessUnitsTab extends BasePageWithHeader {

    private final Locator addCompanyButton = locator("svg[data-icon='circle-plus']").first();

    public SaCompaniesAndBusinessUnitsTab(Page page) {
        super(page);
    }

    @Step("Click (+) with 'Add company' tooltip")
    public AddCompanyWindow clickAddCompany() {
        addCompanyButton.click();

        return new AddCompanyWindow(getPage());
    }
}
