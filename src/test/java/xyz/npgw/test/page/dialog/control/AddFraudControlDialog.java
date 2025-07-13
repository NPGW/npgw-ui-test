package xyz.npgw.test.page.dialog.control;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.system.FraudControlPage;

public class AddFraudControlDialog extends FraudControlDialog<AddFraudControlDialog> implements
        AlertTrait<AddFraudControlDialog> {

    private final Locator createButton = getByRole(AriaRole.BUTTON, "Create");
    private final Locator controlNameField = getByPlaceholder("Enter control name");
    private final Locator controlCodeField = getByPlaceholder("Enter control code");
    private final Locator controlDisplayNameField = getByPlaceholder("Enter display name");
    private final Locator controlConfigField = getByPlaceholder("Enter control config");

    public AddFraudControlDialog(Page page) {
        super(page);
    }

    @Step("Click on the 'Create' button and trigger an error")
    public AddFraudControlDialog clickCreateButtonAndTriggerError() {
        createButton.click();

        return this;
    }

    @Step("Click on the 'Create' button")
    public FraudControlPage clickCreateButton() {
        createButton.click();

        return getReturnPage();
    }

    @Step("Fill in 'Control name' field")
    public AddFraudControlDialog fillControlNameField(String name) {
        getDialogHeader().waitFor();
        controlNameField.fill(name);

        return this;
    }

    @Step("Fill in 'Control code' field")
    public AddFraudControlDialog fillControlCodeField(String code) {
        controlCodeField.fill(code);

        return this;
    }

    @Step("Fill in 'Display name' field")
    public AddFraudControlDialog fillControlDisplayNameField(String display) {
        controlDisplayNameField.fill(display);

        return this;
    }

    @Step("Fill in 'Display name' field")
    public AddFraudControlDialog fillConfigField(String config) {
        controlConfigField.fill(config);

        return this;
    }
}
