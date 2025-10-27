package xyz.npgw.test.common.util;

import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import lombok.SneakyThrows;
import xyz.npgw.test.common.client.Transaction;
import xyz.npgw.test.common.client.TransactionResponse;
import xyz.npgw.test.common.entity.company.Merchant;
import xyz.npgw.test.common.entity.transaction.Status;
import xyz.npgw.test.common.entity.transaction.Type;

public class SaleTransactionUtils {

    @SneakyThrows
    public static TransactionResponse createPendingTransaction(APIRequestContext request,
                                                               int amount, Merchant merchant,
                                                               String externalTransactionId) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .externalTransactionId(externalTransactionId)
                .type(Type.SALE)
                .merchantId(merchant.merchantId())
                .build();
        TransactionResponse transactionResponse = Transaction.create(request, transaction);

        return WaitUtils.waitUntil(request, transactionResponse, Status.PENDING);
    }

    @SneakyThrows
    public static TransactionResponse createSuccessTransaction(Playwright playwright, APIRequestContext request,
                                                               int amount, Merchant merchant,
                                                               String externalTransactionId) {
        TransactionResponse transactionResponse = createPendingTransaction(
                request, amount, merchant, externalTransactionId);
        if (transactionResponse.paymentUrl() == null) {
            throw new RuntimeException("create pending SALE transaction returned null paymentUrl");
        }
        TestUtils.pay(playwright, transactionResponse);

        return WaitUtils.waitUntil(request, transactionResponse, Status.SUCCESS);
    }

    //only for settled transactions
    @SneakyThrows
    public static TransactionResponse createFailedFullRefundAttemptTransaction(Playwright playwright, APIRequestContext request,
                                                                               int amount, Merchant merchant,
                                                                               String externalTransactionId) {
        TransactionResponse transactionResponse = createSuccessTransaction(
                playwright, request, amount, merchant, externalTransactionId);
        Transaction.refund(request, transactionResponse, amount);

        return WaitUtils.waitUntil(request, transactionResponse, Status.SUCCESS);
    }

    @SneakyThrows
    public static TransactionResponse createThreeFailedRefundAttemptTransaction(Playwright playwright, APIRequestContext request,
                                                                                int amount, Merchant merchant,
                                                                                String externalTransactionId) {
        TransactionResponse transactionResponse = createSuccessTransaction(
                playwright, request, amount, merchant, externalTransactionId);
        Transaction.refund(request, transactionResponse, 1);
        Transaction.refund(request, transactionResponse, 2);
        Transaction.refund(request, transactionResponse, 3);

        return WaitUtils.waitUntil(request, transactionResponse, Status.SUCCESS);
    }

    //only for settled transactions
    @SneakyThrows
    public static TransactionResponse createOneFailedPartialRefundTransaction(Playwright playwright, APIRequestContext request,
                                                                              int amount, Merchant merchant,
                                                                              String externalTransactionId) {
        TransactionResponse transactionResponse = createSuccessTransaction(
                playwright, request, amount, merchant, externalTransactionId);
        Transaction.refund(request, transactionResponse, amount / 2);

        return WaitUtils.waitUntil(request, transactionResponse, Status.SUCCESS);
    }
}
