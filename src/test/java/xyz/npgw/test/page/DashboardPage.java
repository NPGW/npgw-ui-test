package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePage;

public final class DashboardPage extends BasePage {
    private final Locator logOutButton = button("Log out");
    private final Header header;

    public DashboardPage(Page page) {
        super(page);
        this.header = new Header(getPage());
    }

    @Step("Press 'Log out' button")
    public LoginPage clickLogOutButton() {
        logOutButton.click();
        return new LoginPage(getPage());
    }

    public Header getHeader() {
        return header;
    }
}
