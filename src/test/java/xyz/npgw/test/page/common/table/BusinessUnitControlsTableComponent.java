package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.control.ActivateBusinessUnitControlDialog;
import xyz.npgw.test.page.dialog.control.DeactivateBusinessUnitControlDialog;
import xyz.npgw.test.page.dialog.control.DeleteBusinessUnitControlDialog;
import xyz.npgw.test.page.system.FraudControlPage;

public class BusinessUnitControlsTableComponent extends BaseTableComponent<FraudControlPage> {

    public BusinessUnitControlsTableComponent(Page page) {
        super(page, page.getByLabel("transactions table")
                .or(page.getByRole(AriaRole.GROUP)
                        .filter(new Locator.FilterOptions().setHasText("Rows Per Page"))).last());
    }

    @Override
    protected FraudControlPage getCurrentPage() {
        return new FraudControlPage(getPage());
    }

    @Step("Click 'Move business unit control down' button")
    public FraudControlPage clickMoveBusinessUnitControlDownButton(String priority) {
        getRow(priority).getByTestId("MoveMerchantControlDownButton").click();

        return getCurrentPage();
    }

    @Step("Click 'Move business unit control up' button")
    public FraudControlPage clickMoveBusinessUnitControlUpButton(String priority) {
        getRow(priority).getByTestId("MoveMerchantControlUpButton").click();

        return getCurrentPage();
    }

    @Step("Click 'Activate business unit control' button")
    public ActivateBusinessUnitControlDialog clickActivateBusinessUnitControlButton(String priority) {
        getRow(priority).locator("//*[@data-icon='check']/..").click();

        return new ActivateBusinessUnitControlDialog(getPage());
    }

    @Step("Click 'Deactivate business unit control' button")
    public DeactivateBusinessUnitControlDialog clickDeactivateBusinessUnitControlButton(String priority) {
        getRow(priority).locator("//*[@data-icon='ban']/..").click();

        return new DeactivateBusinessUnitControlDialog(getPage());
    }

    @Step("Click 'Delete business unit control' button")
    public DeleteBusinessUnitControlDialog clickDeleteBusinessUnitControlButton(String priority) {
        getRow(priority).getByTestId("DeleteControlButton").click();

        return new DeleteBusinessUnitControlDialog(getPage());
    }
}
