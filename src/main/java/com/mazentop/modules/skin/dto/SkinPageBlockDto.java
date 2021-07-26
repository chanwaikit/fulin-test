package com.mazentop.modules.skin.dto;

import com.mazentop.entity.SkinPageBlock;
import lombok.Data;

/**
 * @author zhaoqt
 */
@Data
public class SkinPageBlockDto extends SkinPageBlock {

    private String viewType;

    private String content;

    private ImageDto cover;

}
