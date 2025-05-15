package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import xyz.npgw.test.page.system.TeamPage;

import static io.qameta.allure.model.Parameter.Mode.MASKED;

public class AddUserDialog extends UserDialog<AddUserDialog> {

    private final Locator emailField = getByPlaceholder("Enter user email");
    private final Locator passwordField = getByPlaceholder("Enter user password");
    private final Locator createButton = getByRole(AriaRole.BUTTON, "Create");

    public AddUserDialog(Page page) {
        super(page);
    }

    @Step("Enter user email")
    public AddUserDialog fillEmailField(String email) {
        emailField.fill(email);

        return this;
    }

    @Step("Enter user password")
    public AddUserDialog fillPasswordField(@Param(name = "Password", mode = MASKED) String password) {
        passwordField.fill(password);

        return this;
    }

    @Step("Click 'Create' button")
    public TeamPage clickCreateButton() {
        createButton.click();

        return new TeamPage(getPage());
    }
}
