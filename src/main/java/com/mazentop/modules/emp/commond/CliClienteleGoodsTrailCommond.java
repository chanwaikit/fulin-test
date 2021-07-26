package com.mazentop.modules.emp.commond;

import com.mazentop.entity.CliClienteleGoodsTrail;
import com.mazentop.entity.CliClienteleInfo;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author dengy
 * @title: CliClienteleGoodsTrailCommond
 * @description: 顾客轨迹管理
 * @date 2020/03/11 14:31
 */
@Data
public class CliClienteleGoodsTrailCommond extends PageCommond {

    @Criteria(expression = Expression.LIKE, property = CliClienteleGoodsTrail.F_GOODS_NAME, alias = CliClienteleGoodsTrail.TABLE_NAME)
    private String goodsName;

    @Criteria(expression = Expression.EQ, property = CliClienteleGoodsTrail.F_FK_CLIENTELE_ID, alias = CliClienteleGoodsTrail.TABLE_NAME)
    private String clientId;
}
