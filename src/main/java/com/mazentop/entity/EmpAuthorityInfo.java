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
* Date:        10:14 2020/03/11
* Company:     美赞拓
* Version:     1.0
* Description: EmpAuthorityInfo实体
*/
@SuppressWarnings("all")
public class EmpAuthorityInfo extends BaseBean<EmpAuthorityInfo> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "emp_authority_info";

    /**
     * 权限表主键
     */
    public static final String F_ID = "id";
    /**
     * 权限英文名称
     */
    public static final String F_AUTHORITY_EN_NAME = "authority_en_name";
    /**
     * 权限中文名称
     */
    public static final String F_AUTHORITY_ZH_NAME = "authority_zh_name";
    /**
     * 权限标识
     */
    public static final String F_CODE = "code";
    /**
     *
     */
    public static final String F_REMARK = "remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_AUTHORITY_EN_NAME, null);
        put(F_AUTHORITY_ZH_NAME, null);
        put(F_CODE, null);
        put(F_REMARK, null);
    }

    public EmpAuthorityInfo() {
        super();
    }

    public EmpAuthorityInfo(Map<String, Object> map) {
        super(map);
    }

    public EmpAuthorityInfo(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id 权限表主键<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id 权限表主键 set
     */
    public EmpAuthorityInfo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return authority_en_name to authorityEnName 权限英文名称<BR/>
     */
    public String getAuthorityEnName() {
        return getTypedValue(F_AUTHORITY_EN_NAME, String.class);
    }
    /**
     * @param authorityEnName to authority_en_name 权限英文名称 set
     */
    public EmpAuthorityInfo setAuthorityEnName(String authorityEnName) {
        set(F_AUTHORITY_EN_NAME, authorityEnName);
        return this;
    }
    /**
     * @return authority_zh_name to authorityZhName 权限中文名称<BR/>
     */
    public String getAuthorityZhName() {
        return getTypedValue(F_AUTHORITY_ZH_NAME, String.class);
    }
    /**
     * @param authorityZhName to authority_zh_name 权限中文名称 set
     */
    public EmpAuthorityInfo setAuthorityZhName(String authorityZhName) {
        set(F_AUTHORITY_ZH_NAME, authorityZhName);
        return this;
    }
    /**
     * @return code to code 权限标识<BR/>
     */
    public String getCode() {
        return getTypedValue(F_CODE, String.class);
    }
    /**
     * @param code to code 权限标识 set
     */
    public EmpAuthorityInfo setCode(String code) {
        set(F_CODE, code);
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
    public EmpAuthorityInfo setRemark(String remark) {
        set(F_REMARK, remark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EmpAuthorityInfo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EmpAuthorityInfo me(){
        return new EmpAuthorityInfo();
    }

    private static class Mapper implements RowMapper<EmpAuthorityInfo> {

        private Supplier<EmpAuthorityInfo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EmpAuthorityInfo mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EmpAuthorityInfo bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setAuthorityEnName(Utils.toCast(columnsName.contains(F_AUTHORITY_EN_NAME) ? rs.getObject(F_AUTHORITY_EN_NAME) : null, String.class));
            bean.setAuthorityZhName(Utils.toCast(columnsName.contains(F_AUTHORITY_ZH_NAME) ? rs.getObject(F_AUTHORITY_ZH_NAME) : null, String.class));
            bean.setCode(Utils.toCast(columnsName.contains(F_CODE) ? rs.getObject(F_CODE) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EmpAuthorityInfo> newMapper(){
        return newMapper(EmpAuthorityInfo::new);
    }

    public RowMapper<EmpAuthorityInfo> newMapper(Supplier<EmpAuthorityInfo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EmpAuthorityInfo {
        @Override
        public abstract RowMapper<EmpAuthorityInfo> newMapper();
    }
}
