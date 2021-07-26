package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      dengy
* Mail:        dengy@mazentop.com
* Date:        15:49 2020/04/13
* Company:     美赞拓
* Version:     1.0
* Description: SysNotLoginPurchaseInfo实体
*/
@SuppressWarnings("all")
public class SysNotLoginPurchaseInfo extends BaseBean<SysNotLoginPurchaseInfo> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_not_login_purchase_info";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 无登录购买人姓名
     */
    public static final String F_NOT_LOGIN_NAME = "not_login_name";
    /**
     * 名
     */
    public static final String F_NAME = "name";
    /**
     * 姓
     */
    public static final String F_SURNAME = "surname";
    /**
     * 电子邮件
     */
    public static final String F_EMAIL = "email";
    /**
     * 无登录购买人国家
     */
    public static final String F_COUNTRY = "country";
    /**
     * 电话
     */
    public static final String F_PHONE = "phone";
    /**
     * 省
     */
    public static final String F_PROVINCE = "province";
    /**
     * 城市
     */
    public static final String F_CITY = "city";
    /**
     * 街道
     */
    public static final String F_SHEET = "sheet";
    /**
     * 地址
     */
    public static final String F_ADDRESS = "address";
    /**
     * 邮编
     */
    public static final String F_POST = "post";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * ip地址
     */
    public static final String F_NOT_LOGIN_IP = "not_login_ip";
    /**
     * 无登录购买订单记录
     */
    public static final String F_NOT_LOGIN_ORDER_NO = "not_login_order_no";
    /**
     * 支付是否成功
     */
    public static final String F_IS_PAY_SUCCESS = "is_pay_success";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_NOT_LOGIN_NAME, null);
        put(F_NAME, null);
        put(F_SURNAME, null);
        put(F_EMAIL, null);
        put(F_COUNTRY, null);
        put(F_PHONE, null);
        put(F_PROVINCE, null);
        put(F_CITY, null);
        put(F_SHEET, null);
        put(F_ADDRESS, null);
        put(F_POST, null);
        put(F_ADD_TIME, null);
        put(F_NOT_LOGIN_IP, null);
        put(F_NOT_LOGIN_ORDER_NO, null);
        put(F_IS_PAY_SUCCESS, null);
    }

    public SysNotLoginPurchaseInfo() {
        super();
    }

    public SysNotLoginPurchaseInfo(Map<String, Object> map) {
        super(map);
    }

    public SysNotLoginPurchaseInfo(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 主键 set
     */
    public SysNotLoginPurchaseInfo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return not_login_name to notLoginName 无登录购买人姓名<BR/>
     */
    public String getNotLoginName() {
        return getTypedValue(F_NOT_LOGIN_NAME, String.class);
    }
    /**
     * @param notLoginName to not_login_name 无登录购买人姓名 set
     */
    public SysNotLoginPurchaseInfo setNotLoginName(String notLoginName) {
        set(F_NOT_LOGIN_NAME, notLoginName);
        return this;
    }
    /**
     * @return name to name 名<BR/>
     */
    public String getName() {
        return getTypedValue(F_NAME, String.class);
    }
    /**
     * @param name to name 名 set
     */
    public SysNotLoginPurchaseInfo setName(String name) {
        set(F_NAME, name);
        return this;
    }
    /**
     * @return surname to surname 姓<BR/>
     */
    public String getSurname() {
        return getTypedValue(F_SURNAME, String.class);
    }
    /**
     * @param surname to surname 姓 set
     */
    public SysNotLoginPurchaseInfo setSurname(String surname) {
        set(F_SURNAME, surname);
        return this;
    }
    /**
     * @return email to email 电子邮件<BR/>
     */
    public String getEmail() {
        return getTypedValue(F_EMAIL, String.class);
    }
    /**
     * @param email to email 电子邮件 set
     */
    public SysNotLoginPurchaseInfo setEmail(String email) {
        set(F_EMAIL, email);
        return this;
    }
    /**
     * @return country to country 无登录购买人国家<BR/>
     */
    public String getCountry() {
        return getTypedValue(F_COUNTRY, String.class);
    }
    /**
     * @param country to country 无登录购买人国家 set
     */
    public SysNotLoginPurchaseInfo setCountry(String country) {
        set(F_COUNTRY, country);
        return this;
    }
    /**
     * @return phone to phone 电话<BR/>
     */
    public String getPhone() {
        return getTypedValue(F_PHONE, String.class);
    }
    /**
     * @param phone to phone 电话 set
     */
    public SysNotLoginPurchaseInfo setPhone(String phone) {
        set(F_PHONE, phone);
        return this;
    }
    /**
     * @return province to province 省<BR/>
     */
    public String getProvince() {
        return getTypedValue(F_PROVINCE, String.class);
    }
    /**
     * @param province to province 省 set
     */
    public SysNotLoginPurchaseInfo setProvince(String province) {
        set(F_PROVINCE, province);
        return this;
    }
    /**
     * @return city to city 城市<BR/>
     */
    public String getCity() {
        return getTypedValue(F_CITY, String.class);
    }
    /**
     * @param city to city 城市 set
     */
    public SysNotLoginPurchaseInfo setCity(String city) {
        set(F_CITY, city);
        return this;
    }
    /**
     * @return sheet to sheet 街道<BR/>
     */
    public String getSheet() {
        return getTypedValue(F_SHEET, String.class);
    }
    /**
     * @param sheet to sheet 街道 set
     */
    public SysNotLoginPurchaseInfo setSheet(String sheet) {
        set(F_SHEET, sheet);
        return this;
    }
    /**
     * @return address to address 地址<BR/>
     */
    public String getAddress() {
        return getTypedValue(F_ADDRESS, String.class);
    }
    /**
     * @param address to address 地址 set
     */
    public SysNotLoginPurchaseInfo setAddress(String address) {
        set(F_ADDRESS, address);
        return this;
    }
    /**
     * @return post to post 邮编<BR/>
     */
    public String getPost() {
        return getTypedValue(F_POST, String.class);
    }
    /**
     * @param post to post 邮编 set
     */
    public SysNotLoginPurchaseInfo setPost(String post) {
        set(F_POST, post);
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
    public SysNotLoginPurchaseInfo setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return not_login_ip to notLoginIp ip地址<BR/>
     */
    public String getNotLoginIp() {
        return getTypedValue(F_NOT_LOGIN_IP, String.class);
    }
    /**
     * @param notLoginIp to not_login_ip ip地址 set
     */
    public SysNotLoginPurchaseInfo setNotLoginIp(String notLoginIp) {
        set(F_NOT_LOGIN_IP, notLoginIp);
        return this;
    }
    /**
     * @return not_login_order_no to notLoginOrderNo 无登录购买订单记录<BR/>
     */
    public String getNotLoginOrderNo() {
        return getTypedValue(F_NOT_LOGIN_ORDER_NO, String.class);
    }
    /**
     * @param notLoginOrderNo to not_login_order_no 无登录购买订单记录 set
     */
    public SysNotLoginPurchaseInfo setNotLoginOrderNo(String notLoginOrderNo) {
        set(F_NOT_LOGIN_ORDER_NO, notLoginOrderNo);
        return this;
    }
    /**
     * @return is_pay_success to isPaySuccess 支付是否成功<BR/>
     */
    public Integer getIsPaySuccess() {
        return getTypedValue(F_IS_PAY_SUCCESS, Integer.class);
    }
    /**
     * @param isPaySuccess to is_pay_success 支付是否成功 set
     */
    public SysNotLoginPurchaseInfo setIsPaySuccess(Integer isPaySuccess) {
        set(F_IS_PAY_SUCCESS, isPaySuccess);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysNotLoginPurchaseInfo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysNotLoginPurchaseInfo me(){
        return new SysNotLoginPurchaseInfo();
    }

    private static class Mapper implements RowMapper<SysNotLoginPurchaseInfo> {

        private Supplier<SysNotLoginPurchaseInfo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysNotLoginPurchaseInfo mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysNotLoginPurchaseInfo bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setNotLoginName(Utils.toCast(columnsName.contains(F_NOT_LOGIN_NAME) ? rs.getObject(F_NOT_LOGIN_NAME) : null, String.class));
            bean.setName(Utils.toCast(columnsName.contains(F_NAME) ? rs.getObject(F_NAME) : null, String.class));
            bean.setSurname(Utils.toCast(columnsName.contains(F_SURNAME) ? rs.getObject(F_SURNAME) : null, String.class));
            bean.setEmail(Utils.toCast(columnsName.contains(F_EMAIL) ? rs.getObject(F_EMAIL) : null, String.class));
            bean.setCountry(Utils.toCast(columnsName.contains(F_COUNTRY) ? rs.getObject(F_COUNTRY) : null, String.class));
            bean.setPhone(Utils.toCast(columnsName.contains(F_PHONE) ? rs.getObject(F_PHONE) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setCity(Utils.toCast(columnsName.contains(F_CITY) ? rs.getObject(F_CITY) : null, String.class));
            bean.setSheet(Utils.toCast(columnsName.contains(F_SHEET) ? rs.getObject(F_SHEET) : null, String.class));
            bean.setAddress(Utils.toCast(columnsName.contains(F_ADDRESS) ? rs.getObject(F_ADDRESS) : null, String.class));
            bean.setPost(Utils.toCast(columnsName.contains(F_POST) ? rs.getObject(F_POST) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setNotLoginIp(Utils.toCast(columnsName.contains(F_NOT_LOGIN_IP) ? rs.getObject(F_NOT_LOGIN_IP) : null, String.class));
            bean.setNotLoginOrderNo(Utils.toCast(columnsName.contains(F_NOT_LOGIN_ORDER_NO) ? rs.getObject(F_NOT_LOGIN_ORDER_NO) : null, String.class));
            bean.setIsPaySuccess(Utils.toCast(columnsName.contains(F_IS_PAY_SUCCESS) ? rs.getObject(F_IS_PAY_SUCCESS) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysNotLoginPurchaseInfo> newMapper(){
        return newMapper(SysNotLoginPurchaseInfo::new);
    }

    public RowMapper<SysNotLoginPurchaseInfo> newMapper(Supplier<SysNotLoginPurchaseInfo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysNotLoginPurchaseInfo {
        @Override
        public abstract RowMapper<SysNotLoginPurchaseInfo> newMapper();
    }
}
