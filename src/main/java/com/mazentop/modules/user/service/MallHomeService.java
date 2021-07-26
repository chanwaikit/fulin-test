package com.mazentop.modules.user.service;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mazentop.entity.*;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.OrdSalesOrderCommond;
import com.mazentop.modules.emp.commond.ProProductMasterCommond;
import com.mazentop.modules.web.constants.CacheConstant;
import com.mazentop.modules.web.constants.ProductConstant;
import com.mazentop.modules.web.dto.ProProductStockDto;
import com.mazentop.modules.web.service.ProductService;
import com.mazentop.modules.web.service.SysExchangeRateWebService;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.data.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author cq
 * 商城首页Service
 */
@Service
public class MallHomeService {

    @Autowired
    SysExchangeRateWebService sysExchangeRateWebService;

    @Autowired
    private BaseDao baseDao;
    @Autowired
    private ProductService productService;

    /**
     * 获取首页置顶 首页推荐 首页展示的商品
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> getHomeCommodity(String exchangeId) {

        List<ProProductMaster> recommendStickyProducts = getHomeRecommendStickyBefore3Limit(exchangeId);
        recommendStickyProducts.forEach(c -> {
            c.addExten("seoUrl", Seo.getSeoUrlForPorduct(c.getId()));
        });

        List<String> repeatProductIds = recommendStickyProducts.stream()
                .filter(f -> ProductConstant.HOME_SHOW.equals(f.getIsShowIndex())
                        && ProductConstant.RECOMMEND.equals(f.getIsShowRecommend()))
                .map(ProProductMaster::getId).collect(Collectors.toList());

        List<ProProductMaster> homeRecommendAndHomeShowProducts
                = getHomeRecommendAndHomeShowBefore2Limit(repeatProductIds, exchangeId);
        homeRecommendAndHomeShowProducts.forEach(c -> {
            c.addExten("seoUrl", Seo.getSeoUrlForPorduct(c.getId()));
        });

        repeatProductIds.addAll(homeRecommendAndHomeShowProducts.stream()
                .filter(f -> ProductConstant.HOME_SHOW.equals(f.getIsShowIndex()))
                .map(ProProductMaster::getId).collect(Collectors.toList()));

        List<ProProductMaster> homeShowProducts = getHomeShowBefore8Limit(repeatProductIds, exchangeId);
        homeShowProducts.forEach(c -> {
            c.addExten("seoUrl", Seo.getSeoUrlForPorduct(c.getId()));
        });

        Map<String, Object> productMap = Maps.newHashMap();
        productMap.put("recommendStickyProducts", recommendStickyProducts);
        productMap.put("homeRecommendAndHomeShowProducts", homeRecommendAndHomeShowProducts);
        productMap.put("homeShowProducts", homeShowProducts);

        return productMap;
    }


    /**
     * 得到首页推荐置顶的前3条商品
     *
     * @return List<ProProductMaster>
     */
    private List<ProProductMaster> getHomeRecommendStickyBefore3Limit(String exchangeId) {

        List<ProProductMaster> proProductMasters = ProProductMaster.me()
                .setIsShowTop(ProductConstant.SHOW_TOP)
                .setIsShowRecommend(ProductConstant.RECOMMEND)
                .setIsShelve(Status.YES)
                .setOrderByFields(ProProductMaster.F_SORT)
                .setLimit(ProductConstant.HOME_RECOMMEND_STICKY_SIZE).find();

        productAmountConversion(proProductMasters, exchangeId);
        return proProductMasters;
    }


    /**
     * 得到首页推荐和首页展示的前三数据，不包括推荐和置顶已展示的数据
     *
     * @return List<ProProductMaster>
     */
    private List<ProProductMaster> getHomeRecommendAndHomeShowBefore2Limit(List<String> repeatProductIds, String exchangeId) {

        List<ProProductMaster> proProductMasters = ProProductMaster.me()
                .setIsShowIndex(ProductConstant.HOME_SHOW)
                .setIsShowRecommend(ProductConstant.RECOMMEND)
                .setOrderByFields(ProProductMaster.F_SORT)
                .setIsShelve(Status.YES)
                .setLimit(ProductConstant.HOME_RECOMMEND_STICKY_SIZE
                        + ProductConstant.HOME_RECOMMEND_AND_HOME_SHOW_SIZE
                        + ProductConstant.HOME_SHOW_SIZE).find();

        List<ProProductMaster> repeatProductList = proProductMasters.stream()
                .filter(f -> !repeatProductIds.contains(f.getId()))
                .limit(ProductConstant.HOME_RECOMMEND_AND_HOME_SHOW_SIZE).collect(Collectors.toList());

        productAmountConversion(repeatProductList, exchangeId);
        return repeatProductList;
    }

