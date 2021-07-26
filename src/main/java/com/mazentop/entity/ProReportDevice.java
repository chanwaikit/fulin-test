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
* Date:        10:14 2020/05/12
* Company:     美赞拓
* Version:     1.0
* Description: ProReportDevice实体
*/
@SuppressWarnings("all")
public class ProReportDevice extends BaseBean<ProReportDevice> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_report_device";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 设备
    */
    public static final String F_DEVICE = "device";
    /**
    * 类型1访问,2订单交易
    */
    public static final String F_TYPE = "type";
    /**
    * 来源
    */
    public static final String F_SOURCE = "source";
    /**
    * 访问IP
    */
    public static final String F_IP = "ip";
    /**
    * 访问时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_DEVICE, null);
        put(F_TYPE, null);
        put(F_SOURCE, null);
        put(F_IP, null);
        put(F_ADD_TIME, null);
    }

    public ProReportDevice() {
        super();
    }

    public ProReportDevice(Map<String, Object> map) {
        super(map);
    }

    public ProReportDevice(String id) {
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
    public ProReportDevice setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return device to device 设备<BR/>
    */
    public String getDevice() {
        return getTypedValue(F_DEVICE, String.class);
    }
    /**
    * @param device to device 设备 set
    */
    public ProReportDevice setDevice(String device) {
        set(F_DEVICE, device);
        return this;
    }
    /**
    * @return type to type 类型1访问,2订单交易<BR/>
    */
    public Integer getType() {
        return getTypedValue(F_TYPE, Integer.class);
    }
    /**
    * @param type to type 类型1访问,2订单交易 set
    */
    public ProReportDevice setType(Integer type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return source to source 来源<BR/>
    */
    public String getSource() {
        return getTypedValue(F_SOURCE, String.class);
    }
    /**
    * @param source to source 来源 set
    */
    public ProReportDevice setSource(String source) {
        set(F_SOURCE, source);
        return this;
    }
    /**
    * @return ip to ip 访问IP<BR/>
    */
    public String getIp() {
        return getTypedValue(F_IP, String.class);
    }
    /**
    * @param ip to ip 访问IP set
    */
    public ProReportDevice setIp(String ip) {
        set(F_IP, ip);
        return this;
    }
    /**
    * @return add_time to addTime 访问时间<BR/>
    */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
    * @param addTime to add_time 访问时间 set
    */
    public ProReportDevice setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProReportDevice setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProReportDevice me(){
        return new ProReportDevice();
    }

    private static class Mapper implements RowMapper<ProReportDevice> {

        private Supplier<ProReportDevice> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProReportDevice mapRow(ResultSet rs, int rownum) throws SQLException {
            ProReportDevice bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setDevice(Utils.toCast(rs.getObject(F_DEVICE), String.class));
            bean.setType(Utils.toCast(rs.getObject(F_TYPE), Integer.class));
            bean.setSource(Utils.toCast(rs.getObject(F_SOURCE), String.class));
            bean.setIp(Utils.toCast(rs.getObject(F_IP), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProReportDevice> newMapper(){
        return newMapper(ProReportDevice::new);
    }

    public RowMapper<ProReportDevice> newMapper(Supplier<ProReportDevice> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProReportDevice {
        @Override
        public abstract RowMapper<ProReportDevice> newMapper();
    }
}
