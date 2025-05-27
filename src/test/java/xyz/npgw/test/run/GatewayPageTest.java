package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.BusinessUnit;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.system.GatewayPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GatewayPageTest extends BaseTest {

    private final String companyName = "Company 112172%s".formatted(runId);
    private final String[] expectedBusinessUnitsList = new String[]{"Merchant 1 for C112172", "Merchant 2 for C112172"};
    private final String[] expectedOptions = new String[]{"ALL", "EUR", "USD", "GBP"};
    private BusinessUnit[] businessUnits;

    @BeforeClass
    @Override
    protected void beforeClass() {
        super.beforeClass();
        TestUtils.createCompany(getApiRequestContext(), companyName);
        businessUnits = TestUtils.createBusinessUnits(getApiRequestContext(), companyName, expectedBusinessUnitsList);
    }

    @Test
    @TmsLink("283")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("The 'Currency' dropdown toggles and contains options")
    public void testOpenCurrencyDropdown() {
        GatewayPage gatewayPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickGatewayTab()
                .clickCurrencyValue();

        Allure.step("Verify: The 'Currency' dropdown toggles and contains options");
        assertThat(gatewayPage.getCurrencyOptions()).hasText(expectedOptions);
    }

    @Test
    @TmsLink("285")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("Verify that re-selecting an already selected currency keeps the selection unchanged.")
    public void testRetainCurrencyWhenReSelectingSameOption() {
        GatewayPage gatewayPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickGatewayTab();

        Locator actualCurrency = gatewayPage
                .getCurrencyValue();

        for (String currency : expectedOptions) {
            gatewayPage
                    .clickCurrencyValue()
                    .selectCurrency(currency);

            Allure.step("Verify currency has value: " + currency);
            assertThat(actualCurrency).hasText(currency);

            gatewayPage
                    .clickCurrencyValue()
                    .selectCurrency(currency);

            Allure.step("Verify currency has the same value: " + currency);
            assertThat(actualCurrency).hasText(currency);
        }
    }

    @Test
    @TmsLink("307")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("Check that selecting a company populates the 'Business units list',"
            + " and when no company is selected, the list is empty with 'No items.'")
    public void testBusinessUnitsListUpdatesOnCompanySelection() {
        GatewayPage gatewayPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickGatewayTab()
                .getSelectCompany().clickSelectCompanyPlaceholder()
                .getSelectCompany().selectCompany(companyName);

        Locator selectCompanyPlaceholder = gatewayPage
                .getSelectCompany().getSelectCompanyPlaceholder();

        Allure.step("Verify: The dropdown is closed.");
        assertThat(gatewayPage.getSelectCompany().getCompanyDropdown()).not().isVisible();

        Allure.step("Verify: Placeholder value");
        assertThat(selectCompanyPlaceholder).hasValue(companyName);

        Allure.step("Verify: 'Business units list' title is visible");
        assertThat(gatewayPage.getBusinessUnitsListHeader()).isVisible();

        Allure.step("Verify: Business units list length");
        assertThat(gatewayPage.getBusinessUnitsList()).hasCount(expectedBusinessUnitsList.length);

        Allure.step("Verify: Expected list");
        assertThat(gatewayPage.getBusinessUnitsList()).hasText(expectedBusinessUnitsList);

        gatewayPage
                .getSelectCompany().clickSelectCompanyClearIcon()
                .getSelectCompany().clickSelectCompanyDropdownChevron();

        Allure.step("Verify: Placeholder has value 'Search...'");
        assertThat(selectCompanyPlaceholder).hasAttribute("placeholder", "Search...");

        Allure.step("Verify: Field is empty");
        assertThat(selectCompanyPlaceholder).isEmpty();

        Allure.step("Verify: 'Business units list' title is still visible");
        assertThat(gatewayPage.getBusinessUnitsListHeader()).isVisible();

        Allure.step("Verify: 'Business units list' has 'No items.'");
        assertThat(gatewayPage.getBusinessUnitsList()).hasText(new String[]{"No items."});
    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteBusinessUnits(getApiRequestContext(), companyName, businessUnits);
        TestUtils.deleteCompany(getApiRequestContext(), companyName);
        super.afterClass();
    }
}
