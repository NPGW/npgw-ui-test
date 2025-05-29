package xyz.npgw.test.page.common.table;

import com.microsoft.playwright.Page;
import xyz.npgw.test.page.TransactionsPage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TransactionsTableComponent extends BaseTableComponent<TransactionsPage> {

    public TransactionsTableComponent(Page page) {
        super(page);
    }

    @Override
    protected TransactionsPage getCurrentPage() {
        return new TransactionsPage(getPage());
    }

    public List<LocalDateTime> getAllDatesFromAllPages() {
        List<LocalDateTime> allDates = new ArrayList<>();

        do {
            allDates.addAll(parseDate());
        } while (goToNextPage());

        return allDates;
    }

    private List<LocalDateTime> parseDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        return getColumnValues("Creation Date").stream()
                .map(date -> LocalDateTime.parse(date.trim(), formatter))
                .toList();
    }
}
