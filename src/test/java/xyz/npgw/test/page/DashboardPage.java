package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.common.AlertTrait;
import xyz.npgw.test.page.common.DateRangePickerTrait;
import xyz.npgw.test.page.common.HeaderPage;
import xyz.npgw.test.page.common.SelectBusinessUnitTrait;

import java.util.List;

public final class DashboardPage extends HeaderPage implements DateRangePickerTrait<DashboardPage>,
        AlertTrait<DashboardPage>, SelectBusinessUnitTrait<DashboardPage> {

    private final Locator refreshDataButton = locator("[data-icon='arrows-rotate']");
    private final Locator yaxisLabels = locator(".apexcharts-yaxis-label tspan");
    private final Locator legendLabels = locator(".apexcharts-xaxis tspan");
    private final Locator currencyLegendLabels = locator("span.apexcharts-legend-text");

    public DashboardPage(Page page) {
        super(page);
    }

    @Step("Click 'Refresh data' button")
    public DashboardPage clickRefreshDataButton() {
        refreshDataButton.click();

        return this;
    }

    public List<String> getYAxisLabels() {
        yaxisLabels.first().waitFor();

        return yaxisLabels.all().stream().map(Locator::textContent).map(String::trim).toList();
    }

    public List<String> getLegendLabelsText() {
        legendLabels.first().waitFor();

        return legendLabels.all().stream().map(Locator::textContent).map(String::trim).toList();
    }

    public List<String> getCurrencyLegendLabels() {
        return currencyLegendLabels.allTextContents().stream().map(String::trim).toList();
    }
}
