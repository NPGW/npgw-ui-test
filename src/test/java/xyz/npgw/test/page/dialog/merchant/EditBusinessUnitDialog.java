package xyz.npgw.test.page.dialog.merchant;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

public final class EditBusinessUnitDialog extends BusinessUnitDialog<EditBusinessUnitDialog> {

    public EditBusinessUnitDialog(Page page) {
        super(page, new CompaniesAndBusinessUnitsPage(page));
    }
}
