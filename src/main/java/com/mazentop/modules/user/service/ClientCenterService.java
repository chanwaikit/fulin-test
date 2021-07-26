package com.mazentop.modules.user.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.modules.emp.commond.CliClienteleGoodsTrailCommond;
import com.mazentop.modules.emp.commond.OrdSalesOrderCommond;
import com.mazentop.modules.emp.commond.SysCountryProvinceCityCommand;
import com.mazentop.modules.user.commond.UserOrderCommond;
import com.mazentop.modules.web.User;
import com.mazentop.modules.user.commond.ActGetCouponRecordCommond;
import com.mazentop.modules.web.commond.CliCollectorItemCommond;
import com.mazentop.modules.user.commond.OrdPaymentRecordCommond;
import com.mazentop.modules.web.dto.CountryDTO;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.cache.Options;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cq
 * 用户中心service
 */

@Service
public class ClientCenterService {

    @Autowired
    private BaseDao baseDao;

    public static final String QUERY_CLIENT_COMMON_SQL = "SELECT * FROM cli_clientele_info ";

    /**
     * 根据用户ID 查询用户信息
     *
     * @param clientId 客户id
     * @return
     */
    public CliClienteleInfo queryByClientId(String clientId) {

        String querySql = QUERY_CLIENT_COMMON_SQL + "where id = :id";
        return baseDao.queryForBean(querySql, CliClienteleInfo.me().setId(clientId));
    }


    /**
     * 查询系统信息
     *
     * @param clientId 客户ID
     * @return
     */
    public List<SysMessage> querySystemMessage(String clientId) {
        List<SysMessage> sysMessageList = SysMessage.me().setOrderByFields(" is_to_top and add_time desc").find();
        List<SysMessageStatus> readSysMessageList = SysMessageStatus.me().setFkClienteleId(clientId)
                .setIsDelete(1).find();
        if (readSysMessageList.size() < 1) {
            return sysMessageList;
        }

        Iterator<SysMessage> sysMessageIterator = sysMessageList.iterator();


        while (sysMessageIterator.hasNext()) {
            SysMessage message = sysMessageIterator.next();
            readSysMessageList.forEach(s -> {
                if (s.getFkSysMessageId().equals(message.getId())) {
                    sysMessageIterator.remove();
                }
            });
        }
        return sysMessageList;
    }


    /**
     * 根据消息ID对消息进行逻辑已查看状态更改
     *
     * @param messageId 消息id
     * @param clientId  用户id
     * @param isDelete  是否删除 1删除 0 未删除
     * @param isRead    是否查看 1查看 0未查看
     * @return
     */
    public boolean systemMessageStatusEdit(String messageId, String clientId, Integer isDelete, Integer isRead) {
        SysMessageStatus sysMessageStatus = SysMessageStatus.me().setFkSysMessageId(messageId)
                .setFkClienteleId(clientId).get();
        if (Objects.isNull(sysMessageStatus)) {
            return SysMessageStatus.me().setFkSysMessageId(messageId)
                    .setFkClienteleId(clientId).setIsDelete(isDelete).setIsRead(isRead).insert() > 0;
        } else {
            return SysMessageStatus.me().setId(sysMessageStatus.getId()).setFkSysMessageId(messageId)
                    .setFkClienteleId(clientId).setIsDelete(isDelete).setIsRead(isRead).update() > 0;
        }
    }

    /**
     * 查询客户购物车物品
     *
     * @param clientId 用户ID
     * @return
     */
    public List<OrdShoppingCart> queryShopping(String clientId) {
        return OrdShoppingCart.me().setFkClienteleId(clientId).find();
    }


    public Map<String, Object> helpContent() {
        // 获取所有的帮助类别
        List<SysHelpCenterType> sysHelpCenterTypes = SysHelpCenterType.me().setOrderByFields(SysHelpCenterType.F_SORT).find();
        if (sysHelpCenterTypes.size() < 1) {
            return Maps.newHashMap();
        }
        List<SysHelpCenterContent> sysHelpCenterContents = SysHelpCenterContent.me().setOrderByFields(SysHelpCenterContent.F_SORT).find();
        HashMap<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("sysHelpTitle", sysHelpCenterTypes);
        resultMap.put("sysHelpContent", sysHelpCenterContents);

        return resultMap;
    }

