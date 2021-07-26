package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        10:05 2020/04/10
* Company:     美赞拓
* Version:     1.0
* Description: SysLoginLog实体
*/
@SuppressWarnings("all")
public class SysLoginLog extends BaseBean<SysLoginLog> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_login_log";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 登录名
    */
    public static final String F_LOGIN_NAME = "login_name";
    /**
    * 登录时间
    */
    public static final String F_LOGIN_TIME = "login_time";
    /**
    * 登录IP
    */
    public static final String F_LOGIN_IP = "login_ip";
    /**
    * 是否登录成功
    */
    public static final String F_IS_SUCCESS = "is_success";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_LOGIN_NAME, null);
        put(F_LOGIN_TIME, null);
        put(F_LOGIN_IP, null);
        put(F_IS_SUCCESS, null);
        put(F_REMARK, null);
    }

    public SysLoginLog() {
        super();
    }

    public SysLoginLog(Map<String, Object> map) {
        super(map);
    }

    public SysLoginLog(String id) {
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
    public SysLoginLog setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return login_name to loginName 登录名<BR/>
    */
    public String getLoginName() {
        return getTypedValue(F_LOGIN_NAME, String.class);
    }
    /**
    * @param loginName to login_name 登录名 set
    */
    public SysLoginLog setLoginName(String loginName) {
        set(F_LOGIN_NAME, loginName);
        return this;
    }
    /**
    * @return login_time to loginTime 登录时间<BR/>
    */
    public Long getLoginTime() {
        return getTypedValue(F_LOGIN_TIME, Long.class);
    }
    /**
    * @param loginTime to login_time 登录时间 set
    */
    public SysLoginLog setLoginTime(Long loginTime) {
        set(F_LOGIN_TIME, loginTime);
        return this;
    }
    /**
    * @return login_ip to loginIp 登录IP<BR/>
    */
    public String getLoginIp() {
        return getTypedValue(F_LOGIN_IP, String.class);
    }
    /**
    * @param loginIp to login_ip 登录IP set
    */
    public SysLoginLog setLoginIp(String loginIp) {
        set(F_LOGIN_IP, loginIp);
        return this;
    }
    /**
    * @return is_success to isSuccess 是否登录成功<BR/>
    */
    public Integer getIsSuccess() {
        return getTypedValue(F_IS_SUCCESS, Integer.class);
    }
    /**
    * @param isSuccess to is_success 是否登录成功 set
    */
    public SysLoginLog setIsSuccess(Integer isSuccess) {
        set(F_IS_SUCCESS, isSuccess);
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
    public SysLoginLog setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysLoginLog setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysLoginLog me(){
        return new SysLoginLog();
    }

    private static class Mapper implements RowMapper<SysLoginLog> {

        private Supplier<SysLoginLog> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysLoginLog mapRow(ResultSet rs, int rownum) throws SQLException {
            SysLoginLog bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setLoginName(Utils.toCast(rs.getObject(F_LOGIN_NAME), String.class));
            bean.setLoginTime(Utils.toCast(rs.getObject(F_LOGIN_TIME), Long.class));
            bean.setLoginIp(Utils.toCast(rs.getObject(F_LOGIN_IP), String.class));
            bean.setIsSuccess(Utils.toCast(rs.getObject(F_IS_SUCCESS), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysLoginLog> newMapper(){
        return newMapper(SysLoginLog::new);
    }

    public RowMapper<SysLoginLog> newMapper(Supplier<SysLoginLog> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysLoginLog {
        @Override
        public abstract RowMapper<SysLoginLog> newMapper();
    }
}
