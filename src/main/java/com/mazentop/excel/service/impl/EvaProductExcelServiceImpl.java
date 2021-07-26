package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.entity.*;
import com.mazentop.excel.entity.EvaProProductEntity;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.service.SendEmail;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.emp.dto.ImageDto;
import com.mazentop.modules.emp.dto.query.EvaProductQueryDto;
import com.mazentop.modules.emp.service.ProProductStockService;
import com.mazentop.modules.evaluation.dto.ProProductDto;
import com.mazentop.modules.evaluation.service.EvaProProductService;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
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
import java.util.stream.Collectors;

/**
 * 测评-商品导入导出
 */
@Service("evaProduct-excel")
public class EvaProductExcelServiceImpl implements ExcelService {

    @Autowired
    EvaProProductService productService;

    @Autowired
    ProProductStockService proProductStockService;

    @Autowired
    SendEmail sendEmail;

    /**
     * 导入商品信息
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Result importExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        List<EvaProProductEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), EvaProProductEntity.class, importParams);
        List<ProProductDto> productList = new ArrayList<ProProductDto>();
        Map<String, Object> map = new HashMap<>(1);
        if (list.size() == 0 || Helper.isEmpty(list.get(0).getProductName())) {
            return Result.toast("文件格式有误！");
        } else {
            if (list.size() == 0) {
                return Result.toast("文件格式有误！");
            }
        }
        cache(list, productList, map);
        for (ProProductDto proProductDto : productList) {
            productService.doProProductMasterAddOrUpdate(proProductDto);
        }
        return Result.build(() -> map);
    }


    private void cache(List<EvaProProductEntity> list, List<ProProductDto> productList, Map<String, Object> map) {
        int errorNum = 0;
        String error = null;
        List<EvaProProductEntity> errorList = new ArrayList<EvaProProductEntity>();
        ProProductDto productDto = null;

        int i = 0;
        for (EvaProProductEntity productEntity : list) {
            i++;
            productDto = new ProProductDto();
            if (Helper.isEmpty(productEntity.getProductName())) {
                errorNum++;
                error = "(商品标题)不能为空";
                setError(errorList, error, productEntity);
                continue;
            } else {
                if (productEntity.getProductName().length() > 250) {
                    errorNum++;
                    error = "(商品标题)过长 请限制在250个字符";
                    setError(errorList, error, productEntity);
                    continue;
                }
                productDto.setProductName(productEntity.getProductName());
            }
            if (Helper.isNotEmpty(productEntity.getId())) {
                productDto.setId(productEntity.getId());
            }
            if (Helper.isEmpty(productEntity.getProductPicImageUrl())) {
                errorNum++;
                error = "(商品预览图)不能为空 ";
                setError(errorList, error, productEntity);
                continue;
            }
            if (Helper.isEmpty(productEntity.getIsShelve())) {
                productDto.setIsShelve(Integer.valueOf(0));
            } else {
                if (productEntity.getIsShelve().toUpperCase().equals("Y")) {
                    productDto.setIsShelve(Integer.valueOf(1));
                } else {
                    productDto.setIsShelve(Integer.valueOf(0));
                }
            }
            if (Helper.isNotEmpty(productEntity.getProductDescription())) {
                productDto.setProductDescription(productEntity.getProductDescription());
            }
            if (Helper.isEmpty(productEntity.getProductSku())) {
                productDto.setProductSku(productService.getCode());
            } else {
                productDto.setProductSku(productEntity.getProductSku());
            }
            if (Helper.isNotEmpty(productEntity.getKeyword())) {
                if (productEntity.getKeyword().length() > 200) {
                    errorNum++;
                    error = "(商品关键词)过长 请限制在200个字符";
                    setError(errorList, error, productEntity);
                    continue;
                }
                productDto.setKeywords(productEntity.getKeyword());
            }
            // 商品图片
            if (Helper.isEmpty(productEntity.getProductPicImageUrl())) {
                errorNum++;
                error = "（商品预览图）商品主体预览图不能为空";
                setError(errorList, error, productEntity);
                continue;
            } else {
                List<ImageDto> imageDtos = setImages(productEntity.getProductPicImageUrl());
                productDto.setImages(imageDtos);
            }
            // 商品原价
            if (Helper.isEmpty(productEntity.getOriginalPrice())) {
                errorNum++;
                error = "(商品原价)不能为空";
                setError(errorList, error, productEntity);
                continue;
            } else {
                if (Helper.isNumber(productEntity.getOriginalPrice())) {
                    productDto.setOriginalPriceStr(productEntity.getOriginalPrice());
                } else {
                    errorNum++;
                    error = "(商品原价))只能输入数字或者小数";
                    setError(errorList, error, productEntity);
                    continue;
                }
            }
            // 返现
            if (Helper.isEmpty(productEntity.getRebates())) {
                errorNum++;
                error = "(商品返现)不能为空";
                setError(errorList, error, productEntity);
                continue;
            } else {
                if (Helper.isNumber(productEntity.getOriginalPrice())) {
                    productDto.setRebatesStr(productEntity.getOriginalPrice());
                } else {
                    errorNum++;
                    error = "(商品返现))只能输入数字或者小数";
                    setError(errorList, error, productEntity);
                    continue;
                }
            }
            // 商品售价
            if (Helper.isEmpty(productEntity.getPrice())) {
                errorNum++;
                error = "(商品现现)不能为空";
                setError(errorList, error, productEntity);
                continue;
            } else {
                if (Helper.isNumber(productEntity.getPrice())) {
                    productDto.setPriceStr(productEntity.getPrice());
                } else {
                    errorNum++;
                    error = "(商品售价)只能输入数字或者小数";
                    setError(errorList, error, productEntity);
                    continue;
                }
            }
            // 返现活动总次数
            if (Helper.isEmpty(productEntity.getTrialsTotalTimes())) {
                errorNum++;
                error = "(商品返现活动总次数)不能为空";
                setError(errorList, error, productEntity);
            } else {
                if (Helper.isInteger(productEntity.getTrialsTotalTimes().toString())) {
                    productDto.setTrialsTotalTimes(productEntity.getTrialsTotalTimes());
                } else {
                    errorNum++;
                    error = "(商品返现活动总次数))只能输入数字";
                    setError(errorList, error, productEntity);
                    continue;
                }
            }
            // 返现活动已有次数
            if (Helper.isEmpty(productEntity.getTrialsTimes())) {
                errorNum++;
                error = "(商品返现活动已有次数)不能为空";
                setError(errorList, error, productEntity);
            } else {
                if (Helper.isInteger(productEntity.getTrialsTimes().toString())) {
                    productDto.setTrialsTimes(productEntity.getTrialsTimes());
                } else {
                    errorNum++;
                    error = "(商品返现活动已有次数))只能输入数字";
                    setError(errorList, error, productEntity);
                    continue;
                }
            }
            if (Helper.isNotEmpty(productEntity.getProductSku()) && Helper.isEmpty(productEntity.getId())) {
                Long count = ProProductMaster.me().setProductSku(productEntity.getProductSku()).findCount();
                if (count > 0) {
                    errorNum++;
                    error = "(商品SKU)重复";
                    setError(errorList, error, productEntity);
                    continue;
                }
            }
            setProProduct(productDto, productList);
        }
        if (!errorList.isEmpty()) {
            String key = RandomStringUtils.randomAlphanumeric(20);
            LFUCache.put(key, Helper.toJson(errorList));
            map.put("errorKey", key);
        }
        map.put("error", errorNum);
        map.put("success", productList.size());
    }



    /**
     * 导出商品信息
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = null;
        map = RequestUtil.getValue(request);

        if (Helper.isNotEmpty(map)) {
            String scopes = map.get("scope").toString();
            EvaProductQueryDto queryDto = new EvaProductQueryDto();
            if (scopes.equals("1")) {
                queryDto.setIds(Helper.toList(map.get("selections").toString(), String.class));
            } else if (scopes.equals("2")) {
                EvaProductQueryDto condition = JSONArray.parseObject(map.get("condition").toString(), EvaProductQueryDto.class);
                BeanUtils.copyProperties(condition, queryDto);
            }
            List<EvaProProduct> productList = productService.exportEvaProduct(queryDto);

            List<EvaProProductEntity> exportList = new ArrayList<EvaProProductEntity>();

            productList.forEach(product -> {
                EvaProProductEntity productEntity = new EvaProProductEntity();
                BeanUtils.copyProperties(product, productEntity);

                // 获取商品图片
                List<String> imageList = ProProductImage.me().setFkProductId(product.getId()).find().stream().map(ProProductImage::getProductImageUrl).collect(Collectors.toList());

                String iamge = StringUtils.join(imageList, ",");;
                productEntity.setProductPicImageUrl(iamge);
                String subImage;
                if(iamge.indexOf(",") > -1){
                    subImage = iamge.substring(0, iamge.indexOf(","));
                }else {
                    subImage = iamge;
                }
                productEntity.setStyleImage(subImage.contains("http:") ? subImage : "http:" + subImage);
                if (Helper.isNotEmpty(product.getKeywords())) {
                    productEntity.setKeyword(product.getKeywords());
                }
                productEntity.setPrice(Helper.division(product.getPrice().intValue(), 100));
                productEntity.setOriginalPrice(Helper.division(product.getOriginalPrice().intValue(), 100));
                productEntity.setRebates(Helper.division(product.getRebates().intValue(), 100));

                productEntity.setIsShelve(product.getIsShelve() == 1 ? "Y" : "N");
                exportList.add(productEntity);
            });
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "商品信息", ExcelType.XSSF), EvaProProductEntity.class, exportList);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            String title = "商品导出信息 " + Helper.format(new Date(), Helper.DATE_PATTERN1);
            if(!Objects.isNull(map.get("mail"))) {
                sendEmail.sendEmail(title, map.get("mail").toString(), baos.toByteArray(), "商品信息" + Helper.format(new Date(), Helper.DATE_PATTERN3) + ".xlsx");
            }
            response.setContentType("application/vnd.ms-excel");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
            baos.close();
        }
    }



    private void setError(List<EvaProProductEntity> errorList, String error, EvaProProductEntity dto) {
        dto.setError(error);
        errorList.add(dto);
    }


    private void setProProduct(ProProductDto product, List<ProProductDto> productList) {
        if (product != null) {
            product.setSeo(setSeo(product));
            productList.add(product);
        }
    }



    private static List<ImageDto> setImages(String image) {
        List<ImageDto> images = new ArrayList<ImageDto>();
        String[] split = image.split(",");
        for (String fileName : split) {
            ImageDto dto = new ImageDto();
            dto.setAlt(fileName.substring(fileName.lastIndexOf("/") + 1));
            dto.setUrl(fileName);
            images.add(dto);
        }
        return images;
    }


    private ProSeo setSeo(ProProductDto entity) {
        ProSeo seo = new ProSeo();
        seo.setSeoAddress(entity.getProductName());
        seo.setSeoKeywords(entity.getProductName());
        seo.setSeoDescription(entity.getProductName());
        seo.setSeoTitle(entity.getProductName());
        seo.setIsBranch(BooleanEnum.TRUE.getValue());
        return seo;
    }
}
