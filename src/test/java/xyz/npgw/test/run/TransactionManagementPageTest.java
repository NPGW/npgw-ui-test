package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.TransactionsPage;
import xyz.npgw.test.page.dialog.adjustment.AddAdjustmentDialog;
import xyz.npgw.test.page.system.TransactionManagementPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static xyz.npgw.test.common.Constants.BUSINESS_UNIT_FOR_TEST_RUN;
import static xyz.npgw.test.common.Constants.COMPANY_NAME_FOR_TEST_RUN;

public class TransactionManagementPageTest extends BaseTest {

    @Ignore("Invalid key=value pair (missing equal-sign) in Authorization header ERROR with Create button")
    @Test
    @TmsLink("874")
    @Epic("System/Transaction Management")
    @Feature("Add adjustment")
    @Description("Verify possibility to add adjustment with Transaction adjustment")
    public void testAddTransactionAdjustment() {
        TransactionManagementPage page = new TransactionManagementPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickTransactionManagementTab()
                .clickAddAdjustmentButton()
                .clickOnTheTransaction()
                .clickOnCreateButton();

        assertThat(page.getTransactionsTable()).containsText("id.transaction.");
    }

    @Ignore("There is no longer a default transaction")
    @Test
    @TmsLink("873")
    @Epic("System/Transaction Management")
    @Feature("Add adjustment")
    @Description("Close button with no changes performed")
    public void testClickCloseButtonWithNoChanges() {
        TransactionManagementPage page = new TransactionManagementPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickTransactionManagementTab()
                .clickAddAdjustmentButton()
                .clickOnTheTransaction()
                .clickOnCloseButton();

        assertThat(page.getTransactionsTable()).containsText("No rows to display.");
    }

    @Test
    @TmsLink("886")
    @Epic("System/Transaction Management")
    @Feature("Add adjustment")
    @Description("Search by NPGW reference by paste from clipboard using Ctrl+V")
    public void testPasteReferenceIntoTransactionAdjustment() {
        String referenceFromTable = new DashboardPage(getPage())
                .clickTransactionsLink()
                .getSelectDateRange().setDateRangeFields(TestUtils.lastBuildDate(getApiRequestContext()))
                .getSelectCompany().selectCompany(COMPANY_NAME_FOR_TEST_RUN)
                .getSelectBusinessUnit().selectBusinessUnit(BUSINESS_UNIT_FOR_TEST_RUN)
                .getTable().clickOnFirstReferenceCopyButton()
                .getTable().getFirstRowReference();

        AddAdjustmentDialog addAdjustmentDialog = new TransactionsPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickTransactionManagementTab()
                .clickAddAdjustmentButton()
                .clickOnNpgwReferenceInput()
                .pasteIntoNpgwReferenceInputUsingCtrlV();

        String referenceFromClipboard = addAdjustmentDialog.getNpgwReferenceInputValue();

        Allure.step("Verify: The correct reference value is pasted from the clipboard.");
        Assert.assertEquals(referenceFromClipboard, referenceFromTable,
                "Clipboard reference value must match the value from the transactions table.");

        String referenceFromAdjustment = addAdjustmentDialog.getReference().innerText();

        Allure.step("Verify: The located reference matches the one entered in the search field.");
        Assert.assertEquals(referenceFromAdjustment, referenceFromClipboard,
                "Reference value from the 'Add adjustment' must match the value from the clipboard");
    }
}