    /**
     * 查询首页推荐的前8条记录 不包括已展示的
     *
     * @return List<ProProductMaster>
     */
    private List<ProProductMaster> getHomeShowBefore8Limit(List<String> repeatProductIds, String exchangeId) {
        List<ProProductMaster> proProductMasters = ProProductMaster.me()
                .setIsShowIndex(ProductConstant.HOME_SHOW)
                .setIsShelve(Status.YES)
                .setOrderByFields(ProProductMaster.F_SORT)
                .setLimit(ProductConstant.HOME_RECOMMEND_AND_HOME_SHOW_SIZE
                        + ProductConstant.HOME_SHOW_SIZE).find();

        List<ProProductMaster> repeatProductList = proProductMasters.stream()
                .filter(f -> !repeatProductIds.contains(f.getId()))
                .limit(ProductConstant.HOME_SHOW_SIZE).collect(Collectors.toList());

        productAmountConversion(repeatProductList, exchangeId);
        return repeatProductList;
    }


    /**
     * 商品集合对象金额转换处理
     *
     * @param list 商品集合对象
     */
    private void productAmountConversion(List<ProProductMaster> list, String exchangeId) {
        String currency = null;
        if (Helper.isNotEmpty(exchangeId)) {
            SysExchangeRate sysExchangeRate = SysExchangeRate.me().setId(exchangeId).get();
            currency = sysExchangeRate.getTcur();
        } else {
            currency = LFUCache.get(CacheConstant.SITE_SETTING_CURRENCY).toString();
        }
        for (ProProductMaster  productMaster : list) {
            // 商品商城价格
            productMaster.addExten("currency", currency);
            // 2020-10-30 去掉，商品主表库存字段去掉
//            if (null != productMaster.getProductMallPrice() && productMaster.getProductMallPrice() > 0) {
//                BigDecimal productMallPrice = Helper.transformF2Y(productMaster.getProductMallPrice());
//                if (Helper.isNotEmpty(exchangeId)) {
//                    productMallPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(productMaster.getProductMallPrice()));
//                }
//                productMaster.addExten("productMallPrice", productMallPrice);
//            }
//            // 商品市场价格
//            if (null != productMaster.getProductMarketPrice() && productMaster.getProductMallPrice() > 0) {
//                BigDecimal productMarketPrice = Helper.transformF2Y(productMaster.getProductMarketPrice());
//                if (Helper.isNotEmpty(exchangeId)) {
//                    productMarketPrice = sysExchangeRateWebService.payCalculation(exchangeId, Helper.transformF2Y(productMaster.getProductMarketPrice()));
//                }
//                productMaster.addExten("productMarketPrice", productMarketPrice);
//            }
            List<ProProductStockDto> productStockDto = productService.getProductStockDto(productMaster.getId(), exchangeId);
            if (productStockDto.size() > 0) {
                productMaster.addExten("productMallPrice", productService.productMallPrice(productStockDto));
                productMaster.addExten("productMarketPrice", productService.productMarketPrice(productStockDto));
            }
            if (null != productMaster.getAddTime()) {
                productMaster.addExten("addTime", Helper.timeStampFormat(productMaster.getAddTime()));
            }

        }
    }


