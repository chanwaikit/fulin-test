package com.mazentop.modules.web.dto;

import com.mazentop.entity.ProProductColor;
import lombok.Data;

import java.util.List;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/5 16:22
 */
@Data
public class ColorCardDto {

    private List<String> colorCodes;

    private List<ProProductColor> colors;
}
