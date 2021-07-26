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
* Date:        09:12 2020/03/20
* Company:     美赞拓
* Version:     1.0
* Description: SysMessage实体
*/
@SuppressWarnings("all")
public class SysMessage extends BaseBean<SysMessage> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_message";

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
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 内容
    */
    public static final String F_CONTENT = "content";
    /**
    * 是否置顶
    */
    public static final String F_IS_TO_TOP = "is_to_top";
    /**
    * 是否关闭
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 同级排序
    */
    public static final String F_SORT = "sort";
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

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_TITLE, null);
        put(F_CONTENT, null);
        put(F_IS_TO_TOP, null);
        put(F_IS_ENABLE, null);
        put(F_SORT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
    }

    public SysMessage() {
        super();
    }

    public SysMessage(Map<String, Object> map) {
        super(map);
    }

    public SysMessage(String id) {
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
    public SysMessage setId(String id) {
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
    public SysMessage setCompanyId(String companyId) {
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
    public SysMessage setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
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
    public SysMessage setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return content to content 内容<BR/>
    */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
    * @param content to content 内容 set
    */
    public SysMessage setContent(String content) {
        set(F_CONTENT, content);
        return this;
    }
    /**
    * @return is_to_top to isToTop 是否置顶<BR/>
    */
    public Integer getIsToTop() {
        return getTypedValue(F_IS_TO_TOP, Integer.class);
    }
    /**
    * @param isToTop to is_to_top 是否置顶 set
    */
    public SysMessage setIsToTop(Integer isToTop) {
        set(F_IS_TO_TOP, isToTop);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否关闭<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否关闭 set
    */
    public SysMessage setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
    * @return sort to sort 同级排序<BR/>
    */
    public Integer getSort() {
        return getTypedValue(F_SORT, Integer.class);
    }
    /**
    * @param sort to sort 同级排序 set
    */
    public SysMessage setSort(Integer sort) {
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
    public SysMessage setRemark(String remark) {
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
    public SysMessage setAddTime(Long addTime) {
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
    public SysMessage setAddUserId(String addUserId) {
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
    public SysMessage setAddUserName(String addUserName) {
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
    public SysMessage setOperationTime(Long operationTime) {
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
    public SysMessage setOperationUserId(String operationUserId) {
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
    public SysMessage setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysMessage setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysMessage me(){
        return new SysMessage();
    }

    private static class Mapper implements RowMapper<SysMessage> {

        private Supplier<SysMessage> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysMessage mapRow(ResultSet rs, int rownum) throws SQLException {
            SysMessage bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.setTitle(Utils.toCast(rs.getObject(F_TITLE), String.class));
            bean.setContent(Utils.toCast(rs.getObject(F_CONTENT), String.class));
            bean.setIsToTop(Utils.toCast(rs.getObject(F_IS_TO_TOP), Integer.class));
            bean.setIsEnable(Utils.toCast(rs.getObject(F_IS_ENABLE), Integer.class));
            bean.setSort(Utils.toCast(rs.getObject(F_SORT), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysMessage> newMapper(){
        return newMapper(SysMessage::new);
    }

    public RowMapper<SysMessage> newMapper(Supplier<SysMessage> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysMessage {
        @Override
        public abstract RowMapper<SysMessage> newMapper();
    }
}
