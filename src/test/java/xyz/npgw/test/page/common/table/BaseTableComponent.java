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

    protected int getColumnHeaderIndex(String name) {
        columnHeader.last().waitFor();

        return ((Number) getColumnHeader(name).evaluate("el => el.cellIndex")).intValue();
    }

    public Locator getColumnHeader(String name) {

        return columnHeader.getByText(name);
    }

    public List<String> getColumnValues(String name) {
        int columnIndex = getColumnHeaderIndex(name);
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

        return rows
                .filter(new Locator.FilterOptions().setHasText(rowHeader))
                .locator("td:nth-child(" + (getColumnHeaderIndex(columnHeader) + 1) + ")");
    }
}
