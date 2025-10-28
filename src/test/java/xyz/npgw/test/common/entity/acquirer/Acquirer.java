package xyz.npgw.test.common.entity.acquirer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.Builder;
import lombok.CustomLog;
import lombok.Getter;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.util.TestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static xyz.npgw.test.common.util.TestUtils.encode;

@Getter
@Builder
@CustomLog
public class Acquirer {

    @Builder.Default
    private String acquirerDisplayName = "display name";
    @Builder.Default
    private String acquirerMid = "acquirer mid";
    @Builder.Default
    private String acquirerCode = "NGenius";
    @Builder.Default
    private String acquirerConfig = new Gson().newBuilder().disableHtmlEscaping().create().toJson(new AcquirerConfig());
    @Builder.Default
    private Currency[] currencyList = new Currency[]{Currency.USD, Currency.EUR};
    @Builder.Default
    private SystemConfig systemConfig = new SystemConfig();
    @Builder.Default
    private boolean isActive = true;
    @Builder.Default
    private String acquirerName = "acquirer name";
    @Builder.Default
    private int acquirerMcc = 1111;

    public static class AcquirerBuilder {

        public AcquirerBuilder acquirerName(String acquirerName) {
            this.acquirerName$value = acquirerName;
            this.acquirerName$set = true;

            if (!this.acquirerDisplayName$set) {
                acquirerDisplayName(acquirerName);
            }
            return this;
        }
    }

    public String getStatus() {
        return isActive ? "Active" : "Inactive";
    }

    public String getCurrency() {
        return String.join(", ", Arrays.stream(currencyList).map(Enum::name).toList());
    }

    public static List<String> getAcquirerCodes(APIRequestContext request) {
        record Item(List<String> codes) {
        }

        APIResponse response = request.get("portal-v1/acquirer/codes");
        log.response(response, "get acquirer codes");

        return new Gson().fromJson(response.text(), Item.class).codes;
    }

    public static Acquirer getOneAcquirer(APIRequestContext request, String acquirerName) {
        APIResponse response = request.get("portal-v1/acquirer/%s".formatted(encode(acquirerName)));
        log.response(response, "get '%s' acquirer".formatted(acquirerName));

        return new Gson().fromJson(response.text(), Acquirer.class);
    }

    public static void deleteAcquirer(APIRequestContext request, String acquirerName) {
        APIResponse response = request.delete("portal-v1/acquirer/%s".formatted(encode(acquirerName)));
        log.response(response, "delete acquirer %s".formatted(acquirerName));
    }

    public static void createAcquirer(APIRequestContext request, Acquirer acquirer) {
        APIResponse response = request.post("portal-v1/acquirer", RequestOptions.create().setData(acquirer));
        log.response(response, "create acquirer %s".formatted(acquirer.acquirerName));

        TestUtils.waitForAcquirerPresence(request, acquirer.acquirerName);
    }

    public static List<Acquirer> getAllAcquirers(APIRequestContext request) {
        APIResponse response = request.get("portal-v1/acquirer");
        log.response(response, "get all acquirers");

        if (!response.ok()) {
            return Collections.emptyList();
        }
        return new Gson().fromJson(response.text(), new TypeToken<List<Acquirer>>(){}.getType());
    }
}
