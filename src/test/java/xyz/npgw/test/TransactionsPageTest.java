package xyz.npgw.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.TransactionsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TransactionsPageTest extends BaseTest {

    @BeforeMethod
    public void beforeMethod() {
        new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .uncheckRememberMeCheckbox()
                .clickLoginButton()
                .getHeader()
                .clickTransactions();
    }

    @Test
    @Epic("Transactions page")
    @Description("User navigate to 'Transactions page' after clicking on 'Transactions' menu on the header")
    public void testNavigateToTransactionsPage() {
        TransactionsPage transaction = new TransactionsPage(getPage());
        assertThat(transaction.getPage()).hasURL(Constants.TRANSACTIONS_PAGE_URL);
        assertThat(transaction.getPage()).hasTitle(Constants.TRANSACTIONS_URL_TITLE);
    }
}
