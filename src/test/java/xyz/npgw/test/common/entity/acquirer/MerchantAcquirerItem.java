package xyz.npgw.test.common.entity.acquirer;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.CustomLog;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.company.Merchant;

@CustomLog
public final class MerchantAcquirerItem extends Acquirer {

    int priority;

    MerchantAcquirerItem(String acquirerDisplayName, String acquirerMid, String acquirerCode, String acquirerConfig,
                         Currency[] currencyList, SystemConfig systemConfig, boolean isActive, String acquirerName,
                         int acquirerMcc, int priority) {
        super(acquirerDisplayName, acquirerMid, acquirerCode, acquirerConfig,
                currencyList, systemConfig, isActive, acquirerName, acquirerMcc);
        this.priority = priority;
    }

    public static MerchantAcquirerItem getOneMerchantAcquirerItem(APIRequestContext request, Merchant merchant, int priority) {
        APIResponse response = request.get("portal-v1/merchant-acquirer/%s/%d".formatted(merchant.merchantId(), priority));
        log.response(response, "get merchant acquirer item with priority %d for %s".formatted(priority, merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantAcquirerItem.class);
    }

    public static MerchantAcquirerItem deleteMerchantAcquirerItem(APIRequestContext request, Merchant merchant, int priority) {
        APIResponse response = request.delete("portal-v1/merchant-acquirer/%s/%d".formatted(merchant.merchantId(), priority));
        log.response(response, "get merchant acquirer item with priority %d for %s".formatted(priority, merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantAcquirerItem.class);
    }
}
