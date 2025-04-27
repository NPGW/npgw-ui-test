package xyz.npgw.test.run;

import io.qameta.allure.*;
import org.testng.annotations.Test;
import xyz.npgw.test.common.TestUtils;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;
import static org.testng.Assert.assertEquals;

public class EditBusinessUnitDialogTest extends BaseTest {

    @Test
    @TmsLink("387")
    @Epic("System/Companies and business units")
    @Feature("Edit business unit")
    @Description("Verify that the title of the 'Edit Business Unit' window matches the expected result")
    public void testVerifyTitleEditBusinessUnitWindow() {
        String buName = "NewBUForEdit";
        String companyName = "CompanyForBU";
        TestUtils.deleteCompany(getApiRequestContext(), companyName);
        TestUtils.createCompany(getApiRequestContext(), companyName);
        TestUtils.createBusinessUnit(getApiRequestContext(), companyName, buName);

        String getWindowTitle = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .getSystemMenu().clickCompaniesAndBusinessUnitsTab()
                .clickSelectCompanyDropdown()
                .clickCompanyInDropdown(companyName)
                .clickEditBusinessUnitButton()
                .getDialogHeader()
                .innerText();

        Allure.step("Verify: the header contains the expected title text");
        assertEquals(getWindowTitle, "Edit merchant");
    }
}
