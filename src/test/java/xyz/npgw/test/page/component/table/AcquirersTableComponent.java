package xyz.npgw.test.page.component.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.dialog.acquirer.ActivateAcquirerDialog;
import xyz.npgw.test.page.dialog.acquirer.ActivateGroupGatewayItemsDialog;
import xyz.npgw.test.page.dialog.acquirer.DeactivateAcquirerDialog;
import xyz.npgw.test.page.dialog.acquirer.DeactivateGroupGatewayItemsDialog;
import xyz.npgw.test.page.dialog.acquirer.DeleteAcquirerDialog;
import xyz.npgw.test.page.dialog.acquirer.EditAcquirerMidDialog;
import xyz.npgw.test.page.system.SuperAcquirersPage;

@Getter
public class AcquirersTableComponent extends BaseTableComponent<SuperAcquirersPage> {

    private final Locator tableContent = getByLabelExact("merchants table").locator("tbody");
    private final Locator deactivateGatewayConnectionsButton = getByTextExact("Deactivate gateway connections");
    private final Locator activateGatewayConnectionsButton = getByTextExact("Activate gateway connections");
    private final Locator editAcquirerMidButton = getByTestId("EditAcquirerButton");
    private final Locator activateAcquirerMidButton = locator("//*[@data-icon='check']/..");
    private final Locator deactivateAcquirerMidButton = locator("//*[@data-icon='ban']/..");
    private final Locator acquirerActivityIcon = getByTestId("ChangeAcquirerActivityButton").locator("svg");
    private final Locator deleteAcquirerMidButton = getByTestId("DeleteAcquirerButton");
    private final Locator acquirerBulkButton = locator("svg[data-icon='wand-magic-sparkles']");

    public AcquirersTableComponent(Page page, SuperAcquirersPage currentPage) {
        super(page, currentPage);
    }

    @Override
    protected SuperAcquirersPage getCurrentPage() {
        return new SuperAcquirersPage(getPage());
    }

    @Step("Click 'Edit acquirer MID' button to edit acquirer")
    public EditAcquirerMidDialog clickEditAcquirerMidButton() {
        editAcquirerMidButton.click();

        return new EditAcquirerMidDialog(getPage());
    }

    @Step("Click 'Activate acquirer MID' button")
    public ActivateAcquirerDialog clickActivateAcquirerMidButton() {
        activateAcquirerMidButton.click();

        return new ActivateAcquirerDialog(getPage());
    }

    @Step("Click 'Deactivate acquirer MID' button")
    public DeactivateAcquirerDialog clickDeactivateAcquirerMidButton() {
        deactivateAcquirerMidButton.click();

        return new DeactivateAcquirerDialog(getPage());
    }

    @Step("Click 'Bulk actions' button")
    public AcquirersTableComponent clickBulkActionsButton(String entityName) {
        Locator bulkButton = getRow(entityName).locator(acquirerBulkButton);
        bulkButton.hover();
        getPage().waitForTimeout(1500);

        bulkButton.click();
        Locator dropdown = locator("div[aria-label='Options']");
        dropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
//
//        activateGatewayConnectionsButton.waitFor();
//        deactivateGatewayConnectionsButton.waitFor();

        return this;
    }

    @Step("Select 'Activate gateway connections'")
    public ActivateGroupGatewayItemsDialog selectActivateGatewayConnections() {
        activateGatewayConnectionsButton.click();

        return new ActivateGroupGatewayItemsDialog(getPage());
    }

    @Step("Select 'Deactivate gateway connections'")
    public DeactivateGroupGatewayItemsDialog selectDeactivateGatewayConnections() {
        deactivateGatewayConnectionsButton.click();

        return new DeactivateGroupGatewayItemsDialog(getPage());
    }

    @Step("Hover over 'Edit acquirer MID' icon")
    public SuperAcquirersPage hoverOverEditIcon() {
        editAcquirerMidButton.first().hover();

        return getCurrentPage();
    }

    @Step("Hover over 'Deactivate acquirer MID' icon")
    public SuperAcquirersPage hoverOverChangeActivityIcon() {
        acquirerActivityIcon.first().hover();

        return getCurrentPage();
    }

    @Step("Hover over 'Delete acquirer MID' icon")
    public SuperAcquirersPage hoverOverDeleteIcon() {
        deleteAcquirerMidButton.first().hover();

        return getCurrentPage();
    }

    @Step("Hover over 'Bulk actions' icon")
    public SuperAcquirersPage hoverOverBulkActionsIcon() {
        acquirerBulkButton.first().hover();

        return getCurrentPage();
    }

    @Step("Click 'Delete acquirer MID' button")
    public DeleteAcquirerDialog clickDeleteAcquirerMidButton() {
        deleteAcquirerMidButton.click();

        return new DeleteAcquirerDialog(getPage());
    }

}
