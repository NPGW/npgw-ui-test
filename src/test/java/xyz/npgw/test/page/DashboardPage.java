package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.Getter;
import xyz.npgw.test.page.base.BasePage;

import java.util.concurrent.atomic.AtomicReference;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public abstract class DashboardPage<ReturnPageT extends DashboardPage<ReturnPageT>> extends BasePage {

    private final Locator htmlTag = locator("html");
    @Getter(AccessLevel.NONE)
    private final Locator refreshDataButton = locator("[data-icon='arrows-rotate']");
    private final Locator yAxisLabels = locator(".apexcharts-yaxis-label tspan");
    private final Locator xAxisTexts = locator(".apexcharts-xaxis tspan");
    private final Locator currencyLegendLabels = locator("span.apexcharts-legend-text");
    private final Locator resetFilterButton = getByTestId("ResetFilterButtonDashboardPage");
    @Getter
    private final Locator currencySelector = getByRole(AriaRole.BUTTON, "Currency");

    private final Locator initiatedBlock = getByLabelExact("INITIATED").first();
    private final Locator pendingBlock = getByLabelExact("PENDING").first();
    private final Locator successBlock = getByLabelExact("SUCCESS").first();
    private final Locator failedBlock = getByLabelExact("FAILED").first();

    private final Locator paymentLifecycle = getByTextExact("Payment lifecycle overview").locator("../..");
    private final Locator lifecycleInitiatedBlock = paymentLifecycle.getByLabel("INITIATED");
    private final Locator lifecyclePendingBlock = paymentLifecycle.getByLabel("PENDING");
    private final Locator lifecycleSuccessBlock = paymentLifecycle.getByLabel("SUCCESS");
    private final Locator lifecycleFailedBlock = paymentLifecycle.getByLabel("FAILED");

    private final Locator amountButton = getByTextExact("Amount");
    private final Locator countButton = getByTextExact("Count");

    public DashboardPage(Page page) {
        super(page);
        assertThat(getByRole(AriaRole.LINK, "Dashboard").locator("..")).hasAttribute("data-active", "true");
    }

    protected abstract ReturnPageT getReturnPage();

    @Step("Click 'Refresh data' button")
    public ReturnPageT clickRefreshDataButton() {
        refreshDataButton.click();

        return getReturnPage();
    }

    @Step("Click 'Reset filter' button")
    public ReturnPageT clickResetFilterButton() {
        resetFilterButton.click();

        return getReturnPage();
    }

    @Step("Click Currency Selector")
    public ReturnPageT clickCurrencySelector() {
        currencySelector.click();

        return getReturnPage();
    }

    //TODO - refactor these two to one step currency selection

    @Step("Select currency from dropdown menu")
    public ReturnPageT selectCurrency(String value) {
        getByRole(AriaRole.OPTION, value).click();

        return getReturnPage();
    }

    @Step("Click 'Amount' button")
    public ReturnPageT clickAmountButton() {
        amountButton.click();

        return getReturnPage();
    }

    @Step("Click 'Count' button")
    public ReturnPageT clickCountButton() {
        countButton.click();

        return getReturnPage();
    }

    public String getRequestData() {
        AtomicReference<String> data = new AtomicReference<>("");
        getPage().waitForResponse(response -> {
            if (response.url().contains("/transaction/summary")) {
                data.set(response.request().postData());
                return true;
            }
            return false;
        }, refreshDataButton::click);
        return data.get();
    }
}
