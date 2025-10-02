package xyz.npgw.test.page.component.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.control.ActivateControlDialog;
import xyz.npgw.test.page.dialog.control.ConnectControlToBusinessUnitDialog;
import xyz.npgw.test.page.dialog.control.DeactivateControlDialog;
import xyz.npgw.test.page.dialog.control.DeleteControlDialog;
import xyz.npgw.test.page.dialog.control.EditControlDialog;
import xyz.npgw.test.page.system.SuperFraudControlPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ControlsTableComponent extends BaseTableComponent<SuperFraudControlPage> {

    private final Locator actionsList = getByRole(AriaRole.DIALOG);

    public ControlsTableComponent(Page page, SuperFraudControlPage currentPage) {
        super(page, currentPage,
                page.getByText("Integrated third party controls", new Page.GetByTextOptions().setExact(true))
                        .locator("../.."));
    }

    @Override
    protected SuperFraudControlPage getCurrentPage() {
        return new SuperFraudControlPage(getPage());
    }

    @Step("Get Fraud Control available actions list")
    public Locator getActionsList(String controlName) {
        clickListIconButton(controlName);

        actionsList.waitFor();

        return actionsList;
    }

    @Step("Click 'List icon' button")
    public void clickListIconButton(String controlName) {
        Locator button = getRow(controlName).locator("//*[@data-icon='list']/..");
        assertThat(button).hasAttribute("aria-expanded", "false");
        button.waitFor();
        button.click();
        assertThat(button).hasAttribute("aria-expanded", "true");
    }

    @Step("Click 'Edit control' button")
    public EditControlDialog clickEditControlButton(String controlName) {
        clickListIconButton(controlName);
        getByTextExact("Edit control").click();
//        getRow(controlName).getByTestId("EditControlButton").click();

        return new EditControlDialog(getPage());
    }

    @Step("Click 'Activate control' button")
    public ActivateControlDialog clickActivateControlButton(String controlName) {
        clickListIconButton(controlName);
        getByTextExact("Activate control").click();
//        getRow(controlName).locator("//*[@data-icon='check']/..").click();

        return new ActivateControlDialog(getPage());
    }

    @Step("Click 'Deactivate control' button")
    public DeactivateControlDialog clickDeactivateControlButton(String controlName) {
        clickListIconButton(controlName);
        getByTextExact("Deactivate control").click();
//        getRow(controlName).locator("//*[@data-icon='ban']/..").click();

        return new DeactivateControlDialog(getPage());
    }

    @Step("Click 'Delete control' button")
    public DeleteControlDialog clickDeleteControlButton(String controlName) {
        clickListIconButton(controlName);
        getByTextExact("Delete control").click();
//        getRow(controlName).getByTestId("DeleteControlButton").click();

        return new DeleteControlDialog(getPage());
    }

    @Step("Click 'Connect control' button")
    public ConnectControlToBusinessUnitDialog clickConnectControlButton(String controlName) {
        clickListIconButton(controlName);
        getByTextExact("Connect control to business unit").click();
//        getRow(controlName).getByTestId("ConnectControlButton").click();

        return new ConnectControlToBusinessUnitDialog(getPage());
    }
}
