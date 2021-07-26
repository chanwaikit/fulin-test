package com.mazentop.modules.user.dto;

import com.mazentop.entity.OrdShoppingCart;
import lombok.Data;

import java.util.List;

/**
 * 无登录购物车信息
 */
@Data
public class OrdShoppingCarCookieDto {

    /**
     * 购物车列表
     */
    List<OrdShoppingCart> cartList;


    /**
     * 记录购物车数量
     */
    Long cartCount;
}
