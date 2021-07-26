package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        14:33 2020/07/16
* Version:     1.0
* Description: SkinPageBlock实体
*/
@SuppressWarnings("all")
public class SkinPageBlock extends BaseBean<SkinPageBlock> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_page_block";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * pid
    */
    public static final String F_PID = "pid";
    /**
    * 归属模版主键 子级块可为null
    */
    public static final String F_TEMPLATE_ID = "template_id";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 副标题
    */
    public static final String F_SUB_TITLE = "sub_title";
    /**
    * 布局
    */
    public static final String F_LAYOUT_ID = "layout_id";
    /**
    * 块调用句柄，模板下全局唯一 只可以编辑一次
    */
    public static final String F_HANDLE = "handle";
    /**
    * 显示页面 正常 page 主键,全局显示可特殊定义名称 子级块可为null
    */
    public static final String F_VIEW = "view";
    /**
    * 缩略图
    */
    public static final String F_COVER_URL = "cover_url";
    /**
    * 排序
    */
    public static final String F_SORT = "sort";
    /**
    * 是否启用 0 否 1是
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 扩展信息 例如:幻灯片播放时长、自动播放等
    */
    public static final String F_SETTINGS = "settings";
    /**
    * 备注
    */
    public static final String F_REMARKS = "remarks";
    /**
    * 商品专辑id 可为空
    */
    public static final String F_DATA_ID = "data_id";
    /**
    * 创建时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PID, null);
        put(F_TEMPLATE_ID, null);
        put(F_TITLE, null);
        put(F_SUB_TITLE, null);
        put(F_LAYOUT_ID, null);
        put(F_HANDLE, null);
        put(F_VIEW, null);
        put(F_COVER_URL, null);
        put(F_SORT, null);
        put(F_IS_ENABLE, null);
        put(F_SETTINGS, null);
        put(F_REMARKS, null);
        put(F_DATA_ID, null);
        put(F_ADD_TIME, null);
    }

    public SkinPageBlock() {
        super();
    }

    public SkinPageBlock(Map<String, Object> map) {
        super(map);
    }

    public SkinPageBlock(String id) {
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
    public SkinPageBlock setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return pid to pid pid<BR/>
    */
    public String getPid() {
        return getTypedValue(F_PID, String.class);
    }
    /**
    * @param pid to pid pid set
    */
    public SkinPageBlock setPid(String pid) {
        set(F_PID, pid);
        return this;
    }
    /**
    * @return template_id to templateId 归属模版主键 子级块可为null<BR/>
    */
    public String getTemplateId() {
        return getTypedValue(F_TEMPLATE_ID, String.class);
    }
    /**
    * @param templateId to template_id 归属模版主键 子级块可为null set
    */
    public SkinPageBlock setTemplateId(String templateId) {
        set(F_TEMPLATE_ID, templateId);
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
    public SkinPageBlock setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return sub_title to subTitle 副标题<BR/>
    */
    public String getSubTitle() {
        return getTypedValue(F_SUB_TITLE, String.class);
    }
    /**
    * @param subTitle to sub_title 副标题 set
    */
    public SkinPageBlock setSubTitle(String subTitle) {
        set(F_SUB_TITLE, subTitle);
        return this;
    }
    /**
    * @return layout_id to layoutId 布局<BR/>
    */
    public String getLayoutId() {
        return getTypedValue(F_LAYOUT_ID, String.class);
    }
    /**
    * @param layoutId to layout_id 布局 set
    */
    public SkinPageBlock setLayoutId(String layoutId) {
        set(F_LAYOUT_ID, layoutId);
        return this;
    }
    /**
    * @return handle to handle 块调用句柄，模板下全局唯一 只可以编辑一次<BR/>
    */
    public String getHandle() {
        return getTypedValue(F_HANDLE, String.class);
    }
    /**
    * @param handle to handle 块调用句柄，模板下全局唯一 只可以编辑一次 set
    */
    public SkinPageBlock setHandle(String handle) {
        set(F_HANDLE, handle);
        return this;
    }
    /**
    * @return view to view 显示页面 正常 page 主键,全局显示可特殊定义名称 子级块可为null<BR/>
    */
    public String getView() {
        return getTypedValue(F_VIEW, String.class);
    }
    /**
    * @param view to view 显示页面 正常 page 主键,全局显示可特殊定义名称 子级块可为null set
    */
    public SkinPageBlock setView(String view) {
        set(F_VIEW, view);
        return this;
    }
    /**
    * @return cover_url to coverUrl 缩略图<BR/>
    */
    public String getCoverUrl() {
        return getTypedValue(F_COVER_URL, String.class);
    }
    /**
    * @param coverUrl to cover_url 缩略图 set
    */
    public SkinPageBlock setCoverUrl(String coverUrl) {
        set(F_COVER_URL, coverUrl);
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
    public SkinPageBlock setSort(Integer sort) {
        set(F_SORT, sort);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启用 0 否 1是<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启用 0 否 1是 set
    */
    public SkinPageBlock setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
    * @return settings to settings 扩展信息 例如:幻灯片播放时长、自动播放等<BR/>
    */
    public String getSettings() {
        return getTypedValue(F_SETTINGS, String.class);
    }
    /**
    * @param settings to settings 扩展信息 例如:幻灯片播放时长、自动播放等 set
    */
    public SkinPageBlock setSettings(String settings) {
        set(F_SETTINGS, settings);
        return this;
    }
    /**
    * @return remarks to remarks 备注<BR/>
    */
    public String getRemarks() {
        return getTypedValue(F_REMARKS, String.class);
    }
    /**
    * @param remarks to remarks 备注 set
    */
    public SkinPageBlock setRemarks(String remarks) {
        set(F_REMARKS, remarks);
        return this;
    }
    /**
    * @return data_id to dataId 商品专辑id 可为空<BR/>
    */
    public String getDataId() {
        return getTypedValue(F_DATA_ID, String.class);
    }
    /**
    * @param dataId to data_id 商品专辑id 可为空 set
    */
    public SkinPageBlock setDataId(String dataId) {
        set(F_DATA_ID, dataId);
        return this;
    }
    /**
    * @return add_time to addTime 创建时间<BR/>
    */
    public Long getAddTime() {
        return getTypedValue(F_ADD_TIME, Long.class);
    }
    /**
    * @param addTime to add_time 创建时间 set
    */
    public SkinPageBlock setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinPageBlock setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinPageBlock me(){
        return new SkinPageBlock();
    }

    private static class Mapper implements RowMapper<SkinPageBlock> {

        private Supplier<SkinPageBlock> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinPageBlock mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SkinPageBlock bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setPid(Utils.toCast(columnsName.contains(F_PID) ? rs.getObject(F_PID) : null, String.class));
            bean.setTemplateId(Utils.toCast(columnsName.contains(F_TEMPLATE_ID) ? rs.getObject(F_TEMPLATE_ID) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setSubTitle(Utils.toCast(columnsName.contains(F_SUB_TITLE) ? rs.getObject(F_SUB_TITLE) : null, String.class));
            bean.setLayoutId(Utils.toCast(columnsName.contains(F_LAYOUT_ID) ? rs.getObject(F_LAYOUT_ID) : null, String.class));
            bean.setHandle(Utils.toCast(columnsName.contains(F_HANDLE) ? rs.getObject(F_HANDLE) : null, String.class));
            bean.setView(Utils.toCast(columnsName.contains(F_VIEW) ? rs.getObject(F_VIEW) : null, String.class));
            bean.setCoverUrl(Utils.toCast(columnsName.contains(F_COVER_URL) ? rs.getObject(F_COVER_URL) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setSettings(Utils.toCast(columnsName.contains(F_SETTINGS) ? rs.getObject(F_SETTINGS) : null, String.class));
            bean.setRemarks(Utils.toCast(columnsName.contains(F_REMARKS) ? rs.getObject(F_REMARKS) : null, String.class));
            bean.setDataId(Utils.toCast(columnsName.contains(F_DATA_ID) ? rs.getObject(F_DATA_ID) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinPageBlock> newMapper(){
        return newMapper(SkinPageBlock::new);
    }

    public RowMapper<SkinPageBlock> newMapper(Supplier<SkinPageBlock> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinPageBlock {
        @Override
        public abstract RowMapper<SkinPageBlock> newMapper();
    }
}
