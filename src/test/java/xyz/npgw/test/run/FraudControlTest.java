package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.entity.FraudControl;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.system.FraudControlPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FraudControlTest extends BaseTest {

    private final FraudControl fraudControl = FraudControl.builder()
            .controlName("ControlEverything")
            .controlCode("8848")
            .controlDisplayName("ControlDisplay")
            .controlConfig("default")
            .build();

    @Test
    @TmsLink("")
    @Epic("System/Fraud Control")
    @Feature("Add/Edit/Delete Fraud Control")
    @Description("Add Active Fraud Control")
    public void testAddActiveFraudControl() {
        FraudControlPage page = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickFraudControlTab()
                .clickAddFraudControl()
                .fillControlNameField(fraudControl.getControlName())
                .fillControlCodeField(fraudControl.getControlCode())
                .fillControlDisplayNameField(fraudControl.getControlDisplayName())
                .fillConfigField(fraudControl.getControlConfig())
                .clickCreateButton();

        Locator row = page.getTable().getRow(fraudControl.getControlName()).first();

        assertThat(row).containsText(fraudControl.getControlCode());
        assertThat(row).containsText(fraudControl.getControlName());
        assertThat(row).containsText(fraudControl.getControlConfig());
        assertThat(row).containsText(fraudControl.getControlDisplayName());
        assertThat(row).containsText("Active");
    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteFraudControl(getApiRequestContext(), fraudControl.getControlName());
        super.afterClass();
    }
}
