package com.mazentop.modules.emp.dto.query;

import com.mazentop.entity.CliClienteleInfo;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

import java.util.List;
@Data
public class ExportCliClienteleQueryDto extends BaseCommond {
    @Criteria(expression = Expression.LIKE, property = CliClienteleInfo.F_LOGIN_NAME, alias = CliClienteleInfo.TABLE_NAME)
    private String loginName;

    @Criteria(expression = Expression.LIKE, property = CliClienteleInfo.F_PHONE, alias = CliClienteleInfo.TABLE_NAME)
    private String phone;

    @Criteria(expression = Expression.LIKE, property = CliClienteleInfo.F_EMAIL, alias = CliClienteleInfo.TABLE_NAME)
    private String email;

    @Criteria(expression = Expression.BETWEEN,property = CliClienteleInfo.F_ADD_TIME,alias = CliClienteleInfo.TABLE_NAME)
    private SearchTime addTime;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_IS_BUYER, alias = CliClienteleInfo.TABLE_NAME)
    private Integer isBuyer;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_IS_MORE_BUYER, alias = CliClienteleInfo.TABLE_NAME)
    private Integer isMoreBuyer;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_IS_SUBSCRIBER, alias = CliClienteleInfo.TABLE_NAME)
    private Integer isSubscriber;

    private Long startTime;

    private Long endTime;

    private String query;

    @Criteria(expression = Expression.EQ, property = CliClienteleInfo.F_COMPANY_ID, alias = CliClienteleInfo.TABLE_NAME)
    private String companyId;

    @Criteria(expression = Expression.IN, property = CliClienteleInfo.F_ID, alias = CliClienteleInfo.TABLE_NAME)
    private List<String> ids;
}
