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
* Author:      deng
* Mail:        dengy@mazentop.com
* Date:        09:29 2020/03/31
* Company:     美赞拓
* Version:     1.0
* Description: SysSubscriber实体
*/
@SuppressWarnings("all")
public class SysSubscriber extends BaseBean<SysSubscriber> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_subscriber";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 电子邮件
     */
    public static final String F_EMAIL = "email";
    /**
     * IP
     */
    public static final String F_IP = "ip";
    /**
     * 国家
     */
    public static final String F_COUNTRY_NAME = "country_name";
    /**
     * 省
     */
    public static final String F_PROVINCE = "province";
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
        put(F_EMAIL, null);
        put(F_IP, null);
        put(F_COUNTRY_NAME, null);
        put(F_PROVINCE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public SysSubscriber() {
        super();
    }

    public SysSubscriber(Map<String, Object> map) {
        super(map);
    }

    public SysSubscriber(String id) {
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
    public SysSubscriber setId(String id) {
        set(F_ID, id);
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
    public SysSubscriber setEmail(String email) {
        set(F_EMAIL, email);
        return this;
    }
    /**
     * @return ip to ip IP<BR/>
     */
    public String getIp() {
        return getTypedValue(F_IP, String.class);
    }
    /**
     * @param ip to ip IP set
     */
    public SysSubscriber setIp(String ip) {
        set(F_IP, ip);
        return this;
    }
    /**
     * @return country_name to countryName 国家<BR/>
     */
    public String getCountryName() {
        return getTypedValue(F_COUNTRY_NAME, String.class);
    }
    /**
     * @param countryName to country_name 国家 set
     */
    public SysSubscriber setCountryName(String countryName) {
        set(F_COUNTRY_NAME, countryName);
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
    public SysSubscriber setProvince(String province) {
        set(F_PROVINCE, province);
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
    public SysSubscriber setRemark(String remark) {
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
    public SysSubscriber setAddTime(Long addTime) {
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
    public SysSubscriber setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysSubscriber setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysSubscriber me(){
        return new SysSubscriber();
    }

    private static class Mapper implements RowMapper<SysSubscriber> {

        private Supplier<SysSubscriber> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysSubscriber mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysSubscriber bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setEmail(Utils.toCast(columnsName.contains(F_EMAIL) ? rs.getObject(F_EMAIL) : null, String.class));
            bean.setIp(Utils.toCast(columnsName.contains(F_IP) ? rs.getObject(F_IP) : null, String.class));
            bean.setCountryName(Utils.toCast(columnsName.contains(F_COUNTRY_NAME) ? rs.getObject(F_COUNTRY_NAME) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysSubscriber> newMapper(){
        return newMapper(SysSubscriber::new);
    }

    public RowMapper<SysSubscriber> newMapper(Supplier<SysSubscriber> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysSubscriber {
        @Override
        public abstract RowMapper<SysSubscriber> newMapper();
    }
}
