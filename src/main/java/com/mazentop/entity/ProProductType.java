package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import java.util.function.Supplier;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        17:13 2020/11/03
* Version:     1.0
* Description: ProProductType实体
*/
@SuppressWarnings("all")
public class ProProductType extends BaseBean<ProProductType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_product_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 分类名称
    */
    public static final String F_PRODUCT_TYPE_NAME = "product_type_name";
    /**
    * 上级分类编号
    */
    public static final String F_PARENT_PRODUCT_TYPE_ID = "parent_product_type_id";
    /**
    * 上级分类名称
    */
    public static final String F_PARENT_PRODUCT_TYPE_NAME = "parent_product_type_name";
    /**
    * 商品分类预览图
    */
    public static final String F_PRODUCT_TYPE_PRO_IMAGE = "product_type_pro_image";
    /**
    * 缩略图
    */
    public static final String F_THUMBNAIL_URL = "thumbnail_url";
    /**
    * 是否为根分类
    */
    public static final String F_IS_ROOT_TYPE = "is_root_type";
    /**
    * 是否首页显示
    */
    public static final String F_IS_SHOW_INDEX = "is_show_index";
    /**
    * 是否产品菜单显示
    */
    public static final String F_IS_SHOW_PRODUCT_MENU = "is_show_product_menu";
    /**
    * 分类概述
    */
    public static final String F_DESCRIPTION = "description";
    /**
    * 分类说明
    */
    public static final String F_CONTENT = "content";
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
    * 是否启用
    */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PRODUCT_TYPE_NAME, null);
        put(F_PARENT_PRODUCT_TYPE_ID, null);
        put(F_PARENT_PRODUCT_TYPE_NAME, null);
        put(F_PRODUCT_TYPE_PRO_IMAGE, null);
        put(F_THUMBNAIL_URL, null);
        put(F_IS_ROOT_TYPE, null);
        put(F_IS_SHOW_INDEX, null);
        put(F_IS_SHOW_PRODUCT_MENU, null);
        put(F_DESCRIPTION, null);
        put(F_CONTENT, null);
        put(F_SORT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_IS_ENABLE, null);
    }

    public ProProductType() {
        super();
    }

    public ProProductType(Map<String, Object> map) {
        super(map);
    }

    public ProProductType(String id) {
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
    public ProProductType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return product_type_name to productTypeName 分类名称<BR/>
    */
    public String getProductTypeName() {
        return getTypedValue(F_PRODUCT_TYPE_NAME, String.class);
    }
    /**
    * @param productTypeName to product_type_name 分类名称 set
    */
    public ProProductType setProductTypeName(String productTypeName) {
        set(F_PRODUCT_TYPE_NAME, productTypeName);
        return this;
    }
    /**
    * @return parent_product_type_id to parentProductTypeId 上级分类编号<BR/>
    */
    public String getParentProductTypeId() {
        return getTypedValue(F_PARENT_PRODUCT_TYPE_ID, String.class);
    }
    /**
    * @param parentProductTypeId to parent_product_type_id 上级分类编号 set
    */
    public ProProductType setParentProductTypeId(String parentProductTypeId) {
        set(F_PARENT_PRODUCT_TYPE_ID, parentProductTypeId);
        return this;
    }
    /**
    * @return parent_product_type_name to parentProductTypeName 上级分类名称<BR/>
    */
    public String getParentProductTypeName() {
        return getTypedValue(F_PARENT_PRODUCT_TYPE_NAME, String.class);
    }
    /**
    * @param parentProductTypeName to parent_product_type_name 上级分类名称 set
    */
    public ProProductType setParentProductTypeName(String parentProductTypeName) {
        set(F_PARENT_PRODUCT_TYPE_NAME, parentProductTypeName);
        return this;
    }
    /**
    * @return product_type_pro_image to productTypeProImage 商品分类预览图<BR/>
    */
    public String getProductTypeProImage() {
        return getTypedValue(F_PRODUCT_TYPE_PRO_IMAGE, String.class);
    }
    /**
    * @param productTypeProImage to product_type_pro_image 商品分类预览图 set
    */
    public ProProductType setProductTypeProImage(String productTypeProImage) {
        set(F_PRODUCT_TYPE_PRO_IMAGE, productTypeProImage);
        return this;
    }
    /**
    * @return thumbnail_url to thumbnailUrl 缩略图<BR/>
    */
    public String getThumbnailUrl() {
        return getTypedValue(F_THUMBNAIL_URL, String.class);
    }
    /**
    * @param thumbnailUrl to thumbnail_url 缩略图 set
    */
    public ProProductType setThumbnailUrl(String thumbnailUrl) {
        set(F_THUMBNAIL_URL, thumbnailUrl);
        return this;
    }
    /**
    * @return is_root_type to isRootType 是否为根分类<BR/>
    */
    public Integer getIsRootType() {
        return getTypedValue(F_IS_ROOT_TYPE, Integer.class);
    }
    /**
    * @param isRootType to is_root_type 是否为根分类 set
    */
    public ProProductType setIsRootType(Integer isRootType) {
        set(F_IS_ROOT_TYPE, isRootType);
        return this;
    }
    /**
    * @return is_show_index to isShowIndex 是否首页显示<BR/>
    */
    public Integer getIsShowIndex() {
        return getTypedValue(F_IS_SHOW_INDEX, Integer.class);
    }
    /**
    * @param isShowIndex to is_show_index 是否首页显示 set
    */
    public ProProductType setIsShowIndex(Integer isShowIndex) {
        set(F_IS_SHOW_INDEX, isShowIndex);
        return this;
    }
    /**
    * @return is_show_product_menu to isShowProductMenu 是否产品菜单显示<BR/>
    */
    public Integer getIsShowProductMenu() {
        return getTypedValue(F_IS_SHOW_PRODUCT_MENU, Integer.class);
    }
    /**
    * @param isShowProductMenu to is_show_product_menu 是否产品菜单显示 set
    */
    public ProProductType setIsShowProductMenu(Integer isShowProductMenu) {
        set(F_IS_SHOW_PRODUCT_MENU, isShowProductMenu);
        return this;
    }
    /**
    * @return description to description 分类概述<BR/>
    */
    public String getDescription() {
        return getTypedValue(F_DESCRIPTION, String.class);
    }
    /**
    * @param description to description 分类概述 set
    */
    public ProProductType setDescription(String description) {
        set(F_DESCRIPTION, description);
        return this;
    }
    /**
    * @return content to content 分类说明<BR/>
    */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
    * @param content to content 分类说明 set
    */
    public ProProductType setContent(String content) {
        set(F_CONTENT, content);
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
    public ProProductType setSort(Integer sort) {
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
    public ProProductType setRemark(String remark) {
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
    public ProProductType setAddTime(Long addTime) {
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
    public ProProductType setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启用<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启用 set
    */
    public ProProductType setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductType me(){
        return new ProProductType();
    }

    private static class Mapper implements RowMapper<ProProductType> {

        private Supplier<ProProductType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductType mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductType bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setProductTypeName(Utils.toCast(columnsName.contains(F_PRODUCT_TYPE_NAME) ? rs.getObject(F_PRODUCT_TYPE_NAME) : null, String.class));
            bean.setParentProductTypeId(Utils.toCast(columnsName.contains(F_PARENT_PRODUCT_TYPE_ID) ? rs.getObject(F_PARENT_PRODUCT_TYPE_ID) : null, String.class));
            bean.setParentProductTypeName(Utils.toCast(columnsName.contains(F_PARENT_PRODUCT_TYPE_NAME) ? rs.getObject(F_PARENT_PRODUCT_TYPE_NAME) : null, String.class));
            bean.setProductTypeProImage(Utils.toCast(columnsName.contains(F_PRODUCT_TYPE_PRO_IMAGE) ? rs.getObject(F_PRODUCT_TYPE_PRO_IMAGE) : null, String.class));
            bean.setThumbnailUrl(Utils.toCast(columnsName.contains(F_THUMBNAIL_URL) ? rs.getObject(F_THUMBNAIL_URL) : null, String.class));
            bean.setIsRootType(Utils.toCast(columnsName.contains(F_IS_ROOT_TYPE) ? rs.getObject(F_IS_ROOT_TYPE) : null, Integer.class));
            bean.setIsShowIndex(Utils.toCast(columnsName.contains(F_IS_SHOW_INDEX) ? rs.getObject(F_IS_SHOW_INDEX) : null, Integer.class));
            bean.setIsShowProductMenu(Utils.toCast(columnsName.contains(F_IS_SHOW_PRODUCT_MENU) ? rs.getObject(F_IS_SHOW_PRODUCT_MENU) : null, Integer.class));
            bean.setDescription(Utils.toCast(columnsName.contains(F_DESCRIPTION) ? rs.getObject(F_DESCRIPTION) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
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
    public RowMapper<ProProductType> newMapper(){
        return newMapper(ProProductType::new);
    }

    public RowMapper<ProProductType> newMapper(Supplier<ProProductType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductType {
        @Override
        public abstract RowMapper<ProProductType> newMapper();
    }
}
