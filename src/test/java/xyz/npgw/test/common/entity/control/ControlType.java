package xyz.npgw.test.common.entity.control;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public enum ControlType {

    @SerializedName("bin_check")
    BIN_CHECK("BIN Check", "bin_check"),
    @SerializedName("fraud_screen")
    FRAUD_SCREEN("Fraud Screen", "fraud_screen");

    private final String displayText;
    private final String apiValue;

    ControlType(String displayText, String apiValue) {
        this.displayText = displayText;
        this.apiValue = apiValue;
    }
}
