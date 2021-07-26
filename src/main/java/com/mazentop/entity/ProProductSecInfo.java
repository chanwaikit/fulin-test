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
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        10:37 2020/04/20
* Company:     美赞拓
* Version:     1.0
* Description: ProProductSecInfo实体
*/
@SuppressWarnings("all")
public class ProProductSecInfo extends BaseBean<ProProductSecInfo> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "pro_product_sec_info";

    /**
     *
     */
    public static final String F_ID = "id";
    /**
     * 商品主体编号
     */
    public static final String F_FK_PRODUCT_MASTER_ID = "fk_product_master_id";
    /**
     * 库存编号
     */
    public static final String F_FK_STOCK_ID = "fk_stock_id";
    /**
     * 规格父编号
     */
    public static final String F_FK_PARENT_SPEC_ID = "fk_parent_spec_id";
    /**
     * 规格编号
     */
    public static final String F_FK_SPEC_ID = "fk_spec_id";
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
        put(F_FK_PRODUCT_MASTER_ID, null);
        put(F_FK_STOCK_ID, null);
        put(F_FK_PARENT_SPEC_ID, null);
        put(F_FK_SPEC_ID, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public ProProductSecInfo() {
        super();
    }

    public ProProductSecInfo(Map<String, Object> map) {
        super(map);
    }

    public ProProductSecInfo(String id) {
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
    public ProProductSecInfo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_product_master_id to fkProductMasterId 商品主体编号<BR/>
     */
    public String getFkProductMasterId() {
        return getTypedValue(F_FK_PRODUCT_MASTER_ID, String.class);
    }
    /**
     * @param fkProductMasterId to fk_product_master_id 商品主体编号 set
     */
    public ProProductSecInfo setFkProductMasterId(String fkProductMasterId) {
        set(F_FK_PRODUCT_MASTER_ID, fkProductMasterId);
        return this;
    }
    /**
     * @return fk_stock_id to fkStockId 库存编号<BR/>
     */
    public String getFkStockId() {
        return getTypedValue(F_FK_STOCK_ID, String.class);
    }
    /**
     * @param fkStockId to fk_stock_id 库存编号 set
     */
    public ProProductSecInfo setFkStockId(String fkStockId) {
        set(F_FK_STOCK_ID, fkStockId);
        return this;
    }
    /**
     * @return fk_parent_spec_id to fkParentSpecId 规格父编号<BR/>
     */
    public String getFkParentSpecId() {
        return getTypedValue(F_FK_PARENT_SPEC_ID, String.class);
    }
    /**
     * @param fkParentSpecId to fk_parent_spec_id 规格父编号 set
     */
    public ProProductSecInfo setFkParentSpecId(String fkParentSpecId) {
        set(F_FK_PARENT_SPEC_ID, fkParentSpecId);
        return this;
    }
    /**
     * @return fk_spec_id to fkSpecId 规格编号<BR/>
     */
    public String getFkSpecId() {
        return getTypedValue(F_FK_SPEC_ID, String.class);
    }
    /**
     * @param fkSpecId to fk_spec_id 规格编号 set
     */
    public ProProductSecInfo setFkSpecId(String fkSpecId) {
        set(F_FK_SPEC_ID, fkSpecId);
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
    public ProProductSecInfo setAddTime(Long addTime) {
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
    public ProProductSecInfo setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductSecInfo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductSecInfo me(){
        return new ProProductSecInfo();
    }

    private static class Mapper implements RowMapper<ProProductSecInfo> {

        private Supplier<ProProductSecInfo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductSecInfo mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductSecInfo bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkProductMasterId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_MASTER_ID) ? rs.getObject(F_FK_PRODUCT_MASTER_ID) : null, String.class));
            bean.setFkStockId(Utils.toCast(columnsName.contains(F_FK_STOCK_ID) ? rs.getObject(F_FK_STOCK_ID) : null, String.class));
            bean.setFkParentSpecId(Utils.toCast(columnsName.contains(F_FK_PARENT_SPEC_ID) ? rs.getObject(F_FK_PARENT_SPEC_ID) : null, String.class));
            bean.setFkSpecId(Utils.toCast(columnsName.contains(F_FK_SPEC_ID) ? rs.getObject(F_FK_SPEC_ID) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductSecInfo> newMapper(){
        return newMapper(ProProductSecInfo::new);
    }

    public RowMapper<ProProductSecInfo> newMapper(Supplier<ProProductSecInfo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductSecInfo {
        @Override
        public abstract RowMapper<ProProductSecInfo> newMapper();
    }
}
