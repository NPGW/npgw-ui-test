package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.systemadministration.AcquirersPage;
import xyz.npgw.test.page.systemadministration.AddAcquirerDialog;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;

public class AddAcquirerDialogTest extends BaseTest {

    @Test()
    @TmsLink("249")
    @Epic("SA/Acquirers")
    @Feature("Add acquirer")
    @Description(
            "Verify that the 'Add Acquirer' form opens with the correct header and input fields, and closes correctly.")
    public void testAddAcquirerFormOpensWithCorrectHeaderAndFieldsAndClosesCorrectly() {
        List<String> expectedPlaceholders = List.of(
                "Enter acquirer name",
                "Enter acquirer code",
                "Enter challenge URL",
                "Enter fingerprint URL",
                "Enter resource URL",
                "Enter notification queue",
                "Enter priority",
                "Enter acquirer config"
        );

        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .getSystemAdministrationMenuComponent()
                .clickAcquirersTab();

        AddAcquirerDialog addAcquirerDialog = acquirersPage.clickAddAcquirer();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(addAcquirerDialog.getAddAcquirerDialogHeader()).hasText("Add acquirer");

        Allure.step("Verify: all placeholders are correct for each field");
        assertEquals(addAcquirerDialog.getAllFieldPlaceholders(), expectedPlaceholders);

        Allure.step("Verify: the Status Switch visible and contains switch Active&Inactive");
        assertThat(addAcquirerDialog.getStatusSwitch()).isVisible();
        assertThat(addAcquirerDialog.getStatusSwitch()).hasText("StatusActiveInactive");

        Allure.step("Verify: the 'Allowed Currencies' Checkboxes visible and contains USD&EUR");
        assertThat(addAcquirerDialog.getAllowedCurrenciesCheckboxes()).isVisible();
        assertThat(addAcquirerDialog.getAllowedCurrenciesCheckboxes()).hasText("Allowed currenciesUSDEUR");

        Allure.step("Verify: the 'Select Country' placeholder is visible");
        assertThat(addAcquirerDialog.getSelectCountryPlaceholder()).isVisible();

        Allure.step("Verify: the 'Select Zone' placeholder is visible");
        assertThat(addAcquirerDialog.getSelectTimezone()).isVisible();

        addAcquirerDialog.clickCloseButton();

        Allure.step("Verify: the 'Add acquirer' dialog is no longer visible");
        assertThat(acquirersPage.getAddAcquirerDialog()).isHidden();
    }

    @Test(dataProvider = "getAcquirersStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("255")
    @Epic("SA/Acquirers")
    @Feature("Add acquirer")
    @Description()
    public void testToggleStatusRadioButtonsCorrectly(String status, String value) {

        AddAcquirerDialog acquirerDialog = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .getSystemAdministrationMenuComponent()
                .clickAcquirersTab()
                .clickAddAcquirer();

        acquirerDialog.clickStatusRadiobutton(status);

        Allure.step(String.format("Verify: The radiobutton %s clicked.", status));
        assertThat(acquirerDialog.getStatusRadiobutton(value)).hasAttribute("data-selected", "true");
    }
}
