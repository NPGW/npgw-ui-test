package xyz.npgw.test.page;

import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;
import lombok.Getter;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.entity.CardType;
import xyz.npgw.test.common.entity.Currency;
import xyz.npgw.test.common.entity.Status;
import xyz.npgw.test.common.entity.Transaction;
import xyz.npgw.test.page.base.BasePage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

@Getter
public abstract class TransactionsPage<ReturnPageT extends TransactionsPage<ReturnPageT>>
        extends BasePage {

    private final Locator businessUnitSelector = getByTextExact("Business unit").locator("../../..");
    private final Locator currencySelector = getByLabelExact("Currency");
    private final Locator cardTypeSelector = getByLabelExact("Card type");
    private final Locator cardTypeValue = getByRole(AriaRole.BUTTON, "Card type");

    private final Locator searchTrxIdsButton = getByRole(AriaRole.BUTTON, "Trx IDs");
    private final Locator trxIdAppliedButton =
            locator("button").filter(new Locator.FilterOptions().setHasText("Trx Id"));
    private final Locator trxIdPencil = getByRole(AriaRole.BUTTON, "Trx Id").locator("svg[data-icon='pencil']");
    private final Locator trxIdClearIcon = getByRoleExact(AriaRole.BUTTON, "close chip");

    private final Locator npgwReferenceField = getByLabelExact("NPGW reference");
    private final Locator npgwReferenceFieldClearIcon = getByRole(AriaRole.BUTTON, "clear input").first();

    private final Locator businessUnitReference = getByLabelExact("Business unit reference");
    private final Locator businessUnitReferenceClear = getByRole(AriaRole.BUTTON, "clear input").last();

    private final Locator resetFilterButton = getByTestId("ResetFilterButtonTransactionsPage");
    private final Locator refreshDataButton = locator("[data-icon='arrows-rotate']");
    private final Locator settingsButton = getByTestId("SettingsButtonTransactionsPage");
    private final Locator downloadButton = getByTestId("ExportToFileuttonTransactionsPage");
    private final Locator amountButton = getByRole(AriaRole.BUTTON, "Amount");
    private final Locator amountFromField = getByLabelExact("From").locator("..");
    private final Locator amountToField = getByLabelExact("To").locator("..");
    private final Locator clearAmountFromButton = amountFromField.locator("//button[@aria-label='clear input']");
    private final Locator clearAmountToButton = amountToField.locator("//button[@aria-label='clear input']");
    private final Locator amountFromInputField = amountFromField.locator("//input[@type='text']");
    private final Locator amountToInputField = amountToField.locator("//input[@type='text']");
    private final Locator amountFromIncreaseArrow = amountFromField.locator("//button[@aria-label='Increase From']");
    private final Locator amountFromDecreaseArrow = amountFromField.locator("//button[@aria-label='Decrease From']");
    private final Locator amountToIncreaseArrow = amountToField.locator("//button[@aria-label='Increase To']");
    private final Locator amountToDecreaseArrow = amountToField.locator("//button[@aria-label='Decrease To']");
    private final Locator amountApplyButton = getByRole(AriaRole.BUTTON, "Apply");
    private final Locator amountClearButton = getByTextExact("Clear");
    private final Locator amountAppliedClearButton = getByLabelExact("close chip");
    private final Locator amountErrorMessage = locator("[data-slot='error-message']");
    private final Locator cardTypeOptions = locator("ul[data-slot='listbox']").getByRole(AriaRole.OPTION);
    private final Locator settingsVisibleColumnCheckbox = getByRole(AriaRole.CHECKBOX);
    private final Locator settingsArrowsUpDown = locator("svg[data-icon='arrows-up-down']");
    private final Locator amountEditButton = locator("svg[data-icon='pencil']");
    private final Locator downloadCsvOption = getByRole(AriaRole.MENUITEM, "CSV");
    private final Locator downloadExcelOption = getByRole(AriaRole.MENUITEM, "EXCEL");
    private final Locator downloadPdfOption = getByRole(AriaRole.MENUITEM, "PDF");
    private final Locator dialog = locator("[role='dialog']");
    private final Locator dropdownMenuContent = locator("[data-slot='content'][data-open='true']");

    public TransactionsPage(Page page) {
        super(page);
    }

    protected abstract ReturnPageT getReturnPage();

    public Locator amountApplied(String amount) {
        return getByTextExact(amount);
    }

    @Step("Click Currency Selector")
    public ReturnPageT clickCurrencySelector() {
        currencySelector.click();

        return getReturnPage();
    }

    //TODO - refactor these two to one step currency selection

    @Step("Select currency {value} from dropdown menu")
    public ReturnPageT selectCurrency(String value) {
        Locator option = getByRole(AriaRole.OPTION, value);
        option.waitFor();
        option.click();
        dropdownMenuContent.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));
