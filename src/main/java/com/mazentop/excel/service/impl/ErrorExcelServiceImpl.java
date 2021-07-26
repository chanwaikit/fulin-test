package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.excel.entity.*;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.model.ExcelEnum;
import com.mazentop.modules.emp.dto.CliClienteleInfoDto;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

@Service("e-excel")
public class ErrorExcelServiceImpl implements ExcelService {
    @Override
    public Result importExcel(HttpServletRequest request, MultipartFile file) {
        return null;
    }

    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = RequestUtil.getValue(request);
        if (Helper.isNotEmpty(map)){
            String token = map.get("token").toString();
            String type = map.get("type").toString();
            String json = LFUCache.get(token).toString();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (type.equals(ExcelEnum.EXCEL_CLIENTELE.key())){
                List<CliClienteleeriveEntity> cliClienteleInfoDtos = JSONArray.parseArray(json, CliClienteleeriveEntity.class);
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "顾客错误信息", ExcelType.XSSF), CliClienteleInfoDto.class, cliClienteleInfoDtos);
                workbook.write(baos);
                workbook.close();
            }else if (type.equals(ExcelEnum.EXCEL_PRODUCT.key())){
                List<ProProductMasterEntity> entity = JSONArray.parseArray(json, ProProductMasterEntity.class);
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "订单错误信息", ExcelType.XSSF), ProProductMasterEntity.class, entity);
                workbook.write(baos);
                workbook.close();
            } else if (type.equals(ExcelEnum.EXCEL_STOCK.key())){
                List<StockEntity> entity = JSONArray.parseArray(json, StockEntity.class);
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "库存错误信息", ExcelType.XSSF), StockEntity.class, entity);
                workbook.write(baos);
                workbook.close();
            }else if (type.equals(ExcelEnum.EXCEL_COMMENT.key())){
                List<ProCommentEntity> entity = JSONArray.parseArray(json, ProCommentEntity.class);
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "评论错误信息", ExcelType.XSSF), ProCommentEntity.class, entity);
                workbook.write(baos);
                workbook.close();
            }else if (type.equals(ExcelEnum.EXCEL_EVAPRODUCT.key())){
                List<EvaProProductEntity> entity = JSONArray.parseArray(json, EvaProProductEntity.class);
                Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "商品错误信息", ExcelType.XSSF), EvaProProductEntity.class, entity);
                workbook.write(baos);
                workbook.close();
            }
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("顾客信息.xlsx", "utf-8"));
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

    }
}
