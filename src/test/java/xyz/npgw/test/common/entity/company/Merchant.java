package xyz.npgw.test.common.entity.company;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.TimeoutError;
import com.microsoft.playwright.options.RequestOptions;
import lombok.CustomLog;
import lombok.SneakyThrows;
import xyz.npgw.test.common.ProjectProperties;

import java.util.Arrays;
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
            return Arrays.stream(getAll(request, companyName))
                    .filter(m -> m.merchantTitle.equals(merchantTitle))
                    .findFirst()
                    .orElseThrow();
        }

        return new Gson().fromJson(response.text(), Merchant.class);
    }

    public static Merchant[] getAll(APIRequestContext request, String companyName) {
        APIResponse response = request.get("portal-v1/company/%s/merchant".formatted(encode(companyName)));
        log.response(response, "get all merchants for company %s".formatted(companyName));

        if (response.status() == 404) {
            return new Merchant[]{};
        }
        return new Gson().fromJson(response.text(), Merchant[].class);
    }

    public static String getNewApikey(APIRequestContext request, String companyName, Merchant merchant) {
        APIResponse response = request.post("portal-v1/company/%s/merchant/%s".formatted(encode(companyName), merchant.merchantId));
        log.response(response, "get api key for merchant %s of company %s".formatted(merchant.merchantTitle, companyName));

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
