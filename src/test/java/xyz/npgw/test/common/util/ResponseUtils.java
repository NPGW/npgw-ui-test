package xyz.npgw.test.common.util;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class ResponseUtils {

    public static void clickAndWaitForResponse(Page page, Locator locator, String endpoint) {
        page.waitForResponse(
                r -> r.url().contains(endpoint),
                new Page.WaitForResponseOptions().setTimeout(15000),
                locator::click
        );
    }
}
