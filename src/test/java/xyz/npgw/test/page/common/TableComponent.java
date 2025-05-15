package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import lombok.Getter;
import xyz.npgw.test.page.base.BaseComponent;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
public class TableComponent extends BaseComponent {

    private final Locator tableColumnHeader = getPage().getByRole(AriaRole.COLUMNHEADER);
    private final Locator tableHeader = getPage()
            .getByRole(AriaRole.ROW).filter(new Locator.FilterOptions().setHas(tableColumnHeader));

    private final Locator tableRows = getPage()
            .getByRole(AriaRole.ROW).filter(new Locator.FilterOptions().setHasNot(tableColumnHeader));

    public TableComponent(Page page) {
        super(page);
    }

    protected int getColumnHeaderIndexByName(String columnHeaderName) {
        tableColumnHeader.last().waitFor();

        for (int i = 0; i < tableColumnHeader.count(); i++) {
            if (tableColumnHeader.nth(i).innerText().equals(columnHeaderName)) {
                return i;
            }
        }
        throw new NoSuchElementException("Column with header '" + columnHeaderName + "' not found.");
    }

    public Locator getHeaderByName(String name) {

        return tableColumnHeader.getByText(name);
    }

    public List<String> getColumnValues(String columnHeaderName) {
        Locator header = getHeaderByName(columnHeaderName);
        int columnIndex = ((Number) header.evaluate("el => el.cellIndex")).intValue();
        Locator cells = getPage().locator("tr[role='row'] > td:nth-child(" + (columnIndex + 1) + ")");

        return cells.allInnerTexts();
    }

    public Locator getColumnValues(String columnHeaderName, String text) {
        Locator header = getHeaderByName(columnHeaderName);
        int columnIndex = ((Number) header.evaluate("el => el.cellIndex")).intValue();
        return getPage()
                .locator("tr[role='row']")
                .filter(new Locator.FilterOptions().setHasText(text))
                .locator("td:nth-child(" + (columnIndex + 1) + ")");
    }

    public List<String> getColumnHeadersText() {

        return tableColumnHeader.allInnerTexts();
    }

    public Locator getTableRow(String header) {
        Locator rowHeader = getPage().getByRole(AriaRole.ROWHEADER, new Page.GetByRoleOptions().setName(header));

        return getTableRows().filter(new Locator.FilterOptions().setHas(rowHeader));
    }

    public Locator getTableRowByText(String text) {
        return getTableRows().filter(new Locator.FilterOptions().setHasText(text));
    }
}
