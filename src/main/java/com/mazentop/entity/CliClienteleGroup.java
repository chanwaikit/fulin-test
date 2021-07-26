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
* Date:        16:45 2020/11/16
* Version:     1.0
* Description: CliClienteleGroup实体
*/
@SuppressWarnings("all")
public class CliClienteleGroup extends BaseBean<CliClienteleGroup> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "cli_clientele_group";

    /**
    * 客户分组id
    */
    public static final String F_ID = "id";
    /**
    * 分组名称
    */
    public static final String F_NAME = "name";
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
    /**
    * 筛选条件
    */
    public static final String F_SCREEN_CONDITION = "screen_condition";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_NAME, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_SCREEN_CONDITION, null);
    }

    public CliClienteleGroup() {
        super();
    }

    public CliClienteleGroup(Map<String, Object> map) {
        super(map);
    }

    public CliClienteleGroup(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 客户分组id<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 客户分组id set
    */
    public CliClienteleGroup setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return name to name 分组名称<BR/>
    */
    public String getName() {
        return getTypedValue(F_NAME, String.class);
    }
    /**
    * @param name to name 分组名称 set
    */
    public CliClienteleGroup setName(String name) {
        set(F_NAME, name);
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
    public CliClienteleGroup setRemark(String remark) {
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
    public CliClienteleGroup setAddTime(Long addTime) {
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
    public CliClienteleGroup setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
    * @return screen_condition to screenCondition 筛选条件<BR/>
    */
    public String getScreenCondition() {
        return getTypedValue(F_SCREEN_CONDITION, String.class);
    }
    /**
    * @param screenCondition to screen_condition 筛选条件 set
    */
    public CliClienteleGroup setScreenCondition(String screenCondition) {
        set(F_SCREEN_CONDITION, screenCondition);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliClienteleGroup setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliClienteleGroup me(){
        return new CliClienteleGroup();
    }

    private static class Mapper implements RowMapper<CliClienteleGroup> {

        private Supplier<CliClienteleGroup> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliClienteleGroup mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            CliClienteleGroup bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setName(Utils.toCast(columnsName.contains(F_NAME) ? rs.getObject(F_NAME) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setScreenCondition(Utils.toCast(columnsName.contains(F_SCREEN_CONDITION) ? rs.getObject(F_SCREEN_CONDITION) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliClienteleGroup> newMapper(){
        return newMapper(CliClienteleGroup::new);
    }

    public RowMapper<CliClienteleGroup> newMapper(Supplier<CliClienteleGroup> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliClienteleGroup {
        @Override
        public abstract RowMapper<CliClienteleGroup> newMapper();
    }
}
