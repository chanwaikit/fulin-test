package com.mazentop.modules.emp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.modules.emp.commond.ActPromotionActivityCommond;
import com.mazentop.modules.emp.commond.ActPromotionCommond;
import com.mazentop.modules.emp.dto.ActPromotionActivityDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: dengy
 * @date: 2020/3/19
 * @description:
 */
@Service
public class ActPromotionActivityService {

    @Autowired
    BaseDao baseDao;

    /**
     * 获取限时活动状态数量
     * @return
     */
    public HashMap<String, Object> getPromotionActivityStatus() {
        HashMap<String, Object> map = new HashMap<String, Object>(4);

        map.put("allCount", ActPromotionActivity.me().findCount());
        map.put("notStartedCount", ActPromotionActivity.me().setActivityStatus("1").findCount());
        map.put("inCount", ActPromotionActivity.me().setActivityStatus("2").findCount());
        map.put("finishedCount", ActPromotionActivity.me().setActivityStatus("3").findCount());

        return map;
    }

    public Page actPromotionActivityList(ActPromotionActivityCommond actPromotionActivityCommond){
        StringBuilder sql = new StringBuilder(" select * from act_promotion_activity where 1=1 ");
        actPromotionActivityCommond.setCompanyId(Subject.group());
        Map<String, Object> params = new HashMap<>();
        if (actPromotionActivityCommond.getStartTime() != null) {
            sql.append(" and start_time >= :startTime ");
            params.put("startTime", actPromotionActivityCommond.getStartTime());
        }
        if (actPromotionActivityCommond.getEndTime() != null) {
            sql.append(" and end_time <= :endTime ");
            params.put("endTime", actPromotionActivityCommond.getEndTime());
        }
        if (StringUtils.isNotEmpty(actPromotionActivityCommond.getActivityName())) {
            sql.append(" and activity_name like :activityName ");
            params.put("activityName", "%" + actPromotionActivityCommond.getActivityName() + "%");
        }
        if (StringUtils.isNotEmpty(actPromotionActivityCommond.getActivityStatus())) {
            sql.append(" and activity_status = :status ");
            params.put("status", actPromotionActivityCommond.getActivityStatus());
        }
        if (StringUtils.isNotEmpty(actPromotionActivityCommond.getCompanyId())) {
            sql.append(" and company_id = :companyId ");
            params.put("companyId", actPromotionActivityCommond.getCompanyId());
        }
        List<Map<String, Object>> list = baseDao.paginate(sql.toString(), params, actPromotionActivityCommond);
        if(!list.isEmpty()){
            list.forEach(map->{
               if(!Objects.isNull(map.get("id"))){
                   Long count = ActPromotionProduct.me().setFkActivityId(map.get("id").toString()).findCount();
                   map.put("productCount",count);
               }else{
                   map.put("productCount",0);
               }
               ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setDiscountUseTypeCode(map.get("discount_type_id").toString()).get();
               map.put("activityType",actDiscountUseType);
            });
        }
        return new Page(list,actPromotionActivityCommond);
    }


    /**
     * 新增/修改促销活动
     * @param actPromotionActivityDto
     * @return
     */
    public R doActPromotionActivityUpdateOrAdd(ActPromotionActivityDto actPromotionActivityDto){
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return R.error("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(curEmployee)) {
            return R.error("登陆人信息获取失败!");
        }
        if (Objects.isNull(actPromotionActivityDto)) {
            return R.error("促销活动数据获取失败!");
        }
        if (actPromotionActivityDto.getActPromotionProductList().isEmpty()) {
            return R.error("促销商品不能为空!");
        }
        if (StringUtils.isEmpty(actPromotionActivityDto.getId())) {
            SysCompany sysCompany = SysCompany.me().setId(Subject.group()).get();
            if(Objects.nonNull(sysCompany)){
                actPromotionActivityDto.setCompanyId(sysCompany.getId());
                actPromotionActivityDto.setCompanyName(sysCompany.getName());
            }
            actPromotionActivityDto.setAddTime(Utils.currentTimeSecond());
            actPromotionActivityDto.setAddUserId(curEmployee.getId());
        }
        Long sysDate =  Utils.currentTimeSecond();
        if(sysDate < actPromotionActivityDto.getStartTime()){
            actPromotionActivityDto.setActivityStatus("1");
        }else{
            actPromotionActivityDto.setActivityStatus("2");
        }
        if(sysDate > actPromotionActivityDto.getEndTime()){
            actPromotionActivityDto.setActivityStatus("3");
        }
        actPromotionActivityDto.insertOrUpdate();
        // 处理商品
        addActPromotionProduct(actPromotionActivityDto,curEmployee);
        return R.ok();
    }


