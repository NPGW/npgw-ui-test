package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.system.AcquirersPage;

public class EditAcquirerDialog extends AcquirerDialog<EditAcquirerDialog> {

    private final Locator createButton = getByRole(AriaRole.BUTTON, "Save changes");

    public EditAcquirerDialog(Page page) {
        super(page);
    }

    @Step("Click on the 'Create' button")
    public AcquirersPage clickSaveChangesButton() {
        getPage().waitForCondition(createButton::isEnabled);
        createButton.click();

        return new AcquirersPage(getPage());
    }
}
