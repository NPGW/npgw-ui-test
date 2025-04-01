package xyz.npgw.test.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import xyz.npgw.test.page.base.BasePageWithHeader;
import xyz.npgw.test.page.component.ContentBlock;

public class SystemAdministrationPage extends BasePageWithHeader {

    private final ContentBlock table;
    private final Locator teamButton = tab("Team");
    private final Locator companiesAndBusinessUnitsButton = tab("Companies and business units");
    private final Locator acquirersButton = tab("Acquirers");
    private final Locator gatewayButton = tab("Gateway");
    private final Locator fraudControlButton = tab("Fraud control");
    private final Locator transactionManagementButton = tab("Transaction management");

    public SystemAdministrationPage(Page page) {
        super(page);
        table = new ContentBlock(page);
    }

    @Step("Click Acquirers Button")
    public SaAcquirersTab clickAcquirersTabButton() {
        acquirersButton.click();
        getPage().waitForLoadState();

        return new SaAcquirersTab(getPage());
    }
}
