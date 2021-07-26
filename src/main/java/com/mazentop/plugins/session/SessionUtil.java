package com.mazentop.plugins.session;

import com.mazentop.entity.SkinCountry;
import com.mazentop.model.Constant;
import com.mztframework.commons.WebUtil;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

/**
 * sesson 工具类
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/2 20:40
 */
public class SessionUtil {

    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String name) {
        try {
            return (T) WebUtils.getRequiredSessionAttribute(WebUtil.getHttpServletRequest(), name);
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public static void setSessionAttribute(String name, Object o) {
        WebUtils.setSessionAttribute(WebUtil.getHttpServletRequest(), name, o);
    }


    public static SkinCountry getSessionCountry() {
        SkinCountry skinCountry = SessionUtil.getSessionAttribute(Constant.CURRENT_COUNTRY);
        if(Objects.isNull(skinCountry)) {
            skinCountry = new SkinCountry();
            return skinCountry.setCountryCode("US").get();
        }
        return skinCountry;
    }


    public static void setSessionCountry(SkinCountry skinCountry) {
        setSessionAttribute(Constant.CURRENT_COUNTRY, skinCountry);
    }
}
