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
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        10:31 2020/04/15
* Company:     美赞拓
* Version:     1.0
* Description: SysAdvertisementPop实体
*/
@SuppressWarnings("all")
public class SysAdvertisementPop extends BaseBean<SysAdvertisementPop> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_advertisement_pop";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 广告名称
    */
    public static final String F_ADVERTISEMENT_NAME = "advertisement_name";
    /**
    * 位置
    */
    public static final String F_POSITION = "position";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 内容
    */
    public static final String F_CONTENT = "content";
    /**
    * 背景图片
    */
    public static final String F_BG_IMAGE_URL = "bg_image_url";
    /**
    * 目标跳转地址
    */
    public static final String F_TARGET_URL = "target_url";
    /**
    * 是否启动
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_ADVERTISEMENT_NAME, null);
        put(F_POSITION, null);
        put(F_TITLE, null);
        put(F_CONTENT, null);
        put(F_BG_IMAGE_URL, null);
        put(F_TARGET_URL, null);
        put(F_IS_ENABLE, null);
        put(F_REMARK, null);
    }

    public SysAdvertisementPop() {
        super();
    }

    public SysAdvertisementPop(Map<String, Object> map) {
        super(map);
    }

    public SysAdvertisementPop(String id) {
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
    public SysAdvertisementPop setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return advertisement_name to advertisementName 广告名称<BR/>
    */
    public String getAdvertisementName() {
        return getTypedValue(F_ADVERTISEMENT_NAME, String.class);
    }
    /**
    * @param advertisementName to advertisement_name 广告名称 set
    */
    public SysAdvertisementPop setAdvertisementName(String advertisementName) {
        set(F_ADVERTISEMENT_NAME, advertisementName);
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
    public SysAdvertisementPop setPosition(String position) {
        set(F_POSITION, position);
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
    public SysAdvertisementPop setTitle(String title) {
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
    public SysAdvertisementPop setContent(String content) {
        set(F_CONTENT, content);
        return this;
    }
    /**
    * @return bg_image_url to bgImageUrl 背景图片<BR/>
    */
    public String getBgImageUrl() {
        return getTypedValue(F_BG_IMAGE_URL, String.class);
    }
    /**
    * @param bgImageUrl to bg_image_url 背景图片 set
    */
    public SysAdvertisementPop setBgImageUrl(String bgImageUrl) {
        set(F_BG_IMAGE_URL, bgImageUrl);
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
    public SysAdvertisementPop setTargetUrl(String targetUrl) {
        set(F_TARGET_URL, targetUrl);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启动<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启动 set
    */
    public SysAdvertisementPop setIsEnable(Integer isEnable) {
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
    public SysAdvertisementPop setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysAdvertisementPop setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysAdvertisementPop me(){
        return new SysAdvertisementPop();
    }

    private static class Mapper implements RowMapper<SysAdvertisementPop> {

        private Supplier<SysAdvertisementPop> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysAdvertisementPop mapRow(ResultSet rs, int rownum) throws SQLException {
            SysAdvertisementPop bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setAdvertisementName(Utils.toCast(rs.getObject(F_ADVERTISEMENT_NAME), String.class));
            bean.setPosition(Utils.toCast(rs.getObject(F_POSITION), String.class));
            bean.setTitle(Utils.toCast(rs.getObject(F_TITLE), String.class));
            bean.setContent(Utils.toCast(rs.getObject(F_CONTENT), String.class));
            bean.setBgImageUrl(Utils.toCast(rs.getObject(F_BG_IMAGE_URL), String.class));
            bean.setTargetUrl(Utils.toCast(rs.getObject(F_TARGET_URL), String.class));
            bean.setIsEnable(Utils.toCast(rs.getObject(F_IS_ENABLE), Integer.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysAdvertisementPop> newMapper(){
        return newMapper(SysAdvertisementPop::new);
    }

    public RowMapper<SysAdvertisementPop> newMapper(Supplier<SysAdvertisementPop> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysAdvertisementPop {
        @Override
        public abstract RowMapper<SysAdvertisementPop> newMapper();
    }
}
