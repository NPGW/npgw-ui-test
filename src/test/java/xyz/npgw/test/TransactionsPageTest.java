package xyz.npgw.test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import org.testng.annotations.Test;
import xyz.npgw.test.common.Constants;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.DashboardPage;
import xyz.npgw.test.page.LoginPage;
import xyz.npgw.test.page.TransactionsPage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TransactionsPageTest extends BaseTest {

    @Test
    @Epic("Transactions page")
    @Description("User navigate to 'Transactions page' after clicking on 'Transactions' menu on the header")
    public void testNavigateToTransactionsPage() {

// this code will be gone
        new LoginPage(getPage())
                .fillEmailField(Constants.USER_EMAIL)
                .fillPasswordField(Constants.USER_PASSWORD)
                .uncheckRememberMeCheckbox()
                .clickLoginButton();
// *********************** И сразу приземляемся на TransactionsPage


        TransactionsPage transaction = new DashboardPage(getPage()).getHeader().clickTransaction();
        assertThat(transaction.getPage()).hasURL(Constants.TRANSACTIONS_PAGE_URL);
        assertThat(transaction.getPage()).hasTitle(Constants.TRANSACTIONS_URL_TITLE);
    }
}
