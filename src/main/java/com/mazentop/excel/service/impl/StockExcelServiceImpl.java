package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.ProProductStock;
import com.mazentop.excel.entity.CliClienteleeriveEntity;
import com.mazentop.excel.entity.ProProductMasterEntity;
import com.mazentop.excel.entity.StockEntity;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.service.SendEmail;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.modules.emp.commond.ProProductStockCommond;
import com.mazentop.modules.emp.dto.ProProductMasterDto;
import com.mazentop.modules.emp.dto.query.ExportCliClienteleQueryDto;
import com.mazentop.modules.emp.service.CliClienteleInfoService;
import com.mazentop.modules.emp.service.ProProductStockService;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service("stock-excel")
public class StockExcelServiceImpl implements ExcelService {

    @Autowired
    SendEmail sendEmail;

    @Autowired
    ProProductStockService stockService;


    /**
     * 库存导入
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Result importExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        List<StockEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), StockEntity.class, importParams);
        if (null == list || list.isEmpty()) {
            return Result.toast("文件格式有误！");
        } else {
            list.remove(0);
            if (list.size() == 0) {
                return Result.toast("文件格式有误！");
            }
        }
        // 库存信息存在，能导入的列表数据
        List<ProProductStock> productStockList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        cache(list, productStockList, map);
        for (ProProductStock proProductStock : productStockList) {
            stockService.doEditProductStock(proProductStock);
        }
        return Result.build(() -> map);
    }



    private void cache(List<StockEntity> list, List<ProProductStock> stockList, Map<String, Object> map) {
        int errorNum = 0;
        String error = null;
        ProProductStock productStockInfo;
        // 导入错误列表（库存信息不存在）
        List<StockEntity> errorStockList = new ArrayList<>();
        for (StockEntity stockEntity : list) {
            productStockInfo = new ProProductStock();
            if (Objects.isNull(stockEntity.getStockId())) {
                error = " (库存ID)列不能为空";
                errorNum++;
                setError(errorStockList, error, stockEntity);
                continue;
            } else {
                ProProductStock info = ProProductStock.me().setId(stockEntity.getStockId()).get();
                if (Helper.isEmpty(info)) {
                    error = " (库存)信息不存在";
                    errorNum++;
                    setError(errorStockList, error, stockEntity);
                    continue;
                } else {
                    productStockInfo.setId(info.getId());
                }
            }
            if (Objects.isNull(stockEntity.getProductStockNumber())) {
                error = " (库存)列不能为空";
                errorNum++;
                setError(errorStockList, error, stockEntity);
                continue;
            } else if(isInteger(stockEntity.getProductStockNumber()+"")){
                productStockInfo.setProductStockNumber(Integer.parseInt(stockEntity.getProductStockNumber()));
            } else {
                error = " (库存)列不能格式不正确";
                errorNum++;
                setError(errorStockList, error, stockEntity);
                continue;
            }
            stockList.add(productStockInfo);
        }
        if (errorStockList.size() > 0) {
            String key = RandomStringUtils.randomAlphanumeric(20);
            LFUCache.put(key, Helper.toJson(errorStockList));
            map.put("errorKey", key);
        }
        map.put("error", errorNum);
        map.put("success", stockList.size());

    }

    /**
     * 判断是否是数字类型
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }


    private void setError(List<StockEntity> errorStock, String error, StockEntity dto) {
        dto.setError(error);
        errorStock.add(dto);
    }


    /**
     * 库存导出
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = RequestUtil.getValue(request);
        if (Helper.isNotEmpty(map)) {
            String scopes = map.get("scope").toString();
            ProProductStockCommond queryDto = new ProProductStockCommond();
            if (scopes.equals("1")) {
                queryDto.setIds(Helper.toList(map.get("selections").toString(), String.class));
            } else if (scopes.equals("2")) {
                ProProductStockCommond condition = JSONArray.parseObject(map.get("condition").toString(), ProProductStockCommond.class);
                BeanUtils.copyProperties(condition, queryDto);
            }
            List<ProProductStock> list = stockService.getProProductStockList(queryDto);
            List<StockEntity> entityList = new ArrayList<>();

            StockEntity infoEntity = new StockEntity();

            infoEntity.setStockId(StockEntity.TipInfo.ID_INFO);
            infoEntity.setProductSpec(StockEntity.TipInfo.PRODUCTSPEC_INFO);
            infoEntity.setBarCode(StockEntity.TipInfo.BARCODE_INFO);
            infoEntity.setMarketPrice(StockEntity.TipInfo.MARKETPRICE_INFO);
            infoEntity.setMallPrice(StockEntity.TipInfo.MALLPRICE_INFO);
            infoEntity.setSKU(StockEntity.TipInfo.SKU_INFO);
            infoEntity.setProductName(StockEntity.TipInfo.PRODUCTNAME_INFO);
            infoEntity.setProductStockNumber(StockEntity.TipInfo.PRODUCTSTOCKNUMBER_INFO);
            entityList.add(infoEntity);

            list.forEach(productStock -> {
                StockEntity stockEntity = new StockEntity();

                stockEntity.setStockId(productStock.getId());
                stockEntity.setProductName((String) productStock.getExten().get("productName"));
                stockEntity.setSKU(productStock.getProductSubSku());
                stockEntity.setMallPrice((String) productStock.getExten().get("productMallPrice"));
                stockEntity.setMarketPrice((String) productStock.getExten().get("productMarketPrice"));
                stockEntity.setProductStockNumber(productStock.getProductStockNumber()+"");
                stockEntity.setBarCode(productStock.getBarCode());
                stockEntity.setProductSpec((String) productStock.getExten().get("spec"));

                entityList.add(stockEntity);
            });
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "库存", ExcelType.XSSF), StockEntity.class, entityList);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            String title = "库存导出信息 " + Helper.format(new Date(), Helper.DATE_PATTERN1);
            if(!Objects.isNull(map.get("mail"))) {
                sendEmail.sendEmail(title, map.get("mail").toString(), baos.toByteArray(), "库存信息" + Helper.format(new Date(), Helper.DATE_PATTERN3) + ".xlsx");
            }
            response.setContentType("application/vnd.ms-excel");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

    }

}
