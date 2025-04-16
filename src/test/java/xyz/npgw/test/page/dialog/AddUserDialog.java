package xyz.npgw.test.page.dialog;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.common.UserRole;
import xyz.npgw.test.page.system.TeamPage;

import java.util.Arrays;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static io.qameta.allure.model.Parameter.Mode.MASKED;

@Log4j2
public class AddUserDialog extends BaseDialog {

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

    @Step("Set status 'Active' radiobutton to '{enabled}' state")
    public AddUserDialog setStatusActiveRadiobutton(boolean enabled) {
        getPage().getByRole(
                AriaRole.RADIO,
                new Page.GetByRoleOptions().setExact(true).setName("Active")).setChecked(enabled);

        return this;
    }

    @Step("Check 'Active' status radiobutton")
    public AddUserDialog checkActiveRadiobutton() {
        getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setExact(true).setName("Active")).check();

        return this;
    }

    @Step("Check 'Inactive' status radiobutton")
    public AddUserDialog checkInactiveRadiobutton() {
        getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Inactive")).check();

        return this;
    }

    @Step("Set 'User role' radiobutton checked for '{userRole}'")
    public AddUserDialog setUserRoleRadiobutton(UserRole userRole) {
        getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName(userRole.getName())).check();

        return this;
    }

    @Step("Check 'System admin' user role radiobutton")
    public AddUserDialog checkSystemAdminRadiobutton() {
        getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("System admin")).check();

        return this;
    }

    @Step("Check 'Company admin' user role radiobutton")
    public AddUserDialog checkCompanyAdminRadiobutton() {
        getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Company admin")).check();

        return this;
    }

    @Step("Check 'Company analyst' user role radiobutton")
    public AddUserDialog checkCompanyAnalystRadiobutton() {
        getPage().getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("Company analyst")).check();

        return this;
    }

    @Step("Set 'Allowed business units'")
    public AddUserDialog setAllowedBusinessUnits(String[] businessUnits) {
        log.info("business units array - {}", Arrays.toString(businessUnits));
        Arrays.stream(businessUnits).forEach(
                s -> {
                    log.info("business unit - {}", s);
                    getPage()
                            .getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(s)).last().waitFor();

                    getPage()
                            .getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName(s))
                            .all()
                            .forEach(item -> {
                                log.info("set checked item with inner text - {}", item.innerText());
                                item.setChecked(true);
                                assertThat(item).isChecked();
                            });
                });

        return this;
    }

    @Step("Check 'Create' button")
    public TeamPage clickCreateButton() {
        getPage().waitForTimeout(1000);
        getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Create")).click();

        return new TeamPage(getPage());
    }
}
