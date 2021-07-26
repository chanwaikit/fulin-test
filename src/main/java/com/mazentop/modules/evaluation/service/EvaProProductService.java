package com.mazentop.modules.evaluation.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.mazentop.entity.*;
import com.mazentop.listener.MessageSource;
import com.mazentop.model.*;
import com.mazentop.modules.emp.dto.query.EvaProductQueryDto;
import com.mazentop.modules.emp.dto.query.ProductMasterQueryDto;
import com.mazentop.modules.evaluation.commond.EvaProProductCommond;
import com.mazentop.modules.emp.dto.ImageDto;
import com.mazentop.modules.emp.service.ActCouponService;
import com.mazentop.modules.emp.service.ProProductStockService;
import com.mazentop.modules.emp.service.ProSeoService;
import com.mazentop.modules.evaluation.dto.ProProductDto;
import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mazentop.plugins.event.EventHolder;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import com.mztframework.snowflake.IDSnowflake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mazentop.plugins.event.Message;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: dengy
 * @date: 2020/3/13
 * @description:
 */

@Service
public class EvaProProductService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    ProSeoService proSeoService;

    @Autowired
    ActCouponService actCouponService;

    @Autowired
    SysExchangeRateWebService sysExchangeRateWebService;

    @Autowired
    ProProductStockService stockService;


    /**
     * 新增或修改商品
     * @param proProductDto
     * @return
     */
    public Result doProProductMasterAddOrUpdate(ProProductDto proProductDto) {
        if (Objects.isNull(proProductDto)) {
            return Result.toast("商品信息获取失败!");
        }
        if (null == proProductDto.getImages() ||  proProductDto.getImages().isEmpty()) {
            return Result.toast("商品图片获取失败!");
        }
        Long productCount = EvaProProduct.me().setProductSku(proProductDto.getProductSku()).findCount();
        if (productCount > 0 && Helper.isEmpty(proProductDto.getId())) {
            return Result.toast("商品SKU已存在!");
        }
        String curUserId = Subject.id();

        //商品商城价格金额处理
        handleProductPrice(proProductDto);
        Optional<ImageDto> optionalImageDto = proProductDto.getImages().stream().findFirst();
        optionalImageDto.ifPresent(imageDto -> proProductDto.setProductPicImageUrl(imageDto.getUrl()));
        boolean isUpdate = false;

        // 初始化基础数据
        if (StringUtils.isEmpty(proProductDto.getId())) {
            proProductDto.setId(IDSnowflake.id());
            proProductDto.setAddTime(Utils.currentTimeSecond());
            proProductDto.setAddUserId(curUserId);
            proProductDto.setIsEnable(1);
        //编辑
        } else {
            isUpdate = true;
        }

        if (proProductDto.getIsShelve() == 1) {
            proProductDto.setShelveTime(Utils.currentTimeSecond());
        }
        if (StrUtil.isNotBlank(proProductDto.getAmazonUrl())){
            proProductDto.setAmazonUrl(URLUtil.normalize(proProductDto.getAmazonUrl()));
        }
        proProductDto.setBuyingPatterns(ProductEnum.getKey(proProductDto.getBuyingPatterns()));
        // 购买提示
        if(null != proProductDto.getTipList()){
            proProductDto.setTips(JSON.toJSONString(proProductDto.getTipList()));
        }
        // 订单超时
        SysOptions options = SysOptions.me().setOptionKey("site_order_timing").get();
        if(Objects.nonNull(options)){
            proProductDto.setOrderTiming(Integer.parseInt(options.getOptionValue()));
        }

        if (isUpdate) {
            proProductDto.update();
        } else {
            proProductDto.setSales(0);
            proProductDto.insert();
        }
        // 照片
        addImages(proProductDto, curUserId);
        // 商品分类
        editShortcut(proProductDto.getId(), proProductDto.getTypeIds());

        // seo
        if (Helper.isNotEmpty(proProductDto.getSeo())) {
            proProductDto.getSeo().setSource(proProductDto.getId());
            proSeoService.editProProductSeo(proProductDto.getSeo());
        }

        if (proProductDto.getIsShelve() == 1) {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, proProductDto.getId()));
        }else{
            EventHolder.publishEvent(new Message(MessageSource.PRODUCT_DEL, proProductDto.getId()));
        }

        return Result.success();
    }

    /**
     *  价格处理入库
     * @param dto
     */
    public void handleProductPrice(ProProductDto dto) {
        if (dto.getPriceStr() != null) {
            dto.setPrice(Helper.transformY2F(new BigDecimal(dto.getPriceStr())));
        } else {
            dto.setPrice(0L);
        }
        if (dto.getOriginalPriceStr() != null) {
            dto.setOriginalPrice(Helper.transformY2F(new BigDecimal(dto.getOriginalPriceStr())));
        } else {
            dto.setOriginalPrice(0L);
        }
        // 回扣
        if (dto.getRebatesStr() != null) {
            dto.setRebates(Helper.transformY2F(new BigDecimal(dto.getRebatesStr())));
        } else {
            dto.setRebates(0L);
        }
    }

    /**
     * 处理商品发现活动次数
     * @param productId
     */
    public void doTrialsTimes(String productId) {
        EvaProProduct proProduct = EvaProProduct.me().setId(productId).get();
        if(Objects.nonNull(proProduct)){
            if (proProduct.getTrialsTimes()>Status.NO){
                proProduct.setTrialsTimes(proProduct.getTrialsTimes()-Status.YES).update();
            }else {
                proProduct.setTrialsTimes(Status.NO).update();
            }

        }
    }
    /**
     * 添加商品图片
     * @param proProduct
     */
    public void addImages(ProProductDto proProduct,String curUserId) {
        ProProductImage.me().setFkProductId(proProduct.getId()).delete();
        List<ImageDto> list = proProduct.getImages();
        int i = 0;
        for (ImageDto imageDto : list) {
            ProProductImage image = new ProProductImage();
            image.setFkProductId(proProduct.getId());
            image.setAddTime(Utils.currentTimeSecond());
            image.setAddUserId(curUserId);
            image.setSort(i);
            image.setProductImageUrl(imageDto.getUrl());
            image.setAlt(imageDto.getAlt());
            image.insert();
            i++;
        }
    }


    /**
     * 商品分页查询
     * @param commond
     * @return
     */
    public Page getProProductPage(EvaProProductCommond commond) {
        commond.setOrderBy(" add_time desc");
        commond.setIsEnable(BooleanEnum.TRUE.getValue());
        List<EvaProProduct> list = EvaProProduct.me().ignoreSelectFields(EvaProProduct.F_PRODUCT_DESCRIPTION).find(commond);
        handleProductSku(list);
        return new Page<>(list, commond);
    }


    /**
     * 商品列表
     * @param commond
     * @return
     */
    public List<EvaProProduct> getProProductList(EvaProProductCommond commond) {
        commond.setOrderBy(" add_time desc");
        commond.setIsEnable(BooleanEnum.TRUE.getValue());
        List<EvaProProduct> list = EvaProProduct.me().ignoreSelectFields(EvaProProduct.F_PRODUCT_DESCRIPTION).find(commond);
        handleProductSku(list);
        return list;
    }

    /**
     * 获取商品状态数量
     * @return
     */
    public HashMap<String, Object> getProductStatus() {
        HashMap<String, Object> map = new HashMap<>(3);

        map.put("allCount", EvaProProduct.me().setIsEnable(Status.YES).findCount());
        map.put("onShelfCount", EvaProProduct.me().setIsEnable(Status.YES).setIsShelve(Status.YES).findCount());
        map.put("offShelfCount", EvaProProduct.me().setIsEnable(Status.YES).setIsShelve(Status.NO).findCount());

        return map;
    }


    /**
     * 复制商品
     * @param id
     * @return
     */
    public Result doCopyProduct(String id){
        EvaProProduct proProduct = EvaProProduct.me().setId(id).get();
        if (Helper.isNotEmpty(proProduct)){
            EvaProProduct copyProduct = new EvaProProduct();
            BeanUtils.copyProperties(proProduct,copyProduct);
            copyProduct.setId(null);
            copyProduct.setProductName(Helper.unite(copyProduct.getProductName(),"-copy"));
            copyProduct.setAddTime(Utils.currentTimeSecond());
            copyProduct.setIsEnable(BooleanEnum.TRUE.getValue());
            copyProduct.setIsShelve(BooleanEnum.FALSE.getValue());
            copyProduct.setProductSku(getCode());
            copyProduct.insert();

            // 商品图片
            List<ProProductImage> images = ProProductImage.me().setFkProductId(id).find();
            for (ProProductImage image : images) {
                ProProductImage copyImage = new ProProductImage();
                BeanUtils.copyProperties(image,copyImage);
                copyImage.setId(null);
                copyImage.setFkProductId(copyProduct.getId());
                copyImage.setAddTime(Utils.currentTimeSecond());
                copyImage.insert();
            }

            // 商品分类
            List<ProProductShortcut> shortcuts = ProProductShortcut.me().setFkProductId(id).find();
            for (ProProductShortcut shortcut : shortcuts) {
                ProProductShortcut copyShortcut = new ProProductShortcut();
                BeanUtils.copyProperties(shortcut,copyShortcut);
                copyShortcut.setId(null);
                copyShortcut.setFkProductId(copyProduct.getId());
                copyShortcut.insert();
            }
            // seo
            ProSeo proSeo = ProSeo.me().setSource(id).setType(ProSeoTypeEnum.TYPE_PRODUCT.type()).get();
            ProSeo copySeo = new ProSeo();
            BeanUtils.copyProperties(proSeo,copySeo);
            copySeo.setId(null);
            copySeo.setSource(copyProduct.getId());
            copySeo.setSeoTitle(copyProduct.getProductName());
            proSeoService.editProProductSeo(copySeo);
            return Result.success();
        }
        return Result.toast("暂无数据");
    }

    /**
     * 删除商品
     * @param ids
     * @return
     */
    public R deleteProduct(List<String> ids) {
        if (ids.isEmpty()) {

            return R.toast("参数错误");
        }
        for (String id : ids) {
            EvaProProduct product = EvaProProduct.me().setId(id).get();
            if (!Objects.isNull(product)){
                if (product.getIsShelve().equals(Status.YES)){
                    return R.toast("请先下架产品再删除！");
                }
                product.setIsEnable(Status.NO);
                product.update();
            }
            EventHolder.publishEvent(new Message(MessageSource.PRODUCT_DEL, id));
        }
        return R.ok();
    }


    /**
     * 查询单个商品信息
     * @param productId
     * @return
     */
    public ProProductDto getProductInfo(String productId) {
        EvaProProduct proProduct = EvaProProduct.me().setId(productId).get();
        Objects.requireNonNull(proProduct);

        ProProductDto proProductDto = new ProProductDto();
        BeanUtils.copyProperties(proProduct, proProductDto);
        // 商品图片
        proProductDto.setImages(this.findProductImages(proProduct.getId()));
        // 商品分类
        this.optProductType(proProductDto);

        if (proProductDto.getPrice() != null) {
            proProductDto.setPriceStr(Helper.transformF2Y(proProductDto.getPrice())+"");
        } else {
            proProductDto.setPriceStr(0+"");
        }
        if (proProductDto.getOriginalPrice() != null) {
            proProductDto.setOriginalPriceStr(Helper.transformF2Y(proProductDto.getOriginalPrice())+"");
        } else {
            proProductDto.setOriginalPriceStr(0+"");
        }
        if (proProductDto.getRebates() != null) {
            proProductDto.setRebatesStr(Helper.transformF2Y(proProductDto.getRebates())+"");
        } else {
            proProductDto.setRebatesStr(0+"");
        }

        //获取seo
        proProductDto.setSeo(Optional.ofNullable(proSeoService.getProSeo(productId)).orElse(ProSeo.me()));
        if(!StringUtils.isBlank(proProductDto.getTips())){
            List<String> tips =  JSON.parseArray(proProductDto.getTips(), String.class);
            proProductDto.setTipList(tips);
        }
        return proProductDto;
    }




    public List<ImageDto> findProductImages(String productId) {
        List<ImageDto> imageDtos = new ArrayList<ImageDto>();
        List<ProProductImage> images = ProProductImage.me().setFkProductId(productId).setOrderByFields(Order.asc(ProProductImage.F_SORT)).find();

        images.forEach(image -> imageDtos.add(new ImageDto(image.getProductImageUrl(), image.getAlt())));
        return imageDtos;
    }

    /**
     * 获取产品分类
     *
     * @param proProductDto
     */
    public void optProductType(ProProductDto proProductDto) {
        List<ProProductShortcut> shortcuts = ProProductShortcut.me().setFkProductId(proProductDto.getId()).list();
        proProductDto.setTypeIds(fullProductType(shortcuts));
    }



    /**
     * 修改商品上/下架
     * @param dto
     * @return
     */
    public Result updateIsShelve(ProProductDto dto) {
        if (dto.getIds().isEmpty()) {
            return Result.toast("商品id获取失败!");
        }
        for (String id : dto.getIds()) {
            EvaProProduct proProduct = EvaProProduct.me().setId(id).get();
            // 上架时间不填，影响排序
            if (dto.getIsShelve() == 1) {
                proProduct.setShelveTime(Utils.currentTimeSecond());
            }else{
                proProduct.setShelveTime(null);
            }
            proProduct.setIsShelve(dto.getIsShelve());
            proProduct.update();

        }
        if (dto.getIsShelve() == 1) {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCTS_SELL, dto.getIds()));
        }else {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCTS_DEL, dto.getIds()));
        }
        return Result.success();
    }



    /**
     * 处理商品分类、价格
     * @param productList
     */
    public void handleProductSku(List<EvaProProduct> productList) {
        if (!productList.isEmpty()) {
            productList.forEach(info -> {
                // 处理商品标签
                if(StringUtils.isNotBlank(info.getFkTagId())) {
                    SysDictionary sysDictionary = SysDictionary.me().setId(info.getFkTagId()).get();
                    info.addExten("tagName",sysDictionary.getName());
                    info.addExten("tagValue",sysDictionary.getValue());
                }else{
                    info.addExten("tagName","");
                    info.addExten("tagValue","");
                }
                // 商品分类
                List<ProProductType> types = optProductTypeNodes(info.getId());
                if(Objects.nonNull(types)){
                    StringBuilder typeName = new StringBuilder();
                    types.forEach(type->{
                        if(StringUtils.isNoneBlank(typeName)) {
                            typeName.append("/").append(type.getProductTypeName());
                        }else{
                            typeName.append(type.getProductTypeName());
                        }
                    });
                    SkinCountry country = SkinCountry.me().setCountryCode(info.getCountryId()).get();
                    info.addExten("currency", country.getCurrency());
                    info.addExten("productTypeName",typeName.toString());
                }
                handlePice(info);
            });
        }
    }


    /**
     * 处理商品价格
     * @param product
     */
    private void handlePice(EvaProProduct product) {
        if (null != product.getPrice()) {
            product.addExten("priceStr", Helper.transformF2Y(product.getPrice()));
        } else {
            product.addExten("priceStr", 0);
        }
        if (null != product.getOriginalPrice()) {
            product.addExten("originalPriceStr", Helper.transformF2Y(product.getOriginalPrice()));
        } else {
            product.addExten("originalPriceStr", 0);
        }
        if (null != product.getRebates()) {
            product.addExten("rebatesStr", Helper.transformF2Y(product.getRebates()));
        } else {
            product.addExten("rebatesStr", 0);
        }
    }


    public List<ProProductType> optProductTypeNodes(String productId) {
        ProProductShortcut shortcut = ProProductShortcut.me().setFkProductId(productId).setOrderByFields("id desc").findFirst();
        if(Objects.nonNull(shortcut)) {
            ProProductType proProductType = ProProductType.me().setId(shortcut.getFkProductTypeId()).get();
            if(Objects.nonNull(proProductType)){
                // 获取产品分类路径
                List<ProProductType> proProductTypes = new ArrayList<>();
                proProductTypes.add(proProductType);
                return ThemeUtil.getTypeList(proProductTypes, ProProductType.me().setProductTypeName(Status.TREE_EVALUATION_NODE).get(), proProductType);
            }
        }
        return null;
    }




    /**
     * 随机回去商品sku
     * @return
     */
    public String getCode() {
        return actCouponService.getRandomString(10).toUpperCase();
    }



    /**
     * 添加商品分类
     * @param productId
     * @param typeIds
     */
    private void editShortcut(String productId, List<List<String>> typeIds) {
        if (!Objects.isNull(typeIds)) {
            ProProductShortcut.me().setFkProductId(productId).delete();
            for (List<String> typeIdList : typeIds) {
                ProProductShortcut shortcut = new ProProductShortcut();
                shortcut.setFkProductId(productId);
                shortcut.setFkProductTypeId(typeIdList.get(typeIdList.size()-1));
                shortcut.insert();
            }
        }
    }




    /**
     * 完整路径
     * @param shortcuts
     */
    private List<List<String>> fullProductType (List<ProProductShortcut> shortcuts) {
        List<List<String>> typeIdList = new ArrayList<>();
        shortcuts.forEach(item -> {
            List<String> typeIds = new ArrayList<>();
            String fkProductTypeId = item.getFkProductTypeId();
            typeIds.add(fkProductTypeId);
            typeIdList.add(getParentType(typeIds, fkProductTypeId));
        });
        return typeIdList;
    }

    /**
     * 获取上级目录 集合
     * @param parentId
     * @return
     */
    private List<String> getParentType (List<String> typeIds, String parentId) {
        ProProductType proProductType = ProProductType.me().setId(parentId).get();
        if (!Objects.isNull(proProductType) && !TreeEnum.EVALUATION.getId().equals(proProductType.getParentProductTypeId())) {
            typeIds.add(0,proProductType.getParentProductTypeId());
            getParentType(typeIds,proProductType.getParentProductTypeId());
        }
        return typeIds;
    }


    /**
     * 导出列表
     * @param queryDto
     * @return
     */
    public List<EvaProProduct> exportEvaProduct(EvaProductQueryDto queryDto) {
        queryDto.setIsEnable(Status.YES);
        return  EvaProProduct.me().find(queryDto);
    }



}
