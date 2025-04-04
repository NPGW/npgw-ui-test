package xyz.npgw.test.run;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.AddCompanyDialog;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.systemadministration.CompaniesAndBusinessUnitsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class AddCompanyDialogTest extends BaseTest {

    @Test
    @TmsLink("160")
    @Epic("Companies and business units")
    @Feature("Title Verification")
    @Description("Verify that the 'Add Company' window displays the correct title in the header.")
    public void testVerifyAddCompanyWindowTitle() {
        AddCompanyDialog addCompanyDialog = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(addCompanyDialog.getAddCompanyDialogHeader()).hasText("Add company");
    }

    @Test
    @TmsLink("184")
    @Feature("Close Button Functionality")
    @Description("Verify that clicking the Close button successfully closes the 'Add Company' dialog.")
    public void testVerifyCloseAddCompanyDialogWhenCloseButtonIsClicked() {
        CompaniesAndBusinessUnitsPage companiesAndBusinessUnitsPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton()
                .clickCloseButton();

        Allure.step("Verify: the 'Add Company' dialog is no longer visible");
        assertThat(companiesAndBusinessUnitsPage.getAddCompanyDialog()).not().isVisible();
    }
}
