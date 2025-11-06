package xyz.npgw.test.common.util;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.client.TransactionResponse;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.acquirer.Acquirer;
import xyz.npgw.test.common.entity.company.Company;
import xyz.npgw.test.common.entity.control.Control;
import xyz.npgw.test.common.entity.info.Info;
import xyz.npgw.test.common.entity.transaction.CardType;
import xyz.npgw.test.common.entity.transaction.Status;
import xyz.npgw.test.common.entity.transaction.Transaction;
import xyz.npgw.test.common.entity.transaction.Type;
import xyz.npgw.test.common.entity.user.User;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Log4j2
public final class TestUtils {

    public static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }

    public static String now() {
        return DateTimeFormatter.ofPattern("MMdd.HHmmss").format(ZonedDateTime.now(ZoneOffset.UTC));
    }

    public static boolean isOneHourOld(String date) {
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(date.substring(0, 11) + ".2025",
                DateTimeFormatter.ofPattern("MMdd.HHmmss.yyyy").withZone(ZoneOffset.UTC));
        return zonedDateTime.isBefore(ZonedDateTime.now(ZoneOffset.UTC).minusHours(1));
    }

    public static String lastBuildDate(APIRequestContext request) {
        String lastBuildDate = Info.getVersion(request).replaceAll("^.*\\.|-.*$", "");

        return LocalDateTime.parse(lastBuildDate, DateTimeFormatter.ofPattern("yyMMddHHmm"))
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public static String getCurrentMonthRange() {
        YearMonth yearMonth = YearMonth.from(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return yearMonth.atDay(1).format(formatter) + "-" + yearMonth.atEndOfMonth().format(formatter);
    }

    @SneakyThrows
    public static void waitForUserPresence(APIRequestContext request, String email, String companyName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (User.getAll(request, companyName).stream()
                .noneMatch(user -> user.getEmail().equals(email))) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for user '%s' presence".formatted(email));
            }
        }
        double waitTime = ProjectProperties.getDefaultTimeout() - timeout;
        if (waitTime > 0) {
            log.info("User presence wait took {}ms", waitTime);
        }
    }

    @SneakyThrows
    public static void waitForAcquirerPresence(APIRequestContext request, String name) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Acquirer.getAllAcquirers(request).stream()
                .noneMatch(acquirer -> acquirer.getAcquirerName().equals(name))) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for acquirer '%s' presence".formatted(name));
            }
        }
        double waitTime = ProjectProperties.getDefaultTimeout() - timeout;
        if (waitTime > 0) {
            log.info("Acquirer presence wait took {}ms", waitTime);
        }
    }

    @SneakyThrows
    public static void waitForCompanyPresence(APIRequestContext request, String companyName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Company.getAll(request).stream()
                .noneMatch(item -> item.companyName().equals(companyName))) {
            TimeUnit.MILLISECONDS.sleep(500);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for company '%s' present".formatted(companyName));
            }
        }
        double waitTime = ProjectProperties.getDefaultTimeout() - timeout;
        if (waitTime > 0) {
            log.info("Company presence wait took {}ms", waitTime);
        }
    }

    @SneakyThrows
    public static void waitForFraudControlPresence(APIRequestContext request, String fraudControlName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Control.getAll(request).stream()
                .noneMatch(item -> item.getControlName().equals(fraudControlName))) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for Fraud Control '%s' present".formatted(fraudControlName));
            }
        }
        double waitTime = ProjectProperties.getDefaultTimeout() - timeout;
        if (waitTime > 0) {
            log.info("Fraud Control presence wait took {}ms", waitTime);
        }
    }

    public static Transaction mapToTransaction(List<String> cells) {
        return new Transaction(
                cells.get(0),
                Type.valueOf(cells.get(1)),
                cells.get(2),
                cells.get(3),
                Double.parseDouble(cells.get(4).replaceAll(",", "")),
                Currency.valueOf(cells.get(5)),
                CardType.valueOf(cells.get(6)),
                Status.valueOf(cells.get(7))
        );
    }

    public static void pay(Playwright playwright, TransactionResponse transactionResponse) {
        log.info("pay for {}", transactionResponse.paymentUrl());

        Browser browser = BrowserUtils.createBrowser(playwright);
        Page page = browser.newPage();
        PlaywrightAssertions.setDefaultAssertionTimeout(28_000);

        page.navigate(transactionResponse.paymentUrl());
        log.info("Redirected to: {}", page.title());

        assertThat(page).hasURL(transactionResponse.redirectUrlSuccess());

        browser.close();

        PlaywrightAssertions.setDefaultAssertionTimeout(5_000);
    }
}