    /**
     * 处理促销活动商品信息
     * @param actPromotionActivityDto
     * @param curEmployee
     */
    public void addActPromotionProduct(ActPromotionActivityDto actPromotionActivityDto,EmpEmployeeInfo curEmployee) {
        // 参数传的商品
        List<Map<String,Object>> productMapList = actPromotionActivityDto.getActPromotionProductList();
        // 数据库存的商品
        List<ActPromotionProduct> productList = ActPromotionProduct.me().setFkActivityId(actPromotionActivityDto.getId()).find();
        ActDiscountUseType actDiscountUseType = ActDiscountUseType.me().setDiscountUseTypeCode(actPromotionActivityDto.getDiscountTypeId()).get();
        //若不存在则做新增操做
        if (productList.isEmpty()) {
            if (!productMapList.isEmpty()) {
                productMapList.forEach(map -> {
//                    if (null != map.get("product_mall_price")) {
//                        map.put("product_mall_price", Helper.transformY2F(new BigDecimal(map.get("product_mall_price").toString())));
//                    }
//                    if (null != map.get("product_market_price")) {
//                        map.put("product_market_price", Helper.transformY2F(new BigDecimal(map.get("product_market_price").toString())));
//                    }
//                    if (null != map.get("promotion_after_price")) {
//                        map.put("promotion_after_price", Helper.transformY2F(new BigDecimal(map.get("promotion_after_price").toString())));
//                    }
                    if (null != map.get("discount_value")) {
                        map.put("discount_value", Helper.transformY2F(new BigDecimal(map.get("discount_value").toString())));
                    }
                    ActPromotionProduct actPromotionProduct = JSONObject.parseObject(JSON.toJSONString(map), ActPromotionProduct.class);
                    actPromotionProduct.setFkActivityId(actPromotionActivityDto.getId());
                    actPromotionProduct.setAddTime(Utils.currentTimeSecond());
                    actPromotionProduct.setAddUserId(curEmployee.getId());
                    actPromotionProduct.setAddUserName(curEmployee.getEmployeeName());
                    actPromotionProduct.setActivityName(actPromotionActivityDto.getActivityName());
                    actPromotionProduct.setDiscountTypeId(actDiscountUseType.getId());
                    actPromotionProduct.setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName());
                    actPromotionProduct.insert();
                });
            }
        } else {
            List<String> idList = new ArrayList<>();
            // 获取已存在的优惠信息id
            productList.forEach(id -> {
                idList.add(id.getId());
            });
            // 遍历操作的优惠信息数据
            productMapList.forEach(map -> {
                //判断该数据是否存在优惠信息表
//                if (null != map.get("product_mall_price")) {
//                    map.put("product_mall_price", Helper.transformY2F(new BigDecimal(map.get("product_mall_price").toString())));
//                }
//                if (null != map.get("product_market_price")) {
//                    map.put("product_market_price", Helper.transformY2F(new BigDecimal(map.get("product_market_price").toString())));
//                }
//                if (null != map.get("promotion_after_price")) {
//                    map.put("promotion_after_price", Helper.transformY2F(new BigDecimal(map.get("promotion_after_price").toString())));
//                }
                if (null != map.get("discount_value")) {
                    map.put("discount_value", Helper.transformY2F(new BigDecimal(map.get("discount_value").toString())));
                }
                ActPromotionProduct info = JSONObject.parseObject(JSON.toJSONString(map), ActPromotionProduct.class);
                info.setActivityName(actPromotionActivityDto.getActivityName());
                info.setDiscountTypeId(actDiscountUseType.getId());
                info.setDiscountTypeName(actDiscountUseType.getDiscountUseTypeName());
                if (StringUtils.isNotEmpty(info.getId())) {
                    //若存在列表则移除
                    if (idList.contains(info.getId())) {
                        idList.remove(idList.indexOf(info.getId()));
                    }
                    info.setOperationTime(Utils.currentTimeSecond());
                    info.setOperationUserId(curEmployee.getId());
                    info.setOperationUserName(curEmployee.getEmployeeName());
                } else {
                    info.setAddTime(Utils.currentTimeSecond());
                    info.setAddUserId(curEmployee.getId());
                    info.setAddUserName(curEmployee.getEmployeeName());
                    info.setFkActivityId(actPromotionActivityDto.getId());
                }
                info.insertOrUpdate();
            });
            //id集合若不为空则删除
            if (!idList.isEmpty()) {
                idList.forEach(id -> {
                    ActPromotionProduct.me().setId(id).delete();
                });
            }
        }
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    public R deleteActReduceActivity(List<String>ids){
        if (ids.isEmpty()) {
            return R.error("id获取失败!");
        }
        ids.forEach(id -> {
            ActPromotionActivity.me().setId(id).delete();
            ActPromotionProduct.me().setFkActivityId(id).delete();
        });
        return R.ok();
    }


    /**
     * 获取限时活动详情
     * @param id
     * @return
     */
    public ActPromotionActivity getPromotionActivity(String id){

        ActPromotionActivity actPromotionActivity = ActPromotionActivity.me().setId(id).get();

        if(!Objects.isNull(actPromotionActivity)){
            List<ActPromotionProduct> actPromotionProductList = ActPromotionProduct.me().setFkActivityId(id).find();
            // 处理商品数据
            this.handleProduct(actPromotionProductList);
            actPromotionActivity.addExten("productList",actPromotionProductList);
        }else{
            actPromotionActivity.addExten("productList",null);
        }
        return actPromotionActivity;
    }


