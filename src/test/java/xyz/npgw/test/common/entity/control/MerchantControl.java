package xyz.npgw.test.common.entity.control;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.CustomLog;
import xyz.npgw.test.common.entity.company.Merchant;

import java.util.List;

@CustomLog
public record MerchantControl(
        String merchantId,
        List<MerchantControlItem> controlList) {

    public static MerchantControl getOneMerchantControl(APIRequestContext request, Merchant merchant) {
        APIResponse response = request.get("portal-v1/merchant-control/%s".formatted(merchant.merchantId()));
        log.response(response, "get control list for %s".formatted(merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantControl.class);
    }

    public static void deleteMerchantControl(APIRequestContext request, Merchant merchant) {
        APIResponse response = request.delete("portal-v1/merchant-control/%s".formatted(merchant.merchantId()));
        log.response(response, "delete control for %s".formatted(merchant.merchantTitle()));
    }

    public static MerchantControl updateMerchantControlItem(APIRequestContext request, Merchant merchant,
                                                            int priority, String name, String value) {
        APIResponse response = request.delete("portal-v1/merchant-control/%s/%d".formatted(merchant.merchantId(), priority),
                RequestOptions.create().setQueryParam(name, value));
        log.response(response, "update merchant control item with priority %d for %s".formatted(priority, merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantControl.class);
    }

    public static MerchantControl addMerchantControlItem(APIRequestContext request, Merchant merchant,
                                                         Control control, boolean isActive) {
        record Item(String controlName, String merchantId, boolean isActive) {
        }

        APIResponse response = request.post("portal-v1/merchant-control",
                RequestOptions.create().setData(new Item(control.getControlName(), merchant.merchantId(), isActive)));
        log.response(response, "add '%s' control to '%s'".formatted(control.getControlName(), merchant.merchantTitle()));

        return new Gson().fromJson(response.text(), MerchantControl.class);
    }
}
