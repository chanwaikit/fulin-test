package com.mazentop.modules.emp.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.listener.MessageSource;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.model.Status;
import com.mazentop.model.TreeEnum;
import com.mazentop.modules.emp.commond.ProProductMasterCommond;
import com.mazentop.modules.emp.dto.ImageDto;
import com.mazentop.modules.emp.dto.ProProductMasterDto;
import com.mazentop.modules.emp.dto.ProProductStockDto;
import com.mazentop.modules.emp.dto.ProductMasterIsShelveDto;
import com.mazentop.modules.emp.dto.query.ProductMasterQueryDto;
import com.mazentop.modules.emp.warpper.Article;
import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mazentop.plugins.event.EventHolder;
import com.mazentop.plugins.event.Message;
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
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: dengy
 * @date: 2020/3/13
 * @description:
 */

@Service
public class ProProductMasterService {

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


    public ProProductMaster getProductMaster(String productId) {
        return ProProductMaster.me().setId(productId).get();
    }

    /**
     * 查商品主表
     * @param commond
     * @return
     */
    public Page getProductPage(ProProductMasterCommond commond) {
        commond.setIsEnable(Status.YES);
        List<ProProductMaster> list = ProProductMaster.me().find(commond);
        // 查每个商品的平均
        Map<String,Object> map = new HashMap<>(1);
        for (ProProductMaster proProductMaster : list) {
            map.clear();

            String sql = "select sum(range_num) from pro_comment WHERE fk_product_id = :productId";
            map.put("productId",proProductMaster.getId());
            long rangeNum = baseDao.queryForLong(sql, map);

            // 评论数量
            Long count = ProComment.me().setFkProductId(proProductMaster.getId()).findCount();
            if(count > 0){
                proProductMaster.addExten("avgRate",rangeNum/count);
            }else{
                proProductMaster.addExten("avgRate",0);
            }
        }
        return new Page<>(list, commond);
    }


    /**
     * 获取商品状态数量
     * @return
     */
    public HashMap<String, Object> getProductStatus() {
        HashMap<String, Object> map = new HashMap<>(3);

        map.put("allCount", ProProductMaster.me().setIsEnable(Status.YES).findCount());
        map.put("onShelfCount", ProProductMaster.me().setIsEnable(Status.YES).setIsShelve(Status.YES).findCount());
        map.put("offShelfCount", ProProductMaster.me().setIsEnable(Status.YES).setIsShelve(Status.NO).findCount());

        return map;
    }

    /**
     * 分页查询
     * @param proProductMasterCommond
     * @return
     */
    public Page proProductMasterList(ProProductMasterCommond proProductMasterCommond) {
        Map<String, Object> param = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select p.* from pro_product_master p");

        if (Helper.isNotEmpty(proProductMasterCommond.getFkProductTypeId())) {
            sql.append(" left join pro_product_shortcut s on s.fk_product_id=p.id ");
        }
        sql.append(" where 1=1 and p.is_enable = 1 ");

        if (Helper.isNotEmpty(proProductMasterCommond.getFkProductTypeId())) {
            sql.append(" and s.fk_product_type_id=:productTypeId");
            param.put("productTypeId", proProductMasterCommond.getFkProductTypeId());
        }
        if (Helper.isNotEmpty(proProductMasterCommond.getProductName())) {
            sql.append(" and p.product_name like :productName ");
            param.put("productName", "%"+proProductMasterCommond.getProductName()+"%");
        }
        if(Helper.isNotEmpty(proProductMasterCommond.getProductSku())){
            sql.append(" and p.product_sku like :productSku or p.id in (select s.fk_product_id from pro_product_stock s where s.product_sub_sku like :productSku GROUP BY s.fk_product_id)");
            param.put("productSku", "%"+proProductMasterCommond.getProductSku()+"%");
        }
        if (Helper.isNotEmpty(proProductMasterCommond.getIsShelve())) {
            sql.append(" and p.is_shelve=:isShelve");
            param.put("isShelve", proProductMasterCommond.getIsShelve());
        }
        if(Helper.isNotEmpty(proProductMasterCommond.getFkTagId())){
            sql.append(" and p.fk_tag_id=:fkTagId");
            param.put("fkTagId", proProductMasterCommond.getFkTagId());
        }
        if(StringUtils.isNotBlank(proProductMasterCommond.getId())){
            sql.append(" and p.id=:id");
            param.put("id", proProductMasterCommond.getId());
        }
        sql.append(" order By add_time desc");
        List<Map<String, Object>> list = baseDao.paginate(sql.toString(), param, proProductMasterCommond);
        handleProductSku(list);
        return new Page<>(list, proProductMasterCommond);
    }

