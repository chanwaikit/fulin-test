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
* Date:        11:05 2020/03/23
* Company:     美赞拓
* Version:     1.0
* Description: OrdFreeScheme实体
*/
@SuppressWarnings("all")
public class OrdFreeScheme extends BaseBean<OrdFreeScheme> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_free_scheme";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 公司编号
     */
    public static final String F_COMPANY_ID = "company_id";
    /**
     * 公司名称
     */
    public static final String F_COMPANY_NAME = "company_name";
    /**
     * 方案外部名称
     */
    public static final String F_SCHEME_OUTSIDE_NAME = "scheme_outside_name";
    /**
     * 方案外部说明
     */
    public static final String F_SCHEME_OUTSIDE_CONTENT = "scheme_outside_content";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
     * 添加人名称
     */
    public static final String F_ADD_USER_NAME = "add_user_name";
    /**
     * 操作时间
     */
    public static final String F_OPERATION_TIME = "operation_time";
    /**
     * 操作人编号
     */
    public static final String F_OPERATION_USER_ID = "operation_user_id";
    /**
     * 操作人名称
     */
    public static final String F_OPERATION_USER_NAME = "operation_user_name";
    /**
     * 是否默认方案
     */
    public static final String F_IS_DEFAULT = "is_default";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_SCHEME_OUTSIDE_NAME, null);
        put(F_SCHEME_OUTSIDE_CONTENT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_IS_DEFAULT, null);
    }

    public OrdFreeScheme() {
        super();
    }

    public OrdFreeScheme(Map<String, Object> map) {
        super(map);
    }

    public OrdFreeScheme(String id) {
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
    public OrdFreeScheme setId(String id) {
        set(F_ID, id);
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
    public OrdFreeScheme setCompanyId(String companyId) {
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
    public OrdFreeScheme setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
     * @return scheme_outside_name to schemeOutsideName 方案外部名称<BR/>
     */
    public String getSchemeOutsideName() {
        return getTypedValue(F_SCHEME_OUTSIDE_NAME, String.class);
    }
    /**
     * @param schemeOutsideName to scheme_outside_name 方案外部名称 set
     */
    public OrdFreeScheme setSchemeOutsideName(String schemeOutsideName) {
        set(F_SCHEME_OUTSIDE_NAME, schemeOutsideName);
        return this;
    }
    /**
     * @return scheme_outside_content to schemeOutsideContent 方案外部说明<BR/>
     */
    public String getSchemeOutsideContent() {
        return getTypedValue(F_SCHEME_OUTSIDE_CONTENT, String.class);
    }
    /**
     * @param schemeOutsideContent to scheme_outside_content 方案外部说明 set
     */
    public OrdFreeScheme setSchemeOutsideContent(String schemeOutsideContent) {
        set(F_SCHEME_OUTSIDE_CONTENT, schemeOutsideContent);
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
    public OrdFreeScheme setRemark(String remark) {
        set(F_REMARK, remark);
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
    public OrdFreeScheme setAddTime(Long addTime) {
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
    public OrdFreeScheme setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
     * @return add_user_name to addUserName 添加人名称<BR/>
     */
    public String getAddUserName() {
        return getTypedValue(F_ADD_USER_NAME, String.class);
    }
    /**
     * @param addUserName to add_user_name 添加人名称 set
     */
    public OrdFreeScheme setAddUserName(String addUserName) {
        set(F_ADD_USER_NAME, addUserName);
        return this;
    }
    /**
     * @return operation_time to operationTime 操作时间<BR/>
     */
    public Long getOperationTime() {
        return getTypedValue(F_OPERATION_TIME, Long.class);
    }
    /**
     * @param operationTime to operation_time 操作时间 set
     */
    public OrdFreeScheme setOperationTime(Long operationTime) {
        set(F_OPERATION_TIME, operationTime);
        return this;
    }
    /**
     * @return operation_user_id to operationUserId 操作人编号<BR/>
     */
    public String getOperationUserId() {
        return getTypedValue(F_OPERATION_USER_ID, String.class);
    }
    /**
     * @param operationUserId to operation_user_id 操作人编号 set
     */
    public OrdFreeScheme setOperationUserId(String operationUserId) {
        set(F_OPERATION_USER_ID, operationUserId);
        return this;
    }
    /**
     * @return operation_user_name to operationUserName 操作人名称<BR/>
     */
    public String getOperationUserName() {
        return getTypedValue(F_OPERATION_USER_NAME, String.class);
    }
    /**
     * @param operationUserName to operation_user_name 操作人名称 set
     */
    public OrdFreeScheme setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
     * @return is_default to isDefault 是否默认方案<BR/>
     */
    public Integer getIsDefault() {
        return getTypedValue(F_IS_DEFAULT, Integer.class);
    }
    /**
     * @param isDefault to is_default 是否默认方案 set
     */
    public OrdFreeScheme setIsDefault(Integer isDefault) {
        set(F_IS_DEFAULT, isDefault);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdFreeScheme setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdFreeScheme me(){
        return new OrdFreeScheme();
    }

    private static class Mapper implements RowMapper<OrdFreeScheme> {

        private Supplier<OrdFreeScheme> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdFreeScheme mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdFreeScheme bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.setSchemeOutsideName(Utils.toCast(rs.getObject(F_SCHEME_OUTSIDE_NAME), String.class));
            bean.setSchemeOutsideContent(Utils.toCast(rs.getObject(F_SCHEME_OUTSIDE_CONTENT), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.setIsDefault(Utils.toCast(rs.getObject(F_IS_DEFAULT), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdFreeScheme> newMapper(){
        return newMapper(OrdFreeScheme::new);
    }

    public RowMapper<OrdFreeScheme> newMapper(Supplier<OrdFreeScheme> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdFreeScheme {
        @Override
        public abstract RowMapper<OrdFreeScheme> newMapper();
    }
}
