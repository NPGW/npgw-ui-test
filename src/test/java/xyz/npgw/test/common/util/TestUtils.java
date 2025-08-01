package xyz.npgw.test.common.util;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.TimeoutError;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.entity.Acquirer;
import xyz.npgw.test.common.entity.BusinessUnit;
import xyz.npgw.test.common.entity.CardType;
import xyz.npgw.test.common.entity.Company;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.FraudControl;
import xyz.npgw.test.common.entity.Info;
import xyz.npgw.test.common.entity.MerchantAcquirer;
import xyz.npgw.test.common.entity.Status;
import xyz.npgw.test.common.entity.Transaction;
import xyz.npgw.test.common.entity.User;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    public static ZonedDateTime lastBuildDate(APIRequestContext request) {
        String lastBuildDate = Info.get(request).app().version().replaceAll("^.*\\.|-.*$", "");
        return ZonedDateTime.parse(lastBuildDate, DateTimeFormatter.ofPattern("yyMMddHHmm").withZone(ZoneOffset.UTC));
    }

    public static BusinessUnit createBusinessUnit(APIRequestContext request, String companyName, String merchantTitle) {
        return BusinessUnit.create(request, companyName, merchantTitle);
    }

    public static void createBusinessUnits(APIRequestContext request, String company, String[] merchants) {
        Arrays.stream(merchants).forEach(merchantTitle -> BusinessUnit.create(request, company, merchantTitle));
    }

    public static void createCompany(APIRequestContext request, String companyName) {
        Company.create(request, companyName);
    }

    public static void deleteCompany(APIRequestContext request, String companyName) {
        if (companyName.equals("super")) {
            return;
        }
        while (Company.delete(request, companyName) == 409) {
            Arrays.stream(User.getAll(request, companyName))
                    .forEach(user -> User.delete(request, user.email()));
            Arrays.stream(BusinessUnit.getAll(request, companyName))
                    .forEach(businessUnit -> {
                        while (BusinessUnit.delete(request, companyName, businessUnit) == 409) {
                            MerchantAcquirer.delete(request, businessUnit.merchantId());
                        }
                    });
        }
    }

    public static void createAcquirer(APIRequestContext request, Acquirer acquirer) {
        Acquirer.create(request, acquirer);
    }

    public static void deleteAcquirer(APIRequestContext request, String acquirerName) {
        Acquirer.delete(request, acquirerName);
    }

    public static void createFraudControl(APIRequestContext request, FraudControl fraudControl) {
        FraudControl.create(request, fraudControl);
    }

    public static void deleteFraudControl(APIRequestContext request, String fraudControlName) {
        FraudControl.delete(request, fraudControlName);
    }

    public static String getCurrentRange() {
        final java.time.LocalDate currentDate = java.time.LocalDate.now();
        final String[] dataNow = currentDate.toString().split("-");
        final int lastDay = currentDate.lengthOfMonth();
        final String monthYear = "/" + dataNow[1] + "/" + dataNow[0];

        return "Date range01" + monthYear + "-" + lastDay + monthYear;
    }

    @SneakyThrows
    public static void waitForUserPresence(APIRequestContext request, String email, String companyName) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (Arrays.stream(User.getAll(request, companyName)).noneMatch(user -> user.email().equals(email))) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Waiting for user '%s' presence".formatted(email));
            }
        }
        log.info("User presence wait took {}ms", ProjectProperties.getDefaultTimeout() - timeout);
    }

    public static Transaction mapToTransaction(List<String> cells) {
        return new Transaction(
                cells.get(0),
                cells.get(1),
                cells.get(2),
                Double.parseDouble(cells.get(3)),
                Currency.valueOf(cells.get(4)),
                CardType.valueOf(cells.get(5)),
                Status.valueOf(cells.get(6))
        );
    }
}
