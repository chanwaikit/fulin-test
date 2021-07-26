package com.mazentop.modules.web.controller.advice;

import com.mazentop.entity.CliCollectorItem;
import com.mazentop.entity.OrdShoppingCart;
import com.mazentop.entity.SkinCountry;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.service.TouristService;
import com.mazentop.modules.web.User;
import com.mazentop.plugins.session.SessionUtil;
import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/4 02:25
 */
@ControllerAdvice
public class UserControllerAdvice {

    @Autowired
    BaseDao baseDao;

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Autowired
    TouristService touristService;

    @ModelAttribute("User")
    public UserControllerAdvice userControllerAdvice() {
        return this;
    }

    public User auth() {
        return User.auth();
    }

    public SkinCountry sCountry() {
        return SessionUtil.getSessionCountry();
    }

    public boolean isAuth() {
        return User.isAuth();
    }

    public String redirect() {
        return User.redirect();
    }

    public Long shoppingCartNum() {
        if(isAuth()) {
            return OrdShoppingCart.me().setFkClienteleId(User.id())
                    .setIsGenOrder(0)
                    .setIsGenBalanceAccounts(0)
                    .setIsDel(0)
                    .findCount();
        }else{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
           return touristService.getCartNum(request);
        }
    }

    /**
     * 商品收藏
     * @return
     */
    public Long collectionNum() {
        if(isAuth()){
            return CliCollectorItem.me().setFkClienteleId(User.id()).setIsEnable(1).setFkCollectorItemTypeId("2").findCount();
        }
        return 0L;
    }
}
