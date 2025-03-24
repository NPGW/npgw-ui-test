package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePage;

public final class LoginPage extends BasePage {

    private final Locator emailField = placeholder("Enter your email");
    private final Locator passwordField = placeholder("Enter your password");
    private final Locator loginButton = button("Login");
    private final Locator rememberMeCheckbox = checkbox("Remember me");

    public LoginPage(Page page) {
        super(page);
    }

    @Step("Enter the user's email in the 'Email' field")
    public LoginPage fillEmailField(String userEmail) {
        emailField.fill(userEmail);
        return this;
    }

    public Locator getEmailField() {
        return emailField;
    }

    @Step("Enter the user's password in the 'Password' field")
    public LoginPage fillPasswordField(String userPassword) {
        passwordField.fill(userPassword);
        return this;
    }

    @Step("Press 'Login' button")
    public DashboardPage clickLoginButton() {
        loginButton.click();
        return new DashboardPage(getPage());
    }

    @Step("Check 'Remember me' checkbox")
    public LoginPage checkRememberMeCheckbox() {
        rememberMeCheckbox.setChecked(true);
        return this;
    }

    @Step("Uncheck 'Remember me' checkbox")
    public LoginPage uncheckRememberMeCheckbox() {
        rememberMeCheckbox.setChecked(false);
        return this;
    }
}
