package xyz.npgw.test.page.system;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.Position;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import org.testng.Assert;

public class GatewayPage extends BaseSystemPage {


    @Getter
    private final Locator businessUnitsListHeader = textExact("Business units list");
    @Getter
    private final Locator businessUnitsList = locator("div[data-slot='base'] li");

    @Getter
    private final Locator selectCompanyPlaceholder = locator("input[aria-label='Select company']");

    // selectCompanyClearIcon
    /* Хотела так

    private final Locator selectCompanyContainer = locator("div[data-slot='input-wrapper']")
            .filter(new Locator.FilterOptions()
            .setHas(selectCompanyPlaceholder));
    private final Locator selectCompanyClearIcon = selectCompanyContainer
        .locator("button[aria-label='Show suggestions']:first-child");
    */

    // А так временно сделала - копирует вариант выше просто написано все вместе
    private final Locator selectCompanyClearIcon = locator("div[data-slot='input-wrapper']:has(input[aria-label='Select company']) button[aria-label='Show suggestions']:first-child").locator("..");

    @Getter
    private final Locator companyDropdown = locator("div[data-slot='content']");
    private final Locator lastOptionCompanyDropdown = companyDropdown.locator("li");

    private final Locator currencyLabel = labelExact("Currency");
    @Getter
    private final Locator currencyValue = locator("div[data-slot='innerWrapper'] span");
    private final Locator currencyDropdown = locator("div[data-slot='listbox']");
    @Getter
    private final Locator currencyOptions = option(currencyDropdown);

    public GatewayPage(Page page) {
        super(page);
    }

    @Step("Click Currency value")
    public GatewayPage clickCurrencyValue() {
        currencyLabel.waitFor();
        currencyValue.click();

        return this;
    }

    @Step("Select Currency '{currency}'")
    public GatewayPage selectCurrency(String currency) {
        currencyOptions
                .filter(new Locator.FilterOptions().setHasText(currency))
                .click();
        currencyDropdown.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        return this;
    }

    @Step("Click 'Select company' placeholder")
    public GatewayPage clickSelectCompanyPlaceholder() {
        businessUnitsListHeader.waitFor();
        selectCompanyPlaceholder.click();

        return this;
    }

    @Step("Click company '{company}' in dropdown")
    public GatewayPage clickCompanyInDropdown(String company) {

        boolean found = false;
        String lastOptionBeforeScroll = "";

        while (!found) {
            String currentLastOptionText = lastOptionCompanyDropdown.last().textContent();

            if (lastOptionBeforeScroll.equals(currentLastOptionText) && !lastOptionBeforeScroll.isEmpty()) {
                Assert.fail("Company '" + company + "' not found in dropdown.");
                break;
            }

            lastOptionBeforeScroll = currentLastOptionText;

            Locator matchingOption = lastOptionCompanyDropdown
                    .filter(new Locator.FilterOptions().setHasText(company));

            if (matchingOption.count() > 0 && matchingOption.first().isVisible()) {
                found = true;
                matchingOption.click();
            } else {
                lastOptionCompanyDropdown.last().scrollIntoViewIfNeeded();
                getPage().waitForTimeout(200);
            }
        }

        return this;
    }

    @Step("Click select Company clear icon")
    public GatewayPage clickSelectCompanyClearIcon() {
        getPage().waitForTimeout(2000); //Временно добавлено
        selectCompanyClearIcon.click();
        businessUnitsListHeader.click();

        return this;
    }
}

