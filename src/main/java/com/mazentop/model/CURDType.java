package com.mazentop.model;

import java.util.Objects;

/**
 * @author zhaoqt
 * @title: CURDType
 * @description: 数据库持久化操作类型
 * @date 2019/4/1617:42
 */
public enum CURDType {

    /**
     * 创建 create
     */
    INSERT("insert"),

    /**
     * 修改
     */
    UPDATE("update"),

    /**
     * 读取
     */
    READ("read"),

    /**
     * 删除
     */
    DELETE("delete");


    private String value;

    CURDType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isMe(String value) {
        return Objects.equals(this.value, value);
    }
}
