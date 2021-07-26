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
* Date:        16:49 2020/03/19
* Company:     美赞拓
* Version:     1.0
* Description: SysAdvertisement实体
*/
@SuppressWarnings("all")
public class SysAdvertisement extends BaseBean<SysAdvertisement> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_advertisement";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 标题
     */
    public static final String F_TITLE = "title";
    /**
     * 位置
     */
    public static final String F_POSITION = "position";
    /**
     * 图片地址
     */
    public static final String F_IMAGE_URL = "image_url";
    /**
     * 目标跳转地址
     */
    public static final String F_TARGET_URL = "target_url";
    /**
     * 长
     */
    public static final String F_LENGTH = "length";
    /**
     * 宽
     */
    public static final String F_WIDTH = "width";
    /**
     * 格式
     */
    public static final String F_FORMAT = "format";
    /**
     * 说明
     */
    public static final String F_CONTENT = "content";
    /**
     * 同级排序
     */
    public static final String F_SORT = "sort";
    /**
     * 是否开启
     */
    public static final String F_IS_ENABLE = "is_enable";
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

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TITLE, null);
        put(F_POSITION, null);
        put(F_IMAGE_URL, null);
        put(F_TARGET_URL, null);
        put(F_LENGTH, null);
        put(F_WIDTH, null);
        put(F_FORMAT, null);
        put(F_CONTENT, null);
        put(F_SORT, null);
        put(F_IS_ENABLE, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public SysAdvertisement() {
        super();
    }

    public SysAdvertisement(Map<String, Object> map) {
        super(map);
    }

    public SysAdvertisement(String id) {
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
    public SysAdvertisement setId(String id) {
        set(F_ID, id);
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
    public SysAdvertisement setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
     * @return position to position 位置<BR/>
     */
    public String getPosition() {
        return getTypedValue(F_POSITION, String.class);
    }
    /**
     * @param position to position 位置 set
     */
    public SysAdvertisement setPosition(String position) {
        set(F_POSITION, position);
        return this;
    }
    /**
     * @return image_url to imageUrl 图片地址<BR/>
     */
    public String getImageUrl() {
        return getTypedValue(F_IMAGE_URL, String.class);
    }
    /**
     * @param imageUrl to image_url 图片地址 set
     */
    public SysAdvertisement setImageUrl(String imageUrl) {
        set(F_IMAGE_URL, imageUrl);
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
    public SysAdvertisement setTargetUrl(String targetUrl) {
        set(F_TARGET_URL, targetUrl);
        return this;
    }
    /**
     * @return length to length 长<BR/>
     */
    public Integer getLength() {
        return getTypedValue(F_LENGTH, Integer.class);
    }
    /**
     * @param length to length 长 set
     */
    public SysAdvertisement setLength(Integer length) {
        set(F_LENGTH, length);
        return this;
    }
    /**
     * @return width to width 宽<BR/>
     */
    public Integer getWidth() {
        return getTypedValue(F_WIDTH, Integer.class);
    }
    /**
     * @param width to width 宽 set
     */
    public SysAdvertisement setWidth(Integer width) {
        set(F_WIDTH, width);
        return this;
    }
    /**
     * @return format to format 格式<BR/>
     */
    public String getFormat() {
        return getTypedValue(F_FORMAT, String.class);
    }
    /**
     * @param format to format 格式 set
     */
    public SysAdvertisement setFormat(String format) {
        set(F_FORMAT, format);
        return this;
    }
    /**
     * @return content to content 说明<BR/>
     */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
     * @param content to content 说明 set
     */
    public SysAdvertisement setContent(String content) {
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
    public SysAdvertisement setSort(Integer sort) {
        set(F_SORT, sort);
        return this;
    }
    /**
     * @return is_enable to isEnable 是否开启<BR/>
     */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
     * @param isEnable to is_enable 是否开启 set
     */
    public SysAdvertisement setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
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
    public SysAdvertisement setRemark(String remark) {
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
    public SysAdvertisement setAddTime(Long addTime) {
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
    public SysAdvertisement setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysAdvertisement setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysAdvertisement me(){
        return new SysAdvertisement();
    }

    private static class Mapper implements RowMapper<SysAdvertisement> {

        private Supplier<SysAdvertisement> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysAdvertisement mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysAdvertisement bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setPosition(Utils.toCast(columnsName.contains(F_POSITION) ? rs.getObject(F_POSITION) : null, String.class));
            bean.setImageUrl(Utils.toCast(columnsName.contains(F_IMAGE_URL) ? rs.getObject(F_IMAGE_URL) : null, String.class));
            bean.setTargetUrl(Utils.toCast(columnsName.contains(F_TARGET_URL) ? rs.getObject(F_TARGET_URL) : null, String.class));
            bean.setLength(Utils.toCast(columnsName.contains(F_LENGTH) ? rs.getObject(F_LENGTH) : null, Integer.class));
            bean.setWidth(Utils.toCast(columnsName.contains(F_WIDTH) ? rs.getObject(F_WIDTH) : null, Integer.class));
            bean.setFormat(Utils.toCast(columnsName.contains(F_FORMAT) ? rs.getObject(F_FORMAT) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysAdvertisement> newMapper(){
        return newMapper(SysAdvertisement::new);
    }

    public RowMapper<SysAdvertisement> newMapper(Supplier<SysAdvertisement> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysAdvertisement {
        @Override
        public abstract RowMapper<SysAdvertisement> newMapper();
    }
}
