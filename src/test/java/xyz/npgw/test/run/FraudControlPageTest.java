package xyz.npgw.test.run;

import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.common.util.TestUtils;
import xyz.npgw.test.page.system.FraudControlPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class FraudControlPageTest extends BaseTest {

    private final String fraudControlName = "Test fraudControl name";

    @Test
    @TmsLink("895")
    @Epic("System/Fraud control")
    @Feature("Fraud control")
    @Description("Verify the error message when attempting to create a Fraud Control with the existing name")
    public void testErrorMessageForExistedName () {
        FraudControlPage fraudControlPage = new FraudControlPage(getPage())
                .clickSystemAdministrationLink()
                .getSystemMenu().clickFraudControlTab()
                .clickAddFraudControl()
                .fillFraudControlNameField(fraudControlName)
                .clickCreateButton()
                .clickAddFraudControl()
                .fillFraudControlNameField(fraudControlName)
                .clickCreateButton();

        Allure.step("Verify that the error message ‘ERROR Entity with name … already exists.’ is displayed.");

        assertThat(fraudControlPage.getAlert().getMessage())
                .hasText("ERROREntity with name {" +fraudControlName+ "} already exists.");
    }

    @AfterClass
    @Override
    protected void afterClass() {
        TestUtils.deleteFraudControl(getApiRequestContext(), fraudControlName);
        super.afterClass();
    }
}
