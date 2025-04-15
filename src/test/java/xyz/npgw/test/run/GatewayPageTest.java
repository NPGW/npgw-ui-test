package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class GatewayPageTest extends BaseTest {

    @Test
    @TmsLink("282")
    @Epic("System/Gateway")
    @Feature("Currency")
    @Description("The 'Currency' dropdown toggles and contains options All, USD, EUR.")
    public void testOpenCurrencyDropdown() {
        Locator actualOptions = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .getSystemMenu()
                .clickGatewayTab()
                .clickGatewayCurrencyPlaceholder()
                .getGatewayCurrencyOptions();

        Allure.step("Verify: The 'Currency' dropdown toggles and contains options All, USD, EUR.");
        assertThat(actualOptions).hasText(new String[]{"ALL", "USD", "EUR"});
    }
}
