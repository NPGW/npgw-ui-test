package xyz.npgw.test.common.entity.user;

public record Challenge(String sessionId, Credentials data, String userChallengeType) {
}
