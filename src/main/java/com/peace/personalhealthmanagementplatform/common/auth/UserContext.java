package com.peace.personalhealthmanagementplatform.common.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

public class UserContext {

    private static final ThreadLocal<UserContextInfo> CONTEXT = new ThreadLocal<>();

    public static void set(UserContextInfo info) {
        CONTEXT.set(info);
    }

    public static UserContextInfo get() {
        return CONTEXT.get();
    }

    public static Long getUserId() {
        UserContextInfo info = get();
        return info == null ? null : info.getUserId();
    }

    public static String getUsername() {
        UserContextInfo info = get();
        return info == null ? null : info.getUsername();
    }

    public static String getRole() {
        UserContextInfo info = get();
        return info == null ? null : info.getRole();
    }

    public static void clear() {
        CONTEXT.remove();
    }

    @Data
    @AllArgsConstructor
    public static class UserContextInfo {
        private Long userId;
        private String username;
        private String role;
    }
}
