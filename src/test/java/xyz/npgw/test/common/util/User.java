package xyz.npgw.test.common.util;

import xyz.npgw.test.common.UserRole;

public record User(
        String companyName,
        boolean enabled,
        UserRole userRole,
        String[] merchantIds,
        String email,
        String password) {
}