    /**
     * 查询导航栏
     *
     * @return Map<String, Object>
     */
    public Map<String, Object> getMallNavigation(String exchangeId) {

        // 查询显示的分类
        List<ProProductType> proProductTypes = ProProductType.me()
                .setIsRootType(ProductConstant.ROOT_TYPE)
                .setIsShowIndex(ProductConstant.HOME_SHOW)
                .setOrderByFields(ProProductType.F_SORT)
                .setLimit(ProductConstant.NAVIGATION_SIZE).find();

        List<ProProductMaster> proProductMasters = Lists.newArrayList();

        for (ProProductType p : proProductTypes) {
            String sql = String.format("SELECT product.* ,shortcut.fk_product_type_id as `productTypeId` " +
                            "FROM pro_product_master product INNER JOIN pro_product_shortcut shortcut ON " +
                            "product.id=shortcut.fk_product_id WHERE shortcut.fk_product_type_id='%s' and product.is_shelve =1 ORDER BY " +
                            "product.sort ASC,add_time DESC LIMIT %s",
                    p.getId(), ProductConstant.NAVIGATION_PRODUCT_SIZE);
            List<Map<String, Object>> tempMaps = baseDao.queryForList(sql);
            tempMaps.forEach(t -> {
                ProProductMaster tempMaster = ProProductMaster.me().setId(t.get("id").toString()).get();
                tempMaster.addExten("productTypeId", t.get("productTypeId"));
                tempMaster.addExten("seoUrl", Seo.getSeoUrlForPorduct(t.get("id").toString()));
                proProductMasters.add(tempMaster);
            });
        }

        // 进行金额转换操作
        productAmountConversion(proProductMasters, exchangeId);

        Map<String, Object> navigationMap = Maps.newHashMap();
        navigationMap.put("productType", proProductTypes);
        navigationMap.put("proProductMasters", proProductMasters);
        return navigationMap;
    }

    /**
     * 查询轮播图
     *
     * @return List<SysAdvertisement>
     */
    public List<SysAdvertisement> mallCarousel(String carouselName) {

        List<SysAdvertisement> sysAdvertisements
                = SysAdvertisement.me()
                .setPosition(ProductConstant.CAROUSEL_NAME)
                .setIsEnable(ProductConstant.CAROUSEL_ENABLE)
                .setOrderByFields(SysAdvertisement.F_SORT)
                .find();

        for (SysAdvertisement s : sysAdvertisements) {
            if (null != s.getAddTime()) {
                s.addExten("addTime", Helper.timeStampFormat(s.getAddTime()));
            }
        }

        return sysAdvertisements;
    }

    /**
     * 根据ID查询轮播图详细信息
     *
     * @param id 轮播图ID
     * @return SysAdvertisement
     */
    public SysAdvertisement getMallCarouselById(String id) {

        SysAdvertisement sysAdvertisement = SysAdvertisement.me().setId(id).get();
        sysAdvertisement.addExten("addTime", Helper.timeStampFormat(sysAdvertisement.getAddTime()));

        return sysAdvertisement;
    }

    /**
     * 修改轮播图信息
     *
     * @param sysAdvertisement 轮播图对象
     * @return boolean
     */
    public boolean editMallCarouse(SysAdvertisement sysAdvertisement) {

        return SysAdvertisement.me()
                .setId(sysAdvertisement.getId())
                .setImageUrl(sysAdvertisement.getImageUrl())
                .setTargetUrl(sysAdvertisement.getTargetUrl())
                .setLength(sysAdvertisement.getLength())
                .setWidth(sysAdvertisement.getWidth())
                .setSort(sysAdvertisement.getSort())
                .setIsEnable(sysAdvertisement.getIsEnable()).update() > 0;

    }

    /**
     * 保存用户订阅信息
     *
     * @param email 邮箱
     * @param ip    ip地址
     * @return boolean
     */
    public R saveSubscription(String email, String ip) {

        SysSubscriber sysSubscriber = SysSubscriber.me().setEmail(email).get();
        if (Objects.nonNull(sysSubscriber)) {
            return R.toast("重复提交");
        }

        SysSubscriber.me().setEmail(email)
                .setIp(ip)
                .insert();
        return R.ok();
    }

