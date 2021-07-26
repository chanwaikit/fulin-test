package com.mazentop.modules.skin.dto;

import com.mazentop.entity.SkinI18n;
import lombok.Data;

/**
 * @Author: xhl
 * @Date: 2020/10/22 13:59
 */
@Data
public class SkinI18nSaveDto extends SkinI18n {
    private String sourceLanguage;

    private Integer isTranslate;
}
