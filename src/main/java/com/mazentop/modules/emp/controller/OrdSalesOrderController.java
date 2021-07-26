package com.mazentop.modules.emp.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.mazentop.entity.*;
import com.mazentop.excel.entity.OrdSalesOrderEntity;
import com.mazentop.model.OptionsEnum;
import com.mazentop.model.OrdSalesOrderTypeEnum;
import com.mazentop.modules.emp.commond.OrdSalesOrderCommond;
import com.mazentop.modules.emp.dto.OrdSalesOrderDto;
import com.mazentop.modules.emp.service.OrdSalesOrderService;
import com.mazentop.modules.user.service.ClientCenterService;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dengy
 * @title: OrdSalesOrderController
 * @description: 订单管理
 * @date 2020/3/14 15:24
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/ordSalesOrderInfo")
@Api(value = "/option/v1", tags = "订单:订单管理", description = "OrdSalesOrderController", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrdSalesOrderController {

    @Autowired
    OrdSalesOrderService ordSalesOrderService;

    @Autowired
    ClientCenterService clientCenterService;


    @Log("线上支付订单列表")
    @GetMapping(value = "/ord/onLine/SalesOrderList")
    @ApiOperation(value = "线上支付订单列表", notes = "入参:查询条件")
    public Result ordOnLineSalesOrderList(OrdSalesOrderCommond ordSalesOrderCommond) {
        ordSalesOrderCommond.setPlatformType(OrdSalesOrderTypeEnum.TYPE_PAY.status());
        return Result.build(() ->  ordSalesOrderService.OrdSalesOrderList(ordSalesOrderCommond));
    }

    @Log("获取订单状态数量")
    @GetMapping(value = "/getOrderStatus")
    @ApiOperation(value = "获取订单状态数量", notes = "入参:查询条件")
    public Result getOrderStatus() {
        return Result.build(() -> ordSalesOrderService.getOrderStatus());
    }

    @Log("线下支付订单列表")
    @GetMapping(value = "/ord/offline/SalesOrderList")
    @ApiOperation(value = "线下支付订单列表", notes = "入参:查询条件")
    public Result ordSalesOrderList(OrdSalesOrderCommond ordSalesOrderCommond) {
        ordSalesOrderCommond.setPlatformType(OrdSalesOrderTypeEnum.TYPE_SIGN.status());
        return Result.build(() ->  ordSalesOrderService.OrdSalesOrderList(ordSalesOrderCommond));
    }


    @Log("订单列表")
    @GetMapping(value = "/getOrderList")
    @ApiOperation(value = "订单列表", notes = "入参:查询条件")
    public Result getOrderList(OrdSalesOrderCommond commond) {
        return Result.build(() ->  ordSalesOrderService.getOrderList(commond));
    }



    @Log("生成订单信息")
    @PostMapping(value = "/doOrdSalesOrderAdd")
    @ApiOperation(value = "生成订单信息", notes = "入参:保存参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ordSalesOrderDto", value = "保存参数", required = true, dataType = "OrdSalesOrderDto")
    })
    public Result doOrdSalesOrderAdd(@RequestBody OrdSalesOrderDto ordSalesOrderDto) {
        return Result.build(() -> ordSalesOrderService.doOrdSalesOrderAdd(ordSalesOrderDto));
    }

    @Log("导出订单信息")
    @PostMapping(value = "/exportOrder")
    @ApiOperation(value = "导出订单信息")
    public void exportOrder(HttpServletResponse response, @RequestBody OrdSalesOrderCommond commond) throws IOException {
        List<OrdSalesOrder> ordSalesOrders = ordSalesOrderService.exportOrder(commond);
        List<OrdSalesOrderEntity> list=new ArrayList<>();
        ordSalesOrders.forEach(order->{
            OrdSalesOrderEntity ordSalesOrderEntity=new OrdSalesOrderEntity();
            BeanUtils.copyProperties(order,ordSalesOrderEntity);
            ordSalesOrderEntity.setPaymentTime(Helper.timestampToDate(order.getPaymentTime(),Helper.DATE_PATTERN1));
            ordSalesOrderEntity.setTotalPrice(Helper.transformF2Y(order.getTotalPrice()).toString());
            ordSalesOrderEntity.setPaymentTime(Helper.timestampToDate(order.getPaymentTime(),Helper.DATE_PATTERN1));
            ordSalesOrderEntity.setTotalPaymentFree(Helper.transformF2Y(order.getTotalPaymentFree()).toString());
            if (order.getTotalTransportsFree()!=null){
                ordSalesOrderEntity.setTotalTransportsFree(Helper.transformF2Y(order.getTotalTransportsFree()).toString());
            }
            list.add(ordSalesOrderEntity);
        });

        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("订单信息", "订单信息", ExcelType.XSSF), OrdSalesOrderEntity.class, list);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("订单信息.xlsx", "utf-8"));
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
        response.getOutputStream().close();

    }

    @Log("订单召回邮件发送")
    @GetMapping(value = "/order/recovery/email/{refundId}")
    @ApiOperation(value = "订单召回邮件发送")
    public Result orderRecoverySendEmail(@PathVariable String refundId, HttpServletRequest request){
        OrdRecallRecord record = OrdRecallRecord.me().setId(refundId).get();
        if (Helper.isNotEmpty(record)){
            if("01".equals(record.getRecallStatus())){
                return Result.toast("该订单已召回成功！");
            }
            if (Helper.isNotEmpty(record.getClientEmail())){
                try {
                    SysOptions sysOptions = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get();
                    if(Objects.isNull(sysOptions)){
                        return Result.toast("系统地址获取失败!");
                    }
                    ordSalesOrderService.orderRecoverySendEmail(record,sysOptions);
                }catch (Exception e){
                    e.printStackTrace();
                    return Result.toast("服务器信息获取失败！");
                }
                return Result.success();
            }
            return Result.toast("用户邮箱为空！");
        }
        return Result.toast("暂无此订单");
    }
    @Log("同步可召回订单")
    @GetMapping(value = "/synchronizationRecord")
    @ApiOperation(value = "同步可召回订单")
    public Result synchronizationRecord(){
        Long count = OrdBalanceTheBooks.me().setIsDiscard(0).setIsRecall(0).setIsNeedRecall(1).findCount();
        if(count<=0){
            return Result.toast("暂无可同步召回订单!");
        }
        return Result.build(() ->  clientCenterService.synchronizationRecord(0));
    }

    @GetMapping(value = "/clientele/{clienteleId}")
    @ApiOperation(value = "顾客订单")
    public Result clienteleOrder(@PathVariable("clienteleId")String clienteleId){
        return Result.build(() ->  clientCenterService.clienteleByOrder(clienteleId));
    }

    @PostMapping(value = "/updateOrderTag")
    @ApiOperation(value = "修改订单标签", notes = "入参:主键 商品")
    public Result updateOrderTag(@RequestBody EvaOrdOrder order){
        order.update();
        return Result.success();
    }
}