//        getPage().waitForCondition(() -> LocalTime.now().isAfter(THREAD_LAST_ACTIVITY.get()));

        return getReturnPage();
    }

    @Step("Click 'Refresh Data' button")
    public ReturnPageT clickRefreshDataButton() {
        refreshDataButton.click();

        return getReturnPage();
    }

    @Step("Click 'Amount' button")
    public ReturnPageT clickAmountButton() {
        amountButton.click();

        return getReturnPage();
    }

    @Step("Fill 'From' amount value")
    public ReturnPageT fillAmountFromField(String value) {
        amountFromInputField.click();
        amountFromInputField.clear();
        amountFromInputField.fill(value);
        amountFromField.press("Enter");

        return getReturnPage();
    }

    @Step("Fill 'To' amount value")
    public ReturnPageT fillAmountToField(String value) {
        amountToInputField.click();
        amountToInputField.clear();
        amountToInputField.fill(value);
        amountToField.press("Enter");

        return getReturnPage();
    }

    @Step("Clear 'From' amount input field ")
    public ReturnPageT clickClearAmountFromButton() {
        clearAmountFromButton.click();

        return getReturnPage();
    }

    @Step("Clear 'To' amount input field ")
    public ReturnPageT clickClearAmountToButton() {
        clearAmountToButton.click();

        return getReturnPage();
    }

    @Step("Click 'From' amount increase arrow ")
    public ReturnPageT clickAmountFromIncreaseArrow() {
        amountFromIncreaseArrow.click();

        return getReturnPage();
    }

    @Step("Click 'From' amount decrease arrow ")
    public ReturnPageT clickAmountFromDecreaseArrow() {
        amountFromDecreaseArrow.click();

        return getReturnPage();
    }

    @Step("Click 'To' amount increase arrow ")
    public ReturnPageT clickAmountToIncreaseArrow() {
        amountToIncreaseArrow.click();

        return getReturnPage();
    }

    @Step("Click 'To' amount decrease arrow ")
    public ReturnPageT clickAmountToDecreaseArrow() {
        amountToDecreaseArrow.click();

        return getReturnPage();
    }

    @Step("Click 'Apply' amount button")
    public ReturnPageT clickAmountApplyButton() {
        amountApplyButton.click();
        dropdownMenuContent.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.HIDDEN));

        return getReturnPage();
    }

    @Step("Click 'Clear' amount button")
    public ReturnPageT clickAmountClearButton() {
        amountClearButton.click();

        return getReturnPage();
    }

    @Step("Click clear applied amount button")
    public ReturnPageT clickAmountAppliedClearButton() {
        amountAppliedClearButton.click();

        return getReturnPage();
    }

    @Step("Click 'Card type' selector")
    public ReturnPageT clickCardTypeSelector() {
        cardTypeSelector.click();

        return getReturnPage();
    }

    public List<String> getCardTypeOptions() {
        return cardTypeOptions.all().stream().map(Locator::innerText).toList();
    }

    @Step("Click on the Settings button")
    public ReturnPageT clickSettingsButton() {
        settingsButton.click();
        getByTextExact("Visible columns").waitFor();

        return getReturnPage();
    }


    public Locator getColumns() {
        return getByRole(AriaRole.MENUITEM).getByRole(AriaRole.BUTTON);
    }

    private void uncheckIfSelected(Locator checkbox) {
        if ((boolean) checkbox.evaluate("el => el.checked")) {
            checkbox.dispatchEvent("click");
        }
    }

    private void checkIfNotSelected(Locator checkbox) {
        if (!(boolean) checkbox.evaluate("el => el.checked")) {
            checkbox.dispatchEvent("click");
        }
    }

    @Step("Uncheck all 'Visible columns' in Settings")
    public ReturnPageT uncheckAllCheckboxInSettings() {
        settingsVisibleColumnCheckbox
                .all()
                .forEach(this::uncheckIfSelected);

        return getReturnPage();
    }

    @Step("Uncheck Visible column '{name}' in Settings")
    public ReturnPageT uncheckVisibleColumn(String name) {
        settingsVisibleColumnCheckbox
                .all()
                .stream()
                .filter(l -> name.equals(l.getAttribute("aria-label")))
                .findFirst()
                .ifPresent(this::uncheckIfSelected);

        return getReturnPage();
    }

    @Step("Check all 'Visible columns' in Settings")
    public ReturnPageT checkAllCheckboxInSettings() {
        settingsVisibleColumnCheckbox
                .all()
                .forEach(this::checkIfNotSelected);

        return getReturnPage();
    }

    @Step("Check visible column '{name}' in Settings")
    public ReturnPageT checkVisibleColumn(String name) {
        settingsVisibleColumnCheckbox
                .all()
                .stream()
                .filter(l -> name.equals(l.getAttribute("aria-label")))
                .findFirst()
                .ifPresent(this::checkIfNotSelected);

        return getReturnPage();
    }

    @Step("Click amount 'Edit' button")
    public ReturnPageT clickAmountEditButton() {
        amountEditButton.click();

        return getReturnPage();
    }

    @Step("Click 'Download' button")
    public ReturnPageT clickDownloadButton() {
        downloadButton.click();

        return getReturnPage();
    }

    public boolean isFileAvailableAndNotEmpty(String fileType) {
        Download download = getPage().waitForDownload(
                new Page.WaitForDownloadOptions().setTimeout(ProjectProperties.getDefaultTimeout() * 6),
                () -> getByRole(AriaRole.MENUITEM, fileType).click());

        int length = 0;
        try (InputStream inputStream = download.createReadStream()) {
            length = inputStream.readAllBytes().length;
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
        return length > 0;
    }

    @Step("Click 'Reset filter' button")
    public ReturnPageT clickResetFilterButton() {
        resetFilterButton.click();
//        getPage().waitForCondition(() -> LocalTime.now().isAfter(THREAD_LAST_ACTIVITY.get()));

        return getReturnPage();
    }

    @Step("Click Search 'Trx IDs' button")
    public ReturnPageT clickSearchTrxIdsButton() {
        searchTrxIdsButton.click();

        return getReturnPage();
    }

    @Step("Fill '{value}' into 'NPGW reference' field")
    public ReturnPageT fillNpgwReference(String value) {
        npgwReferenceField.fill(value);
        return getReturnPage();
    }

    @Step("Click 'Trx Id' button")
    public ReturnPageT clickTrxIdAppliedButton() {
        trxIdAppliedButton.click();

        return getReturnPage();
    }

    @Step("Click TrxId Clear Icon")
    public ReturnPageT clickTrxIdClearIcon() {
        trxIdClearIcon.click();

        return getReturnPage();
    }

    @Step("Click TrxId Pencil Icon")
    public ReturnPageT clickTrxIdPencilIcon() {
        trxIdPencil.click();

        return getReturnPage();
    }

    @Step("Click 'Npgw reference' Clear Icon")
    public ReturnPageT clickNpgwReferenceClearIcon() {
        npgwReferenceFieldClearIcon.click();

        return getReturnPage();
    }

    @Step("Select Card type '{value}' from dropdown menu")
    public ReturnPageT selectCardType(String value) {
        cardTypeSelector.click();
        getByRole(AriaRole.OPTION, value).click();

        return getReturnPage();
    }

    public String getRequestData() {
        AtomicReference<String> data = new AtomicReference<>("");
        getPage().waitForResponse(response -> {
            if (response.url().contains("/transaction/status")) {
                data.set(response.request().postData());
                return true;
            }
            return false;
        }, refreshDataButton::click);
        return data.get();
    }

    @Step("Click 'Export table data to file' button")
    public ReturnPageT clickExportTableDataToFileButton() {
        downloadButton.click();

        return getReturnPage();
    }

    @Step("Select 'CSV' option")
    public ReturnPageT selectCsv() {
        downloadCsvOption.click();

        return getReturnPage();
    }

    @Step("Select 'PDF' option")
    public ReturnPageT selectPdf() {
        downloadPdfOption.click();

        return getReturnPage();
    }

    @Step("Select 'Excel' option")
    public ReturnPageT selectExcel() {
        downloadExcelOption.click();

        return getReturnPage();
    }

    @Step("Read and parse CSV from path: {csvFilePath}")
    public List<List<String>> readCsv(Path csvFilePath) throws IOException {
        List<List<String>> rows = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(csvFilePath)) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> cells = Arrays.stream(line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)"))
                        .map(s -> s.replaceAll("^\"|\"$", "").trim())
                        .toList();
                rows.add(cells);
            }
        }

        return rows;
    }

    @Step("Read and parse transactions from PDF text")
    public List<Transaction> readPdf(String text) {
        List<Transaction> transactions = new ArrayList<>();

        String[] lines = text.split("\\R");
        int i = 0;

        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Pattern amountLinePattern = Pattern.compile(
                ".*\\d+\\.\\d{2} (EUR|USD|GBT) (MASTERCARD|VISA) "
                        + "(INITIATED|PENDING|SUCCESS|FAILED|CANCELLED|EXPIRED|PARTIAL_REFUND|REFUNDED)");

        while (i < lines.length && (lines[i].trim().isEmpty() || lines[i].contains("Creation Date (GMT)"))) {
            i++;
        }

        while (i < lines.length) {
            String creationDate = lines[i].trim();
            if (!datePattern.matcher(creationDate).matches()) {
                i++;
                continue;
            }
            i++;

            StringBuilder npgwReferenceBuilder = new StringBuilder();
            while (i < lines.length) {
                String line = lines[i].trim();
                if (line.isEmpty()) {
                    break;
                }
                npgwReferenceBuilder.append(line);
                i++;
                if (npgwReferenceBuilder.length() >= 101) {
                    break;
                }
            }
            String npgwReference = npgwReferenceBuilder.toString().trim();

            StringBuilder businessUnitReferenceBuilder = new StringBuilder();
            String amountLine = null;

            while (i < lines.length) {
                String line = lines[i].trim();
                if (line.isEmpty() || datePattern.matcher(line).matches()) {
                    break;
                }

                if (amountLinePattern.matcher(line).matches()) {
                    String[] parts = line.split("\\s+", 2);
                    businessUnitReferenceBuilder.append(parts[0]);
                    amountLine = parts.length > 1 ? parts[1].trim() : "";
                    i++;
                    break;
                } else {
                    businessUnitReferenceBuilder.append(line);
                    i++;
                    if (businessUnitReferenceBuilder.length() >= 50) {
                        break;
                    }
                }
            }

            String businessUnitReference = businessUnitReferenceBuilder.toString().trim();

            if (amountLine == null && i < lines.length) {
                amountLine = lines[i].trim();
                i++;
            }

            if (amountLine == null || !amountLinePattern.matcher(amountLine).matches()) {
                continue;
            }

            String[] tokens = amountLine.split("\\s+");
            if (tokens.length < 4) {
                continue;
            }

            double amount;
            try {
                amount = Double.parseDouble(tokens[0]);
            } catch (NumberFormatException e) {
                continue;
            }

            try {
                Currency currency = Currency.valueOf(tokens[1]);
                CardType cardType = CardType.valueOf(tokens[2]);
                Status status = Status.valueOf(tokens[3]);

                Transaction transaction = new Transaction(
                        creationDate,
                        npgwReference,
                        businessUnitReference,
                        amount,
                        currency,
                        cardType,
                        status
                );

                transactions.add(transaction);
            } catch (IllegalArgumentException e) {
                // intentionally ignored - invalid enum value
            }
        }

        return transactions;
    }

    @Step("Read and parse transactions from Excel file: {filePath}")
    public List<Transaction> readExcel(String filePath) throws IOException {
        List<Transaction> transactionList = new ArrayList<>();
        DataFormatter formatter = new DataFormatter();

        try (
                FileInputStream fis = new FileInputStream(filePath);
                Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }
                String creationDate = formatter.formatCellValue(row.getCell(0));
                String npqwReference = formatter.formatCellValue(row.getCell(1));
                String businessUnit = formatter.formatCellValue(row.getCell(2));
                String amountStr = formatter.formatCellValue(row.getCell(3));
                double amount = Double.parseDouble(amountStr);
                String currency = formatter.formatCellValue(row.getCell(4));
                String cardType = formatter.formatCellValue(row.getCell(5));
                String status = formatter.formatCellValue(row.getCell(6));

                transactionList.add(
                        new Transaction(
                                creationDate,
                                npqwReference,
                                businessUnit,
                                amount,
                                Currency.valueOf(currency),
                                CardType.valueOf(cardType),
                                Status.valueOf(status)
                        ));
            }
        }

        return transactionList;
    }

    public ReturnPageT dragArrows(String from, String to) {
        dragAndDrop(getArrowsUpDown(from), getSettingsVisibleColumnCheckbox(to));

        return getReturnPage();
    }

    public ReturnPageT dragArrowsToFirstPosition(String from) {
        dragAndDrop(getArrowsUpDown(from), settingsVisibleColumnCheckbox.first());

        return getReturnPage();
    }

    public ReturnPageT dragArrowsToLastPosition(String from) {
        dragAndDrop(getArrowsUpDown(from), settingsVisibleColumnCheckbox.last());

        return getReturnPage();
    }

    private void dragAndDrop(Locator source, Locator target) {
        source.dragTo(target);
    }

    private Locator getArrowsUpDown(String name) {
        return getByRole(AriaRole.BUTTON, name).locator(settingsArrowsUpDown);
    }

    private Locator getSettingsVisibleColumnCheckbox(String name) {
        return getByRole(AriaRole.BUTTON, name).locator(settingsVisibleColumnCheckbox);
    }
}
