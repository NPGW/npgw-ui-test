package xyz.npgw.test.page.dashboard;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.Getter;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;

import java.util.concurrent.atomic.AtomicReference;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public abstract class BaseDashboardPage<CurrentPageT extends BaseDashboardPage<CurrentPageT>>
        extends HeaderPage<CurrentPageT> implements
        SelectDateRangeTrait<CurrentPageT>,
        AlertTrait<CurrentPageT>,
        SelectBusinessUnitTrait<CurrentPageT> {

    private final Locator htmlTag = locator("html");
    @Getter(AccessLevel.NONE)
    private final Locator refreshDataButton = locator("[data-icon='arrows-rotate']");
    private final Locator yAxisLabels = locator(".apexcharts-yaxis-label tspan");
    private final Locator xAxisTexts = locator(".apexcharts-xaxis tspan");
    private final Locator currencyLegendLabels = locator("span.apexcharts-legend-text");
    private final Locator resetFilterButton = getByTestId("ResetFilterButtonDashboardPage");
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

    public BaseDashboardPage(Page page) {
        super(page);
        assertThat(getDashboardButton().locator("..")).hasAttribute("data-active", "true");
    }

    @Step("Click 'Refresh data' button")
    public CurrentPageT clickRefreshDataButton() {
        refreshDataButton.click();

        return self();
    }

    @Step("Click 'Reset filter' button")
    public CurrentPageT clickResetFilterButton() {
        resetFilterButton.click();

        return self();
    }

    @Step("Click Currency Selector")
    public CurrentPageT clickCurrencySelector() {
        currencySelector.click();

        return self();
    }

    @Step("Select currency from dropdown menu")
    public CurrentPageT selectCurrency(String value) {
        getByRole(AriaRole.OPTION, value).click();

        return self();
    }

    @Step("Click 'Amount' button")
    public CurrentPageT clickAmountButton() {
        amountButton.click();

        return self();
    }

    @Step("Click 'Count' button")
    public CurrentPageT clickCountButton() {
        countButton.click();

        return self();
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
