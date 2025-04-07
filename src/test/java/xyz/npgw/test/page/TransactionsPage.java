package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.Getter;
import xyz.npgw.test.page.base.BasePageWithHeaderAndTable;

@Getter
public class TransactionsPage extends BasePageWithHeaderAndTable {

    private final Locator rowsPerPageButton = getButtonByName("Rows Per Page");
    private final Locator rowsPerPageOptions = getDialog();
    @Getter(AccessLevel.NONE)
    private final Locator nextPageButton = getButtonByName("next page button");
    private final Locator paginationItemTwoActiveButton = getButtonByName("pagination item 2 active");
    private final Locator dateRangePicker = getGroupByName("DateRange");
    private final Locator businessUnitSelector = getLocatorByExactText("Business unit").locator("../../..");
    private final Locator currencySelector = getLabelByExactText("Currency");
    private final Locator paymentMethodSelector = getLabelByExactText("Payment method");
    private final Locator statusSelector = getLabelByExactText("Status");
    private final Locator amountButton = getButtonByName("Amount");
    private final Locator resetFilterButton = getLocatorBySelector("svg[data-icon='xmark']");
    private final Locator applyDataButton = getLocatorBySelector("svg[data-icon='filter']");
    private final Locator settingsButton = getLocatorBySelector("svg[data-icon='gear']");
    private final Locator downloadButton = getLocatorBySelector("svg[data-icon='download']");

    public TransactionsPage(Page page) {
        super(page);
    }

    @Step("Click Currency Selector")
    public TransactionsPage clickCurrencySelector() {
        currencySelector.click();

        return this;
    }

    @Step("Click Options Currency {value}")
    public TransactionsPage clickCurrency(String value) {
        getLabelByExactText(value).click();

        return this;
    }

    @Step("Click Icon Apply Data")
    public TransactionsPage clickApplyDataButton() {
        applyDataButton.click();

        return this;
    }

    public boolean getTableRow(String value) {
        for (Locator item : getPage().locator("tbody tr").all()) {
            if (!item.locator("td").nth(5).textContent().equals(value)) {
                return false;
            }
        }
        return true;
    }

    @Step("Click Button Rows Per Page")
    public TransactionsPage clickRowsPerPageButton() {
        rowsPerPageButton.click();

        return this;
    }

    @Step("Click next Page Button")
    public TransactionsPage clickNextPageButton() {
        nextPageButton.click();

        return this;
    }
}
