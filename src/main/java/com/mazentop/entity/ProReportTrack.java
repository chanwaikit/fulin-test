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
* Description: ProReportTrack实体
*/
@SuppressWarnings("all")
public class ProReportTrack extends BaseBean<ProReportTrack> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_report_track";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 商品id
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 浏览用户（未登录是空）
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * ip
    */
    public static final String F_IP = "ip";
    /**
    * 1商品浏览量,2着陆页浏览量
    */
    public static final String F_TYPE = "type";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_TITLE, null);
        put(F_IP, null);
        put(F_TYPE, null);
        put(F_ADD_TIME, null);
    }

    public ProReportTrack() {
        super();
    }

    public ProReportTrack(Map<String, Object> map) {
        super(map);
    }

    public ProReportTrack(String id) {
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
    public ProReportTrack setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_product_id to fkProductId 商品id<BR/>
    */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
    * @param fkProductId to fk_product_id 商品id set
    */
    public ProReportTrack setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 浏览用户（未登录是空）<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 浏览用户（未登录是空） set
    */
    public ProReportTrack setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return title to title 标题<BR/>
    */
    public String getTitle() {
        return getTypedValue(F_TITLE, String.class);
    }
    /**
    * @param title to title 标题 set
    */
    public ProReportTrack setTitle(String title) {
        set(F_TITLE, title);
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
    public ProReportTrack setIp(String ip) {
        set(F_IP, ip);
        return this;
    }
    /**
    * @return type to type 1商品浏览量,2着陆页浏览量<BR/>
    */
    public Integer getType() {
        return getTypedValue(F_TYPE, Integer.class);
    }
    /**
    * @param type to type 1商品浏览量,2着陆页浏览量 set
    */
    public ProReportTrack setType(Integer type) {
        set(F_TYPE, type);
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
    public ProReportTrack setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProReportTrack setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProReportTrack me(){
        return new ProReportTrack();
    }

    private static class Mapper implements RowMapper<ProReportTrack> {

        private Supplier<ProReportTrack> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProReportTrack mapRow(ResultSet rs, int rownum) throws SQLException {
            ProReportTrack bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkProductId(Utils.toCast(rs.getObject(F_FK_PRODUCT_ID), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setTitle(Utils.toCast(rs.getObject(F_TITLE), String.class));
            bean.setIp(Utils.toCast(rs.getObject(F_IP), String.class));
            bean.setType(Utils.toCast(rs.getObject(F_TYPE), Integer.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProReportTrack> newMapper(){
        return newMapper(ProReportTrack::new);
    }

    public RowMapper<ProReportTrack> newMapper(Supplier<ProReportTrack> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProReportTrack {
        @Override
        public abstract RowMapper<ProReportTrack> newMapper();
    }
}
