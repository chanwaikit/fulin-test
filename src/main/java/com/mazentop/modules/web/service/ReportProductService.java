package com.mazentop.modules.web.service;

import com.mazentop.entity.ProReportProduct;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ReportProductService {

    public void reportRecordingAdd(ProReportProduct product){
        if (Helper.isNotEmpty(product.getTitle())){
            CompletableFuture.runAsync(()->{
                product.setAddTime(Utils.currentTimeSecond());
                product.insert();
            });
        }
    }

}
