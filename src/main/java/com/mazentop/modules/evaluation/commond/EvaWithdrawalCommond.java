package com.mazentop.modules.evaluation.commond;

import com.mazentop.entity.EvaWithdrawal;
import com.mztframework.commond.PageCommond;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.SearchTime;
import lombok.Data;

/**
 *
 * @author zhoumei
 * @title: EvaWithdrawalCommond
 * @description: 提现
 * @date 2021/1/8
 */
@Data
public class EvaWithdrawalCommond extends PageCommond {

    @Criteria(expression = Expression.EQ, property = EvaWithdrawal.F_STATUS, alias = EvaWithdrawal.TABLE_NAME)
    private Integer status;

    @Criteria(expression = Expression.BETWEEN, property = EvaWithdrawal.F_ADD_TIME, alias = EvaWithdrawal.TABLE_NAME)
    private SearchTime addTime;

    private Long startTime;

    private Long endTime;

    @Criteria(expression = Expression.SQL, sql = " and eva_withdrawal.fk_clientele_id in (select id from cli_clientele_info  where email like:email )")
    private String email;

    @Criteria(expression = Expression.LIKE, property = EvaWithdrawal.F_SN, alias = EvaWithdrawal.TABLE_NAME)
    private String sn;

    @Criteria(expression = Expression.LIKE, property = EvaWithdrawal.F_PAYPAL_ACCOUNT, alias = EvaWithdrawal.TABLE_NAME)
    private String paypalAccount;
}
