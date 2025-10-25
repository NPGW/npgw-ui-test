package xyz.npgw.test.common.entity.control;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

@Getter
public enum ControlCode {

    @SerializedName("MaxmindScore")
    MAXMIND_SCORE("MaxmindScore"),
    @SerializedName("MaxmindInsights")
    MAXMIND_INSIGHTS("MaxmindInsights"),
    @SerializedName("Neutrino")
    NEUTRINO("Neutrino");

    private final String name;

    ControlCode(String name) {
        this.name = name;
    }
}
