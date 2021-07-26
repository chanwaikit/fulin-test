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
* Date:        19:54 2020/04/14
* Company:     美赞拓
* Version:     1.0
* Description: OrdFreeSchemeDetails实体
*/
@SuppressWarnings("all")
public class OrdFreeSchemeDetails extends BaseBean<OrdFreeSchemeDetails> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_free_scheme_details";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 外部方案主键
    */
    public static final String F_FK_ORD_FREE_SCHEME_ID = "fk_ord_free_scheme_id";
    /**
    * 方案内部名称
    */
    public static final String F_SCHEME_INSIDE_NAME = "scheme_inside_name";
    /**
    * 方案内部说明
    */
    public static final String F_SCHEME_INSIDE_CONTENT = "scheme_inside_content";
    /**
    * 是否支持货到付款
    */
    public static final String F_IS_CASH_ON_DELIVERY = "is_cash_on_delivery";
    /**
    * 是否固定运费
    */
    public static final String F_IS_FIXED_FREE = "is_fixed_free";
    /**
    * 固定运费费用
    */
    public static final String F_FIXED_FREE_VALUE = "fixed_free_value";
    /**
    * 币制
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 是否开启首重+续重
    */
    public static final String F_IS_YKG_AND_KG = "is_ykg_and_kg";
    /**
    * 首重运费
    */
    public static final String F_YKG_FREE = "ykg_free";
    /**
    * 首重数量
    */
    public static final String F_YKG_VALUE = "ykg_value";
    /**
    * 首重单位
    */
    public static final String F_YKG_UNIT = "ykg_unit";
    /**
    * 续重运费
    */
    public static final String F_KG_FREE = "kg_free";
    /**
    * 续重数量
    */
    public static final String F_KG_VALUE = "kg_value";
    /**
    * 续重单位
    */
    public static final String F_KG_UNIT = "kg_unit";
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
    * 添加人名称
    */
    public static final String F_ADD_USER_NAME = "add_user_name";
    /**
    * 操作时间
    */
    public static final String F_OPERATION_TIME = "operation_time";
    /**
    * 操作人编号
    */
    public static final String F_OPERATION_USER_ID = "operation_user_id";
    /**
    * 操作人名称
    */
    public static final String F_OPERATION_USER_NAME = "operation_user_name";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_ORD_FREE_SCHEME_ID, null);
        put(F_SCHEME_INSIDE_NAME, null);
        put(F_SCHEME_INSIDE_CONTENT, null);
        put(F_IS_CASH_ON_DELIVERY, null);
        put(F_IS_FIXED_FREE, null);
        put(F_FIXED_FREE_VALUE, null);
        put(F_CURRENCY, null);
        put(F_IS_YKG_AND_KG, null);
        put(F_YKG_FREE, null);
        put(F_YKG_VALUE, null);
        put(F_YKG_UNIT, null);
        put(F_KG_FREE, null);
        put(F_KG_VALUE, null);
        put(F_KG_UNIT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
    }

    public OrdFreeSchemeDetails() {
        super();
    }

    public OrdFreeSchemeDetails(Map<String, Object> map) {
        super(map);
    }

    public OrdFreeSchemeDetails(String id) {
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
    public OrdFreeSchemeDetails setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return fk_ord_free_scheme_id to fkOrdFreeSchemeId 外部方案主键<BR/>
    */
    public String getFkOrdFreeSchemeId() {
        return getTypedValue(F_FK_ORD_FREE_SCHEME_ID, String.class);
    }
    /**
    * @param fkOrdFreeSchemeId to fk_ord_free_scheme_id 外部方案主键 set
    */
    public OrdFreeSchemeDetails setFkOrdFreeSchemeId(String fkOrdFreeSchemeId) {
        set(F_FK_ORD_FREE_SCHEME_ID, fkOrdFreeSchemeId);
        return this;
    }
    /**
    * @return scheme_inside_name to schemeInsideName 方案内部名称<BR/>
    */
    public String getSchemeInsideName() {
        return getTypedValue(F_SCHEME_INSIDE_NAME, String.class);
    }
    /**
    * @param schemeInsideName to scheme_inside_name 方案内部名称 set
    */
    public OrdFreeSchemeDetails setSchemeInsideName(String schemeInsideName) {
        set(F_SCHEME_INSIDE_NAME, schemeInsideName);
        return this;
    }
    /**
    * @return scheme_inside_content to schemeInsideContent 方案内部说明<BR/>
    */
    public String getSchemeInsideContent() {
        return getTypedValue(F_SCHEME_INSIDE_CONTENT, String.class);
    }
    /**
    * @param schemeInsideContent to scheme_inside_content 方案内部说明 set
    */
    public OrdFreeSchemeDetails setSchemeInsideContent(String schemeInsideContent) {
        set(F_SCHEME_INSIDE_CONTENT, schemeInsideContent);
        return this;
    }
    /**
    * @return is_cash_on_delivery to isCashOnDelivery 是否支持货到付款<BR/>
    */
    public Integer getIsCashOnDelivery() {
        return getTypedValue(F_IS_CASH_ON_DELIVERY, Integer.class);
    }
    /**
    * @param isCashOnDelivery to is_cash_on_delivery 是否支持货到付款 set
    */
    public OrdFreeSchemeDetails setIsCashOnDelivery(Integer isCashOnDelivery) {
        set(F_IS_CASH_ON_DELIVERY, isCashOnDelivery);
        return this;
    }
    /**
    * @return is_fixed_free to isFixedFree 是否固定运费<BR/>
    */
    public Integer getIsFixedFree() {
        return getTypedValue(F_IS_FIXED_FREE, Integer.class);
    }
    /**
    * @param isFixedFree to is_fixed_free 是否固定运费 set
    */
    public OrdFreeSchemeDetails setIsFixedFree(Integer isFixedFree) {
        set(F_IS_FIXED_FREE, isFixedFree);
        return this;
    }
    /**
    * @return fixed_free_value to fixedFreeValue 固定运费费用<BR/>
    */
    public Long getFixedFreeValue() {
        return getTypedValue(F_FIXED_FREE_VALUE, Long.class);
    }
    /**
    * @param fixedFreeValue to fixed_free_value 固定运费费用 set
    */
    public OrdFreeSchemeDetails setFixedFreeValue(Long fixedFreeValue) {
        set(F_FIXED_FREE_VALUE, fixedFreeValue);
        return this;
    }
    /**
    * @return currency to currency 币制<BR/>
    */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
    * @param currency to currency 币制 set
    */
    public OrdFreeSchemeDetails setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
    * @return is_ykg_and_kg to isYkgAndKg 是否开启首重+续重<BR/>
    */
    public Integer getIsYkgAndKg() {
        return getTypedValue(F_IS_YKG_AND_KG, Integer.class);
    }
    /**
    * @param isYkgAndKg to is_ykg_and_kg 是否开启首重+续重 set
    */
    public OrdFreeSchemeDetails setIsYkgAndKg(Integer isYkgAndKg) {
        set(F_IS_YKG_AND_KG, isYkgAndKg);
        return this;
    }
    /**
    * @return ykg_free to ykgFree 首重运费<BR/>
    */
    public Long getYkgFree() {
        return getTypedValue(F_YKG_FREE, Long.class);
    }
    /**
    * @param ykgFree to ykg_free 首重运费 set
    */
    public OrdFreeSchemeDetails setYkgFree(Long ykgFree) {
        set(F_YKG_FREE, ykgFree);
        return this;
    }
    /**
    * @return ykg_value to ykgValue 首重数量<BR/>
    */
    public Long getYkgValue() {
        return getTypedValue(F_YKG_VALUE, Long.class);
    }
    /**
    * @param ykgValue to ykg_value 首重数量 set
    */
    public OrdFreeSchemeDetails setYkgValue(Long ykgValue) {
        set(F_YKG_VALUE, ykgValue);
        return this;
    }
    /**
    * @return ykg_unit to ykgUnit 首重单位<BR/>
    */
    public String getYkgUnit() {
        return getTypedValue(F_YKG_UNIT, String.class);
    }
    /**
    * @param ykgUnit to ykg_unit 首重单位 set
    */
    public OrdFreeSchemeDetails setYkgUnit(String ykgUnit) {
        set(F_YKG_UNIT, ykgUnit);
        return this;
    }
    /**
    * @return kg_free to kgFree 续重运费<BR/>
    */
    public Long getKgFree() {
        return getTypedValue(F_KG_FREE, Long.class);
    }
    /**
    * @param kgFree to kg_free 续重运费 set
    */
    public OrdFreeSchemeDetails setKgFree(Long kgFree) {
        set(F_KG_FREE, kgFree);
        return this;
    }
    /**
    * @return kg_value to kgValue 续重数量<BR/>
    */
    public Long getKgValue() {
        return getTypedValue(F_KG_VALUE, Long.class);
    }
    /**
    * @param kgValue to kg_value 续重数量 set
    */
    public OrdFreeSchemeDetails setKgValue(Long kgValue) {
        set(F_KG_VALUE, kgValue);
        return this;
    }
    /**
    * @return kg_unit to kgUnit 续重单位<BR/>
    */
    public String getKgUnit() {
        return getTypedValue(F_KG_UNIT, String.class);
    }
    /**
    * @param kgUnit to kg_unit 续重单位 set
    */
    public OrdFreeSchemeDetails setKgUnit(String kgUnit) {
        set(F_KG_UNIT, kgUnit);
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
    public OrdFreeSchemeDetails setRemark(String remark) {
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
    public OrdFreeSchemeDetails setAddTime(Long addTime) {
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
    public OrdFreeSchemeDetails setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
    * @return add_user_name to addUserName 添加人名称<BR/>
    */
    public String getAddUserName() {
        return getTypedValue(F_ADD_USER_NAME, String.class);
    }
    /**
    * @param addUserName to add_user_name 添加人名称 set
    */
    public OrdFreeSchemeDetails setAddUserName(String addUserName) {
        set(F_ADD_USER_NAME, addUserName);
        return this;
    }
    /**
    * @return operation_time to operationTime 操作时间<BR/>
    */
    public Long getOperationTime() {
        return getTypedValue(F_OPERATION_TIME, Long.class);
    }
    /**
    * @param operationTime to operation_time 操作时间 set
    */
    public OrdFreeSchemeDetails setOperationTime(Long operationTime) {
        set(F_OPERATION_TIME, operationTime);
        return this;
    }
    /**
    * @return operation_user_id to operationUserId 操作人编号<BR/>
    */
    public String getOperationUserId() {
        return getTypedValue(F_OPERATION_USER_ID, String.class);
    }
    /**
    * @param operationUserId to operation_user_id 操作人编号 set
    */
    public OrdFreeSchemeDetails setOperationUserId(String operationUserId) {
        set(F_OPERATION_USER_ID, operationUserId);
        return this;
    }
    /**
    * @return operation_user_name to operationUserName 操作人名称<BR/>
    */
    public String getOperationUserName() {
        return getTypedValue(F_OPERATION_USER_NAME, String.class);
    }
    /**
    * @param operationUserName to operation_user_name 操作人名称 set
    */
    public OrdFreeSchemeDetails setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdFreeSchemeDetails setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdFreeSchemeDetails me(){
        return new OrdFreeSchemeDetails();
    }

    private static class Mapper implements RowMapper<OrdFreeSchemeDetails> {

        private Supplier<OrdFreeSchemeDetails> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdFreeSchemeDetails mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdFreeSchemeDetails bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkOrdFreeSchemeId(Utils.toCast(rs.getObject(F_FK_ORD_FREE_SCHEME_ID), String.class));
            bean.setSchemeInsideName(Utils.toCast(rs.getObject(F_SCHEME_INSIDE_NAME), String.class));
            bean.setSchemeInsideContent(Utils.toCast(rs.getObject(F_SCHEME_INSIDE_CONTENT), String.class));
            bean.setIsCashOnDelivery(Utils.toCast(rs.getObject(F_IS_CASH_ON_DELIVERY), Integer.class));
            bean.setIsFixedFree(Utils.toCast(rs.getObject(F_IS_FIXED_FREE), Integer.class));
            bean.setFixedFreeValue(Utils.toCast(rs.getObject(F_FIXED_FREE_VALUE), Long.class));
            bean.setCurrency(Utils.toCast(rs.getObject(F_CURRENCY), String.class));
            bean.setIsYkgAndKg(Utils.toCast(rs.getObject(F_IS_YKG_AND_KG), Integer.class));
            bean.setYkgFree(Utils.toCast(rs.getObject(F_YKG_FREE), Long.class));
            bean.setYkgValue(Utils.toCast(rs.getObject(F_YKG_VALUE), Long.class));
            bean.setYkgUnit(Utils.toCast(rs.getObject(F_YKG_UNIT), String.class));
            bean.setKgFree(Utils.toCast(rs.getObject(F_KG_FREE), Long.class));
            bean.setKgValue(Utils.toCast(rs.getObject(F_KG_VALUE), Long.class));
            bean.setKgUnit(Utils.toCast(rs.getObject(F_KG_UNIT), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdFreeSchemeDetails> newMapper(){
        return newMapper(OrdFreeSchemeDetails::new);
    }

    public RowMapper<OrdFreeSchemeDetails> newMapper(Supplier<OrdFreeSchemeDetails> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdFreeSchemeDetails {
        @Override
        public abstract RowMapper<OrdFreeSchemeDetails> newMapper();
    }
}
