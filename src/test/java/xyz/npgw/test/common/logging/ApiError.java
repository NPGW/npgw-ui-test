package xyz.npgw.test.common.logging;

public record ApiError(long timestamp, int status, String message, String path, String code) {
}
