package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
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
* Description: EmpDepartmentInfo实体
*/
@SuppressWarnings("all")
public class EmpDepartmentInfo extends BaseBean<EmpDepartmentInfo> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "emp_department_info";

    /**
     * 部门表主键
     */
    public static final String F_ID = "id";
    /**
     * 部门名称
     */
    public static final String F_DEPARTMENT_NAME = "department_name";
    /**
     * 部门概述
     */
    public static final String F_DEPARTMENT_CONTENT = "department_content";
    /**
     * 部门人数
     */
    public static final String F_EMPLOYEE_NUMBER = "employee_number";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
     * 备注
     */
    public static final String F_REMARK = "remark";
    /**
     * 0.否1.是（是否启用）
     */
    public static final String F_IS_ENABLE = "is_enable";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_DEPARTMENT_NAME, null);
        put(F_DEPARTMENT_CONTENT, null);
        put(F_EMPLOYEE_NUMBER, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_REMARK, null);
        put(F_IS_ENABLE, null);
    }

    public EmpDepartmentInfo() {
        super();
    }

    public EmpDepartmentInfo(Map<String, Object> map) {
        super(map);
    }

    public EmpDepartmentInfo(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 部门表主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 部门表主键 set
     */
    public EmpDepartmentInfo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return department_name to departmentName 部门名称<BR/>
     */
    public String getDepartmentName() {
        return getTypedValue(F_DEPARTMENT_NAME, String.class);
    }
    /**
     * @param departmentName to department_name 部门名称 set
     */
    public EmpDepartmentInfo setDepartmentName(String departmentName) {
        set(F_DEPARTMENT_NAME, departmentName);
        return this;
    }
    /**
     * @return department_content to departmentContent 部门概述<BR/>
     */
    public String getDepartmentContent() {
        return getTypedValue(F_DEPARTMENT_CONTENT, String.class);
    }
    /**
     * @param departmentContent to department_content 部门概述 set
     */
    public EmpDepartmentInfo setDepartmentContent(String departmentContent) {
        set(F_DEPARTMENT_CONTENT, departmentContent);
        return this;
    }
    /**
     * @return employee_number to employeeNumber 部门人数<BR/>
     */
    public Integer getEmployeeNumber() {
        return getTypedValue(F_EMPLOYEE_NUMBER, Integer.class);
    }
    /**
     * @param employeeNumber to employee_number 部门人数 set
     */
    public EmpDepartmentInfo setEmployeeNumber(Integer employeeNumber) {
        set(F_EMPLOYEE_NUMBER, employeeNumber);
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
    public EmpDepartmentInfo setAddTime(Long addTime) {
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
    public EmpDepartmentInfo setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
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
    public EmpDepartmentInfo setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }
    /**
     * @return is_enable to isEnable 0.否1.是（是否启用）<BR/>
     */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
     * @param isEnable to is_enable 0.否1.是（是否启用） set
     */
    public EmpDepartmentInfo setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EmpDepartmentInfo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EmpDepartmentInfo me(){
        return new EmpDepartmentInfo();
    }

    private static class Mapper implements RowMapper<EmpDepartmentInfo> {

        private Supplier<EmpDepartmentInfo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EmpDepartmentInfo mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EmpDepartmentInfo bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setDepartmentName(Utils.toCast(columnsName.contains(F_DEPARTMENT_NAME) ? rs.getObject(F_DEPARTMENT_NAME) : null, String.class));
            bean.setDepartmentContent(Utils.toCast(columnsName.contains(F_DEPARTMENT_CONTENT) ? rs.getObject(F_DEPARTMENT_CONTENT) : null, String.class));
            bean.setEmployeeNumber(Utils.toCast(columnsName.contains(F_EMPLOYEE_NUMBER) ? rs.getObject(F_EMPLOYEE_NUMBER) : null, Integer.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EmpDepartmentInfo> newMapper(){
        return newMapper(EmpDepartmentInfo::new);
    }

    public RowMapper<EmpDepartmentInfo> newMapper(Supplier<EmpDepartmentInfo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EmpDepartmentInfo {
        @Override
        public abstract RowMapper<EmpDepartmentInfo> newMapper();
    }
}