    /**
     * 根据客户ID分页查询订单
     *
     * @return
     */
    public Map<String, Object> queryOrderPageList(UserOrderCommond userOrderCommond) {
        userOrderCommond.setIsEnable(1);
        if(!Objects.isNull(userOrderCommond.getOrderStatusList()) && !userOrderCommond.getOrderStatusList().isEmpty()) {
            userOrderCommond.setOrderStatus(null);
        }
        userOrderCommond.setO("-" + OrdSalesOrder.F_ADD_TIME);

        List<OrdSalesOrder> ordSalesOrders = OrdSalesOrder.me().find(userOrderCommond);

        if (ordSalesOrders.isEmpty()) {
            return Maps.newHashMap();
        }

        StringBuilder orderDetailSql = new StringBuilder("select * from ord_sales_order_detail where fk_sales_order_id in (");
        ordSalesOrders.forEach(ordSalesOrder-> {
            ordSalesOrder.addExten("orderStatusDesc", OrdSalesOrderStatusEnum.getDescEn(ordSalesOrder.getSalesOrderStatus()));
            orderDetailSql.append("'").append(ordSalesOrder.getId()).append("',");
            ordSalesOrder.addExten("payment",Helper.transformF2Y(ordSalesOrder.getTotalPaymentFree()));
            ordSalesOrder.addExten("orderStatus", OrdSalesOrderStatusEnum.getDescEn(ordSalesOrder.getSalesOrderStatus()));
            if("01".equals(ordSalesOrder.getSalesOrderStatus())){
                OrdPaymentRecord ordPaymentRecord = OrdPaymentRecord.me().setOrderId(ordSalesOrder.getSalesOrderNo()).get();
                if(!Objects.isNull(ordPaymentRecord)){
                    String webDomain = Options.get("site_setting_domain_name");
                    String url = webDomain+"/jumpSettlement/"+ordPaymentRecord.getBalanceTheBooksNo()+"?step=payment";
                    ordSalesOrder.addExten("payUrl",url);
                }
            }
        });

        List<Map<String, Object>> ordSalesOrderDetails = baseDao.queryForList(orderDetailSql.substring(0, orderDetailSql.length() - 1) + ")");
            ordSalesOrderDetails.forEach(item -> {
                // 判断商品是否下架或删除，则标志
                ProProductMaster productMaster = ProProductMaster.me().setId(item.get("fk_product_id").toString()).get();
                if(Objects.isNull(productMaster) || productMaster.getIsShelve() == 0){
                    item.put("isEnable",BooleanEnum.FALSE.getValue());
                }
                if (Objects.nonNull(item.get("fk_product_id"))) {
                    item.put("seoUrl",Seo.getSeoUrlForPorduct(item.get("fk_product_id").toString()));
                }
            });

        Map<String, Object> map = Maps.newHashMap();
        map.put("OrdSalesOrder", new Page<>(ordSalesOrders, userOrderCommond));
        map.put("commond", userOrderCommond);
        map.put("ordSalesOrderDetails", orderDetailAmountHandle(ordSalesOrderDetails));

        return map;
    }


    /**
     * 订单详情类的金额处理
     *
     * @param
     */
    private List<Map<String, Object>> orderDetailAmountHandle(List<Map<String, Object>> maps) {
        if (Objects.isNull(maps)) {
            return Lists.newArrayList();
        }

        Iterator<Map<String, Object>> iterator = maps.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> m = iterator.next();
            // product_market_price 市场价
            if (null != m.get("product_market_price")) {
                m.put("product_market_price", Helper.transformF2Y(m.get("product_market_price")));
            }
            // product_mall_price 商品价
            if (null != m.get("product_mall_price")) {
                m.put("product_mall_price", Helper.transformF2Y(m.get("product_mall_price")));
            }

            // product_activity_price 活动价
            if (null != m.get("product_activity_price")) {
                m.put("product_activity_price", Helper.transformF2Y(m.get("product_activity_price")));
            }

            // 判断是否有活动
            if (null != m.get("promotion_activity_id")) {
                m.put("totalAmount", new BigDecimal(m.get("product_activity_price").toString())
                        .multiply(new BigDecimal(m.get("product_num").toString())));
            } else {
                m.put("totalAmount", new BigDecimal(m.get("product_mall_price")
                        .toString()).multiply(new BigDecimal(m.get("product_num").toString())));
            }
        }

