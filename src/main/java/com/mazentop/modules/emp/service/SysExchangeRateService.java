package com.mazentop.modules.emp.service;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.SysExchangeRate;
import com.mazentop.modules.web.constants.*;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SysExchangeRateService {

    @Autowired
    BaseDao baseDao;

    public Result doEditSysExchangeRate(SysExchangeRate sysExchangeRate){
        if (Helper.isEmpty(sysExchangeRate.getId())){
            sysExchangeRate.insert();
            LFUCache.remove(CacheConstant.SYS_EXCHANGE_RATE);
            List<SysExchangeRate> sysExchangeRates =sysExchangeRateList();
            LFUCache.put(CacheConstant.SYS_EXCHANGE_RATE,JSON.toJSONString(sysExchangeRates));
        }else {
            sysExchangeRate.update();
            LFUCache.remove(CacheConstant.SYS_EXCHANGE_RATE);
            List<SysExchangeRate> sysExchangeRates =sysExchangeRateList();
            LFUCache.put(CacheConstant.SYS_EXCHANGE_RATE,JSON.toJSONString(sysExchangeRates));
        }


        return Result.success();
    }

    public List<SysExchangeRate> sysExchangeRateList() {
        String sql="SELECT * from  (SELECT * from  sys_exchange_rate  ORDER BY edit_time desc  LIMIT 100)as rate GROUP BY rate.tcur";
        List<SysExchangeRate> list = baseDao.queryForBeanList(sql, new SysExchangeRate());
        list.forEach(rate->{
            if (Helper.isNotEmpty(rate.getTagRatio())){
                rate.setTagRatio(rate.getTagRatio().setScale(2, BigDecimal.ROUND_DOWN));
            }
            rate.setRatio(rate.getRatio().setScale(2, BigDecimal.ROUND_DOWN));
        });
        return list;
    }

    public Result add(){

        return null;
    }
}
