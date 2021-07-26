package com.mazentop.modules.web.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.ProProductStockCommond;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.modules.skin.dto.SkinProductSkuDto;
import com.mazentop.modules.web.commond.ProProductMasterCommond;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.dto.ColorCardDto;
import com.mazentop.modules.web.dto.ProProductSpecDto;
import com.mazentop.modules.web.dto.ProProductStockDto;
import com.mazentop.modules.web.dto.SkuDto;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.util.Helper;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.sun.org.apache.xerces.internal.xs.LSInputList;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    ProProductMasterService proProductMasterService;

    @Autowired
    BaseDao baseDao;

    @Autowired
    SysExchangeRateWebService sysExchangeRateWebService;


    //查询商品列表
    public Page proProductMasterList(ProProductMasterCommond proProductMasterCommond, HttpServletRequest request) {
        String exchangeId = Helper.getExchangeId(request, CacheConstant.RATIO);
        proProductMasterCommond.setIsEnable(1);
        proProductMasterCommond.setIsShelve(1);
        Map<String, Object> param = new HashMap<>();
        StringBuilder sql = new StringBuilder();
        sql.append("select pro.id,pro.product_name,pro.product_mall_price,prduct_pic_image_url from pro_product_master pro");
        //判断分类id是否为空
        if (Helper.isNotEmpty(proProductMasterCommond.getRootTypeId())) {
            sql.append("  left join pro_product_shortcut sho  on pro.id=sho.fk_product_id ");
            //递归处理
            List<String> list = new ArrayList<>();
            list.add(proProductMasterCommond.getRootTypeId());
            optionProductType(list, proProductMasterCommond.getRootTypeId());
            proProductMasterCommond.setFkProductTypeId(list);
            sql.append(" where sho.fk_product_type_id in (:list)");
            param.put("list", list);
        } else {
            sql.append(" where 1=1 ");
        }

        //根据金额区间过筛
        SearchTime searchTime = new SearchTime();
        if (!Helper.isEmpty(proProductMasterCommond.getMinProductMallPrice())) {
            Long startPrice = Helper.transformY2F(new BigDecimal(proProductMasterCommond.getMinProductMallPrice()));
            searchTime.setStart(startPrice);
            sql.append(" and product_mall_price >=:startPrice");
            param.put("startPrice", startPrice);
        }
        if (!Helper.isEmpty(proProductMasterCommond.getMaxProductMallPrice())) {
            Long endPrice = Helper.transformY2F(new BigDecimal(proProductMasterCommond.getMaxProductMallPrice()));
            searchTime.setEnd(endPrice);
            sql.append(" and product_mall_price <=:endPrice");
            param.put("endPrice", endPrice);
        }
        sql.append(" and is_enable=1 ");
        sql.append(" and is_shelve=1 ");
        // 2020-10-30 去掉，商品主表库存字段去掉
//        proProductMasterCommond.setProductMallPrice(searchTime);
        //判断是否有排序条件
        if (Helper.isEmpty(proProductMasterCommond.getOrderByNames())) {
            proProductMasterCommond.setOrderBy(Order.asc(ProProductMaster.F_SORT));
        } else {
            proProductMasterCommond.setOrderBy(proProductMasterCommond.getOrderByNames() + " desc");
        }
        List<Map<String, Object>> list = baseDao.paginate(sql.toString(), param, proProductMasterCommond);
        List<ProProductMaster> productMasters = new ArrayList<>();
        list.forEach(map -> {
            ProProductMaster proProductMaster = new ProProductMaster();
            proProductMaster.setId(map.get("id").toString());
            // 2020-10-30 去掉，商品主表库存字段去掉
//            proProductMaster.setProductMallPrice(Long.parseLong(map.get("product_mall_price").toString()));
            proProductMaster.setProductName(map.get("product_name").toString());
            if (Helper.isNotEmpty(map.get("prduct_pic_image_url"))) {
                proProductMaster.setPrductPicImageUrl(map.get("prduct_pic_image_url").toString());
            }
            proProductMaster.addExten("exchangeId", exchangeId);
            proProductMasterService.optProductPrice(proProductMaster);
            productMasters.add(proProductMaster);
        });

        return new Page(productMasters, proProductMasterCommond);
    }

    /**
     * 查询商品根目录分类信息
     */
    public List<Map<String, Object>> findProductTypeList() {
        String sql = "select id,product_type_name from pro_product_type where 1=1 and parent_product_type_id  is not null and is_root_type = :isRootType  ORDER BY sort";
        Map<String, Object> param = new HashMap<>();
        param.put("isRootType", 1);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        return list;
    }

    /**
     * 分类查询递归处理
     *
     * @param typeList
     * @param typeId
     */
    public void optionProductType(List<String> typeList, String typeId) {
        List<ProProductType> proProductTypes = ProProductType.me().setParentProductTypeId(typeId).find();
        if (!proProductTypes.isEmpty()) {
            proProductTypes.forEach(type -> {
                typeList.add(type.getId());
                optionProductType(typeList, type.getId());
            });
        }
    }

    public List<Map<String, Object>> productColorList(String productId) {
        String sql = "select id,color from pro_product_stock where 1=1 and fk_product_id = :productId group by color";
        Map<String, Object> param = new HashMap<>(1);
        param.put("productId", productId);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        return list;
    }

    /**
     * 获取商品规格属性，按照用户输入顺序返回
     * @param productId
     * @return
     */
    public List<ProProductSpecDto> getProductSpec(String productId) {
        String parentSql = "select spec.* from pro_product_spec spec,pro_product_sec_info info where spec.id = info.fk_parent_spec_id and  info.fk_product_master_id =:productId GROUP BY info.fk_parent_spec_id ORDER BY info.id asc";
        Map<String, Object> param = new HashMap<>(1);
        param.put("productId", productId);
        // 属性名集合
        List<ProProductSpec> parentSpecList = baseDao.queryForBeanList(parentSql, param, new ProProductSpec());
        if(parentSpecList.size() > 0 ){
            // 属性值集合
            String sql = "select spec.* from pro_product_spec spec,pro_product_sec_info info where spec.id = info.fk_spec_id and  info.fk_product_master_id =:productId GROUP BY info.fk_spec_id ORDER BY info.id asc";
            List<ProProductSpec> specList = baseDao.queryForBeanList(sql, param, new ProProductSpec());
            List<ProProductSpecDto> resultList = new ArrayList<>();
            for (ProProductSpec parentSpec : parentSpecList) {
                ProProductSpecDto parentSpecSpecDto = new ProProductSpecDto();
                BeanUtils.copyProperties(parentSpec, parentSpecSpecDto);
                if(parentSpecSpecDto.getSpecName().equals("Color")){
                    parentSpecSpecDto.setColorFlag(1);
                }else{
                    parentSpecSpecDto.setColorFlag(0);
                }
                for (ProProductSpec productSpec : specList) {
                    if (parentSpecSpecDto.getId().equals(productSpec.getParentId())) {
                        if(parentSpecSpecDto.getColorFlag() == 1){
                            ProProductColor proProductColor = ProProductColor.me().setColorName(productSpec.getSpecName()).get();
                            if (Helper.isNotEmpty(proProductColor)){
                                productSpec.addExten("colorCode",proProductColor.getColor());
                            }
                        }
                        List<ProProductSpec> proProductSpecs = null;
                        if (null == parentSpecSpecDto.getSpecList()) {
                            proProductSpecs = new ArrayList<>();
                        }else {
                            proProductSpecs = parentSpecSpecDto.getSpecList();
                        }
                        proProductSpecs.add(productSpec);
                        parentSpecSpecDto.setSpecList(proProductSpecs);
                    }
                }
                resultList.add(parentSpecSpecDto);
            }
            resultList.sort(Comparator.comparing(ProProductSpecDto::getColorFlag).reversed());
            return resultList;
        }
        return new ArrayList<>();
    }



    public List<ProProductStockDto> getProductStockDto(String productId, String exchangeId) {
        List<ProProductStock> proProductStocks = ProProductStock.me().setFkProductId(productId).find();
        List<ProProductStockDto> list = new ArrayList<>();
        ActPromotionProduct actPromotionProduct = getActPromotionProduct(productId, exchangeId);
        Map<String, Map<String, Object>> specs = getSpecs(productId);
        for (ProProductStock proProductStock : proProductStocks) {
            ProProductStockDto proProductStockDto = new ProProductStockDto();
            proProductStockDto.setActPromotionProduct(actPromotionProduct);
            BeanUtils.copyProperties(proProductStock, proProductStockDto);
            if (Helper.isNotEmpty(exchangeId)) {
                proProductStockDto.setProductMallPrice(sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(proProductStock.getProductMallPrice())).toString());
                proProductStockDto.setProductMarketPrice(sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(proProductStock.getProductMarketPrice())).toString());
            } else {
                proProductStockDto.setProductMallPrice(Helper.transformF2Y(proProductStock.getProductMallPrice()).toString());
                proProductStockDto.setProductMarketPrice(Helper.transformF2Y(proProductStock.getProductMarketPrice()).toString());
            }
            if (specs.containsKey(proProductStock.getId())){
                Map<String, Object> objectMap = specs.get(proProductStock.getId());
                proProductStockDto.setSpec(objectMap.get("spec").toString());
                proProductStockDto.setSpecName(Helper.toJson(Convert.toList(String.class,objectMap.get("name"))));
            }
            ProProductMaster productMaster = ProProductMaster.me().setId(proProductStock.getFkProductId()).get();
            proProductStockDto.setProductName(productMaster.getProductName());
            list.add(proProductStockDto);
        }

        return list;
    }

    /**
     * 根据sku查商品属性
     * @param stockId
     * @return
     */
    public List<Map<String, Object>> getSpec(String stockId) {
        String sql = "select fk_spec_id,(SELECT spec_name from pro_product_spec WHERE id=fk_spec_id)as name from pro_product_sec_info where fk_stock_id=(:stockId)";
        Map<String, Object> param = new HashMap<>(1);
        param.put("stockId", stockId);
        return baseDao.queryForList(sql, param);
    }


    public Map<String,Map<String,Object>> getSpecs(String productId) {
        String sql = "select  fk_stock_id,fk_spec_id,(SELECT spec_name from pro_product_spec WHERE id=fk_spec_id)as name from pro_product_sec_info where fk_product_master_id=(:productId)";
        Map<String, Object> param = new HashMap<>();
        param.put("productId", productId);
        List<Map<String, Object>> maps = baseDao.queryForList(sql, param);
        Map<String,Map<String,Object>> spec=new HashMap<>();
        for (Map<String, Object> map : maps) {
            if (spec.containsKey(map.get("fk_stock_id"))){
                Map<String, Object> objectMap = spec.get(map.get("fk_stock_id"));
                String specId = "{}|{}";
                specId= StrUtil.format(specId,objectMap.get("spec").toString(),map.get("fk_spec_id").toString());
                objectMap.put("spec",specId);
                List<String> name = Convert.toList(String.class,objectMap.get("name"));
                name.add(map.get("name").toString());
                objectMap.put("name",name);
            }else {
                Map<String, Object> objectMap =new HashMap<>();
                objectMap.put("spec",map.get("fk_spec_id").toString());
                List<String> name=new ArrayList<>();
                name.add(map.get("name").toString());
                objectMap.put("name",name);
                spec.put(map.get("fk_stock_id").toString(),objectMap);
            }
        }
        return spec;
    }

    public List<Map<String, Object>> getSpecAndParent(String stockId) {
        String sql = "SELECT fk_spec_id, fk_parent_spec_id,(SELECT spec_name FROM pro_product_spec WHERE id = fk_parent_spec_id ) AS name,(SELECT spec_name FROM pro_product_spec WHERE id = fk_spec_id ) AS spec_name FROM  pro_product_sec_info \n" +
                "WHERE fk_stock_id=(:stockId)";
        Map<String, Object> param = new HashMap<>();
        param.put("stockId", stockId);
        List<Map<String, Object>> maps = baseDao.queryForList(sql, param);
        return maps;
    }


    public ActPromotionProduct getActPromotionProduct(String productId, String exchangeId) {
        String currency = LFUCache.get(CacheConstant.SITE_SETTING_CURRENCY).toString();
        if (Helper.isNotEmpty(exchangeId)) {
            SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(exchangeId).get();
            currency = sysExchangeRate.getTcur();
        }
        ProProductMaster proProductMaster = ProProductMaster.me().setId(productId).get();
        ActPromotionActivity promotionActivity = ActPromotionActivity.me().setActivityStatus("2").get();
        if (!Objects.isNull(promotionActivity)) {

            ActPromotionProduct actPromotionProduct = ActPromotionProduct.me().setFkProductId(productId).setFkActivityId(promotionActivity.getId()).get();
            if (!Objects.isNull(actPromotionProduct)) {
                //满减计算
                actPromotionProduct.addExten("currency", currency);
                BigDecimal discontValue = new BigDecimal(0);
                if (actPromotionProduct.getDiscountValue() != null) {
                    if (Helper.isNotEmpty(exchangeId)) {
                        discontValue = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(actPromotionProduct.getDiscountValue()));
                    } else {
                        discontValue = Helper.transformF2Y(actPromotionProduct.getDiscountValue());
                    }

                    actPromotionProduct.addExten("discountValue", discontValue);
                }
                ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setId(actPromotionProduct.getDiscountTypeId()).get();
                if (!Objects.isNull(actDiscountUseType)) {
                    // 2020-10-30 去掉，商品主表库存字段去掉
//                    BigDecimal promotionAfterPrice = Helper.transformF2Y(proProductMaster.getProductMallPrice());
//                    if (Helper.isNotEmpty(exchangeId)) {
//                        promotionAfterPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(proProductMaster.getProductMallPrice()));
//                    }
//                    if (actDiscountUseType.getDiscountUseTypeCode().equals("02")) {
//                        actPromotionProduct.addExten("promotionAfterPrice", promotionAfterPrice.subtract(discontValue));
//                    } else {
//                        discontValue = discontValue.divide(new BigDecimal(10));
//                        actPromotionProduct.addExten("promotionAfterPrice", promotionAfterPrice.multiply(discontValue));
//                    }
                } else {
//                    if (actPromotionProduct.getPromotionAfterPrice() != null) {
//                        BigDecimal promotionAfterPrice = Helper.transformF2Y(actPromotionProduct.getPromotionAfterPrice());
//                        if (Helper.isNotEmpty(exchangeId)) {
////                            promotionAfterPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(proProductMaster.getProductMallPrice()));
//                        }
//                        actPromotionProduct.addExten("promotionAfterPrice", promotionAfterPrice);
//                    }
                }
                return actPromotionProduct;
            }
        }
        return null;
    }

    public List<SkuDto> getSkuSpec(String id) {
        Map<String, Object> param = new HashMap<>();
        String sql = "SELECT fk_spec_id, fk_parent_spec_id,(SELECT spec_name FROM pro_product_spec WHERE id = fk_parent_spec_id ) AS name,(SELECT spec_name FROM pro_product_spec WHERE id = fk_spec_id ) AS spec_name FROM  pro_product_sec_info  WHERE fk_product_master_id = :id ";
        param.put("id", id);
        List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
        Map<String, Set<String>> specMap = new HashMap<>();
        for (Map<String, Object> map : mapList) {
            if (specMap.containsKey(map.get("name"))) {
                Set<String> set = specMap.get(map.get("name"));
                set.add(map.get("spec_name").toString());
            } else {
                Set<String> set = new HashSet<>();
                set.add(map.get("spec_name").toString());
                specMap.put(map.get("name").toString(), set);
            }
        }
        specMap.remove("Color");
        List<SkuDto> dto = new ArrayList<>();
        for (String key : specMap.keySet()) {
            SkuDto skuDto = new SkuDto();
            skuDto.setSpec(key);
            ;
            skuDto.setSpecItem(new ArrayList<>(specMap.get(key)));
            dto.add(skuDto);
        }
        return dto;
    }

    public String productMallPrice(List<ProProductStockDto> list) {
        if (list.size() == 0) {
            return "0";
        }
        List<String> collect = list.stream().map(ProProductStockDto::getProductMallPrice).collect(Collectors.toList());
        List<Long> price = new ArrayList<>();
        for (String prce : collect) {
            price.add(Helper.transformY2F(new BigDecimal(prce)));
        }
        return Helper.transformF2Y(Collections.min(price)).toString();

    }

    public String productMarketPrice(List<ProProductStockDto> list) {
        if (list.size() == 0) {
            return "0";
        }
        List<String> collect = list.stream().map(ProProductStockDto::getProductMarketPrice).collect(Collectors.toList());
        List<Long> price = new ArrayList<>();
        for (String prce : collect) {
            price.add(Helper.transformY2F(new BigDecimal(prce)));
        }
        return Helper.transformF2Y(Collections.min(price)).toString();

    }

    //获取商品评分
    public String getScoreCount(String proMasterId){
        List<ProComment> proCommentList = ProComment.me().setFkProductId(proMasterId).setIsAuditPass(1).find();
        BigDecimal scoreCount = new BigDecimal(0);
        if (!proCommentList.isEmpty()) {
            for (ProComment proComment : proCommentList) {
                if (proComment.getRangeNum() != null) {
                    scoreCount= scoreCount.add(new BigDecimal(proComment.getRangeNum()));
                }
            }
            return scoreCount.multiply(new BigDecimal(proCommentList.size())).toString();
        }else{
            return scoreCount.toString();
        }
    }



    public SkinProductSkuDto getSkinProductSkuData(String productId){
        SkinProductSkuDto skinProductSkuDto = new SkinProductSkuDto();
        ProProductMaster productMaster = ProProductMaster.me().setId(productId).get();
        //商品规格
        List<ProProductSpecDto> specList = getProductSpec(productId);
        skinProductSkuDto.setSkus(optionSpec(specList,productMaster.getIsSingleProduct()));
        //商品库存
        skinProductSkuDto.setStocks(getStock(productId));
        // 处理商品价格
        optProductPrice(productMaster,skinProductSkuDto);
        return skinProductSkuDto;
    }


    // 金额处理
    public void optProductPrice(ProProductMaster proProductMaster,SkinProductSkuDto skinProductSkuDto) {

        List<ProProductStock> stockList = ProProductStock.me().setFkProductId(proProductMaster.getId()).find();

        skinProductSkuDto.setIsSingle(proProductMaster.getIsSingleProduct());
        skinProductSkuDto.setProductId(proProductMaster.getId());
        List<Long> mallprice = new ArrayList<Long>();
        List<Long> marketPrice = new ArrayList<Long>();
        int stockNumber = 0;
        for (ProProductStock stock : stockList) {
            mallprice.add(stock.getProductMallPrice());
            marketPrice.add(stock.getProductMarketPrice());
            if (stock.getProductStockNumber() != null) {
                stockNumber += stock.getProductStockNumber();
            }
        }
        skinProductSkuDto.setStockNumber(stockNumber);
        if (!mallprice.isEmpty()) {
            skinProductSkuDto.setMaxPrice(Helper.transformF2Y(Collections.max(mallprice)).toString());
            skinProductSkuDto.setMinPrice(Helper.transformF2Y(Collections.min(mallprice)).toString());
        } else{
            skinProductSkuDto.setMaxPrice("0");
            skinProductSkuDto.setMinPrice("0");
        }
        if (!marketPrice.isEmpty()) {
            skinProductSkuDto.setMinMarketPrice(Helper.transformF2Y(Collections.min(marketPrice)).toString());
        } else{
            skinProductSkuDto.setMinMarketPrice("0");
        }
    }




    public List<SkinProductSkuDto.Sku> optionSpec(List<ProProductSpecDto> specList,Integer isSingle){
        List<SkinProductSkuDto.Sku> specDtos = new ArrayList<>();
        if(isSingle == 0) {
            if (!specList.isEmpty()) {
                specList.forEach(specDto -> {
                    SkinProductSkuDto.Sku productSpecDto = new SkinProductSkuDto.Sku();
                    BeanUtils.copyProperties(specDto, productSpecDto);
                    List<SkinProductSkuDto.Spec> specDtoList = new ArrayList<>();
                    specDto.getSpecList().forEach(spec -> {
                        SkinProductSkuDto.Spec skinSpecDto = new SkinProductSkuDto.Spec();

                        BeanUtils.copyProperties(spec, skinSpecDto);
                        if(!Objects.isNull(spec.getExten().get("colorCode"))){
                            skinSpecDto.setColorCode(spec.getExten().get("colorCode").toString());
                        }
                        specDtoList.add(skinSpecDto);
                    });
                    productSpecDto.setSpecList(specDtoList);
                    specDtos.add(productSpecDto);
                });
            }
        }
        return specDtos;
    }

    public List<SkinProductSkuDto.Stock> getStock(String productId) {
        List<ProProductStock> proProductStocks = ProProductStock.me().setFkProductId(productId).setIsEnable(BooleanEnum.TRUE.getValue()).find();
        List<SkinProductSkuDto.Stock> list = new ArrayList<>();
        Map<String, Map<String, Object>> specs = getSpecs(productId);
        proProductStocks.forEach(stock->{
            SkinProductSkuDto.Stock productStock = new SkinProductSkuDto.Stock();
            productStock.setMallPrice(Helper.transformF2Y(stock.getProductMallPrice()).toString());
            productStock.setMarketPrice(Helper.transformF2Y(stock.getProductMarketPrice()).toString());
            if (specs.containsKey(stock.getId())){
                Map<String, Object> objectMap = specs.get(stock.getId());
                productStock.setSpec(objectMap.get("spec").toString());
                productStock.setSpecName(Helper.toJson(Convert.toList(String.class,objectMap.get("name"))));
            }
            ProProductMaster productMaster = ProProductMaster.me().setId(stock.getFkProductId()).get();
            productStock.setProductName(productMaster.getProductName());
            productStock.setImageUrl(stock.getProductMasterImageUrl());
            productStock.setStockNumber(stock.getProductStockNumber());
            // 去掉库存id
            productStock.setId(stock.getId());
            productStock.setProductSubSku(stock.getProductSubSku());
            list.add(productStock);
        });
        return list;
    }

    public ColorCardDto findColorCard(ProProductMaster productMaster) {
        Long sectInfoCount = ProProductSecInfo.me().setFkProductMasterId(productMaster.getId()).findCount();
        if(sectInfoCount >0 && productMaster.getIsSingleProduct().equals(BooleanEnum.FALSE.getValue())) {
            List<ProProductSecInfo> secInfoList = ProProductSecInfo.me().setFkProductMasterId(productMaster.getId()).setGroupByFields(ProProductSecInfo.F_FK_STOCK_ID).find();
            if(!secInfoList.isEmpty()) {
                List<String> ids = new ArrayList<>();
                secInfoList.forEach(secInfo->{
                    ids.add(secInfo.getFkStockId());
                });
                ProProductStockCommond proProductStockCommond = new ProProductStockCommond();
                proProductStockCommond.setIds(ids);
                proProductStockCommond.setGroupBy("color");
                List<ProProductStock> stockList = ProProductStock.me().find(proProductStockCommond);

                if (!stockList.isEmpty()) {
                    ColorCardDto colorCardDto = new ColorCardDto();
                    List<String> colorArray = new ArrayList<>();
                    List<ProProductColor> colorList =new ArrayList<>();
                    stockList.forEach(stock -> {
                        if (Helper.isNotEmpty(stock.getColor())) {
                            ProProductColor color = ProProductColor.me().setId(stock.getColor()).get();
                            ProProductSpec spec = ProProductSpec.me().setSpecName("Color").setParentId("0").get();
                            ProProductSecInfo secInfo = ProProductSecInfo.me().setFkStockId(stock.getId()).setFkParentSpecId(spec.getId()).get();
                            color.addExten("secId",secInfo.getFkSpecId());
                            colorArray.add(stock.getColor());
                            colorList.add(color);
                        }
                    });
                    colorCardDto.setColorCodes(colorArray);
                    colorCardDto.setColors(colorList);
                    return  colorCardDto;
                }

            }
        }
        return null;
    }


    /**
     *
     * @param productId
     * @return
     */
    public List<ProProductType> findProProductType(String productId) {
        List<ProProductShortcut> proProductShortcuts = Optional.ofNullable(ProProductShortcut.me().setFkProductId(productId).setOrderByFields("id desc").list()).orElse(Collections.emptyList());
        List<ProProductType> allProProductTypes = new ArrayList<>();
        proProductShortcuts.forEach(item -> {
            List<ProProductType> proProductTypes = new ArrayList<>();
            ProProductType proProductType = ProProductType.me().setId(item.getFkProductTypeId()).get();
            if (!Objects.isNull(proProductType)) {
                proProductTypes.add(proProductType);
                List<ProProductType> typeList = ThemeUtil.getTypeList(proProductTypes, ProProductType.me().setProductTypeName(Status.TREE_ROOT_NODE).get(), proProductType);
                allProProductTypes.addAll(typeList);
            }
        });
        return allProProductTypes;
    }
}
