package xyz.npgw.test.page.dialog.merchant;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.dialog.BaseDialog;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

@Getter
public abstract class BusinessUnitDialog<CurrentDialogT extends BusinessUnitDialog<CurrentDialogT>>
        extends BaseDialog<CompaniesAndBusinessUnitsPage, CurrentDialogT> {

    private final Locator companyNameField = getByLabelExact("Company name");

    public BusinessUnitDialog(Page page, CompaniesAndBusinessUnitsPage returnPage) {
        super(page, returnPage);
    }

}
