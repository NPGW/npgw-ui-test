package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.system.AcquirersPage;

public class EditAcquirerDialog extends AcquirerDialog<EditAcquirerDialog> {

    public EditAcquirerDialog(Page page) {
        super(page, new AcquirersPage(page));
    }

}