        return maps;
    }


    /**
     *
     * @param orderId
     * @return
     */
    public Map<String, Object> queryOrderDetail(String orderId) {

        OrdSalesOrder ordSalesOrder = OrdSalesOrder.me().setId(orderId).get();
        List<OrdSalesOrderDetail> ordSalesOrderDetails = OrdSalesOrderDetail.me().setFkSalesOrderId(orderId).find();
        String addressSql = String.format("SELECT a.country as `countryCode`, a.province as `province` ,a.address as `address`," +
                " c.name as `countryName` , a.city AS `city` from cli_clientele_receiver_address a INNER JOIN sys_country c" +
                " on a.country = c.alpha3 WHERE a.id = '%s'", ordSalesOrder.getFkClienteleAddressId());

        // 总数量
        BigDecimal totalNumber = new BigDecimal(0);
        // 订单总价格
        BigDecimal orderTotalAmount = new BigDecimal(0);
        // 订单总折扣价格
        BigDecimal orderDiscountAmount = new BigDecimal(0);

        for (OrdSalesOrderDetail ordSalesOrderDetail : ordSalesOrderDetails) {

            BigDecimal productMallPrice = new BigDecimal(0);
            BigDecimal productMarketPrice = new BigDecimal(0);
            BigDecimal productActivityPrice = new BigDecimal(0);
            BigDecimal productNumber = new BigDecimal(ordSalesOrderDetail.getProductNum());

            if (ordSalesOrderDetail.getProductMallPrice() != null) {
                productMallPrice = Helper.transformF2Y(ordSalesOrderDetail.getProductMallPrice());
                ordSalesOrderDetail.addExten("productMallPrice", productMallPrice);
            }
            if (ordSalesOrderDetail.getProductMarketPrice() != null) {
                productMarketPrice = Helper.transformF2Y(ordSalesOrderDetail.getProductMarketPrice());
                ordSalesOrderDetail.addExten("productMarketPrice", productMarketPrice);
            }

            if (ordSalesOrderDetail.getProductActivityPrice() != null) {
                productActivityPrice = Helper.transformF2Y(ordSalesOrderDetail.getProductActivityPrice());
                ordSalesOrderDetail.addExten("productActivityPrice", productActivityPrice);
            }

            BigDecimal discountAmount;
            BigDecimal productMallSumAmount = productMallPrice.multiply(productNumber);
            ordSalesOrderDetail.addExten("totalAmount", productMallSumAmount);
            if (ordSalesOrderDetail.getPromotionActivityId() != null) {
                discountAmount = (productActivityPrice.multiply(productNumber));
                ordSalesOrderDetail.addExten("discountAmount", discountAmount);
            } else {
                discountAmount = new BigDecimal(0);
                ordSalesOrderDetail.addExten("discountAmount", discountAmount);
            }
            totalNumber = totalNumber.add(productNumber);
            orderTotalAmount = orderTotalAmount.add(productMallSumAmount);
            orderDiscountAmount = orderDiscountAmount.add(discountAmount);

            ordSalesOrderDetail.addExten("seoUrl",Seo.getSeoUrlForPorduct(ordSalesOrderDetail.getFkProductId()));
        }

        ordSalesOrder.setTotalTransportsFree(Helper.transformF2Y(ordSalesOrder.getTotalTransportsFree()).longValue());
        ordSalesOrder.addExten("orderStatus", OrdSalesOrderStatusEnum.getDescEn(ordSalesOrder.getSalesOrderStatus()));
        ordSalesOrder.addExten("totalNumber", totalNumber.toString());
        ordSalesOrder.addExten("orderTotalAmount", orderTotalAmount.toString());
        ordSalesOrder.addExten("orderDiscountAmount", orderDiscountAmount.toString());
        ordSalesOrder.addExten("actualAmount", orderTotalAmount.subtract(orderDiscountAmount));
        ordSalesOrder.addExten("taxAmount", Helper.transformF2Y(ordSalesOrder.getTaxAmount()));
        Map<String, Object> address = baseDao.queryForMap(addressSql);

        if (address.size() < 1) {
            String notLoginAddressSql = String.format("SELECT a.country AS `countryCode`,a.province AS " +
                    " `province`,a.city AS `city`,a.address AS `address`,c.NAME AS `countryName`" +
                    " FROM sys_not_login_purchase_info a INNER JOIN sys_country c ON a.country=c.alpha3 " +
                    " WHERE a.not_login_order_no='%s'", ordSalesOrder.getSalesOrderNo());
            address = baseDao.queryForMap(notLoginAddressSql);
        }

        Map<String, Object> map = Maps.newHashMap();
        map.put("ordSalesOrder", ordSalesOrder);
        map.put("ordSalesOrderDetail", ordSalesOrderDetails);
        map.put("address", address);

        if (ordSalesOrder.getSalesOrderStatus().equals(OrdSalesOrderStatusEnum.CANCEL_COMPLETE.status())) {
            map.put("refundRequest", OrdRefundRequest.me().setFkSalesOrderId(ordSalesOrder.getId()).get());
        }

        return map;
    }

    /**
     * z
     * 退款申请
     *
     * @param ordRefundRequest
     * @param id
     * @return
     */
    public Result refundOrder(OrdRefundRequest ordRefundRequest, String id) {

        OrdRefundRequest queryRefundOrder = OrdRefundRequest.me().setSalesOrderNo(ordRefundRequest.getSalesOrderNo()).get();
        if (Objects.nonNull(queryRefundOrder)) {
            if (queryRefundOrder.getRefundRequestStatus().equals("03")) {
                return Result.toast("该订单已被审核拒绝，无法发起二次申请");
            }
            return Result.toast("该订单正在退款流程中，请勿重复申请");
        }

        OrdSalesOrder ordSalesOrder = OrdSalesOrder.me().setSalesOrderNo(ordRefundRequest.getSalesOrderNo()).get();
        ordRefundRequest.setCompanyId(ordSalesOrder.getCompanyId())
                .setFkSalesOrderId(ordSalesOrder.getId())
                .setSalesOrderNo(ordSalesOrder.getSalesOrderNo())
                .setCompanyName(ordSalesOrder.getCompanyName())
                .setFkClienteleId(id)
                .setRefundRequestStatus("01")
                .setClientName(ordSalesOrder.getClientName())
                .setClientPhone(ordSalesOrder.getClientPhone())
                .setClientEmail(ordSalesOrder.getClientEmail())
                .setCurrency(ordSalesOrder.getCurrency())
                .setAddTime(Utils.currentTimeSecond())
                .setRefundAmount(ordSalesOrder.getTotalPaymentFree());

        ordSalesOrder.setSalesOrderStatus(OrdSalesOrderStatusEnum.REFUND_ING.status()).update();

        if (ordRefundRequest.insert() > 0) {
            return Result.build(() -> "申请成功");
        }
        return Result.toast("申请失败，请联系客服人员");
    }

    /**
     * 分页查询我的收藏
     *
     */
    public Page<CliCollectorItem> queryMeFavoriteList(CliCollectorItemCommond commond) {
        commond.setPageSize(6);
        commond.setCollectorItemTypeId("2");
        commond.setO("-" + CliCollectorItem.F_ADD_TIME);
        List<CliCollectorItem> cliCollectorItems = CliCollectorItem.me().find(commond);
        cliCollectorItems.forEach(item -> {
            item.addExten("seoUrl", Seo.getSeoUrlForPorduct(item.getFkProductId()));
            // 判断商品是否下架或删除，则标志
            ProProductMaster productMaster = ProProductMaster.me().setId(item.getFkProductId()).get();
            if(Objects.isNull(productMaster) || productMaster.getIsShelve() == 0){
                item.setIsEnable(BooleanEnum.FALSE.getValue());
            }
        });
        return new Page<>(cliCollectorItems, commond);
    }

    /**
     * 分页查询优惠卷
     *
     * @param commond
     * @return
     */
    public Page<ActGetCouponRecord> queryCouponList(ActGetCouponRecordCommond commond) {
        List<ActGetCouponRecord> actGetCouponRecords = ActGetCouponRecord.me().find(commond);
        actGetCouponRecords.forEach(couponRecord -> {
            ActConditionType conditionType = ActConditionType.me().setId(couponRecord.getFkConditionTypeId()).get();
            couponRecord.addExten("typeName", conditionType.getDiscountTypeName());
        });
        return new Page<>(actGetCouponRecords, commond);
    }

    public Page<OrdPaymentRecord> queryPaymentRecord(OrdPaymentRecordCommond commond) {
        commond.setOrderBy("-" + OrdPaymentRecord.F_ADD_TIME);

        List<OrdPaymentRecord> ordPaymentRecords = OrdPaymentRecord.me().find(commond);
        ordPaymentRecords.forEach(o -> {
            o.addExten("productTotalPrice", Helper.transformF2Y(o.getProductTotalPrice()));
            o.addExten("productDiscountPrice", Helper.transformF2Y(o.getProductDiscountPrice()));
            o.addExten("freeSchemeTotalPrice", Helper.transformF2Y(o.getFreeSchemeTotalPrice()));
            o.addExten("paymentTotalPrice", Helper.transformF2Y(o.getPaymentTotalPrice()));
        });
        return new Page<>(ordPaymentRecords, commond);
    }

    /**
     * 新增商品收藏
     *
     * @param id          客户ID
     * @param commodityId 商品ID
     */
    public boolean addFavorite(String id, String commodityId) {
        CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setId(id).get();
        ProProductMaster master = ProProductMaster.me().setId(commodityId).get();

        CliCollectorItem collectorItem = new CliCollectorItem();
        collectorItem.setFkClienteleId(clienteleInfo.getId());
        collectorItem.setClientName(clienteleInfo.getClientName());
        collectorItem.setFkCollectorItemTypeId("2");
        collectorItem.setCollectorItemTypeName("商品");
        collectorItem.setFkProductId(master.getId());
        collectorItem.setProductName(master.getProductName());
        collectorItem.setProductImageUrl(master.getPrductPicImageUrl());
        collectorItem.setIsEnable(1);
        collectorItem.setAddTime(Utils.currentTimeSecond());
        return collectorItem.insert() > 1;
    }

    /**
     * 订单召回
     */
    public void orderRecall(Integer beforeDay) {
        LocalDate currentTime = LocalDate.now();
        LocalDateTime beforeTime = currentTime.minusDays(beforeDay).atTime(LocalTime.MIN);
        String sql = "";
        if(beforeDay == 1) {
             sql = String.format("select * from ord_balance_the_books where  add_time<= %s AND is_discard = 0 and is_recall = 0 and is_need_recall = 1",
                    Helper.localDateTimeToTimestamp(beforeTime) + 60 * 60 * 12);
        }else{
              sql = "select * from ord_balance_the_books where   is_discard = 0 and is_recall = 0 and is_need_recall = 1";
        }
        /*   List<OrdShoppingCart> ordShoppingCarts = baseDao.queryForBeanList(sql, OrdShoppingCart.me());*/
        List<OrdBalanceTheBooks> ordBalanceTheBooks = baseDao.queryForBeanList(sql, OrdBalanceTheBooks.me());
        if (!ordBalanceTheBooks.isEmpty()) {
            ordBalanceTheBooks.forEach(theBooks -> {
                Long count = OrdRecallRecord.me().setCatList(theBooks.getShoppingCartList()).findCount();
                if(count<=0) {
                    addRecall(theBooks);
                }
            });
        }
    }
    public R synchronizationRecord(Integer beforeDay){
        orderRecall(beforeDay);
        return R.ok();
    }

    public Map<String,Object> clienteleByOrder(String clienteleId){
        Map<String,Object> map=new HashMap<>();
        List<OrdPaymentRecord> list = OrdPaymentRecord.me().setFkClienteleId(clienteleId).setOrderByFields("payment_time desc").find();
        Long count = OrdPaymentRecord.me().setFkClienteleId(clienteleId).findCount();
        if (list.size()>0){
            OrdPaymentRecord ordPaymentRecord = list.get(0);
            ordPaymentRecord.getPaymentTime();
            Map<String, Long> distanceTime = Helper.getDistanceTime(ordPaymentRecord.getPaymentTime(), System.currentTimeMillis());
            StringBuilder time=new StringBuilder();
            if (distanceTime.containsKey("day")){
                time.append(distanceTime.get("day")+"天");
            }
            if (distanceTime.containsKey("hour")){
                time.append(distanceTime.get("hour")+"小时");
            }
            if (distanceTime.containsKey("20")){
                time.append(distanceTime.get("20")+"分");
            }
            if (distanceTime.containsKey("sec")){
                time.append(distanceTime.get("sec")+"秒");
            }
            time.append("前");
            map.put("recent",time.toString());
        }else {
            map.put("recent","暂无订单");
        }
        long totalPrice=0;
        for (OrdPaymentRecord ordPaymentRecord : list) {
            totalPrice=totalPrice+ordPaymentRecord.getPaymentTotalPrice();
        }
        BigDecimal price = Helper.transformF2Y(totalPrice);
        if (price.signum()>0){
            map.put("price",price.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP));
        }else {
            map.put("price",0);
        }
        map.put("sumPrice",price);
        map.put("countOrder",count);
        return map;
    }

    private void addRecall(OrdBalanceTheBooks books) {
        Long count = OrdRecallRecord.me().setFkBalanceTheBooksId(books.getId()).findCount();
        if(count<=0) {
            CliClienteleInfo clientInfo = CliClienteleInfo.me().setId(books.getFkClienteleId()).get();

            OrdRecallRecord record = new OrdRecallRecord();
            record.setIsSendSuccess(0)
                    .setRecallStatus("0")
                    .setAddTime(Utils.currentTimeSecond());
            record.setFkBalanceTheBooksId(books.getId());
            if (Objects.nonNull(clientInfo)) {
                record.setFkClienteleId(clientInfo.getId())
                        .setClientName(clientInfo.getClientSurname() + clientInfo.getClientName())
                        .setClientEmail(clientInfo.getEmail())
                        .setClientPhone(clientInfo.getPhone());
            }

            CliClienteleInfo receiptClient = CliClienteleInfo.me().setId(books.getFkClienteleId()).get();

            record.setFkClienteleAddressId(books.getFkClienteleReceiverAddressId())
                    .setReceiverCountry(books.getCountryName());

            CliClienteleReceiverAddress address = CliClienteleReceiverAddress.me().setId(record.getFkClienteleAddressId()).get();
            if (Objects.nonNull(address)) {
                record.setReceiverProvince(address.getProvince())
                        .setReceiverCity(address.getCity())
                        .setReceiverSheet(address.getSheet())
                        .setReceiverAddress(address.getAddress());

            }
            if (Objects.nonNull(receiptClient)) {
                record.setReceiverName(receiptClient.getClientSurname() + receiptClient.getClientName())
                        .setReceiverId(receiptClient.getId())
                        .setReceiverEmail(receiptClient.getEmail())
                        .setReceiverPhone(receiptClient.getPhone());
            }
            record.setFkBalanceTheBooksTime(books.getAddTime());
            record.setCatList(books.getShoppingCartList());
            record.insert();
        }
    }


    /**
     * 根据国家名称查询国家地址
     *
     * @param name
     * @return
     */
    public List<Map<String, Object>> findByCountryQuery(String name) {
        if (StringUtils.isBlank(name)) {
            return baseDao.queryForList("SELECT * FROM sys_country");
        }

        String sql = String.format("SELECT * FROM sys_country WHERE name like '%s' or name_cn like UPPER('%s')", name + "%", name + "%");
        return baseDao.queryForList(sql);

    }

    /**
     * 根据国家DTO 查询国家下的省份以及城市
     *
     * @param countryDTO 国家信息DTO
     * @return Result
     */
    public Result findByCountryDTOQueryCountryList(CountryDTO countryDTO) {
        Map<String, Object> countryMap = Maps.newHashMap();
        SysCountryProvinceCityCommand command = new SysCountryProvinceCityCommand();
        command.setCountrySort(countryDTO.getCountrySort());
        command.setProvinceEn(countryDTO.getProvinceName());
        command.setCity(countryDTO.getCityName());
        List<SysCountryProvinceCity> sysCountryProvinceCities = SysCountryProvinceCity.me().find(command);


        ArrayList<SysCountryProvinceCity> countrys = sysCountryProvinceCities.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(SysCountryProvinceCity::getCountrySort))), ArrayList::new));
        countryMap.put("country", countrys);


        ArrayList<SysCountryProvinceCity> provinces = sysCountryProvinceCities.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(SysCountryProvinceCity::getProvinceEn))), ArrayList::new));
        if (provinces.size() > 0 && StringUtils.isNotBlank(provinces.get(0).getProvinceEn())) {
            countryMap.put("province", provinces);
        } else {
            countryMap.put("province", Lists.newArrayList());
        }

        ArrayList<SysCountryProvinceCity> citys = sysCountryProvinceCities.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(SysCountryProvinceCity::getCity))), ArrayList::new));
        countryMap.put("city", citys);

        return Result.build(() -> countryMap);
    }

    /**
     * 流水详情
     *
     * @param paymentId 流水ID
     * @return
     */
    public Result queryPaymentRecordDetail(String paymentId) {

        OrdPaymentRecord payment = OrdPaymentRecord.me().setId(paymentId).get();
        payment.addExten("productTotalPrice", Helper.transformF2Y(payment.getProductTotalPrice()));
        payment.addExten("productDiscountPrice", Helper.transformF2Y(payment.getProductDiscountPrice()));
        payment.addExten("freeSchemeTotalPrice", Helper.transformF2Y(payment.getFreeSchemeTotalPrice()));
        payment.addExten("paymentTotalPrice", Helper.transformF2Y(payment.getPaymentTotalPrice()));
        payment.addExten("addTime", Helper.timeStampFormat(payment.getAddTime()));
        if (1 == payment.getIsPaySuccess()) {
            payment.addExten("paySuccess", "支付成功");
        } else {
            payment.addExten("paySuccess", "支付失败");
        }

        if (StringUtils.isNotBlank(payment.getPaymentReturnRecord())) {
            JSONObject paymentJson = JSONObject.parseObject(payment.getPaymentReturnRecord());
            OrdSalesOrder order = OrdSalesOrder.me().setPaymentStreamNo(paymentJson.get("paymentId").toString()).get();
            if (Objects.nonNull(order)) {
                payment.addExten("currency", order.getCurrency());
            }
            payment.addExten("paymentId", paymentJson.get("paymentId"));
        }

        return Result.build(() -> payment);
    }

    public R editReceiptAddress(CliClienteleReceiverAddress address) {
        if (BooleanEnum.TRUE.getValue().equals(address.getIsDefault())) {
            CliClienteleReceiverAddress.me()
                    .setFkClienteleId(User.id()).setIsDefault(1)
                    .list()
                    .forEach(item -> CliClienteleReceiverAddress.me()
                            .setId(item.getId()).setIsDefault(BooleanEnum.FALSE.getValue()).update());
        }
        address.setFkClienteleId(User.id());
        address.insertOrUpdate();
        return R.ok();
    }


    /**
     * 根据ID查询优惠券详情信息
     *
     * @param id 优惠券ID
     * @return Result
     */
    public Result findByIdQueryCouponDetails(String id) {

        ActCouponActivity actCouponActivity = ActCouponActivity.me().setId(id).get();
        if (Objects.isNull(actCouponActivity)) {
            return Result.toast("coupon id error No coupons found");
        }
        List<ActConditionType> actConditionTypes = ActConditionType.me().setFkActivityId(actCouponActivity.getId()).find();
        actConditionTypes.forEach(c -> {
            c.addExten("typeCondition", Helper.transformF2Y(c.getTypeCondition()));
            c.addExten("discountValue", Helper.transformF2Y(c.getDiscountValue()));
        });
        actCouponActivity.addExten("actConditionTypes", actConditionTypes);
        actCouponActivity.addExten("startTime", Helper.timeStampFormat(actCouponActivity.getStartTime()));
        actCouponActivity.addExten("endTime", Helper.timeStampFormat(actCouponActivity.getEndTime()));

        return Result.build(() -> actCouponActivity);
    }

    public Result dynamicLoadingThirdPartyCode() {
        String pcShow = "1";
        SysOptions thirdPartyCodeTypeName = SysOptions.me().setOptionKey("third_party_code_type_name").get();
        if (pcShow.equals(thirdPartyCodeTypeName.getOptionValue())) {
            SysOptions thirdPartyCodeContent = SysOptions.me().setOptionKey("third_party_code_content").get();
            return Result.build(() -> thirdPartyCodeContent);
        }
        return Result.success();
    }

    /**
     * 查询商品浏览记录
     *
     */
    public Page<CliClienteleGoodsTrail> queryGoodsTrailsList(CliClienteleGoodsTrailCommond command) {

        command.setO("-" + CliClienteleGoodsTrail.F_ADD_TIME);
        List<CliClienteleGoodsTrail> cliClienteleGoodsTrails = CliClienteleGoodsTrail.me().find(command);

        cliClienteleGoodsTrails.forEach(trail -> {
            trail.addExten("addTime", Helper.timeStampFormat(trail.getAddTime()));
            ProProductMaster master = ProProductMaster.me().setId(trail.getFkGoodsId()).get();
            // 判断商品是否下架或删除，则标志
            if(Objects.isNull(master) || master.getIsShelve() == 0){
                trail.setIsEnable(BooleanEnum.FALSE.getValue());
            }
            trail.addExten("seoUrl", Seo.getSeoUrlForPorduct(trail.getFkGoodsId()));
        });
        return new Page<>(cliClienteleGoodsTrails, command);
    }
}
