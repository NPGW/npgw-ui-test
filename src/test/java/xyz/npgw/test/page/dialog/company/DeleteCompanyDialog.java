package xyz.npgw.test.page.dialog.company;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

public class DeleteCompanyDialog extends BaseDialog<CompaniesAndBusinessUnitsPage, DeleteCompanyDialog> {

    public DeleteCompanyDialog(Page page) {
        super(page);
    }

    @Override
    protected CompaniesAndBusinessUnitsPage getReturnPage() {
        return new CompaniesAndBusinessUnitsPage(getPage());
    }

    @Step("Click 'Delete' button")
    public CompaniesAndBusinessUnitsPage clickDeleteButton() {
        getByRole(AriaRole.BUTTON, "Delete").click();

        return getReturnPage();
    }
}
