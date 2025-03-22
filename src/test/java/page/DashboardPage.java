package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class DashboardPage extends BasePage {
    private final Locator logOutButton = button("Log out");

    public DashboardPage(Page page) {
        super(page);
    }

    public LoginPage clickLogOutButton() {
        logOutButton.click();
        return new LoginPage(getPage());
    }
}
