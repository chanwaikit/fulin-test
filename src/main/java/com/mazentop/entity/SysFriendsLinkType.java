package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        18:12 2020/04/01
* Company:     美赞拓
* Version:     1.0
* Description: SysFriendsLinkType实体
*/
@SuppressWarnings("all")
public class SysFriendsLinkType extends BaseBean<SysFriendsLinkType> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_friends_link_type";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 友情链接类型名称
    */
    public static final String F_FRIENDS_LINK_TYPE_NAME = "friends_link_type_name";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FRIENDS_LINK_TYPE_NAME, null);
        put(F_REMARK, null);
    }

    public SysFriendsLinkType() {
        super();
    }

    public SysFriendsLinkType(Map<String, Object> map) {
        super(map);
    }

    public SysFriendsLinkType(String id) {
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
    public SysFriendsLinkType setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return friends_link_type_name to friendsLinkTypeName 友情链接类型名称<BR/>
    */
    public String getFriendsLinkTypeName() {
        return getTypedValue(F_FRIENDS_LINK_TYPE_NAME, String.class);
    }
    /**
    * @param friendsLinkTypeName to friends_link_type_name 友情链接类型名称 set
    */
    public SysFriendsLinkType setFriendsLinkTypeName(String friendsLinkTypeName) {
        set(F_FRIENDS_LINK_TYPE_NAME, friendsLinkTypeName);
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
    public SysFriendsLinkType setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysFriendsLinkType setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysFriendsLinkType me(){
        return new SysFriendsLinkType();
    }

    private static class Mapper implements RowMapper<SysFriendsLinkType> {

        private Supplier<SysFriendsLinkType> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysFriendsLinkType mapRow(ResultSet rs, int rownum) throws SQLException {
            SysFriendsLinkType bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFriendsLinkTypeName(Utils.toCast(rs.getObject(F_FRIENDS_LINK_TYPE_NAME), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysFriendsLinkType> newMapper(){
        return newMapper(SysFriendsLinkType::new);
    }

    public RowMapper<SysFriendsLinkType> newMapper(Supplier<SysFriendsLinkType> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysFriendsLinkType {
        @Override
        public abstract RowMapper<SysFriendsLinkType> newMapper();
    }
}
