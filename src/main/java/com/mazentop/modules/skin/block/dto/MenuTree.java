package com.mazentop.modules.skin.block.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author zhaoqt
 * @title: MenuTree
 * @description: 菜单树
 * @date 2019/5/214:28
 */
@Data
public class MenuTree implements Serializable {

    public static final String TYPE_CLASSIFICATIONS = "category";

    public static final String TYPE_LINK = "link";

    private static final long serialVersionUID = -4882346453973524370L;

    private String id;

    private String title;

    private String target;

    private String url;

    private List<MenuTree> children = Collections.emptyList();

    private String active;

    private String type;

    private String imgUrl;

    private List<ArrayList> inlineData;

    private String collection;

    private String navTitle;

    public boolean isChild() {
        return !Objects.isNull(children) && !children.isEmpty();
    }
}