    /**
     * 处理限时活动商品数据
     * @param actPromotionProductList
     */
    public void handleProduct(List<ActPromotionProduct> actPromotionProductList) {
        if (!actPromotionProductList.isEmpty()) {
            actPromotionProductList.forEach(product -> {

                ProProductMaster proProductMaster = ProProductMaster.me().setId(product.getFkProductId()).get();
                if(Objects.nonNull(proProductMaster)){
                    List<ProProductStock> stockList = ProProductStock.me().setFkProductId(product.getFkProductId()).find();
                    List<Long> price = new ArrayList<>();
                    List<Long> marketPrice = new ArrayList<>();
                    int stockNumber = 0;
                    for (ProProductStock stock : stockList) {
                        price.add(stock.getProductMallPrice());
                        marketPrice.add(stock.getProductMarketPrice());
                        if (stock.getProductStockNumber() != null) {
                            stockNumber += stock.getProductStockNumber();
                        }
                    }
                    product.addExten("productPicImageUrl", proProductMaster.getPrductPicImageUrl());
                    product.addExten("productName", proProductMaster.getProductName());
                    product.addExten("productStockNumber", stockNumber);
                    handlePice(product, price, marketPrice);
                }
                if (product.getDiscountValue() != null) {
                    product.addExten("discountValue", Helper.transformF2Y(product.getDiscountValue()));
                } else {
                    product.addExten("discountValue", 0);
                }

//                if (product.getProductMallPrice() != null) {
//                    product.addExten("productMallPrice", Helper.transformF2Y(product.getProductMallPrice()));
//                } else {
//                    product.addExten("productMallPrice", Helper.transformF2Y(0));
//                }
//                if (product.getProductMarketPrice() != null) {
//                    product.addExten("productMarketPrice", Helper.transformF2Y(product.getProductMarketPrice()));
//                } else {
//                    product.addExten("productMarketPrice", Helper.transformF2Y(0));
//                }
//                if (product.getPromotionAfterPrice() != null) {
//                    product.addExten("promotionAfterPrice", Helper.transformF2Y(product.getPromotionAfterPrice()));
//                } else {
//                    product.addExten("promotionAfterPrice", Helper.transformF2Y(0));
//                }
//                if (product.getDiscountValue() != null) {
//                    product.addExten("discountValue", Helper.transformF2Y(product.getDiscountValue()));
//                } else {
//                    product.addExten("discountValue", 0);
//                }
            });
        }
    }

    private void handlePice(ActPromotionProduct info, List<Long> price, List<Long> marketPrice) {
        if (!price.isEmpty()) {
            info.addExten("productMallPriceMax", Helper.transformF2Y(Collections.max(price)).toString());
            info.addExten("productMallPriceMin", Helper.transformF2Y(Collections.min(price)).toString());
        } else {
            info.addExten("productMallPriceMax", 0);
            info.addExten("productMallPriceMin", 0);
        }
        if (!marketPrice.isEmpty()) {
            info.addExten("productMarketPriceMax", Helper.transformF2Y(Collections.max(marketPrice)).toString());
            info.addExten("productMarketPriceMin", Helper.transformF2Y(Collections.min(marketPrice)).toString());
        } else {
            info.addExten("productMarketPriceMax", 0);
            info.addExten("productMarketPriceMin", 0);
        }
    }

    public Map<String, Object> getActivityMap(String productId) {
        Map<String, Object> map = new HashMap<>(1);
        ActPromotionCommond actPromotionCommond = new ActPromotionCommond();
        actPromotionCommond.setEndTime(Utils.currentTimeSecond());
        actPromotionCommond.setType("2");
        List<ActPromotionActivity> activityLists = ActPromotionActivity.me().find(actPromotionCommond);
        if (!activityLists.isEmpty()) {
            activityLists.forEach(activity -> {
                ActPromotionProduct promotionProduct = ActPromotionProduct.me().setFkActivityId(activity.getId()).setFkProductId(productId).get();
                if (Objects.nonNull(promotionProduct)) {
                    if (promotionProduct.getProductCeilNumber() > 0) {
                        List<OrdSalesOrderDetail> ordOrderItems = OrdSalesOrderDetail.me().setPromotionActivityId(activity.getId()).setFkProductId(productId).find();
                        Long count = 0L;
                        if (!ordOrderItems.isEmpty()) {
                            for (OrdSalesOrderDetail orderItem : ordOrderItems) {
                                count += orderItem.getProductNum();
                            }
                        }
                        if (promotionProduct.getProductCeilNumber() > count) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            map.put("endTime", sdf.format(new Date(Long.parseLong(activity.getEndTime() + "000"))));
                        }
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        map.put("endTime", sdf.format(new Date(Long.parseLong(activity.getEndTime() + "000"))));
                    }
                }
            });
        }
        return map;
    }
}
