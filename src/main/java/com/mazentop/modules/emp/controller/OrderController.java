package com.mazentop.modules.emp.controller;

import com.mazentop.entity.OrdRecallRecord;
import com.mazentop.modules.emp.commond.OrdRecallRecordCommond;
import com.mazentop.modules.emp.commond.OrdSalesOrderCommond;
import com.mazentop.modules.emp.service.ManageOrderService;
import com.mazentop.modules.emp.service.OrdRecallRecordService;
import com.mztframework.commons.Utils;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理后台订单接口
 *
 * @author chen quan
 * @date 2020/4/13 16:37
 **/
@RestController
@RequestMapping("/manage/order")
public class OrderController {

    @Autowired
    private ManageOrderService manageOrderService;

    @Autowired
    OrdRecallRecordService ordRecallRecordService;

    @Log("召回订单查询")
    @GetMapping("/recall/list")
    public Result recallRecordPage(OrdRecallRecordCommond commond) {
        return Result.build(() -> ordRecallRecordService.ordRecallRecordList(commond));
    }


    @Log("获取召回订单状态数量")
    @GetMapping(value = "/recall/getRecallOrderStatus")
    @ApiOperation(value = "获取召回订单状态数量", notes = "入参:查询条件")
    public Result getRecallOrderStatus() {
        return Result.build(() -> ordRecallRecordService.getRecallOrderStatus());
    }

    @Log("根据ID查询找回订单")
    @GetMapping("/recall/{id}")
    public Result findByIdQuery(@PathVariable(value = "id")String id){
        return Result.build(() -> ordRecallRecordService.getRecallRecordDetails(id));
    }

    @Log("根据ID删除找回订单")
    @GetMapping("/recall/del/{id}")
    public Result findByIdDel(@PathVariable(value = "id")String id){
        return Result.build(() -> OrdRecallRecord.me().setId(id).delete());
    }

    @Log("根据ID修改找回订单")
    @PostMapping("/recall/edit")
    public Result findByIdEdit(@RequestBody OrdRecallRecord ordRecallRecord){
        ordRecallRecord.setOperationTime( Utils.currentTimeSecond());
        return Result.build(() -> ordRecallRecord.update());
    }

    @Log("查询购买订单信息")
    @GetMapping("/order/list")
    @ResponseBody
    public Result queryClientOrderInformation(OrdSalesOrderCommond commond){

        return manageOrderService.queryClientOrderInformation(commond);
    }


}
