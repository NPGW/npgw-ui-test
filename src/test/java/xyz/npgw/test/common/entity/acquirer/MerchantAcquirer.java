package xyz.npgw.test.common.entity.acquirer;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.CustomLog;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.company.Merchant;

import java.util.List;

@CustomLog
public record MerchantAcquirer(
        String merchantId,
        List<MerchantAcquirerItem> acquirerList) {

    public static MerchantAcquirer getOneMerchantAcquirer(APIRequestContext request, Merchant merchant) {
        APIResponse response = request.get("portal-v1/merchant-acquirer/%s".formatted(merchant.merchantId()));
        log.response(response, "get acquirer list for %s".formatted(merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantAcquirer.class);
    }

    public static void deleteMerchantAcquirer(APIRequestContext request, Merchant merchant) {
        APIResponse response = request.delete("portal-v1/merchant-acquirer/%s".formatted(merchant.merchantId()));
        log.response(response, "delete merchant acquirer for %s".formatted(merchant.merchantTitle()));
    }

    public static MerchantAcquirer updateMerchantAcquirerItem(APIRequestContext request,
                                                              Merchant merchant, int priority, String name, String value) {
        APIResponse response = request.delete("portal-v1/merchant-acquirer/%s/%d".formatted(merchant.merchantId(), priority),
                RequestOptions.create().setQueryParam(name, value));
        log.response(response, "update merchant acquirer item with priority %d for %s".formatted(priority, merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantAcquirer.class);
    }

    public static MerchantAcquirer addMerchantAcquirerItem(APIRequestContext request, Merchant merchant, Acquirer acquirer,
                                                           Currency[] currencyList, boolean isActive) {
        record Item(String acquirerName, Currency[] currencyList, String merchantId, boolean isActive) {
        }

        APIResponse response = request.post("portal-v1/merchant-acquirer",
                RequestOptions.create().setData(new Item(acquirer.getAcquirerName(), currencyList, merchant.merchantId(), isActive)));
        log.response(response, "add %s acquirer to %s".formatted(acquirer.getAcquirerName(), merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantAcquirer.class);
    }

    public static MerchantAcquirer addMerchantAcquirerItem(APIRequestContext request,
                                                           Merchant merchant, Acquirer acquirer, Currency[] currencyList) {
        return addMerchantAcquirerItem(request, merchant, acquirer, currencyList, true);
    }

    public static MerchantAcquirer addMerchantAcquirerItem(APIRequestContext request, Merchant merchant, Acquirer acquirer) {
        return addMerchantAcquirerItem(request, merchant, acquirer, acquirer.getCurrencyList());
    }
}
