package xyz.npgw.test.common.entity.control;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.CustomLog;
import xyz.npgw.test.common.entity.company.Merchant;

@CustomLog
public final class MerchantControlItem extends Control {

    int priority;

    MerchantControlItem(String controlName, String controlDisplayName, String controlType, String controlCode,
                        String controlConfig, boolean isActive, int priority) {
        super(controlName, controlDisplayName, controlType, controlCode, controlConfig, isActive);
        this.priority = priority;
    }

    public static MerchantControlItem getOneMerchantControlItem(APIRequestContext request, Merchant merchant, int priority) {
        APIResponse response = request.get("portal-v1/merchant-control/%s/%d".formatted(merchant.merchantId(), priority));
        log.response(response, "get merchant control item with priority %d for %s".formatted(priority, merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantControlItem.class);
    }

    public static MerchantControlItem deleteMerchantControlItem(APIRequestContext request, Merchant merchant, int priority) {
        APIResponse response = request.delete("portal-v1/merchant-control/%s/%d".formatted(merchant.merchantId(), priority));
        log.response(response, "get merchant control item with priority %d for %s".formatted(priority, merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantControlItem.class);
    }
}
