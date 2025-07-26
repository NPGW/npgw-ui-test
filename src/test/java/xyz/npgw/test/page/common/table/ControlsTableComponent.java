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

    private final Locator editIconHover = getByTextExact("Edit control");
    private final Locator deactivateControlHover = getByTextExact("Deactivate control");
    private final Locator deleteHover = getByTextExact("Delete control");
    private final Locator connectControlHover = getByTextExact("Connect control to business unit");
    private final Locator activateControlHover = getByTextExact("Аctivate control");

    public ControlsTableComponent(Page page) {
        super(page, page.getByText("Controls", new Page.GetByTextOptions().setExact(true)).locator("../.."));
    }

    @Override
    protected FraudControlPage getCurrentPage() {
        return new FraudControlPage(getPage());
    }

    @Step("Get Edit Control icon hover")
    public Locator getEditIconHover(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).getByTestId("EditControlButton").hover();

        editIconHover.waitFor();

        return editIconHover;
    }

    @Step("Get Deactivate Control icon hover")
    public Locator getDeactivateControlHover(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).locator("//*[@data-icon='ban']/..").hover();

        deactivateControlHover.waitFor();

        return deactivateControlHover;
    }

    @Step("Get Activate Control icon hover")
    public Locator getActivateControlHover(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).locator("//*[@data-icon='check']/..").hover();

        activateControlHover.waitFor();

        return activateControlHover;
    }

    @Step("Get Activate Control icon hover")
    public Locator getDeleteHover(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).getByTestId("DeleteControlButton").hover();

        deleteHover.waitFor();

        return deleteHover;
    }

    @Step("Get Activate Control icon hover")
    public Locator getConnectControlHover(String controlName) {
        getRow(controlName).hover();
        getRow(controlName).getByTestId("ConnectControlButton").hover();

        connectControlHover.waitFor();

        return connectControlHover;
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
