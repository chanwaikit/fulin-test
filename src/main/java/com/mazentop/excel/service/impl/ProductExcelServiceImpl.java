package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.entity.ProProductImage;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProProductSpec;
import com.mazentop.entity.ProSeo;
import com.mazentop.excel.entity.ProProductMasterEntity;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.service.SendEmail;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.emp.dto.ImageDto;
import com.mazentop.modules.emp.dto.ProProductMasterDto;
import com.mazentop.modules.emp.dto.ProProductSpecDto;
import com.mazentop.modules.emp.dto.ProProductStockDto;
import com.mazentop.modules.emp.dto.query.ProductMasterQueryDto;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.modules.emp.service.ProProductStockService;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.*;
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
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

@Service("product-excel")
public class ProductExcelServiceImpl implements ExcelService {

    @Autowired
    ProProductMasterService proProductMasterService;

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
        List<ProProductMasterEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), ProProductMasterEntity.class, importParams);
        List<ProProductMasterDto> proProductMaster = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (list.size() == 0 || Helper.isEmpty(list.get(0).getProductName())) {
            return Result.toast("文件格式有误！");
        } else {
            list.remove(0);
            if (list.size() == 0) {
                return Result.toast("文件格式有误！");
            }
        }
        cache(list, proProductMaster, map);
        for (ProProductMasterDto proProductMasterDto : proProductMaster) {
            proProductMasterService.doProProductMasterAddOrUpdate(proProductMasterDto,"2");
        }
        return Result.build(() -> map);
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
            ProductMasterQueryDto queryDto = new ProductMasterQueryDto();
            if (scopes.equals("1")) {
                queryDto.setIds(Helper.toList(map.get("selections").toString(), String.class));
            } else if (scopes.equals("2")) {
                ProductMasterQueryDto condition = JSONArray.parseObject(map.get("condition").toString(), ProductMasterQueryDto.class);
                BeanUtils.copyProperties(condition, queryDto);
            }
            List<ProProductMaster> productMasterList = proProductMasterService.exportProductMaster(queryDto);

            List<ProProductMasterEntity> exportList = new ArrayList<>();
            ProProductMasterEntity tipInfo = new ProProductMasterEntity();

            // 添加导出头部提示信息
            tipInfo.setId(ProProductMasterEntity.TipInfo.ID_INFO);
            tipInfo.setProductName(ProProductMasterEntity.TipInfo.NAME_INFO);
            tipInfo.setProductSku(ProProductMasterEntity.TipInfo.SKUNAME_INFO);
            tipInfo.setIsShelve(ProProductMasterEntity.TipInfo.STATUS_INFO);
            tipInfo.setProductStockNumber(ProProductMasterEntity.TipInfo.PRODUCT_STOCK_INFO);
            tipInfo.setStyleImage(ProProductMasterEntity.TipInfo.PRODUCT_STYLE_INFO);
            tipInfo.setStyle1(ProProductMasterEntity.TipInfo.SKU1_INFO);
            tipInfo.setStyle2(ProProductMasterEntity.TipInfo.SKU2_INFO);
            tipInfo.setStyle3(ProProductMasterEntity.TipInfo.SKU3_INFO);
            tipInfo.setAttribute(ProProductMasterEntity.TipInfo.PROPERTY_INFO);
            tipInfo.setHeight(ProProductMasterEntity.TipInfo.PRODUCT_HEIGHT_INFO);
            tipInfo.setWidth(ProProductMasterEntity.TipInfo.PRODUCT_WEIGHT_INFO);
            tipInfo.setLength(ProProductMasterEntity.TipInfo.PRODUCT_LENGTH_INFO);
            tipInfo.setProductMallPrice(ProProductMasterEntity.TipInfo.PRICE_INFO);
            tipInfo.setProductMarketPrice(ProProductMasterEntity.TipInfo.PRICE_KET_INFO);
            tipInfo.setNetWeight(ProProductMasterEntity.TipInfo.PRODUCT_NEW_HEIGHT);
            tipInfo.setPrductPicImageUrl(ProProductMasterEntity.TipInfo.PRODUCT_PARTIMG_INFO);
            tipInfo.setProductDescription(ProProductMasterEntity.TipInfo.DESCRIPTION_INFO);
            tipInfo.setProductVideoImageUrl(ProProductMasterEntity.TipInfo.VIDEO_IMAGE_INFO);
            tipInfo.setProductVideoUrl(ProProductMasterEntity.TipInfo.VIDEO_INFO);
            tipInfo.setIsSingleProduct(ProProductMasterEntity.TipInfo.PRODUCT_IS_SINGLE);
            tipInfo.setProductContent(ProProductMasterEntity.TipInfo.TRANSPORTATION_INFO);
            tipInfo.setKeyword(ProProductMasterEntity.TipInfo.PRODUCT_KEYWORD_INFO);
            exportList.add(tipInfo);

            productMasterList.forEach(productMaster -> {
                ProProductMasterEntity productMasterEntity = new ProProductMasterEntity();
                BeanUtils.copyProperties(productMaster, productMasterEntity);

                // 获取商品的sku
                List<ProProductStockDto> skuList = proProductStockService.getSku(productMaster.getId());
                // 获取商品的属性
                List<Map<String, Object>> specList = proProductStockService.getSpec(productMaster.getId());
                // 获取商品图片
                List<String> imageList = ProProductImage.me().setFkProductId(productMaster.getId()).find().stream().map(ProProductImage::getProductImageUrl).collect(Collectors.toList());

                productMasterEntity.setNetWeight(productMaster.getNetWeight() != null ? productMaster.getNetWeight() + "" : 0 + "");
                productMasterEntity.setLength(productMaster.getLength() != null ? productMaster.getLength() + "" : 0 + "");
                productMasterEntity.setWidth(productMaster.getWidth() != null ? productMaster.getWidth() + "" : 0 + "");
                productMasterEntity.setHeight(productMaster.getHeight() != null ? productMaster.getHeight() + "" : 0 + "");
                productMasterEntity.setProductContent(null);
                String iamge = StringUtils.join(imageList, ",");;
                productMasterEntity.setPrductPicImageUrl(iamge);
                String subImage;
                if(iamge.indexOf(",") > -1){
                    subImage = iamge.substring(0, iamge.indexOf(","));
                }else {
                    subImage = iamge;
                }

                productMasterEntity.setStyleImage(subImage.contains("http:") ? subImage : "http:" + subImage);
                if (Helper.isNotEmpty(productMaster.getKeywords())) {
                    productMasterEntity.setKeyword(productMaster.getKeywords());
                }
                productMasterEntity.setIsSingleProduct(productMaster.getIsSingleProduct() == 0 ? "N" : "Y");
                productMasterEntity.setIsShelve(productMaster.getIsShelve() == 1 ? "Y" : "N");
                productMasterEntity.setAttribute("M");
                if (!specList.isEmpty()) {
                    for (int i = 0; i < specList.size(); i++) {
                        if (i == 0) {
                            productMasterEntity.setStyle1(specList.get(i).get("name").toString());
                        }
                        if (i == 1) {
                            productMasterEntity.setStyle2(specList.get(i).get("name").toString());
                        }
                        if (i == 2) {
                            productMasterEntity.setStyle3(specList.get(i).get("name").toString());
                        }
                    }
                }
                exportList.add(productMasterEntity);
                if (!skuList.isEmpty()) {
                    for (ProProductStockDto proProductStockDto : skuList) {
                        ProProductMasterEntity sku = new ProProductMasterEntity();

                        sku.setProductName(productMaster.getProductName());
                        sku.setId(proProductStockDto.getId());
                        sku.setAttribute("P");
                        if (Helper.isNotEmpty(proProductStockDto.getProductStockNumber())) {
                            sku.setProductStockNumber(String.valueOf(proProductStockDto.getProductStockNumber()));
                        }
                        // 循环sku属性
                        List<ProProductSpecDto> proSpecList = proProductStockDto.getList();
                        for (ProProductSpecDto productSpecDto : proSpecList) {
                            if (productMasterEntity.getStyle1() != null && productSpecDto.getSpecName().equals(productMasterEntity.getStyle1())) {
                                 sku.setStyle1(productSpecDto.getProductSpec().getSpecName());
                            }
                            if (productMasterEntity.getStyle2() != null && productSpecDto.getSpecName().equals(productMasterEntity.getStyle2())) {
                                sku.setStyle2(productSpecDto.getProductSpec().getSpecName());
                            }
                            if (productMasterEntity.getStyle3() != null && productSpecDto.getSpecName().equals(productMasterEntity.getStyle3())) {
                                sku.setStyle3(productSpecDto.getProductSpec().getSpecName());
                            }
                        }
                        sku.setProductMallPrice(Helper.division(proProductStockDto.getProductMallPrice().intValue(), 100));
                        sku.setProductMarketPrice(Helper.division(proProductStockDto.getProductMarketPrice().intValue(), 100));
                        sku.setNetWeight(proProductStockDto.getNetWeight() != null ? productMaster.getNetWeight() + "" : 0 + "");
                        if(Helper.isNotEmpty(proProductStockDto.getProductMasterImageUrl())){
                            sku.setStyleImage(proProductStockDto.getProductMasterImageUrl().contains("http:") ? proProductStockDto.getProductMasterImageUrl() : "http:" + proProductStockDto.getProductMasterImageUrl());
                        }
                        if (Helper.isNotEmpty(proProductStockDto.getProductSubSku())) {
                            sku.setProductSku(proProductStockDto.getProductSubSku());
                        }
                        exportList.add(sku);
                    }
                }

            });
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "商品信息", ExcelType.XSSF), ProProductMasterEntity.class, exportList);
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



    private void cache(List<ProProductMasterEntity> list, List<ProProductMasterDto> productMasterList, Map<String, Object> map) {
        int errorNum = 0;
        String error = null;
        List<ProProductMasterEntity> errorList = new ArrayList<ProProductMasterEntity>();
        List<ProProductStockDto> productStockList = null;
        Map<String, String> attributeMap = new HashMap<>(1);
        ProProductMasterDto productMaster = null;

        int i = 0;
        for (ProProductMasterEntity productMasterEntity : list) {
            i++;
            // 商品主体部分
            if (productMasterEntity.getAttribute().toUpperCase().equals("M")) {
                if (productMaster != null) {
                    setProProductMaster(productMaster, productStockList, productMasterList);
                }
                productStockList = new ArrayList<>();
                setAttribute(attributeMap, productMasterEntity);
                productMaster = new ProProductMasterDto();
                if (Helper.isEmpty(productMasterEntity.getProductName())) {
                    errorNum++;
                    error = "(商品标题)不能为空";
                    setError(errorList, error, productMasterEntity);
                    continue;
                } else {
                    if (productMasterEntity.getProductName().length() > 250) {
                        errorNum++;
                        error = "(商品标题)过长 请限制在250个字符";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                    productMaster.setProductName(productMasterEntity.getProductName());
                }
                if (Helper.isNotEmpty(productMasterEntity.getId())) {
                    productMaster.setId(productMasterEntity.getId());
                }
                if (Helper.isEmpty(productMasterEntity.getPrductPicImageUrl())) {
                    errorNum++;
                    error = "(商品预览图)不能为空 ";
                    setError(errorList, error, productMasterEntity);
                    continue;
                }
                if (Helper.isNotEmpty(productMasterEntity.getProductSku())) {
                    productMaster.setProductSku(productMasterEntity.getProductSku());
                }
                if (Helper.isEmpty(productMasterEntity.getIsShelve())) {
                    productMaster.setIsShelve(Integer.valueOf(0));
                } else {
                    if (productMasterEntity.getIsShelve().toUpperCase().equals("Y")) {
                        productMaster.setIsShelve(Integer.valueOf(1));
                    } else {
                        productMaster.setIsShelve(Integer.valueOf(0));
                    }
                }
                productMaster.setIsShowRecommend(0);
                productMaster.setIsShowIndex(0);
                productMaster.setIsShowTop(0);
                if (Helper.isNotEmpty(productMasterEntity.getProductDescription())) {
                    productMaster.setProductDescription(productMasterEntity.getProductDescription());
                }
                if (Helper.isEmpty(productMasterEntity.getProductSku())) {
                    productMaster.setProductSku(proProductMasterService.getCode());
                } else {
                    productMaster.setProductSku(productMasterEntity.getProductSku());
                }
                if (Helper.isNotEmpty(productMasterEntity.getHeight()) && Helper.isNumber(productMasterEntity.getHeight())) {
                    productMaster.setHeight(Helper.toIt(productMasterEntity.getHeight(), Long.class));
                } else {
                    productMaster.setHeight(new Long(0));
                }
                if (Helper.isNotEmpty(productMasterEntity.getWidth()) && Helper.isNumber(productMasterEntity.getWidth())) {
                    productMaster.setWidth(Helper.toIt(productMasterEntity.getWidth(), Long.class));
                } else {
                    productMaster.setWidth(new Long(0));
                }
                if (Helper.isNotEmpty(productMasterEntity.getKeyword())) {
                    if (productMasterEntity.getKeyword().length() > 150) {
                        errorNum++;
                        error = "(商品关键词)过长 请限制在200个字符";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                    productMaster.setKeywords(productMasterEntity.getKeyword());
                }

                if (Helper.isNotEmpty(productMasterEntity.getLength()) && Helper.isNumber(productMasterEntity.getLength())) {
                    productMaster.setLength(Helper.toIt(productMasterEntity.getLength(), Long.class));
                } else {
                    productMaster.setLength(new Long(0));
                }
                if (Helper.isNotEmpty(productMasterEntity.getNetWeight()) && Helper.isNumber(productMasterEntity.getNetWeight())) {
                    productMaster.setNetWeightStr(productMasterEntity.getNetWeight());
                } else {
                    productMaster.setNetWeightStr(String.valueOf(0));
                }
                if (Helper.isEmpty(productMasterEntity.getIsSingleProduct())) {
                    productMaster.setIsSingleProduct(BooleanEnum.TRUE.getValue());
                    productMasterEntity.setIsSingleProduct("Y");
                } else {
                    if (productMasterEntity.getIsSingleProduct().toUpperCase().equals("N")) {
                        productMaster.setIsSingleProduct(BooleanEnum.FALSE.getValue());
                    } else {
                        productMaster.setIsSingleProduct(BooleanEnum.TRUE.getValue());
                    }
                }
                if (Helper.isEmpty(productMasterEntity.getPrductPicImageUrl())) {
                    errorNum++;
                    error = "（商品预览图）商品主体预览图不能为空";
                    setError(errorList, error, productMasterEntity);
                    continue;
                } else {
                    List<ImageDto> imageDtos = setImages(productMasterEntity.getPrductPicImageUrl());
                    productMaster.setImages(imageDtos);
                }
                if (Helper.isNotEmpty(productMasterEntity.getProductContent())) {
                    productMaster.setProductContent(productMasterEntity.getProductContent());
                }
                if (Helper.isNotEmpty(productMasterEntity.getProductVideoUrl())) {
                    if (productMasterEntity.getProductVideoUrl().length() > 400) {
                        errorNum++;
                        error = "(商品视频地址)过长 请限制在400个字符";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                    productMaster.setProductVideoUrl(productMasterEntity.getProductVideoUrl());

                }
                if (Helper.isNotEmpty(productMasterEntity.getProductVideoImageUrl())) {
                    if (productMasterEntity.getProductVideoImageUrl().length() > 400) {
                        errorNum++;
                        error = "(商品视预览图地址)过长 请限制在400个字符";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                    productMaster.setProductVideoImageUrl(productMasterEntity.getProductVideoImageUrl());
                }
                if (Helper.isEmpty(productMasterEntity.getProductMallPrice())) {
                    productMasterEntity.setProductMallPrice(String.valueOf(0));
                } else {
                    if (Helper.isNumber(productMasterEntity.getProductMallPrice())) {
                        productMaster.setProductMallPriceStr(productMasterEntity.getProductMallPrice());
                    } else {
                        errorNum++;
                        error = "(商品商城价格)只能输入数字或者小数";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                }
                if (Helper.isEmpty(productMasterEntity.getProductMarketPrice())) {
                    productMasterEntity.setProductMarketPrice(String.valueOf(0));
                } else {
                    if (Helper.isNumber(productMasterEntity.getProductMarketPrice())) {
                        productMaster.setProductMarketPriceStr(productMasterEntity.getProductMarketPrice());
                    } else {
                        errorNum++;
                        error = "(商品市场价格))只能输入数字或者小数";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                }
                if (Helper.isNotEmpty(productMasterEntity.getProductSku()) && Helper.isEmpty(productMasterEntity.getId())) {
                    Long count = ProProductMaster.me().setProductSku(productMasterEntity.getProductSku()).findCount();
                    if (count > 0) {
                        errorNum++;
                        error = "(商品SKU)重复";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                }
                if (Helper.isNotEmpty(productMasterEntity.getProductStockNumber())) {
                    if (Helper.isInteger(productMasterEntity.getProductStockNumber())) {
                        productMaster.setProductStockNumber(Helper.toIt(productMasterEntity.getProductStockNumber(), Integer.class));
                    } else {
                        errorNum++;
                        error = "(商品库存)列只能输入数字";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                }
                if (Helper.isNotEmpty(productMasterEntity.getNetWeight())) {
                    if (Helper.isNumber(productMasterEntity.getNetWeight())) {
                        productMaster.setNetWeight(Helper.transformY2F(new BigDecimal(productMasterEntity.getNetWeight())));
                    } else {
                        errorNum++;
                        error = "(净重)只能输入数字或小数点";
                        setError(errorList, error, productMasterEntity);
                        continue;
                    }
                } else {
                    productMaster.setNetWeightStr("0");
                }
            // 商品sku部分
            } else if (productMasterEntity.getAttribute().toUpperCase().equals("P")) {
                // 没有主体商品
                if(null == productStockList){
                    errorNum++;
                    error = "(商品属性) 没有商品主体";
                    setError(errorList, error, productMasterEntity);
                }else{
                    setAttributeSpec(productStockList, attributeMap, productMasterEntity);
                }
            } else {
                errorNum++;
                error = "(商品属性) 商品主体（Main）：填入M款式，部分（Part）：填入P （必填M或P，只能有一个M属性和一个或多个P属性）";
                setError(errorList, error, productMasterEntity);
            }
            if (productMasterList.size() > 0 && Helper.isNotEmpty(productMasterEntity.getAttribute()) && (Helper.isEmpty(productMasterEntity.getIsSingleProduct()) || productMasterEntity.getIsSingleProduct().toUpperCase().equals("N"))) {
                String cache = cacheSku(productMasterEntity);
                if (Helper.isNotEmpty(cache)) {
                    errorNum++;
                    error = "cache";
                    setError(errorList, error, productMasterEntity);
                    continue;
                }

            }
            if (i == list.size()) {
                setProProductMaster(productMaster, productStockList, productMasterList);
            }
        }
        if (!errorList.isEmpty()) {
            String key = RandomStringUtils.randomAlphanumeric(20);
            LFUCache.put(key, Helper.toJson(errorList));
            map.put("errorKey", key);
        }
        map.put("error", errorNum);
        map.put("success", productMasterList.size());
    }



    private void setError(List<ProProductMasterEntity> errorList, String error, ProProductMasterEntity dto) {
        dto.setError(error);
        errorList.add(dto);
    }


    private void setProProductMaster(ProProductMasterDto productMaster, List<ProProductStockDto> productStockList, List<ProProductMasterDto> productMasterList) {
        if (productMaster != null) {
            productMaster.setSeo(setSeo(productMaster));
            productMaster.setProProductStock(productStockList);
            productMasterList.add(productMaster);
        }
    }



    private String cacheSku(ProProductMasterEntity entity) {
        if (Helper.isNotEmpty(entity.getStyle1())) {
            if (Helper.isNotEmpty(entity.getStyle2())) {
                if (entity.getStyle1().equals(entity.getStyle2())) {
                    return "款式1与款式2相同";
                }
            }
            if (Helper.isNotEmpty(entity.getStyle3())) {
                if (entity.getStyle1().equals(entity.getStyle3())) {
                    return "款式1与款式3相同";
                }
            }
            if (Helper.isNotEmpty(entity.getStyle2()) && Helper.isNotEmpty(entity.getStyle3())) {
                if (entity.getStyle2().equals(entity.getStyle3())) {
                    return "款式2与款式3相同";
                }
            }
        } else {
            return "款式1必填";
        }
        return null;
    }



    private void setAttribute(Map<String, String> attribute, ProProductMasterEntity entity) {
        attribute.clear();
        if (Helper.isNotEmpty(entity.getStyle1())) {
            attribute.put("style1", entity.getStyle1());
        }
        if (Helper.isNotEmpty(entity.getStyle2())) {
            attribute.put("style2", entity.getStyle2());
        }
        if (Helper.isNotEmpty(entity.getStyle3())) {
            attribute.put("style3", entity.getStyle3());
        }

    }


    /**
     * 商品导入，sku
     * @param productStockList
     * @param attribute
     * @param entity
     */
    private void setAttributeSpec(List<ProProductStockDto> productStockList, Map<String, String> attribute, ProProductMasterEntity entity) {

        ProProductStockDto proProductStockDto = new ProProductStockDto();
        List<ProProductSpecDto> list = new ArrayList<ProProductSpecDto>();
        if (Helper.isNotEmpty(entity.getId())) {
            proProductStockDto.setId(entity.getId());
        }
        if (Helper.isEmpty(entity.getProductMallPrice())) {
            proProductStockDto.setProductMallPriceStr(String.valueOf(0));
        } else {
            proProductStockDto.setProductMallPriceStr(entity.getProductMallPrice());
        }
        if (Helper.isEmpty(entity.getProductMarketPrice())) {
            proProductStockDto.setProductMarketPriceStr(String.valueOf(0));
        } else {
            proProductStockDto.setProductMarketPriceStr(entity.getProductMarketPrice());
        }
        if (Helper.isNotEmpty(entity.getProductStockNumber())) {
            proProductStockDto.setProductStockNumber(Integer.valueOf(entity.getProductStockNumber()));
        } else {
            proProductStockDto.setProductStockNumber(Integer.valueOf(0));
        }
        if (Helper.isNotEmpty(entity.getPrductPicImageUrl())) {
            proProductStockDto.setProductMasterImageUrl(entity.getPrductPicImageUrl());
        }
        if (Helper.isEmpty(entity.getProductSku())) {
            proProductStockDto.setProductSubSku(proProductMasterService.getCode());
        } else {
            proProductStockDto.setProductSubSku(entity.getProductSku());
        }
        proProductStockDto.setIsEnable(BooleanEnum.TRUE.getValue());
        if (Helper.isNotEmpty(entity.getNetWeight())) {
            proProductStockDto.setNetWeight(Long.valueOf(entity.getNetWeight()));
        } else {
            proProductStockDto.setNetWeight(Long.valueOf(0));
        }
        if (Helper.isNotEmpty(entity.getStyle1())) {
            ProProductSpecDto specDto = new ProProductSpecDto();
            ProProductSpec spec = new ProProductSpec();
            spec.setSpecName(entity.getStyle1());
            specDto.setProductSpec(spec);
            specDto.setSpecName(attribute.get("style1"));
            list.add(specDto);
        }
        if (Helper.isNotEmpty(entity.getStyle2())) {
            ProProductSpecDto specDto = new ProProductSpecDto();
            ProProductSpec spec = new ProProductSpec();
            spec.setSpecName(entity.getStyle2());
            specDto.setProductSpec(spec);
            specDto.setSpecName(attribute.get("style2"));
            list.add(specDto);
        }
        if (Helper.isNotEmpty(entity.getStyle3())) {
            ProProductSpecDto specDto = new ProProductSpecDto();
            ProProductSpec spec = new ProProductSpec();
            spec.setSpecName(entity.getStyle3());
            specDto.setProductSpec(spec);
            specDto.setSpecName(attribute.get("style3"));
            list.add(specDto);
        }
        proProductStockDto.setList(list);
        productStockList.add(proProductStockDto);
    }



    private static List<ImageDto> setImages(String image) {
        List<ImageDto> images = new ArrayList<>();
        String[] split = image.split(",");
        for (String fileName : split) {
            ImageDto dto = new ImageDto();
            dto.setAlt(fileName.substring(fileName.lastIndexOf("/") + 1));
            dto.setUrl(fileName);
            images.add(dto);
        }
        return images;
    }


    private ProSeo setSeo(ProProductMasterDto entity) {
        ProSeo seo = new ProSeo();
        seo.setSeoAddress(entity.getProductName());
        seo.setSeoKeywords(entity.getProductName());
        seo.setSeoDescription(entity.getProductName());
        seo.setSeoTitle(entity.getProductName());
        seo.setIsBranch(BooleanEnum.TRUE.getValue());
        return seo;
    }
}
