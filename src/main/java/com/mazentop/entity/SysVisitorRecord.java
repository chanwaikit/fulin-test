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
* Description: SysVisitorRecord实体
*/
@SuppressWarnings("all")
public class SysVisitorRecord extends BaseBean<SysVisitorRecord> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_visitor_record";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 访客编号
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 访客名称
    */
    public static final String F_CLIENT_NAME = "client_name";
    /**
    * IP
    */
    public static final String F_IP = "ip";
    /**
    * 访问时间
    */
    public static final String F_VISIT_TIME = "visit_time";
    /**
    * 访问URL
    */
    public static final String F_VISIT_URL = "visit_url";
    /**
    * 访问设备
    */
    public static final String F_VISIT_MACHINE = "visit_machine";
    /**
    * 访问Cookies
    */
    public static final String F_VISIT_COOKIES = "visit_cookies";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_IP, null);
        put(F_VISIT_TIME, null);
        put(F_VISIT_URL, null);
        put(F_VISIT_MACHINE, null);
        put(F_VISIT_COOKIES, null);
        put(F_REMARK, null);
    }

    public SysVisitorRecord() {
        super();
    }

    public SysVisitorRecord(Map<String, Object> map) {
        super(map);
    }

    public SysVisitorRecord(String id) {
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
    public SysVisitorRecord setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 访客编号<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 访客编号 set
    */
    public SysVisitorRecord setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return client_name to clientName 访客名称<BR/>
    */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
    * @param clientName to client_name 访客名称 set
    */
    public SysVisitorRecord setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
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
    public SysVisitorRecord setIp(String ip) {
        set(F_IP, ip);
        return this;
    }
    /**
    * @return visit_time to visitTime 访问时间<BR/>
    */
    public Long getVisitTime() {
        return getTypedValue(F_VISIT_TIME, Long.class);
    }
    /**
    * @param visitTime to visit_time 访问时间 set
    */
    public SysVisitorRecord setVisitTime(Long visitTime) {
        set(F_VISIT_TIME, visitTime);
        return this;
    }
    /**
    * @return visit_url to visitUrl 访问URL<BR/>
    */
    public String getVisitUrl() {
        return getTypedValue(F_VISIT_URL, String.class);
    }
    /**
    * @param visitUrl to visit_url 访问URL set
    */
    public SysVisitorRecord setVisitUrl(String visitUrl) {
        set(F_VISIT_URL, visitUrl);
        return this;
    }
    /**
    * @return visit_machine to visitMachine 访问设备<BR/>
    */
    public String getVisitMachine() {
        return getTypedValue(F_VISIT_MACHINE, String.class);
    }
    /**
    * @param visitMachine to visit_machine 访问设备 set
    */
    public SysVisitorRecord setVisitMachine(String visitMachine) {
        set(F_VISIT_MACHINE, visitMachine);
        return this;
    }
    /**
    * @return visit_cookies to visitCookies 访问Cookies<BR/>
    */
    public String getVisitCookies() {
        return getTypedValue(F_VISIT_COOKIES, String.class);
    }
    /**
    * @param visitCookies to visit_cookies 访问Cookies set
    */
    public SysVisitorRecord setVisitCookies(String visitCookies) {
        set(F_VISIT_COOKIES, visitCookies);
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
    public SysVisitorRecord setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysVisitorRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysVisitorRecord me(){
        return new SysVisitorRecord();
    }

    private static class Mapper implements RowMapper<SysVisitorRecord> {

        private Supplier<SysVisitorRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysVisitorRecord mapRow(ResultSet rs, int rownum) throws SQLException {
            SysVisitorRecord bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setClientName(Utils.toCast(rs.getObject(F_CLIENT_NAME), String.class));
            bean.setIp(Utils.toCast(rs.getObject(F_IP), String.class));
            bean.setVisitTime(Utils.toCast(rs.getObject(F_VISIT_TIME), Long.class));
            bean.setVisitUrl(Utils.toCast(rs.getObject(F_VISIT_URL), String.class));
            bean.setVisitMachine(Utils.toCast(rs.getObject(F_VISIT_MACHINE), String.class));
            bean.setVisitCookies(Utils.toCast(rs.getObject(F_VISIT_COOKIES), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysVisitorRecord> newMapper(){
        return newMapper(SysVisitorRecord::new);
    }

    public RowMapper<SysVisitorRecord> newMapper(Supplier<SysVisitorRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysVisitorRecord {
        @Override
        public abstract RowMapper<SysVisitorRecord> newMapper();
    }
}