    /**
     * 关键词搜索商品
     *
     * @param keyWorld 关键词
     * @return List<ProProductMaster>
     */
    public Map<String, Object> keyWorldSearch(String keyWorld, String ip) {

        ProProductMasterCommond commond = new ProProductMasterCommond();
        commond.setProductName(keyWorld);
        Page page = ProProductMaster.me().paginate(commond);

        SysSiteSearchRecord.me()
                .setKeywords(keyWorld)
                .setSearchTime(Utils.currentTimeSecond())
                .setReturnResult(JSONArray.toJSONString(page.getList()))
                .setIp(ip).insert();

        SysSiteSearchRecord sysSiteSearchRecord = SysSiteSearchRecord.me().get();

        Map<String, Object> map = Maps.newHashMap();
        map.put("page", page);
        map.put("keyId", sysSiteSearchRecord.getId());

        return map;
    }

    /**
     * 记录通过关键字搜索进入的商品
     *
     * @param keyId     关键字ID
     * @param productId 商品ID
     * @return
     */
    public boolean recordKeyWorldLinkProduct(HttpServletRequest request, String keyId, String productId) {

        SysSiteSearchRecord sysSiteSearchRecord = SysSiteSearchRecord.me().setId(keyId).get();
        ProProductMaster master = ProProductMaster.me().setId(productId).get();

        return SysSiteSearchRecordDetail.me()
                .setFkSiteSearchRecordId(sysSiteSearchRecord.getId())
                .setKeywords(sysSiteSearchRecord.getKeywords())
                .setSearchTime(sysSiteSearchRecord.getSearchTime())
                .setFkProductId(master.getId())
                .setProductName(master.getProductName())
                .setIp(Helper.getIpAddress(request)).insert() > 0;
    }

    /**
     * 统计
     */

    public void monthStatisticalReport() {

        LocalDateTime todayStart = LocalDateTime
                .of(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime
                .of(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);

        OrdSalesOrderCommond commond = new OrdSalesOrderCommond();
        SearchTime searchTime = new SearchTime();
        searchTime.setStart(todayStart.toEpochSecond(ZoneOffset.of("+8")));
        searchTime.setEnd(todayEnd.toEpochSecond(ZoneOffset.of("+8")));
        commond.setAddTime(searchTime);
        commond.setSalesOrderStatus(OrdSalesOrderStatusEnum.COMPLETE.status());

        List<OrdSalesOrder> ordSalesOrders = OrdSalesOrder.me()
                .find(commond);

        // 统计总金额
        BigDecimal orderTotalAmount = new BigDecimal(0);
        Integer productNumber = 0;
        for (OrdSalesOrder c : ordSalesOrders) {
            orderTotalAmount = orderTotalAmount.add(BigDecimal.valueOf(c.getTotalPrice()));
            productNumber += c.getTotalProductNumber();
        }

        DatMonthSum.me().setAddTime(Utils.currentTimeSecond())
                .setTotalSum(orderTotalAmount.longValue())
                .setMonth(Utils.currentTimeSecond())
                .setTotalOrderNumber(ordSalesOrders.size())
                .setTotalProductNumber(productNumber).insert();

    }

    /**
     * 日统计
     */
    public void dayStatisticalReport() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        OrdSalesOrderCommond commond = new OrdSalesOrderCommond();
        SearchTime searchTime = new SearchTime();
        searchTime.setStart(todayStart.toEpochSecond(ZoneOffset.of("+8")));
        searchTime.setEnd(todayEnd.toEpochSecond(ZoneOffset.of("+8")));
        commond.setAddTime(searchTime);
        commond.setSalesOrderStatus(OrdSalesOrderStatusEnum.COMPLETE.status());

        List<OrdSalesOrder> ordSalesOrders = OrdSalesOrder.me()
                .find(commond);

        // 统计总金额
        BigDecimal orderTotalAmount = new BigDecimal(0);
        Integer productNumber = 0;
        for (OrdSalesOrder c : ordSalesOrders) {
            orderTotalAmount = orderTotalAmount.add(BigDecimal.valueOf(c.getTotalPrice()));
            productNumber += c.getTotalProductNumber();
        }

        DatDateSum.me().setAddTime(Utils.currentTimeSecond())
                .setTotalSum(orderTotalAmount.longValue())
                .setDate(Utils.currentTimeSecond())
                .setTotalOrderNumber(ordSalesOrders.size())
                .setTotalProductNumber(productNumber).insert();

    }
}
