package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.trait.TableTrait;

public class TeamPage extends SystemBasePage implements TableTrait {

    private final Locator companiesAndBusinessUnitsTabButton = tab("Companies and business units");

    public TeamPage(Page page) {
        super(page);
    }

    @Step("Click 'Companies and business units' button")
    public CompaniesAndBusinessUnitsPage clickCompaniesAndBusinessUnitsTabButton() {
        companiesAndBusinessUnitsTabButton.click();

        return new CompaniesAndBusinessUnitsPage(getPage());
    }
}
