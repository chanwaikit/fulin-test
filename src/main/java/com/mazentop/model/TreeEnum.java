package com.mazentop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TreeEnum {

    /**
     * 树结构跟节点枚举
     */
    ROOT("root", "根节点"),

    EVALUATION("evaluation", "根节点");

    private String id;

    private String name;

}
