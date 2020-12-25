package com.sflpro.cafemanager.user.domain.enums;

public enum UserRole {
    ROLE_MANAGER(Code.MANAGER),
    ROLE_WAITER(Code.WAITER);

    UserRole(String name) {
    }

    public static class Code {
        public static final String MANAGER = "ROLE_MANAGER";
        public static final String WAITER = "ROLE_WAITER";
    }
}
