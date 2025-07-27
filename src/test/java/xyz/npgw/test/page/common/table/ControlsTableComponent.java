package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.control.ActivateControlDialog;
import xyz.npgw.test.page.dialog.control.ConnectControlToBusinessUnitDialog;
import xyz.npgw.test.page.dialog.control.DeactivateControlDialog;
import xyz.npgw.test.page.dialog.control.DeleteControlDialog;
import xyz.npgw.test.page.dialog.control.EditControlDialog;
import xyz.npgw.test.page.system.FraudControlPage;

public class ControlsTableComponent extends BaseTableComponent<FraudControlPage> {

    private final Locator editIconTooltip = getByTextExact("Edit control");
    private final Locator deactivateControlTooltip = getByTextExact("Deactivate control");
    private final Locator deleteTooltip = getByTextExact("Delete control");
    private final Locator connectControlTooltip = getByTextExact("Connect control to business unit");
    private final Locator activateControlTooltip = getByTextExact("Аctivate control");

    public ControlsTableComponent(Page page) {
        super(page, page.getByText("Controls", new Page.GetByTextOptions().setExact(true)).locator("../.."));
    }

    @Override
    protected FraudControlPage getCurrentPage() {
        return new FraudControlPage(getPage());
    }

    @Step("Get Edit Control icon Tooltip")
    public Locator hoverOverEditIcon(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).getByTestId("EditControlButton").hover();

        editIconTooltip.waitFor();

        return editIconTooltip;
    }

    @Step("Get Deactivate Control icon Tooltip")
    public Locator hoverOverDeactivateControl(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).locator("//*[@data-icon='ban']/..").hover();

        deactivateControlTooltip.waitFor();

        return deactivateControlTooltip;
    }

    @Step("Get Activate Control icon Tooltip")
    public Locator hoverOverActivateControl(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).locator("//*[@data-icon='check']/..").hover();

        activateControlTooltip.waitFor();

        return activateControlTooltip;
    }

    @Step("Get Activate Control icon Tooltip")
    public Locator hoverOverDelete(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).getByTestId("DeleteControlButton").hover();

        deleteTooltip.waitFor();

        return deleteTooltip;
    }

    @Step("Get Activate Control icon Tooltip")
    public Locator hoverOverConnectControl(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).getByTestId("ConnectControlButton").hover();

        connectControlTooltip.waitFor();

        return connectControlTooltip;
    }

    @Step("Click 'Edit control' button")
    public EditControlDialog clickEditControlButton(String controlName) {
        getRow(controlName).getByTestId("EditControlButton").click();

        return new EditControlDialog(getPage());
    }

    @Step("Click 'Activate control' button")
    public ActivateControlDialog clickActivateControlButton(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).locator("//*[@data-icon='check']/..").click();

        return new ActivateControlDialog(getPage());
    }

    @Step("Click 'Deactivate control' button")
    public DeactivateControlDialog clickDeactivateControlButton(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).locator("//*[@data-icon='ban']/..").click();

        return new DeactivateControlDialog(getPage());
    }

    @Step("Click 'Delete control' button")
    public DeleteControlDialog clickDeleteControlButton(String controlName) {
        getRow(controlName).getByTestId("DeleteControlButton").click();

        return new DeleteControlDialog(getPage());
    }

    @Step("Click 'Connect control' button")
    public ConnectControlToBusinessUnitDialog clickConnectControlButton(String controlName) {
        getRow(controlName).getByTestId("ConnectControlButton").click();

        return new ConnectControlToBusinessUnitDialog(getPage());
    }
}
