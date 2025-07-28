package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import xyz.npgw.test.page.system.TeamPage;

public final class AdminAddUserDialog<ReturnPageT extends TeamPage<ReturnPageT>>
        extends UserDialog<ReturnPageT, AdminAddUserDialog<ReturnPageT>> {

    private final Locator createButton = getByRole(AriaRole.BUTTON, "Create");
    private final ReturnPageT returnPageT;

    public AdminAddUserDialog(Page page, ReturnPageT returnPageT) {
        super(page);
        this.returnPageT = returnPageT;
    }

    @Override
    protected ReturnPageT getReturnPage() {
        return returnPageT;
    }

//    @Step("Enter user email")
//    public AdminAddUserDialog<ReturnPageT> fillEmailField(String email) {
//        getByPlaceholder("Enter user email").fill(email);
//
//        return this;
//    }
//
//    @Step("Enter user password")
//    public AdminAddUserDialog<ReturnPageT> fillPasswordField(
//    @Param(name = "Password", mode = MASKED) String password) {
//        getByPlaceholder("Enter user password").fill(password);
//
//        return this;
//    }
//
//    @Step("Click 'Create' button")
//    public TeamPage<ReturnPageT> clickCreateButton() {
//        createButton.click();
//
//        return getReturnPage();
//    }
//
//    @Step("Click on the 'Create' button and trigger an error")
//    public AdminAddUserDialog<ReturnPageT> clickCreateButtonAndTriggerError() {
//        createButton.click();
//
//        return this;
//    }
}
