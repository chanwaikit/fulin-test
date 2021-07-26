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
* Date:        20:31 2020/06/04
* Company:     美赞拓
* Version:     1.0
* Description: CmsTaxonomy实体
*/
@SuppressWarnings("all")
public class CmsTaxonomy extends BaseBean<CmsTaxonomy> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "cms_taxonomy";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 父级别分类主键
    */
    public static final String F_PID = "pid";
    /**
    * 访问标识
    */
    public static final String F_SLUG = "slug";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 图标
    */
    public static final String F_ICON = "icon";
    /**
    * 该分类下文章数量
    */
    public static final String F_COUNT = "count";
    /**
    * 排序号
    */
    public static final String F_SORT = "sort";
    /**
    * 模块 例如 博客/FAQ
    */
    public static final String F_MODULE = "module";
    /**
    * 是否启用 0否 1是
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 布局
    */
    public static final String F_LAYOUT = "layout";
    /**
    * 类型  标签/专题/分类/菜单
    */
    public static final String F_TYPE = "type";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_PID, null);
        put(F_SLUG, null);
        put(F_TITLE, null);
        put(F_ICON, null);
        put(F_COUNT, null);
        put(F_SORT, null);
        put(F_MODULE, null);
        put(F_IS_ENABLE, null);
        put(F_LAYOUT, null);
        put(F_TYPE, null);
        put(F_ADD_TIME, null);
    }

    public CmsTaxonomy() {
        super();
    }

    public CmsTaxonomy(Map<String, Object> map) {
        super(map);
    }

    public CmsTaxonomy(String id) {
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
    public CmsTaxonomy setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return pid to pid 父级别分类主键<BR/>
    */
    public String getPid() {
        return getTypedValue(F_PID, String.class);
    }
    /**
    * @param pid to pid 父级别分类主键 set
    */
    public CmsTaxonomy setPid(String pid) {
        set(F_PID, pid);
        return this;
    }
    /**
    * @return slug to slug 访问标识<BR/>
    */
    public String getSlug() {
        return getTypedValue(F_SLUG, String.class);
    }
    /**
    * @param slug to slug 访问标识 set
    */
    public CmsTaxonomy setSlug(String slug) {
        set(F_SLUG, slug);
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
    public CmsTaxonomy setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return icon to icon 图标<BR/>
    */
    public String getIcon() {
        return getTypedValue(F_ICON, String.class);
    }
    /**
    * @param icon to icon 图标 set
    */
    public CmsTaxonomy setIcon(String icon) {
        set(F_ICON, icon);
        return this;
    }
    /**
    * @return count to count 该分类下文章数量<BR/>
    */
    public Integer getCount() {
        return getTypedValue(F_COUNT, Integer.class);
    }
    /**
    * @param count to count 该分类下文章数量 set
    */
    public CmsTaxonomy setCount(Integer count) {
        set(F_COUNT, count);
        return this;
    }
    /**
    * @return sort to sort 排序号<BR/>
    */
    public Integer getSort() {
        return getTypedValue(F_SORT, Integer.class);
    }
    /**
    * @param sort to sort 排序号 set
    */
    public CmsTaxonomy setSort(Integer sort) {
        set(F_SORT, sort);
        return this;
    }
    /**
    * @return module to module 模块 例如 博客/FAQ<BR/>
    */
    public String getModule() {
        return getTypedValue(F_MODULE, String.class);
    }
    /**
    * @param module to module 模块 例如 博客/FAQ set
    */
    public CmsTaxonomy setModule(String module) {
        set(F_MODULE, module);
        return this;
    }
    /**
    * @return is_enable to isEnable 是否启用 0否 1是<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 是否启用 0否 1是 set
    */
    public CmsTaxonomy setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
    * @return layout to layout 布局<BR/>
    */
    public String getLayout() {
        return getTypedValue(F_LAYOUT, String.class);
    }
    /**
    * @param layout to layout 布局 set
    */
    public CmsTaxonomy setLayout(String layout) {
        set(F_LAYOUT, layout);
        return this;
    }
    /**
    * @return type to type 类型  标签/专题/分类/菜单<BR/>
    */
    public String getType() {
        return getTypedValue(F_TYPE, String.class);
    }
    /**
    * @param type to type 类型  标签/专题/分类/菜单 set
    */
    public CmsTaxonomy setType(String type) {
        set(F_TYPE, type);
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
    public CmsTaxonomy setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CmsTaxonomy setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CmsTaxonomy me(){
        return new CmsTaxonomy();
    }

    private static class Mapper implements RowMapper<CmsTaxonomy> {

        private Supplier<CmsTaxonomy> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CmsTaxonomy mapRow(ResultSet rs, int rownum) throws SQLException {
            CmsTaxonomy bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setPid(Utils.toCast(rs.getObject(F_PID), String.class));
            bean.setSlug(Utils.toCast(rs.getObject(F_SLUG), String.class));
            bean.setTitle(Utils.toCast(rs.getObject(F_TITLE), String.class));
            bean.setIcon(Utils.toCast(rs.getObject(F_ICON), String.class));
            bean.setCount(Utils.toCast(rs.getObject(F_COUNT), Integer.class));
            bean.setSort(Utils.toCast(rs.getObject(F_SORT), Integer.class));
            bean.setModule(Utils.toCast(rs.getObject(F_MODULE), String.class));
            bean.setIsEnable(Utils.toCast(rs.getObject(F_IS_ENABLE), Integer.class));
            bean.setLayout(Utils.toCast(rs.getObject(F_LAYOUT), String.class));
            bean.setType(Utils.toCast(rs.getObject(F_TYPE), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CmsTaxonomy> newMapper(){
        return newMapper(CmsTaxonomy::new);
    }

    public RowMapper<CmsTaxonomy> newMapper(Supplier<CmsTaxonomy> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CmsTaxonomy {
        @Override
        public abstract RowMapper<CmsTaxonomy> newMapper();
    }
}
