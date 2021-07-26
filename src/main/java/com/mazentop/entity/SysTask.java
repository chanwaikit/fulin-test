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
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        09:26 2020/04/08
* Company:     美赞拓
* Version:     1.0
* Description: SysTask实体
*/
@SuppressWarnings("all")
public class SysTask extends BaseBean<SysTask> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_task";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 任务名
    */
    public static final String F_JOB_NAME = "job_name";
    /**
    * 任务描述
    */
    public static final String F_DESCRIPTION = "description";
    /**
    * cron表达式
    */
    public static final String F_CRON_EXPRESSION = "cron_expression";
    /**
    * 任务执行时调用哪个类的方法 包名+类名
    */
    public static final String F_BEAN_CLASS = "bean_class";
    /**
    * 任务状态
    */
    public static final String F_JOB_STATUS = "job_status";
    /**
    * 任务分组
    */
    public static final String F_JOB_GROUP = "job_group";
    /**
    * 创建者
    */
    public static final String F_ADD_USER = "add_user";
    /**
    * 创建时间
    */
    public static final String F_ADD_TIME = "add_time";
    /**
    * 更新者
    */
    public static final String F_UPDATE_USER = "update_user";
    /**
    * 更新时间
    */
    public static final String F_UPDATE_TIME = "update_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_JOB_NAME, null);
        put(F_DESCRIPTION, null);
        put(F_CRON_EXPRESSION, null);
        put(F_BEAN_CLASS, null);
        put(F_JOB_STATUS, null);
        put(F_JOB_GROUP, null);
        put(F_ADD_USER, null);
        put(F_ADD_TIME, null);
        put(F_UPDATE_USER, null);
        put(F_UPDATE_TIME, null);
    }

    public SysTask() {
        super();
    }

    public SysTask(Map<String, Object> map) {
        super(map);
    }

    public SysTask(String id) {
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
    public SysTask setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return job_name to jobName 任务名<BR/>
    */
    public String getJobName() {
        return getTypedValue(F_JOB_NAME, String.class);
    }
    /**
    * @param jobName to job_name 任务名 set
    */
    public SysTask setJobName(String jobName) {
        set(F_JOB_NAME, jobName);
        return this;
    }
    /**
    * @return description to description 任务描述<BR/>
    */
    public String getDescription() {
        return getTypedValue(F_DESCRIPTION, String.class);
    }
    /**
    * @param description to description 任务描述 set
    */
    public SysTask setDescription(String description) {
        set(F_DESCRIPTION, description);
        return this;
    }
    /**
    * @return cron_expression to cronExpression cron表达式<BR/>
    */
    public String getCronExpression() {
        return getTypedValue(F_CRON_EXPRESSION, String.class);
    }
    /**
    * @param cronExpression to cron_expression cron表达式 set
    */
    public SysTask setCronExpression(String cronExpression) {
        set(F_CRON_EXPRESSION, cronExpression);
        return this;
    }
    /**
    * @return bean_class to beanClass 任务执行时调用哪个类的方法 包名+类名<BR/>
    */
    public String getBeanClass() {
        return getTypedValue(F_BEAN_CLASS, String.class);
    }
    /**
    * @param beanClass to bean_class 任务执行时调用哪个类的方法 包名+类名 set
    */
    public SysTask setBeanClass(String beanClass) {
        set(F_BEAN_CLASS, beanClass);
        return this;
    }
    /**
    * @return job_status to jobStatus 任务状态<BR/>
    */
    public String getJobStatus() {
        return getTypedValue(F_JOB_STATUS, String.class);
    }
    /**
    * @param jobStatus to job_status 任务状态 set
    */
    public SysTask setJobStatus(String jobStatus) {
        set(F_JOB_STATUS, jobStatus);
        return this;
    }
    /**
    * @return job_group to jobGroup 任务分组<BR/>
    */
    public String getJobGroup() {
        return getTypedValue(F_JOB_GROUP, String.class);
    }
    /**
    * @param jobGroup to job_group 任务分组 set
    */
    public SysTask setJobGroup(String jobGroup) {
        set(F_JOB_GROUP, jobGroup);
        return this;
    }
    /**
    * @return add_user to addUser 创建者<BR/>
    */
    public String getAddUser() {
        return getTypedValue(F_ADD_USER, String.class);
    }
    /**
    * @param addUser to add_user 创建者 set
    */
    public SysTask setAddUser(String addUser) {
        set(F_ADD_USER, addUser);
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
    public SysTask setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }
    /**
    * @return update_user to updateUser 更新者<BR/>
    */
    public String getUpdateUser() {
        return getTypedValue(F_UPDATE_USER, String.class);
    }
    /**
    * @param updateUser to update_user 更新者 set
    */
    public SysTask setUpdateUser(String updateUser) {
        set(F_UPDATE_USER, updateUser);
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
    public SysTask setUpdateTime(Long updateTime) {
        set(F_UPDATE_TIME, updateTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysTask setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysTask me(){
        return new SysTask();
    }

    private static class Mapper implements RowMapper<SysTask> {

        private Supplier<SysTask> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysTask mapRow(ResultSet rs, int rownum) throws SQLException {
            SysTask bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setJobName(Utils.toCast(rs.getObject(F_JOB_NAME), String.class));
            bean.setDescription(Utils.toCast(rs.getObject(F_DESCRIPTION), String.class));
            bean.setCronExpression(Utils.toCast(rs.getObject(F_CRON_EXPRESSION), String.class));
            bean.setBeanClass(Utils.toCast(rs.getObject(F_BEAN_CLASS), String.class));
            bean.setJobStatus(Utils.toCast(rs.getObject(F_JOB_STATUS), String.class));
            bean.setJobGroup(Utils.toCast(rs.getObject(F_JOB_GROUP), String.class));
            bean.setAddUser(Utils.toCast(rs.getObject(F_ADD_USER), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setUpdateUser(Utils.toCast(rs.getObject(F_UPDATE_USER), String.class));
            bean.setUpdateTime(Utils.toCast(rs.getObject(F_UPDATE_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysTask> newMapper(){
        return newMapper(SysTask::new);
    }

    public RowMapper<SysTask> newMapper(Supplier<SysTask> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysTask {
        @Override
        public abstract RowMapper<SysTask> newMapper();
    }
}
