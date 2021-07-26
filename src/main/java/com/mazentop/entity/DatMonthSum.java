package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        09:50 2020/04/10
* Company:     美赞拓
* Version:     1.0
* Description: DatMonthSum实体
*/
@SuppressWarnings("all")
public class DatMonthSum extends BaseBean<DatMonthSum> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "dat_month_sum";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 月份
    */
    public static final String F_MONTH = "month";
    /**
    * 总金额
    */
    public static final String F_TOTAL_SUM = "total_sum";
    /**
    * 总单数
    */
    public static final String F_TOTAL_ORDER_NUMBER = "total_order_number";
    /**
    * 总商品数
    */
    public static final String F_TOTAL_PRODUCT_NUMBER = "total_product_number";
    /**
    * 总uv量
    */
    public static final String F_TOTAL_UV_NUMBER = "total_uv_number";
    /**
    * 总IP量
    */
    public static final String F_TOTAL_IP_NUMBER = "total_ip_number";
    /**
    * 总PV量
    */
    public static final String F_TOTAL_PV_NUMBER = "total_pv_number";
    /**
    * 币种
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 公司编号
    */
    public static final String F_COMPANY_ID = "company_Id";
    /**
    * 公司名称
    */
    public static final String F_COMPANY_NAME = "company_name";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_MONTH, null);
        put(F_TOTAL_SUM, null);
        put(F_TOTAL_ORDER_NUMBER, null);
        put(F_TOTAL_PRODUCT_NUMBER, null);
        put(F_TOTAL_UV_NUMBER, null);
        put(F_TOTAL_IP_NUMBER, null);
        put(F_TOTAL_PV_NUMBER, null);
        put(F_CURRENCY, null);
        put(F_ADD_TIME, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_REMARK, null);
    }

    public DatMonthSum() {
        super();
    }

    public DatMonthSum(Map<String, Object> map) {
        super(map);
    }

    public DatMonthSum(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 编号<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 编号 set
    */
    public DatMonthSum setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return month to month 月份<BR/>
    */
    public Long getMonth() {
        return getTypedValue(F_MONTH, Long.class);
    }
    /**
    * @param month to month 月份 set
    */
    public DatMonthSum setMonth(Long month) {
        set(F_MONTH, month);
        return this;
    }
    /**
    * @return total_sum to totalSum 总金额<BR/>
    */
    public Long getTotalSum() {
        return getTypedValue(F_TOTAL_SUM, Long.class);
    }
    /**
    * @param totalSum to total_sum 总金额 set
    */
    public DatMonthSum setTotalSum(Long totalSum) {
        set(F_TOTAL_SUM, totalSum);
        return this;
    }
    /**
    * @return total_order_number to totalOrderNumber 总单数<BR/>
    */
    public Integer getTotalOrderNumber() {
        return getTypedValue(F_TOTAL_ORDER_NUMBER, Integer.class);
    }
    /**
    * @param totalOrderNumber to total_order_number 总单数 set
    */
    public DatMonthSum setTotalOrderNumber(Integer totalOrderNumber) {
        set(F_TOTAL_ORDER_NUMBER, totalOrderNumber);
        return this;
    }
    /**
    * @return total_product_number to totalProductNumber 总商品数<BR/>
    */
    public Integer getTotalProductNumber() {
        return getTypedValue(F_TOTAL_PRODUCT_NUMBER, Integer.class);
    }
    /**
    * @param totalProductNumber to total_product_number 总商品数 set
    */
    public DatMonthSum setTotalProductNumber(Integer totalProductNumber) {
        set(F_TOTAL_PRODUCT_NUMBER, totalProductNumber);
        return this;
    }
    /**
    * @return total_uv_number to totalUvNumber 总uv量<BR/>
    */
    public Integer getTotalUvNumber() {
        return getTypedValue(F_TOTAL_UV_NUMBER, Integer.class);
    }
    /**
    * @param totalUvNumber to total_uv_number 总uv量 set
    */
    public DatMonthSum setTotalUvNumber(Integer totalUvNumber) {
        set(F_TOTAL_UV_NUMBER, totalUvNumber);
        return this;
    }
    /**
    * @return total_ip_number to totalIpNumber 总IP量<BR/>
    */
    public Integer getTotalIpNumber() {
        return getTypedValue(F_TOTAL_IP_NUMBER, Integer.class);
    }
    /**
    * @param totalIpNumber to total_ip_number 总IP量 set
    */
    public DatMonthSum setTotalIpNumber(Integer totalIpNumber) {
        set(F_TOTAL_IP_NUMBER, totalIpNumber);
        return this;
    }
    /**
    * @return total_pv_number to totalPvNumber 总PV量<BR/>
    */
    public Integer getTotalPvNumber() {
        return getTypedValue(F_TOTAL_PV_NUMBER, Integer.class);
    }
    /**
    * @param totalPvNumber to total_pv_number 总PV量 set
    */
    public DatMonthSum setTotalPvNumber(Integer totalPvNumber) {
        set(F_TOTAL_PV_NUMBER, totalPvNumber);
        return this;
    }
    /**
    * @return currency to currency 币种<BR/>
    */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
    * @param currency to currency 币种 set
    */
    public DatMonthSum setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
    * @return add_time to addTime 添加时间<BR/>
    */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
    * @param addTime to add_time 添加时间 set
    */
    public DatMonthSum setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
    * @return company_Id to companyId 公司编号<BR/>
    */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
    * @param companyId to company_Id 公司编号 set
    */
    public DatMonthSum setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
    * @return company_name to companyName 公司名称<BR/>
    */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
    * @param companyName to company_name 公司名称 set
    */
    public DatMonthSum setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
    * @return remark to remark 备注<BR/>
    */
    public String getRemark() {
        return getTypedValue(F_REMARK, String.class);
    }
    /**
    * @param remark to remark 备注 set
    */
    public DatMonthSum setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public DatMonthSum setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static DatMonthSum me(){
        return new DatMonthSum();
    }

    private static class Mapper implements RowMapper<DatMonthSum> {

        private Supplier<DatMonthSum> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public DatMonthSum mapRow(ResultSet rs, int rownum) throws SQLException {
            DatMonthSum bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setMonth(Utils.toCast(rs.getObject(F_MONTH), Long.class));
            bean.setTotalSum(Utils.toCast(rs.getObject(F_TOTAL_SUM), Long.class));
            bean.setTotalOrderNumber(Utils.toCast(rs.getObject(F_TOTAL_ORDER_NUMBER), Integer.class));
            bean.setTotalProductNumber(Utils.toCast(rs.getObject(F_TOTAL_PRODUCT_NUMBER), Integer.class));
            bean.setTotalUvNumber(Utils.toCast(rs.getObject(F_TOTAL_UV_NUMBER), Integer.class));
            bean.setTotalIpNumber(Utils.toCast(rs.getObject(F_TOTAL_IP_NUMBER), Integer.class));
            bean.setTotalPvNumber(Utils.toCast(rs.getObject(F_TOTAL_PV_NUMBER), Integer.class));
            bean.setCurrency(Utils.toCast(rs.getObject(F_CURRENCY), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<DatMonthSum> newMapper(){
        return newMapper(DatMonthSum::new);
    }

    public RowMapper<DatMonthSum> newMapper(Supplier<DatMonthSum> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends DatMonthSum {
        @Override
        public abstract RowMapper<DatMonthSum> newMapper();
    }
}
