package com.mazentop.modules.web.seodetail.impl;

import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.emp.commond.DictionaryCommond;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.seodetail.ISeoDetails;
import com.mazentop.plugins.session.SessionUtil;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 产品详情页面
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/4/27 11:00
 */
@Service("product-details")
public class ProProductDetailsImpl implements ISeoDetails {

    @Autowired
    ProProductMasterService proProductMasterService;


    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Override
    public String builder(ModelMap modelMap, ProSeo seo) {
        String exchangeId = Helper.getExchangeId(CacheConstant.RATIO);
        EvaProProduct evaProduct = EvaProProduct.me().setId(seo.getSource()).setIsShelve(BooleanEnum.TRUE.getValue()).get();
        if(Objects.isNull(evaProduct)) {
            return errorNull();
        }
        if (!Utils.isBlank(exchangeId)){
            evaProduct.addExten("exchangeId",exchangeId);
        }
        // 处理价格
        if (evaProduct.getPrice() != null) {
            evaProduct.addExten("price",Helper.transformF2Y(evaProduct.getPrice()));
        } else {
            evaProduct.addExten("price",0L);
        }
        if (evaProduct.getOriginalPrice() != null) {
            evaProduct.addExten("originalPrice",Helper.transformF2Y(evaProduct.getOriginalPrice()));
        } else {
            evaProduct.addExten("originalPrice",0L);
        }
        // 返现金额
        if (evaProduct.getRebates() != null) {
            BigDecimal bigDecimal = Helper.transformF2Y(evaProduct.getRebates());
            BigDecimal decimal = Helper.transformF2Y(evaProduct.getPrice());
            evaProduct.addExten("rebatesRatio",100);
            evaProduct.addExten("rebates",Helper.transformF2Y(evaProduct.getRebates()));
            if (bigDecimal.compareTo(BigDecimal.ZERO)==1&&decimal.compareTo(BigDecimal.ZERO)==1){
                evaProduct.addExten("rebatesRatio",bigDecimal.divide(decimal, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).intValue());
            }

        } else {
            evaProduct.addExten("rebates",0L);
        }

        if(StringUtils.isNoneBlank(evaProduct.getTips())){
            List<SysDictionary> dictionaryList = new ArrayList<SysDictionary>();
            List<String> ids =  JSONObject.parseObject(evaProduct.getTips(), List.class);
            if(!ids.isEmpty()) {
                DictionaryCommond dictionaryCommond = new DictionaryCommond();
                dictionaryCommond.setIds(ids);
                dictionaryList = SysDictionary.me().find(dictionaryCommond);
            }
            evaProduct.addExten("tips",dictionaryList);
        }
        // 分享金
        evaProduct.addExten("commissionRules",SysOptions.me().setOptionKey("site_commission_rules").get());
        // 国家货币单位
        SkinCountry skinCountry = SessionUtil.getSessionCountry();
        modelMap.put("countryCurrency", skinCountry.getCurrency());
        modelMap.put("countryFlag", skinCountry.getFlag());
        modelMap.put("product", evaProduct);
        modelMap.put("cid","");
        if(User.isAuth()){
            modelMap.put("cid",User.cid());
        }
        return originalTemplate(seo);
    }
}
