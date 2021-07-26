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
 * Date:        18:42 2021/05/18
 * Version:     1.0
 * Description: CmsArticle实体
 */
@SuppressWarnings("all")
public class CmsArticle extends BaseBean<CmsArticle> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "cms_article";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 归属用户主键
     */
    public static final String F_FK_USER_ID = "fk_user_id";
    /**
     * 请求路径后置参数默认文章标题可修改不重复
     */
    public static final String F_SLUG = "slug";
    /**
     * 文章标题
     */
    public static final String F_TITLE = "title";
    /**
     * 缩略图
     */
    public static final String F_THUMBNAIL = "thumbnail";
    /**
     * 文章内容
     */
    public static final String F_CONTENT = "content";
    /**
     * 模块 例如 博客/FAQ
     */
    public static final String F_MODULE = "module";
    /**
     * 摘要
     */
    public static final String F_SUMMARY = "summary";
    /**
     * 连接到相关文章 多个id逗号隔开
     */
    public static final String F_LINK_TO = "link_to";
    /**
     * 文章状态 normal 发布 draft 草稿 trash回收站
     */
    public static final String F_STATUS = "status";
    /**
     * 是否允许评论 0否 1是
     */
    public static final String F_COMMENT_STATUS = "comment_status";
    /**
     * 评论总数
     */
    public static final String F_COMMENT_COUNT = "comment_count";
    /**
     * 最后评论时间
     */
    public static final String F_COMMENT_LOGGED = "comment_logged";
    /**
     * 访问量
     */
    public static final String F_VIEW_COUNT = "view_count";
    /**
     * SEO关键词
     */
    public static final String F_META_KEYWORDS = "meta_keywords";
    /**
     * SEO描述信息
     */
    public static final String F_META_DESCRIPTION = "meta_description";
    /**
     * 排序
     */
    public static final String F_SORT = "sort";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 更新时间
     */
    public static final String F_UPDATE_TIME = "update_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_USER_ID, null);
        put(F_SLUG, null);
        put(F_TITLE, null);
        put(F_THUMBNAIL, null);
        put(F_CONTENT, null);
        put(F_MODULE, null);
        put(F_SUMMARY, null);
        put(F_LINK_TO, null);
        put(F_STATUS, null);
        put(F_COMMENT_STATUS, null);
        put(F_COMMENT_COUNT, null);
        put(F_COMMENT_LOGGED, null);
        put(F_VIEW_COUNT, null);
        put(F_META_KEYWORDS, null);
        put(F_META_DESCRIPTION, null);
        put(F_SORT, null);
        put(F_ADD_TIME, null);
        put(F_UPDATE_TIME, null);
    }

    public CmsArticle() {
        super();
    }

    public CmsArticle(Map<String, Object> map) {
        super(map);
    }

    public CmsArticle(String id) {
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
    public CmsArticle setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_user_id to fkUserId 归属用户主键<BR/>
     */
    public String getFkUserId() {
        return getTypedValue(F_FK_USER_ID, String.class);
    }
    /**
     * @param fkUserId to fk_user_id 归属用户主键 set
     */
    public CmsArticle setFkUserId(String fkUserId) {
        set(F_FK_USER_ID, fkUserId);
        return this;
    }
    /**
     * @return slug to slug 请求路径后置参数默认文章标题可修改不重复<BR/>
     */
    public String getSlug() {
        return getTypedValue(F_SLUG, String.class);
    }
    /**
     * @param slug to slug 请求路径后置参数默认文章标题可修改不重复 set
     */
    public CmsArticle setSlug(String slug) {
        set(F_SLUG, slug);
        return this;
    }
    /**
     * @return title to title 文章标题<BR/>
     */
    public String getTitle() {
        return getTypedValue(F_TITLE, String.class);
    }
    /**
     * @param title to title 文章标题 set
     */
    public CmsArticle setTitle(String title) {
        set(F_TITLE, title);
        return this;
    }
    /**
     * @return thumbnail to thumbnail 缩略图<BR/>
     */
    public String getThumbnail() {
        return getTypedValue(F_THUMBNAIL, String.class);
    }
    /**
     * @param thumbnail to thumbnail 缩略图 set
     */
    public CmsArticle setThumbnail(String thumbnail) {
        set(F_THUMBNAIL, thumbnail);
        return this;
    }
    /**
     * @return content to content 文章内容<BR/>
     */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
     * @param content to content 文章内容 set
     */
    public CmsArticle setContent(String content) {
        set(F_CONTENT, content);
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
    public CmsArticle setModule(String module) {
        set(F_MODULE, module);
        return this;
    }
    /**
     * @return summary to summary 摘要<BR/>
     */
    public String getSummary() {
        return getTypedValue(F_SUMMARY, String.class);
    }
    /**
     * @param summary to summary 摘要 set
     */
    public CmsArticle setSummary(String summary) {
        set(F_SUMMARY, summary);
        return this;
    }
    /**
     * @return link_to to linkTo 连接到相关文章 多个id逗号隔开<BR/>
     */
    public String getLinkTo() {
        return getTypedValue(F_LINK_TO, String.class);
    }
    /**
     * @param linkTo to link_to 连接到相关文章 多个id逗号隔开 set
     */
    public CmsArticle setLinkTo(String linkTo) {
        set(F_LINK_TO, linkTo);
        return this;
    }
    /**
     * @return status to status 文章状态 normal 发布 draft 草稿 trash回收站<BR/>
     */
    public String getStatus() {
        return getTypedValue(F_STATUS, String.class);
    }
    /**
     * @param status to status 文章状态 normal 发布 draft 草稿 trash回收站 set
     */
    public CmsArticle setStatus(String status) {
        set(F_STATUS, status);
        return this;
    }
    /**
     * @return comment_status to commentStatus 是否允许评论 0否 1是<BR/>
     */
    public Integer getCommentStatus() {
        return getTypedValue(F_COMMENT_STATUS, Integer.class);
    }
    /**
     * @param commentStatus to comment_status 是否允许评论 0否 1是 set
     */
    public CmsArticle setCommentStatus(Integer commentStatus) {
        set(F_COMMENT_STATUS, commentStatus);
        return this;
    }
    /**
     * @return comment_count to commentCount 评论总数<BR/>
     */
    public Integer getCommentCount() {
        return getTypedValue(F_COMMENT_COUNT, Integer.class);
    }
    /**
     * @param commentCount to comment_count 评论总数 set
     */
    public CmsArticle setCommentCount(Integer commentCount) {
        set(F_COMMENT_COUNT, commentCount);
        return this;
    }
    /**
     * @return comment_logged to commentLogged 最后评论时间<BR/>
     */
    public Long getCommentLogged() {
        return getTypedValue(F_COMMENT_LOGGED, Long.class);
    }
    /**
     * @param commentLogged to comment_logged 最后评论时间 set
     */
    public CmsArticle setCommentLogged(Long commentLogged) {
        set(F_COMMENT_LOGGED, commentLogged);
        return this;
    }
    /**
     * @return view_count to viewCount 访问量<BR/>
     */
    public Integer getViewCount() {
        return getTypedValue(F_VIEW_COUNT, Integer.class);
    }
    /**
     * @param viewCount to view_count 访问量 set
     */
    public CmsArticle setViewCount(Integer viewCount) {
        set(F_VIEW_COUNT, viewCount);
        return this;
    }
    /**
     * @return meta_keywords to metaKeywords SEO关键词<BR/>
     */
    public String getMetaKeywords() {
        return getTypedValue(F_META_KEYWORDS, String.class);
    }
    /**
     * @param metaKeywords to meta_keywords SEO关键词 set
     */
    public CmsArticle setMetaKeywords(String metaKeywords) {
        set(F_META_KEYWORDS, metaKeywords);
        return this;
    }
    /**
     * @return meta_description to metaDescription SEO描述信息<BR/>
     */
    public String getMetaDescription() {
        return getTypedValue(F_META_DESCRIPTION, String.class);
    }
    /**
     * @param metaDescription to meta_description SEO描述信息 set
     */
    public CmsArticle setMetaDescription(String metaDescription) {
        set(F_META_DESCRIPTION, metaDescription);
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
    public CmsArticle setSort(Integer sort) {
        set(F_SORT, sort);
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
    public CmsArticle setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return update_time to updateTime 更新时间<BR/>
     */
    public Long getUpdateTime() {
        return getTypedValue(F_UPDATE_TIME, Long.class);
    }
    /**
     * @param updateTime to update_time 更新时间 set
     */
    public CmsArticle setUpdateTime(Long updateTime) {
        set(F_UPDATE_TIME, updateTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CmsArticle setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CmsArticle me(){
        return new CmsArticle();
    }

    private static class Mapper implements RowMapper<CmsArticle> {

        private Supplier<CmsArticle> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CmsArticle mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            CmsArticle bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkUserId(Utils.toCast(columnsName.contains(F_FK_USER_ID) ? rs.getObject(F_FK_USER_ID) : null, String.class));
            bean.setSlug(Utils.toCast(columnsName.contains(F_SLUG) ? rs.getObject(F_SLUG) : null, String.class));
            bean.setTitle(Utils.toCast(columnsName.contains(F_TITLE) ? rs.getObject(F_TITLE) : null, String.class));
            bean.setThumbnail(Utils.toCast(columnsName.contains(F_THUMBNAIL) ? rs.getObject(F_THUMBNAIL) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setModule(Utils.toCast(columnsName.contains(F_MODULE) ? rs.getObject(F_MODULE) : null, String.class));
            bean.setSummary(Utils.toCast(columnsName.contains(F_SUMMARY) ? rs.getObject(F_SUMMARY) : null, String.class));
            bean.setLinkTo(Utils.toCast(columnsName.contains(F_LINK_TO) ? rs.getObject(F_LINK_TO) : null, String.class));
            bean.setStatus(Utils.toCast(columnsName.contains(F_STATUS) ? rs.getObject(F_STATUS) : null, String.class));
            bean.setCommentStatus(Utils.toCast(columnsName.contains(F_COMMENT_STATUS) ? rs.getObject(F_COMMENT_STATUS) : null, Integer.class));
            bean.setCommentCount(Utils.toCast(columnsName.contains(F_COMMENT_COUNT) ? rs.getObject(F_COMMENT_COUNT) : null, Integer.class));
            bean.setCommentLogged(Utils.toCast(columnsName.contains(F_COMMENT_LOGGED) ? rs.getObject(F_COMMENT_LOGGED) : null, Long.class));
            bean.setViewCount(Utils.toCast(columnsName.contains(F_VIEW_COUNT) ? rs.getObject(F_VIEW_COUNT) : null, Integer.class));
            bean.setMetaKeywords(Utils.toCast(columnsName.contains(F_META_KEYWORDS) ? rs.getObject(F_META_KEYWORDS) : null, String.class));
            bean.setMetaDescription(Utils.toCast(columnsName.contains(F_META_DESCRIPTION) ? rs.getObject(F_META_DESCRIPTION) : null, String.class));
            bean.setSort(Utils.toCast(columnsName.contains(F_SORT) ? rs.getObject(F_SORT) : null, Integer.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setUpdateTime(Utils.toCast(columnsName.contains(F_UPDATE_TIME) ? rs.getObject(F_UPDATE_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CmsArticle> newMapper(){
        return newMapper(CmsArticle::new);
    }

    public RowMapper<CmsArticle> newMapper(Supplier<CmsArticle> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CmsArticle {
        @Override
        public abstract RowMapper<CmsArticle> newMapper();
    }
}
