package xyz.npgw.test.run;

import com.google.gson.Gson;
import com.microsoft.playwright.Route;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.BusinessUnit;
import xyz.npgw.test.common.entity.CardType;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.Status;
import xyz.npgw.test.common.entity.Transaction;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.TransactionsPage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

public class TransactionsTableTest extends BaseTest {

    private static final String MERCHANT_TITLE = "%s test transaction table merchant".formatted(RUN_ID);
    private static final List<String> COLUMNS_HEADERS = List.of(
            "Creation Date",
            "Business unit ID",
            "NPGW Reference",
            "Merchant Reference",
            "Amount",
            "Currency",
            "Card type",
            "Status");

    private BusinessUnit businessUnit;

    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        businessUnit = TestUtils.createBusinessUnit(getApiRequestContext(), getCompanyName(), MERCHANT_TITLE);
    }

    @Ignore("0.1.2506170300-nightly")
    @Test
    @TmsLink("311")
    @Epic("Transactions")
    @Feature("Amount")
    @Description("Filtering transactions by Amount")
    public void testFilterTransactionsByAmount() {
        int amountFrom = 10;
        int amountTo = 500;

        List<String> amountValues = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields("01-04-2025", "01-05-2025")
                .clickAmountButton()
                .fillAmountFromField(String.valueOf(amountFrom))
                .fillAmountToField(String.valueOf(amountTo))
                .clickAmountApplyButton()
                .getTable().getColumnValues("Amount");

        Allure.step("Verify: Amount column has values within the specified amount range");
        assertTrue(amountValues.stream()
                .map(Integer::parseInt)
                .allMatch(value -> value >= amountFrom && value <= amountTo));
    }

    @Test(dataProvider = "getCardType", dataProviderClass = TestDataProvider.class)
    @TmsLink("673")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Filtering transactions by Card type displays only matching entries in the table.")
    public void testFilterByCardType(String cardType) {
        List<String> cardTypeList = new DashboardPage(getPage())
                .clickTransactionsLink()
                .selectCardType(cardType)
                .getTable().getColumnValuesFromAllPages("Card type", Function.identity());

        Allure.step("Verify: all entries in the 'Card type' column match the selected filter");
        assertTrue(cardTypeList.stream().allMatch(value -> value.equals(cardType)));
    }

    @Test
    @TmsLink("677")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Filtering transactions by date range")
    public void testFilteringTransactionsByDateRange() {
        String startDate = "01-06-2025";
        String endDate = "05-06-2025";

        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(startDate, endDate)
                .clickRefreshDataButton();

        Allure.step("Verify: Transactions can be filtered by date range");
        Assert.assertTrue(transactionsPage.getTable().isBetween(startDate, endDate));
    }

    // TODO bug - status isn't sent to server
    @Ignore("multistatus not working atm")
    @Test(expectedExceptions = AssertionError.class,
            dataProvider = "getMultiStatus2", dataProviderClass = TestDataProvider.class)
    @TmsLink("679")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Compare number of transactions with selected statuses in the table before and after filter")
    public void testFilterByStatus(String firstStatus, String secondStatus) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields("01-06-2025", "06-06-2025");

        int statusesCount = transactionsPage
                .getTable().countValues("Status", firstStatus, secondStatus);

        int filteredTransactionCount = transactionsPage
                .getSelectStatus().selectTransactionStatuses(firstStatus, secondStatus)
                .getTable().countValues("Status", firstStatus, secondStatus);

        int totalFilteredRows = transactionsPage.getTable().countAllRows();

        Allure.step("Verify: All transactions with selected statuses are shown after filter.");
        assertEquals(statusesCount, filteredTransactionCount);

        Allure.step("Verify: Only transactions with selected statuses are shown after filter.");
        assertEquals(totalFilteredRows, filteredTransactionCount);
    }

    @Ignore("0.1.2506170300-nightly")
    @Test(dataProvider = "getCurrency", dataProviderClass = TestDataProvider.class)
    @TmsLink("319")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Filtering transactions by Currency")
    public void testFilterTransactionsByCurrency(String currency) {
        List<String> currencyValues = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields("01-05-2025", "30-05-2025")
                .clickCurrencySelector()
                .selectCurrency(currency)
                .getTable().getColumnValuesFromAllPages("Currency", Function.identity());

        Allure.step("Verify: All values in the Currency column match the selected currency");
        assertTrue(currencyValues.stream().allMatch(value -> value.equals(currency)));
    }

    @Ignore("0.1.2506170300-nightly")
    // TODO bug - transactions isn't present in the table when a currency filter is applied on the last page
    @Test(expectedExceptions = AssertionError.class)
    @TmsLink("682")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Verify that transactions are present in the table when a currency filter is applied on the last page")
    public void testTableDisplayWhenCurrencyFilterAppliedWhileOnLastPage() {
        String euro = "EUR";

        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields("26-05-2025", "31-05-2025");

        int numberWithEuroInTable = transactionsPage.getTable().countValues("Currency", euro);
        transactionsPage.getTable().goToLastPageIfNeeded();
        transactionsPage.clickCurrencySelector().selectCurrency(euro);

        Allure.step("Verify: Transactions are present in the table");
        assertTrue(numberWithEuroInTable > 0 && !transactionsPage.getTable().isTableEmpty());
    }

    @Ignore
    @Test
    @TmsLink("559")
    @Epic("Transactions")
    @Feature("Table sorting")
    @Description("'Creation Date' column sorts ascending by default and descending on click.")
    public void testSortByCreationDate() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        List<LocalDateTime> actualDates = transactionsPage
                .getTable().getAllCreationDates();

        Allure.step("Verify: transactions are sorted by creation date in ascending order by default");
        assertEquals(actualDates, actualDates.stream().sorted().toList());

        transactionsPage
                .getTable().clickSortIcon("Creation Date");

        Allure.step(
                "Verify: transactions are sorted by creation date in descending order after clicking the sort icon");
        assertEquals(transactionsPage.getTable().getAllCreationDates(),
                actualDates.stream().sorted(Comparator.reverseOrder()).toList());
    }

    @Ignore
    @Test
    @TmsLink("659")
    @Epic("Transactions")
    @Feature("Table sorting")
    @Description("'Amount' column sorts ascending on first click and descending on second click.")
    public void testSortByAmount() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getTable().selectRowsPerPageOption("100")
                .getTable().clickSortIcon("Amount");

        List<Double> actualAmount = transactionsPage
                .getTable().getAllAmounts();

        Allure.step("Verify: transactions are sorted by amount in ascending order after first click");
        assertEquals(actualAmount, actualAmount.stream().sorted().toList());

        transactionsPage
                .getTable().clickSortIcon("Amount");

        Allure.step("Verify: transactions are sorted by amount in descending order after second click");
        assertEquals(transactionsPage.getTable().getAllAmounts(),
                actualAmount.stream().sorted(Comparator.reverseOrder()).toList());
    }

    @Ignore("0.1.2506170300-nightly")
    @Test
    @TmsLink("106")
    @Epic("Transactions")
    @Feature("Pagination")
    @Description("Displaying the default number of rows on the RowsPerPage selector")
    public void testCountSelectorRows() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: default row count - 25");
        assertThat(transactionsPage.getTable().getRowsPerPage()).containsText("25");
    }

    @Ignore("0.1.2506170300-nightly")
    @Test
    @TmsLink("127")
    @Epic("Transactions")
    @Feature("Pagination")
    @Description("Displaying rows per page options when clicking on the RowsPerPage selector")
    public void testCountOptionsSelectorRows() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getTable().clickRowsPerPageChevron();

        Allure.step("Verify: displaying all options when clicking on Selector Rows");
        assertThat(transactionsPage.getTable().getRowsPerPageOptions()).hasText(new String[]{"10", "25", "50", "100"});
    }

    @Ignore("0.1.2506170300-nightly")
    @Test
    @TmsLink("130")
    @Epic("Transactions")
    @Feature("Pagination")
    @Description("Verifying that we can switch the page when we click next button")
    public void testPaginationNextButton() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields("01-04-2025", "31-05-2025")
                .getTable().clickNextPageButton();

        Allure.step("Verify: button 2 is active");
        assertThat(transactionsPage.getTable().getActivePageButton()).hasText("2");
    }

    @Test
    @TmsLink("350")
    @Epic("Transactions")
    @Feature("Settings")
    @Description("Verify full lists of column headers in table and visible columns from Settings")
    public void testCheckUncheckAllVisibleColumns() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickSettingsButton()
                .checkAllCheckboxInSettings();

        List<String> visibleColumnsLabels = transactionsPage
                .getVisibleColumnsLabels();

        List<String> headersList = transactionsPage
                .clickRefreshDataButton()
                .getTable().getColumnHeadersText();

        List<String> headersListAfterUncheckAllVisibleColumns = transactionsPage
                .clickSettingsButton()
                .uncheckAllCheckboxInSettings()
                .clickRefreshDataButton()
                .getTable().getColumnHeadersText();

        Allure.step("Verify: All column headers are displayed in the Settings");
        assertEquals(visibleColumnsLabels, COLUMNS_HEADERS);

        Allure.step("Verify: All column headers are displayed in the transactions table");
        assertTrue(headersList.containsAll(COLUMNS_HEADERS));

        Allure.step("Verify: Column headers are not displayed in the transactions table "
                + "after it's unchecking in the Settings");
        assertEquals(headersListAfterUncheckAllVisibleColumns.size(), 1);

        Allure.step("Verify: Only 'Actions' is displayed in the transactions table "
                + "after it's unchecking in the Settings");
        assertEquals(headersListAfterUncheckAllVisibleColumns.get(0), "Actions");
    }

    @Test
    @TmsLink("358")
    @Epic("Transactions")
    @Feature("Settings")
    @Description("Check/Uncheck Visible columns in the Settings and verify table column headers")
    public void testCheckUncheckOneVisibleColumn() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickSettingsButton()
                .checkAllCheckboxInSettings();

        COLUMNS_HEADERS.forEach(item -> {
            List<String> headersListAfterUncheckOne = transactionsPage
                    .uncheckVisibleColumn(item)
                    .clickRefreshDataButton()
                    .getTable().getColumnHeadersText();

            Allure.step("Verify: Only one column header is NOT displayed in the Transactions. And it's - '{item}'");
            assertTrue((headersListAfterUncheckOne.size() == COLUMNS_HEADERS.size())
                    && !headersListAfterUncheckOne.contains(item));

            transactionsPage
                    .clickSettingsButton()
                    .checkVisibleColumn(item);
        });

        transactionsPage
                .uncheckAllCheckboxInSettings();

        COLUMNS_HEADERS.forEach(item -> {
            List<String> headersListAfterCheckOnlyOne = transactionsPage
                    .checkVisibleColumn(item)
                    .clickRefreshDataButton()
                    .getTable().getColumnHeadersText();

            Allure.step("Verify: Only two column headers are displayed in the transactions table - '{item}' column header and 'Actions'");
            assertTrue((headersListAfterCheckOnlyOne.size() == 2) && headersListAfterCheckOnlyOne.contains(item));

            transactionsPage
                    .clickSettingsButton()
                    .uncheckVisibleColumn(item);
        });
    }

    @Test
    @TmsLink("712")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Filter transactions by business unit")
    public void testFilterTransactionsByBusinessUnit() {
        getPage().route("**/history*", route -> {
            if (route.request().postData().contains(businessUnit.merchantId())) {
                List<Transaction> transactionList = new ArrayList<>();
                transactionList.add(new Transaction("2025-06-02T04:18:09.047146423Z",
                        "12345", "", 100,
                        CardType.VISA, Currency.USD, Status.FAILED));
                route.fulfill(new Route.FulfillOptions().setBody(new Gson().toJson(transactionList)));
                return;
            }
            route.fallback();
        });

        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(getCompanyName())
                .getSelectBusinessUnit().selectBusinessUnit(MERCHANT_TITLE);

        Allure.step("Verify mock transaction is displayed");
        assertThat(transactionsPage.getTable().getFirstRowCell("NPGW Reference")).hasText("12345");
    }

    @AfterClass
    @Override
    protected void afterClass() {
        super.afterClass();
    }
}
