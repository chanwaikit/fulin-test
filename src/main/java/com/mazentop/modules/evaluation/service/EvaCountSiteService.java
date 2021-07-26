package com.mazentop.modules.evaluation.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.modules.emp.commond.CountCommond;
import com.mazentop.modules.emp.dto.BaseGroupPlotDto;
import com.mazentop.modules.emp.dto.BasePlotDto;
import com.mazentop.modules.emp.dto.EquipmentDto;
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
public class EvaCountSiteService {

    @Autowired
    BaseDao baseDao;

    /**
     * 统计概览
     *
     * @param
     * @return
     */
    public Map<String, Object> countOverview(CountCommond commond) {
        //获取当天开始的时间戳
        Long startTime = DateUtil.beginOfDay(new DateTime()).getTime()/1000;
        Long endTime = DateUtil.endOfDay(new DateTime()).getTime()/1000;
        // 获取昨天的时间戳

        Map<String, Object> map = new HashMap<>();
        Map<String, Object> param = new HashMap<>(4);
        param.put("startTime", startTime);
        param.put("endTime", endTime);

        // 所有加购商品数量
        String sumTotalProductNumSql = "select sum(product_num) as productNum  from eva_ord_order WHERE is_enable =1 and status = 6";
        Long totalProductNum = baseDao.queryForLong(sumTotalProductNumSql);
        map.put("totalProduct", totalProductNum);


        // 所有返现金额
        String sumTotalRebateql = "select sum(o.rebate) from eva_ord_order o, eva_cash_back_apply c WHERE  o.id=c.eva_order_id and o.`status` =6 and o.is_enable =1 and c.`status` = 4";
        Long totalRebate = baseDao.queryForLong(sumTotalRebateql);
        map.put("totalCashback", Helper.transformF2Y(totalRebate).toString());


        //统计当天加购商品数量
        String productSql = "select sum(product_num) as productNum  from eva_ord_order WHERE is_enable =1 and status = 6  and add_time >=:startTime  and add_time<=:endTime";
        Long toDayProduct = baseDao.queryForLong(productSql, param);
        map.put("toDayProduct", toDayProduct);


        //统计当天返现金额
        String cashbackSql = "select sum(o.rebate) from eva_ord_order o, eva_cash_back_apply c WHERE  o.id=c.eva_order_id and o.`status` =6 and o.is_enable =1 and c.`status` = 4 and  c.reviewer_time >=:startTime  and c.reviewer_time<=:endTime";
        Long toDayCashback = baseDao.queryForLong(cashbackSql,param);
        map.put("toDayCashback", Helper.transformF2Y(toDayCashback).toString());

        // 计算较昨天
//        Long yesterdayProductNum= baseDao.queryForLong(productSql, param);
//        double riseProduct = (double) (toDayProduct - yesterdayProductNum) / totalProductNum;
//        map.put("riseProduct", String.format("%.2f", riseProduct * 100));
//
//
//        Long yesterdayInsertUserNum = baseDao.queryForLong(cashbackSql, param);
//        double riseCashback = (double) (toDayCashback - yesterdayInsertUserNum) / totalRebate;
//        map.put("riseCashback", String.format("%.2f", riseCashback * 100));


        return map;
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
            String sql = "select sum(o.rebate) as count,FROM_UNIXTIME(c.reviewer_time,'%Y-%m-%d %H') as time from eva_ord_order o, eva_cash_back_apply c WHERE  o.id=c.eva_order_id and o.`status` =6 and o.is_enable =1 and c.`status` = 4  and c.reviewer_time>=:startTime and c.reviewer_time<:endTime GROUP BY time";
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
            String sql = "select sum(o.rebate) as count,FROM_UNIXTIME(c.reviewer_time,'%Y-%m-%d') as time from eva_ord_order o, eva_cash_back_apply c WHERE  o.id=c.eva_order_id and o.`status` =6 and o.is_enable =1 and c.`status` = 4  and c.reviewer_time>=:startTime and c.reviewer_time<:endTime GROUP BY time";
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
                            dataMap.put("value", Helper.transformF2Y(objectMap.get("count")).toString());
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
        String sumSalesSql = "select sum(o.rebate) from eva_ord_order o, eva_cash_back_apply c WHERE  o.id=c.eva_order_id and o.`status` =6 and o.is_enable =1 and c.`status` = 4 and  c.reviewer_time >=:startTime  and c.reviewer_time<=:endTime";
        Long sumSales = baseDao.queryForLong(sumSalesSql, param);
        map.put("cashback", Helper.transformF2Y(sumSales).toString());
        return map;
    }


    /**
     *  获取最近订单
     * @param commond
     * @return
     */
    public List<Map<String, Object>> getNearestOrder(CountCommond commond) {
        String sql = "select o.product_name,o.amazon_order_no,o.product_sku,u.email,o.rebate , c.reviewer_time " +
                "from eva_ord_order o, eva_cash_back_apply c,cli_clientele_info u WHERE  " +
                "o.id=c.eva_order_id and o.fk_clientele_id=u.id  and o.`status` =6 and o.is_enable =1 and c.`status` = 4  " +
                "ORDER BY c.reviewer_time desc limit 5";
        List<Map<String, Object>> list =  baseDao.queryForList(sql);
        list.forEach(info -> {
            info.put("rebateStr", Helper.transformF2Y(info.get("rebate")).toString());
        });
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
