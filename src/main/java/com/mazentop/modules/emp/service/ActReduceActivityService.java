package com.mazentop.modules.emp.service;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.emp.commond.ActReduceActivityCommond;
import com.mazentop.modules.emp.dto.ActConditionTypeDto;
import com.mazentop.modules.emp.dto.ActReducectivityDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author dengy
 * @title: ActReduceActivityService
 * @description: 满减活动业务类
 * @date 2020/3/18 09:32
 */

@Service
public class ActReduceActivityService {

    @Autowired
    BaseDao baseDao;

    public Page actReduceActivityList(ActReduceActivityCommond actReduceActivityCommond) {
        StringBuilder sql = new StringBuilder(" select * from act_reduce_activity where 1=1 ");
        actReduceActivityCommond.setCompanyId(Subject.group());
        Map<String, Object> params = new HashMap<>();
        if (actReduceActivityCommond.getStartTime() != null) {
            sql.append(" and start_time >= :startTime ");
            params.put("startTime", actReduceActivityCommond.getStartTime());
        }
        if (actReduceActivityCommond.getEndTime() != null) {
            sql.append(" and end_time <= :endTime ");
            params.put("endTime", actReduceActivityCommond.getEndTime());
        }
        if (StringUtils.isNotEmpty(actReduceActivityCommond.getActivityName())) {
            sql.append(" and activity_name like :activityName ");
            params.put("activityName", actReduceActivityCommond.getActivityName());
        }
        if (StringUtils.isNotEmpty(actReduceActivityCommond.getActivityStatus())) {
            sql.append(" and activity_status = :status ");
            params.put("status", actReduceActivityCommond.getActivityStatus());
        }
        if (StringUtils.isNotEmpty(actReduceActivityCommond.getCompanyId())) {
            sql.append(" and company_id = :companyId ");
            params.put("companyId", actReduceActivityCommond.getCompanyId());
        }
        List<Map<String, Object>> list = baseDao.paginate(sql.toString(), params, actReduceActivityCommond);
        if (!list.isEmpty()) {
            list.forEach(map -> {
                List<ActConditionType> actConditionTypeList = ActConditionType.me().setFkActivityId(map.get("id").toString()).find();
                optActConditionType(actConditionTypeList);
                map.put("typeList", actConditionTypeList);
            });
        }
        return new Page(list, actReduceActivityCommond);
    }


    /**
     * 获取满减活动状态数量
     * @return
     */
    public HashMap<String, Object> getReduceActivityStatus() {
        HashMap<String, Object> map = new HashMap<>(4);

        map.put("allCount", ActReduceActivity.me().findCount());
        map.put("notStartedCount", ActReduceActivity.me().setActivityStatus("1").findCount());
        map.put("inCount", ActReduceActivity.me().setActivityStatus("2").findCount());
        map.put("finishedCount", ActReduceActivity.me().setActivityStatus("3").findCount());

        return map;
    }



    public R doActReduceActivityUpdateOrAdd(ActReducectivityDto actReducectivityDto) {
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return R.error("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(curEmployee)) {
            return R.error("登陆人信息获取失败!");
        }
        if (Objects.isNull(actReducectivityDto)) {
            return R.error("活动数据获取失败!");
        }
        if (actReducectivityDto.getTypeList().isEmpty()) {
            return R.error("活动优惠规则为空!");
        }
        if (StringUtils.isEmpty(actReducectivityDto.getId())) {
            actReducectivityDto.setAddTime(Utils.currentTimeSecond());
            actReducectivityDto.setAddUserId(curEmployee.getId());
        }
        Long sysDate =  Utils.currentTimeSecond();
        if(sysDate < actReducectivityDto.getStartTime()){
            actReducectivityDto.setActivityStatus("1");
        }else{
            actReducectivityDto.setActivityStatus("2");
        }
        if(sysDate > actReducectivityDto.getEndTime()){
            actReducectivityDto.setActivityStatus("3");
        }
        actReducectivityDto.insertOrUpdate();
        addConditionType(actReducectivityDto, curEmployee);
        return R.ok();
    }

