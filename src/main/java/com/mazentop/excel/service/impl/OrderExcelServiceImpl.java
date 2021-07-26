package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.excel.entity.OrdSalesOrderDetailEntity;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.service.SendEmail;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.modules.emp.commond.OrdSalesOrderCommond;
import com.mazentop.modules.emp.service.OrdSalesOrderService;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("order-excel")
public class OrderExcelServiceImpl implements ExcelService {

    @Autowired
    SendEmail sendEmail;

    @Autowired
    OrdSalesOrderService ordSalesOrderService;

    @Override
    public Result importExcel(HttpServletRequest request, MultipartFile file) {
        System.out.println("order import");
        request.getParameterMap();
        return null;
    }

    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = null;
        map = RequestUtil.getValue(request);
        if (Helper.isNotEmpty(map)) {
            OrdSalesOrderCommond commond = new OrdSalesOrderCommond();
            String scopes = map.get("scope").toString();
            if (scopes.equals("1")) {
                commond.setIds(Helper.toList(map.get("selections").toString(), String.class));
            } else if (scopes.equals("2")) {
                commond = JSONArray.parseObject(map.get("condition").toString(), OrdSalesOrderCommond.class);
            }
            List<OrdSalesOrderDetailEntity> ordSalesOrders = ordSalesOrderService.exportOrderDetail(commond);
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "订单信息", ExcelType.XSSF), OrdSalesOrderDetailEntity.class, ordSalesOrders);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            String title = "订单导出信息 " + Helper.format(new Date(), Helper.DATE_PATTERN1);
            if(!Objects.isNull(map.get("mail"))) {
                sendEmail.sendEmail(title, map.get("mail").toString(), baos.toByteArray(), "订单信息" + Helper.format(new Date(), Helper.DATE_PATTERN3) + ".xlsx");
            }
            response.setContentType("application/vnd.ms-excel");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

}
