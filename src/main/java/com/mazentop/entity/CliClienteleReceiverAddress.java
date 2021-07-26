package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        16:57 2020/04/14
* Company:     美赞拓
* Version:     1.0
* Description: CliClienteleReceiverAddress实体
*/
@SuppressWarnings("all")
public class CliClienteleReceiverAddress extends BaseBean<CliClienteleReceiverAddress> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "cli_clientele_receiver_address";

    /**
     * 顾客地址表主键
     */
    public static final String F_ID = "id";
    /**
     * 顾客编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 姓
     */
    public static final String F_CLIENT_SURNAME = "client_surname";
    /**
     * 名
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 电子邮件
     */
    public static final String F_EMAIL = "email";
    /**
     * 手机
     */
    public static final String F_PHONE = "phone";
    /**
     * 国家
     */
    public static final String F_COUNTRY = "country";
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
     * 是否为默认地址 0否 1 是
     */
    public static final String F_IS_DEFAULT = "is_default";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_SURNAME, null);
        put(F_CLIENT_NAME, null);
        put(F_EMAIL, null);
        put(F_PHONE, null);
        put(F_COUNTRY, null);
        put(F_PROVINCE, null);
        put(F_CITY, null);
        put(F_SHEET, null);
        put(F_ADDRESS, null);
        put(F_POST, null);
        put(F_IS_DEFAULT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public CliClienteleReceiverAddress() {
        super();
    }

    public CliClienteleReceiverAddress(Map<String, Object> map) {
        super(map);
    }

    public CliClienteleReceiverAddress(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 顾客地址表主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 顾客地址表主键 set
     */
    public CliClienteleReceiverAddress setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 顾客编号<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 顾客编号 set
     */
    public CliClienteleReceiverAddress setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return client_surname to clientSurname 姓<BR/>
     */
    public String getClientSurname() {
        return getTypedValue(F_CLIENT_SURNAME, String.class);
    }
    /**
     * @param clientSurname to client_surname 姓 set
     */
    public CliClienteleReceiverAddress setClientSurname(String clientSurname) {
        set(F_CLIENT_SURNAME, clientSurname);
        return this;
    }
    /**
     * @return client_name to clientName 名<BR/>
     */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
     * @param clientName to client_name 名 set
     */
    public CliClienteleReceiverAddress setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
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
    public CliClienteleReceiverAddress setEmail(String email) {
        set(F_EMAIL, email);
        return this;
    }
    /**
     * @return phone to phone 手机<BR/>
     */
    public String getPhone() {
        return getTypedValue(F_PHONE, String.class);
    }
    /**
     * @param phone to phone 手机 set
     */
    public CliClienteleReceiverAddress setPhone(String phone) {
        set(F_PHONE, phone);
        return this;
    }
    /**
     * @return country to country 国家<BR/>
     */
    public String getCountry() {
        return getTypedValue(F_COUNTRY, String.class);
    }
    /**
     * @param country to country 国家 set
     */
    public CliClienteleReceiverAddress setCountry(String country) {
        set(F_COUNTRY, country);
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
    public CliClienteleReceiverAddress setProvince(String province) {
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
    public CliClienteleReceiverAddress setCity(String city) {
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
    public CliClienteleReceiverAddress setSheet(String sheet) {
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
    public CliClienteleReceiverAddress setAddress(String address) {
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
    public CliClienteleReceiverAddress setPost(String post) {
        set(F_POST, post);
        return this;
    }
    /**
     * @return is_default to isDefault 是否为默认地址 0否 1 是<BR/>
     */
    public Integer getIsDefault() {
        return getTypedValue(F_IS_DEFAULT, Integer.class);
    }
    /**
     * @param isDefault to is_default 是否为默认地址 0否 1 是 set
     */
    public CliClienteleReceiverAddress setIsDefault(Integer isDefault) {
        set(F_IS_DEFAULT, isDefault);
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
    public CliClienteleReceiverAddress setRemark(String remark) {
        set(F_REMARK, remark);
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
    public CliClienteleReceiverAddress setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return add_user_id to addUserId 添加人编号<BR/>
     */
    public String getAddUserId() {
        return getTypedValue(F_ADD_USER_ID, String.class);
    }
    /**
     * @param addUserId to add_user_id 添加人编号 set
     */
    public CliClienteleReceiverAddress setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliClienteleReceiverAddress setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliClienteleReceiverAddress me(){
        return new CliClienteleReceiverAddress();
    }

    private static class Mapper implements RowMapper<CliClienteleReceiverAddress> {

        private Supplier<CliClienteleReceiverAddress> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliClienteleReceiverAddress mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            CliClienteleReceiverAddress bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setClientSurname(Utils.toCast(columnsName.contains(F_CLIENT_SURNAME) ? rs.getObject(F_CLIENT_SURNAME) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setEmail(Utils.toCast(columnsName.contains(F_EMAIL) ? rs.getObject(F_EMAIL) : null, String.class));
            bean.setPhone(Utils.toCast(columnsName.contains(F_PHONE) ? rs.getObject(F_PHONE) : null, String.class));
            bean.setCountry(Utils.toCast(columnsName.contains(F_COUNTRY) ? rs.getObject(F_COUNTRY) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setCity(Utils.toCast(columnsName.contains(F_CITY) ? rs.getObject(F_CITY) : null, String.class));
            bean.setSheet(Utils.toCast(columnsName.contains(F_SHEET) ? rs.getObject(F_SHEET) : null, String.class));
            bean.setAddress(Utils.toCast(columnsName.contains(F_ADDRESS) ? rs.getObject(F_ADDRESS) : null, String.class));
            bean.setPost(Utils.toCast(columnsName.contains(F_POST) ? rs.getObject(F_POST) : null, String.class));
            bean.setIsDefault(Utils.toCast(columnsName.contains(F_IS_DEFAULT) ? rs.getObject(F_IS_DEFAULT) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliClienteleReceiverAddress> newMapper(){
        return newMapper(CliClienteleReceiverAddress::new);
    }

    public RowMapper<CliClienteleReceiverAddress> newMapper(Supplier<CliClienteleReceiverAddress> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliClienteleReceiverAddress {
        @Override
        public abstract RowMapper<CliClienteleReceiverAddress> newMapper();
    }
}
