package xyz.npgw.test.common.entity.user;

public record TokenResponse(String userChallengeType, Token token, String sessionId) {
}
