package xyz.npgw.test.run;

import com.google.gson.Gson;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Route;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
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
import xyz.npgw.test.page.dialog.transactions.RefundTransactionDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static xyz.npgw.test.common.Constants.BUSINESS_UNIT_FOR_TEST_RUN;
import static xyz.npgw.test.common.Constants.COMPANY_NAME_FOR_TEST_RUN;

public class TransactionsTableTest extends BaseTest {

    private static final String MERCHANT_TITLE = "%s test transaction table merchant".formatted(RUN_ID);
    private static final List<String> COLUMNS_HEADERS = List.of(
            "Creation Date (GMT)",
            "Business unit ID",
            "NPGW reference",
            "Business unit reference",
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

    @Test
    @TmsLink("311")
    @Epic("Transactions")
    @Feature("Amount")
    @Description("Filtering transactions by Amount")
    public void testFilterTransactionsByAmount() {
        String amountFrom = "10.12";
        String amountTo = "500.34";

        List<String> amountValues = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .clickAmountButton()
                .fillAmountFromField(amountFrom)
                .fillAmountToField(amountTo)
                .clickAmountApplyButton()
                .getTable().getColumnValues("Amount");

        Allure.step("Verify: Amount column has values within the specified amount range");
        assertFalse(amountValues.isEmpty());
        assertTrue(amountValues.stream()
                .map(Double::parseDouble)
                .allMatch(value -> value >= Double.parseDouble(amountFrom) && value <= Double.parseDouble(amountTo)));
    }

    @Test(dataProvider = "getCardType", dataProviderClass = TestDataProvider.class)
    @TmsLink("673")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Filtering transactions by Card type displays only matching entries in the table.")
    public void testFilterByCardType(String cardType) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        Allure.step("Verify: transaction page table has data");
        assertThat(transactionsPage.getTable().getNoRowsToDisplayMessage()).isHidden();

        List<String> cardTypeList = transactionsPage.selectCardType(cardType)
                .getTable().getCardTypeColumnValuesAllPages();

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

    @Test(dataProvider = "getStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("679")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Compare number of transactions with selected statuses in the table before and after filter")
    public void testFilterByStatus(String status) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        Allure.step("Verify: transaction page table has data");
        assertThat(transactionsPage.getTable().getNoRowsToDisplayMessage()).isHidden();

        int statusesCount = transactionsPage
                .getTable().countValues("Status", status);

        int filteredTransactionCount = transactionsPage
                .getSelectStatus().select(status)
                .getTable().countValues("Status", status);

        int totalFilteredRows = transactionsPage.getTable().countAllRows();

        Allure.step("Verify: All transactions with selected statuses are shown after filter.");
        assertEquals(statusesCount, filteredTransactionCount);

        Allure.step("Verify: Only transactions with selected statuses are shown after filter.");
        assertEquals(totalFilteredRows, filteredTransactionCount);
    }

    @Test(dataProvider = "getCurrency", dataProviderClass = TestDataProvider.class)
    @TmsLink("319")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Filtering transactions by Currency")
    public void testFilterTransactionsByCurrency(String currency) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        Allure.step("Verify: transaction page table has data");
        assertThat(transactionsPage.getTable().getNoRowsToDisplayMessage()).isHidden();

        List<String> currencyValues = transactionsPage.clickCurrencySelector()
                .selectCurrency(currency)
                .getTable().getColumnValuesFromAllPages("Currency", Function.identity());

        Allure.step("Verify: Filter displays the selected currency");
        assertThat(transactionsPage.getCurrencySelector()).containsText(currency);

