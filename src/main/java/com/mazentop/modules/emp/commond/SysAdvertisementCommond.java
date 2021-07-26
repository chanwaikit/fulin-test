package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysAdvertisement;
import com.mazentop.entity.SysHelpCenterContent;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import lombok.Data;

/**
 * @author: wangzy
 * @date: 2020/3/19
 * @description:
 */
@Data
public class SysAdvertisementCommond extends PageCommond{

    @Criteria(expression = Expression.LIKE, property = SysAdvertisement.F_TITLE, alias = SysAdvertisement.TABLE_NAME)
    private String title;


}
