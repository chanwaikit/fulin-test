package com.mazentop.modules.emp.service;

import com.mazentop.entity.SysSiteMap;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import org.springframework.stereotype.Service;

@Service
public class SysSiteMapService {

    public Result editSiteMap(SysSiteMap sysSiteMap){
        if (Helper.isNotEmpty(sysSiteMap)){
            if (Helper.isEmpty(sysSiteMap.getId())){
                sysSiteMap.insert();
            }else {
                sysSiteMap.update();
            }
        }
        return Result.success();
    }
}
