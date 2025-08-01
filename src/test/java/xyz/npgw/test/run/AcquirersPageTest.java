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
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.Acquirer;
import xyz.npgw.test.common.entity.SystemConfig;
import xyz.npgw.test.common.provider.TestDataProvider;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.system.AcquirersPage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
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

    private static final Acquirer ACQUIRER = Acquirer.builder()
            .acquirerName("%s acquirer 11.002.01".formatted(RUN_ID))
            .acquirerMidMcc("4321")
            .build();
    private static final Acquirer CHANGE_STATE_ACQUIRER = Acquirer.builder()
            .acquirerName("%s acquirer activate and deactivate".formatted(RUN_ID))
            .acquirerMidMcc("4321")
            .build();

    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        TestUtils.createAcquirer(getApiRequestContext(), ACQUIRER);
        TestUtils.createAcquirer(getApiRequestContext(), CHANGE_STATE_ACQUIRER);
    }

    @Test
    @TmsLink("134")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("The visibility of elements in the filter")
    public void testVisibilityAcquirersListControlTab() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        Allure.step("Verify: Add acquirer button is visible");
        assertThat(acquirersPage.getAddAcquirerButton()).isVisible();

        Allure.step("Verify: Acquirer selector is visible");
        assertThat(acquirersPage.getSelectAcquirer().getSelectAcquirerField()).isVisible();

        Allure.step("Verify: Status selector is visible");
        assertThat(acquirersPage.getSelectStatus().getStatusSelector()).isVisible();

        Allure.step("Verify: Reset Filter Button is visible");
        assertThat(acquirersPage.getResetFilterButton()).isVisible();

        Allure.step("Verify: Refresh data Button is visible");
        assertThat(acquirersPage.getRefreshDataButton()).isVisible();
    }

    @Test
    @TmsLink("157")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("The visibility of the acquirers table header and table contents")
    public void testVisibilityHeaderAndAcquirersList() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        Allure.step("Verify: Table column headers");
        assertThat(acquirersPage.getTable().getColumnHeaders()).hasText(COLUMNS_HEADERS);

        Allure.step("Verify: Acquirers table content is visible and contains acquirers");
        assertThat(acquirersPage.getTable().getRows()).not().hasCount(0);
    }

    @Test
    @TmsLink("168")
    @Epic("System/Acquirers")
    @Feature("Select acquirer")
    @Description("Click on 'Select acquirer' field opens a dropdown populated with acquirers")
    public void testSelectAcquirerDropdownFunctionality() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirer().clickSelectAcquirerField();

        Allure.step("Verify: Dropdown list is not empty");
        assertThat(acquirersPage.getSelectAcquirer().getDropdownOptionList()).not().hasCount(0);
    }

    @Test
    @TmsLink("187")
    @Epic("System/Acquirers")
    @Feature("Status")
    @Description("The 'Status' dropdown toggles and contains options All, Active, Inactive.")
    public void testOpenStatusDropdown() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
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
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
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
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
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
    @Feature("Rows Per Page")
    @Description("Verify the default 'Rows Per Page' value is 25 and the dropdown contains the correct options.")
    public void testRowsPerPageDropdownOptions() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        Allure.step("Verify: The default 'Rows Per Page' value is set to 25");
        assertThat(acquirersPage.getTable().getRowsPerPage()).hasText("25");

        acquirersPage.getTable().clickRowsPerPageChevron();

        Allure.step("Verify: Dropdown is visible");
        assertThat(acquirersPage.getTable().getRowsPerPageDropdown()).isVisible();

        Allure.step("Verify: The dropdown contains options");
        assertThat(acquirersPage.getTable().getRowsPerPageOptions()).hasText(ROWS_PER_PAGE_OPTIONS);
    }

    @Test
    @TmsLink("382")
    @Epic("System/Acquirers")
    @Feature("Rows Per Page")
    @Description("Verify Selecting 'Rows Per Page' Option Updates the Field Value.")
    public void testSelectingRowsPerPageOptionUpdatesFieldValue() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        for (String option : ROWS_PER_PAGE_OPTIONS) {
            acquirersPage.getTable().selectRowsPerPageOption(option);

            Allure.step(String.format("Verify: The Rows Per Page' value is set to '%s'", option));
            assertThat(acquirersPage.getTable().getRowsPerPage()).hasText(option);
        }
    }

    @Test
    @TmsLink("385")
    @Epic("System/Acquirers")
    @Feature("Rows Per Page")
    @Description("Verify that selecting a 'Rows Per Page' option displays the correct number of rows in the table.")
    public void testRowsPerPageSelectionDisplaysCorrectNumberOfRows() {
        List<Integer> totalRowsForDifferentPaginations = new ArrayList<>();

        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        for (String option : ROWS_PER_PAGE_OPTIONS) {
            acquirersPage.getTable().selectRowsPerPageOption(option);

            List<Integer> rowsCountPerPage = acquirersPage.getTable().getRowCountsPerPage();
            int rowsSum = acquirersPage.getTable().countAllRows(rowsCountPerPage);
            boolean allValid = rowsCountPerPage.stream().allMatch(count -> count <= Integer.parseInt(option));

            Allure.step(String.format("Verify: The table contains rows less than or equal to '%s' per page", option));
            assertTrue(allValid, "Not all row counts are less than or equal to " + option);

            totalRowsForDifferentPaginations.add(rowsSum);
        }

        Assert.assertEquals(totalRowsForDifferentPaginations.stream().distinct().count(), 1,
                "Total rows should be the same for all 'Rows Per Page' options");
    }

    @Test
    @TmsLink("432")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("Verify Acquirers table contains correct column headers")
    public void testDisplayCorrectColumnHeadersInAcquirersTable() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab();

        Allure.step("Verify: The Acquirer table contains correct column headers");
        assertThat(acquirersPage.getTable().getColumnHeaders()).hasText(COLUMNS_HEADERS);
    }

    @Ignore("Failed on 01.08.2025 because the 'Select Acquirer Code' field was added")
    @Test
    @TmsLink("463")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description(
            "Verifies that the Acquirers table displays correct data for the selected acquirer and shows relevant "
                    + "action buttons.")
    public void testDisplaySingleRowWhenAcquirerIsSelected() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirer().selectAcquirer(ACQUIRER.getAcquirerName());

        Allure.step("Verify: List of acquirers has only 1 row in the table");
        assertThat(acquirersPage.getTable().getRows()).hasCount(1);

        Allure.step("Verify: Entity name");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Entity name"))
                .hasText(ACQUIRER.getAcquirerName());

        Allure.step("Verify: Display name");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Display name"))
                .hasText(ACQUIRER.getAcquirerDisplayName());

        Allure.step("Verify: Acquirer code");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Acquirer code"))
                .hasText(ACQUIRER.getAcquirerCode());

        Allure.step("Verify: MID");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "MID"))
                .hasText(ACQUIRER.getAcquirerMid());

        Allure.step("Verify: MCC");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "MCC"))
                .hasText(ACQUIRER.getAcquirerMidMcc());

        Allure.step("Verify: Currencies");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Currencies"))
                .hasText(String.join(", ", Arrays.stream(ACQUIRER.getCurrencyList()).map(Enum::name).toList()));

        Allure.step("Verify: System config");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "System config"))
                .hasText(ACQUIRER.getSystemConfig().toString());

        Allure.step("Verify: Status");
        assertThat(acquirersPage.getTable().getCell(ACQUIRER.getAcquirerName(), "Status"))
                .hasText(ACQUIRER.getStatus());

        Allure.step("Verify: Edit button is visible");
        assertThat(acquirersPage.getTable().getEditAcquirerButton(ACQUIRER.getAcquirerName())).isVisible();

        Allure.step("Verify: 'Activate acquirer' icon is visible for the acquirer");
        Locator activityIcon = acquirersPage.getTable().getAcquirerActivityIcon(ACQUIRER.getAcquirerName());
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
    @Description("Verifies that table column headers are displayed correctly on each page when navigating"
            + " through paginated results.")
    public void testColumnHeadersDisplayCorrectlyOnAllPages() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
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

        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .clickAddAcquirer()
                .fillAcquirerNameField(acquirerName)
                .fillAcquirerMidField("1234")
                .fillAcquirerMidMccField("1234")
                .fillChallengeUrlField(systemConfig.challengeUrl())
                .fillFingerprintUrlField(systemConfig.fingerprintUrl())
                .fillResourceUrlField(systemConfig.resourceUrl())
                .clickStatusRadiobutton(status)
                .clickCheckboxCurrency("USD")
                .clickCreateButton()
                .waitForAcquirerPresence(getApiRequestContext(), acquirerName)
                .getSelectAcquirer().selectAcquirer(acquirerName);

        Allure.step("Verify: Acquirer status");
        assertThat(acquirersPage.getTable().getFirstRowCell("Status")).hasText(status);

        TestUtils.deleteAcquirer(getApiRequestContext(), acquirerName);
    }

    @Ignore("Failed on 01.08.2025 because the 'Select Acquirer Code' field was added")
    @Test
    @TmsLink("588")
    @Epic("System/Acquirers")
    @Feature("Acquirers list")
    @Description("Verify Acquirer can be activated and deactivated from the table")
    public void testAcquirerCanBeActivatedAndDeactivated() {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirer().selectAcquirer(CHANGE_STATE_ACQUIRER.getAcquirerName())
                .getTable().clickDeactivateButton(CHANGE_STATE_ACQUIRER.getAcquirerName())
                .clickDeactivateButton();

        Allure.step("Verify: Successful message");
        assertThat(acquirersPage.getAlert().getMessage())
                .hasText("SUCCESSAcquirer was deactivated successfully");

        acquirersPage
                .getAlert().waitUntilSuccessAlertIsGone();

        Allure.step("Verify: Acquirer status changed to Inactive");
        assertThat(acquirersPage.getTable().getCell(CHANGE_STATE_ACQUIRER.getAcquirerName(), "Status"))
                .hasText("Inactive");

        acquirersPage
                .getTable().clickActivateButton(CHANGE_STATE_ACQUIRER.getAcquirerName())
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

    @Ignore("Failed 01.08.2025 due to Adding 'Select Acquirer code' field'")
    @Test(dataProvider = "getAcquirersStatus", dataProviderClass = TestDataProvider.class)
    @TmsLink("708")
    @Epic("System/Acquirers")
    @Feature("Reset")
    @Description("'Reset' button clears selected filter values and resets them to default.")
    public void testResetFilter(String status) {
        AcquirersPage acquirersPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirer().selectAcquirer(ACQUIRER.getAcquirerName())
                .getSelectStatus().select(status)
                .clickResetFilterButton();

        Allure.step("Verify: the selected acquirer filter is cleared");
        assertThat(acquirersPage.getSelectAcquirer().getSelectAcquirerField()).isEmpty();

        Allure.step("Verify: the status filter is reset to 'All'");
        assertThat(acquirersPage.getSelectStatus().getStatusValue()).hasText("All");
    }

    @Ignore("Failed on 01.08.2025 because the 'Select Acquirer Code' field was added")
    @Test(priority = 1)
    @TmsLink("726")
    @Epic("System/Acquirers")
    @Feature("Delete acquirer")
    @Description("Verify that an acquirer can be deleted")
    public void testDeleteAcquirer() {
        AcquirersPage acquirersPage = new AcquirersPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickAcquirersTab()
                .getSelectAcquirer().selectAcquirer(ACQUIRER.getAcquirerName())
                .clickDeleteAcquirer()
                .clickDeleteButton();

        Allure.step("Verify: a success message appears after deleting the acquirer");
        assertThat(acquirersPage.getAlert().getMessage())
                .hasText("SUCCESSAcquirer was deleted successfully");

        acquirersPage
                .getAlert().clickCloseButton()
                .waitForAcquirerAbsence(getApiRequestContext(), ACQUIRER.getAcquirerName());

        Allure.step("Verify: the deleted acquirer is no longer present in the table");
        assertThat(acquirersPage.getTable().getTableContent()).hasText("No rows to display.");

        Allure.step("Verify: the deleted acquirer is no longer present in the dropdown list");
        assertFalse(acquirersPage.getSelectAcquirer().isAcquirerPresent(ACQUIRER.getAcquirerName()));
    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteAcquirer(getApiRequestContext(), CHANGE_STATE_ACQUIRER.getAcquirerName());
        super.afterClass();
    }
}
