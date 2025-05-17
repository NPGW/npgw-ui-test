package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import xyz.npgw.test.page.base.BaseComponent;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
public abstract class BaseTableComponent extends BaseComponent {

    private final Locator columnHeader = getByRole(AriaRole.COLUMNHEADER);
    private final Locator headersRow = getByRole(AriaRole.ROW).filter(new Locator.FilterOptions().setHas(columnHeader));
    private final Locator rows = getByRole(AriaRole.ROW).filter(new Locator.FilterOptions().setHasNot(columnHeader));

    public BaseTableComponent(Page page) {
        super(page);
    }

    protected int getColumnHeaderIndexByName(String columnHeaderName) {
        columnHeader.last().waitFor();

        for (int i = 0; i < columnHeader.count(); i++) {
            if (columnHeader.nth(i).innerText().equals(columnHeaderName)) {
                return i;
            }
        }
        throw new NoSuchElementException("Column with header '" + columnHeaderName + "' not found.");
    }

    public Locator getHeaderByName(String name) {

        return columnHeader.getByText(name);
    }

    public List<String> getColumnValues(String columnHeaderName) {
        Locator header = getHeaderByName(columnHeaderName);
        int columnIndex = ((Number) header.evaluate("el => el.cellIndex")).intValue();
        Locator cells = getPage().locator("tr[role='row'] > td:nth-child(" + (columnIndex + 1) + ")");

        return cells.allInnerTexts();
    }

    public List<String> getColumnHeadersText() {

        return columnHeader.allInnerTexts();
    }

    public Locator getTableRow(String rowHeader) {
        Locator header = getPage().getByRole(AriaRole.ROWHEADER, new Page.GetByRoleOptions().setName(rowHeader));

        return getRows().filter(new Locator.FilterOptions().setHas(header));
    }

    public Locator getCell(String columnHeader, String rowHeader) {
        Locator header = getHeaderByName(columnHeader);
        int columnIndex = ((Number) header.evaluate("el => el.cellIndex")).intValue();
        return getPage()
                .locator("tr[role='row']")
                .filter(new Locator.FilterOptions().setHasText(rowHeader))
                .locator("td:nth-child(" + (columnIndex + 1) + ")");
    }
}
