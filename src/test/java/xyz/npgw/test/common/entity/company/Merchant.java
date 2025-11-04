package xyz.npgw.test.common.entity.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.RequestOptions;
import lombok.CustomLog;
import lombok.SneakyThrows;
import xyz.npgw.test.common.ProjectProperties;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static xyz.npgw.test.common.util.TestUtils.encode;

@CustomLog
public record Merchant(
        String merchantId,
        String merchantTitle) {

    public Merchant(String merchantTitle) {
        this(null, merchantTitle);
    }

    public static Merchant create(APIRequestContext request, String companyName, String merchantTitle) {
        APIResponse response = request.post("portal-v1/company/%s/merchant".formatted(encode(companyName)),
                RequestOptions.create().setData(new Merchant(merchantTitle)));
        log.response(response, "create merchant for company %s".formatted(companyName));

        if (response.status() == 422) {
            return getAll(request, companyName).stream()
                    .filter(m -> m.merchantTitle.equals(merchantTitle))
                    .findFirst()
                    .orElseThrow();
        }

        return new Gson().fromJson(response.text(), Merchant.class);
    }

    public static void create(APIRequestContext request, String companyName, String[] merchantTitles) {
        Arrays.stream(merchantTitles).forEach(merchantTitle -> Merchant.create(request, companyName, merchantTitle));
    }

    public static List<Merchant> getAll(APIRequestContext request, String companyName) {
        APIResponse response = request.get("portal-v1/company/%s/merchant".formatted(encode(companyName)));
        log.response(response, "get all merchants for company %s".formatted(companyName));

        if (!response.ok()) {
            return Collections.emptyList();
        }
        return new Gson().fromJson(response.text(), new TypeToken<List<Merchant>>(){}.getType());
    }

    public static String getNewApikey(APIRequestContext request, Merchant merchant) {
        APIResponse response = request.post("merchant-v1/token/secret",
                RequestOptions.create().setQueryParam("merchantId", merchant.merchantId));
        log.response(response, "get api key for merchant %s".formatted(merchant.merchantTitle));

        return response.text();
    }

    @SneakyThrows
    public static void deleteWithTimeout(APIRequestContext request, String companyName, Merchant merchant) {
        double timeout = ProjectProperties.getDefaultTimeout();
        while (204 != delete(request, companyName, merchant)) {
            TimeUnit.MILLISECONDS.sleep(300);
            timeout -= 300;
            if (timeout <= 0) {
                throw new TimeoutError("Delete '%s'".formatted(merchant.merchantTitle));
            }
        }
    }

    public static int delete(APIRequestContext request, String companyName, Merchant merchant) {
        APIResponse response = request.delete(
                "portal-v1/company/%s/merchant/%s".formatted(encode(companyName), merchant.merchantId()));
        log.response(response, "delete merchant %s".formatted(merchant.merchantId()));

        return response.status();
    }
}
