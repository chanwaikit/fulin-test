package com.mazentop.modules.emp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.model.ActCouponEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.ActCouponCommond;
import com.mazentop.modules.emp.commond.CouponRecordCommond;
import com.mazentop.modules.emp.dto.ActConditionTypeDto;
import com.mazentop.modules.emp.dto.ActCouponActivityDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import com.mztframework.snowflake.IDSnowflake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author: wangzy
 * @date: 2020/3/18
 * @description:
 */
@Service
public class ActCouponService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    ActPromotionActivityService actPromotionActivityService;

    /**
     * 获取优惠券状态数量
     * @return
     */
    public HashMap<String, Object> getCouponActivityStatus() {
        HashMap<String, Object> map = new HashMap<>(4);

        map.put("allCount", ActCouponActivity.me().findCount());
        map.put("notStartedCount", ActCouponActivity.me().setActivityStatus("01").findCount());
        map.put("inCount", ActCouponActivity.me().setActivityStatus("02").findCount());
        map.put("finishedCount", ActCouponActivity.me().setActivityStatus("03").findCount());

        return map;
    }

    public List<ActCouponGetType> findActCouponTypeList() {
        return ActCouponGetType.me().find();
    }

    /**
     * 分页获取优惠券
     * @param couponCommond
     * @return
     */
    public Page<Map<String, Object>> findActCouponList(ActCouponCommond couponCommond) {

        SearchTime searchTime = new SearchTime();
        if (couponCommond.getStartTime() != null) {
            searchTime.setStart(couponCommond.getStartTime());
        }
        if (couponCommond.getEndTime() != null) {
            searchTime.setEnd(couponCommond.getEndTime());
        }

        String sql = " select * from act_coupon_activity where 1=1 ";
        couponCommond.setAddTime(searchTime);
        List<Map<String,Object>> couponList = baseDao.paginate(sql,couponCommond);
        return new Page<>(couponList,couponCommond);

    }


    /**
     * 查优惠券的使用记录
     * @param commond
     * @return
     */
    public Page getCouponRecordList(CouponRecordCommond commond) {
        commond.setOrderBy("add_time desc");
        List<ActGetCouponRecord> list = ActGetCouponRecord.me().find(commond);
        for (ActGetCouponRecord couponRecord: list) {
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(couponRecord.getFkClienteleId()).get();
            if(Objects.nonNull(cliClienteleInfo)){
                couponRecord.addExten("loginName",cliClienteleInfo.getLoginName());
            }
        }
        return new Page<>(list,commond);

    }


    /**
     * 获取优惠券详情
     * @param id
     * @return
     */
    public ActCouponActivity getActCoupon(String id){

        ActCouponActivity actCouponActivity = ActCouponActivity.me().setId(id).get();
        actCouponActivity.addExten("type", ActCouponGetType.me().setId(actCouponActivity.getFkCouponGetTypeId()).get());

        List<ActConditionType> actConditionTypes = ActConditionType.me().setFkActivityId(actCouponActivity.getId()).find();

        // 优惠券规则
        List<ActConditionTypeDto> actConditionTypeDto = new ArrayList<>();
        actConditionTypes.forEach(actConditionType -> {
            ActConditionTypeDto actCondition=new ActConditionTypeDto();
            BeanUtils.copyProperties(actConditionType,actCondition);
            actCondition.setDiscountValueStr(new BigDecimal(actConditionType.getDiscountValue()).divide(new BigDecimal(100)).toString());
            actCondition.setTypeConditionStr(new BigDecimal(actConditionType.getTypeCondition()).divide(new BigDecimal(100)).toString());
            actConditionTypeDto.add(actCondition);
        });
        // 加入优惠券商品
        List<ActPromotionProduct> actPromotionProductList = ActPromotionProduct.me().setFkActivityId(id).find();
        actPromotionActivityService.handleProduct(actPromotionProductList);

        actCouponActivity.addExten("ruleList", actConditionTypeDto);
        actCouponActivity.addExten("actPromotionProductList", actPromotionProductList);

        // 查优惠券领取情况
        Long couponCount = ActGetCouponRecord.me().setFkActivityId(id).findCount();
        actCouponActivity.addExten("couponCount", couponCount);

        // 查优惠券使用情况
        Long useCount = ActGetCouponRecord.me().setFkActivityId(id).setIsUse(Status.YES).findCount();
        actCouponActivity.addExten("useCount", useCount);

        return actCouponActivity;
    }


    /**
     * 给用户派发优惠券
     * @param clienteleList-用户集合
     * @param activityId - 优惠券id
     * @return
     */
    public R doDistributeCoupon(List<String> clienteleList,String activityId){
        ActCouponActivity couponActivity = ActCouponActivity.me().setId(activityId).get();
        ActConditionType actConditionType = ActConditionType.me().setFkActivityId(activityId).findFirst();
        if(Objects.isNull(couponActivity)){
            R.error("优惠券不存在！");
        }
        if(Objects.isNull(actConditionType)){
            R.error("优惠券规则不完整！");
        }
        for (String clienteleId : clienteleList ) {
            CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setId(clienteleId).get();
            if(Objects.nonNull(clienteleInfo)){
                // 检查用户是否派发了，已经派发不再派发
                ActGetCouponRecord record = ActGetCouponRecord.me().setFkClienteleId(clienteleInfo.getId()).setFkActivityId(activityId).setIsUse(Status.NO).findFirst();
                if(Objects.isNull(record)){
                    ActGetCouponRecord couponRecord = new ActGetCouponRecord();
                    couponRecord.setIsUse(Status.NO);
                    couponRecord.setFkActivityId(activityId);
                    couponRecord.setFkClienteleId(clienteleInfo.getId());
                    couponRecord.setActivityName(couponActivity.getActivityName());
                    couponRecord.setCouponCode(couponActivity.getCouponCode());
                    couponRecord.setClientName(clienteleInfo.getClientSurname() + clienteleInfo.getClientName());
                    couponRecord.setCouponDiscountTypeName(actConditionType.getDiscountTypeId());
                    couponRecord.setCouponDiscountValue(actConditionType.getDiscountValue());
                    couponRecord.setFkConditionTypeId(actConditionType.getId());
                    couponRecord.setCouponGetTypeName(couponActivity.getCouponGetTypeName());
                    couponRecord.setFkCouponGetTypeId(couponActivity.getFkCouponGetTypeId());
                    couponRecord.setEndTime(couponActivity.getEndTime());
                    couponRecord.setStartTime(couponActivity.getStartTime());
                    couponRecord.setAddUserId(Subject.id());
                    couponRecord.setAddTime(Utils.currentTimeSecond());
                    couponRecord.insert();
                }
            }
        }
        return R.ok();
    }


    /**
     * 新增或编辑优惠券
     * @param actCouponActivity
     * @return
     */
    public R doActCouponAddOrUpdate(ActCouponActivityDto actCouponActivity) {
        String curUserId = Subject.id();
        if(StringUtils.isBlank(curUserId)){
            R.error("当前用户信息为空");
        }
        EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(empEmployeeInfo)){
            R.error("当前用户失效");
        }

        // 获取当前选择的优惠券类型
        ActCouponGetType actCouponGetType = ActCouponGetType.me().setCouponGetTypeName("即时生成").get();

        String status = ActCouponEnum.NOT_START.status();
        // 判断当前优惠券状态
        if(actCouponActivity.getStartTime()<Utils.currentTimeSecond() && actCouponActivity.getEndTime() > Utils.currentTimeSecond()){
            status =  ActCouponEnum.UNDERWAY.status();
        }

        if( actCouponActivity.getEndTime() < Utils.currentTimeSecond()){
            status =  ActCouponEnum.FINISHED.status();
        }

        List<ActConditionType> ruleList = new ArrayList<ActConditionType>();
        List<Map<String, Object>> data = (List<Map<String, Object>>) actCouponActivity.getExten().get("ruleList");
        data.forEach(item ->{
            ActConditionType actConditionType = JSONObject.parseObject(JSON.toJSONString(item), ActConditionType.class);
            actConditionType.setTypeCondition(Helper.transformY2F(new BigDecimal(item.get("typeConditionStr").toString())));
            actConditionType.setDiscountValue(Helper.transformY2F(new BigDecimal(item.get("discountValueStr").toString())));
            ruleList.add(actConditionType);
        });
        ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setDiscountUseTypeCode(actCouponActivity.getDiscountTypeId()).get();
        if(StringUtils.isBlank(actCouponActivity.getId())){

            String id = IDSnowflake.id();

            actCouponActivity.setId(id).setActivityStatus(status).setFkCouponGetTypeId(actCouponGetType.getId()).setCouponGetTypeName(actCouponGetType.getCouponGetTypeName()).setAddTime(Utils.currentTimeSecond()).setAddUserId(curUserId).insert();

            for (ActConditionType actConditionType : ruleList) {
                actConditionType.setFkActivityId(id).setDiscountTypeId(actDiscountUseType.getId()).setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName()).setActivityName(actCouponActivity.getActivityName()).setAddTime(Utils.currentTimeSecond()).setAddUserId(curUserId).insert();
            }

        }else {
            actCouponActivity.setActivityStatus(status).setFkCouponGetTypeId(actCouponGetType.getId()).setCouponGetTypeName(actCouponGetType.getCouponGetTypeName()).update();
            for (ActConditionType actConditionType : ruleList) {
                if (Helper.isNotEmpty(actConditionType.getId())){
                    actConditionType.setFkActivityId(actCouponActivity.getId()).setDiscountTypeId(actDiscountUseType.getId()).setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName()).setActivityName(actCouponActivity.getActivityName()).update();
                }else{
                    actConditionType.setFkActivityId(actCouponActivity.getId()).setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName()).setActivityName(actCouponActivity.getActivityName()).setAddTime(Utils.currentTimeSecond()).setAddUserId(curUserId).insert();
                }
            }
            ActPromotionProduct.me().setFkActivityId(actCouponActivity.getId()).delete();
        }
        if(!actCouponActivity.getActPromotionProductList().isEmpty()){
            List<Map<String,Object>> mapList = actCouponActivity.getActPromotionProductList();
            mapList.forEach(map->{
                ActPromotionProduct actPromotionProduct = JSONObject.parseObject(JSON.toJSONString(map), ActPromotionProduct.class);
                actPromotionProduct.setFkActivityId(actCouponActivity.getId());
                actPromotionProduct.setAddTime(Utils.currentTimeSecond());
                actPromotionProduct.setAddUserId(empEmployeeInfo.getId());
                actPromotionProduct.setAddUserName(empEmployeeInfo.getEmployeeName());
                actPromotionProduct.setActivityName(actCouponActivity.getActivityName());
                actPromotionProduct.setDiscountTypeId(actDiscountUseType.getId());
                actPromotionProduct.setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName());
                actPromotionProduct.insert();
            });
        }
        return R.ok();
    }


    /**
     * 删除优惠券
     * @param ids
     * @return
     */
    public R deleteActCoupon(List<String> ids) {
        Db.tx(() -> {
            for (String id : ids) {
                ActCouponActivity.me().setId(id).delete();
            }
            return true;
        });
        return  R.ok();
    }


    /**
     * 随机获取优惠券码
     * @return
     */
    public String getCode() {
        return getRandomString(10);
    }

    /**
     * 获取指定长度随机字符串
     *
     * @param length
     * @return
     */
    public String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(3);
            long result = 0;
            switch (number) {
                case 0:
                    result = Math.round(Math.random() * 25 + 65);
                    sb.append(String.valueOf((char) result));
                    break;
                case 1:
                    result = Math.round(Math.random() * 25 + 97);
                    sb.append(String.valueOf((char) result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
                default:
                    System.out.println("default");
                    break;
            }
        }
        return sb.toString();
    }

}
