package xyz.npgw.test.page.dialog.control;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.common.trait.AlertTrait;

public class AddFraudControlDialog extends FraudControlDialog<AddFraudControlDialog> implements
        AlertTrait<AddFraudControlDialog> {

    public AddFraudControlDialog(Page page) {
        super(page);
    }
}
