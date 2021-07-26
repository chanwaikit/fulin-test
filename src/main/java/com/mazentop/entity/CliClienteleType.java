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
* Date:        14:40 2020/03/11
* Company:     美赞拓
* Version:     1.0
* Description: CliClienteleType实体
*/
@SuppressWarnings("all")
public class CliClienteleType extends BaseBean<CliClienteleType> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "cli_clientele_type";

    /**
     * 顾客类型表主键
     */
    public static final String F_ID = "id";
    /**
     * 顾客类型名称
     */
    public static final String F_CLIENTELE_TYPE_NAME = "clientele_type_name";
    /**
     * 备注
     */
    public static final String F_CLIENTELE_TYPE_REMARK = "clientele_type_remark";
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
        put(F_CLIENTELE_TYPE_NAME, null);
        put(F_CLIENTELE_TYPE_REMARK, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
    }

    public CliClienteleType() {
        super();
    }

    public CliClienteleType(Map<String, Object> map) {
        super(map);
    }

    public CliClienteleType(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 顾客类型表主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 顾客类型表主键 set
     */
    public CliClienteleType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return clientele_type_name to clienteleTypeName 顾客类型名称<BR/>
     */
    public String getClienteleTypeName() {
        return getTypedValue(F_CLIENTELE_TYPE_NAME, String.class);
    }
    /**
     * @param clienteleTypeName to clientele_type_name 顾客类型名称 set
     */
    public CliClienteleType setClienteleTypeName(String clienteleTypeName) {
        set(F_CLIENTELE_TYPE_NAME, clienteleTypeName);
        return this;
    }
    /**
     * @return clientele_type_remark to clienteleTypeRemark 备注<BR/>
     */
    public String getClienteleTypeRemark() {
        return getTypedValue(F_CLIENTELE_TYPE_REMARK, String.class);
    }
    /**
     * @param clienteleTypeRemark to clientele_type_remark 备注 set
     */
    public CliClienteleType setClienteleTypeRemark(String clienteleTypeRemark) {
        set(F_CLIENTELE_TYPE_REMARK, clienteleTypeRemark);
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
    public CliClienteleType setCompanyId(String companyId) {
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
    public CliClienteleType setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliClienteleType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliClienteleType me(){
        return new CliClienteleType();
    }

    private static class Mapper implements RowMapper<CliClienteleType> {

        private Supplier<CliClienteleType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliClienteleType mapRow(ResultSet rs, int rownum) throws SQLException {
            CliClienteleType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setClienteleTypeName(Utils.toCast(rs.getObject(F_CLIENTELE_TYPE_NAME), String.class));
            bean.setClienteleTypeRemark(Utils.toCast(rs.getObject(F_CLIENTELE_TYPE_REMARK), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliClienteleType> newMapper(){
        return newMapper(CliClienteleType::new);
    }

    public RowMapper<CliClienteleType> newMapper(Supplier<CliClienteleType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliClienteleType {
        @Override
        public abstract RowMapper<CliClienteleType> newMapper();
    }
}
