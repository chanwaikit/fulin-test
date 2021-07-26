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
 * Date:        09:12 2020/03/31
 * Company:     美赞拓
 * Version:     1.0
 * Description: CliCollectorItemType实体
 */
@SuppressWarnings("all")
public class CliCollectorItemType extends BaseBean<CliCollectorItemType> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "cli_collector_item_type";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 收藏类型名称
     */
    public static final String F_COLLECTOR_ITEM_TYPE_NAME = "collector_item_type_name";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues() {
        put(F_ID, null);
        put(F_COLLECTOR_ITEM_TYPE_NAME, null);
        put(F_REMARK, null);
    }

    public CliCollectorItemType() {
        super();
    }

    public CliCollectorItemType(Map<String, Object> map) {
        super(map);
    }

    public CliCollectorItemType(String id) {
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
    public CliCollectorItemType setId(String id) {
        set(F_ID, id);
        return this;
    }

    /**
     * @return collector_item_type_name to collectorItemTypeName 收藏类型名称<BR/>
     */
    public String getCollectorItemTypeName() {
        return getTypedValue(F_COLLECTOR_ITEM_TYPE_NAME, String.class);
    }

    /**
     * @param collectorItemTypeName to collector_item_type_name 收藏类型名称 set
     */
    public CliCollectorItemType setCollectorItemTypeName(String collectorItemTypeName) {
        set(F_COLLECTOR_ITEM_TYPE_NAME, collectorItemTypeName);
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
    public CliCollectorItemType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliCollectorItemType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliCollectorItemType me() {
        return new CliCollectorItemType();
    }

    private static class Mapper implements RowMapper<CliCollectorItemType> {

        private Supplier<CliCollectorItemType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliCollectorItemType mapRow(ResultSet rs, int rownum) throws SQLException {
            CliCollectorItemType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCollectorItemTypeName(Utils.toCast(rs.getObject(F_COLLECTOR_ITEM_TYPE_NAME), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliCollectorItemType> newMapper() {
        return newMapper(CliCollectorItemType::new);
    }

    public RowMapper<CliCollectorItemType> newMapper(Supplier<CliCollectorItemType> supplier) {
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliCollectorItemType {
        @Override
        public abstract RowMapper<CliCollectorItemType> newMapper();
    }
}