    public void addConditionType(ActReducectivityDto actReducectivityDto, EmpEmployeeInfo curEmployee) {
        List<ActConditionTypeDto> actConditionTypeList = actReducectivityDto.getTypeList();
        List<ActConditionType> typeList = ActConditionType.me().setFkActivityId(actReducectivityDto.getId()).find();
        ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setDiscountUseTypeCode(actReducectivityDto.getDiscountTypeId()).get();
        //若不存在则做新增操做
        if (typeList.isEmpty()) {
            if (!actConditionTypeList.isEmpty()) {
                actConditionTypeList.forEach(type -> {
                    type.setFkActivityId(actReducectivityDto.getId());
                    type.setAddTime(Utils.currentTimeSecond());
                    type.setAddUserId(curEmployee.getId());
                    type.setActivityName(actReducectivityDto.getActivityName());
                    type.setDiscountTypeId(actDiscountUseType.getId());
                    type.setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName());
                    type.setIsNotThreshold(actReducectivityDto.getIsLimit());
                    optActConditionTypes(type);
                    type.insert();
                });
            }
        } else {
            List<String> types = new ArrayList<>();
            // 获取已存在的优惠信息id
            typeList.forEach(id -> {
                types.add(id.getId());
            });
            // 遍历操作的优惠信息数据
            actConditionTypeList.forEach(info -> {
                //判断该数据是否存在优惠信息表
                info.setActivityName(actReducectivityDto.getActivityName());
                info.setDiscountTypeId(actDiscountUseType.getId());
                info.setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName());
                info.setIsNotThreshold(actReducectivityDto.getIsLimit());
                if (StringUtils.isNotEmpty(info.getId())) {
                    //若存在列表则移除
                    if (types.indexOf(info.getId()) != -1) {
                        types.remove(types.indexOf(info.getId()));
                    }
                } else {
                    info.setAddTime(Utils.currentTimeSecond());
                    info.setAddUserId(curEmployee.getId());
                    info.setFkActivityId(actReducectivityDto.getId());
                }
                optActConditionTypes(info);
                info.insertOrUpdate();
            });
            //id集合若不为空则删除
            if (!types.isEmpty()) {
                types.forEach(id -> {
                    ActConditionType.me().setId(id).delete();
                });
            }
        }
    }

    public R deleteActReduceActivity(List<String> ids) {
        if (ids.isEmpty()) {
            return R.error("id获取失败!");
        }
        ids.forEach(id -> {
            ActReduceActivity.me().setId(id).delete();
            ActConditionType.me().setFkActivityId(id).delete();
        });
        return R.ok();
    }

    // 查询金额处理
    public void optActConditionType(List<ActConditionType> actConditionTypeList) {
        if (!actConditionTypeList.isEmpty()) {
            actConditionTypeList.forEach(type -> {
                if (type.getTypeCondition() != null) {
                    type.addExten("typeCondition", Helper.transformF2Y(type.getTypeCondition()));
                } else {
                    type.addExten("typeCondition", 0);
                }
                if (type.getDiscountValue() != null) {
                    type.addExten("discountValue", Helper.transformF2Y(type.getDiscountValue()));
                } else {
                    type.addExten("discountValue", 0);
                }
            });
        }
    }

    // 入库活动优惠处理
    public void optActConditionTypes(ActConditionTypeDto actConditionType) {
        if (StringUtils.isNoneBlank(actConditionType.getDiscountValueStr())) {
            actConditionType.setDiscountValue(Helper.transformY2F(new BigDecimal(actConditionType.getDiscountValueStr())));
        } else {
            actConditionType.setDiscountValue(new Long(0));
        }
        if (StringUtils.isNoneBlank(actConditionType.getTypeConditionStr())) {
            actConditionType.setTypeCondition(Helper.transformY2F(new BigDecimal(actConditionType.getTypeConditionStr())));
        } else {
            actConditionType.setTypeCondition(new Long(0));
        }
    }
}
