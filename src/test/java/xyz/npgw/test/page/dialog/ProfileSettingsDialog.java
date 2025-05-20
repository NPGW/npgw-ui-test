package xyz.npgw.test.page.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.HeaderPage;

public class ProfileSettingsDialog<ReturnPageT extends HeaderPage<ReturnPageT>>
        extends BaseDialog<ReturnPageT, ProfileSettingsDialog<ReturnPageT>> {

    private final Locator passwordField = getByPlaceholder("Enter new password");
    private final Locator repeatPasswordField = getByPlaceholder("Repeat new password");
    private final Locator saveButton = locator("button:has-text('Save')");

    public ProfileSettingsDialog(Page page, ReturnPageT returnPage) {
        super(page, returnPage);
    }

    @Step("Enter new password in the 'Password' field")
    public ProfileSettingsDialog<ReturnPageT> fillPasswordField(String newPassword) {
        passwordField.fill(newPassword);

        return this;
    }

    @Step("Enter new password in the 'Repeat Password' field")
    public ProfileSettingsDialog<ReturnPageT> fillRepeatPasswordField(String newPassword) {
        repeatPasswordField.fill(newPassword);

        return this;
    }

    @Step("Press 'Save' button")
    public ReturnPageT clickSaveButton() {
        saveButton.click();

        return getReturnPage();
    }

}
