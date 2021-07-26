package com.mazentop.modules.freemarker.tag;

import com.mazentop.modules.emp.service.ActPromotionActivityService;
import com.mztframework.commons.Utils;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 获取产品活动时间
 *
 * @author dengy
 * @version 1.0
 * @date 2020/9/9 18:26
 */
@Component
@Slf4j
public class SkinActPromotionActivityTag extends Tag {

    @Autowired
    ActPromotionActivityService activityService;

    @Override
    public void execute() {
        String productId = getParam("productId");
        if(!Utils.isBlank(productId)) {
            setVariable("activityMap", activityService.getActivityMap(productId));
            renderBody();
        }else{
            log.error("[@skin_actPromotion_activity /] 缺少必要参数 productId，例如：[@skin_actPromotion_activity productId=\"产品ID\" /]");
        }
    }

    @Override
    public String name() {
        return "skin_actPromotion_activity";
    }
}
