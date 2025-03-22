package page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class LoginPage extends BasePage {

    private final Locator emailField;
    private final Locator passwordField;
    private final Locator loginButton;
    private final Locator rememberMeCheckbox;

    public LoginPage(Page page) {
        super(page);
        emailField = placeholder("Enter your email");
        passwordField = placeholder("Enter your password");
        loginButton = button("Login");
        rememberMeCheckbox = checkbox("Remember me");
    }

    public LoginPage fillEmailField(String userEmail) {
        emailField.fill(userEmail);
        return this;
    }

    public Locator getEmailField() {
        return emailField;
    }

    public LoginPage fillPasswordField(String userPassword) {
        passwordField.fill(userPassword);
        return this;
    }

    public DashboardPage clickLoginButton() {
        loginButton.click();
        return new DashboardPage(getPage());
    }

    public LoginPage checkRememberMeCheckbox() {
        rememberMeCheckbox.setChecked(true);
        return this;
    }

    public LoginPage uncheckRememberMeCheckbox() {
        rememberMeCheckbox.setChecked(false);
        return this;
    }
}
