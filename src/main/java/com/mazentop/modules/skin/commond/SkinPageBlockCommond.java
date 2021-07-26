package com.mazentop.modules.skin.commond;

import com.mazentop.entity.SkinPageBlock;
import com.mazentop.modules.web.User;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

import java.util.Objects;

/**
 * @author zhaoqt
 */
@Data
public class SkinPageBlockCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = SkinPageBlock.F_ID, alias = SkinPageBlock.TABLE_NAME)
    private String id;

    @Criteria(expression = Expression.EQ, property = SkinPageBlock.F_IS_ENABLE, alias = SkinPageBlock.TABLE_NAME)
    private Integer isEnable;

    @Criteria(expression = Expression.EQ, property = SkinPageBlock.F_VIEW, alias = SkinPageBlock.TABLE_NAME)
    private String view;

    @Criteria(expression = Expression.EQ, property = SkinPageBlock.F_HANDLE, alias = SkinPageBlock.TABLE_NAME)
    private String handle;

    @Criteria(expression = Expression.EQ, property = SkinPageBlock.F_PID, alias = SkinPageBlock.TABLE_NAME)
    private String pid;

    @Criteria(expression = Expression.EQ, property = SkinPageBlock.F_TEMPLATE_ID, alias = SkinPageBlock.TABLE_NAME)
    private String templateId;


    public SkinPageBlockCommond() {
        if(!Objects.isNull(User.theme())) {
            setTemplateId(User.theme().getId());
        }
    }
}
