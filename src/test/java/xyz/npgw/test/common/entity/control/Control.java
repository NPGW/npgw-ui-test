package xyz.npgw.test.common.entity.control;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.options.RequestOptions;
import lombok.Builder;
import lombok.CustomLog;
import lombok.Getter;
import xyz.npgw.test.common.util.TestUtils;

import java.util.Collections;
import java.util.List;

import static xyz.npgw.test.common.util.TestUtils.encode;

@Getter
@Builder
@CustomLog
public class Control {

    @Builder.Default
    private String controlName = "";
    @Builder.Default
    private String controlDisplayName = "";
    @Builder.Default
    private String controlType = ControlType.BIN_CHECK.getApiValue();
    @Builder.Default
    private String controlCode = ControlCode.NEUTRINO.getName();
    @Builder.Default
    private String controlConfig = "";
    @Builder.Default
    private boolean isActive = true;

    public static class ControlBuilder {

        public ControlBuilder controlName(String controlName) {
            this.controlName$value = controlName;
            this.controlName$set = true;

            if (!this.controlDisplayName$set) {
                controlDisplayName(controlName);
            }
            return this;
        }
    }

    public static void create(APIRequestContext request, Control control) {
        APIResponse response = request.post("portal-v1/control", RequestOptions.create().setData(control));
        log.response(response, "create control %s".formatted(control.controlName));

        TestUtils.waitForFraudControlPresence(request, control.controlName);
    }

    public static void delete(APIRequestContext request, String controlName) {
        APIResponse response = request.delete("portal-v1/control/%s".formatted(encode(controlName)));
        log.response(response, "delete control %s".formatted(controlName));
    }

    public static List<Control> getAll(APIRequestContext request) {
        APIResponse response = request.get("portal-v1/control");
        log.response(response, "get all Fraud Controls");

        if (!response.ok()) {
            return Collections.emptyList();
        }
        return new Gson().fromJson(response.text(), new TypeToken<List<Control>>(){}.getType());
    }

    @Override
    public String toString() {
        return "%s%s%s%s%s%s".formatted(controlType.equals("bin_check") ? "BIN Check" : "Fraud Screen", controlName,
                controlDisplayName, controlCode, controlConfig, isActive ? "Active" : "Inactive");
    }
}
