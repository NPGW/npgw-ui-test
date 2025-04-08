package xyz.npgw.test.common;

public enum StatusOptions {
    ALL("All"),
    ACTIVE("Active"),
    INACTIVE("Inactive");
    private final String value;

    StatusOptions(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
