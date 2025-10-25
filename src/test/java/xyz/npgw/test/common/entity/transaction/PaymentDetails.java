package xyz.npgw.test.common.entity.transaction;

public record PaymentDetails(
        String paymentMethod,
        String pan,
        String expMonth,
        String expYear,
        String cvv,
        String cardHolderName,
        CardType cardType) {

    public PaymentDetails(CardType cardType) {
        this("", "", "", "", "", "", cardType);
    }
}
