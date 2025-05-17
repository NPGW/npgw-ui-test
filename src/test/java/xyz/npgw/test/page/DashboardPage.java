package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.common.AlertTrait;
import xyz.npgw.test.page.common.DateRangePickerTrait;
import xyz.npgw.test.page.common.HeaderPage;
import xyz.npgw.test.page.common.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.SelectCompanyTrait;

public final class DashboardPage extends HeaderPage implements DateRangePickerTrait<DashboardPage>,
        AlertTrait<DashboardPage>, SelectBusinessUnitTrait<DashboardPage>,
        SelectCompanyTrait<DashboardPage> {

    private final Locator refreshDataButton = locator("[data-icon='arrows-rotate']");
    private final Locator resetFilterButton = getByTestId("ResetFilterButtonDashboardPage");
    @Getter
    private final Locator currencySelector = getByLabelExact("Currency");

    public DashboardPage(Page page) {
        super(page);
    }

    @Step("Click 'Refresh data' button")
    public DashboardPage clickRefreshDataButton() {
        refreshDataButton.click();

        return this;
    }

    @Step("Reload dashboard page")
    public DashboardPage reloadDashboard() {
        getPage().reload();

        return this;
    }

    @Step("Click 'Reset filter' button")
    public DashboardPage clickResetFilterButton() {
        resetFilterButton.click();

        return this;
    }

    @Step("Click Currency Selector")
    public DashboardPage clickCurrencySelector() {
        currencySelector.click();

        return this;
    }

    @Step("Select currency from dropdown menu")
    public DashboardPage selectCurrency(String value) {
        getByRole(AriaRole.OPTION, value).click();
        getPage().locator("h2").filter(new Locator.FilterOptions().setHasText(value)).first().waitFor();

        return this;
    }
}
