package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        18:30 2020/04/26
* Company:     美赞拓
* Version:     1.0
* Description: ProProductImage实体
*/
@SuppressWarnings("all")
public class ProProductImage extends BaseBean<ProProductImage> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "pro_product_image";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 商品编号
     */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
     * 商品分类预览图
     */
    public static final String F_PRODUCT_IMAGE_URL = "product_image_url";
    /**
     * SEO-标题
     */
    public static final String F_ALT = "alt";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
     * 排序
     */
    public static final String F_SORT = "sort";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_IMAGE_URL, null);
        put(F_ALT, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_SORT, null);
    }

    public ProProductImage() {
        super();
    }

    public ProProductImage(Map<String, Object> map) {
        super(map);
    }

    public ProProductImage(String id) {
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
    public ProProductImage setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_product_id to fkProductId 商品编号<BR/>
     */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
     * @param fkProductId to fk_product_id 商品编号 set
     */
    public ProProductImage setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
     * @return product_image_url to productImageUrl 商品分类预览图<BR/>
     */
    public String getProductImageUrl() {
        return getTypedValue(F_PRODUCT_IMAGE_URL, String.class);
    }
    /**
     * @param productImageUrl to product_image_url 商品分类预览图 set
     */
    public ProProductImage setProductImageUrl(String productImageUrl) {
        set(F_PRODUCT_IMAGE_URL, productImageUrl);
        return this;
    }
    /**
     * @return alt to alt SEO-标题<BR/>
     */
    public String getAlt() {
        return getTypedValue(F_ALT, String.class);
    }
    /**
     * @param alt to alt SEO-标题 set
     */
    public ProProductImage setAlt(String alt) {
        set(F_ALT, alt);
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
    public ProProductImage setAddTime(Long addTime) {
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
    public ProProductImage setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
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
    public ProProductImage setSort(Integer sort) {
        set(F_SORT, sort);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductImage setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductImage me(){
        return new ProProductImage();
    }

    private static class Mapper implements RowMapper<ProProductImage> {

        private Supplier<ProProductImage> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductImage mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductImage bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setProductImageUrl(Utils.toCast(columnsName.contains(F_PRODUCT_IMAGE_URL) ? rs.getObject(F_PRODUCT_IMAGE_URL) : null, String.class));
            bean.setAlt(Utils.toCast(columnsName.contains(F_ALT) ? rs.getObject(F_ALT) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductImage> newMapper(){
        return newMapper(ProProductImage::new);
    }

    public RowMapper<ProProductImage> newMapper(Supplier<ProProductImage> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductImage {
        @Override
        public abstract RowMapper<ProProductImage> newMapper();
    }
}
