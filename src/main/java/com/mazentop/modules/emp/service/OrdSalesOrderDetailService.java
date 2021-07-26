package com.mazentop.modules.emp.service;

import com.mazentop.entity.EmpEmployeeInfo;
import com.mazentop.entity.OrdSalesOrder;
import com.mazentop.entity.OrdSalesOrderDetail;
import com.mazentop.entity.OrdSalesOrderOperateRecord;
import com.mazentop.modules.emp.dto.OrdSalesOrderDetailDto;
import com.mazentop.modules.emp.dto.UpdateOrdSalesOrderDto;
import com.mazentop.util.Helper;
import com.mazentop.util.Helper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: wangzy
 * @date: 2020/3/14
 * @description:
 */
@Service
public class OrdSalesOrderDetailService {

    @Autowired
    OrdSalesOrderOperateRecordService ordSalesOrderOperateRecordService;

    public OrdSalesOrderDetailDto getOrdSalesOrderDetail(String id) {

        OrdSalesOrderDetailDto data = new OrdSalesOrderDetailDto();

        // 获取订单主表数据
        OrdSalesOrder ordSalesOrder = OrdSalesOrder.me().setId(id).get();

        // 获取订单详情集合
        List<OrdSalesOrderDetail> ordSalesOrderDetails = OrdSalesOrderDetail.me().setFkSalesOrderId(id).find();
        ordSalesOrderDetails.forEach(ordSalesOrderDetail -> {
            if (Helper.isNotEmpty(ordSalesOrderDetail.getProductMallPrice())){
                ordSalesOrderDetail.addExten("productMallPrice",Helper.transformF2Y(ordSalesOrderDetail.getProductMallPrice()));
            }
            if (Helper.isNotEmpty(ordSalesOrderDetail.getCusPrice())){
                ordSalesOrderDetail.addExten("cusPrice",Helper.transformF2Y(ordSalesOrderDetail.getCusPrice()));
            }
            if (Helper.isNotEmpty(ordSalesOrderDetail.getProductMarketPrice())){
                ordSalesOrderDetail.addExten("productMarketPrice",Helper.transformF2Y(ordSalesOrderDetail.getProductMarketPrice()));
            }
            if (Helper.isNotEmpty(ordSalesOrderDetail.getProductActivityPrice())){
                ordSalesOrderDetail.addExten("productActivityPrice",Helper.transformF2Y(ordSalesOrderDetail.getProductActivityPrice()));
            }
            if (Helper.isNotEmpty(ordSalesOrderDetail.getPurchasePrice())){
                ordSalesOrderDetail.addExten("purchasePrice",Helper.transformF2Y(ordSalesOrderDetail.getPurchasePrice()));
            }
        });
        // 获取订单操作记录
        List<OrdSalesOrderOperateRecord> ordSalesOrderOperateRecords = OrdSalesOrderOperateRecord.me().setFkSalesOrderId(id).find();
        BeanUtils.copyProperties(ordSalesOrder,data);
        if (Helper.isNotEmpty(data.getTotalPaymentFree())){
            data.addExten("totalPaymentFree", Helper.transformF2Y(data.getTotalPaymentFree()));
        }
        if(Helper.isNotEmpty(data.getTotalPrice())){
            data.addExten("totalPrice", Helper.transformF2Y(data.getTotalPrice()));
        }
        if (Helper.isNotEmpty(data.getTotalTransportsFree())){
            data.addExten("totalTransportsFree", Helper.transformF2Y(data.getTotalTransportsFree()));
        }
        if (Helper.isNotEmpty(data.getProductDiscountPrice())){
            data.addExten("productDiscountPrice", Helper.transformF2Y(data.getProductDiscountPrice()));
        }
        if (Helper.isNotEmpty(data.getTaxAmount())){
            data.addExten("taxAmount", Helper.transformF2Y(data.getTaxAmount()));
        }
        data.setOrdSalesOrderDetailList(ordSalesOrderDetails);
        data.setOrdSalesOrderOperateRecordList(ordSalesOrderOperateRecords);

        return data;
    }

    /**
     * 取消订单
     * @param curEmployee
     * @param updateOrdSalesOrderDto
     */
    public void cancellationOfOrder(EmpEmployeeInfo curEmployee, UpdateOrdSalesOrderDto updateOrdSalesOrderDto) {
        ordSalesOrderOperateRecordService.doSaveOperateRecord(OrdSalesOrder.me().setSalesOrderNo(updateOrdSalesOrderDto.getId()).get(),curEmployee);
    }

    /**
     * 订单发货
     * @param curEmployee
     * @param updateOrdSalesOrderDto
     */
    public void shippedOfOrder(EmpEmployeeInfo curEmployee, UpdateOrdSalesOrderDto updateOrdSalesOrderDto) {
        ordSalesOrderOperateRecordService.doSaveOperateRecord(OrdSalesOrder.me().setSalesOrderNo(updateOrdSalesOrderDto.getId()).get(),curEmployee);
    }
}
