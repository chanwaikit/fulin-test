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
* Date:        14:14 2020/03/10
* Company:     美赞拓
* Version:     1.0
* Description: EmpEmployeeAuthorityInfo实体
*/
@SuppressWarnings("all")
public class EmpEmployeeAuthorityInfo extends BaseBean<EmpEmployeeAuthorityInfo> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "emp_employee_authority_info";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 权限表主键
    */
    public static final String F_FK_AUTHORITY_ID = "fk_authority_id";
    /**
    * 员工表主键
    */
    public static final String F_FK_EMPLOYEE_ID = "fk_employee_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_AUTHORITY_ID, null);
        put(F_FK_EMPLOYEE_ID, null);
    }

    public EmpEmployeeAuthorityInfo() {
        super();
    }

    public EmpEmployeeAuthorityInfo(Map<String, Object> map) {
        super(map);
    }

    public EmpEmployeeAuthorityInfo(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id <BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id  set
    */
    public EmpEmployeeAuthorityInfo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_authority_id to fkAuthorityId 权限表主键<BR/>
    */
    public String getFkAuthorityId() {
        return getTypedValue(F_FK_AUTHORITY_ID, String.class);
    }
    /**
    * @param fkAuthorityId to fk_authority_id 权限表主键 set
    */
    public EmpEmployeeAuthorityInfo setFkAuthorityId(String fkAuthorityId) {
        set(F_FK_AUTHORITY_ID, fkAuthorityId);
        return this;
    }
    /**
    * @return fk_employee_id to fkEmployeeId 员工表主键<BR/>
    */
    public String getFkEmployeeId() {
        return getTypedValue(F_FK_EMPLOYEE_ID, String.class);
    }
    /**
    * @param fkEmployeeId to fk_employee_id 员工表主键 set
    */
    public EmpEmployeeAuthorityInfo setFkEmployeeId(String fkEmployeeId) {
        set(F_FK_EMPLOYEE_ID, fkEmployeeId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EmpEmployeeAuthorityInfo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EmpEmployeeAuthorityInfo me(){
        return new EmpEmployeeAuthorityInfo();
    }

    private static class Mapper implements RowMapper<EmpEmployeeAuthorityInfo> {

        private Supplier<EmpEmployeeAuthorityInfo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EmpEmployeeAuthorityInfo mapRow(ResultSet rs, int rownum) throws SQLException {
            EmpEmployeeAuthorityInfo bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkAuthorityId(Utils.toCast(rs.getObject(F_FK_AUTHORITY_ID), String.class));
            bean.setFkEmployeeId(Utils.toCast(rs.getObject(F_FK_EMPLOYEE_ID), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EmpEmployeeAuthorityInfo> newMapper(){
        return newMapper(EmpEmployeeAuthorityInfo::new);
    }

    public RowMapper<EmpEmployeeAuthorityInfo> newMapper(Supplier<EmpEmployeeAuthorityInfo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EmpEmployeeAuthorityInfo {
        @Override
        public abstract RowMapper<EmpEmployeeAuthorityInfo> newMapper();
    }
}
