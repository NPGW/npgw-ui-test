package xyz.npgw.test.common.util;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ResponseUtils {

    public static void clickAndWaitForResponse(Page page, Locator locator, String endpoint) {
        page.waitForResponse(
                response -> response.url().contains(endpoint),
                new Page.WaitForResponseOptions().setTimeout(11111),
                locator::click
        );
        page.waitForTimeout(1000);
    }

    public static void clickAndWaitForText(Page page, Locator locator, String text) {
        locator.click();
        page.getByText(text, new Page.GetByTextOptions().setExact(true))
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(6000));
    }

    public static void waitUntilInputReady(Locator input, int timeoutMillis) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < timeoutMillis) {
            if (input.isVisible() && input.isEnabled() && input.isEditable()) {
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
                log.info("InterruptedException ignored");
            }
        }
    }
}
