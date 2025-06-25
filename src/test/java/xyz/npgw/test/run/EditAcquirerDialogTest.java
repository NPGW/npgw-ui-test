package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.Acquirer;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.SystemConfig;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.dialog.acquirer.EditAcquirerDialog;
import xyz.npgw.test.page.system.AcquirersPage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class EditAcquirerDialogTest extends BaseTest {

    private static final String ACQUIRER_NAME = "%s acquirer for edit form".formatted(RUN_ID);
    private static final List<String> COLUMNS_HEADERS = List.of(
            "Acquirer name",
            "Acquirer display name",
            "Acquirer code",
            "Acquirer MID",
            "Acquirer MID MCC",
            "Currencies",
            "Acquirer config",
            "System config",
            "Status",
            "Actions");

    private static final Acquirer ACQUIRER = new Acquirer(
            "display name",
            "acquirer mid",
            "NGenius",
            "default",
            new Currency[]{Currency.USD, Currency.EUR},
            new SystemConfig(),
            true,
            "%s acquirer 11.002.01".formatted(RUN_ID),
            "mcc");

    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        TestUtils.createAcquirer(getApiRequestContext(), new Acquirer(ACQUIRER_NAME));
    }

    @Test
    @TmsLink("239")
    @Epic("System/Acquirers")
    @Feature("Edit acquirers")
    @Description("Verifies that all form field placeholders are set correctly")
    public void testVerifyPlaceholdersEditForm() {
        List<String> expectedPlaceholders = List.of(
                "Enter acquirer name",
                "Enter acquirer code",
                "Enter acquirer display name",
                "Enter acquirer MID",
                "Enter acquirer MID MCC",
                "Enter challenge URL",
                "Enter fingerprint URL",
                "Enter resource URL",
                "Enter notification queue",
                "Enter acquirer config"
        );

        List<String> actualPlaceholders = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirer().typeName(ACQUIRER_NAME)
                .getSelectAcquirer().clickAcquirerInDropdown(ACQUIRER_NAME)
                .getTable().clickEditAcquirerButton(ACQUIRER_NAME)
                .getAllPlaceholders();

        Allure.step("Verify placeholders match expected values for all fields");
        assertEquals(actualPlaceholders, expectedPlaceholders);
    }

    @Test
    @TmsLink("239")
    @Epic("System/Acquirers")
    @Feature("Edit acquirers")
    @Description("Edit Acquirer and Verify Updated Data in the Table")
    public void testEditAcquirerVerifyUpdatedData() {

//        Map<String, String> expectedColumnValues = Map.of(
//                COLUMNS_HEADERS.get(0), ACQUIRER.acquirerName(),
//                COLUMNS_HEADERS.get(1), ACQUIRER.acquirerDisplayName(),
//                COLUMNS_HEADERS.get(2), ACQUIRER.acquirerCode(),
//                COLUMNS_HEADERS.get(3), ACQUIRER.acquirerMid(),
//                COLUMNS_HEADERS.get(4), ACQUIRER.acquirerMidMcc(),
//
//                COLUMNS_HEADERS.get(5), String.join(", ", Arrays.stream(ACQUIRER.currencyList())
//                        .map(Enum::name)
//                        .toList()),
//                COLUMNS_HEADERS.get(6), ACQUIRER.acquirerConfig(),
//                COLUMNS_HEADERS.get(7), String.join("\n",
//                        "Challenge URL\n" + ACQUIRER.systemConfig().challengeUrl(),
//                        "Fingerprint URL\n" + ACQUIRER.systemConfig().fingerprintUrl(),
//                        "Resource URL\n" + ACQUIRER.systemConfig().resourceUrl(),
//                        "Notification queue\n" + ACQUIRER.systemConfig().notificationQueue()),
//                COLUMNS_HEADERS.get(8), ACQUIRER.isActive() ? "Active" : "Inactive"
//        );

        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirer().typeName(ACQUIRER_NAME)
                .getSelectAcquirer().clickAcquirerInDropdown(ACQUIRER_NAME)
                .getTable().clickEditAcquirerButton(ACQUIRER_NAME)
                .fillAcquirerDisplayNameField("new display name")
                .fillAcquirerMidField("new mid name")
                .fillAcquirerMidMccField("new acquirer mid mcc")
                .fillChallengeUrlField("https://test.npgw.xyz/challenge/new/url")
                .fillFingerprintUrlField("https://test.npgw.xyz/fingerprint/new/url")
                .fillResourceUrlField("https://test.npgw.xyz/resource/new/url")
                .fillNotificationQueueField("new notificationQueue")
                .clickStatusRadiobutton("Inactive")
                .clickCheckboxCurrency("GBP")
                .clickSaveChangesButton()
                ;

//        editAcquirerDialog.acquirerDisplayNameField.inputValue()
//
        getPage().pause();

    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteAcquirer(getApiRequestContext(), ACQUIRER_NAME);
        super.afterClass();
    }
}
