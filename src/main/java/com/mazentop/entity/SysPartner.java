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
* Date:        18:12 2020/04/01
* Company:     美赞拓
* Version:     1.0
* Description: SysPartner实体
*/
@SuppressWarnings("all")
public class SysPartner extends BaseBean<SysPartner> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_partner";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 合作伙伴标题
    */
    public static final String F_PARTNER_TITLE = "partner_title";
    /**
    * 关键字
    */
    public static final String F_KEYWORDS = "keywords";
    /**
    * 关键图片
    */
    public static final String F_KEY_IMAGE_URL = "key_image_url";
    /**
    * 目标跳转地址
    */
    public static final String F_TARGET_URL = "target_url";
    /**
    * 排序
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
    /**
    * 是否停用
    */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PARTNER_TITLE, null);
        put(F_KEYWORDS, null);
        put(F_KEY_IMAGE_URL, null);
        put(F_TARGET_URL, null);
        put(F_SORT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_IS_ENABLE, null);
    }

    public SysPartner() {
        super();
    }

    public SysPartner(Map<String, Object> map) {
        super(map);
    }

    public SysPartner(String id) {
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
    public SysPartner setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return partner_title to partnerTitle 合作伙伴标题<BR/>
    */
    public String getPartnerTitle() {
        return getTypedValue(F_PARTNER_TITLE, String.class);
    }
    /**
    * @param partnerTitle to partner_title 合作伙伴标题 set
    */
    public SysPartner setPartnerTitle(String partnerTitle) {
        set(F_PARTNER_TITLE, partnerTitle);
        return this;
    }
    /**
    * @return keywords to keywords 关键字<BR/>
    */
    public String getKeywords() {
        return getTypedValue(F_KEYWORDS, String.class);
    }
    /**
    * @param keywords to keywords 关键字 set
    */
    public SysPartner setKeywords(String keywords) {
        set(F_KEYWORDS, keywords);
        return this;
    }
    /**
    * @return key_image_url to keyImageUrl 关键图片<BR/>
    */
    public String getKeyImageUrl() {
        return getTypedValue(F_KEY_IMAGE_URL, String.class);
    }
    /**
    * @param keyImageUrl to key_image_url 关键图片 set
    */
    public SysPartner setKeyImageUrl(String keyImageUrl) {
        set(F_KEY_IMAGE_URL, keyImageUrl);
        return this;
    }
    /**
    * @return target_url to targetUrl 目标跳转地址<BR/>
    */
    public String getTargetUrl() {
        return getTypedValue(F_TARGET_URL, String.class);
    }
    /**
    * @param targetUrl to target_url 目标跳转地址 set
    */
    public SysPartner setTargetUrl(String targetUrl) {
        set(F_TARGET_URL, targetUrl);
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
    public SysPartner setSort(Integer sort) {
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
    public SysPartner setRemark(String remark) {
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
    public SysPartner setAddTime(Long addTime) {
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
    public SysPartner setAddUserId(String addUserId) {
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
    public SysPartner setAddUserName(String addUserName) {
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
    public SysPartner setOperationTime(Long operationTime) {
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
    public SysPartner setOperationUserId(String operationUserId) {
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
    public SysPartner setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否停用<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否停用 set
    */
    public SysPartner setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysPartner setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysPartner me(){
        return new SysPartner();
    }

    private static class Mapper implements RowMapper<SysPartner> {

        private Supplier<SysPartner> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysPartner mapRow(ResultSet rs, int rownum) throws SQLException {
            SysPartner bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setPartnerTitle(Utils.toCast(rs.getObject(F_PARTNER_TITLE), String.class));
            bean.setKeywords(Utils.toCast(rs.getObject(F_KEYWORDS), String.class));
            bean.setKeyImageUrl(Utils.toCast(rs.getObject(F_KEY_IMAGE_URL), String.class));
            bean.setTargetUrl(Utils.toCast(rs.getObject(F_TARGET_URL), String.class));
            bean.setSort(Utils.toCast(rs.getObject(F_SORT), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.setIsEnable(Utils.toCast(rs.getObject(F_IS_ENABLE), Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysPartner> newMapper(){
        return newMapper(SysPartner::new);
    }

    public RowMapper<SysPartner> newMapper(Supplier<SysPartner> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysPartner {
        @Override
        public abstract RowMapper<SysPartner> newMapper();
    }
}
