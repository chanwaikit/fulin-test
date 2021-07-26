package com.mazentop.modules.emp.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.modules.emp.commond.CountCommond;
import com.mazentop.modules.emp.dto.BaseGroupPlotDto;
import com.mazentop.modules.emp.dto.BasePlotDto;
import com.mazentop.modules.emp.dto.EquipmentDto;
import com.mazentop.util.Helper;
import com.mazentop.util.Helper;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.SearchTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountSiteService {

    @Autowired
    BaseDao baseDao;

    /**
     * 统计概览
     * @param
     * @return
     */
    public Map<String, Object> countOverview(CountCommond commond) {
        //获取当天开始的时间戳
        Long startTime = Helper.dayTimeInMillis();
        Long endTime = Helper.plusMinuteData(new Date().getTime());
        // 获取昨天的时间戳
        Long yesterdayStartTime = Helper.Date2TimeStamp(Helper.getPastDate(2 - 1, new Date()), Helper.DATE_PATTERN3);
        Long yesterdayEndTime = Helper.Date2TimeStamp(Helper.formatDateByFormat(new Date(), Helper.DATE_PATTERN1), Helper.DATE_PATTERN1);

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>(4);
        param.put("startTime", startTime);
        param.put("endTime", endTime);

        //统计当天的利益
        String sumTotalPriceSql = "select sum(payment_total_price) as totalPrice  from ord_payment_record WHERE is_pay_success =1 and payment_time >=:startTime  and payment_time<=:endTime";
        Long currentTotalPrice = baseDao.queryForLong(sumTotalPriceSql, param);
        map.put("totalIncome", Helper.transformF2Y(currentTotalPrice));

        //统计当天的已出售的商品数量
        String productNumberSql = "SELECT sum(total_product_number) as productNumber  from  ord_sales_order WHERE sales_order_status = '03' and payment_time >=:startTime  and payment_time<=:endTime";
        Long currentProductNumber = baseDao.queryForLong(productNumberSql,param);
        map.put("productNumber", currentProductNumber);


        //统计当天的用户新增
        String insertUserSql = "select count(*)  from cli_clientele_info WHERE add_time >=:startTime and add_time<=:endTime";
        Long currentInsertUserNumber = baseDao.queryForLong(insertUserSql, param);
        map.put("countInsertUserNumber", currentInsertUserNumber);


        //统计当天的访问
        String countVisitSql = "select count( * ) from (select * from cli_clientele_log WHERE ip is not null and  add_time >= :startTime AND add_time <=:endTime GROUP BY ip) as count";
        Long currentVisitNumber = baseDao.queryForLong(countVisitSql, param);
        map.put("countVisitNumber", currentVisitNumber);

        param.put("startTime", yesterdayStartTime);
        param.put("endTime", yesterdayEndTime);

        // 较昨天上涨下铁
        // 计算总价格
        String totalPriceSql = "select sum(payment_total_price) as totalPrice  from ord_payment_record WHERE is_pay_success =1";
        Long totalPrice = baseDao.queryForLong(totalPriceSql);

        Long yesterdayTotalPrice = baseDao.queryForLong(sumTotalPriceSql, param);
        double riseIncome = (double) (currentTotalPrice - yesterdayTotalPrice) / totalPrice;
        if (riseIncome > 0) {
            map.put("riseIncome", String.format("%.2f", riseIncome * 100));
        } else {
            map.put("riseIncome", "0");
        }


        // 较昨天上涨下铁
        // 计算总出售的商品数量
        String productNumSql = "SELECT sum(total_product_number) as productNumber  from  ord_sales_order WHERE sales_order_status = '03'";
        Long totalProductNum = baseDao.queryForLong(productNumSql,param);

        Long yesterdayProductNumber = baseDao.queryForLong(productNumberSql, param);
        double riseProduct = (double) (currentProductNumber - yesterdayProductNumber) / totalProductNum;
        if (riseProduct > 0) {
            map.put("riseProduct", String.format("%.2f", riseProduct * 100));
        } else {
            map.put("riseProduct", "0");
        }

        // 较昨天上涨下铁
        // 计算总用户量
        String totalUserSql = "select count(*) from cli_clientele_info";
        Long totalUserNumber = baseDao.queryForLong(totalUserSql);

        Long yesterdayInsertUserNumber = baseDao.queryForLong(insertUserSql, param);
        double riseUser = (double) (currentInsertUserNumber - yesterdayInsertUserNumber) / totalUserNumber;
        if (riseUser > 0) {
            map.put("riseUser", String.format("%.2f", riseUser * 100));
        } else {
            map.put("riseUser", "0");
        }

        //较昨天上涨下铁
        //计算总访问量
        String totalVisitSql = "select count( * ) from (select * from cli_clientele_log WHERE ip is not null  GROUP BY ip) as count";
        Long totalVisitNumber = baseDao.queryForLong(totalVisitSql);

        Long yesterdayVisitNumber = baseDao.queryForLong(countVisitSql, param);
        double riseVisit = (double) (currentVisitNumber - yesterdayVisitNumber) / totalVisitNumber;
        if (riseVisit > 0) {
            map.put("riseVisit", String.format("%.2f", riseVisit * 100));
        } else {
            map.put("riseVisit", "0");
        }

       /* // 收益-最近7天的趋势图
        String priceSql = "select sum(payment_total_price) as count,FROM_UNIXTIME(payment_time,'%Y-%m-%d') as time from ord_payment_record WHERE payment_time >= :startTime AND payment_time <=:endTime GROUP BY time";
        map.put("priceChartData",queryChart(priceSql,true));

        // 已售产品-最近7天的趋势图
        String productSql = "select sum(total_product_number) as count,FROM_UNIXTIME(payment_time,'%Y-%m-%d') as time from ord_sales_order WHERE payment_time >= :startTime AND payment_time <=:endTime GROUP BY time";
        map.put("productChartData",queryChart(productSql,false));

        // 新客户-最近7天的趋势图
        String userSql = "select count(*) as count,FROM_UNIXTIME(add_time,'%Y-%m-%d') as time from cli_clientele_info WHERE add_time >= :startTime AND add_time <=:endTime GROUP BY time";
        map.put("userChartData",queryChart(userSql,false));

        // 访问量-最近7天的趋势图
        String visitSql = "select count(*) as count,FROM_UNIXTIME(add_time,'%Y-%m-%d') as time from cli_clientele_log WHERE ip is not null and  add_time >= :startTime AND add_time <=:endTime GROUP BY time,ip";
        map.put("visitChartData",queryChart(visitSql,false));
*/
        Date curDate = new Date();
        Date beforeDate = DateUtil.offsetDay(curDate, -7);
        List<DateTime> dateTimes =  DateUtil.rangeToList(beforeDate, curDate, DateField.DAY_OF_YEAR);

        // 收益-最近7天的趋势图
        map.put("priceChartData",getPriceChartData(dateTimes));

        // 已售产品-最近7天的趋势图
        map.put("productChartData",getProductChartData(dateTimes));

        // 新客户-最近7天的趋势图
        map.put("userChartData",getUserChartData(dateTimes));

        // 访问量-最近7天的趋势图
        map.put("visitChartData",getVisitChartData(dateTimes));

        return map;
    }

    private List<BasePlotDto> getPriceChartData(List<DateTime> dateTimes) {
        List<BasePlotDto> basePlotDtos = new ArrayList<>(7);
        dateTimes.forEach(item -> {
            Date beginOfDay = DateUtil.beginOfDay(item);
            Date endOfDay = DateUtil.endOfDay(item);
            BasePlotDto basePlotDto = new BasePlotDto();
            Map<String, Object> param = new HashMap<>(4);
            param.put("startTime", beginOfDay.getTime() / 1000);
            param.put("endTime", endOfDay.getTime() / 1000);

            Long currentTotalPrice = baseDao.queryForLong("select sum(payment_total_price) as totalPrice  from ord_payment_record WHERE is_pay_success =1 and payment_time >=:startTime  and payment_time<=:endTime", param);
            basePlotDto.setValue(Helper.transformF2Y(currentTotalPrice));
            basePlotDto.setKey(item.toString(DatePattern.NORM_DATE_PATTERN));
            basePlotDtos.add(basePlotDto);
        });
        return basePlotDtos;
    }

    private List<BasePlotDto> getVisitChartData(List<DateTime> dateTimes){
        List<BasePlotDto> basePlotDtos = new ArrayList<>(7);
        dateTimes.forEach(item -> {
            Date beginOfDay = DateUtil.beginOfDay(item);
            Date endOfDay = DateUtil.endOfDay(item);
            BasePlotDto basePlotDto = new BasePlotDto();
            Map<String, Object> param = new HashMap<>(4);
            param.put("startTime", beginOfDay.getTime() / 1000);
            param.put("endTime", endOfDay.getTime() / 1000);

            Long currentTotalPrice = baseDao.queryForLong("select count( * ) from (select * from cli_clientele_log WHERE ip is not null and  add_time >= :startTime AND add_time <=:endTime GROUP BY ip) as count", param);
            basePlotDto.setValue(new BigDecimal(currentTotalPrice));
            basePlotDto.setKey(item.toString(DatePattern.NORM_DATE_PATTERN));
            basePlotDtos.add(basePlotDto);
        });
        return basePlotDtos;
    }

    private List<BasePlotDto> getUserChartData(List<DateTime> dateTimes) {
        List<BasePlotDto> basePlotDtos = new ArrayList<>(7);
        dateTimes.forEach(item -> {
            Date beginOfDay = DateUtil.beginOfDay(item);
            Date endOfDay = DateUtil.endOfDay(item);
            BasePlotDto basePlotDto = new BasePlotDto();
            Map<String, Object> param = new HashMap<>(4);
            param.put("startTime", beginOfDay.getTime() / 1000);
            param.put("endTime", endOfDay.getTime() / 1000);

            Long currentTotalPrice = baseDao.queryForLong("select count(*)  from cli_clientele_info WHERE add_time >=:startTime and add_time<=:endTime", param);
            basePlotDto.setValue(new BigDecimal(currentTotalPrice));
            basePlotDto.setKey(item.toString(DatePattern.NORM_DATE_PATTERN));
            basePlotDtos.add(basePlotDto);
        });
        return basePlotDtos;
    }

    private List<BasePlotDto> getProductChartData(List<DateTime> dateTimes) {
        List<BasePlotDto> basePlotDtos = new ArrayList<>(7);
        dateTimes.forEach(item -> {
            Date beginOfDay = DateUtil.beginOfDay(item);
            Date endOfDay = DateUtil.endOfDay(item);
            BasePlotDto basePlotDto = new BasePlotDto();
            Map<String, Object> param = new HashMap<>(4);
            param.put("startTime", beginOfDay.getTime() / 1000);
            param.put("endTime", endOfDay.getTime() / 1000);

            Long currentTotalPrice = baseDao.queryForLong("select sum(total_product_number) as productNumber  from  ord_sales_order WHERE sales_order_status = '03' and payment_time >=:startTime  and payment_time<=:endTime", param);
            basePlotDto.setValue(new BigDecimal(currentTotalPrice));
            basePlotDto.setKey(item.toString(DatePattern.NORM_DATE_PATTERN));
            basePlotDtos.add(basePlotDto);
        });
        return basePlotDtos;
    }


    /**
     * 返现走势
     *
     * @return
     */
    public Map<String, Object> countCashbackTrend(CountCommond commond) {
        Map<String, Object> map = new HashMap<>();
        map.put("nowDay", new ArrayList<>());
        Integer day = getDay(commond.getCountType());
        Map<String, Object> param = new HashMap<>();
        if (day <= 2 && day > 0) {
            List<Long> dayList = null;
            if (day == 1) {
                //当天
                dayList = Helper.pastHour(new Date());
            } else {
                //昨天
                dayList = Helper.pastHour(Helper.getYesterday());
            }
            String sql = "SELECT sum(ord.rebate) as count,FROM_UNIXTIME(app.reviewer_time,'%Y-%m-%d %H') as time from eva_ord_order ord,eva_cash_back_apply app WHERE ord.id = app.eva_order_id and app.`status` = 2 and ord.`status` = 6 and app.reviewer_time>=:startTime and app.reviewer_time<:endTime GROUP BY time";
            param.put("startTime", dayList.get(0));
            param.put("endTime", Helper.getBeginDayOfTomorrow());
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            if (mapList.size() > 0) {
                List<Map<String, Object>> nowDay = setSalesTrendList(dayList, mapList);
                for (Map<String, Object> objectMap : nowDay) {
                    objectMap.put("value", Helper.transformF2Y(objectMap.get("value")));
                }
                map.put("nowDay", nowDay);
            }
        } else {
            String sql = "SELECT sum(ord.rebate) as count,FROM_UNIXTIME(app.reviewer_time,'%Y-%m-%d') as time from eva_ord_order ord,eva_cash_back_apply app WHERE ord.id = app.eva_order_id and app.`status` = 2 and ord.`status` = 6 and app.reviewer_time>=:startTime and app.reviewer_time<:endTime GROUP BY time";
            setParam(param, commond);
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            if (mapList.size() > 0) {
                List<Map<String, Object>> maps = new ArrayList<>();
                Integer day1 = getDay(commond.getCountType());
                List<String> pastDateList=null;
                if (day>=5&&day<=6){
                    pastDateList= Helper.getIntervalDate(Convert.toLong(param.get("startTime")),Convert.toLong(param.get("endTime")));
                }else {
                    pastDateList = Helper.getPastDateList(day1);
                }
                for (String date : pastDateList) {
                    Map<String, Object> dataMap = new HashMap<>();
                    boolean isv = false;
                    for (Map<String, Object> objectMap : mapList) {
                        if (objectMap.get("time").equals(date)) {
                            dataMap.put("date", objectMap.get("time"));
                            dataMap.put("value", Helper.transformF2Y(objectMap.get("count")));
                            isv = true;
                        }
                    }
                    if (!isv) {
                        dataMap.put("date", date);
                        dataMap.put("value", 0);
                    }
                    maps.add(dataMap);
                }
                map.put("nowDay", maps);
            }
        }
        // 总返现额
        String sumSalesSql = "SELECT sum(ord.rebate) as num  from eva_ord_order ord,eva_cash_back_apply app WHERE ord.id = app.eva_order_id and app.`status` = 2 and ord.`status` = 6 and app.reviewer_time>=:startTime and app.reviewer_time<:endTime ";
        Long sumSales = baseDao.queryForLong(sumSalesSql, param);
        map.put("cashback", Helper.transformF2Y(sumSales));
        return map;
    }
























    public List<Map<String, Object>> getPreviewHotGoodsCount(CountCommond countCommond) {
        StringBuilder sql = new StringBuilder("SELECT count(trail.fk_goods_id) AS previewCount,trail.goods_name as goodsName FROM cli_clientele_goods_trail trail ");
        sql.append("WHERE 1 = 1 and add_time >= :startTime and add_time <= :endTime ");
        sql.append("GROUP BY trail.fk_goods_id ORDER BY previewCount DESC LIMIT 0,6");
        Map<String, Object> param = new HashMap<>();
        if (countCommond.getStartTime() != null && countCommond.getEndTime() != null) {
            param.put("startTime", countCommond.getStartTime());
            param.put("endTime", countCommond.getEndTime());
        } else {
            SearchTime searchTime = getSearChTime(countCommond.getCountType());
            param.put("startTime", searchTime.getStart());
            param.put("endTime", searchTime.getEnd());
        }
        List<Map<String, Object>> list = baseDao.queryForList(sql.toString(), param);
        int count = 0;
        for (Map<String, Object> map : list) {
            count += Integer.parseInt(map.get("previewCount").toString());
        }
        for (Map<String, Object> map : list) {
            int num = Integer.parseInt(map.get("previewCount").toString());
            int counts = count;
            map.put("proportion", division(num, counts));
        }
        return list;
    }



    /**
     * 最近7天的新增用户趋势图
     * @return
     */
    public List<BasePlotDto> queryChart(String sql,boolean isMoney) {
        Map<String, Object> param = new HashMap<>(2);
        Long startTime = Helper.Date2TimeStamp(Helper.getPastDate(7 - 1, new Date()), Helper.DATE_PATTERN3);
        Long endTime = Helper.Date2TimeStamp(Helper.formatDateByFormat(new Date(), Helper.DATE_PATTERN1), Helper.DATE_PATTERN1);
        param.put("startTime", startTime);
        param.put("endTime", endTime);

        List<BasePlotDto>  resultList = new ArrayList<BasePlotDto>();
        List<String> pastDateList = Helper.getPastDateList(7);
        List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);

//        if (mapList.size() > 0) {
            for (String date : pastDateList) {
                BasePlotDto plotDto = new BasePlotDto();
                boolean isv = false;
                for (Map<String, Object> objectMap : mapList) {
                    if (objectMap.get("time").equals(date)) {
                        plotDto.setKey(objectMap.get("time").toString());
                        if(isMoney){
                            plotDto.setValue(Helper.transformF2Y(objectMap.get("count").toString()));
                        }else{
                            plotDto.setValue(new BigDecimal(objectMap.get("count").toString()));
                        }
                        isv = true;
                    }
                }
                if (!isv) {
                    plotDto.setKey(date);
                    plotDto.setValue(new BigDecimal(0));
                }
                resultList.add(plotDto);
            }
//        }
        return resultList;
    }


    /**
     * 统计核心数据
     *
     * @param commond
     * @return
     */
    public Map<String, Object> dataAnalysis(CountCommond commond) {

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        //用户访问记录
        String countVisitSql = "select count(1) from (SELECT count(1) from pro_report_device WHERE add_time >=:startTime  and add_time <=:endTime and ip is not null  and type =1 GROUP BY ip) ip";
        Long countVisitNumber = baseDao.queryForLong(countVisitSql, param);
        map.put("countVisitNumber", countVisitNumber);
        //订单量
        String countOrderSql = "select count(*) from  ord_payment_record where payment_time  >= :startTime AND payment_time <=:endTime and is_pay_success =1 ";
        Long countOrderNumber = baseDao.queryForLong(countOrderSql, param);
        map.put("countOrderNumber", countOrderNumber);
        //成交额
        String countOrderSuccessSql = "select sum(payment_total_price) from  ord_payment_record where payment_time  >= :startTime AND payment_time <=:endTime and is_pay_success =1";
        Long countOrderSuccessNumber = baseDao.queryForLong(countOrderSuccessSql, param);
        map.put("countOrderSuccessPrice", Helper.transformF2Y(countOrderSuccessNumber));

        map.put("transform", division(countOrderNumber.intValue(), countVisitNumber.intValue()));
        return map;
    }

    /**
     * 核心订单趋势
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> countOrderTrends(CountCommond commond) {
        List<Long> timeList = null;
        List<Map<String, Object>> sqlList = null;
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        if (Helper.isNotEmpty(commond.getCountType()) && commond.getCountType().equals(1)) {
            //当天
            timeList = Helper.pastHour(new Date());
        } else if (Helper.isNotEmpty(commond.getCountType()) && commond.getCountType().equals(2)) {
            //昨天
            timeList = Helper.pastHour(Helper.getYesterday());
        } else {
            Integer day = getDay(commond.getCountType());
            if (commond.getStartTime() != null && commond.getEndTime() != null) {
                param.put("startTime", commond.getStartTime());
                param.put("endTime", commond.getEndTime());
            } else {
                setParam(param,commond);
            }
            String sql = "SELECT count(1) as count,sum(payment_total_price) as paymentTotalPrice,FROM_UNIXTIME(payment_time,'%Y-%m-%d') as time  from ord_payment_record WHERE payment_time >=:startTime  and payment_time <=:endTime and payment_time is not null and is_pay_success=1 GROUP BY time";
            sqlList = baseDao.queryForList(sql, param);
            List<String> pastDateList=null;
            if (day>=5&&day<=6){
                pastDateList= Helper.getIntervalDate(Convert.toLong(param.get("startTime")),Convert.toLong(param.get("endTime")));
            }else {
                pastDateList = Helper.getPastDateList(day);
            }
            if (sqlList.size() > 0) {
                for (String date : pastDateList) {
                    boolean isv = false;
                    Map<String, Object> orderMap = new HashMap<>();
                    Map<String, Object> salesMap = new HashMap<>();
                    for (Map<String, Object> m : sqlList) {
                        if (date.equals(m.get("time"))) {
                            orderMap.put("country", "订单量");
                            orderMap.put("date", m.get("time"));
                            orderMap.put("value", m.get("count"));
                            salesMap.put("country", "销售额");
                            salesMap.put("date", m.get("time"));
                            salesMap.put("value", Helper.transformF2Y(Convert.toLong(m.get("paymentTotalPrice"))));
                            isv = true;
                        }
                    }
                    if (!isv) {
                        orderMap.put("country", "订单量");
                        orderMap.put("date", date);
                        orderMap.put("value", 0);
                        salesMap.put("country", "销售额");
                        salesMap.put("date", date);
                        salesMap.put("value", 0);
                    }
                    resultList.add(orderMap);
                    resultList.add(salesMap);
                }
            }

        }
        if (Helper.isNotEmpty(timeList)) {
            Long startTime = timeList.get(0);
            Long endTime = Helper.plusMinuteData(startTime);
            String sql = "SELECT count(1) as count,sum(payment_total_price) as paymentTotalPrice,FROM_UNIXTIME(payment_time,'%Y-%m-%d %H') as time  from ord_payment_record WHERE payment_time >=:startTime  and payment_time <=:endTime and payment_time is not null and is_pay_success=1 GROUP BY time";
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            sqlList = baseDao.queryForList(sql, param);
            if (sqlList.size() > 0) {
                for (Long time : timeList) {
                    boolean isv = false;
                    Map<String, Object> orderMap = new HashMap<>();
                    Map<String, Object> salesMap = new HashMap<>();
                    String date = Helper.timestampToDate(time, Helper.DATE_PATTERN6);
                    String britishTime = Helper.toBritishTime(time);
                    String[] split = britishTime.split(" ");
                    for (Map<String, Object> m : sqlList) {
                        if (m.get("time").equals(date)) {
                            orderMap.put("country", "订单量");
                            orderMap.put("date", split[1]);
                            orderMap.put("value", m.get("count"));
                            salesMap.put("country", "销售额");
                            salesMap.put("date", split[1]);
                            salesMap.put("value", Helper.transformF2Y(Convert.toLong(m.get("paymentTotalPrice"))));
                            isv = true;
                        }
                    }
                    if (!isv) {
                        orderMap.put("country", "订单量");
                        orderMap.put("date", split[1]);
                        orderMap.put("value", 0);
                        salesMap.put("country", "销售额");
                        salesMap.put("date", split[1]);
                        salesMap.put("value", 0);
                    }
                    resultList.add(orderMap);
                    resultList.add(salesMap);
                }
            }
        }
        return resultList;
    }

    /**
     * 核心访问趋势
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> countClienteleTrends(CountCommond commond) {
        List<Long> timeList = null;
        List<Map<String, Object>> sqlList = null;
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> param = new HashMap<>();
        if (Helper.isNotEmpty(commond.getCountType()) && commond.getCountType().equals(1)) {
            //当天
            timeList = Helper.pastHour(new Date());
        } else if (Helper.isNotEmpty(commond.getCountType()) && commond.getCountType().equals(2)) {
            //昨天
            timeList = Helper.pastHour(Helper.getYesterday());
        } else {
            Integer day = getDay(commond.getCountType());

            if (commond.getStartTime() != null && commond.getEndTime() != null) {
                param.put("startTime", commond.getStartTime());
                param.put("endTime", commond.getEndTime());
            } else {
               setParam(param,commond);
            }
            String sql = "SELECT count(1) as count,FROM_UNIXTIME(add_time,'%Y-%m-%d') as time  from pro_report_device WHERE add_time >=:startTime  and add_time <=:endTime and ip is not null and type=1   GROUP BY time";
            sqlList = baseDao.queryForList(sql, param);
            if (sqlList.size() > 0) {
                List<String> pastDateList=null;
                if (day>=5&&day<=6){
                    pastDateList= Helper.getIntervalDate(Convert.toLong(param.get("startTime")),Convert.toLong(param.get("endTime")));
                }else {
                    pastDateList = Helper.getPastDateList(day);
                }
                for (String date : pastDateList) {
                    boolean isv = false;
                    Map<String, Object> orderMap = new HashMap<>();
                    for (Map<String, Object> m : sqlList) {
                        if (date.equals(m.get("time"))) {
                            orderMap.put("country", "访问量");
                            orderMap.put("date", m.get("time"));
                            orderMap.put("value", m.get("count"));
                            isv = true;
                        }
                    }
                    if (!isv) {
                        orderMap.put("country", "访问量");
                        orderMap.put("date", date);
                        orderMap.put("value", 0);
                    }
                    resultList.add(orderMap);
                }
            }
        }
        if (Helper.isNotEmpty(timeList)) {
            String sql = "SELECT count(1) as count,FROM_UNIXTIME(add_time,'%Y-%m-%d %H') as time  from pro_report_device WHERE add_time >=:startTime  and add_time <=:endTime and ip is not null and type=1  GROUP BY time";
            Long startTime = timeList.get(0);
            Long endTime = Helper.plusMinuteData(startTime);
            param.put("startTime", startTime);
            param.put("endTime", endTime);
            sqlList = baseDao.queryForList(sql, param);
            if (sqlList.size() > 0) {
                for (Long time : timeList) {
                    boolean isv = false;
                    Map<String, Object> map = new HashMap<>();
                    String date = Helper.timestampToDate(time, Helper.DATE_PATTERN6);
                    String britishTime = Helper.toBritishTime(time);
                    String[] split = britishTime.split(" ");
                    for (Map<String, Object> m : sqlList) {
                        if (m.get("time").equals(date)) {
                            map.put("country", "访问量");
                            map.put("value", m.get("count"));
                            map.put("date", split[1]);
                            isv = true;
                        }
                    }
                    if (!isv) {
                        map.put("country", "访问量");
                        map.put("value", 0);
                        map.put("date", split[1]);
                    }
                    resultList.add(map);
                }
            }
        }


        return resultList;
    }

    /**
     * 统计搜索关键词
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> countHotKeyword(CountCommond commond) {
        String sql = "select keyword ,count(keyword) AS num  from pro_search_key WHERE add_time >=:startTime  and add_time <=:endTime GROUP BY keyword ORDER BY num desc LIMIT 6";
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        setPercentage(list);
        return list;
    }

    /**
     * 统计商品浏览量
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> countHotGoodsTrail(CountCommond commond) {
        String sql = "SELECT count(goods_name) num,goods_name as goodsName from  cli_clientele_goods_trail WHERE add_time >=:startTime  and add_time <=:endTime GROUP BY goodsName ORDER BY num desc limit 6";
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        setPercentage(list);
        return list;
    }

    /**
     * 统计转化率
     *
     * @param commond
     * @return
     */
    public Map<String, Object> countConversion(CountCommond commond) {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        //访问量
        String visitSql = "select count(1) from (SELECT count(1) from pro_report_device WHERE add_time >=:startTime  and add_time <=:endTime and ip is not null  and type =1 GROUP BY ip) ip ";
        setParam(param, commond);
        Long visitCount = baseDao.queryForLong(visitSql, param);
        map.put("visitCount", visitCount);

        //加入购物车
        String cartSql = "SELECT count(1) as num from (select fk_clientele_id as num from ord_shopping_cart WHERE add_time >=:startTime  and add_time <=:endTime GROUP BY fk_clientele_id) as cart";
        setParam(param, commond);
        Long cartCount = baseDao.queryForLong(cartSql, param);
        map.put("cartCount", cartCount);

        //发起支付
        String ordSql = "SELECT count(1) as num from (select fk_clientele_id as num from ord_sales_order WHERE payment_time is not null and payment_time >=:startTime  and payment_time <=:endTime GROUP BY fk_clientele_id) as ord";
        setParam(param, commond);
        Long ordCount = baseDao.queryForLong(ordSql, param);
        map.put("ordCount", ordCount);

        //交易成功
        String paySuccessSql = "SELECT count(1) as num from (select fk_clientele_id  from ord_payment_record WHERE payment_time >=:startTime  and payment_time <=:endTime and fk_payment_type_id is not null and is_pay_success=1 GROUP BY fk_clientele_id) as pay";
        setParam(param, commond);
        Long paySuccessCount = baseDao.queryForLong(paySuccessSql, param);
        map.put("paySuccessCount", paySuccessCount);
        //计算转化率
        map.put("transform", division(paySuccessCount.intValue(), visitCount.intValue()));
        return map;
    }


    /**
     * 社交来源销售
     * @param commond
     * @return
     */
    public List<BaseGroupPlotDto> countSocialSales(CountCommond commond) {
        String sql = "SELECT sum(payment_total_price) as count,FROM_UNIXTIME(payment_time,'%Y-%m-%d') as time,info.google_account as google,info.facebook_account as facebook,info.twitter_account as twitter from ord_payment_record re inner join cli_clientele_info info on(re.fk_clientele_id = info.id and (info.google_account is not null or facebook_account is not null or twitter_account is not null))  WHERE payment_time>=:startTime and payment_time<:endTime AND is_pay_success=1 GROUP BY time ";
        Map<String, Object> param = new HashMap<>(2);
        Integer day = getDay(commond.getCountType());
        setParam(param, commond);
        List<BaseGroupPlotDto> resultList = new ArrayList<BaseGroupPlotDto>();
        List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
        if (mapList.size() > 0){
            List<String> pastDateList = null;
            if (day >= 5 && day <= 6){
                pastDateList= Helper.getIntervalDate(Convert.toLong(param.get("startTime")),Convert.toLong(param.get("endTime")));
            }else {
                pastDateList = Helper.getPastDateList(day);
            }
            for (String date : pastDateList) {
                BaseGroupPlotDto googleDto = new BaseGroupPlotDto();
                BaseGroupPlotDto facebookDto = new BaseGroupPlotDto();
                BaseGroupPlotDto twitterDto = new BaseGroupPlotDto();
                boolean google = false;
                boolean facebook = false;
                boolean twitter = false;
                for (Map<String, Object> objectMap : mapList) {
                    if (objectMap.get("time").equals(date) && Helper.isNotEmpty(objectMap.get("google"))) {
                        googleDto.setKey(objectMap.get("time")+"");
                        googleDto.setType("Google");
                        googleDto.setValue(Helper.transformF2Y(objectMap.get("count")));
                        google = true;
                    }
                    if (objectMap.get("time").equals(date) && Helper.isNotEmpty(objectMap.get("facebook"))) {
                        facebookDto.setKey(objectMap.get("time")+"");
                        facebookDto.setType("Facebook");
                        facebookDto.setValue(Helper.transformF2Y(objectMap.get("count")));
                        facebook = true;
                    }
                    if (objectMap.get("time").equals(date) && Helper.isNotEmpty(objectMap.get("twitter"))) {
                        twitterDto.setKey(objectMap.get("time")+"");
                        twitterDto.setType("Twitter");
                        twitterDto.setValue(Helper.transformF2Y(objectMap.get("count")));
                        twitter = true;
                    }
                }
                if (!google) {
                    googleDto.setKey(date);
                    googleDto.setType("Google");
                    googleDto.setValue(new BigDecimal(0));
                }
                if (!facebook) {
                    facebookDto.setKey(date);
                    facebookDto.setType("Facebook");
                    facebookDto.setValue(new BigDecimal(0));
                }
                if (!twitter) {
                    twitterDto.setKey(date);
                    twitterDto.setType("Twitter");
                    twitterDto.setValue(new BigDecimal(0));
                }
                resultList.add(googleDto);
                resultList.add(facebookDto);
                resultList.add(twitterDto);
            }
        }
        return resultList;
    }

    /**
     * 订单量走势
     *
     * @return
     */
    public Map<String, Object> countOrderTrend(CountCommond commond) {
        Map<String, Object> map = new HashMap<>();
        Integer day = getDay(commond.getCountType());
        Map<String, Object> param = new HashMap<>();
        if (day <= 2 && day > 0) {
            List<Long> dayList = null;
            if (day == 1) {
                //当天
                dayList = Helper.pastHour(new Date());
            } else {
                //昨天
                dayList = Helper.pastHour(Helper.getYesterday());
            }
            String sql = "SELECT count(1) as count,FROM_UNIXTIME(payment_time,'%Y-%m-%d %H') as time  from ord_sales_order WHERE payment_time>=:startTime and payment_time<:endTime and payment_time is not null GROUP BY time";
            param.put("startTime", dayList.get(0));
            param.put("endTime", Helper.getBeginDayOfTomorrow());
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            List<Map<String, Object>> nowDay = setSalesTrendList(dayList, mapList);
            map.put("nowDay", nowDay);
        } else {
            String sql = "SELECT count(1) as count,FROM_UNIXTIME(payment_time,'%Y-%m-%d') as time  from ord_sales_order WHERE payment_time>=:startTime and payment_time<:endTime and payment_time is not null GROUP BY time";
            setParam(param, commond);
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            if (mapList.size() > 0) {
                List<Map<String, Object>> maps = new ArrayList<>();
                Integer day1 = getDay(commond.getCountType());
                List<String> pastDateList=null;
                if (day>=5&&day<=6){
                    pastDateList= Helper.getIntervalDate(Convert.toLong(param.get("startTime")),Convert.toLong(param.get("endTime")));
                }else {
                    pastDateList = Helper.getPastDateList(day1);
                }
                for (String date : pastDateList) {
                    Map<String, Object> dataMap = new HashMap<>();
                    boolean isv = false;
                    for (Map<String, Object> objectMap : mapList) {
                        if (objectMap.get("time").equals(date)) {
                            dataMap.put("date", objectMap.get("time"));
                            dataMap.put("value", objectMap.get("count"));
                            isv = true;
                        }
                    }
                    if (!isv) {
                        dataMap.put("date", date);
                        dataMap.put("value", 0);
                    }
                    maps.add(dataMap);
                }
                map.put("nowDay", maps);
                map.put("yesterday", "");
            }
        }
        String countOrderSql = "SELECT count(1) as num  from ord_sales_order WHERE payment_time>=:startTime and payment_time<:endTime and payment_time is not null ";
        Long countOrder = baseDao.queryForLong(countOrderSql, param);
        map.put("countOrder", countOrder);
        return map;
    }

    /**
     * 统计热卖商品
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> countHotGoods(CountCommond commond) {
        String sql = "select fk_product_id as productId,title,sum(quantity) as num from  pro_report_product WHERE type=1 and add_time >=:startTime and add_time <=:endTime GROUP BY fk_product_id ORDER BY num desc limit 6";
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        setPercentage(list);
        return list;
    }


    /**
     *  获取最近订单
     * @param commond
     * @return
     */
    public List<Map<String, Object>> getNearestOrder(CountCommond commond) {
//        Long startTime = null;
//        Long endTime = null;
//        if (commond.getStartTime() != null && commond.getStartTime() > 0) {
//            startTime = commond.getStartTime();
//            endTime = commond.getEndTime();
//        }else{
//            startTime = Helper.dayTimeInMillis();
//            endTime = Helper.plusMinuteData(new Date().getTime());
//        }
        String sql = "select de.product_name AS productName,de.localhost_sn AS orderNo,de.add_user_name AS userName,de.product_mall_price AS mallPrice,ord.sales_order_status orderStatus from  ord_sales_order_detail de,ord_sales_order ord WHERE (de.fk_sales_order_id = ord.id) ORDER BY de.add_time desc limit 5";
        List<Map<String, Object>> list =  baseDao.queryForList(sql);
//        String sql = "select de.product_name AS productName,de.sales_order_no AS orderNo,de.add_user_name AS userName,de.product_mall_price AS mallPrice,ord.sales_order_status orderStatus from  ord_sales_order_detail de,ord_sales_order ord WHERE (de.fk_sales_order_id = ord.id) and (de.add_time >=:startTime and de.add_time <=:endTime) ORDER BY de.add_time desc limit 5";
//        Map<String, Object> param = new HashMap<>(1);
//        param.put("startTime",startTime);
//        param.put("endTime",endTime);
//        List<Map<String, Object>> list =  baseDao.queryForList(sql, param);
        list.forEach(info -> {
            info.put("mallPriceStr", Helper.transformF2Y(info.get("mallPrice").toString()));
            info.put("status", OrdSalesOrderStatusEnum.getDesc(info.get("orderStatus").toString()));
        });
        return list;

    }

    /**
     * 统计复购商品
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> countHotRepurchaseGoods(CountCommond commond) {
        String sql = "select fk_product_id as productId,title,sum(quantity) as num from  pro_report_product WHERE type=2 and add_time >=:startTime and add_time <=:endTime GROUP BY fk_product_id ORDER BY num desc limit 6";
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        setPercentage(list);
        return list;
    }

    /**
     * 统计热门国家
     *
     * @param commond
     * @return
     */
    public List<Map<String, Object>> countHotCountry(CountCommond commond) {
        String sql = "select address,sum(quantity) as num from  pro_report_product WHERE type=1 and add_time >=:startTime and add_time <=:endTime GROUP BY address ORDER BY num desc limit 6";
        Map<String, Object> param = new HashMap<>(1);
        setParam(param, commond);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        setPercentage(list);
        return list;
    }

    /**
     * 统计访问设备
     *
     * @param commond
     * @return
     */
    public List<EquipmentDto> countAccessEquipment(CountCommond commond) {
        String sql = "select  device as visitMachine,count(device) as num  from pro_report_device WHERE device is not null and add_time>=:startTime and  add_time<=:endTime  and type=1 GROUP BY device ORDER BY num desc ";
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        List<EquipmentDto> equipment = new ArrayList<>();
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        if (list.size()>0){
            Integer number = 0;
            for (int i = 0; i < list.size(); i++) {
                number = number + Convert.toInt(list.get(i).get("num"));
            }
            for (Map<String, Object> objectMap : list) {
                EquipmentDto dto = new EquipmentDto();
                if (objectMap.get("visitMachine").equals("Windows") || objectMap.get("visitMachine").equals("Mac")) {
                    dto.setItem("PC");
                    dto.setCount(Convert.toInt(objectMap.get("num")));
                    dto.setPercent(setPercent(objectMap.get("num"), number));
                    //equipment.put("PC",map);
                } else if (objectMap.get("visitMachine").equals("Android")) {
                    dto.setItem("移动端");
                    dto.setCount(Convert.toInt(objectMap.get("num")));
                    dto.setPercent(setPercent(objectMap.get("num"), number));
                    //equipment.put("mobile",map);
                } else {

                    Integer anInt = Convert.toInt(objectMap.get("num"));
                    if (Helper.isNotEmpty(dto.getCount())) {
                        anInt = dto.getCount() + anInt;
                    }
                    dto.setCount(anInt);
                    dto.setItem("其他");
                    dto.setPercent(setPercent(dto.getCount(), number));
                    //equipment.put("other",map);
                }
                equipment.add(dto);
            }
            setEquipment(equipment);
        }
        return equipment;
    }


    /**
     * 交易设备占比
     *
     * @param commond
     * @return
     */
    public List<EquipmentDto> countTradingDevice(CountCommond commond) {
        String sql = "select  device as visitMachine,count(device) as num  from pro_report_device WHERE device is not null and add_time>=:startTime and  add_time<=:endTime  and type=2 GROUP BY device ORDER BY num desc ";
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        List<EquipmentDto> equipment = new ArrayList<>();
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        if (list.size()>0){
            Integer number = 0;
            for (int i = 0; i < list.size(); i++) {
                number = number + Convert.toInt(list.get(i).get("num"));
            }
            for (Map<String, Object> objectMap : list) {
                EquipmentDto dto = new EquipmentDto();
                if (objectMap.get("visitMachine").equals("Windows") || objectMap.get("visitMachine").equals("Mac")) {
                    dto.setItem("PC");
                    dto.setCount(Convert.toInt(objectMap.get("num")));
                    dto.setPercent(setPercent(objectMap.get("num"), number));
                    //equipment.put("PC",map);
                } else if (objectMap.get("visitMachine").equals("Android")) {
                    dto.setItem("移动端");
                    dto.setCount(Convert.toInt(objectMap.get("num")));
                    dto.setPercent(setPercent(objectMap.get("num"), number));
                    //equipment.put("mobile",map);
                } else {
                    Integer anInt = Convert.toInt(objectMap.get("num"));
                    if (Helper.isNotEmpty(dto.getCount())) {
                        anInt = dto.getCount() + anInt;
                    }
                    dto.setCount(anInt);
                    dto.setItem("其他");
                    dto.setPercent(setPercent(dto.getCount(), number));
                    //equipment.put("other",map);
                }
                equipment.add(dto);
            }
            setEquipment(equipment);
        }

        return equipment;
    }


    /**
     * 统计支付方式占比
     *
     * @param commond
     * @return
     */
    public List<EquipmentDto> countPayment(CountCommond commond) {
        String sql = "select payment_platform_name as paymentPlatformName,count(payment_platform_name) as num from ord_sales_order WHERE payment_time >=:startTime and payment_time <=:endTime and payment_platform_name is not null GROUP BY paymentPlatformName ORDER BY num desc";
        Map<String, Object> param = new HashMap<>();
        setParam(param, commond);
        List<Map<String, Object>> list = baseDao.queryForList(sql, param);
        Integer number = 0;
        for (int i = 0; i < list.size(); i++) {
            number = number + Convert.toInt(list.get(i).get("num"));
        }
        List<EquipmentDto> dtoList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            EquipmentDto dto = new EquipmentDto();
            dto.setItem(map.get("paymentPlatformName").toString());
            dto.setCount(Convert.toInt(map.get("num")));
            dto.setPercent(setPercent(map.get("num"), number));
            dtoList.add(dto);
        }
        EquipmentDto dto = null;
        if (dtoList.size() > 0) {
            List<String> items = dtoList.stream().map(EquipmentDto::getItem).collect(Collectors.toList());
            if (!items.contains("PayPal")) {
                dto = new EquipmentDto();
                dto.setItem("PayPal");
                dto.setCount(0);
                dto.setPercent(new BigDecimal(0));
                dtoList.add(dto);
            }
            if (!items.contains("Asiabill")) {
                dto = new EquipmentDto();
                dto.setItem("Asiabill");
                dto.setCount(0);
                dto.setPercent(new BigDecimal(0));
                dtoList.add(dto);
            }
            if (!items.contains("线下支付")) {
                dto = new EquipmentDto();
                dto.setItem("线下支付");
                dto.setCount(0);
                dto.setPercent(new BigDecimal(0));
                dtoList.add(dto);
            }
        }
        return dtoList;
    }


    /**
     * 统计在线用户
     *
     * @return
     */
    public Map<String, Object> countOnlineUser(CountCommond commond) {
        Map<String, Object> param = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        Integer day = getDay(commond.getCountType());
        if (day <= 2 && day > 0) {
            List<Long> dayList = null;
            if (day == 1) {
                //当天
                dayList = Helper.pastHour(new Date());
            } else {
                //昨天
                dayList = Helper.pastHour(Helper.getYesterday());
            }
            String sql = "select count(1) as count,FROM_UNIXTIME(add_time,'%Y-%m-%d %H') as time from cli_clientele_log WHERE ip is not null  and type=1 and add_time>=:startTime and add_time<:endTime GROUP BY  time";
            param.put("startTime", dayList.get(0));
            param.put("endTime", Helper.getBeginDayOfTomorrow());
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            List<Map<String, Object>> nowDay = setSalesTrendList(dayList, mapList);
            map.put("nowDay", nowDay);
        } else {
            String sql = "select count(1) count,FROM_UNIXTIME(add_time,'%Y-%m-%d') as time from cli_clientele_log WHERE ip is not null  and type=1 and add_time>=:startTime and add_time<:endTime GROUP BY  time";
            setParam(param, commond);
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            List<Map<String, Object>> maps = new ArrayList<>();
            Integer day1 = getDay(commond.getCountType());
            List<String> pastDateList=null;
            if (day>=5&&day<=6){
                pastDateList= Helper.getIntervalDate(Convert.toLong(param.get("startTime")),Convert.toLong(param.get("endTime")));
            }else {
                pastDateList = Helper.getPastDateList(day1);
            }
            for (String date : pastDateList) {
                Map<String, Object> dataMap = new HashMap<>();
                boolean isv = false;
                for (Map<String, Object> objectMap : mapList) {
                    if (objectMap.get("time").equals(date)) {
                        dataMap.put("date", objectMap.get("time"));
                        dataMap.put("value", objectMap.get("count"));
                        isv = true;
                    }
                }
                if (!isv) {
                    dataMap.put("date", date);
                    dataMap.put("value", 0);
                }
                maps.add(dataMap);
            }
            map.put("nowDay", maps);
            map.put("yesterday", null);
        }
        String countOnlineUserSql = "SELECT count(1) as num  from cli_clientele_log WHERE add_time>=:startTime and add_time<:endTime and type=1 and ip is not null ";
        Long countOnlineUser = baseDao.queryForLong(countOnlineUserSql, param);
        String countAccessSql = "SELECT count(1) as num  from cli_clientele_log WHERE add_time>=:startTime and add_time<:endTime and type=0 and ip is not null ";
        Long countAccessUser = baseDao.queryForLong(countAccessSql, param);
        map.put("countOnlineUser", countOnlineUser);
        map.put("countAccessUser", countAccessUser);
        return map;
    }

    /**
     * 统计客单价
     *
     * @return
     */
    public Map<String, Object> countOrderPrice(CountCommond commond) {
        Map<String, Object> param = new HashMap<>();
        Integer day = getDay(commond.getCountType());
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> nowDay = null;
        List<Map<String, Object>> yesterday = null;
        Long nowDayTotalPrice = Long.valueOf(0);
        if (day <= 2 && day > 0) {

            List<Long> dayList = null;
            if (day == 1) {
                //当天
                dayList = Helper.pastHour(new Date());
            } else {
                //昨天
                dayList = Helper.pastHour(Helper.getYesterday());
            }
            String sql = "select sum(payment_total_price) as value,FROM_UNIXTIME(payment_time,'%Y-%m-%d %H') as date from ord_payment_record WHERE is_pay_success=1 and payment_time>=:startTime and payment_time<:endTime GROUP BY  date";
            param.put("startTime", dayList.get(0));
            param.put("endTime", Helper.getBeginDayOfTomorrow());
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            nowDay = setOrderPrice(dayList, mapList);
            for (Map<String, Object> m : nowDay) {
                Long price = Convert.toLong(m.get("value"));
                if (Helper.isEmpty(price)) {
                    price = Long.valueOf(0);
                } else {
                    nowDayTotalPrice = nowDayTotalPrice + price;
                }
                if (m.containsKey("value")) {
                    m.remove("value");
                }
                m.put("value", Helper.transformF2Y(price));
            }

        } else {
            String sql = "select sum(payment_total_price) as value,FROM_UNIXTIME(payment_time,'%Y-%m-%d') as date from ord_payment_record WHERE is_pay_success=1 and payment_time>=:startTime and payment_time<:endTime GROUP BY  date";
            setParam(param, commond);
            List<Map<String, Object>> mapList = baseDao.queryForList(sql, param);
            nowDay = mapList;
            if (day == 0) {
                Map<String, Long> distanceTime = Helper.getDistanceTime(commond.getStartTime(), commond.getEndTime());
                day = distanceTime.get("day").intValue();
            }
            List<String> pastDateList=null;
            if (day>=5&&day<=6){
                pastDateList= Helper.getIntervalDate(Convert.toLong(param.get("startTime")),Convert.toLong(param.get("endTime")));
            }else {
                pastDateList = Helper.getPastDateList(day);
            }
            for (String date : pastDateList) {
                boolean isv = false;
                for (Map<String, Object> m : nowDay) {
                    if (m.get("date").equals(date)) {
                        Long price = Convert.toLong(m.get("value"));
                        if (Helper.isEmpty(price)) {
                            price = Long.valueOf(0);
                        } else {
                            nowDayTotalPrice = nowDayTotalPrice + price;
                        }
                        m.put("value", Helper.transformF2Y(price));
                        isv = true;
                    }

                }
                if (!isv) {
                    Map<String, Object> dateMap = new HashMap<>();
                    dateMap.put("value", 0);
                    dateMap.put("date", date);
                    nowDay.add(dateMap);
                }

            }

        }

        String countSql = "select count(1) from ord_payment_record WHERE is_pay_success=1 and payment_time>=:startTime and payment_time<:endTime ";
        Long count = baseDao.queryForLong(countSql, param);
        BigDecimal nowDayPrice = Helper.transformF2Y(nowDayTotalPrice);
        map.put("nowDay", nowDay);
        if (count > 0) {
            map.put("nowDayAvgPrice", nowDayPrice.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            map.put("nowDayAvgPrice", 0);
        }
        return map;
    }

    private List<Map<String, Object>> setOrderPrice(List<Long> dateList, List<Map<String, Object>> mapList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (mapList.size()>0){
            for (Long time : dateList) {
                boolean isv = false;
                Map<String, Object> map = new HashMap<>();
                String date = Helper.timestampToDate(time, Helper.DATE_PATTERN6);
                for (Map<String, Object> m : mapList) {
                    if (m.get("date").equals(date)) {
                        map.put("date", Helper.timestampToDate(time, Helper.DATE_PATTERN4));
                        map.put("value", m.get("value"));
                        isv = true;
                    }
                }
                if (!isv) {
                    map.put("date", Helper.timestampToDate(time, Helper.DATE_PATTERN4));
                    map.put("value", 0);
                }
                list.add(map);
            }
        }
        return list;
    }

    public List<Map<String, Object>> setSalesTrendList(List<Long> dateList, List<Map<String, Object>> mapList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (mapList.size()>0){
            for (Long time : dateList) {
                boolean isv = false;
                Map<String, Object> map = new HashMap<>();
                String date = Helper.timestampToDate(time, Helper.DATE_PATTERN6);
                String britishTime = Helper.toBritishTime(time);
                String[] split = britishTime.split(" ");
                for (Map<String, Object> m : mapList) {
                    if (m.get("time").equals(date)) {
                        map.put("value", m.get("count"));
                        map.put("comparison", split[0]);
                        map.put("date", split[1]);
                        isv = true;
                    }
                }
                if (!isv) {
                    map.put("value", 0);
                    map.put("comparison", split[0]);
                    map.put("date", split[1]);
                }
                list.add(map);
            }
        }
        return list;
    }

    private BigDecimal setPercent(Object num, Integer number) {
        String division = division(Convert.toInt(num), number);
        String numeral = division.substring(0, division.indexOf("%"));
        BigDecimal decimal = new BigDecimal(numeral);
        BigDecimal decimal1 = new BigDecimal("100");
        return decimal.divide(decimal1, 2, BigDecimal.ROUND_DOWN);
    }


    private void setEquipment(List<EquipmentDto> equipment) {
        EquipmentDto dto = null;
        List<String> list = equipment.stream().map(EquipmentDto::getItem).collect(Collectors.toList());
        if (!list.contains("PC")) {
            dto = new EquipmentDto();
            dto.setItem("PC");
            dto.setCount(0);
            dto.setPercent(new BigDecimal(0));
            equipment.add(dto);
        }
        if (!list.contains("移动端")) {
            dto = new EquipmentDto();
            dto.setItem("移动端");
            dto.setCount(0);
            dto.setPercent(new BigDecimal(0));
            equipment.add(dto);
        }
        if (!list.contains("其他")) {
            dto = new EquipmentDto();
            dto.setItem("其他");
            dto.setCount(0);
            dto.setPercent(new BigDecimal(0));
            equipment.add(dto);
        }

    }

    private void setPercentage(List<Map<String, Object>> list) {
        Integer number = 0;
        for (int i = 0; i < list.size(); i++) {
            number = number + Convert.toInt(list.get(i).get("num"));
        }
        for (Map<String, Object> objectMap : list) {
            objectMap.put("percentage", division(Convert.toInt(objectMap.get("num")), number));
        }
    }

    public String division(int num1, int num2) {
        String rate = "0.00%";
        //定义格式化起始位数
        String format = "0.00";
        if (num2 != 0 && num1 != 0) {
            DecimalFormat dec = new DecimalFormat(format);
            rate = dec.format((double) num1 / num2 * 100) + "%";
            while (true) {
                if (rate.equals(format + "%")) {
                    format = format + "0";
                    DecimalFormat dec1 = new DecimalFormat(format);
                    rate = dec1.format((double) num1 / num2 * 100) + "%";
                } else {
                    break;
                }
            }
        } else if (num1 != 0 && num2 == 0) {
            rate = "100%";
        }
        return rate;
    }

    /**
     * 根据条件获取不同时间戳
     *
     * @param countType
     * @return
     */
    public SearchTime getSearChTime(Integer countType) {
        SearchTime searchTime = new SearchTime();
        if (countType == null || countType == 1) {
            //当前时间的时间戳
            Long time = System.currentTimeMillis();
            //获取今天12点时间戳
            long todayStartTime = time - ((time + TimeZone.getDefault().getRawOffset()) % (24 * 60 * 60 * 1000L));
            searchTime.setStart(todayStartTime / 1000);
            //获取今天23:59 59秒
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            searchTime.setEnd(calendar.getTime().getTime() / 1000);
        }
        if (countType == 2) {
            //计算昨天0点时间戳
            getYesterday(searchTime);

            //计算获得昨天23:59 59秒
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH) - 1, 23, 59, 59);
            searchTime.setEnd(endCalendar.getTime().getTime() / 1000);
        }

        if (countType == 3) {
            //计算近七天时间戳
            getYesterday(searchTime);

            //计算获得七天23:59 59秒
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH) - 7, 23, 59, 59);
            searchTime.setEnd(endCalendar.getTime().getTime() / 1000);
        }
        if (countType == 4) {
            //计算近30天时间戳
            getYesterday(searchTime);


            //计算获得30天23:59 59秒
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH), endCalendar.get(Calendar.DAY_OF_MONTH) - 30, 0, 0, 0);
            searchTime.setEnd(endCalendar.getTime().getTime() / 1000);
        }
        if (countType == 5) {
            //获取上周周一0点时间戳
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONDAY), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            calendar.add(Calendar.DATE, -7);
            searchTime.setStart(calendar.getTime().getTime() / 1000);

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONDAY), endCalendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            endCalendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            endCalendar.add(Calendar.DATE, -1);
            searchTime.setEnd(endCalendar.getTime().getTime() / 1000);
        }
        if (countType == 6) {
            //获取上月月初0点时间戳
            Calendar calendar = Calendar.getInstance();
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, 1, 0, 0, 0);
            searchTime.setStart(calendar.getTime().getTime() / 1000);


            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH) - 1, endCalendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            searchTime.setEnd(endCalendar.getTime().getTime() / 1000);
        }
        return searchTime;
    }

    private void getYesterday(SearchTime searchTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) - 1, 0, 0, 0);
        searchTime.setStart(calendar.getTime().getTime() / 1000);
    }


    private void setParam(Map<String, Object> param, CountCommond commond) {
        param.clear();
        if (Helper.isNotEmpty(commond.getStartTime()) && Helper.isNotEmpty(commond.getEndTime())) {
            param.put("startTime", commond.getStartTime());
            param.put("endTime", commond.getEndTime());
        } else if (Helper.isNotEmpty(commond.getCountType())) {
            Integer day = getDay(commond.getCountType());
            if (day == 5) {
                Map<String, Long> date = Helper.getDate();
                String startDate = Helper.timestampToDate(date.get("date1"), Helper.DATE_PATTERN1);
                String endDate = Helper.timestampToDate(date.get("date2"), Helper.DATE_PATTERN1);
                long startTime = Helper.Date2TimeStamp(startDate, Helper.DATE_PATTERN1);
                long endTime = Helper.Date2TimeStamp(endDate, Helper.DATE_PATTERN1);
                param.put("startTime", startTime);
                param.put("endTime", endTime);
            } else if (day == 6) {
                Map<String, Long> monthDate = Helper.getMonthDate();
                String startDate = Helper.timestampToDate(monthDate.get("date1"), Helper.DATE_PATTERN1);
                String endDate = Helper.timestampToDate(monthDate.get("date2"), Helper.DATE_PATTERN1);
                long startTime = Helper.Date2TimeStamp(startDate, Helper.DATE_PATTERN1);
                long endTime = Helper.Date2TimeStamp(endDate, Helper.DATE_PATTERN1);
                param.put("startTime", startTime);
                param.put("endTime", endTime);
            } else {
                Long startTime = Helper.Date2TimeStamp(Helper.getPastDate(day - 1, new Date()), Helper.DATE_PATTERN3);
                Long endTime = Helper.Date2TimeStamp(Helper.formatDateByFormat(new Date(), Helper.DATE_PATTERN1), Helper.DATE_PATTERN1);
                param.put("startTime", startTime);
                param.put("endTime", endTime);
            }

        }
    }

    public Integer getDay(Integer type) {
        if (Helper.isNotEmpty(type)) {
            if (type == 1) {
                return 1;
            } else if (type == 2) {
                return 2;
            } else if (type == 3) {
                return 7;
            } else if (type == 4) {
                return 15;
            } else if (type == 5) {
                return 5;
            } else if (type == 6) {
                return 6;
            } else if (type == 7) {
                return 30;
            } else if (type == 8) {
                return 365;
            }
        }
        return 0;
    }
}