    /**
     * 处理多属性商品的价格及库存【价格给区间值，库存相加】
     * @param productList
     */
    public void handleProductSku(List<Map<String, Object>> productList) {
        if (!productList.isEmpty()) {
            productList.forEach(info -> {
                List<ProProductStock> stockList = ProProductStock.me().setFkProductId(info.get("id").toString()).find();
                List<Long> price = new ArrayList<>();
                List<Long> marketPrice = new ArrayList<Long>();
                int stockNumber = 0;
                for (ProProductStock stock : stockList) {
                    price.add(stock.getProductMallPrice());
                    marketPrice.add(stock.getProductMarketPrice());
                    if (stock.getProductStockNumber() != null) {
                        stockNumber += stock.getProductStockNumber();
                    }
                }
                // 处理商品标签
                if(StringUtils.isNotBlank(info.get("fk_tag_id").toString())) {
                    SysDictionary sysDictionary = SysDictionary.me().setId(info.get("fk_tag_id").toString()).get();
                    info.put("tagName",sysDictionary.getName());
                    info.put("tagValue",sysDictionary.getValue());
                }else{
                    info.put("tagName","");
                    info.put("tagValue","");
                }
                // 商品分类
                List<ProProductType> types = optProductTypeNodes(info.get("id").toString());
                if(Objects.nonNull(types)){
                    StringBuilder typeName = new StringBuilder();
                    types.forEach(type->{
                        if(StringUtils.isNoneBlank(typeName)) {
                            typeName.append("/").append(type.getProductTypeName());
                        }else{
                            typeName.append(type.getProductTypeName());
                        }
                    });
                    info.put("productTypeName",typeName.toString());
                }

                handlePice(info, price, marketPrice, stockNumber);
            });
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
                return ThemeUtil.getTypeList(proProductTypes, ProProductType.me().setProductTypeName(Status.TREE_ROOT_NODE).get(), proProductType);
            }
        }
        return null;
    }

    /**
     * 处理多属性商品的
     * @param info
     * @param price
     * @param marketPrice
     * @param stockNumber
     */
    private void handlePice(Map<String, Object> info, List<Long> price, List<Long> marketPrice, int stockNumber) {
        info.put("productStockNumber", stockNumber);
        if (!price.isEmpty()) {
            info.put("productMallPriceMax", Helper.transformF2Y(Collections.max(price)).toString());
            info.put("productMallPriceMin", Helper.transformF2Y(Collections.min(price)).toString());
        } else {
            info.put("productMallPriceMax", 0);
            info.put("productMallPriceMin", 0);
        }
        if (!marketPrice.isEmpty()) {
            info.put("productMarketPriceMax", Helper.transformF2Y(Collections.max(marketPrice)).toString());
            info.put("productMarketPriceMin", Helper.transformF2Y(Collections.min(marketPrice)).toString());
        } else {
            info.put("productMarketPriceMax", 0);
            info.put("productMarketPriceMin", 0);
        }
    }


    /**
     * 新增或修改商品
     * @param proProductMaster
     * @param operateType - 操作类型 1-页面修改 2-导入修改
     * @return
     */
    public Result doProProductMasterAddOrUpdate(ProProductMasterDto proProductMaster,String operateType) {
        if (Objects.isNull(proProductMaster)) {
            return Result.toast("商品信息获取失败!");
        }
        if (proProductMaster.getImages().isEmpty()) {
            return Result.toast("商品图片获取失败!");
        }
        Long productMasterCount = ProProductMaster.me().setProductSku(proProductMaster.getProductSku()).findCount();
        if (productMasterCount > 0 && Helper.isEmpty(proProductMaster.getId())) {
            return Result.toast("商品SKU已存在!");
        }
        String curUserId = Subject.id();

        proProductMaster.setTotalVol(Helper.transformY2F(new BigDecimal(proProductMaster.getLength() * proProductMaster.getWidth() * proProductMaster.getHeight())));

        //商品商城价格金额处理
        handleProductPrice(proProductMaster);
        Optional<ImageDto> optionalImageDto = proProductMaster.getImages().stream().findFirst();
        optionalImageDto.ifPresent(imageDto -> proProductMaster.setPrductPicImageUrl(imageDto.getUrl()));
        boolean isUpdate = false;

        // 初始化基础数据
        if (StringUtils.isEmpty(proProductMaster.getId())) {
            proProductMaster.setId(IDSnowflake.id());
            proProductMaster.setAddTime(Utils.currentTimeSecond());
            proProductMaster.setAddUserId(curUserId);
            proProductMaster.setIsEnable(1);
            SysOptions sysOptions = SysOptions.me().setOptionKey("site_setting_currency").get();
            if (!Objects.isNull(sysOptions)) {
                proProductMaster.setCurrency(sysOptions.getOptionValue());
            }
        //编辑
        } else {
            isUpdate = true;
        }

        if (proProductMaster.getIsShelve() == 1) {
            proProductMaster.setShelveTime(Utils.currentTimeSecond());
        }

        // 多属性商品
        if (BooleanEnum.FALSE.getValue().equals(proProductMaster.getIsSingleProduct())) {
            stockService.editProProductStock(proProductMaster.getProProductStock(), proProductMaster.getId());
        // 单属性商品
        }else {
            // 添加库存sku数据
            if(operateType.equals("1")){
               addMasterStock(proProductMaster);
            }else{
               addMasterSkuByImport(proProductMaster.getProProductStock(), proProductMaster);
            }
        }
        if(null != proProductMaster.getExplain()){
            proProductMaster.setExplainData(JSON.toJSONString(proProductMaster.getExplain()));
        }
        if (isUpdate) {
            proProductMaster.update();
        } else {
            proProductMaster.setSales(0);
            proProductMaster.insert();
        }
        addImages(proProductMaster, curUserId);
        editShortcut(proProductMaster.getId(), proProductMaster.getTypeIds());

        // seo
        if (Helper.isNotEmpty(proProductMaster.getSeo())) {
            proProductMaster.getSeo().setSource(proProductMaster.getId());
            proSeoService.editProProductSeo(proProductMaster.getSeo());
        }
        return Result.success();
    }



    // 金额乘百入库
    public void handleProductPrice(ProProductMasterDto proProductMaster) {
        if (proProductMaster.getCusPrice() != null) {
            proProductMaster.setCusPrice(Helper.transformY2F(new BigDecimal(proProductMaster.getCusPriceStr())));
        } else {
            proProductMaster.setCusPrice(0L);
        }
    }

    /**
     *  添加商品库存sku
     * @param stockDtoList
     */
    public void addMasterSkuByImport(List<ProProductStockDto> stockDtoList,ProProductMasterDto proProductMaster) {
        if (null == stockDtoList || stockDtoList.size() == 0) {
            return;
        }
        for (ProProductStockDto productStock :stockDtoList) {

            if (StringUtils.isBlank(proProductMaster.getId())) {
                productStock.setIsEnable(BooleanEnum.TRUE.getValue());
                productStock.setAddUserId(Subject.id());
            }
            productStock.setFkProductId(proProductMaster.getId());
            productStock.setProductMallPrice(Helper.transformY2F(new BigDecimal(productStock.getProductMallPriceStr())));
            productStock.setProductMarketPrice(Helper.transformY2F(new BigDecimal(productStock.getProductMarketPriceStr())));
            productStock.insertOrUpdate();
        }
    }


    /**
     *  添加商品库存sku
     * @param proProductMaster
     */
    public void addMasterStock(ProProductMasterDto proProductMaster) {
        ProProductStock productStock = ProProductStock.me().setProductSubSku(proProductMaster.getProductSku()).setIsEnable(BooleanEnum.TRUE.getValue()).get();
        if(Objects.isNull(productStock)){
            productStock = new ProProductStock();
            productStock.setIsEnable(BooleanEnum.TRUE.getValue());
            productStock.setAddUserId(Subject.id());
            productStock.setAddTime(Utils.currentTimeSecond());
        }
        productStock.setFkProductId(proProductMaster.getId());
        productStock.setProductMallPrice(Helper.transformY2F(new BigDecimal(proProductMaster.getProductMallPriceStr())));
        productStock.setProductMarketPrice(Helper.transformY2F(new BigDecimal(proProductMaster.getProductMarketPriceStr())));
        productStock.setNetWeight(Long.valueOf(proProductMaster.getNetWeightStr()));
        productStock.setProductMasterImageUrl(proProductMaster.getPrductPicImageUrl());
        productStock.setBarCode(proProductMaster.getProductBarcode());
        productStock.setProductSubSku(proProductMaster.getProductSku());
        productStock.setProductStockNumber(proProductMaster.getProductStockNumber());
        productStock.insertOrUpdate();
    }


    /**
     * 添加商品图片
     * @param proProductMaster
     */
    public void addImages(ProProductMasterDto proProductMaster,String curUserId) {
        ProProductImage.me().setFkProductId(proProductMaster.getId()).delete();
        List<ImageDto> list = proProductMaster.getImages();
        int i = 0;
        for (ImageDto imageDto : list) {
            ProProductImage image = new ProProductImage();
            image.setFkProductId(proProductMaster.getId());
            image.setAddTime(Utils.currentTimeSecond());
            image.setAddUserId(curUserId);
            image.setSort(i);
            image.setProductImageUrl(imageDto.getUrl());
            image.setAlt(imageDto.getAlt());
            image.insert();
            i++;
        }
    }


    public List<ImageDto> findProductImages(ProProductMaster proProductMaster) {
        List<ImageDto> imageDtos = new ArrayList<ImageDto>();
        List<ProProductImage> images = ProProductImage.me().setFkProductId(proProductMaster.getId()).setOrderByFields(Order.asc(ProProductImage.F_SORT)).find();

        images.forEach(image -> imageDtos.add(new ImageDto(image.getProductImageUrl(), image.getAlt())));
        return imageDtos;
    }



    // 金额处理
    public void optProductPrice(ProProductMaster proProductMaster) {
        List<ProProductStock> stockList = ProProductStock.me().setFkProductId(proProductMaster.getId()).find();
        List<Long> price = new ArrayList<Long>();
        stockList.forEach(stock -> price.add(stock.getProductMallPrice()));
        if (price.isEmpty()) {
            proProductMaster.addExten("productMallPriceMax", 0);
            proProductMaster.addExten("productMallPriceMin", 0);
        } else {
            proProductMaster.addExten("productMallPriceMax", Helper.transformF2Y(Collections.max(price)).toString());
            proProductMaster.addExten("productMallPriceMin", Helper.transformF2Y(Collections.min(price)).toString());
        }
    }


    /**
     * 查询单个商品信息
     * @param productId
     * @return
     */
    public ProProductMasterDto getProductMasterInfo(String productId) {
        ProProductMaster proProductMaster = ProProductMaster.me().setId(productId).get();
        Objects.requireNonNull(proProductMaster);

        ProProductMasterDto proProductMasterDto = new ProProductMasterDto();
        BeanUtils.copyProperties(proProductMaster, proProductMasterDto);
        // 商品图片
        proProductMasterDto.setImages(this.findProductImages(proProductMaster));
        // 商品价格
//            this.optProductPrice(proProductMasterDto);
        // 商品分类
        this.optProductType(proProductMasterDto);

        // 单一款式
        if(proProductMaster.getIsSingleProduct() == 1){
            this.handleSingleSku(proProductMasterDto);
            // 多款式
        }else{
            this.getProColorOrSize(proProductMasterDto);
        }
        //获取seo
        proProductMasterDto.setSeo(Optional.ofNullable(proSeoService.getProSeo(productId)).orElse(ProSeo.me()));
        if(!StringUtils.isBlank(proProductMaster.getExplainData())){
            List<String> explain =  JSON.parseArray(proProductMaster.getExplainData(), String.class);
            proProductMasterDto.setExplain(explain);
        }
        return proProductMasterDto;
    }


    /**
     * 删除商品
     * @param ids
     * @return
     */
    public R doDeleteProductMaster(List<String> ids) {
        if (ids.isEmpty()) {
            return R.toast("参数错误");
        }
        for (String id : ids) {
            ProProductMaster.me().setId(id).delete();
            ProProductStock.me().setFkProductId(id).delete();
            ProProductShortcut.me().setFkProductId(id).delete();
            ProProductImage.me().setFkProductId(id).delete();
            ProSeo.me().setSource(id).delete();
            ProProductSecInfo.me().setFkProductMasterId(id).delete();
            EventHolder.publishEvent(new Message(MessageSource.PRODUCT_DEL, id));
            // 删除商品的其他数据
            // 活动商品数据
            ActPromotionProduct.me().setFkProductId(id).delete();
            // 商品评论数据
            ProComment.me().setFkProductId(id).delete();
            //商品问答数据
            ProProductQa.me().setFkProductId(id).delete();

        }
        return R.ok();
    }



    /**
     * 处理单一款式sku
     * @param proProductMasterDto
     */
    public void handleSingleSku(ProProductMasterDto proProductMasterDto) {
        ProProductStock productStock = ProProductStock.me().setFkProductId(proProductMasterDto.getId()).get();
        if(Objects.nonNull(productStock)){
            proProductMasterDto.setNetWeightStr(productStock.getNetWeight()+"");
            proProductMasterDto.setProductMallPrice(Helper.transformF2Y(productStock.getProductMallPrice()).toString());
            proProductMasterDto.setProductMarketPrice(Helper.transformF2Y(productStock.getProductMarketPrice()).toString());
            proProductMasterDto.setProductStockNumber(productStock.getProductStockNumber());
            proProductMasterDto.setBarCode(productStock.getBarCode());
        }

    }


    /**
     * 商品详情，处理商品sku及属性
     * @param proProductMasterDto
     * @return
     */
    public ProProductMasterDto getProColorOrSize(ProProductMasterDto proProductMasterDto) {
        //查询商品规格库存信息
        String sql = "select * from pro_product_stock where 1=1 and fk_product_id = :productId and is_enable = 1";
        Map<String, Object> param = new HashMap<>(2);
        param.put("productId", proProductMasterDto.getId());
        List<Map<String, Object>> skuList = baseDao.queryForList(sql, param);

        //获取商品属性
        String parentSql = "select spec.id, spec.parent_id, spec.fk_product_id, spec.spec_name as name, spec.spec_value from pro_product_spec spec,pro_product_sec_info info where spec.id = info.fk_parent_spec_id and  info.fk_product_master_id =:productId GROUP BY info.fk_parent_spec_id ORDER BY info.id asc";
        List<Map<String, Object>> parentSpecList = baseDao.queryForList(parentSql, param);
        if (parentSpecList.size() > 0) {
            parentSpecList.forEach(parentSpec -> {
                // 获取属性下面的属性值
                param.clear();
                String specSql = "select spec.id, spec.parent_id as fkParentSpecId, spec.fk_product_id, spec.spec_name as name, spec.spec_value from pro_product_spec spec,pro_product_sec_info info where spec.id = info.fk_spec_id and info.fk_product_master_id =:productId and fk_parent_spec_id =:parentId GROUP BY info.fk_spec_id ORDER BY info.id asc";
                param.put("productId", proProductMasterDto.getId());
                param.put("parentId", parentSpec.get("id").toString());
                List<Map<String, Object>> tagList = baseDao.queryForList(specSql, param);
                parentSpec.put("tagList", tagList);
                parentSpec.put("flag",0);
                if("Color".equals(parentSpec.get("name").toString())){
                    parentSpec.put("flag",1);
                }
            });
        }
        if (!skuList.isEmpty()) {
            for (Map<String, Object> colorOrSizeMap : skuList) {
                if (null != colorOrSizeMap.get("product_mall_price")) {
                    colorOrSizeMap.put("product_mall_price", Helper.transformF2Y(colorOrSizeMap.get("product_mall_price")));
                } else {
                    colorOrSizeMap.put("product_mall_price", 0);
                }
                if (null != colorOrSizeMap.get("product_market_price")) {
                    colorOrSizeMap.put("product_market_price", Helper.transformF2Y(colorOrSizeMap.get("product_market_price")));
                } else {
                    colorOrSizeMap.put("product_market_price", 0);
                }
                String secSql = " select fk_spec_id as id from pro_product_sec_info where 1=1 and fk_stock_id = :stockId ORDER BY fk_parent_spec_id";
                Map<String, Object> specParam = new HashMap<>();
                specParam.put("stockId", colorOrSizeMap.get("id"));
                List<Map<String, Object>> propertyList = baseDao.queryForList(secSql, specParam);
                propertyList.forEach(map -> {
                    ProProductSpec tagSpec = ProProductSpec.me().setId(map.get("id").toString()).get();
                    map.put("value", tagSpec.getSpecName());
                    map.put("id", tagSpec.getId());
                    map.put("parentId", tagSpec.getParentId());
                    map.put("flag", 0);
                    map.put("colorCode", "");
                    if (null != colorOrSizeMap.get("color")) {
                        Long count = ProProductColor.me().setId(colorOrSizeMap.get("color").toString()).setColorName(tagSpec.getSpecName()).findCount();
                        if (count > 0) {
                            ProProductColor proProductColor = ProProductColor.me().setId(colorOrSizeMap.get("color").toString()).setColorName(tagSpec.getSpecName()).get();
                            map.put("flag", 1);
                            map.put("colorCode", proProductColor.getColor());
                        }
                    }
                    Map<String, Object> name = new HashMap<>();
                    ProProductSpec parent = ProProductSpec.me().setId(tagSpec.getParentId()).get();
                    name.put("id", parent.getId());
                    name.put("name", parent.getSpecName());
                    map.put("name", name);
                });
                colorOrSizeMap.put("propertyList", propertyList);
            }
        }
        proProductMasterDto.setColorSizeList(skuList);
        proProductMasterDto.setParentList(parentSpecList);
        return proProductMasterDto;
    }


    /**
     *  删除商品单条sku信息
     * @param id
     * @return
     */
    public Result doDeleteProductColorOrSize(String id) {
        ProProductStock.me().setId(id).setIsEnable(1).update();
        return Result.success();
    }


    /**
     * 随机回去商品sku
     * @return
     */
    public String getCode() {
        return actCouponService.getRandomString(10).toUpperCase();
    }

    /**
     * 修改商品上/下架
     * @param productMasterIsShelveDto
     * @return
     */
    public Result updateIsShelve(ProductMasterIsShelveDto productMasterIsShelveDto) {
        if (productMasterIsShelveDto.getIds().isEmpty()) {
            return Result.toast("商品id获取失败!");
        }
        for (String id : productMasterIsShelveDto.getIds()) {
            ProProductMaster proProductMaster = ProProductMaster.me().setId(id).get();
            // 上架时间不填，影响排序
            if (productMasterIsShelveDto.getIsShelve() == 1) {
                proProductMaster.setShelveTime(Utils.currentTimeSecond());
            }else{
                proProductMaster.setShelveTime(null);
            }
            proProductMaster.setIsShelve(productMasterIsShelveDto.getIsShelve());
            proProductMaster.update();

        }
        if (productMasterIsShelveDto.getIsShelve() == 1) {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCTS_SELL, productMasterIsShelveDto.getIds()));
        }
        if (productMasterIsShelveDto.getIsShelve() == 0) {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCTS_DEL, productMasterIsShelveDto.getIds()));
        }
        return Result.success();
    }



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
     * 产品分类
     *
     * @param proProductMasterDto
     */
    public void optProductType(ProProductMasterDto proProductMasterDto) {
        List<ProProductShortcut> shortcuts = ProProductShortcut.me().setFkProductId(proProductMasterDto.getId()).list();
        proProductMasterDto.setTypeIds(fullProductType(shortcuts));
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
        if (!Objects.isNull(proProductType) && !TreeEnum.ROOT.getId().equals(proProductType.getParentProductTypeId())) {
            typeIds.add(0,proProductType.getParentProductTypeId());
            getParentType(typeIds,proProductType.getParentProductTypeId());
        }
        return typeIds;
    }


    /**
     * 批量修改商品销售量
     * @param ids
     * @param sales
     * @return
     */
    public Result updateSales(List<String> ids, Integer sales) {
        List<Integer> random = null;
        if (ids.size() > 1 && sales > ids.size()) {
            random = Helper.random(sales, ids.size());
        }
        for (int i = 0; i < ids.size(); i++) {
            ProProductMaster proProductMaster = ProProductMaster.me().setId(ids.get(i)).get();
            if (proProductMaster.getSales() == null) {
                proProductMaster.setSales(BooleanEnum.FALSE.getValue());
            }
            if (random != null) {
                proProductMaster.setSales(proProductMaster.getSales() + random.get(i));
            } else {
                proProductMaster.setSales(proProductMaster.getSales() + sales);
            }
            proProductMaster.update();

        }
        EventHolder.publishEvent(new Message(MessageSource.PRODUCTS_DEL, ids));
        EventHolder.publishEvent(new Message(MessageSource.PRODUCTS_SELL, ids));
        return Result.success();
    }


    /**
     * 复制商品
     * @param id
     * @return
     */
    public Result doCopyProduct(String id){
        ProProductMaster proProductMaster = ProProductMaster.me().setId(id).get();
        if (Helper.isNotEmpty(proProductMaster)){
            ProProductMaster master = new ProProductMaster();
            BeanUtils.copyProperties(proProductMaster,master);
            master.setId(null);
            master.setProductName(Helper.unite(proProductMaster.getProductName(),"-copy"));
            master.setAddTime(Utils.currentTimeSecond());
            master.setIsEnable(BooleanEnum.TRUE.getValue());
            master.setIsShelve(BooleanEnum.FALSE.getValue());
            master.setProductSku(getCode());
            master.insert();

            List<ProProductImage> images = ProProductImage.me().setFkProductId(id).find();
            for (ProProductImage image : images) {
                ProProductImage copyImage = new ProProductImage();
                BeanUtils.copyProperties(image,copyImage);
                copyImage.setId(null);
                copyImage.setFkProductId(master.getId());
                copyImage.setAddTime(Utils.currentTimeSecond());
                copyImage.insert();
            }

            List<ProProductShortcut> shortcuts = ProProductShortcut.me().setFkProductId(id).find();
            for (ProProductShortcut shortcut : shortcuts) {
                ProProductShortcut copyShortcut = new ProProductShortcut();
                BeanUtils.copyProperties(shortcut,copyShortcut);
                copyShortcut.setId(null);
                copyShortcut.setFkProductId(master.getId());
                copyShortcut.insert();
            }

            List<ProProductStock> stocks = ProProductStock.me().setFkProductId(proProductMaster.getId()).setIsEnable(BooleanEnum.TRUE.getValue()).find();
            Map<String,String> map=new HashMap<>(1);
            for (ProProductStock stock : stocks) {
                ProProductStock copSku = new ProProductStock();
                BeanUtils.copyProperties(stock,copSku);
                if(copSku.getProductSubSku().contains(proProductMaster.getProductSku())){
                    if(master.getIsSingleProduct() == 0b0) {
                        String sku = copSku.getProductSubSku().split(proProductMaster.getProductSku())[1];
                        copSku.setProductSubSku(master.getProductSku()+sku);
                    }else{
                        copSku.setProductSubSku(master.getProductSku());
                    }
                }
                copSku.setId(null);
                copSku.setFkProductId(master.getId());
                copSku.setAddTime(Utils.currentTimeSecond());
                copSku.insert();
                map.put(stock.getId(),copSku.getId());
            }
            List<ProProductSecInfo> secInfos = ProProductSecInfo.me().setFkProductMasterId(id).find();
            for (ProProductSecInfo secInfo : secInfos) {
                if (map.containsKey(secInfo.getFkStockId())){
                    ProProductSecInfo copySecInfo = new ProProductSecInfo();
                    BeanUtils.copyProperties(secInfo,copySecInfo);
                    copySecInfo.setId(null);
                    copySecInfo.setFkStockId(map.get(secInfo.getFkStockId()));
                    copySecInfo.setFkProductMasterId(master.getId());
                    copySecInfo.setAddTime(Utils.currentTimeSecond());
                    copySecInfo.insert();
                }
            }
            ProSeo proSeo = ProSeo.me().setSource(id).setType(ProSeoTypeEnum.TYPE_PRODUCT.type()).get();
            ProSeo copySeo = new ProSeo();
            BeanUtils.copyProperties(proSeo,copySeo);
            copySeo.setId(null);
            copySeo.setSource(master.getId());
            copySeo.setSeoTitle(master.getProductName());
            proSeoService.editProProductSeo(copySeo);
            return Result.success();
        }
        return Result.toast("暂无数据");
    }


    /**
     * 查询需要导出的商品数据
     * @param queryDto
     * @return
     */
    public List<ProProductMaster> exportProductMaster(ProductMasterQueryDto queryDto) {
        queryDto.setIsEnable(Status.YES);
        return  ProProductMaster.me().find(queryDto);
    }


}
