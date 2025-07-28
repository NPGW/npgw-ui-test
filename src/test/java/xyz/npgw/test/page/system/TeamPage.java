package xyz.npgw.test.page.system;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.entity.User;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.dialog.user.AddUserDialog;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Log4j2
public abstract class TeamPage<ReturnPageT extends TeamPage<ReturnPageT>>
        extends HeaderPage<TeamPage<ReturnPageT>> {

    private final Locator settings = getByTestId("SettingsButtonTeamPage");

    public TeamPage(Page page) {
        super(page);
    }

    protected abstract ReturnPageT getReturnPage();

    @Step("Click 'Add user' button")
    public AddUserDialog<ReturnPageT> clickAddUserButton() {
        getByTestId("AddUserButtonTeamPage").click();

        return new AddUserDialog<>(getPage(), getReturnPage());
    }

    @Step("Click 'Refresh data' button")
    public ReturnPageT clickRefreshDataButton() {
        getPage().waitForResponse("**/portal-v1/user/list/*", getByTestId("ApplyFilterButtonTeamPage")::click);

        return getReturnPage();
    }

    @Step("Click 'Reset filter' button")
    public ReturnPageT clickResetFilterButton() {
        getByTestId("ResetFilterButtonTeamPage").click();

        return getReturnPage();
    }

    @SneakyThrows
    public ReturnPageT waitForUserPresence(APIRequestContext request, String email, String companyName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Arrays.stream(User.getAll(request, companyName)).noneMatch(user -> user.email().equals(email))) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for user '%s' presence".formatted(email));
            }
        }
        log.info("User presence wait took {}ms", ProjectProperties.getDefaultTimeout() - timeout);
        clickRefreshDataButton();

        return getReturnPage();
    }

    @SneakyThrows
    public ReturnPageT waitForUserAbsence(APIRequestContext request, String email, String companyName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Arrays.stream(User.getAll(request, companyName)).anyMatch(user -> user.email().equals(email))) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for user '%s' absence".formatted(email));
            }
        }
        log.info("User absence wait took {}ms", ProjectProperties.getDefaultTimeout() - timeout);
        clickRefreshDataButton();

        return getReturnPage();
    }

    @SneakyThrows
    public ReturnPageT waitForUserActivation(APIRequestContext request, String email, String companyName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Arrays.stream(User.getAll(request, companyName))
                .noneMatch(user -> user.email().equals(email) && user.enabled())) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for user '%s' activation".formatted(email));
            }
        }
        log.info("User activation wait took {}ms", ProjectProperties.getDefaultTimeout() - timeout);
        clickRefreshDataButton();

        return getReturnPage();
    }

    @SneakyThrows
    public ReturnPageT waitForUserDeactivation(APIRequestContext request, String email, String companyName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Arrays.stream(User.getAll(request, companyName))
                .noneMatch(user -> user.email().equals(email) && !user.enabled())) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for user '%s' deactivation".formatted(email));
            }
        }
        log.info("User deactivation wait took {}ms", ProjectProperties.getDefaultTimeout() - timeout);
        clickRefreshDataButton();

        return getReturnPage();
    }

    @Step("Click 'Settings'")
    public ReturnPageT clickSettings() {
//        getPage().waitForCondition(() -> LocalTime.now().isAfter(THREAD_LAST_ACTIVITY.get()));
        locator("[data-icon='gear']").click();

        return getReturnPage();
    }
}
