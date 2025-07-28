package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import xyz.npgw.test.page.system.TeamPage;

import static io.qameta.allure.model.Parameter.Mode.MASKED;

public final class AddUserDialog<ReturnPageT extends TeamPage<ReturnPageT>>
        extends UserDialog<ReturnPageT, AddUserDialog<ReturnPageT>> {

    private final Locator createButton = getByRole(AriaRole.BUTTON, "Create");
    private final ReturnPageT returnPageT;

    public AddUserDialog(Page page, ReturnPageT returnPageT) {
        super(page);
        this.returnPageT = returnPageT;
    }

    @Override
    protected ReturnPageT getReturnPage() {
        return returnPageT;
    }

    @Step("Enter user email")
    public AddUserDialog<ReturnPageT> fillEmailField(String email) {
        getByPlaceholder("Enter user email").fill(email);

        return this;
    }

    @Step("Enter user password")
    public AddUserDialog<ReturnPageT> fillPasswordField(@Param(name = "Password", mode = MASKED) String password) {
        getByPlaceholder("Enter user password").fill(password);

        return this;
    }

    @Step("Click 'Create' button")
    public ReturnPageT clickCreateButton() {
        createButton.click();

        return getReturnPage();
    }

    @Step("Click on the 'Create' button and trigger an error")
    public AddUserDialog<ReturnPageT> clickCreateButtonAndTriggerError() {
        createButton.click();

        return this;
    }
}
