package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import net.datafaker.Faker;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.Company;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.system.GatewayPage;

import java.util.Arrays;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GatewayPageTest extends BaseTest {

    @Ignore("FAU 23/05")
    @Test
    @TmsLink("283")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("The 'Currency' dropdown toggles and contains options All, USD, EUR.")
    public void testOpenCurrencyDropdown() {
        Locator actualOptions = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickGatewayTab()
                .clickCurrencyValue()
                .getCurrencyOptions();

        Allure.step("Verify: The 'Currency' dropdown toggles and contains options All, USD, EUR.");
        assertThat(actualOptions).hasText(new String[]{"ALL", "USD", "EUR"});
    }

    @Ignore("FAU 23/05")
    @Test(expectedExceptions = AssertionError.class)
    @TmsLink("285")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("Verify that re-selecting an already selected currency keeps the selection unchanged.")
    public void testRetainCurrencyWhenReSelectingSameOption() {

        Allure.step("This test is temporarily disabled till bug fixed.");

        List<String> expectedOptions = List.of("ALL", "USD", "EUR");

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

    @Ignore
    @Test
    @TmsLink("307")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description(
            "Check that selecting a company populates the 'Business units list',"
                    + " and when no company is selected, the list is empty with 'No items.'")
    public void testBusinessUnitsListUpdatesOnCompanySelection() {

        String companyName = "Company 112172";
        String[] expectedBusinessUnitsList = new String[]{"Merchant 1 for C112172", "Merchant for C112172"};
        int expectedCount = expectedBusinessUnitsList.length;

        GatewayPage gatewayPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickGatewayTab()
                .getSelectCompany().clickSelectCompanyPlaceholder()
                .getSelectCompany().selectCompany(companyName);

        Locator selectCompanyPlaceholder = gatewayPage
                .getSelectCompany().getSelectCompanyPlaceholder();

        Allure.step("Verify: The dropdown is closed.");
        assertThat(gatewayPage.getSelectCompany().getCompanyDropdown()).not().isVisible();

        Allure.step(String.format("Verify: Placeholder has value '%s'", companyName));
        assertThat(selectCompanyPlaceholder).hasValue(companyName);

        Allure.step("Verify: 'Business units list' title is visible");
        assertThat(gatewayPage.getBusinessUnitsListHeader()).isVisible();

        Allure.step(String.format("Verify: Business units list has expected count of %d", expectedCount));
        Locator actualBusinessUnitsList = gatewayPage.getBusinessUnitsList();
        assertThat(actualBusinessUnitsList).hasCount(expectedCount);

        for (int i = 0; i < expectedCount; i++) {
            String actualBusinessUnitsText = actualBusinessUnitsList.nth(i).innerText().trim();
            Allure.step(String.format("Verify: '%s' is in expected list", actualBusinessUnitsText));
            Assert.assertTrue(Arrays.asList(expectedBusinessUnitsList).contains(actualBusinessUnitsText),
                    String.format("Unexpected item: %s", actualBusinessUnitsText));
        }

        gatewayPage
                .getSelectCompany().clickSelectCompanyClearIcon()
                .getSelectCompany().clickSelectCompanyDropdownChevron();

        Allure.step("Verify: Placeholder has value 'Search...'", () -> {
            Assert.assertEquals(selectCompanyPlaceholder.getAttribute("placeholder"), "Search...");
            assertThat(selectCompanyPlaceholder).hasValue("");
        });

        Allure.step("Verify: 'Business units list' title is still visible");
        assertThat(gatewayPage.getBusinessUnitsListHeader()).isVisible();

        Allure.step("Verify: 'Business units list' has 'No items.'");
        assertThat(gatewayPage.getBusinessUnitsList()).hasText(new String[]{"No items."});
    }

    @Ignore
    @Test
    @TmsLink("602")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("Verify that if company is selected all it's business units are presented in the list")
    public void testCompaniesBusinessUnitsPresence() {
        Company company = new Company(new Faker());
        String businessUnit = new Faker().company().industry();

        GatewayPage gatewayPage = new DashboardPage(getPage())
                .getHeader().clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickAddCompanyButton()
                .fillCompanyNameField(company.companyName())
                .fillCompanyTypeField(company.companyType())
                .clickCreateButton()
                .getAlert().waitUntilSuccessAlertIsGone()
                .getSelectCompany().selectCompany(company.companyName())
                .clickOnAddBusinessUnitButton()
                .fillBusinessUnitNameField(company.companyType())
                .clickCreateButton()
                .getAlert().waitUntilSuccessAlertIsGone()
                .clickOnAddBusinessUnitButton()
                .fillBusinessUnitNameField(businessUnit)
                .clickCreateButton()
                .getAlert().waitUntilSuccessAlertIsGone()
                .getSystemMenu().clickGatewayTab()
                .getSelectCompany().clickSelectCompanyPlaceholder()
                .getSelectCompany().selectCompany(company.companyName());

        Allure.step("Verify that all the Business units are presented in the list");
        assertThat(gatewayPage.getBusinessUnitsBlock()).containsText(company.companyType());
        assertThat(gatewayPage.getBusinessUnitsBlock()).containsText(businessUnit);

        TestUtils.deleteAllByMerchantTitle(getApiRequestContext(), company.companyName(), company.companyType());
        TestUtils.deleteAllByMerchantTitle(getApiRequestContext(), company.companyName(), businessUnit);
        TestUtils.deleteCompany(getApiRequestContext(), company.companyName());
    }
}
