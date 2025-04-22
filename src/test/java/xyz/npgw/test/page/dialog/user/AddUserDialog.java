package xyz.npgw.test.page.dialog.user;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.page.system.TeamPage;

import static io.qameta.allure.model.Parameter.Mode.MASKED;

@Log4j2
public class AddUserDialog extends UserDialog<AddUserDialog> {

    public AddUserDialog(Page page) {
        super(page);
    }

    @Step("Enter user email")
    public AddUserDialog fillEmailField(String email) {
        getPage().getByPlaceholder("Enter user email").fill(email);

        return this;
    }

    @Step("Enter user password")
    public AddUserDialog fillPasswordField(@Param(name = "Password", mode = MASKED) String password) {
        getPage().getByPlaceholder("Enter user password").fill(password);

        return this;
    }


    @Step("Click 'Create' button")
    public TeamPage clickCreateButton() {
        getPage().route("**/*/list/*", route -> {
            log.info("current route {}", route.request().url());
            if (route.request().url().contains("list")) {
                log.info("list on hold for 1000");
                getPage().waitForTimeout(1000);
            }
            log.info("current route fallback");
            route.fallback();
        });

        getPage().waitForResponse(
                response -> {
                    log.info("response for {} - {}", response.request().url(), response.statusText());
                    if (response.request().url().contains("/portal-v1/user/create") && response.ok()) {
                        log.info("create done");
                        return true;
                    }
                    return false;
                },
                getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create"))::click
        );

        return new TeamPage(getPage());
    }
}
