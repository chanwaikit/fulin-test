package com.mazentop.modules.web;

import java.util.Objects;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/8/8 17:49
 */
public enum RoleType {
    /**
     * 普通角色
     */
    COMMON("common", "普通");

    private String value;

    private String reason;

    RoleType(String value, String reason) {
        this.value = value;
        this.reason = reason;
    }

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }


    public boolean isMe(String value) {
        return Objects.equals(this.value, value);
    }


    public static RoleType getRoleType(String value) {
        for (RoleType roleType : RoleType.values()) {
            if (roleType.isMe(value)) {
                return roleType;
            }
        }
        return RoleType.COMMON;
    }

}
