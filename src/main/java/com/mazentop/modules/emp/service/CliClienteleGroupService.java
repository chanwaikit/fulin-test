package com.mazentop.modules.emp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.ConditionEnum;
import com.mazentop.modules.emp.commond.CliClienteleGoodsTrailCommond;
import com.mazentop.modules.emp.commond.CliClienteleGroupCommond;
import com.mazentop.modules.emp.commond.CliClienteleInfoCommond;
import com.mazentop.modules.emp.dto.query.ExportCliClienteleQueryDto;
import com.mazentop.modules.emp.model.ScreenConditionDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 顾客分组
 * @version 1.0
 * @date 2020/11/10 14:57
 */

@Service
public class CliClienteleGroupService {

    @Autowired
    BaseDao baseDao;

    /**
     * 客户分组分页
     * @param commond
     * @return
     */
    public Page cliClienteleGroupPage(CliClienteleGroupCommond commond) {
        commond.setOrderBy(" add_time desc");
        List<CliClienteleGroup> list = CliClienteleGroup.me().find(commond);
        for (CliClienteleGroup group : list) {
            //group.addExten("count", CliClienteleInfoGroup.me().setGroupId(group.getId()).findCount());
            List<ScreenConditionDto> conditionList = JSONArray.parseArray(group.getScreenCondition(), ScreenConditionDto.class);
            if(conditionList.size() == 0 ){
                group.addExten("count","0");
            }else{
                String sql = handleConditionSql(conditionList);
                group.addExten("count",baseDao.queryForLong(sql));
            }
        }
        return new Page(list, commond);
    }


    private String handleConditionSql(List<ScreenConditionDto> conditionList) {
        StringBuilder sql = new StringBuilder(" select count(*) from cli_clientele_info where is_enable = 1 ");
        for (ScreenConditionDto condition : conditionList) {
            if(condition.getExpression().equals(ConditionEnum.BETWEEN.getKey())){
                String[] strs = condition.getValue().split(",");
                // 消费金额
                if (condition.getField().equals("buy_sum")){
                    sql.append(" and (" + condition.getField() + " BETWEEN "+ Helper.transformY2F(new BigDecimal(strs[0])) +" AND "+ Helper.transformY2F(new BigDecimal(strs[1])) +" )");
                    // 访问次数
                }else if(condition.getField().equals("visit_times")){
                    sql.append(" and id in (select re.fk_clientele_id  from sys_visitor_record re where re.fk_clientele_id is not null GROUP BY re.fk_clientele_id HAVING count(*)  BETWEEN "+ strs[0] +" AND "+ strs[1] + ")");
                    // 放弃的支付
                }else if(condition.getField().equals("waive_payment")){
                    sql.append(" and id in (select ord.fk_clientele_id  from ord_sales_order ord where ord.sales_order_status in('06','99') GROUP BY ord.fk_clientele_id HAVING count(*)  BETWEEN "+ strs[0] +" AND "+ strs[1] + ")");
                    // 下单次数
                }else if(condition.getField().equals("order_time")){
                    sql.append(" and id in (select ordd.fk_clientele_id  from ord_sales_order ordd GROUP BY ordd.fk_clientele_id HAVING count(*)  BETWEEN "+ strs[0] +" AND "+ strs[1] + ")");
                    // 最后一次消费时间
                }else if(condition.getField().equals("end_time")){
                    sql.append(" and id in (select ordr.fk_clientele_id  from ord_sales_order ordr where ordr.add_time  BETWEEN "+ strs[0] +" AND "+ strs[1] + ")");
                }else{
                    sql.append(" and (" + condition.getField() + " BETWEEN "+ strs[0] +" AND "+ strs[1] +" )");
                }
            }else if(condition.getExpression().equals(ConditionEnum.EQ.getKey())) {
                if (condition.getField().equals("buy_sum")){
                    sql.append(" and " + condition.getField() + " = " + Helper.transformY2F(new BigDecimal(condition.getValue())) + "");
                }else if(condition.getField().equals("visit_times")){
                    sql.append(" and id in(select re.fk_clientele_id  from sys_visitor_record re where re.fk_clientele_id is not null GROUP BY re.fk_clientele_id HAVING count(*) = " + condition.getValue() + ")");
                }else if(condition.getField().equals("waive_payment")){
                    sql.append(" and id in(select ord.fk_clientele_id  from ord_sales_order ord  where ord.sales_order_status in('06','99') GROUP BY ord.fk_clientele_id HAVING count(*) = " + condition.getValue() + ")");
                }else if(condition.getField().equals("order_time")){
                    sql.append(" and id in(select ordd.fk_clientele_id  from ord_sales_order ordd GROUP BY ordd.fk_clientele_id HAVING count(*) = " + condition.getValue() + ")");
                }else{
                    sql.append(" and " + condition.getField() + " = " + condition.getValue() + "");
                }
            }else if(condition.getExpression().equals(ConditionEnum.UEQ.getKey())){
                if(condition.getField().equals("buy_sum")){
                    sql.append(" and " + condition.getField() + " != " + Helper.transformY2F(new BigDecimal(condition.getValue())) + "");
                }else if(condition.getField().equals("visit_times")){
                    sql.append(" and id in(select re.fk_clientele_id  from sys_visitor_record re where re.fk_clientele_id is not null GROUP BY re.fk_clientele_id HAVING count(*) != " + condition.getValue() + ")");
                }else if(condition.getField().equals("waive_payment")){
                    sql.append(" and id in(select ord.fk_clientele_id  from ord_sales_order ord where ord.sales_order_status in('06','99') GROUP BY ord.fk_clientele_id HAVING count(*) != " + condition.getValue() + ")");
                }else if(condition.getField().equals("order_time")){
                    sql.append(" and id in(select ordd.fk_clientele_id  from ord_sales_order ordd GROUP BY ordd.fk_clientele_id HAVING count(*) != " + condition.getValue() + ")");
                }else{
                    sql.append(" and " + condition.getField() + " != " + condition.getValue() + "");
                }
            }else if(condition.getExpression().equals(ConditionEnum.WITHIN.getKey())){
                // 最后一次消费时间
                if(condition.getField().equals("end_time")){
                    sql.append(" and id in (select ordr.fk_clientele_id from ord_sales_order ordr where DATE_SUB(now(), INTERVAL "+ condition.getValue() + " DAY) <= DATE_FORMAT(from_unixtime(ordr.add_time), '%Y%m%d'))");
                } else {
                    sql.append(" and  DATE_SUB(now(), INTERVAL " + condition.getValue() + " DAY) <= DATE_FORMAT(from_unixtime(" + condition.getField() + "), '%Y%m%d')  ");
                }
            }
        }
        return sql.toString();
    }



