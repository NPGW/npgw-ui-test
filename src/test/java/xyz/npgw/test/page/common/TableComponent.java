package xyz.npgw.test.page.common;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.qameta.allure.Step;
import lombok.Getter;
import xyz.npgw.test.page.base.BaseComponent;

import java.util.List;
import java.util.NoSuchElementException;

@Getter
public class TableComponent extends BaseComponent {

    private final Locator tableHeader = getPage().getByRole(AriaRole.COLUMNHEADER);

    private final Locator tableRows = getPage()
            .getByRole(AriaRole.ROW).filter(new Locator.FilterOptions().setHasNot(tableHeader));

    public TableComponent(Page page) {
        super(page);
    }

    protected int getColumnHeaderIndexByName(String columnHeaderName) {
        tableHeader.last().waitFor();

        for (int i = 0; i < tableHeader.count(); i++) {
            if (tableHeader.nth(i).innerText().equals(columnHeaderName)) {
                return i;
            }
        }
        throw new NoSuchElementException("Column with header '" + columnHeaderName + "' not found.");
    }

    @Step("Get list of values in column '{columnHeaderName}'")
    public List<String> getColumnValues(String columnHeaderName) {
        int columnIndex = getColumnHeaderIndexByName(columnHeaderName);

        return tableRows.all().stream()
                .map(row -> row.getByRole(AriaRole.GRIDCELL)
                        .or(row.getByRole(AriaRole.ROWHEADER))
                        .nth(columnIndex)
                        .textContent())
                .toList();
    }

    public List<String> getColumnHeadersText() {
        return tableHeader.allInnerTexts();
    }

    public Locator getTableRow(String header) {
        Locator rowHeader = getPage().getByRole(AriaRole.ROWHEADER, new Page.GetByRoleOptions().setName(header));

        return getTableRows().filter(new Locator.FilterOptions().setHas(rowHeader));
    }
}
