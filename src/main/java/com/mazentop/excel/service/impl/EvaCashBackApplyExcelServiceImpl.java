package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.mazentop.entity.*;
import com.mazentop.excel.entity.EvaCashBackApplyEntity;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.service.SendEmail;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.model.EvaCashBackApplyStatusEnum;
import com.mazentop.modules.downloader.HttpUrlConnectionDownloader;
import com.mazentop.plugins.cache.CacheName;
import com.mazentop.util.ExcelStyleUtil;
import com.mazentop.util.Helper;
import com.mztframework.FileProperties;
import com.mztframework.cache.Options;
import com.mztframework.data.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service("evaCashBackApply-excel")
public class EvaCashBackApplyExcelServiceImpl implements ExcelService {

    @Autowired
    SendEmail sendEmail;

    @Autowired
    HttpUrlConnectionDownloader downloader;

    @Autowired
    FileProperties fileProperties;

    @Override
    public Result importExcel(HttpServletRequest request, MultipartFile file) throws IOException, Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        List<EvaCashBackApplyEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), EvaCashBackApplyEntity.class, importParams);
        Map<String, Object> map = new HashMap<>(1);
        if (null == list || list.size() == 0) {
            return Result.toast("文件格式有误！");
        } else {
            if (list.size() == 0) {
                return Result.toast("文件格式有误！");
            }
        }
        List<EvaCashBackApplyEntity> entityEmpty = list.stream().filter(item -> StrUtil.isBlank(item.getEvaOrderNo())).collect(Collectors.toList());
        if (!entityEmpty.isEmpty()) {
           return Result.toast("亚马逊订单号不能为空! 请检查 或者删除掉没有订单号的行数");
        }
        int e=0;
        int s=0;
        for (EvaCashBackApplyEntity evaCashBackApplyEntity : list) {
            EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setAmazonOrderNo(evaCashBackApplyEntity.getEvaOrderNo()).get();
            if (Objects.isNull(evaOrdOrder)){
                continue;
            }
            EvaCashBackApply evaCashBackApply = EvaCashBackApply.me().setStatus(2).setEvaOrderId(evaOrdOrder.getId()).get();
            if(!Objects.isNull(evaCashBackApply)){
                if(StrUtil.isNotBlank(evaCashBackApplyEntity.getPaypalAccount())
                        && (StrUtil.isNotBlank(evaCashBackApplyEntity.getPaypalSerialNo())
                        || StrUtil.isNotBlank(evaCashBackApplyEntity.getTransferVoucher()))){
                    evaCashBackApply.setPaypalAccount(evaCashBackApplyEntity.getPaypalAccount());
                    evaCashBackApply.setStatus(4);
                    if (StrUtil.isNotBlank(evaCashBackApplyEntity.getPaypalSerialNo())){
                        evaCashBackApply.setPaypalSerialNo(evaCashBackApplyEntity.getPaypalSerialNo());
                    }
                    if (StrUtil.isNotBlank(evaCashBackApplyEntity.getTransferVoucher())){
                        evaCashBackApply.setTransferVoucher(getImagePath(evaCashBackApplyEntity.getTransferVoucher()));
                    }
                    evaCashBackApply.update();
                    s++;
                }else {
                    e++;
                }
            }
        }
        map.put("count",list.size());
        map.put("error",e);
        map.put("success",s);
        return Result.build(() -> map);
    }


    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = RequestUtil.getValue(request);
            List<EvaCashBackApply> list = EvaCashBackApply.me().setStatus(EvaCashBackApplyStatusEnum.ADOPT.getStatus()).find();
            List<EvaCashBackApplyEntity> entityList = new ArrayList<>();
            list.forEach(evaCashBackApply->{
                EvaCashBackApplyEntity applyEntity = new EvaCashBackApplyEntity();
                BeanUtils.copyProperties(evaCashBackApply,applyEntity);
                applyEntity.setCommentImg(URLUtil.normalize(evaCashBackApply.getCommentImg()));
                CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setId(evaCashBackApply.getFkClienteleId()).get();
                applyEntity.setEmail(clienteleInfo.getEmail());

                if(StringUtils.isNotBlank(evaCashBackApply.getTransferVoucher())) {
                    String url = Options.get("site_image_domin");
                    applyEntity.setTransferVoucher(evaCashBackApply.getTransferVoucher().contains(url) ? evaCashBackApply.getTransferVoucher() : url + evaCashBackApply.getTransferVoucher());
                }
                EvaOrdOrder evaOrdOrder = EvaOrdOrder.me().setId(evaCashBackApply.getEvaOrderId()).get();
                EvaProProduct proProduct = EvaProProduct.me().setProductSku(evaOrdOrder.getProductSku()).get();
                if (Objects.nonNull(proProduct)){
                    SysCountry country = SysCountry.me().setId(proProduct.getCountryId()).get();
                    applyEntity.setCountry(country.getNameCn());
                }
                applyEntity.setDate(DateUtil.format(DateUtil.date(evaOrdOrder.getAddTime()*1000), DatePattern.NORM_DATETIME_PATTERN));
                applyEntity.setEvaOrderNo(evaOrdOrder.getAmazonOrderNo());
                applyEntity.setPhone(clienteleInfo.getPhone());
                applyEntity.setPrice(Helper.transformF2Y(evaOrdOrder.getPrice()).toString());
                applyEntity.setOriginalPrice(Helper.transformF2Y(evaOrdOrder.getOriginalPrice()).toString());
                applyEntity.setRebate(Helper.transformF2Y(evaOrdOrder.getRebate()).toString());
                entityList.add(applyEntity);
            });
            ExportParams params = new ExportParams(null, "返现订单", ExcelType.XSSF);
            params.setStyle(ExcelStyleUtil.class);
            Workbook workbook = ExcelExportUtil.exportExcel(params, EvaCashBackApplyEntity.class, entityList);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            String title = "返现订单导出信息 " + Helper.format(new Date(), Helper.DATE_PATTERN1);
            if (!Objects.isNull(map.get("mail")) && StrUtil.isNotBlank(map.get("mail").toString())) {
                sendEmail.sendEmail(title, map.get("mail").toString(), baos.toByteArray(), "返现订单" + Helper.format(new Date(), Helper.DATE_PATTERN3) + ".xlsx");
            }
            response.setContentType("application/vnd.ms-excel");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
    }

    public String getImagePath(String url){
        File in = FileUtil.file(url);
        String path = fileProperties.getLocalPath()+File.separator + DateUtil.today();
        if(!FileUtil.exist(path)){
            FileUtil.mkdir(path);
        }
        String outUrl=File.separator + DateUtil.today() + File.separator +StrUtil.sub(url,url.lastIndexOf("/")+1,url.length());
        File out = FileUtil.file(path);
        FileUtil.move(in, out,false);
        return fileProperties.getBaseUrl()+ outUrl;
    }
}
