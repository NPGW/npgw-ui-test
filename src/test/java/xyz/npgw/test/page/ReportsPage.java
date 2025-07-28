package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.dialog.reports.ReportsParametersDialog;

public abstract class ReportsPage<ReturnPageT extends ReportsPage<ReturnPageT>> extends HeaderPage<ReturnPageT> {

    private final Locator refreshDataButton = locator("[data-icon='arrows-rotate']");
    private final Locator generateReportButton = getByRole(AriaRole.BUTTON, "Generate report");
    private final Locator resetFilterButton = getByTestId("ResetFilterButtonReportsPage");

    public ReportsPage(Page page) {
        super(page);
    }

    protected abstract ReturnPageT getReturnPage();

    @Step("Click 'Refresh data' button")
    public ReturnPageT clickRefreshDataButton() {
        refreshDataButton.click();

        return getReturnPage();
    }

    @Step("Click 'Generation report' button")
    public ReportsParametersDialog clickGenerateReportButton() {
        generateReportButton.click();

        return new ReportsParametersDialog(getPage());
    }

    @Step("Reload Reports page")
    public ReportsPage<ReturnPageT> refreshReports() {
        getPage().reload();

        return this;
    }

    @Step("Click 'Reset filter' button")
    public ReportsPage<ReturnPageT> clickResetFilterButton() {
        resetFilterButton.click();

        return this;
    }
}
