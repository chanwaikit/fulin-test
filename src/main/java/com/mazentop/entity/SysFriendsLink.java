package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
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
* Description: SysFriendsLink实体
*/
@SuppressWarnings("all")
public class SysFriendsLink extends BaseBean<SysFriendsLink> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_friends_link";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 友情链接类型编号
     */
    public static final String F_FK_FRIENDS_LINK_TYPE_ID = "fk_friends_link_type_id";
    /**
     * 友情链接类型名称
     */
    public static final String F_FRIENDS_LINK_TYPE_NAME = "friends_link_type_name";
    /**
     * 友情链接标题
     */
    public static final String F_FRIENDS_LINK_TITLE = "friends_link_title";
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
     * 是否停用
     */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_FRIENDS_LINK_TYPE_ID, null);
        put(F_FRIENDS_LINK_TYPE_NAME, null);
        put(F_FRIENDS_LINK_TITLE, null);
        put(F_KEYWORDS, null);
        put(F_KEY_IMAGE_URL, null);
        put(F_TARGET_URL, null);
        put(F_SORT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_IS_ENABLE, null);
    }

    public SysFriendsLink() {
        super();
    }

    public SysFriendsLink(Map<String, Object> map) {
        super(map);
    }

    public SysFriendsLink(String id) {
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
    public SysFriendsLink setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_friends_link_type_id to fkFriendsLinkTypeId 友情链接类型编号<BR/>
     */
    public String getFkFriendsLinkTypeId() {
        return getTypedValue(F_FK_FRIENDS_LINK_TYPE_ID, String.class);
    }
    /**
     * @param fkFriendsLinkTypeId to fk_friends_link_type_id 友情链接类型编号 set
     */
    public SysFriendsLink setFkFriendsLinkTypeId(String fkFriendsLinkTypeId) {
        set(F_FK_FRIENDS_LINK_TYPE_ID, fkFriendsLinkTypeId);
        return this;
    }
    /**
     * @return friends_link_type_name to friendsLinkTypeName 友情链接类型名称<BR/>
     */
    public String getFriendsLinkTypeName() {
        return getTypedValue(F_FRIENDS_LINK_TYPE_NAME, String.class);
    }
    /**
     * @param friendsLinkTypeName to friends_link_type_name 友情链接类型名称 set
     */
    public SysFriendsLink setFriendsLinkTypeName(String friendsLinkTypeName) {
        set(F_FRIENDS_LINK_TYPE_NAME, friendsLinkTypeName);
        return this;
    }
    /**
     * @return friends_link_title to friendsLinkTitle 友情链接标题<BR/>
     */
    public String getFriendsLinkTitle() {
        return getTypedValue(F_FRIENDS_LINK_TITLE, String.class);
    }
    /**
     * @param friendsLinkTitle to friends_link_title 友情链接标题 set
     */
    public SysFriendsLink setFriendsLinkTitle(String friendsLinkTitle) {
        set(F_FRIENDS_LINK_TITLE, friendsLinkTitle);
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
    public SysFriendsLink setKeywords(String keywords) {
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
    public SysFriendsLink setKeyImageUrl(String keyImageUrl) {
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
    public SysFriendsLink setTargetUrl(String targetUrl) {
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
    public SysFriendsLink setSort(Integer sort) {
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
    public SysFriendsLink setRemark(String remark) {
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
    public SysFriendsLink setAddTime(Long addTime) {
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
    public SysFriendsLink setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
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
    public SysFriendsLink setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysFriendsLink setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysFriendsLink me(){
        return new SysFriendsLink();
    }

    private static class Mapper implements RowMapper<SysFriendsLink> {

        private Supplier<SysFriendsLink> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysFriendsLink mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysFriendsLink bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkFriendsLinkTypeId(Utils.toCast(columnsName.contains(F_FK_FRIENDS_LINK_TYPE_ID) ? rs.getObject(F_FK_FRIENDS_LINK_TYPE_ID) : null, String.class));
            bean.setFriendsLinkTypeName(Utils.toCast(columnsName.contains(F_FRIENDS_LINK_TYPE_NAME) ? rs.getObject(F_FRIENDS_LINK_TYPE_NAME) : null, String.class));
            bean.setFriendsLinkTitle(Utils.toCast(columnsName.contains(F_FRIENDS_LINK_TITLE) ? rs.getObject(F_FRIENDS_LINK_TITLE) : null, String.class));
            bean.setKeywords(Utils.toCast(columnsName.contains(F_KEYWORDS) ? rs.getObject(F_KEYWORDS) : null, String.class));
            bean.setKeyImageUrl(Utils.toCast(columnsName.contains(F_KEY_IMAGE_URL) ? rs.getObject(F_KEY_IMAGE_URL) : null, String.class));
            bean.setTargetUrl(Utils.toCast(columnsName.contains(F_TARGET_URL) ? rs.getObject(F_TARGET_URL) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysFriendsLink> newMapper(){
        return newMapper(SysFriendsLink::new);
    }

    public RowMapper<SysFriendsLink> newMapper(Supplier<SysFriendsLink> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysFriendsLink {
        @Override
        public abstract RowMapper<SysFriendsLink> newMapper();
    }
}
