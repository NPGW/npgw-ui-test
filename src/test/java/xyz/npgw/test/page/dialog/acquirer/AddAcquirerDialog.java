package xyz.npgw.test.page.dialog.acquirer;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.system.AcquirersPage;
import xyz.npgw.test.page.system.CompaniesAndBusinessUnitsPage;

import java.util.List;

public class AddAcquirerDialog extends AcquirerDialog<AddAcquirerDialog> {

    @Getter
    private final Locator createButton = buttonByName("Create");

    public AddAcquirerDialog(Page page) {
        super(page);
    }

    @Step("Click on the 'Create' button")
    public AcquirersPage clickCreateButton() {
        createButton.click();

        return new AcquirersPage(getPage());
    }


}
