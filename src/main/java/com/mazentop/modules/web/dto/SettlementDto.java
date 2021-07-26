package com.mazentop.modules.web.dto;

import com.mazentop.entity.OrdShoppingCart;
import com.mazentop.modules.user.dto.SettlementConfirmDto;
import lombok.Data;

import java.util.List;

@Data
public class SettlementDto {

    /**
     * 结算单id
     */
    private String settlementId;

    /**
     * 用户地址
     */
    private SettlementConfirmDto.Address shippingAddress;


    private String addressId;

    private String countryName;

    /**
     * 满减活动
     */
    private String reduceActivity;

    /**
     * 发票邮寄地址
     */
    private SettlementConfirmDto.Address invoiceAddress;
    /**
     * 优惠卷码
     */
    private String couponCode;
    /**
     * 购物车
     */
    private List<OrdShoppingCart> carts;
    /**
     * 数量
     */
    private int cartNum;
    /**
     * 总价
     */
    private String subTotalPrice;
    /**
     * 支付价格
     */
    private String totalPrice;
    /**
     * 运费
     */
    private String shippingPrice;
    /**
     * 是否保存该地址
     */
    private Boolean isSaveAddress;
    /**
     *是否与收货地址相同
     */
    private Boolean shippingAndInvoice;
    /**
     * 是否启用物流方式
     */
    private Boolean isOptionLogistics;
    /**
     * 物流方案
     */
    private String freeSchemes;

    /**
     * 物流名称
     */
    private String freeSchemesName;

    /**
     * 物流方案集合
     */
    private List<FreeSchemes> freeSchemeList;

    /**
     * 购物车paypal支付标识
     */
    private Boolean cartFlag;

    @Data
    public static class FreeSchemes{
       private String  id;

       private String name;

       private String freeValue;

       private Integer flag;
    }
}
