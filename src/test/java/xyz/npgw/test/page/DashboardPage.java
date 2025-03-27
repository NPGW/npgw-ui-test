package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.AuthenticatedPage;

public final class DashboardPage extends AuthenticatedPage {

    private final Locator logOutButton = button("Log out");

    public DashboardPage(Page page) {
        super(page);
    }

    @Step("Press 'Log out' button")
    public LoginPage clickLogOutButton() {
        logOutButton.click();
        return new LoginPage(getPage());
    }
}
