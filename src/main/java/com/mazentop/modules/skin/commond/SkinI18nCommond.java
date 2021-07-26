package com.mazentop.modules.skin.commond;

import com.mazentop.entity.SkinI18n;
import com.mazentop.entity.SkinI18nMap;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

@Data
public class SkinI18nCommond extends PageCommond {


    @Criteria(expression = Expression.EQ, property = SkinI18n.F_TEMPLATE_ID, alias = SkinI18n.TABLE_NAME)
    private String templateId;

    @Criteria(expression = Expression.LIKE, property = SkinI18n.F_LANG_TITLE, alias = SkinI18n.TABLE_NAME)
    private String langTitle;

    @Criteria(expression = Expression.EQ, property = SkinI18nMap.F_I18N_ID, alias = SkinI18nMap.TABLE_NAME)
    private String i18nId;

    @Criteria(expression = Expression.LIKE_R, property = SkinI18nMap.F_I18N_KEY, alias = SkinI18nMap.TABLE_NAME)
    private String i18nKey;

    @Criteria(expression = Expression.LIKE, property = SkinI18nMap.F_I18N_VALUE, alias = SkinI18nMap.TABLE_NAME)
    private String i18nValue;

    @Criteria(expression = Expression.LIKE, property = SkinI18nMap.F_PAGE, alias = SkinI18nMap.TABLE_NAME)
    private String pageTitle;
}
