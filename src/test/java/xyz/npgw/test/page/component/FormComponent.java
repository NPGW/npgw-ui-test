package xyz.npgw.test.page.component;

import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.base.BaseComponent;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

import java.util.List;

public class FormComponent extends BaseComponent {

    @Getter
    private final Locator banner = dialog().getByRole(AriaRole.BANNER);
    private final Locator closeButton = dialog().getByText("Close");
    private final Locator closeIcon = dialog().getByLabel("Close");
    @Getter
    private final Locator requiredFields = dialog().locator("[required]");
    @Getter
    private final Locator allInputFields = dialog().getByRole(AriaRole.TEXTBOX);
    private final Locator fieldsWithPlaceHolder = dialog().locator(
            "input[placeholder], textarea[placeholder], span[data-slot='value']");

    public FormComponent(Page page) {
        super(page);
    }

    @Step("Click 'Close' icon to close form")
    public void clickCloseIcon() {
        closeIcon.click();
    }

    @Step("Click 'Close' button to close form")
    public void clickCloseButton() {
        closeButton.click();
    }

    public List<String> getPlaceholdersOrTextForFields() {
        fieldsWithPlaceHolder.last()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return fieldsWithPlaceHolder.all().stream().map(locator -> {
            String placeholder = locator.getAttribute("placeholder");
            return placeholder != null ? placeholder : locator.innerText();
        }).toList();
    }

    public List<String> getPlaceholdersForInputFields() {
        allInputFields.last()
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        return allInputFields.all().stream()
                .map(locator -> locator.getAttribute("placeholder")).toList();
    }

    @Step("Clear all form input fields")
    public FormComponent clearInputFields() {
        allInputFields.last().waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));

        allInputFields.all().forEach(locator -> {
            locator.clear();
            banner.click();
        });
        return this;
    }
}
