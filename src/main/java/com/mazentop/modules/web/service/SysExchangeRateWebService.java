package com.mazentop.modules.web.service;

import com.alibaba.fastjson.JSONArray;
import com.mazentop.entity.SysExchangeRate;
import com.mazentop.modules.web.constants.*;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;

@Service
public class SysExchangeRateWebService {
    @Autowired
    BaseDao baseDao;


    public List<SysExchangeRate> sysExchangeRateList() {

        String sql = "SELECT * from  (SELECT * from  sys_exchange_rate  ORDER BY edit_time desc  LIMIT 100)as rate GROUP BY rate.tcur";
        List<SysExchangeRate> list = baseDao.queryForBeanList(sql, new SysExchangeRate());
        list.forEach(rate -> {
            if (Helper.isNotEmpty(rate.getTagRatio())) {
                rate.setTagRatio(rate.getTagRatio().setScale(2, BigDecimal.ROUND_DOWN));
            }
            rate.setRatio(rate.getRatio().setScale(2, BigDecimal.ROUND_DOWN));
        });
        return list;
    }

    public BigDecimal payCalculation(String id, BigDecimal prices) {
        BigDecimal price = null;
        SysExchangeRate sysExchangeRate = getSysExchangeRate(id);

        if (Helper.isEmpty(sysExchangeRate)) {
            return prices;
        }
        if (Helper.isNotEmpty(sysExchangeRate.getTagRatio()) && sysExchangeRate.getTagRatio().signum() > 0) {
            price = sysExchangeRate.getTagRatio().multiply(prices);
        } else {
            price = sysExchangeRate.getRatio().multiply(prices);
        }
        return price.setScale(2, BigDecimal.ROUND_DOWN);
    }
    public BigDecimal CurrencyToDollar(String id, BigDecimal prices) {
        BigDecimal price = null;

        SysExchangeRate sysExchangeRate =  getSysExchangeRate(id);;

        if (Helper.isEmpty(sysExchangeRate)) {
            return prices;
        }
        if (Helper.isNotEmpty(sysExchangeRate.getTagRatio()) && sysExchangeRate.getTagRatio().signum() > 0) {
            price = prices.divide(sysExchangeRate.getTagRatio(),2,BigDecimal.ROUND_DOWN);
        } else {
            price = prices.divide(sysExchangeRate.getRatio(),2,BigDecimal.ROUND_DOWN);
        }
        return price.setScale(2, BigDecimal.ROUND_DOWN);
    }

    public Result addSysExchangeRateCookie(HttpServletResponse response, String id) {
        SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(id).get();
        if (Helper.isEmpty(sysExchangeRate)) {
            return Result.toast("切换失败，暂无数据！");
        }
        try {
            // 制作cookie数据
            Cookie exchange = new Cookie(CacheConstant.RATIO, URLEncoder.encode(id+"-"+sysExchangeRate.getTcur(), "utf-8"));
            //设置在该项目下都可以访问该cookie
            exchange.setPath("/");
            exchange.setMaxAge(-1);
            //添加cookie
            response.addCookie(exchange);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return Result.success();
    }
    
    private SysExchangeRate getSysExchangeRate(String id){
        Object object = LFUCache.get(CacheConstant.SYS_EXCHANGE_RATE);
        if (Helper.isNotEmpty(object)){
            List<SysExchangeRate> list = JSONArray.parseArray(object.toString(), SysExchangeRate.class);
            for (SysExchangeRate sysExchangeRate : list) {
                if (sysExchangeRate.getId().equals(id)){
                    return sysExchangeRate;
                }
            }
        }else {
            SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(id).get();
            return sysExchangeRate;
        }
        return null;
    }
}
