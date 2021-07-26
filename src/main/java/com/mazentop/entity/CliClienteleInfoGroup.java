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
* Date:        09:44 2020/11/10
* Version:     1.0
* Description: CliClienteleInfoGroup实体
*/
@SuppressWarnings("all")
public class CliClienteleInfoGroup extends BaseBean<CliClienteleInfoGroup> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "cli_clientele_info_group";

    /**
    * 主键id
    */
    public static final String F_ID = "id";
    /**
    * 客户分组id
    */
    public static final String F_GROUP_ID = "group_id";
    /**
    * 客户信息id
    */
    public static final String F_CLIENTELE_ID = "clientele_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_GROUP_ID, null);
        put(F_CLIENTELE_ID, null);
    }

    public CliClienteleInfoGroup() {
        super();
    }

    public CliClienteleInfoGroup(Map<String, Object> map) {
        super(map);
    }

    public CliClienteleInfoGroup(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 主键id<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 主键id set
    */
    public CliClienteleInfoGroup setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return group_id to groupId 客户分组id<BR/>
    */
    public String getGroupId() {
        return getTypedValue(F_GROUP_ID, String.class);
    }
    /**
    * @param groupId to group_id 客户分组id set
    */
    public CliClienteleInfoGroup setGroupId(String groupId) {
        set(F_GROUP_ID, groupId);
        return this;
    }
    /**
    * @return clientele_id to clienteleId 客户信息id<BR/>
    */
    public String getClienteleId() {
        return getTypedValue(F_CLIENTELE_ID, String.class);
    }
    /**
    * @param clienteleId to clientele_id 客户信息id set
    */
    public CliClienteleInfoGroup setClienteleId(String clienteleId) {
        set(F_CLIENTELE_ID, clienteleId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliClienteleInfoGroup setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliClienteleInfoGroup me(){
        return new CliClienteleInfoGroup();
    }

    private static class Mapper implements RowMapper<CliClienteleInfoGroup> {

        private Supplier<CliClienteleInfoGroup> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliClienteleInfoGroup mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            CliClienteleInfoGroup bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setGroupId(Utils.toCast(columnsName.contains(F_GROUP_ID) ? rs.getObject(F_GROUP_ID) : null, String.class));
            bean.setClienteleId(Utils.toCast(columnsName.contains(F_CLIENTELE_ID) ? rs.getObject(F_CLIENTELE_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliClienteleInfoGroup> newMapper(){
        return newMapper(CliClienteleInfoGroup::new);
    }

    public RowMapper<CliClienteleInfoGroup> newMapper(Supplier<CliClienteleInfoGroup> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliClienteleInfoGroup {
        @Override
        public abstract RowMapper<CliClienteleInfoGroup> newMapper();
    }
}
