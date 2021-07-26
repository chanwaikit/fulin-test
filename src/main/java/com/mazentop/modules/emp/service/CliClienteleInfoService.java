package com.mazentop.modules.emp.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.ConditionEnum;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.CliClienteleGoodsTrailCommond;
import com.mazentop.modules.emp.commond.CliClienteleInfoCommond;
import com.mazentop.modules.emp.commond.CliClienteleReceiverAddressCommond;
import com.mazentop.modules.emp.dto.query.ExportCliClienteleQueryDto;
import com.mazentop.modules.emp.model.ScreenConditionDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Maps;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Expression;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * 顾客管理业务类
 *
 * @author dengy
 * @version 1.0
 * @date 2020/3/11 14:57
 */

@Service
public class CliClienteleInfoService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CliClienteleInfoService cliClienteleInfoService;

    @Autowired
    EmailService emailService;

    public Page cliClienteleInfoList(CliClienteleInfoCommond commond) {
        String sql = " select * from cli_clientele_info where 1 = 1 ";
        commond.setOrderBy(" add_time desc");
        SearchTime searchTime = new SearchTime();
        if (commond.getStartTime() != null) {
            searchTime.setStart(commond.getStartTime());
        }
        if (commond.getEndTime() != null) {
            searchTime.setEnd(commond.getEndTime());
        }
        commond.setAddTime(searchTime);
        // 成功订单数
        SearchTime searchCount = new SearchTime();
        if (commond.getMinCount() != null) {
            searchCount.setStart(commond.getMinCount());
        }
        if (commond.getMaxCount() != null) {
            searchCount.setEnd(commond.getMaxCount());
        }
        commond.setBuyTime(searchCount);
        List<Map<String, Object>> clienteleInfoList = baseDao.paginate(sql, commond);
        List<String> clienteleId = clienteleInfoList.stream().map((Map m) -> (String) m.get("id")).collect(Collectors.toList());
        Map<String, String> userGroup=new HashMap<>();
        if (!clienteleId.isEmpty()){
            String groupSql=" select g.name,cg.clientele_id from cli_clientele_group g, cli_clientele_info_group cg where g.id=cg.group_id and cg.clientele_id in (:clienteleId)";
            List<Map<String, Object>> clienteleGroup = baseDao.queryForList(groupSql, Maps.of("clienteleId", clienteleId));
            userGroup = clienteleGroup.stream().collect(Collectors.toMap((Map m) -> (String) m.get("clientele_id"), (Map m) -> (String) m.get("name")));
        }
        for (Map<String, Object> clienteleInfo : clienteleInfoList) {
            clienteleInfo.put("group","");
            if (userGroup.containsKey(clienteleInfo.get("id"))){
                clienteleInfo.put("group",userGroup.get(clienteleInfo.get("id")));
            }
            clienteleInfo.put("buy_sum",Helper.transformF2Y(new BigDecimal(clienteleInfo.get("buy_sum").toString())));
            // 国家
            if(!Objects.isNull(clienteleInfo.get("country"))){
                SysCountry country = SysCountry.me().setId(clienteleInfo.get("country").toString()).get();
                clienteleInfo.put("countryName","");
                if(!Objects.isNull(country)) {
                   clienteleInfo.put("countryName", country.getNameCn());
               }
            }
        }
        return new Page(clienteleInfoList, commond);
    }


    /**
     * 获取顾客状态数量
     * @return
     */
    public HashMap<String, Object> getClientStatus() {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("allCount", CliClienteleInfo.me().setIsEnable(Status.YES).findCount());
        map.put("blacklistCount", CliClienteleInfo.me().setIsEnable(Status.NO).findCount());
        return map;
    }


    /**
     * 查客户分组的用户
     * @param commond
     * @return
     */
    public Page getInfoPageByGroup(CliClienteleInfoCommond commond) {
        commond.setOrderBy(" add_time desc");
        commond.setIsEnable(Status.YES);
        return this.getClientelePageByScreenCondition(commond);
//        SearchTime searchTime = new SearchTime();
//        if (commond.getStartTime() != null) {
//            searchTime.setStart(commond.getStartTime());
//        }
//        if (commond.getEndTime() != null) {
//            searchTime.setEnd(commond.getEndTime());
//        }
//        commond.setAddTime(searchTime);
//        List<CliClienteleInfo> clienteleInfoList = CliClienteleInfo.me().find(commond);
//        return new Page(clienteleInfoList, commond);
    }


    /**
     * 根据客户分组筛选条件查客户
     * @param commond
     * @return
     */
    public Page getClientelePageByScreenCondition(CliClienteleInfoCommond commond) {
        List<ScreenConditionDto> conditionList = commond.getScreenCondition();
        if(conditionList.size() == 0){
            return new Page(new ArrayList<>(), commond);
        }
        String sql = handleConditionSql(conditionList);
        List<Map<String, Object>> clienteleInfoList = baseDao.paginate(sql,commond);
        return new Page(clienteleInfoList, commond);
    }


    /**
     * 处理筛选sql
     * @param conditionList
     * @return
     */
    private String handleConditionSql(List<ScreenConditionDto> conditionList) {
        StringBuilder sql = new StringBuilder(" select * from cli_clientele_info where is_enable = 1 ");
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
     * 查客户的收货地址
     * @param commond
     * @return
     */
    public Page getReceiverAddressPage(CliClienteleReceiverAddressCommond commond) {
        List<CliClienteleReceiverAddress> receiverAddressList = CliClienteleReceiverAddress.me().find(commond);
        return new Page(receiverAddressList, commond);
    }



    public List<CliClienteleInfo> cliClienteleInfoAllList(CliClienteleInfoCommond cliClienteleInfoCommond) {

        String sql = " select * from cli_clientele_info where 1=1 ";
        cliClienteleInfoCommond.setCompanyId(Subject.group());
        SearchTime searchTime = new SearchTime();

        if (cliClienteleInfoCommond.getStartTime() != null) {
            searchTime.setStart(cliClienteleInfoCommond.getStartTime());
        }

        if (cliClienteleInfoCommond.getEndTime() != null) {
            searchTime.setEnd(cliClienteleInfoCommond.getEndTime());
        }

        cliClienteleInfoCommond.setAddTime(searchTime);
        List<Map<String, Object>> clienteleInfoList = baseDao.paginate(sql, cliClienteleInfoCommond);
        List<CliClienteleInfo> list = new ArrayList<>();

        for (Map<String, Object> stringObjectMap : clienteleInfoList) {
            list.add(JSONObject.parseObject(JSON.toJSONString(stringObjectMap), CliClienteleInfo.class));
        }

        return list;
    }

    /**
     * 获取客户详情
     * @param id
     * @return
     */
    public CliClienteleInfo getCliClienteleInfo(String id) {
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().ignoreSelectFields(CliClienteleInfo.F_PASSWORD).setId(id).get();
        // 返现次数
        cliClienteleInfo.addExten("cashBackNumber",EvaCashBackApply.me().setFkClienteleId(cliClienteleInfo.getId()).setStatus(EvaCashBackApplyStatusEnum.REMITTANCE.getStatus()).findCount());
        EvaUserBill bill = EvaUserBill.me().setFkClienteleId(id).get();
        cliClienteleInfo.addExten("totalCashBack",0);
        // 佣金、总佣金
        cliClienteleInfo.addExten("totalCommission",0);
        cliClienteleInfo.addExten("commission",0);
        cliClienteleInfo.addExten("country","");
        cliClienteleInfo.addExten("referrer","");
        if (Objects.nonNull(bill)){
            // 返现总额
            cliClienteleInfo.addExten("totalCashBack",Helper.transformF2Y(bill.getTotalCashBack()));
            // 佣金、总佣金
            cliClienteleInfo.addExten("totalCommission",Helper.transformF2Y(bill.getTotalCommission()));
            cliClienteleInfo.addExten("commission",Helper.transformF2Y(bill.getCommissionBalance()));
        }
        // 国家
        if (StrUtil.isNotBlank(cliClienteleInfo.getCountry())){
            cliClienteleInfo.addExten("country",SysCountry.me().setId(cliClienteleInfo.getCountry()).get());
        }
        EvaUserRecommendation recommendation = EvaUserRecommendation.me().setUserId(cliClienteleInfo.getId()).get();
        if (Objects.nonNull(recommendation)){
            // 邀请人
            CliClienteleInfo info = CliClienteleInfo.me().ignoreSelectFields(CliClienteleInfo.F_PASSWORD).setId(recommendation.getReferrerId()).get();
            cliClienteleInfo.addExten("referrer",info);
        }
        return cliClienteleInfo;
    }


    /**
     * 新增或修改
     * @param cliClienteleInfo
     * @return
     */
    public R doCliClienteleInfoAddOrUpdate(CliClienteleInfo cliClienteleInfo) {
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return R.toast("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(cliClienteleInfo)) {
            return R.toast("数据获取失败!");
        }
        if (StringUtils.isEmpty(cliClienteleInfo.getId())) {
            cliClienteleInfo.setAddTime(Utils.currentTimeSecond());
            cliClienteleInfo.setAddUserId(curEmployee.getId());
            cliClienteleInfo.setLevel(0);
            cliClienteleInfo.setBuySum(new Long(0));
            cliClienteleInfo.setBuyTime(0);
            cliClienteleInfo.setIsEnable(1);
            cliClienteleInfo.setIsBuyer(0);
            cliClienteleInfo.setIsMoreBuyer(0);
            cliClienteleInfo.setIsSubscriber(0);
            cliClienteleInfo.setBuyGoodsNumber(0);
            cliClienteleInfo.setLoginName(cliClienteleInfo.getEmail());
            if (Helper.isEmpty(cliClienteleInfo.getIconImageUrl())){
                cliClienteleInfo.setIconImageUrl("http://192.168.0.11:9999/404.jpg");
            }
            SysCompany sysCompany = SysCompany.me().setId(Subject.group()).get();
            if(Objects.nonNull(sysCompany)){
                cliClienteleInfo.setCompanyId(sysCompany.getId());
                cliClienteleInfo.setCompanyName(sysCompany.getName());
            }
            cliClienteleInfo.setPassword(passwordEncoder.encode(cliClienteleInfo.getPassword()));
            cliClienteleInfo.setIsFlag(0);
        } else {
            CliClienteleInfo info = CliClienteleInfo.me().setId(cliClienteleInfo.getId()).get();
            if (!info.getPassword().equals(cliClienteleInfo.getPassword())) {
                cliClienteleInfo.setPassword(passwordEncoder.encode(cliClienteleInfo.getPassword()));
            }
        }
        cliClienteleInfo.insertOrUpdate();
        return R.ok();
    }



    public Page cliClienteleGoodsTrailList(CliClienteleGoodsTrailCommond cliClienteleGoodsTrailCommond) {
        List<CliClienteleGoodsTrail> trailList = CliClienteleGoodsTrail.me().find(cliClienteleGoodsTrailCommond);
        return new Page(trailList, cliClienteleGoodsTrailCommond);
    }

    public Result doDeleteCliClienteleInfo(List<String> ids) {
        if (ids.isEmpty()) {
            return Result.toast("请选择顾客信息!");
        }
        Db.tx(() -> {
            for (String id : ids) {
                CliClienteleInfo.me().setId(id).delete();
            }
            return true;
        });
        return Result.success();
    }



    public Result delete(String id) {
        if (id.isEmpty()) {
            return Result.toast("请选择顾客信息!");
        }
        Db.tx(() -> {
            CliClienteleInfo.me().setId(id).delete();
            return true;
        });
        return Result.success();
    }

    public List<CliClienteleInfo> exportCustomers(ExportCliClienteleQueryDto queryDto) {
        String sql = "select * from  cli_clientele_info WHERE 1=1";
        SearchTime searchTime = new SearchTime();
        if (queryDto.getStartTime() != null&&queryDto.getStartTime()>0) {
            searchTime.setStart(queryDto.getStartTime());
        }
        if (queryDto.getEndTime() != null&&queryDto.getEndTime()>0) {
            searchTime.setEnd(queryDto.getEndTime());
        }
        queryDto.setAddTime(searchTime);
        List<CliClienteleInfo> list = baseDao.find(sql,queryDto,CliClienteleInfo.me().newMapper());

        return list;
    }



    public Result doImportCustomers(List<CliClienteleInfo> list,Integer type,List<CliClienteleInfo> updateCliClienteleInfo) {
        SysCompany sysCompany = SysCompany.me().setId("1").get();
        list.forEach(cliClienteleInfo -> {
            cliClienteleInfo.setPassword(passwordEncoder.encode(GetMD5Code(cliClienteleInfo.getEmail() + "#" + cliClienteleInfo.getPassword())));
            cliClienteleInfo.setCompanyId(sysCompany.getId() );
            cliClienteleInfo.setCompanyName(sysCompany.getName());
            cliClienteleInfo.setLevel(BooleanEnum.FALSE.getValue());
            cliClienteleInfo.setBuySum(Long.parseLong("0"));
            cliClienteleInfo.setBuyTime(BooleanEnum.FALSE.getValue());
            cliClienteleInfo.setClientTypeId("1");
            cliClienteleInfo.setClientTypeName("订阅客户");
            cliClienteleInfo.setAddTime(Utils.currentTimeSecond());
            cliClienteleInfo.setIsEnable(BooleanEnum.TRUE.getValue());
            cliClienteleInfo.setIsSubscriber(BooleanEnum.TRUE.getValue());
            cliClienteleInfo.setIsFlag(BooleanEnum.FALSE.getValue());
            cliClienteleInfo.setIsBuyer(BooleanEnum.FALSE.getValue());
            cliClienteleInfo.setIsMoreBuyer(BooleanEnum.FALSE.getValue());
            cliClienteleInfo.setLoginName(cliClienteleInfo.getEmail());
            if (type.equals(BooleanEnum.FALSE.getValue())){
                cliClienteleInfo.setId(null);
                cliClienteleInfo.insert();
            }else {
                if (Helper.isNotEmpty(cliClienteleInfo.getId())){
                    cliClienteleInfo.update();
                }else {
                    cliClienteleInfo.insert();
                }
            }

        });

        updateCliClienteleInfo.forEach(cliClienteleInfo->{
            if (Helper.isNotEmpty(cliClienteleInfo.getPassword())){
                cliClienteleInfo.setPassword(passwordEncoder.encode(GetMD5Code(cliClienteleInfo.getEmail() + "#" + cliClienteleInfo.getPassword())));
            }
            cliClienteleInfo.update();
        });
        return Result.success();
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte) {
        String[] strDigits = {"0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * MD5加密
     *
     * @param strObj
     * @return
     * @throws Exception
     */
    public static String GetMD5Code(String strObj) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return byteToString(md.digest(strObj.getBytes()));
        } catch (Exception e) {

        }
        return null;
    }
}