    /**
     * 新增或编辑 客户分组
     * @param dto
     * @return
     */
    public R doGroupAddOrUpdate(CliClienteleGroup dto) {
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return R.toast("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(dto)) {
            return R.toast("数据获取失败!");
        }
        if (StringUtils.isEmpty(dto.getId())) {
            dto.setAddTime(Utils.currentTimeSecond());
            dto.setAddUserId(curEmployee.getId());
        }
        dto.insertOrUpdate();
        return R.ok();
    }


    /**
     * 删除客户分组
     * @param groupId
     * @return
     */
    public R deleteGroup(String groupId) {
        if (StringUtils.isEmpty(groupId)) {
            return R.toast("请选择顾客信息!");
        }
        CliClienteleInfoGroup.me().setGroupId(groupId).delete();
        CliClienteleGroup.me().setId(groupId).delete();
        return R.ok();
    }


    /**
     * 查客户分组详情
     * @param groupId - 分组id
     * @return
     */
    public CliClienteleGroup getGroupInfo(String groupId) {
        return CliClienteleGroup.me().setId(groupId).get();
    }


    /**
     * 客户添加到分组
     * @param clientId - 客户id
     * @param groupIds - 分组id集合
     * @return
     */
    public R doClientAddGroup(String clientId, List<String> groupIds) {
        if (StringUtils.isEmpty(clientId)) {
            return R.toast("请选择顾客信息!");
        }
        CliClienteleInfoGroup.me().setClienteleId(clientId).delete();
        for (String groupId : groupIds) {

            CliClienteleInfoGroup.me().setClienteleId(clientId).setGroupId(groupId).insert();

        }
        return R.ok();
    }

    public R doClientAddGroup(String clientId, String groupId) {
        CliClienteleInfo info = CliClienteleInfo.me().setId(clientId).get();

        CliClienteleGroup group=CliClienteleGroup.me().setId(groupId).get();
        if (Objects.isNull(info)||Objects.isNull(group)){
            return R.error("用户 或用户组不存在！请刷新页面");
        }
        CliClienteleInfoGroup.me().setClienteleId(clientId).delete();
        CliClienteleInfoGroup infoGroup=new CliClienteleInfoGroup();
        infoGroup.setClienteleId(info.getId());
        infoGroup.setGroupId(group.getId());
        infoGroup.insert();
        return R.ok();
    }

}
