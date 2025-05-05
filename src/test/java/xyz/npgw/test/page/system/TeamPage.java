package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.page.common.SelectCompanyTrait;
import xyz.npgw.test.page.common.TableTrait;
import xyz.npgw.test.page.dialog.user.AddUserDialog;
import xyz.npgw.test.page.dialog.user.ChangeUserActivityDialog;
import xyz.npgw.test.page.dialog.user.EditUserDialog;

@Log4j2
public class TeamPage extends BaseSystemPage<TeamPage> implements TableTrait, SelectCompanyTrait<TeamPage> {

    private final Locator applyFilterButton = getByTestId("ApplyFilterButtonTeamPage");
    private final Locator addUserButton = getByTestId("AddUserButtonTeamPage");
    private final Locator selectCompanyInput = placeholder("Search...");

    public TeamPage(Page page) {
        super(page);
    }

    @Step("Click 'Add user' button")
    public AddUserDialog clickAddUserButton() {
        addUserButton.click();

        return new AddUserDialog(getPage());
    }

    @Step("Click 'Edit user'")
    public EditUserDialog clickEditUser(String username) {
        Locator editButton = getPage().getByRole(
                AriaRole.ROW, new Page.GetByRoleOptions().setName(username)).getByTestId("EditUserButton");
        editButton.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        editButton.click();

        return new EditUserDialog(getPage());
    }

    public Locator getUserEmailByUsername(String username) {
        Locator row = getPage()
                .getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(username));

        return row.locator("td").first();
    }

    public Locator getUserRoleByUsername(String username) {
        Locator row = getPage()
                .getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(username));

        return row.locator("td").nth(1);
    }

    public Locator getUserStatusByUsername(String username) {
        return getPage()
                .getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(username))
                .locator("td").nth(2);
    }

    public Locator waitForChangingUserStatusText(String username, String expectedText) {
        Locator statusCell = getUserStatusByUsername(username);

        for (int i = 0; i < 10; i++) {
            String visibleText = statusCell.innerText().trim();
            if (visibleText.equals(expectedText)) {
                return statusCell;
            }
            getPage().waitForTimeout(500);
        }

        throw new AssertionError("Visible status did not change to '%s' within timeout".formatted(expectedText));
    }

    @Step("Click 'Apply filter")
    public TeamPage clickApplyFilter() {
        selectCompanyInput.waitFor();

        applyFilterButton.click();

        return this;
    }

    public Locator getChangeUserActivityButton(String username) {
        return getPage()
                .getByRole(AriaRole.ROW, new Page.GetByRoleOptions().setName(username))
                .getByTestId("ChangeUserActivityButton")
                .locator("svg");
    }

    @Step("Click user activation button")
    public ChangeUserActivityDialog clickChangeUserActivityButton(String username) {
        Locator button = getChangeUserActivityButton(username);
        button.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
        button.click();

        return new ChangeUserActivityDialog(getPage());
    }
}
