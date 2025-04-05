package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Param;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.base.BasePage;

import static io.qameta.allure.model.Parameter.Mode.MASKED;

public final class LoginPage extends BasePage {

    @Getter
    private final Locator emailField = placeholder("Enter your email");
    private final Locator passwordField = placeholder("Enter your password");
    private final Locator loginButton = button("Login");
    private final Locator rememberMeCheckbox = checkbox("Remember me");
    @Getter
    private final Locator loginFormTitle = locator(".login-form-container h3");

    public LoginPage(Page page) {
        super(page);
    }

    @Step("Enter the user's email in the 'Email' field")
    public LoginPage fillEmailField(String userEmail) {
        emailField.fill(userEmail);

        return this;
    }

    @Step("Enter the user's password in the 'Password' field")
    public LoginPage fillPasswordField(@Param(name = "Password", mode = MASKED) String userPassword) {
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
