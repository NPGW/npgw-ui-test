package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.BusinessUnit;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.dialog.TransactionDetailsDialog;

import java.util.List;
import java.util.Random;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static xyz.npgw.test.common.Constants.BUSINESS_UNIT_FOR_TEST_RUN;
import static xyz.npgw.test.common.Constants.COMPANY_NAME_FOR_TEST_RUN;

public class TransactionsPageTest extends BaseTest {

    private static final String COMPANY_NAME = "%s test request company".formatted(RUN_ID);
    private static final String MERCHANT_TITLE = "%s test request merchant".formatted(RUN_ID);
    private final String[] businessUnitNames = new String[]{"Business unit 1", "Business unit 2", "Business unit 3"};
    private BusinessUnit businessUnit;

    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        TestUtils.createBusinessUnits(getApiRequestContext(), getCompanyName(), businessUnitNames);
        TestUtils.createCompany(getApiRequestContext(), COMPANY_NAME);
        businessUnit = TestUtils.createBusinessUnit(getApiRequestContext(), COMPANY_NAME, MERCHANT_TITLE);
    }

    @Test
    @TmsLink("108")
    @Epic("Transactions")
    @Feature("Navigation")
    @Description("User navigate to 'Transactions page' after clicking on 'Transactions' link on the header")
    public void testNavigateToTransactionsPage() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: Transactions Page URL");
        assertThat(transactionsPage.getPage()).hasURL(Constants.TRANSACTIONS_PAGE_URL);

        Allure.step("Verify: Transactions Page Title");
        assertThat(transactionsPage.getPage()).hasTitle(Constants.TRANSACTIONS_URL_TITLE);
    }

    @Test
    @TmsLink("181")
    @Epic("Transactions")
    @Feature("Panel")
    @Description("Verify that on the Transactions Page user can see Panel:"
            + " Date range, Business unit, Currency, Payment method, Status, Amount, Reset filter, "
            + "Apply data, Download file, Settings.")
    public void testVisibilityOfControlPanelElements() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: DataRange picker is visible");
        assertThat(transactionsPage.getSelectDateRange().getDateRangeField()).isVisible();

        Allure.step("Verify: Business Unit selector is visible");
        assertThat(transactionsPage.getBusinessUnitSelector()).isVisible();

        Allure.step("Verify: Currency selector is visible");
        assertThat(transactionsPage.getCurrencySelector()).isVisible();

        Allure.step("Verify: Payment method selector is visible");
        assertThat(transactionsPage.getCardTypeSelector()).isVisible();

        Allure.step("Verify: Status selector is visible");
        assertThat(transactionsPage.getSelectStatus().getStatusSelector()).isVisible();

        Allure.step("Verify: Search 'Trx Ids'  is visible");
        assertThat(transactionsPage.getSearchTrxIdsButton()).isVisible();

        Allure.step("Verify: Amount button is visible");
        assertThat(transactionsPage.getAmountButton()).isVisible();

        Allure.step("Verify: Reset filter button is visible");
        assertThat(transactionsPage.getResetFilterButton()).isVisible();

        Allure.step("Verify: Apply data button is visible");
        assertThat(transactionsPage.getRefreshDataButton()).isVisible();

        Allure.step("Verify: Download button is visible");
        assertThat(transactionsPage.getDownloadButton()).isVisible();

        Allure.step("Verify: Settings button is visible");
        assertThat(transactionsPage.getSettingsButton()).isVisible();
    }

    @Test
    @TmsLink("229")
    @Epic("Transactions")
    @Feature("Status")
    @Description("Verify that user can see selector Status Options")
    public void testTheVisibilityOfTheStatusSelectorOptions() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectStatus().clickSelector();

        Allure.step("Verify: Selector Status Options are visible");
        assertThat(transactionsPage.getSelectStatus().getStatusOptions()).hasText(new String[]{
                "ALL",
                "INITIATED",
                "PENDING",
                "SUCCESS",
                "FAILED",
                "CANCELLED",
                "EXPIRED"
        });

        Allure.step("Verify: Default selected option in status selector is 'ALL'");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).containsText("ALL");
    }

    @Test
    @TmsLink("263")
    @Epic("Transactions")
    @Feature("Amount")
    @Description("Choose amount popup functionality")
    public void testChooseAmountPopUp() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickAmountButton()
                .fillAmountFromField("10")
                .fillAmountFromField("20")
                .clickAmountClearButton()
                .fillAmountFromField("100")
                .clickAmountFromIncreaseArrow()
                .clickAmountFromIncreaseArrow()
                .clickAmountFromDecreaseArrow()
                .clickClearAmountToButton()
                .fillAmountToField("5000")
                .clickAmountToIncreaseArrow()
                .clickAmountToDecreaseArrow()
                .clickAmountToDecreaseArrow()
                .clickAmountApplyButton();

        Allure.step("Verify: Applied amount is visible");
        assertThat(transactionsPage.amountApplied("Amount: 101 - 4999")).isVisible();

        transactionsPage
                .clickAmountAppliedClearButton();

        Allure.step("Verify: Amount button is visible after reset");
        assertThat(transactionsPage.getAmountButton()).isVisible();
    }

    @Test
    @TmsLink("335")
    @Epic("Transactions")
    @Feature("Amount")
    @Description("Error message 'From should be lesser than To' appears")
    public void testErrorMessageByAmount() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickAmountButton()
                .fillAmountFromField("500")
                .fillAmountToField("10");

        Allure.step("Verify: error message 'From should be lesser than To' appears");
        assertThat(transactionsPage.getAmountErrorMessage()).hasText("\"From\" should be lesser than \"To");
    }

    @Test
    @TmsLink("342")
    @Epic("Transactions")
    @Feature("Card type")
    @Description("Verify that user can see 'Card type' options")
    public void testTheVisibilityOfTheCardTypeOptions() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickCardTypeSelector();

        Allure.step("Verify: Payment Method Options are visible");
        assertEquals(transactionsPage.getCardTypeOptions(), List.of("ALL", "VISA", "MASTERCARD"));

        Allure.step("Verify: Default selected option in Payment Method Options is 'ALL'");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).containsText("ALL");
    }

    @Test
    @TmsLink("340")
    @Epic("Transactions")
    @Feature("Date range")
    @Description("Error message is displayed when start date is after end date.")
    public void testErrorMessageForReversedDateRange() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields("01-04-2025", "01-04-2024")
                .clickRefreshDataButton();

        Allure.step("Verify: error message is shown for invalid date range");
        assertThat(transactionsPage.getSelectDateRange().getErrorMessage())
                .hasText("Start date must be before end date.");
    }

    @Test
    @TmsLink("354")
    @Epic("Transactions")
    @Feature("Amount")
    @Description("Edit Amount")
    public void testEditAmount() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickAmountButton()
                .fillAmountFromField("500")
                .fillAmountToField("10000")
                .clickAmountApplyButton()
                .clickAmountEditButton()
                .fillAmountFromField("500")
                .fillAmountToField("10300");

        Allure.step("Verify: Edited amount is visible");
        assertThat(transactionsPage.amountApplied("Amount: 500 - 10300")).isVisible();
    }

    @Test
    @TmsLink("355")
    @Epic("Transactions")
    @Feature("Amount")
    @Description("Reset Amount Values")
    public void testResetAmountValues() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickAmountButton()
                .fillAmountFromField("500")
                .fillAmountToField("10000")
                .clickAmountApplyButton()
                .clickAmountAppliedClearButton()
                .clickAmountButton();

        Allure.step("Verify: From Amount is zero");
        assertThat(transactionsPage.getAmountFromInputField()).hasValue("0.00");

        Allure.step("Verify: To Amount is zero");
        assertThat(transactionsPage.getAmountToInputField()).hasValue("0.00");

        transactionsPage
                .clickAmountApplyButton();

        Allure.step("Verify: Applied amount is visible");
        assertThat(transactionsPage.amountApplied("Amount: 0.00 - 0.00")).isVisible();
    }

    @Test
    @TmsLink("356")
    @Epic("Transactions")
    @Feature("Export table data")
    @Description("The presence of the dropdown options export table data to file")
    public void testPresenceOfDownloadFilesOptions() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickDownloadButton();

        Allure.step("Verify: CSV option is visible");
        assertThat(transactionsPage.getDownloadCsvOption()).isVisible();

        Allure.step("Verify: EXCEL option is visible");
        assertThat(transactionsPage.getDownloadExcelOption()).isVisible();

        Allure.step("Verify: PDF option is visible");
        assertThat(transactionsPage.getDownloadPdfOption()).isVisible();
    }

    @Test(dataProvider = "getExportFileType", dataProviderClass = TestDataProvider.class)
    @TmsLink("357")
    @Epic("Transactions")
    @Feature("Export table data")
    @Description("Download files: PDF, Excel, CSV")
    public void testDownloadFiles(String fileType) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .clickDownloadButton();

        Allure.step("Verify: that files can be downloaded");
        Assert.assertTrue(transactionsPage.isFileAvailableAndNotEmpty(fileType));
    }

    @Test
    @TmsLink("503")
    @Epic("Transactions")
    @Feature("Business unit")
    @Description("Verify that the Company admin can see all the company's business units in the Business unit "
            + "dropdown list")
    public void testTheVisibilityOfTheAvailableBusinessUnitOptions(@Optional("ADMIN") String userRole) {
        TransactionsPage transactionsPage = new DashboardPage((getPage()))
                .clickTransactionsLink()
                .getSelectBusinessUnit().clickSelectBusinessUnitPlaceholder();

        Allure.step("Verify: Company's business units are visible");
        assertThat(transactionsPage.getSelectBusinessUnit().getDropdownOptionList()).hasText(businessUnitNames);
    }

    @Test(dataProvider = "getCurrency", dataProviderClass = TestDataProvider.class)
    @TmsLink("567")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that 'Reset filter' button change 'Currency' to default value ( ALL)")
    public void testResetCurrency(String currency) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: Filter displays 'ALL' by default");
        assertThat(transactionsPage.getCurrencySelector()).containsText("ALL");

        transactionsPage
                .clickCurrencySelector()
                .selectCurrency(currency);

        Allure.step("Verify: Filter displays the selected currency");
        assertThat(transactionsPage.getCurrencySelector()).containsText(currency);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: Filter displays 'ALL' after applying 'Reset filter' button ");
        assertThat(transactionsPage.getCurrencySelector()).containsText("ALL");
    }

    @Test
    @TmsLink("620")
    @Epic("Transactions")
    @Feature("Refresh data")
    @Description("Verify the request to server contains all the information from the filter")
    public void testRequestToServer() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields("01-05-2025", "07-05-2025")
                .getSelectCompany().selectCompany(COMPANY_NAME)
                .getSelectBusinessUnit().selectBusinessUnit(MERCHANT_TITLE)
                .clickCurrencySelector()
                .selectCurrency("USD")
                .selectCardType("VISA")
                .clickAmountButton()
                .fillAmountFromField("500")
                .fillAmountToField("10000")
                .clickAmountApplyButton();

        Allure.step("Verify: merchant ID is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains(businessUnit.merchantId()));

        Allure.step("Verify: dateFrom is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains("2025-05-01T00:00:00.000Z"));

        Allure.step("Verify: dateTo is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains("2025-05-07T23:59:59.999Z"));

        Allure.step("Verify: currency is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains("USD"));

        Allure.step("Verify: paymentMethod is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains("VISA"));

        Allure.step("Verify:amountFrom is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains("500"));

        Allure.step("Verify: amountTo is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains("10000"));
    }

    @Test
    @TmsLink("621")
    @Epic("Transactions")
    @Feature("Refresh data")
    @Description("Verify the status is sent to the server")
    public void testStatusRequestServer() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME)
                .getSelectBusinessUnit().selectBusinessUnit(MERCHANT_TITLE)
                .getSelectStatus().clickSelector()
                .getSelectStatus().clickValue("SUCCESS")
                .getSelectStatus().clickSelector();

        Allure.step("Verify: status is sent to the server");
        assertTrue(transactionsPage.getRequestData().contains("SUCCESS"));
    }

    @Test(dataProvider = "getCardType", dataProviderClass = TestDataProvider.class)
    @TmsLink("598")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that 'Reset filter' button change 'Card Type' to default value ( ALL)")
    public void testResetCardType(String cardType) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: Filter displays 'ALL' by default");
        assertThat(transactionsPage.getCardTypeValue()).containsText("ALL");

        transactionsPage
                .selectCardType(cardType);

        Allure.step("Verify: Filter displays the selected payment method");
        assertThat(transactionsPage.getCardTypeValue()).containsText(cardType);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: Filter displays 'ALL' after applying 'Reset filter' button");
        assertThat(transactionsPage.getCardTypeValue()).containsText("ALL");
    }

    @Test(dataProvider = "getStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("639")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that 'Reset filter' button change 'Status' to default value ( ALL)")
    public void testResetStatus(String status) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: Filter displays 'ALL' by default");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).hasText("ALL");

        transactionsPage
                .getSelectStatus().selectTransactionStatuses(status);

        Allure.step("Verify: Filter displays the selected Status");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).hasText(status);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: Filter displays 'ALL' after applying 'Reset filter' button");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).hasText("ALL");
    }

    @Ignore("multistatus not working atm")
    @Test(dataProvider = "getMultiStatus2", dataProviderClass = TestDataProvider.class)
    @TmsLink("655")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that 'Reset filter' button change 'Status' (two options are checked) to default value ( ALL)")
    public void testResetMultiStatus(String status1, String status2) {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: Filter displays 'ALL' by default");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).hasText("ALL");

        transactionsPage
                .getSelectStatus().selectTransactionStatuses(status1, status2);

        Allure.step("Verify: Filter displays the selected Status");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).hasText(status1 + ", " + status2);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: Filter displays 'ALL' after applying 'Reset filter' button");
        assertThat(transactionsPage.getSelectStatus().getStatusValue()).hasText("ALL");
    }

    @Test
    @TmsLink("638")
    @Epic("Transactions")
    @Feature("Transaction details")
    @Description("Check that after click on transactions in column NPGW reference user see transaction details")
    public void testCheckTransactionDetails() {
        TransactionDetailsDialog transactionDetailsDialog = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .getTable().clickOnFirstTransaction();

        Allure.step("Verify: The dialog box header has text 'Transaction details'");
        assertThat(transactionDetailsDialog.getDialogHeader()).containsText("Transaction details");

        Allure.step("Verify: The dialog box section names");
        assertThat(transactionDetailsDialog.getSectionNames())
                .hasText(new String[]{"Amount", "Updated on (GMT)", "NPGW reference", "Merchant reference",
                        "Payment lifecycle", "Card details", "Customer details", "3D Secure"});

        Allure.step("Verify: The Card details labels");
        assertThat(transactionDetailsDialog.getCardDetailsLabels())
                .hasText(new String[]{"Payment method", "Card type", "Card holder", "Card number", "Expiry date"});

        Allure.step("Verify: The Customer details labels");
        assertThat(transactionDetailsDialog.getCustomerDetailsLabels())
                .hasText(new String[]{"Name", "Date of birth", "E-Mail", "Phone",
                        "Country", "State", "City", "ZIP", "Address"});
    }

    @Test
    @TmsLink("668")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that 'Reset filter' button change 'Amount' to default value ( AMOUNT)")
    public void testResetAmount() {
        final String amountFrom = "10";
        final String amountTo = "20";
        final String chosenAmount = "Amount: " + amountFrom + " - " + amountTo;

        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: Filter 'Amount' displays 'Amount' by default");
        assertThat(transactionsPage.getAmountButton()).isVisible();
        assertThat(transactionsPage.getAmountButton()).hasText("Amount");

        transactionsPage
                .clickAmountButton()
                .fillAmountFromField(amountFrom)
                .fillAmountToField(amountTo)
                .clickAmountApplyButton();

        Allure.step("Verify: Filter 'Amount' displays 'Amount: {amountFrom}- {amountTo}'");
        assertThat(transactionsPage.amountApplied(chosenAmount)).isVisible();
        assertThat(transactionsPage.amountApplied(chosenAmount)).hasText(chosenAmount);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: Filter 'Amount' displays 'Amount' by default");
        assertThat(transactionsPage.getAmountButton()).isVisible();
        assertThat(transactionsPage.getAmountButton()).hasText("Amount");
    }

    @Test
    @TmsLink("686")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that 'Reset filter' clean 'Company' input field")
    public void testResetCompany() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: the 'Company' input field is empty by default");
        assertThat(transactionsPage.getSelectCompany().getSelectCompanyField()).isEmpty();

        transactionsPage
                .getSelectCompany().selectCompany(COMPANY_NAME);

        Allure.step("Verify: selected company is displayed in the 'Company' input field");
        assertThat(transactionsPage.getSelectCompany().getSelectCompanyField()).hasValue(COMPANY_NAME);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: the 'Company' input field is empty after reset");
        assertThat(transactionsPage.getSelectCompany().getSelectCompanyField()).isEmpty();
    }

    @Test
    @TmsLink("661")
    @Epic("Transactions")
    @Feature("Transaction details")
    @Description("Check the hiding of parameters by pressing the chevron in Card details section")
    public void testCheckTheHidingOfParameters() {
        TransactionDetailsDialog transactionDetailsDialog = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .getTable().clickOnFirstTransaction()
                .clickSection("Card details");

        Allure.step("Verify: Parameter 'Payment method' is hidden after click on chevron in Card details field ");
        assertThat(transactionDetailsDialog.getPaymentMethodValue()).isHidden();

        Allure.step("Verify: Parameter 'Card holder' is hidden after click on chevron in Card details field ");
        assertThat(transactionDetailsDialog.getCardHolderValue()).isHidden();

        Allure.step("Verify: Parameter 'Card number' is hidden after click on chevron in Card details field ");
        assertThat(transactionDetailsDialog.getCardNumberValue()).isHidden();

        Allure.step("Verify: Parameter 'Expiry date' is hidden after click on chevron in Card details field ");
        assertThat(transactionDetailsDialog.getExpiryDateValue()).isHidden();

        transactionDetailsDialog
                .clickSection("Customer details");

        Allure.step("Verify: Parameter 'Name' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getNameValue()).isHidden();

        Allure.step("Verify: Parameter 'Date of birth' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getDateOfBirthValue()).isHidden();

        Allure.step("Verify: Parameter 'E-Mail' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getEmailValue()).isHidden();

        Allure.step("Verify: Parameter 'Phone' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getPhoneValue()).isHidden();

        Allure.step("Verify: Parameter 'Country' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getCountryValue()).isHidden();

        Allure.step("Verify: Parameter 'State' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getStateValue()).isHidden();

        Allure.step("Verify: Parameter 'City' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getCityValue()).isHidden();

        Allure.step("Verify: Parameter 'ZIP' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getZipValue()).isHidden();

        Allure.step("Verify: Parameter 'Address' is hidden after click on chevron in Customer details field ");
        assertThat(transactionDetailsDialog.getAddressValue()).isHidden();
    }

    @Test
    @TmsLink("701")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that 'Reset filter' clean 'Business Unit' input field")
    public void testResetBusinessUnit() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: the 'Business Unit' input field is empty by default");
        assertThat(transactionsPage.getSelectBusinessUnit().getSelectBusinessUnitField()).isEmpty();

        transactionsPage
                .getSelectCompany().selectCompany(COMPANY_NAME)
                .getSelectBusinessUnit().selectBusinessUnit(MERCHANT_TITLE);

        Allure.step("Verify: selected Business Unit is displayed in the 'Business Unit' input field");
        assertThat(transactionsPage.getSelectBusinessUnit().getSelectBusinessUnitField()).hasValue(MERCHANT_TITLE);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: the 'Business Unit' input field is empty after reset");
        assertThat(transactionsPage.getSelectBusinessUnit().getSelectBusinessUnitField()).isEmpty();
    }

    @Test
    @TmsLink("736")
    @Epic("Transactions")
    @Feature("Reset filter")
    @Description("Verify, that ")
    public void testResetData() {
        final String startDate = "01-04-2025";
        final String endDate = "30-04-2025";
        final String dataFrom = startDate.replaceAll("-", "/");
        final String dataTo = endDate.replaceAll("-", "/");
        final String selectedRange = "Date range" + dataFrom + "-" + dataTo;
        final String currentRange = TestUtils.getCurrentRange();

        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink();

        Allure.step("Verify: the 'Data' input field value is current month by default");
        assertThat(transactionsPage.getSelectDateRange().getDateRangeField()).hasText(currentRange);

        transactionsPage
                .getSelectDateRange().setDateRangeFields(startDate, endDate);

        Allure.step("Verify: the 'Data' input field value is checked period");
        assertThat(transactionsPage.getSelectDateRange().getDateRangeField()).hasText(selectedRange);

        transactionsPage
                .clickResetFilterButton();

        Allure.step("Verify: the 'Data' input field value is current month after reset");
        assertThat(transactionsPage.getSelectDateRange().getDateRangeField()).hasText(currentRange);
    }

    @Test
    @TmsLink("738")
    @Epic("Transactions")
    @Feature("Transaction details")
    @Description("Closes the transaction details dialog using both the button and the icon.")
    public void testCloseTransactionDetailsDialog() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .getTable().clickOnFirstTransaction()
                .clickCloseButton();

        Allure.step("Verify: Transaction details dialog is closed ");
        assertThat(transactionsPage.getDialog()).not().isAttached();

        transactionsPage
                .getTable().clickOnFirstTransaction()
                .clickCloseIcon();

        Allure.step("Verify: Transaction details dialog is closed ");
        assertThat(transactionsPage.getDialog()).not().isAttached();
    }

    @Test
    @TmsLink("749")
    @Epic("Transactions")
    @Feature("Transaction details")
    @Description("Verify, that the data in Transaction Details Dialog corresponds to the data in Transactions table")
    public void testDataMatching() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        String currency = transactionsPage
                .getTable().getFirstRowCell("Currency").textContent();

        String status = transactionsPage
                .getTable().getFirstRowCell("Status").textContent();

        String amount = transactionsPage
                .getTable().getFirstRowCell("Amount").textContent();

        String merchantReference = transactionsPage
                .getTable().getFirstRowCell("Merchant Reference").textContent();

        String cardType = transactionsPage
                .getTable().getFirstRowCardType();

        TransactionDetailsDialog transactionDetails = transactionsPage
                .getTable().clickOnFirstTransaction();

        Allure.step("Verify: 'Status' value is the same as in the table");
        assertThat(transactionDetails.getStatusValue()).hasText(status);

        Allure.step("Verify: Merchant Reference  is the same as in the table");
        assertThat(transactionDetails.getMerchantReferenceValue()).hasText(merchantReference);

        Allure.step("Verify: Amount value and Currency are the same as in the table");
        assertThat(transactionDetails.getAmountValue()).hasText(currency + " " + amount);

        Allure.step("Verify: Card type is the same as in table");
        assertThat(transactionDetails.getCardTypeValue()).hasText(cardType);
    }

    @Test
    @TmsLink("828")
    @Epic("Transactions")
    @Feature("Transaction details")
    @Description("Check that the 'Pending' occurs at most once in the Payment lifecycle section")
    public void testPendingOccursAtMostOnceInLifecycle() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .getTable().selectRowsPerPageOption("50");

        int numberOfTransactions = transactionsPage.getTable().getNumberOfTransactionOnCurrentPage();

        for (int i = 0; i < numberOfTransactions; i++) {
            TransactionDetailsDialog transactionDetails = transactionsPage
                    .getTable().clickOnTransaction(i);

            String statusInHeader = transactionDetails.getStatusValue().innerText();
            String lastTypeInLifecycle = transactionDetails.getLastPaymentLifecycleType().innerText();

            Allure.step("Verify: Statuses in the dialog header and lifecycle are the same");
            assertEquals(statusInHeader, lastTypeInLifecycle, "Statuses should match!");

            Allure.step("Verify: The Payment lifecycle begins with 'INITIATED'");
            assertThat(transactionDetails.getFirstPaymentLifecycleType()).hasText("INITIATED");

            Allure.step("Verify: The 'INITIATED' occurs exactly once in the lifecycle");
            assertThat(transactionDetails.getPaymentLifecycleType("INITIATED")).hasCount(1);

            Allure.step("Verify: the 'PENDING' occurs at most once");
            assertTrue(transactionDetails.countPaymentLifecycleType("PENDING") <= 1, String.format(
                    "The 'PENDING' occurs several times in the transaction details (#%d on the page)", i + 1));

            transactionDetails.clickCloseIcon();
        }
    }

    @Test
    @TmsLink("851")
    @Epic("Transactions")
    @Feature("Transactions Search")
    @Description("Verify that 'NPGW reference' and 'Merchant reference' fields appear when clicking on 'Trx IDs'.")
    public void testSearchOptionsVisibleAfterClickingTrxIds() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .clickSearchTrxIdsButton();

        Allure.step("Verify: 'NPGW reference' is visible ");
        assertThat(transactionsPage.getNpgwReferenceField()).isVisible();

        Allure.step("Verify: 'Merchant reference' is visible ");
        assertThat(transactionsPage.getMerchantReferenceField()).isVisible();
    }

    @Test
    @TmsLink("853")
    @Epic("Transactions")
    @Feature("Transactions Search")
    @Description("Verify that 'NPGW reference' and 'Merchant reference' fields appear when clicking on 'Trx IDs'.")
    public void testTransactionSearchByNpgwReference() {
        TransactionsPage transactionsPage = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN);

        List<Locator> npgwReference = transactionsPage.getTable().getColumnCells("NPGW Reference");

        int index1 = new Random().nextInt(npgwReference.size());
        int index2;
        do {
            index2 = new Random().nextInt(npgwReference.size());
        } while (index2 == index1);

        String npgwReferenceText1 = npgwReference.get(index1).innerText();
        String npgwReferenceText2 = npgwReference.get(index2).innerText();

        Locator filteredRows1 = transactionsPage
                .clickSearchTrxIdsButton()
                .typeNpgwReference(npgwReferenceText1)
                .clickTrxIdAppliedButton().getTable().getRows();

        Allure.step("Verify: Table has only one row with the N1 NPGW reference");
        assertThat(filteredRows1).hasCount(1);
        assertThat(filteredRows1).containsText(npgwReferenceText1);

        Locator filteredRows2 = transactionsPage
                .clickTrxIdPencil()
                .clickNpgwReferenceClearIcon()
                .typeNpgwReference(npgwReferenceText2)
                .clickTrxIdAppliedButton()
                .getTable().getRows();

        Allure.step("Verify: Table has only one row with the N2 NPGW reference");
        assertThat(filteredRows2).hasCount(1);
        assertThat(filteredRows2).containsText(npgwReferenceText2);

        Locator tableTransactionNotFiltered = transactionsPage
                .clickTrxIdClearIcon()
                .getTable().getRows();

        Allure.step("Verify: Table contains more than one row");
        assertTrue(tableTransactionNotFiltered.count() > 1, "Expected more than one transaction row");
    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteCompany(getApiRequestContext(), COMPANY_NAME);
        super.afterClass();
    }
}
