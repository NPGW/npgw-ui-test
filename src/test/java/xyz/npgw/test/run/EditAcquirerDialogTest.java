package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.dialog.acquirer.AddAcquirerDialog;
import xyz.npgw.test.page.system.AcquirersPage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static xyz.npgw.test.common.util.TestUtils.createAcquirer;
import static xyz.npgw.test.common.util.TestUtils.getAcquirer;

public class EditAcquirerDialogTest extends BaseTest {

    @Test
    @TmsLink("239")
    @Epic("System/Acquirers")
    @Feature("Edit acquirers")
    @Description("Verifies that all form field placeholders are set correctly")
    public void testVerifyPlaceholdersEditForm() {

        String acquirerName = "Acquirer for edit form";
        if (!getAcquirer(getApiRequestContext(), acquirerName)) {
            createAcquirer(getApiRequestContext(), acquirerName);
        }

        List<String> expectedPlaceholders = List.of(
                "Enter acquirer name",
                "Enter acquirer code",
                "Enter challenge URL",
                "Enter fingerprint URL",
                "Enter resource URL",
                "Enter notification queue",
                "Enter acquirer config"
        );

        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .getSystemMenu()
                .clickAcquirersTab()
                .enterAcquirerName(acquirerName)
                .clickAcquirerInDropdown(acquirerName);

        String row = acquirersPage.getTable().getTableRows().innerText();



        String acquirerName = fullText.split("\n")[0];

        Allure.step("Verify: List has only 1");
        assertThat(acquirersPage.getTable().getTableRows()).hasCount(1);

        Allure.step(String.format(
                "Verify: List has acquirer '%s'", acquirersPage.getTable().getTableRows().innerText());




//        Allure.step(String.format("Verify: Dropdown contain '%s' acquirer", acquirerName));
//        assertThat(acquirersPage.getAddAcquirerDialog()).hasText(acquirerName);





//        AddAcquirerDialog addAcquirerDialog = acquirersPage.clickAddAcquirer();
//
//        Allure.step("Verify: the header contains the expected title text");
//        assertThat(addAcquirerDialog.getDialogHeader()).hasText("Add acquirer");
//
//        Allure.step("Verify: all placeholders are correct for each field");
//        assertEquals(addAcquirerDialog.getAllFieldPlaceholders(), expectedPlaceholders);
//
//        Allure.step("Verify: the Status Switch visible and contains switch Active&Inactive");
//        assertThat(addAcquirerDialog.getStatusSwitch()).isVisible();
//        assertThat(addAcquirerDialog.getStatusSwitch()).hasText("StatusActiveInactive");
//
//        Allure.step("Verify: the 'Allowed Currencies' Checkboxes visible and contains USD&EUR");
//        assertThat(addAcquirerDialog.getAllowedCurrenciesCheckboxes()).isVisible();
//        assertThat(addAcquirerDialog.getAllowedCurrenciesCheckboxes()).hasText("Allowed currenciesUSDEUR");
//
//        addAcquirerDialog.clickCloseButton();
//
//        Allure.step("Verify: the 'Add acquirer' dialog is no longer visible");
//        assertThat(acquirersPage.getAddAcquirerDialog()).isHidden();





//        List<String> expectedPlaceholders = List.of(
//                "Enter acquirer name", "Enter acquirer code", "Enter challenge URL", "Enter fingerprint URL",
//                "Enter resource URL", "Enter notification queue", "Enter priority", "Enter acquirer config",
//                "Search...", "Select timezone");
//
//        List<String> actualPlaceholders = new DashboardPage(getPage())
//                .getHeader()
//                .clickSystemAdministrationLink()
//                .getSystemMenu()
//                .clickAcquirersTab()
//                .clickEditButtonForAcquirer("acquirer1")
//                .clearInputFields()
//                .getPlaceholdersOrTextsFromFields();
//
//        Allure.step("Verify placeholders match expected values for all fields");
//        assertEquals(actualPlaceholders, expectedPlaceholders);
    }
}
