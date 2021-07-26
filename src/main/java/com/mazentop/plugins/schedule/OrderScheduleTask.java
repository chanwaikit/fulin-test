package com.mazentop.plugins.schedule;

import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.entity.EvaProProduct;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.modules.evaluation.service.EvaProProductService;
import com.mazentop.modules.user.commond.EvaluationUserOrderCommond;
import com.mazentop.modules.user.service.EvaluationOrderService;
import com.mazentop.modules.web.User;
import com.mztframework.commons.Lists;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
//@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class OrderScheduleTask {

    @Autowired
    EvaluationOrderService orderService;

    @Autowired
    EvaProProductService proProductService;
    // 订单扫描5分钟一次
    @Scheduled(fixedRate=1000*60*5)
    private void configureTasks() {
        // 处理订单超时
        EvaluationUserOrderCommond commond = new EvaluationUserOrderCommond();
        commond.setIsEnable(BooleanEnum.TRUE.getValue());
        commond.setOrderStatusList(Lists.newArrayList(1));
        List<EvaOrdOrder> list = EvaOrdOrder.me().find(commond);

        // 超过4小时，更改订单状态
        for(EvaOrdOrder order : list){
            // 是否超时
            if(Objects.nonNull(order.getExpirationTime())){
                long time = order.getExpirationTime() - Utils.currentTimeSecond();
                if(time <= 0 ) {
                    order.setStatus(EvaOrdOrderStatusEnum.CLOSED_ORDERS.getStatus()).update();
                    // 减商品返现次数
                    proProductService.doTrialsTimes(order.getFkProductId());
                }
            }
        }
    }
}
