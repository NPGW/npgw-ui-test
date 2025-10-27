package xyz.npgw.test.common.entity.user;

public record Token(String accessToken, int expiresIn, String idToken, String refreshToken, String tokenType) {
}
