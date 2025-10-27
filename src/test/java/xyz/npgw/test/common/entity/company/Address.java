package xyz.npgw.test.common.entity.company;

public record Address(
        String city,
        String state,
        String zip,
        String country,
        String phone,
        String mobile,
        String fax) {

    public Address() {
        this("", "", "", "", "", "", "");
    }
}
