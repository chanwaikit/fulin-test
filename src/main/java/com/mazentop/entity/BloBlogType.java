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
 * Date:        14:50 2020/03/11
 * Company:     美赞拓
 * Version:     1.0
 * Description: BloBlogType实体
 */
@SuppressWarnings("all")
public class BloBlogType extends BaseBean<BloBlogType> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "blo_blog_type";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 博客分类名称
     */
    public static final String F_BLOG_TYPE_NAME = "blog_type_name";
    /**
     * 文章总数量
     */
    public static final String F_CONTENT_NUMBER = "content_number";
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
        put(F_BLOG_TYPE_NAME, null);
        put(F_CONTENT_NUMBER, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
    }

    public BloBlogType() {
        super();
    }

    public BloBlogType(Map<String, Object> map) {
        super(map);
    }

    public BloBlogType(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 主键 set
     */
    public BloBlogType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return blog_type_name to blogTypeName 博客分类名称<BR/>
     */
    public String getBlogTypeName() {
        return getTypedValue(F_BLOG_TYPE_NAME, String.class);
    }
    /**
     * @param blogTypeName to blog_type_name 博客分类名称 set
     */
    public BloBlogType setBlogTypeName(String blogTypeName) {
        set(F_BLOG_TYPE_NAME, blogTypeName);
        return this;
    }
    /**
     * @return content_number to contentNumber 文章总数量<BR/>
     */
    public Integer getContentNumber() {
        return getTypedValue(F_CONTENT_NUMBER, Integer.class);
    }
    /**
     * @param contentNumber to content_number 文章总数量 set
     */
    public BloBlogType setContentNumber(Integer contentNumber) {
        set(F_CONTENT_NUMBER, contentNumber);
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
    public BloBlogType setRemark(String remark) {
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
    public BloBlogType setAddTime(Long addTime) {
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
    public BloBlogType setAddUserId(String addUserId) {
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
    public BloBlogType setAddUserName(String addUserName) {
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
    public BloBlogType setOperationTime(Long operationTime) {
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
    public BloBlogType setOperationUserId(String operationUserId) {
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
    public BloBlogType setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
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
    public BloBlogType setCompanyId(String companyId) {
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
    public BloBlogType setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public BloBlogType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static BloBlogType me(){
        return new BloBlogType();
    }

    private static class Mapper implements RowMapper<BloBlogType> {

        private Supplier<BloBlogType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public BloBlogType mapRow(ResultSet rs, int rownum) throws SQLException {
            BloBlogType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setBlogTypeName(Utils.toCast(rs.getObject(F_BLOG_TYPE_NAME), String.class));
            bean.setContentNumber(Utils.toCast(rs.getObject(F_CONTENT_NUMBER), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<BloBlogType> newMapper(){
        return newMapper(BloBlogType::new);
    }

    public RowMapper<BloBlogType> newMapper(Supplier<BloBlogType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends BloBlogType {
        @Override
        public abstract RowMapper<BloBlogType> newMapper();
    }
}
