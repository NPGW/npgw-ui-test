package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.common.HeaderPage;

public final class DashboardPage extends HeaderPage {

    private final Locator dateRange = getPage().getByRole(AriaRole.SPINBUTTON);
    @Getter
    private final Locator dataRangeErrorMessage = locator("[data-slot='error-message']");

    public DashboardPage(Page page) {
        super(page);
    }

    @Step("Set start date: {startDate}")
    public DashboardPage setStartDate(String startDate) {
        Locator day = dateRange.nth(0);
        Locator month = dateRange.nth(1);
        Locator year = dateRange.nth(2);

        day.fill(startDate.split("-")[0]);
        month.fill(startDate.split("-")[1]);
        year.fill(startDate.split("-")[2]);

        return this;
    }

    @Step("Set end date: {endDate}")
    public DashboardPage setEndDateAndPressTab(String endDate) {
        Locator day = dateRange.nth(3);
        Locator month = dateRange.nth(4);
        Locator year = dateRange.nth(5);

        day.fill(endDate.split("-")[0]);
        month.fill(endDate.split("-")[1]);
        year.fill(endDate.split("-")[2]);
        year.press("Tab");

        return this;
    }
}
