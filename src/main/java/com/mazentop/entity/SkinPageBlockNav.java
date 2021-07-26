package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        02:54 2020/06/14
* Version:     1.0
* Description: SkinPageBlockNav实体
*/
@SuppressWarnings("all")
public class SkinPageBlockNav extends BaseBean<SkinPageBlockNav> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_page_block_nav";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 块主键
    */
    public static final String F_BLOCK_ID = "block_id";
    /**
    * 是否父节点0否1是
    */
    public static final String F_IS_PARENT = "is_parent";
    /**
    * pid
    */
    public static final String F_PID = "pid";
    /**
    * 标题
    */
    public static final String F_TITLE = "title";
    /**
    * 跳转地址
    */
    public static final String F_URL = "url";
    /**
    * 跳转目标 _blank _self
    */
    public static final String F_TARGET = "target";
    /**
    * 图标
    */
    public static final String F_ICON = "icon";
    /**
    * 类型 超连接 link 分类 category  其他自定义
    */
    public static final String F_TYPE = "type";
    /**
    * 内联数据 type 非 link 类型 该数据必填, json格式
    */
    public static final String F_INLINE_DATA = "inline_data";
    /**
    * 排序
    */
    public static final String F_SORT = "sort";
    /**
    * 创建时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_BLOCK_ID, null);
        put(F_IS_PARENT, null);
        put(F_PID, null);
        put(F_TITLE, null);
        put(F_URL, null);
        put(F_TARGET, null);
        put(F_ICON, null);
        put(F_TYPE, null);
        put(F_INLINE_DATA, null);
        put(F_SORT, null);
        put(F_ADD_TIME, null);
    }

    public SkinPageBlockNav() {
        super();
    }

    public SkinPageBlockNav(Map<String, Object> map) {
        super(map);
    }

    public SkinPageBlockNav(String id) {
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
    public SkinPageBlockNav setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return block_id to blockId 块主键<BR/>
    */
    public String getBlockId() {
        return getTypedValue(F_BLOCK_ID, String.class);
    }
    /**
    * @param blockId to block_id 块主键 set
    */
    public SkinPageBlockNav setBlockId(String blockId) {
        set(F_BLOCK_ID, blockId);
        return this;
    }
    /**
    * @return is_parent to isParent 是否父节点0否1是<BR/>
    */
    public Integer getIsParent() {
        return getTypedValue(F_IS_PARENT, Integer.class);
    }
    /**
    * @param isParent to is_parent 是否父节点0否1是 set
    */
    public SkinPageBlockNav setIsParent(Integer isParent) {
        set(F_IS_PARENT, isParent);
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
    public SkinPageBlockNav setPid(String pid) {
        set(F_PID, pid);
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
    public SkinPageBlockNav setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return url to url 跳转地址<BR/>
    */
    public String getUrl() {
        return getTypedValue(F_URL, String.class);
    }
    /**
    * @param url to url 跳转地址 set
    */
    public SkinPageBlockNav setUrl(String url) {
        set(F_URL, url);
        return this;
    }
    /**
    * @return target to target 跳转目标 _blank _self<BR/>
    */
    public String getTarget() {
        return getTypedValue(F_TARGET, String.class);
    }
    /**
    * @param target to target 跳转目标 _blank _self set
    */
    public SkinPageBlockNav setTarget(String target) {
        set(F_TARGET, target);
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
    public SkinPageBlockNav setIcon(String icon) {
        set(F_ICON, icon);
        return this;
    }
    /**
    * @return type to type 类型 超连接 link 分类 category  其他自定义<BR/>
    */
    public String getType() {
        return getTypedValue(F_TYPE, String.class);
    }
    /**
    * @param type to type 类型 超连接 link 分类 category  其他自定义 set
    */
    public SkinPageBlockNav setType(String type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return inline_data to inlineData 内联数据 type 非 link 类型 该数据必填, json格式<BR/>
    */
    public String getInlineData() {
        return getTypedValue(F_INLINE_DATA, String.class);
    }
    /**
    * @param inlineData to inline_data 内联数据 type 非 link 类型 该数据必填, json格式 set
    */
    public SkinPageBlockNav setInlineData(String inlineData) {
        set(F_INLINE_DATA, inlineData);
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
    public SkinPageBlockNav setSort(Integer sort) {
        set(F_SORT, sort);
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
    public SkinPageBlockNav setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinPageBlockNav setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinPageBlockNav me(){
        return new SkinPageBlockNav();
    }

    private static class Mapper implements RowMapper<SkinPageBlockNav> {

        private Supplier<SkinPageBlockNav> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinPageBlockNav mapRow(ResultSet rs, int rownum) throws SQLException {

            SkinPageBlockNav bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setBlockId(Utils.toCast(rs.getObject(F_BLOCK_ID), String.class));
            bean.setIsParent(Utils.toCast(rs.getObject(F_IS_PARENT), Integer.class));
            bean.setPid(Utils.toCast(rs.getObject(F_PID), String.class));
            bean.setTitle(Utils.toCast(rs.getObject(F_TITLE), String.class));
            bean.setUrl(Utils.toCast(rs.getObject(F_URL), String.class));
            bean.setTarget(Utils.toCast(rs.getObject(F_TARGET), String.class));
            bean.setIcon(Utils.toCast(rs.getObject(F_ICON), String.class));
            bean.setType(Utils.toCast(rs.getObject(F_TYPE), String.class));
            bean.setInlineData(Utils.toCast(rs.getObject(F_INLINE_DATA), String.class));
            bean.setSort(Utils.toCast(rs.getObject(F_SORT), Integer.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinPageBlockNav> newMapper(){
        return newMapper(SkinPageBlockNav::new);
    }

    public RowMapper<SkinPageBlockNav> newMapper(Supplier<SkinPageBlockNav> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinPageBlockNav {
        @Override
        public abstract RowMapper<SkinPageBlockNav> newMapper();
    }
}
