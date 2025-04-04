package xyz.npgw.test.testdata;

public enum StatusOption {
    ALL("All"),
    ACTIVE("Active"),
    INACTIVE("Inactive");
    private final String value;

    StatusOption(String value) {
        this.value = value;
    }

    public String getValue() {

        return value;
    }
}
