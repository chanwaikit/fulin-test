package com.mazentop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoumei
 * 测评-返现申请状态枚举
 */
@AllArgsConstructor
@Getter
public enum EvaCashBackApplyStatusEnum {

    /**
     * 返现申请状态
     */
    REVIEVED(1,"待审核","Applying"),

    ADOPT(2,"通过","Completed"),

    REJECT(3,"驳回" ,"Failed"),

    REMITTANCE(4,"转账" ,"Remittance");




    private final Integer status;

    private final String desc;

    private final String descEn;


    public static String getDescEn(Integer value) {
        EvaCashBackApplyStatusEnum[] statusEnums = values();
        for (EvaCashBackApplyStatusEnum applyStatusEnum : statusEnums) {
            if (applyStatusEnum.getStatus().equals(value)) {
                return applyStatusEnum.getDescEn();
            }
        }
        return null;
    }

    public static String getDesc(Integer value) {
        EvaCashBackApplyStatusEnum[] statusEnums = values();
        for (EvaCashBackApplyStatusEnum applyStatusEnum : statusEnums) {
            if (applyStatusEnum.getStatus().equals(value)) {
                return applyStatusEnum.getDesc();
            }
        }
        return null;
    }



}
