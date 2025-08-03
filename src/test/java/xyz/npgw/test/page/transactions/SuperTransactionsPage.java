package xyz.npgw.test.page.transactions;

import com.microsoft.playwright.Page;
import lombok.Getter;
import xyz.npgw.test.page.common.trait.SelectCompanyTrait;

@Getter
public class SuperTransactionsPage extends BaseTransactionsPage<SuperTransactionsPage> implements
        SelectCompanyTrait<SuperTransactionsPage> {

    public SuperTransactionsPage(Page page) {
        super(page);
    }

}
