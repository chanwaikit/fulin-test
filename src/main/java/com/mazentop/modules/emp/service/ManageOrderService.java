package com.mazentop.modules.emp.service;

import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.OrdSalesOrderCommond;
import com.mazentop.util.Helper;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author chen quan
 * @date 2020/4/26 20:06
 **/
@Service
public class ManageOrderService {


    public Result queryClientOrderInformation(OrdSalesOrderCommond commond) {
        commond.setO("-" + OrdSalesOrder.F_ADD_TIME);
        Page pageList = OrdSalesOrder.me().paginate(commond);
        pageList.getList().forEach(c -> {
            OrdSalesOrder o = (OrdSalesOrder) c;
            o.addExten("totalPrice", Helper.transformF2Y(o.getTotalPrice()));
            o.addExten("totalTransportsFree",Helper.transformF2Y(o.getTotalTransportsFree()));
            o.addExten("totalPaymentFree",Helper.transformF2Y(o.getTotalPaymentFree()));
            o.addExten("addTime", Helper.timeStampFormat(o.getAddTime()));
            o.addExten("status",OrdSalesOrderStatusEnum.getDesc(o.getSalesOrderStatus()));
        });

        return Result.build(() -> pageList);
    }
}
