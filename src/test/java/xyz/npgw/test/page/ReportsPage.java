package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.common.DateRangePickerTrait;
import xyz.npgw.test.page.common.HeaderPage;
import xyz.npgw.test.page.common.SelectBusinessUnitTrait;
import xyz.npgw.test.page.common.TableTrait;
import xyz.npgw.test.page.dialog.reports.ReportsParametersDialog;

public class ReportsPage extends HeaderPage implements TableTrait, DateRangePickerTrait<ReportsPage>,
        SelectBusinessUnitTrait<ReportsPage> {

    private final Locator refreshDataButton = locator("[data-icon='arrows-rotate']");
    private final Locator generateReportButton = getByRole(AriaRole.BUTTON, "Generate report");

    public ReportsPage(Page page) {
        super(page);
    }

    @Step("Click on the 'Refresh data' button")
    public ReportsPage clickRefreshDataButton() {
        refreshDataButton.click();

        return this;
    }

    @Step("Click on the 'Generation report' button")
    public ReportsParametersDialog clickGenerateReportButton() {
        generateReportButton.click();

        return new ReportsParametersDialog(getPage());
    }
}
