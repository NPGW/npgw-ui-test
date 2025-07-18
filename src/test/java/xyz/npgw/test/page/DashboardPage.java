package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.AccessLevel;
import lombok.Getter;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.trait.AlertTrait;
import xyz.npgw.test.page.common.trait.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;
import xyz.npgw.test.page.common.trait.SelectDateRangeTrait;

import java.util.concurrent.atomic.AtomicReference;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@Getter
public final class DashboardPage extends HeaderPage<DashboardPage> implements
        SelectDateRangeTrait<DashboardPage>,
        AlertTrait<DashboardPage>,
        SelectBusinessUnitTrait<DashboardPage>,
        SelectCompanyTrait<DashboardPage> {

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
        assertThat(getDashboardButton().locator("..")).hasAttribute("data-active", "true");
    }

    @Step("Click 'Refresh data' button")
    public DashboardPage clickRefreshDataButton() {
        refreshDataButton.click();

        return this;
    }

    @Step("Click 'Reset filter' button")
    public DashboardPage clickResetFilterButton() {
        resetFilterButton.click();

        return this;
    }

    @Step("Click Currency Selector")
    public DashboardPage clickCurrencySelector() {
        currencySelector.click();

        return this;
    }

    @Step("Select currency from dropdown menu")
    public DashboardPage selectCurrency(String value) {
        getByRole(AriaRole.OPTION, value).click();

        return this;
    }

    @Step("Click 'Amount' button")
    public DashboardPage clickAmountButton() {
        amountButton.click();

        return this;
    }

    @Step("Click 'Count' button")
    public DashboardPage clickCountButton() {
        countButton.click();

        return this;
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
