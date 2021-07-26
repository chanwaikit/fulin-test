package com.mazentop.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhoumei
 * 测评-订单状态枚举
 */
@AllArgsConstructor
@Getter
public enum EvaOrdOrderStatusEnum {

    /**
     * 订单状态
     */
    PENDING(1,"待处理","Pending Orders"),

    AMAZON_WAIT(2,"亚马逊信息待审核","Wait For Confirmation"),

    AMAZON_ADOPT(3,"亚马逊信息审核通过","Confirmed"),

    AMAZON_REJECT(4,"亚马逊信息审核驳回","Confirmation failed"),

    CASHBACK_WAIT(5,"返现待审核","Wait For Review"),

    CASHBACK_ADOPT(6,"返现审核通过","Completed Orders"),

    CASHBACK_REJECT(7,"返现审核驳回","Cash back refusal"),

    CLOSED_ORDERS(8,"已关闭" ,"Closed Orders");


    private Integer status;

    private String desc;

    private String descEn;


    public static String getDescEn(Integer value) {
        EvaOrdOrderStatusEnum[] statusEnums = values();
        for (EvaOrdOrderStatusEnum orderStatusEnum : statusEnums) {
            if (orderStatusEnum.getStatus().equals(value)) {
                return orderStatusEnum.getDescEn();
            }
        }
        return null;
    }



    public static String getDescStr(Integer value) {
        EvaOrdOrderStatusEnum[] statusEnums = values();
        for (EvaOrdOrderStatusEnum orderStatusEnum : statusEnums) {
            if (orderStatusEnum.getStatus().equals(value)) {
                return orderStatusEnum.getDesc();
            }
        }
        return null;
    }



}
