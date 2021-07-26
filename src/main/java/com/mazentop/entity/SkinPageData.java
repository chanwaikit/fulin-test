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
* Author:      dengy
* Mail:        dengy@mazentop.com
* Date:        14:11 2020/06/08
* Company:     美赞拓
* Version:     1.0
* Description: SkinPageData实体
*/
@SuppressWarnings("all")
public class SkinPageData extends BaseBean<SkinPageData> {
    /**
     * 表名
     */
    public static final String TABLE_NAME = "skin_page_data";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 数据类型 collections 产品专题
     */
    public static final String F_DATA_TYPE = "data_type";
    /**
     * 数据来源 手动 manual  自动 automatic
     */
    public static final String F_DATA_SOURCE = "data_source";
    /**
     * 排序规则
     */
    public static final String F_SORTING_RULES = "sorting_rules";
    /**
     * 检索条件 数据来源为 automatic 自动 该值必填
     */
    public static final String F_DATA_OPTIONS = "data_options";
    /**
     * 最大显示条目 0 表示不限制
     */
    public static final String F_MAX_LIMIT = "max_limit";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_DATA_TYPE, null);
        put(F_DATA_SOURCE, null);
        put(F_SORTING_RULES, null);
        put(F_DATA_OPTIONS, null);
        put(F_MAX_LIMIT, null);
    }

    public SkinPageData() {
        super();
    }

    public SkinPageData(Map<String, Object> map) {
        super(map);
    }

    public SkinPageData(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 主键 <BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 主键  set
     */
    public SkinPageData setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return data_type to dataType 数据类型 collections 产品专题<BR/>
     */
    public String getDataType() {
        return getTypedValue(F_DATA_TYPE, String.class);
    }
    /**
     * @param dataType to data_type 数据类型 collections 产品专题 set
     */
    public SkinPageData setDataType(String dataType) {
        set(F_DATA_TYPE, dataType);
        return this;
    }
    /**
     * @return data_source to dataSource 数据来源 手动 manual  自动 automatic<BR/>
     */
    public String getDataSource() {
        return getTypedValue(F_DATA_SOURCE, String.class);
    }
    /**
     * @param dataSource to data_source 数据来源 手动 manual  自动 automatic set
     */
    public SkinPageData setDataSource(String dataSource) {
        set(F_DATA_SOURCE, dataSource);
        return this;
    }
    /**
     * @return sorting_rules to sortingRules 排序规则<BR/>
     */
    public String getSortingRules() {
        return getTypedValue(F_SORTING_RULES, String.class);
    }
    /**
     * @param sortingRules to sorting_rules 排序规则 set
     */
    public SkinPageData setSortingRules(String sortingRules) {
        set(F_SORTING_RULES, sortingRules);
        return this;
    }
    /**
     * @return data_options to dataOptions 检索条件 数据来源为 automatic 自动 该值必填<BR/>
     */
    public String getDataOptions() {
        return getTypedValue(F_DATA_OPTIONS, String.class);
    }
    /**
     * @param dataOptions to data_options 检索条件 数据来源为 automatic 自动 该值必填 set
     */
    public SkinPageData setDataOptions(String dataOptions) {
        set(F_DATA_OPTIONS, dataOptions);
        return this;
    }
    /**
     * @return max_limit to maxLimit 最大显示条目 0 表示不限制<BR/>
     */
    public Integer getMaxLimit() {
        return getTypedValue(F_MAX_LIMIT, Integer.class);
    }
    /**
     * @param maxLimit to max_limit 最大显示条目 0 表示不限制 set
     */
    public SkinPageData setMaxLimit(Integer maxLimit) {
        set(F_MAX_LIMIT, maxLimit);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinPageData setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinPageData me(){
        return new SkinPageData();
    }

    private static class Mapper implements RowMapper<SkinPageData> {

        private Supplier<SkinPageData> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinPageData mapRow(ResultSet rs, int rownum) throws SQLException {
            SkinPageData bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setDataType(Utils.toCast(rs.getObject(F_DATA_TYPE), String.class));
            bean.setDataSource(Utils.toCast(rs.getObject(F_DATA_SOURCE), String.class));
            bean.setSortingRules(Utils.toCast(rs.getObject(F_SORTING_RULES), String.class));
            bean.setDataOptions(Utils.toCast(rs.getObject(F_DATA_OPTIONS), String.class));
            bean.setMaxLimit(Utils.toCast(rs.getObject(F_MAX_LIMIT), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinPageData> newMapper(){
        return newMapper(SkinPageData::new);
    }

    public RowMapper<SkinPageData> newMapper(Supplier<SkinPageData> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinPageData {
        @Override
        public abstract RowMapper<SkinPageData> newMapper();
    }
}
