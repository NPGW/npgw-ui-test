package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.BoundingBox;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.Acquirer;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.SystemConfig;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.dashboard.SuperDashboardPage;
import xyz.npgw.test.page.dialog.acquirer.SetupAcquirerMidDialog;
import xyz.npgw.test.page.system.SuperAcquirersPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class AcquirersPageTest extends BaseTest {

    private static final String[] STATUS_OPTIONS = {"All", "Active", "Inactive"};
    private static final String[] ROWS_PER_PAGE_OPTIONS = {"10", "25", "50", "100"};
    private static final String[] COLUMNS_HEADERS = {
            "Entity name",
            "Display name",
            "Acquirer code",
            "MID",
            "MCC",
            "Currencies",
            "Acquirer config",
            "System config",
            "Status",
            "Actions"};

    private static final List<String> PLACEHOLDER_LIST = List.of(
            "Enter entity name",
            "Enter acquirer code",
            "Enter display name",
            "Enter MID",
            "Enter MCC",
            "Enter challenge URL",
            "Enter fingerprint URL",
            "Enter resource URL",
            "Enter notification queue",
            "Enter acquirer config"
    );

    private static final SystemConfig DEFAULT_CONFIG = new SystemConfig();

    private static final Acquirer ACQUIRER = Acquirer.builder()
            .acquirerMid("123456")
            .acquirerCode("ACQ001")
            .acquirerDisplayName("%s display acquirer".formatted(TestUtils.now()))
            .currencyList(new Currency[]{Currency.USD})
            .acquirerName("%s my-acquirer".formatted(TestUtils.now()))
            .acquirerMidMcc("5411")
            .build();

    private static final Acquirer ACQUIRER_EDITED = Acquirer.builder()
            .acquirerName(ACQUIRER.getAcquirerDisplayName())
            .acquirerDisplayName("%s display acquirer edited".formatted(RUN_ID))
            .acquirerCode("NGenius")
            .acquirerMid("new mid name")
            .acquirerMidMcc("2222")
            .currencyList(new Currency[]{Currency.GBP})
            .acquirerConfig("new config")
            .systemConfig(new SystemConfig("https://test.npgw.xyz/challenge/new/url",
                    "https://test.npgw.xyz/fingerprint/new/url",
                    "https://test.npgw.xyz/resource/new/url",
                    "new notificationQueue"))
            .isActive(false)
            .build();

    private static final Acquirer ACQUIRER2 = Acquirer.builder()
            .acquirerName("%s acquirer 11.002.01".formatted(RUN_ID))
            .acquirerDisplayName("%s display name".formatted(RUN_ID))
            .acquirerMidMcc("4321")
            .build();
    private static final Acquirer CHANGE_STATE_ACQUIRER = Acquirer.builder()
            .acquirerName("%s acquirer activate and deactivate".formatted(RUN_ID))
            .acquirerDisplayName("%s display name activation check".formatted(RUN_ID))
            .acquirerMidMcc("4321")
            .build();

    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        TestUtils.createAcquirer(getApiRequestContext(), ACQUIRER2);
        TestUtils.createAcquirer(getApiRequestContext(), CHANGE_STATE_ACQUIRER);
    }

    @Test
    @TmsLink("157")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("The visibility of the acquirers table header and table contents")
    public void testVisibilityHeaderAndAcquirersList() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        Allure.step("Verify: Table column headers");
        assertThat(acquirersPage.getTable().getColumnHeaders()).hasText(COLUMNS_HEADERS);

        Allure.step("Verify: Acquirers table content is visible and contains acquirers");
        assertThat(acquirersPage.getTable().getRows()).not().hasCount(0);
    }

    @Test
    @TmsLink("187")
    @Epic("System/Acquirers")
    @Feature("Status")
    @Description("The 'Status' dropdown toggles and contains options All, Active, Inactive.")
    public void testVerifyStatusDropdownContainsAllOptions() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectStatus().clickSelector();

        Allure.step("Verify: The 'Status' dropdown toggles and contains options");
        assertThat(acquirersPage.getSelectStatus().getStatusOptions()).hasText(STATUS_OPTIONS);
    }

    @Test(dataProvider = "getAcquirersStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("243")
    @Epic("System/Acquirers")
    @Feature("Status")
    @Description("Filter acquirers by status.")
    public void testFilterAcquirersByStatus(String status) {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectStatus().select(status);

        Allure.step(String.format("Verify: The 'Acquirers' list shows only '%s' items after filtering.", status));
        assertTrue(acquirersPage.getTable().getColumnValuesFromAllPages("Status")
                .stream().allMatch(value -> value.equals(status)));
    }

    @Test
    @TmsLink("268")
    @Epic("System/Acquirers")
    @Feature("Status")
    @Description("Verify that re-selecting an already selected status keeps the selection unchanged.")
    public void testRetainStatusWhenReSelectingSameOption() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        for (String status : STATUS_OPTIONS) {
            acquirersPage
                    .getSelectStatus().select(status);

            Allure.step("Verify placeholder matches expected value: " + status);
            assertThat(acquirersPage.getSelectStatus().getStatusValue()).hasText(status);

            acquirersPage
                    .getSelectStatus().select(status);

            Allure.step("Verify again placeholder matches expected value: " + status);
            assertThat(acquirersPage.getSelectStatus().getStatusValue()).hasText(status);
        }
    }

    @Test
    @TmsLink("380")
    @Epic("System/Acquirers")
    @Feature("Rows per page")
    @Description("Verify the default 'Rows per page' value is 25 and the dropdown contains the correct options.")
    public void testRowsPerPageDropdownOptions() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        Allure.step("Verify: The default 'Rows per page' value is set to 25");
        assertThat(acquirersPage.getTable().getRowsPerPage()).hasText("25");

        acquirersPage
                .getTable().clickRowsPerPageChevron();

        Allure.step("Verify: Dropdown is visible");
        assertThat(acquirersPage.getTable().getRowsPerPageDropdown()).isVisible();

        Allure.step("Verify: The dropdown contains options");
        assertThat(acquirersPage.getTable().getRowsPerPageOptions()).hasText(ROWS_PER_PAGE_OPTIONS);
    }

    @Test
    @TmsLink("382")
    @Epic("System/Acquirers")
    @Feature("Rows per page")
    @Description("Verify Selecting 'Rows per page' Option Updates the Field Value.")
    public void testSelectingRowsPerPageOptionUpdatesFieldValue() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        for (String option : ROWS_PER_PAGE_OPTIONS) {
            acquirersPage.getTable().selectRowsPerPageOption(option);

            Allure.step(String.format("Verify: The 'Rows per page' value is set to '%s'", option));
            assertThat(acquirersPage.getTable().getRowsPerPage()).hasText(option);
        }
    }

    @Test
    @TmsLink("385")
    @Epic("System/Acquirers")
    @Feature("Rows per page")
    @Description("Verify that selecting a 'Rows per page' option displays the correct number of rows in the table.")
    public void testRowsPerPageSelectionDisplaysCorrectNumberOfRows() {
        List<Integer> totalRowsForDifferentPagination = new ArrayList<>();

        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        for (String option : ROWS_PER_PAGE_OPTIONS) {
            List<Integer> rowsCountPerPage = acquirersPage
                    .getTable().selectRowsPerPageOption(option)
                    .getTable().getRowCountsPerPage();

            Allure.step(String.format("Verify: The table contains rows less than or equal to '%s' per page", option));
            assertTrue(rowsCountPerPage.stream().allMatch(count -> count <= Integer.parseInt(option)),
                    "Not all row counts are less than or equal to " + option);

            totalRowsForDifferentPagination.add(rowsCountPerPage.stream().mapToInt(Integer::intValue).sum());
        }

        Assert.assertEquals(totalRowsForDifferentPagination.stream().distinct().count(), 1,
                "Total rows should be the same for all 'Rows per page' options");
    }

    @Test
    @TmsLink("249")
    @TmsLink("526")
    @Epic("System/Acquirers")
    @Feature("Setup acquirer MID")
    @Description("Validate correct layout and behavior of the 'Setup acquirer MID' dialog.")
    public void verifyDefaultValuesInAcquirerMidDialogAndClose() {
        SetupAcquirerMidDialog setupAcquirerMidDialog = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .clickSetupAcquirerMidButton();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(setupAcquirerMidDialog.getDialogHeader()).hasText("Setup acquirer MID");

        Allure.step("Verify: Acquirer name field is marked as invalid");
        assertThat(setupAcquirerMidDialog.getAcquirerNameField()).hasAttribute("aria-invalid", "true");

        Allure.step("Verify: Acquirer code field is read-only");
        assertThat(setupAcquirerMidDialog.getAcquirerCodeField()).hasAttribute("aria-readonly", "true");

        Allure.step("Verify: Challenge URL field is marked as invalid");
        assertThat(setupAcquirerMidDialog.getChallengeURLField()).hasAttribute("aria-invalid", "true");

        Allure.step("Verify: Fingerprint URL field is marked as invalid");
        assertThat(setupAcquirerMidDialog.getFingerprintUrlField()).hasAttribute("aria-invalid", "true");

        Allure.step("Verify: Resource URL field is marked as invalid");
        assertThat(setupAcquirerMidDialog.getResourceUrlField()).hasAttribute("aria-invalid", "true");

        Allure.step("Verify: 'Active' status is selected by default");
        assertThat(setupAcquirerMidDialog.getStatusRadiobutton("Active")).isChecked();

        Allure.step("Verify: 'EUR' is selected as the default allowed currency");
        assertThat(setupAcquirerMidDialog.getAllowedCurrencyRadio("EUR")).isChecked();

        Allure.step("Verify: 'Create' button is disabled when required fields are not filled");
        assertThat(setupAcquirerMidDialog.getCreateButton()).isDisabled();

        Allure.step("Verify: all placeholders are correct for each field");
        assertEquals(setupAcquirerMidDialog.getAllPlaceholders(), PLACEHOLDER_LIST);

        Allure.step("Verify: the Status Switch visible and contains switch Active&Inactive");
        assertThat(setupAcquirerMidDialog.getStatusSwitch()).hasText("StatusActiveInactive");

        Allure.step("Verify: the 'Allowed Currencies' Checkboxes visible");
        assertThat(setupAcquirerMidDialog.getAllowedCurrenciesCheckboxes()).hasText("Allowed currencyEURUSDGBP");

        SuperAcquirersPage acquirersPage = setupAcquirerMidDialog
                .clickCloseButton();

        Allure.step("Verify: the 'Add acquirer' dialog is no longer visible");
        assertThat(acquirersPage.getSetupAcquirerMidDialog()).isHidden();
    }

    @Test(dataProvider = "getAcquirersStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("255")
    @Epic("System/Acquirers")
    @Feature("Setup acquirer MID")
    @Description("Verifies that the status radio buttons ('Active' and 'Inactive') toggle correctly.")
    public void testToggleStatusRadioButtonsCorrectly(String status) {
        Locator statusRadiobutton = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .clickSetupAcquirerMidButton()
                .clickStatusRadiobutton(status)
                .getStatusRadiobutton(status);

        Allure.step("Verify: The radiobutton is selected");
        assertThat(statusRadiobutton).hasAttribute("data-selected", "true");
    }

    @Test
    @TmsLink("1119")
    @Epic("System/Acquirers")
    @Feature("Setup acquirer MID")
    @Description("Verify that the 'Entity name' field requires between 4 and 100 characters")
    public void testEntityNameIsMandatoryLengthRestrictions() {
        String invalidControlName3Chars = "a".repeat(3);
        String validControlName4Chars = "a".repeat(4);
        String validControlName100Chars = "a".repeat(100);
        String invalidControlName101Chars = "a".repeat(101);

        SetupAcquirerMidDialog setupAcquirerMidDialog = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .clickSetupAcquirerMidButton()
                .fillChallengeUrlField(DEFAULT_CONFIG.challengeUrl())
                .fillFingerprintUrlField(DEFAULT_CONFIG.fingerprintUrl())
                .fillResourceUrlField(DEFAULT_CONFIG.resourceUrl())
                .fillAcquirerNameField(invalidControlName3Chars);

        String ariaInvalid = setupAcquirerMidDialog.getAcquirerNameField().getAttribute("aria-invalid");

        Allure.step("Verify that the 'Entity name' field is highlighted in red");
        Assert.assertEquals(ariaInvalid, "true", "The 'Entity name' field should be"
                + " highlighted in red");

        Allure.step("Verify that the 'Entity name' field is marked with '*'");
        Assert.assertTrue(((String) setupAcquirerMidDialog.getAcquirerNameLabel()
                        .evaluate("el => getComputedStyle(el, '::after').content")).contains("*"),
                "The '*' symbol is not displayed in the 'Entity name' label");

        Allure.step("Verify that the Create button is disabled if the 'Entity name' contains 3 characters");
        assertThat(setupAcquirerMidDialog.getCreateButton()).isDisabled();

        setupAcquirerMidDialog
                .fillAcquirerNameField(validControlName4Chars);

        Allure.step("Verify that the Create button is enabled if the 'Entity name' field contains 4 characters");
        assertThat(setupAcquirerMidDialog.getCreateButton()).isEnabled();

        setupAcquirerMidDialog
                .fillAcquirerNameField(validControlName100Chars);

        Allure.step("Verify that the Setup button is enabled if the 'Entity name' field contains 100 characters");
        assertThat(setupAcquirerMidDialog.getCreateButton()).isEnabled();

        setupAcquirerMidDialog
                .fillAcquirerNameField(invalidControlName101Chars);

        Allure.step("Verify that the 'Entity name' field has limit of 100 characters");
        Assert.assertEquals(setupAcquirerMidDialog.getAcquirerNameField().inputValue().length(), 100);
    }

    @Test
    @TmsLink("412")
    @Epic("System/Acquirers")
    @Feature("Setup acquirer MID")
    @Description("New acquirer MID can be successfully set up and displayed correctly in the acquirers table.")
    public void testSetupAcquirerMid() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .clickSetupAcquirerMidButton()
                .fillAcquirerNameField(ACQUIRER.getAcquirerName())
                .fillAcquirerDisplayNameField(ACQUIRER.getAcquirerDisplayName())
                .fillAcquirerMidField(ACQUIRER.getAcquirerMid())
                .fillAcquirerMccField(ACQUIRER.getAcquirerMidMcc())
                .fillChallengeUrlField(ACQUIRER.getSystemConfig().challengeUrl())
                .fillFingerprintUrlField(ACQUIRER.getSystemConfig().fingerprintUrl())
                .fillResourceUrlField(ACQUIRER.getSystemConfig().resourceUrl())
                .fillNotificationQueueField(ACQUIRER.getSystemConfig().notificationQueue())
                .clickCheckboxCurrency(ACQUIRER.getCurrency())
                .fillAcquirerConfigField(ACQUIRER.getAcquirerConfig())
                .clickCreateButton();

        Allure.step("Verify: The 'Add acquirer' dialog is no longer visible");
        assertThat(acquirersPage.getSetupAcquirerMidDialog()).isHidden();

        Allure.step("Verify: Acquirer creation success message is displayed");
        assertThat(acquirersPage.getAlert().getMessage())
                .containsText("SUCCESSAcquirer was created successfully");

        acquirersPage
                .getSelectAcquirerMid().selectAcquirerMid(ACQUIRER.getAcquirerDisplayName());

        Allure.step("Verify: Entity name matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Entity name"))
                .hasText(ACQUIRER.getAcquirerName());

        Allure.step("Verify: Display name matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Display name"))
                .hasText(ACQUIRER.getAcquirerDisplayName());

        Allure.step("Verify: Acquirer code is 'NGenius' by default");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Acquirer code"))
                .hasText("NGenius");

        Allure.step("Verify: Acquirer MID matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "MID"))
                .hasText(ACQUIRER.getAcquirerMid());

        Allure.step("Verify: Acquirer MID MCC matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "MCC"))
                .hasText(ACQUIRER.getAcquirerMidMcc());

        Allure.step("Verify: Currencies column contains expected currency");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Currencies"))
                .hasText(ACQUIRER.getCurrency());

        Allure.step("Verify: Acquirer config matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Acquirer config"))
                .hasText(ACQUIRER.getAcquirerConfig());

        Allure.step("Verify: 'System config' cell contains all values in correct order");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "System config"))
                .hasText(ACQUIRER.getSystemConfig().toString());

        Allure.step("Verify: Status matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Status"))
                .hasText("Active");
    }

    @Test(dependsOnMethods = "testSetupAcquirerMid")
    @TmsLink("427")
    @Epic("System/Acquirers")
    @Feature("Setup acquirer MID")
    @Description("Verify error appears when creating an Acquirer with a duplicate name.")
    public void testVerifyErrorOnCreatingAcquirerWithDuplicateName() {
        SetupAcquirerMidDialog setupAcquirerMidDialog = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .clickSetupAcquirerMidButton()
                .fillAcquirerNameField(ACQUIRER.getAcquirerName())
                .fillAcquirerMidField("1234")
                .fillAcquirerMccField("1234")
                .fillChallengeUrlField(DEFAULT_CONFIG.challengeUrl())
                .fillFingerprintUrlField(DEFAULT_CONFIG.fingerprintUrl())
                .fillResourceUrlField(DEFAULT_CONFIG.resourceUrl())
                .clickCheckboxCurrency("USD")
                .clickCreateButtonAndTriggerError();

        Allure.step("Verify: Acquirer Error message is displayed");
        assertThat(setupAcquirerMidDialog.getAlert().getMessage())
                .containsText("Acquirer with name {" + ACQUIRER.getAcquirerName() + "} already exists.");
    }

    @Test(dependsOnMethods = "testVerifyErrorOnCreatingAcquirerWithDuplicateName")
    @TmsLink("239")
    @Epic("System/Acquirers")
    @Feature("Edit acquirer MID")
    @Description("Verifies that all form field placeholders are set correctly")
    public void testVerifyPlaceholdersEditForm() {
        List<String> actualPlaceholders = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirerMid().selectAcquirerMid(ACQUIRER.getAcquirerDisplayName())
                .getTable().clickEditAcquirerMidButton(ACQUIRER.getAcquirerName())
                .getAllPlaceholders();

        Allure.step("Verify placeholders match expected values for all fields");
        assertEquals(actualPlaceholders, PLACEHOLDER_LIST);
    }

    @Test(dependsOnMethods = "testVerifyPlaceholdersEditForm")
    @TmsLink("450")
    @Epic("System/Acquirers")
    @Feature("Edit acquirer MID")
    @Description("Edit Acquirer Mid and Verify Updated Data in the Table")
    public void testEditAcquirerMid() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirerMid().selectAcquirerMid(ACQUIRER.getAcquirerDisplayName())
                .getTable().clickEditAcquirerMidButton(ACQUIRER.getAcquirerDisplayName())
                .fillAcquirerDisplayNameField(ACQUIRER_EDITED.getAcquirerDisplayName())
                .fillAcquirerMidField(ACQUIRER_EDITED.getAcquirerMid())
                .fillAcquirerMccField(ACQUIRER_EDITED.getAcquirerMidMcc())
                .fillChallengeUrlField(ACQUIRER_EDITED.getSystemConfig().challengeUrl())
                .fillFingerprintUrlField(ACQUIRER_EDITED.getSystemConfig().fingerprintUrl())
                .fillResourceUrlField(ACQUIRER_EDITED.getSystemConfig().resourceUrl())
                .fillNotificationQueueField(ACQUIRER_EDITED.getSystemConfig().notificationQueue())
                .fillAcquirerConfigField(ACQUIRER_EDITED.getAcquirerConfig())
                .clickStatusRadiobutton(ACQUIRER_EDITED.getStatus())
                .clickCheckboxCurrency(ACQUIRER_EDITED.getCurrency())
                .clickSaveChangesButton();

        Allure.step("Verify: Successful message");
        assertThat(acquirersPage.getAlert().getMessage())
                .hasText("SUCCESSAcquirer was updated successfully");

        Allure.step("Verify: Acquirer display name matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Display name"))
                .hasText(ACQUIRER_EDITED.getAcquirerDisplayName());

        Allure.step("Verify: Acquirer code is 'NGenius' by default");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER_EDITED.getAcquirerName(), "Acquirer code"))
                .hasText("NGenius");

        Allure.step("Verify: Acquirer MID matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER_EDITED.getAcquirerName(), "MID"))
                .hasText(ACQUIRER_EDITED.getAcquirerMid());

        Allure.step("Verify: Acquirer MID MCC matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER_EDITED.getAcquirerName(), "MCC"))
                .hasText(ACQUIRER_EDITED.getAcquirerMidMcc());

        Allure.step("Verify: Currencies column contains expected currency");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER_EDITED.getAcquirerName(), "Currencies"))
                .hasText(ACQUIRER_EDITED.getCurrency());

        Allure.step("Verify: Acquirer config matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER_EDITED.getAcquirerName(), "Acquirer config"))
                .hasText(ACQUIRER_EDITED.getAcquirerConfig());

        Allure.step("Verify: 'System config' cell contains all values in correct order");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER_EDITED.getAcquirerName(), "System config"))
                .hasText(ACQUIRER_EDITED.getSystemConfig().toString());

        Allure.step("Verify: Status matches expected");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER_EDITED.getAcquirerName(), "Status"))
                .hasText(ACQUIRER_EDITED.getStatus());
    }

    @Test(dependsOnMethods = "testEditAcquirerMid")
    @TmsLink("726")
    @Epic("System/Acquirers")
    @Feature("Delete acquirer")
    @Description("Verify that an acquirer can be deleted")
    public void testDeleteAcquirerMid() {
        SuperAcquirersPage acquirersPage = new SuperAcquirersPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirerMid().selectAcquirerMid(ACQUIRER_EDITED.getAcquirerDisplayName())
                .clickDeleteAcquirerMidButton()
                .clickDeleteButton();

        Allure.step("Verify: a success message appears after deleting the acquirer");
        assertThat(acquirersPage.getAlert().getMessage())
                .hasText("SUCCESSAcquirer was deleted successfully");

        acquirersPage
                .getAlert().clickCloseButton()
                .waitForAcquirerAbsence(getApiRequestContext(), ACQUIRER.getAcquirerName());

        Allure.step("Verify: the deleted acquirer is no longer present in the table");
        assertFalse(acquirersPage.getTable().getColumnValuesFromAllPages("Entity name")
                .contains(ACQUIRER.getAcquirerName()));

        Allure.step("Verify: the deleted acquirer is no longer present in the dropdown list");
        assertFalse(acquirersPage.getSelectAcquirerMid().isAcquirerPresent(ACQUIRER.getAcquirerName()));
    }

    @Test
    @TmsLink("463")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description(
            "Verifies that the Acquirers table displays correct data for the selected acquirer and shows relevant "
                    + "action buttons.")
    public void testDisplaySingleRowWhenAcquirerIsSelected() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirerMid().selectAcquirerMid(ACQUIRER2.getAcquirerDisplayName());

        Allure.step("Verify: List of acquirers has only 1 row in the table");
        assertThat(acquirersPage.getTable().getRows()).hasCount(1);

        Allure.step("Verify: Entity name");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "Entity name"))
                .hasText(ACQUIRER2.getAcquirerName());

        Allure.step("Verify: Display name");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "Display name"))
                .hasText(ACQUIRER2.getAcquirerDisplayName());

        Allure.step("Verify: Acquirer code");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "Acquirer code"))
                .hasText(ACQUIRER2.getAcquirerCode());

        Allure.step("Verify: MID");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "MID"))
                .hasText(ACQUIRER2.getAcquirerMid());

        Allure.step("Verify: MCC");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "MCC"))
                .hasText(ACQUIRER2.getAcquirerMidMcc());

        Allure.step("Verify: Currencies");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "Currencies"))
                .hasText(String.join(", ", Arrays.stream(ACQUIRER2.getCurrencyList()).map(Enum::name).toList()));

        Allure.step("Verify: System config");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "System config"))
                .hasText(ACQUIRER2.getSystemConfig().toString());

        Allure.step("Verify: Status");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER2.getAcquirerName(), "Status"))
                .hasText(ACQUIRER2.getStatus());

        Allure.step("Verify: Edit button is visible");
        assertThat(acquirersPage.getTable().getEditAcquirerMidButton(ACQUIRER2.getAcquirerName())).isVisible();

        Allure.step("Verify: 'Activate acquirer' icon is visible for the acquirer");
        Locator activityIcon = acquirersPage.getTable().getAcquirerActivityIcon(ACQUIRER2.getAcquirerName());
        assertThat(activityIcon).isVisible();
        assertThat(activityIcon).hasAttribute("data-icon", "ban");

        Allure.step("Verify: Pagination shows only one page labeled '1'");
        assertThat(acquirersPage.getTable().getPaginationItems()).isVisible();
        assertThat(acquirersPage.getTable().getPaginationItems()).hasText("1");
    }

    @Test
    @TmsLink("487")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("Table column headers fit inside viewport on each page when navigating through paginated results")
    public void testColumnHeadersDisplayCorrectlyOnAllPages() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        double pageWidth = getPage().viewportSize().width;

        for (String option : ROWS_PER_PAGE_OPTIONS) {
            acquirersPage.getTable().forEachPage(option, activePage -> {
                BoundingBox box = acquirersPage.getTable().getHeaderRow().boundingBox();

                Allure.step(String.format("Verify headers on page '%s' with '%s' pagination", activePage, option));
                assertThat(acquirersPage.getTable().getColumnHeaders()).hasText(COLUMNS_HEADERS);

                assertTrue(box.x >= 0, String.format(
                        "Headers x-position must be within viewport on page '%s' with '%s'/page", activePage, option));

                assertTrue((box.x + box.width) <= pageWidth, String.format(
                        "Headers right edge must be within viewport on page '%s' with '%s'/page", activePage, option));

            });
        }
    }

    @Test(dataProvider = "getAcquirersStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("557")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("Verify Acquirer status 'Active/Inactive' is displayed in column 'Status'")
    public void testVerifyAcquirerStatus(String status) {
        String acquirerName = "%s %s acquirer".formatted(TestUtils.now(), status);
        SystemConfig systemConfig = new SystemConfig();

        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .clickSetupAcquirerMidButton()
                .fillAcquirerNameField(acquirerName)
                .fillAcquirerMidField("1234")
                .fillAcquirerMccField("1234")
                .fillChallengeUrlField(systemConfig.challengeUrl())
                .fillFingerprintUrlField(systemConfig.fingerprintUrl())
                .fillResourceUrlField(systemConfig.resourceUrl())
                .clickStatusRadiobutton(status)
                .clickCheckboxCurrency("USD")
                .clickCreateButton()
                .waitForAcquirerPresence(getApiRequestContext(), acquirerName)
                .getSelectAcquirerMid().selectAcquirerMid(acquirerName);

        Allure.step("Verify: Acquirer status");
        assertThat(acquirersPage.getTable().getFirstRowCell("Status")).hasText(status);

        TestUtils.deleteAcquirer(getApiRequestContext(), acquirerName);
    }

    @Test
    @TmsLink("588")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("Verify Acquirer can be activated and deactivated from the table")
    public void testAcquirerCanBeActivatedAndDeactivated() {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirerMid().selectAcquirerMid(CHANGE_STATE_ACQUIRER.getAcquirerDisplayName())
                .getTable().clickDeactivateAcquirerMidButton(CHANGE_STATE_ACQUIRER.getAcquirerName())
                .clickDeactivateButton();

        Allure.step("Verify: Successful message");
        assertThat(acquirersPage.getAlert().getMessage())
                .hasText("SUCCESSAcquirer was deactivated successfully");

        acquirersPage
                .getAlert().clickCloseButton();

        Allure.step("Verify: Acquirer status changed to Inactive");
        assertThat(acquirersPage.getTable().getCell(CHANGE_STATE_ACQUIRER.getAcquirerName(), "Status"))
                .hasText("Inactive");

        acquirersPage
                .getTable().clickActivateAcquirerMidButton(CHANGE_STATE_ACQUIRER.getAcquirerName())
                .clickActivateButton();

        Allure.step("Verify: Successful message");
        assertThat(acquirersPage.getAlert().getMessage())
                .hasText("SUCCESSAcquirer was activated successfully");

        acquirersPage
                .getAlert().clickCloseButton();

        Allure.step("Verify: Acquirer status changed back to Active");
        assertThat(acquirersPage.getTable().getCell(CHANGE_STATE_ACQUIRER.getAcquirerName(), "Status"))
                .hasText("Active");
    }

    @Test(dataProvider = "getAcquirersStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("708")
    @Epic("System/Acquirers")
    @Feature("Reset")
    @Description("'Reset' button clears selected filter values and resets them to default.")
    public void testResetFilter(String status) {
        SuperAcquirersPage acquirersPage = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirerMid().selectAcquirerMid(ACQUIRER2.getAcquirerDisplayName())
                .getSelectStatus().select(status)
                .clickResetFilterButton();

        //TODO add 'Select acquirer code' assert when component would be realized

        Allure.step("Verify: the selected acquirer filter is cleared");
        assertThat(acquirersPage.getSelectAcquirerMid().getSelectAcquirerMidField()).isEmpty();

        Allure.step("Verify: the status filter is reset to 'All'");
        assertThat(acquirersPage.getSelectStatus().getStatusValue()).hasText("All");
    }

    @Test
    @TmsLink("1101")
    @Epic("System/Acquirers")
    @Feature("Acquirers table")
    @Description("Tooltips for available actions check")
    public void testTooltipsForAcquirersTable() {
        SuperAcquirersPage page = new SuperDashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        Locator editIconTooltip = page
                .getTable().hoverOverEditIcon()
                .getTable().getTooltip();

        Allure.step("Verify that Edit icon Tooltip is appears on the Acquirers table");
        assertThat(editIconTooltip).isVisible();
        assertThat(editIconTooltip).hasText("Edit acquirer MID");

        Locator deactivateIconTooltip = page
                .getTable().hoverOverChangeActivityIcon()
                .getTable().getTooltip();

        Allure.step("Verify that Deactivate icon Tooltip is appears on the Acquirers table");
        assertThat(deactivateIconTooltip).isVisible();
        assertThat(deactivateIconTooltip).hasText("Deactivate acquirer MID");

        Locator deleteIconTooltip = page
                .getTable().hoverOverDeleteIcon()
                .getTable().getTooltip();

        Allure.step("Verify that Delete icon Tooltip is appears on the Acquirers table");
        assertThat(deleteIconTooltip).isVisible();
        assertThat(deleteIconTooltip).hasText("Delete acquirer MID");

        Locator bulkActionsIconTooltip = page
                .getTable().hoverOverBulkActionsIcon()
                .getTable().getTooltip();

        Allure.step("Verify that Bulk actions icon Tooltip is appears on the Acquirers table");
        assertThat(bulkActionsIconTooltip).isVisible();
        assertThat(bulkActionsIconTooltip).hasText("Bulk actions");
    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteAcquirer(getApiRequestContext(), CHANGE_STATE_ACQUIRER.getAcquirerName());
        super.afterClass();
    }
}
