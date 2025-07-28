package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.base.HeaderPage;
import xyz.npgw.test.page.common.trait.AdminBusinessUnitsTableTrait;
import xyz.npgw.test.page.common.trait.AdminHeaderTrait;
import xyz.npgw.test.page.common.trait.AdminMenuTrait;
import xyz.npgw.test.page.common.trait.AlertTrait;

@Getter
public final class AdminBusinessUnitsPage extends HeaderPage<AdminBusinessUnitsPage> implements
        AdminHeaderTrait<AdminBusinessUnitsPage>,
        AdminMenuTrait,
        AdminBusinessUnitsTableTrait,
        AlertTrait<AdminBusinessUnitsPage> {

    private final Locator settings = getByTestId("SettingsButtonMerchantsPage");

    private final Locator pageContent = locator("div.contentBlock");

    private final Locator companyInfoBlock = locator("//div[text()='Company info']/..");
    private final Locator name = getByLabelExact("Name");
    private final Locator type = getByLabelExact("Type");
    private final Locator description = getByLabelExact("Description");
    private final Locator website = getByLabelExact("Website");
    private final Locator primaryContact = getByLabelExact("Primary contact");
    private final Locator email = getByLabelExact("Email");
    private final Locator apiActive = getByLabelExact("API active");
    private final Locator portalActive = getByLabelExact("Portal active");
    private final Locator phone = getByLabelExact("Phone");
    private final Locator mobile = getByLabelExact("Mobile");
    private final Locator fax = getByLabelExact("Fax");
    private final Locator country = getByLabelExact("Country");
    private final Locator state = getByLabelExact("State");
    private final Locator zip = getByLabelExact("ZIP");
    private final Locator city = getByLabelExact("City");

    private final Locator showRadiobutton = locator("[value='show']");
    private final Locator hideRadiobutton = locator("[value='hide']");

    public AdminBusinessUnitsPage(Page page) {
        super(page);
    }

    @Step("Click 'Settings'")
    public AdminBusinessUnitsPage clickSettings() {
//        getPage().waitForCondition(() -> LocalTime.now().isAfter(THREAD_LAST_ACTIVITY.get()));
        settings.click();

        return this;
    }

    @Step("Check 'Show' Company info option")
    public AdminBusinessUnitsPage checkShowCompanyInfo() {
        showRadiobutton.check();

        return this;
    }

    @Step("Check 'Hide' Company info option")
    public AdminBusinessUnitsPage checkHideCompanyInfo() {
        hideRadiobutton.check();

        return this;
    }
}
