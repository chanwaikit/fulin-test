package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        11:16 2020/05/15
* Company:     美赞拓
* Version:     1.0
* Description: CliClienteleLog实体
*/
@SuppressWarnings("all")
public class CliClienteleLog extends BaseBean<CliClienteleLog> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "cli_clientele_log";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 用户id
    */
    public static final String F_CLI_CLIENTELE_ID = "cli_clientele_id";
    /**
    * 登录成功创建的session
    */
    public static final String F_SESSION_ID = "session_id";
    /**
    * 登录状态
    */
    public static final String F_STATUS = "status";
    /**
    * 0临时访问1登录访问
    */
    public static final String F_TYPE = "type";
    /**
    * ip
    */
    public static final String F_IP = "ip";
    /**
    * 登录设备
    */
    public static final String F_DEVICE = "device";
    /**
    * 离线时间
    */
    public static final String F_LEAVE_TIME = "leave_time";
    /**
    * 登录时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_CLI_CLIENTELE_ID, null);
        put(F_SESSION_ID, null);
        put(F_STATUS, null);
        put(F_TYPE, null);
        put(F_IP, null);
        put(F_DEVICE, null);
        put(F_LEAVE_TIME, null);
        put(F_ADD_TIME, null);
    }

    public CliClienteleLog() {
        super();
    }

    public CliClienteleLog(Map<String, Object> map) {
        super(map);
    }

    public CliClienteleLog(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id <BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id  set
    */
    public CliClienteleLog setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return cli_clientele_id to cliClienteleId 用户id<BR/>
    */
    public String getCliClienteleId() {
        return getTypedValue(F_CLI_CLIENTELE_ID, String.class);
    }
    /**
    * @param cliClienteleId to cli_clientele_id 用户id set
    */
    public CliClienteleLog setCliClienteleId(String cliClienteleId) {
        set(F_CLI_CLIENTELE_ID, cliClienteleId);
        return this;
    }
    /**
    * @return session_id to sessionId 登录成功创建的session<BR/>
    */
    public String getSessionId() {
        return getTypedValue(F_SESSION_ID, String.class);
    }
    /**
    * @param sessionId to session_id 登录成功创建的session set
    */
    public CliClienteleLog setSessionId(String sessionId) {
        set(F_SESSION_ID, sessionId);
        return this;
    }
    /**
    * @return status to status 登录状态<BR/>
    */
    public Integer getStatus() {
        return getTypedValue(F_STATUS, Integer.class);
    }
    /**
    * @param status to status 登录状态 set
    */
    public CliClienteleLog setStatus(Integer status) {
        set(F_STATUS, status);
        return this;
    }
    /**
    * @return type to type 0临时访问1登录访问<BR/>
    */
    public Integer getType() {
        return getTypedValue(F_TYPE, Integer.class);
    }
    /**
    * @param type to type 0临时访问1登录访问 set
    */
    public CliClienteleLog setType(Integer type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return ip to ip ip<BR/>
    */
    public String getIp() {
        return getTypedValue(F_IP, String.class);
    }
    /**
    * @param ip to ip ip set
    */
    public CliClienteleLog setIp(String ip) {
        set(F_IP, ip);
        return this;
    }
    /**
    * @return device to device 登录设备<BR/>
    */
    public String getDevice() {
        return getTypedValue(F_DEVICE, String.class);
    }
    /**
    * @param device to device 登录设备 set
    */
    public CliClienteleLog setDevice(String device) {
        set(F_DEVICE, device);
        return this;
    }
    /**
    * @return leave_time to leaveTime 离线时间<BR/>
    */
    public Long getLeaveTime() {
        return getTypedValue(F_LEAVE_TIME, Long.class);
    }
    /**
    * @param leaveTime to leave_time 离线时间 set
    */
    public CliClienteleLog setLeaveTime(Long leaveTime) {
        set(F_LEAVE_TIME, leaveTime);
        return this;
    }
    /**
    * @return add_time to addTime 登录时间<BR/>
    */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
    * @param addTime to add_time 登录时间 set
    */
    public CliClienteleLog setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliClienteleLog setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliClienteleLog me(){
        return new CliClienteleLog();
    }

    private static class Mapper implements RowMapper<CliClienteleLog> {

        private Supplier<CliClienteleLog> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliClienteleLog mapRow(ResultSet rs, int rownum) throws SQLException {
            CliClienteleLog bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCliClienteleId(Utils.toCast(rs.getObject(F_CLI_CLIENTELE_ID), String.class));
            bean.setSessionId(Utils.toCast(rs.getObject(F_SESSION_ID), String.class));
            bean.setStatus(Utils.toCast(rs.getObject(F_STATUS), Integer.class));
            bean.setType(Utils.toCast(rs.getObject(F_TYPE), Integer.class));
            bean.setIp(Utils.toCast(rs.getObject(F_IP), String.class));
            bean.setDevice(Utils.toCast(rs.getObject(F_DEVICE), String.class));
            bean.setLeaveTime(Utils.toCast(rs.getObject(F_LEAVE_TIME), Long.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliClienteleLog> newMapper(){
        return newMapper(CliClienteleLog::new);
    }

    public RowMapper<CliClienteleLog> newMapper(Supplier<CliClienteleLog> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliClienteleLog {
        @Override
        public abstract RowMapper<CliClienteleLog> newMapper();
    }
}
