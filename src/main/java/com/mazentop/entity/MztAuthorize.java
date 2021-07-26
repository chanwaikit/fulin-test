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
 * Date:        16:50 2021/04/13
 * Version:     1.0
 * Description: MztAuthorize实体
 */
@SuppressWarnings("all")
public class MztAuthorize extends BaseBean<MztAuthorize> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "mzt_authorize";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 来源
     */
    public static final String F_SOURCE = "source";
    /**
     * 电子邮箱
     */
    public static final String F_EMAIL = "email";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     *
     */
    public static final String F_CONTENT = "content";
    /**
     * 用户授权码
     */
    public static final String F_USER_TOKEN = "user_token";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SOURCE, null);
        put(F_EMAIL, null);
        put(F_ADD_TIME, null);
        put(F_CONTENT, null);
        put(F_USER_TOKEN, null);
    }

    public MztAuthorize() {
        super();
    }

    public MztAuthorize(Map<String, Object> map) {
        super(map);
    }

    public MztAuthorize(String id) {
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
    public MztAuthorize setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return source to source 来源<BR/>
     */
    public String getSource() {
        return getTypedValue(F_SOURCE, String.class);
    }
    /**
     * @param source to source 来源 set
     */
    public MztAuthorize setSource(String source) {
        set(F_SOURCE, source);
        return this;
    }
    /**
     * @return email to email 电子邮箱<BR/>
     */
    public String getEmail() {
        return getTypedValue(F_EMAIL, String.class);
    }
    /**
     * @param email to email 电子邮箱 set
     */
    public MztAuthorize setEmail(String email) {
        set(F_EMAIL, email);
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
    public MztAuthorize setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
     * @return content to content <BR/>
     */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
     * @param content to content  set
     */
    public MztAuthorize setContent(String content) {
        set(F_CONTENT, content);
        return this;
    }
    /**
     * @return user_token to userToken 用户授权码<BR/>
     */
    public String getUserToken() {
        return getTypedValue(F_USER_TOKEN, String.class);
    }
    /**
     * @param userToken to user_token 用户授权码 set
     */
    public MztAuthorize setUserToken(String userToken) {
        set(F_USER_TOKEN, userToken);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public MztAuthorize setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static MztAuthorize me(){
        return new MztAuthorize();
    }

    private static class Mapper implements RowMapper<MztAuthorize> {

        private Supplier<MztAuthorize> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public MztAuthorize mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            MztAuthorize bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setSource(Utils.toCast(columnsName.contains(F_SOURCE) ? rs.getObject(F_SOURCE) : null, String.class));
            bean.setEmail(Utils.toCast(columnsName.contains(F_EMAIL) ? rs.getObject(F_EMAIL) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setUserToken(Utils.toCast(columnsName.contains(F_USER_TOKEN) ? rs.getObject(F_USER_TOKEN) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<MztAuthorize> newMapper(){
        return newMapper(MztAuthorize::new);
    }

    public RowMapper<MztAuthorize> newMapper(Supplier<MztAuthorize> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends MztAuthorize {
        @Override
        public abstract RowMapper<MztAuthorize> newMapper();
    }
}
