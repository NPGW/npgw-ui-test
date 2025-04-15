package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.system.GatewayPage;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GatewayPageTest extends BaseTest {

    @Test
    @TmsLink("283")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("The 'Currency' dropdown toggles and contains options All, USD, EUR.")
    public void testOpenCurrencyDropdown() {
        Locator actualOptions = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .getSystemMenu()
                .clickGatewayTab()
                .clickCurrencyPlaceholder()
                .getCurrencyOptions();

        Allure.step("Verify: The 'Currency' dropdown toggles and contains options All, USD, EUR.");
        assertThat(actualOptions).hasText(new String[]{"ALL", "USD", "EUR"});
    }

    @Ignore
    @Test
    @TmsLink("285")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("Verify that re-selecting an already selected currency keeps the selection unchanged.")
    public void testRetainCurrencyWhenReSelectingSameOption() {

        List<String> expectedOptions = List.of("All", "USD", "EUR");

        GatewayPage gatewayPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .getSystemMenu()
                .clickGatewayTab();

        for (String currency : expectedOptions) {
            Locator actualCurrency = gatewayPage
                    .clickCurrencyPlaceholder()
                    .selectCurrency(currency)
                    .getCurrencyPlaceholder();

            Allure.step("Verify placeholder matches expected value: " + currency);
            assertThat(actualCurrency).hasText(currency);

            gatewayPage.clickCurrencyPlaceholder()
                    .selectCurrency(currency)
                    .getCurrencyPlaceholder();

            Allure.step("Verify again placeholder matches expected value: " + currency);
            assertThat(actualCurrency).hasText(currency);
        }
    }
}
