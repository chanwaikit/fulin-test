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
* Date:        19:46 2021/06/18
* Version:     1.0
* Description: SkinPage实体
*/
@SuppressWarnings("all")
public class SkinPage extends BaseBean<SkinPage> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "skin_page";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 归属模版主键
    */
    public static final String F_TEMPLATE_ID = "template_id";
    /**
    * 页面标题
    */
    public static final String F_TITLE = "title";
    /**
    * 短链接地址
    */
    public static final String F_SLUG = "slug";
    /**
    * 页面访问地址
    */
    public static final String F_URL = "url";
    /**
    * 描述
    */
    public static final String F_DESCRIPTION = "description";
    /**
    * 封面
    */
    public static final String F_COVER_URL = "cover_url";
    /**
    * 缩略图
    */
    public static final String F_THUMBNAIL_URL = "thumbnail_url";
    /**
    * 来源类型 special-专辑 theme-主题 自定义页面-custom 测评-evaluation
    */
    public static final String F_TYPE = "type";
    /**
    * 创建时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 国家id
    */
    public static final String F_COUNTRY_ID = "country_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TEMPLATE_ID, null);
        put(F_TITLE, null);
        put(F_SLUG, null);
        put(F_URL, null);
        put(F_DESCRIPTION, null);
        put(F_COVER_URL, null);
        put(F_THUMBNAIL_URL, null);
        put(F_TYPE, null);
        put(F_ADD_TIME, null);
        put(F_COUNTRY_ID, null);
    }

    public SkinPage() {
        super();
    }

    public SkinPage(Map<String, Object> map) {
        super(map);
    }

    public SkinPage(String id) {
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
    public SkinPage setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return template_id to templateId 归属模版主键<BR/>
    */
    public String getTemplateId() {
        return getTypedValue(F_TEMPLATE_ID, String.class);
    }
    /**
    * @param templateId to template_id 归属模版主键 set
    */
    public SkinPage setTemplateId(String templateId) {
        set(F_TEMPLATE_ID, templateId);
        return this;
    }
    /**
    * @return title to title 页面标题<BR/>
    */
    public String getTitle() {
        return getTypedValue(F_TITLE, String.class);
    }
    /**
    * @param title to title 页面标题 set
    */
    public SkinPage setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
    * @return slug to slug 短链接地址<BR/>
    */
    public String getSlug() {
        return getTypedValue(F_SLUG, String.class);
    }
    /**
    * @param slug to slug 短链接地址 set
    */
    public SkinPage setSlug(String slug) {
        set(F_SLUG, slug);
        return this;
    }
    /**
    * @return url to url 页面访问地址<BR/>
    */
    public String getUrl() {
        return getTypedValue(F_URL, String.class);
    }
    /**
    * @param url to url 页面访问地址 set
    */
    public SkinPage setUrl(String url) {
        set(F_URL, url);
        return this;
    }
    /**
    * @return description to description 描述<BR/>
    */
    public String getDescription() {
        return getTypedValue(F_DESCRIPTION, String.class);
    }
    /**
    * @param description to description 描述 set
    */
    public SkinPage setDescription(String description) {
        set(F_DESCRIPTION, description);
        return this;
    }
    /**
    * @return cover_url to coverUrl 封面<BR/>
    */
    public String getCoverUrl() {
        return getTypedValue(F_COVER_URL, String.class);
    }
    /**
    * @param coverUrl to cover_url 封面 set
    */
    public SkinPage setCoverUrl(String coverUrl) {
        set(F_COVER_URL, coverUrl);
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
    public SkinPage setThumbnailUrl(String thumbnailUrl) {
        set(F_THUMBNAIL_URL, thumbnailUrl);
        return this;
    }
    /**
    * @return type to type 来源类型 special-专辑 theme-主题 自定义页面-custom 测评-evaluation<BR/>
    */
    public String getType() {
        return getTypedValue(F_TYPE, String.class);
    }
    /**
    * @param type to type 来源类型 special-专辑 theme-主题 自定义页面-custom 测评-evaluation set
    */
    public SkinPage setType(String type) {
        set(F_TYPE, type);
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
    public SkinPage setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
    * @return country_id to countryId 国家id<BR/>
    */
    public Long getCountryId() {
        return getTypedValue(F_COUNTRY_ID, Long.class);
    }
    /**
    * @param countryId to country_id 国家id set
    */
    public SkinPage setCountryId(Long countryId) {
        set(F_COUNTRY_ID, countryId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SkinPage setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SkinPage me(){
        return new SkinPage();
    }

    private static class Mapper implements RowMapper<SkinPage> {

        private Supplier<SkinPage> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SkinPage mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SkinPage bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setTemplateId(Utils.toCast(columnsName.contains(F_TEMPLATE_ID) ? rs.getObject(F_TEMPLATE_ID) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setSlug(Utils.toCast(columnsName.contains(F_SLUG) ? rs.getObject(F_SLUG) : null, String.class));
            bean.setUrl(Utils.toCast(columnsName.contains(F_URL) ? rs.getObject(F_URL) : null, String.class));
            bean.setDescription(Utils.toCast(columnsName.contains(F_DESCRIPTION) ? rs.getObject(F_DESCRIPTION) : null, String.class));
            bean.setCoverUrl(Utils.toCast(columnsName.contains(F_COVER_URL) ? rs.getObject(F_COVER_URL) : null, String.class));
            bean.setThumbnailUrl(Utils.toCast(columnsName.contains(F_THUMBNAIL_URL) ? rs.getObject(F_THUMBNAIL_URL) : null, String.class));
            bean.setType(Utils.toCast(columnsName.contains(F_TYPE) ? rs.getObject(F_TYPE) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setCountryId(Utils.toCast(columnsName.contains(F_COUNTRY_ID) ? rs.getObject(F_COUNTRY_ID) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SkinPage> newMapper(){
        return newMapper(SkinPage::new);
    }

    public RowMapper<SkinPage> newMapper(Supplier<SkinPage> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SkinPage {
        @Override
        public abstract RowMapper<SkinPage> newMapper();
    }
}
