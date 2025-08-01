package xyz.npgw.test.page.dialog.control;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.system.FraudControlPage;

public class EditControlDialog extends ControlDialog<EditControlDialog> {

    public EditControlDialog(Page page) {
        super(page);
    }

    @Step("Click 'Save changes' button")
    public FraudControlPage clickSaveChangesButton() {
        getByRole(AriaRole.BUTTON, "Save changes").click();

        return getReturnPage();
    }
}
