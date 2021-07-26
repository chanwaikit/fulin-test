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
* Date:        09:33 2020/03/19
* Company:     美赞拓
* Version:     1.0
* Description: SysHelpCenterType实体
*/
@SuppressWarnings("all")
public class SysHelpCenterType extends BaseBean<SysHelpCenterType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_help_center_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 帮助中心分类名称
    */
    public static final String F_HELP_CENTER_TYPE_NAME = "help_center_type_name";
    /**
    * 排序
    */
    public static final String F_SORT = "sort";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";
    /**
    * 公司编号
    */
    public static final String F_COMPANY_ID = "company_id";
    /**
    * 公司名称
    */
    public static final String F_COMPANY_NAME = "company_name";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_HELP_CENTER_TYPE_NAME, null);
        put(F_SORT, null);
        put(F_REMARK, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
    }

    public SysHelpCenterType() {
        super();
    }

    public SysHelpCenterType(Map<String, Object> map) {
        super(map);
    }

    public SysHelpCenterType(String id) {
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
    public SysHelpCenterType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return help_center_type_name to helpCenterTypeName 帮助中心分类名称<BR/>
    */
    public String getHelpCenterTypeName() {
        return getTypedValue(F_HELP_CENTER_TYPE_NAME, String.class);
    }
    /**
    * @param helpCenterTypeName to help_center_type_name 帮助中心分类名称 set
    */
    public SysHelpCenterType setHelpCenterTypeName(String helpCenterTypeName) {
        set(F_HELP_CENTER_TYPE_NAME, helpCenterTypeName);
        return this;
    }
    /**
    * @return sort to sort 排序<BR/>
    */
    public Integer getSort() {
        return getTypedValue(F_SORT, Integer.class);
    }
    /**
    * @param sort to sort 排序 set
    */
    public SysHelpCenterType setSort(Integer sort) {
        set(F_SORT, sort);
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
    public SysHelpCenterType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }
    /**
    * @return company_id to companyId 公司编号<BR/>
    */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
    * @param companyId to company_id 公司编号 set
    */
    public SysHelpCenterType setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
    * @return company_name to companyName 公司名称<BR/>
    */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
    * @param companyName to company_name 公司名称 set
    */
    public SysHelpCenterType setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysHelpCenterType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysHelpCenterType me(){
        return new SysHelpCenterType();
    }

    private static class Mapper implements RowMapper<SysHelpCenterType> {

        private Supplier<SysHelpCenterType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysHelpCenterType mapRow(ResultSet rs, int rownum) throws SQLException {
            SysHelpCenterType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setHelpCenterTypeName(Utils.toCast(rs.getObject(F_HELP_CENTER_TYPE_NAME), String.class));
            bean.setSort(Utils.toCast(rs.getObject(F_SORT), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysHelpCenterType> newMapper(){
        return newMapper(SysHelpCenterType::new);
    }

    public RowMapper<SysHelpCenterType> newMapper(Supplier<SysHelpCenterType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysHelpCenterType {
        @Override
        public abstract RowMapper<SysHelpCenterType> newMapper();
    }
}
