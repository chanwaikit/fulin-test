package com.mazentop.modules.emp.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeDto implements Serializable {

    private String id;

    private String pid;

    private String name;

    private String icon;

    private List<TreeDto> children;

    public TreeDto() {}

    public TreeDto(String id, String pid, String name, String icon, List<TreeDto> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.icon = icon;
        this.children = children;
    }

    public TreeDto(String id, String pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    public TreeDto(String id, String pid, String name, List<TreeDto> children) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        this.children = children;
    }
}
