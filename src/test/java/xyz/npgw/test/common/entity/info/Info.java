package xyz.npgw.test.common.entity.info;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.CustomLog;

@CustomLog
public record Info(App app) {

    public static String getVersion(APIRequestContext request) {
        APIResponse response = request.get("portal-v1/info");
        log.response(response, "get portal-v1 info");

        return new Gson().fromJson(response.text(), Info.class).app.version;
    }

    private record App(String version, String name, String description) {
    }
}