        Allure.step("Verify: All values in the Currency column match the selected currency");
        assertTrue(currencyValues.stream().allMatch(value -> value.equals(currency)));
    }

    @Test
    @TmsLink("682")
    @Epic("Transactions")
    @Feature("Filter")
    @Description("Verify that transactions are present in the table when a currency filter is applied on the last page")
    public void testTableDisplayWhenCurrencyFilterAppliedWhileOnLastPage() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        Allure.step("Verify: Transactions are present in the table");
        assertThat(transactionsPage.getTable().getRows()).not().hasCount(0);

        transactionsPage
                .getTable().goToLastPageIfNeeded();

        transactionsPage
                .clickCurrencySelector().selectCurrency("EUR");

        Allure.step("Verify: Transactions are still present then filter is applied on the last page");
        assertThat(transactionsPage.getTable().getRows()).not().hasCount(0);
    }

    @Test
    @TmsLink("559")
    @Epic("Transactions")
    @Feature("Table sorting")
    @Description("'Creation Date' column sorts ascending by default and descending on click.")
    public void testSortByCreationDate() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        List<LocalDateTime> actualDates = transactionsPage
                .getTable().getAllCreationDates();

        Allure.step("Verify: transactions are sorted by creation date in ascending order by default");
        assertEquals(actualDates, actualDates.stream().sorted().toList());

        transactionsPage
                .getTable().clickSortIcon("Creation Date (GMT)");

        Allure.step(
                "Verify: transactions are sorted by creation date in descending order after clicking the sort icon");
        assertEquals(transactionsPage.getTable().getAllCreationDates(),
                actualDates.stream().sorted(Comparator.reverseOrder()).toList());
    }

    @Test
    @TmsLink("659")
    @Epic("Transactions")
    @Feature("Table sorting")
    @Description("'Amount' column sorts ascending on first click and descending on second click.")
    public void testSortByAmount() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
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

    @Test
    @TmsLink("106")
    @Epic("Transactions")
    @Feature("Pagination")
    @Description("Displaying the default number of rows on the RowsPerPage selector")
    public void testCountSelectorRows() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        Allure.step("Verify: default row count - 25");
        assertThat(transactionsPage.getTable().getRowsPerPage()).containsText("25");
    }

    @Test
    @TmsLink("127")
    @Epic("Transactions")
    @Feature("Pagination")
    @Description("Displaying rows per page options when clicking on the RowsPerPage selector")
    public void testCountOptionsSelectorRows() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .getTable().clickRowsPerPageChevron();

        Allure.step("Verify: displaying all options when clicking on Selector Rows");
        assertThat(transactionsPage.getTable().getRowsPerPageOptions()).hasText(new String[]{"10", "25", "50", "100"});
    }

    @Test
    @TmsLink("130")
    @Epic("Transactions")
    @Feature("Pagination")
    @Description("Verifying that we can switch the page when we click next button")
    public void testPaginationNextButton() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .getTable().selectRowsPerPageOption("10")
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

            Allure.step("Verify: Only two column headers are displayed in the transactions table -"
                    + " '{item}' column header and 'Actions'");
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
        getPage().route("**/status*", route -> {
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
        assertThat(transactionsPage.getTable().getFirstRowCell("NPGW reference")).hasText("12345");
    }

    @Test
    @TmsLink("818")
    @Epic("Transactions")
    @Feature("Actions")
    @Description("Refund button is visible only for transactions with status 'SUCCESS'")
    public void testRefundButtonVisibility() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        List<String> statuses = transactionsPage
                .getTable().getAllTransactionsStatusList();

        List<Boolean> refundVisible = transactionsPage
                .getTable().getRefundButtonVisibilityFromAllPages();

        assertFalse(statuses.isEmpty(), "Statuses list should not be empty");

        for (int i = 0; i < statuses.size(); i++) {
            String status = statuses.get(i).trim();
            boolean isVisible = refundVisible.get(i);

            if ("SUCCESS".equals(status)) {
                Allure.step("Verify: refund button is visible at row " + i + " with status: " + status);
                assertTrue(isVisible);
            } else {
                Allure.step("Verify: refund button is NOT visible at row " + i + " with status: " + status);
                assertFalse(isVisible);
            }
        }
    }

    @Test
    @TmsLink("863")
    @Epic("Transactions")
    @Feature("Actions")
    @Description("All refundable transactions display correct refund dialog text and pre-filled refund amount.")
    public void testRefundDialogDisplaysCorrectTextAndAmount() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        do {
            List<Locator> currentRows = transactionsPage.getTable().getRows().all();

            for (Locator row : currentRows) {
                Locator refundButton = transactionsPage.getTable().getRefundButton(row);

                if (refundButton.count() > 0 && refundButton.isVisible()) {
                    String amount = transactionsPage.getTable().getCell(row, "Amount").innerText().trim();
                    String currency = transactionsPage.getTable().getCell(row, "Currency").innerText().trim();

                    RefundTransactionDialog refundTransactionDialog = transactionsPage
                            .getTable().clickRefundTransaction(row);

                    Allure.step("Verify: refund dialog header is correct");
                    assertThat(refundTransactionDialog.getDialogHeader()).hasText("Refund transaction");

                    Allure.step("Verify: refund message contains correct max amount and currency");
                    assertThat(refundTransactionDialog.getRefundMessage(
                            String.format("Enter amount up to %s %s to refund?", amount, currency))).isVisible();

                    Allure.step("Verify: refund amount input is pre-filled with correct value");
                    assertThat(refundTransactionDialog.getAmountToRefundInput()).hasValue(amount);

                    Allure.step("Verify: increase amount button to refund is disabled");
                    assertThat(refundTransactionDialog.getIncreaseAmountToRefundButton()).isDisabled();

                    refundTransactionDialog.clickCloseIcon();
                }
            }

        } while (transactionsPage.getTable().goToNextPage());
    }

    @Test
    public void testTransactionTableMatchesDownloadedCsv() throws IOException {
        // 1. Открываем страницу и применяем фильтры
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        // 2. Собираем данные с UI
        List<List<String>> rowsFromUI = new ArrayList<>();
        do {
            List<Locator> rows = transactionsPage.getTable().getRows().all();

            for (Locator row : rows) {
                List<String> rowData = new ArrayList<>();
                rowData.add(transactionsPage.getTable().getCell(row, "Creation Date (GMT)").innerText().trim());
                rowData.add(transactionsPage.getTable().getCell(row, "NPGW reference").innerText().trim());
                rowData.add(transactionsPage.getTable().getCell(row, "Business unit reference").innerText().trim());
                rowData.add(transactionsPage.getTable().getCell(row, "Amount").innerText().trim());
                rowData.add(transactionsPage.getTable().getCell(row, "Currency").innerText().trim());
                // Добавь нужные столбцы
                rowsFromUI.add(rowData);
            }
        } while (transactionsPage.getTable().goToNextPage());

        transactionsPage.getTable().goToFirstPageIfNeeded();

        // 3. Скачиваем CSV
        Download download = getPage().waitForDownload(() -> {
            transactionsPage.clickExportToCsv(); // или: getPage().locator(...).click()
        });

        Path targetPath = Paths.get("downloads", download.suggestedFilename());
        download.saveAs(targetPath);

        // 4. Читаем CSV
        List<List<String>> rowsFromCsv = readCsv(targetPath);
        rowsFromCsv.remove(0); // Убираем заголовки, если есть

        // 5. Сравнение
        assertEquals(rowsFromUI.size(), rowsFromCsv.size(), "Row count mismatch between UI and CSV");

        for (int i = 0; i < rowsFromUI.size(); i++) {
            List<String> uiRow = rowsFromUI.get(i);
            List<String> csvRow = rowsFromCsv.get(i);

            for (int j = 0; j < uiRow.size(); j++) {
                String fromUI = normalize(uiRow.get(j));
                String fromCSV = normalize(csvRow.get(j));
                assertEquals(fromUI, fromCSV, String.format("Mismatch at row %d, column %d", i + 1, j + 1));
            }
        }
    }

    public List<List<String>> readCsv(Path csvFilePath) throws IOException {
        List<List<String>> rows = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(csvFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> cells = Arrays.stream(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                        .map(s -> s.replaceAll("^\"|\"$", "").trim())
                        .toList();
                rows.add(cells);
            }
        }

        return rows;
    }

    public String normalize(String value) {
        if (value == null) return "";
        value = value.trim();
        if (value.matches("\\d+\\.00")) return value.replace(".00", "");
        return value;
    }
}
