package com.mazentop.modules.user.service;

import com.google.common.collect.Maps;
import com.mazentop.entity.CliClienteleGoodsTrail;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.plugins.seo.Seo;
import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author cq
 * 订单service
 */
@Service
public class OrderService {

    @Autowired
    private BaseDao baseDao;

    /**
     * 根据客户ID查询客户名下各个状态的订单数量
     *
     * @param clientId 客户id
     * @return
     */
    public Map<String, Integer> queryOrderStatusCount(String clientId) {

        String countOrderStatusSql = String.format("select sales_order_status as orderStatus," +
                " count(*) as `count`  FROM ord_sales_order  WHERE fk_clientele_id = '%s' " +
                "GROUP BY sales_order_status", clientId);

        List<Map<String, Object>> basMap = baseDao.queryForList(countOrderStatusSql);

        Map<String, Integer> countOrderStatusMap = Maps.newHashMap();
        countOrderStatusMap.put("needPay", 0);
        countOrderStatusMap.put("notShipped", 0);
        countOrderStatusMap.put("shipped", 0);
        basMap.forEach(c -> {
            if (OrdSalesOrderStatusEnum.CREATE_COMPLETE.status().equals(c.get("orderStatus"))) {
                countOrderStatusMap.put("needPay", Integer.parseInt(c.get("count").toString()));
            }
            if (OrdSalesOrderStatusEnum.DELIVER_ING_COMPLETE.status().equals(c.get("orderStatus"))) {
                countOrderStatusMap.put("notShipped", Integer.parseInt(c.get("count").toString()));
            }
            if (OrdSalesOrderStatusEnum.DELIVER_COMPLETE.status().equals(c.get("orderStatus"))) {
                countOrderStatusMap.put("shipped", Integer.parseInt(c.get("count").toString()));
            }
        });
        return countOrderStatusMap;
    }


    public Map<String, Object> queryNewestOrderInformation(String clientId) {

        String queryOrderNo = String.format("select id, sales_order_no as `salesOrderNo`, sales_order_status as `salesOrderStatus`," +
                " transports_no as `transportsNo`, transports_channel_name as `transportsChannelName`from ord_sales_order " +
                "where fk_clientele_id = %s and sales_order_status in (03,04) ORDER BY FROM_UNIXTIME(payment_time)" +
                " DESC limit 3", clientId);
        List<Map<String, Object>> orderList = baseDao.queryForList(queryOrderNo);

        if(orderList.size() < 1){
            return Maps.newHashMap();
        }

        StringBuffer appendIn = new StringBuffer("(");
        orderList.forEach(c -> {
            appendIn.append("'").append(c.get("id")).append("',");
        });

        String queryOrderDetailSql = String.format("SELECT s1.sales_order_no AS `salesOrderNo`" +
                        ",s1.company_id AS `companyId` ,s1.company_name AS `companyName`" +
                        ",FROM_UNIXTIME(s1.payment_time) AS `paymentTime` ,detail.fk_product_id as `productId`" +
                        ",detail.product_name AS `productName`,detail.product_image_url AS `productImageUrl`" +
                        ",detail.product_num AS `ProductNum`,detail.product_market_price AS `productMarketPrice`" +
                        ",detail.product_mall_price AS `productMallPrice`,detail.product_activity_price " +
                        "AS `productActivityPrice` FROM ord_sales_order s1 INNER JOIN ord_sales_order_detail detail" +
                        " ON s1.id=detail.fk_sales_order_id AND s1.id IN %s " +
                        "ORDER BY FROM_UNIXTIME(s1.payment_time) DESC"
                , appendIn.substring(0, appendIn.length() - 1) + ")");

        List<Map<String, Object>> orderDetailMaps = baseDao.queryForList(queryOrderDetailSql);
        orderDetailMaps.forEach(c -> {
            if (Objects.nonNull(c.get("productId"))) {
                c.put("seoUrl", Seo.getSeoUrlForPorduct(c.get("productId").toString()));
            }

            BigDecimal totalAmount = new BigDecimal(c.get("productActivityPrice").toString()).multiply(new BigDecimal(c.get("ProductNum").toString())).setScale(2);

            orderList.forEach(o -> {
                if (o.get("salesOrderNo").equals(c.get("salesOrderNo"))) {
                    c.put("salesOrderStatus", OrdSalesOrderStatusEnum.getDescEn(o.get("salesOrderStatus").toString()));
                    o.put("totalAmount", new BigDecimal(o.get("totalAmount") == null ? "0" : o.get("totalAmount").toString()).add(totalAmount));
                }
            });
        });

        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("orderNo",orderList);
        resultMap.put("orderDetail",orderDetailMaps);
        return resultMap;
    }

    /**
     * 查询客户最近的浏览记录
     * @param clientId 客户ID
     * @param limit 条数
     * @return
     */
    public List<CliClienteleGoodsTrail>  queryGoodsTrail(String clientId,Integer limit){
        List<CliClienteleGoodsTrail> goodsTrails = CliClienteleGoodsTrail.me().setFkClienteleId(clientId).setOrderByFields("-" + CliClienteleGoodsTrail.F_ADD_TIME).setLimit(limit).find();
        for (CliClienteleGoodsTrail goodsTrail : goodsTrails) {
            ProProductMaster productMaster = ProProductMaster.me().setId(goodsTrail.getFkGoodsId()).get();
            if(!Objects.isNull(productMaster)) {
                goodsTrail.addExten("seoUrl", Seo.getSeoUrlForPorduct(productMaster.getId()));
            }
        }
        return goodsTrails;
    }







}
