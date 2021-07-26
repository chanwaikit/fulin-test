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
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        16:08 2020/03/25
* Company:     美赞拓
* Version:     1.0
* Description: EmpEmployeeInfo实体
*/
@SuppressWarnings("all")
public class EmpEmployeeInfo extends BaseBean<EmpEmployeeInfo> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "emp_employee_info";

    /**
     * 员工表主键
     */
    public static final String F_ID = "id";
    /**
     * 员工姓名
     */
    public static final String F_EMPLOYEE_NAME = "employee_name";
    /**
     * 部门编号
     */
    public static final String F_FK_DEPARTMENT_ID = "fk_department_id";
    /**
     * 部门名称
     */
    public static final String F_DEPARTMENT_NAME = "department_name";
    /**
     * 登录名称
     */
    public static final String F_LOGIN_NAME = "login_name";
    /**
     * 登录密码
     */
    public static final String F_PASSWORD = "password";
    /**
     * 0.否1.是（是否启用）
     */
    public static final String F_IS_ENABLE = "is_enable";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
     *
     */
    public static final String F_REMARK = "remark";
    /**
     * 修改密碼时间
     */
    public static final String F_UPDATE_PWD_TIME = "update_pwd_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_EMPLOYEE_NAME, null);
        put(F_FK_DEPARTMENT_ID, null);
        put(F_DEPARTMENT_NAME, null);
        put(F_LOGIN_NAME, null);
        put(F_PASSWORD, null);
        put(F_IS_ENABLE, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_REMARK, null);
        put(F_UPDATE_PWD_TIME, null);
    }

    public EmpEmployeeInfo() {
        super();
    }

    public EmpEmployeeInfo(Map<String, Object> map) {
        super(map);
    }

    public EmpEmployeeInfo(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 员工表主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 员工表主键 set
     */
    public EmpEmployeeInfo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return employee_name to employeeName 员工姓名<BR/>
     */
    public String getEmployeeName() {
        return getTypedValue(F_EMPLOYEE_NAME, String.class);
    }
    /**
     * @param employeeName to employee_name 员工姓名 set
     */
    public EmpEmployeeInfo setEmployeeName(String employeeName) {
        set(F_EMPLOYEE_NAME, employeeName);
        return this;
    }
    /**
     * @return fk_department_id to fkDepartmentId 部门编号<BR/>
     */
    public String getFkDepartmentId() {
        return getTypedValue(F_FK_DEPARTMENT_ID, String.class);
    }
    /**
     * @param fkDepartmentId to fk_department_id 部门编号 set
     */
    public EmpEmployeeInfo setFkDepartmentId(String fkDepartmentId) {
        set(F_FK_DEPARTMENT_ID, fkDepartmentId);
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
    public EmpEmployeeInfo setDepartmentName(String departmentName) {
        set(F_DEPARTMENT_NAME, departmentName);
        return this;
    }
    /**
     * @return login_name to loginName 登录名称<BR/>
     */
    public String getLoginName() {
        return getTypedValue(F_LOGIN_NAME, String.class);
    }
    /**
     * @param loginName to login_name 登录名称 set
     */
    public EmpEmployeeInfo setLoginName(String loginName) {
        set(F_LOGIN_NAME, loginName);
        return this;
    }
    /**
     * @return password to password 登录密码<BR/>
     */
    public String getPassword() {
        return getTypedValue(F_PASSWORD, String.class);
    }
    /**
     * @param password to password 登录密码 set
     */
    public EmpEmployeeInfo setPassword(String password) {
        set(F_PASSWORD, password);
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
    public EmpEmployeeInfo setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
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
    public EmpEmployeeInfo setAddTime(Long addTime) {
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
    public EmpEmployeeInfo setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
     * @return remark to remark <BR/>
     */
    public String getRemark() {
        return getTypedValue(F_REMARK, String.class);
    }
    /**
     * @param remark to remark  set
     */
    public EmpEmployeeInfo setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }
    /**
     * @return update_pwd_time to updatePwdTime 修改密碼时间<BR/>
     */
    public Long getUpdatePwdTime() {
        return getTypedValue(F_UPDATE_PWD_TIME, Long.class);
    }
    /**
     * @param updatePwdTime to update_pwd_time 修改密碼时间 set
     */
    public EmpEmployeeInfo setUpdatePwdTime(Long updatePwdTime) {
        set(F_UPDATE_PWD_TIME, updatePwdTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EmpEmployeeInfo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EmpEmployeeInfo me(){
        return new EmpEmployeeInfo();
    }

    private static class Mapper implements RowMapper<EmpEmployeeInfo> {

        private Supplier<EmpEmployeeInfo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EmpEmployeeInfo mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EmpEmployeeInfo bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setEmployeeName(Utils.toCast(columnsName.contains(F_EMPLOYEE_NAME) ? rs.getObject(F_EMPLOYEE_NAME) : null, String.class));
            bean.setFkDepartmentId(Utils.toCast(columnsName.contains(F_FK_DEPARTMENT_ID) ? rs.getObject(F_FK_DEPARTMENT_ID) : null, String.class));
            bean.setDepartmentName(Utils.toCast(columnsName.contains(F_DEPARTMENT_NAME) ? rs.getObject(F_DEPARTMENT_NAME) : null, String.class));
            bean.setLoginName(Utils.toCast(columnsName.contains(F_LOGIN_NAME) ? rs.getObject(F_LOGIN_NAME) : null, String.class));
            bean.setPassword(Utils.toCast(columnsName.contains(F_PASSWORD) ? rs.getObject(F_PASSWORD) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setUpdatePwdTime(Utils.toCast(columnsName.contains(F_UPDATE_PWD_TIME) ? rs.getObject(F_UPDATE_PWD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EmpEmployeeInfo> newMapper(){
        return newMapper(EmpEmployeeInfo::new);
    }

    public RowMapper<EmpEmployeeInfo> newMapper(Supplier<EmpEmployeeInfo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EmpEmployeeInfo {
        @Override
        public abstract RowMapper<EmpEmployeeInfo> newMapper();
    }
}
