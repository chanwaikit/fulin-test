package com.mazentop.modules.skin.dto;

import com.mazentop.modules.skin.block.dto.MenuTree;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/21 07:17
 */
@Data
public class SkinBlockMenuDto implements Serializable {

    private List<MenuTree> menus;

    private String title;

    private String id;
}
