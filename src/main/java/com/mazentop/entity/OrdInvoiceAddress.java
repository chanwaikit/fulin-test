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
* Date:        14:29 2020/04/16
* Company:     美赞拓
* Version:     1.0
* Description: OrdInvoiceAddress实体
*/
@SuppressWarnings("all")
public class OrdInvoiceAddress extends BaseBean<OrdInvoiceAddress> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_invoice_address";

    /**
     * 主键
     */
    public static final String F_ID = "id";
    /**
     * 顾客编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 结算单编号
     */
    public static final String F_FK_BALANCE_THE_BOOKS_ID = "fk_balance_the_books_id";
    /**
     * 姓
     */
    public static final String F_RECEIPT_SURNAME = "receipt_surname";
    /**
     * 名
     */
    public static final String F_RECEIPT_NAME = "receipt_name";
    /**
     * 电子邮件
     */
    public static final String F_RECEIPT_EMAIL = "receipt_email";
    /**
     * 手机
     */
    public static final String F_RECEIPT_PHONE = "receipt_phone";
    /**
     * 国家
     */
    public static final String F_RECEIPT_COUNTRY = "receipt_country";
    /**
     * 省
     */
    public static final String F_RECEIPT_PROVINCE = "receipt_province";
    /**
     * 城市
     */
    public static final String F_RECEIPT_CITY = "receipt_city";
    /**
     * 街道
     */
    public static final String F_RECEIPT_SHEET = "receipt_sheet";
    /**
     * 地址
     */
    public static final String F_RECEIPT_ADDRESS = "receipt_address";
    /**
     * 邮编
     */
    public static final String F_RECEIPT_POST = "receipt_post";
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
        put(F_FK_CLIENTELE_ID, null);
        put(F_FK_BALANCE_THE_BOOKS_ID, null);
        put(F_RECEIPT_SURNAME, null);
        put(F_RECEIPT_NAME, null);
        put(F_RECEIPT_EMAIL, null);
        put(F_RECEIPT_PHONE, null);
        put(F_RECEIPT_COUNTRY, null);
        put(F_RECEIPT_PROVINCE, null);
        put(F_RECEIPT_CITY, null);
        put(F_RECEIPT_SHEET, null);
        put(F_RECEIPT_ADDRESS, null);
        put(F_RECEIPT_POST, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
    }

    public OrdInvoiceAddress() {
        super();
    }

    public OrdInvoiceAddress(Map<String, Object> map) {
        super(map);
    }

    public OrdInvoiceAddress(String id) {
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
    public OrdInvoiceAddress setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 顾客编号<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 顾客编号 set
     */
    public OrdInvoiceAddress setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return fk_balance_the_books_id to fkBalanceTheBooksId 结算单编号<BR/>
     */
    public String getFkBalanceTheBooksId() {
        return getTypedValue(F_FK_BALANCE_THE_BOOKS_ID, String.class);
    }
    /**
     * @param fkBalanceTheBooksId to fk_balance_the_books_id 结算单编号 set
     */
    public OrdInvoiceAddress setFkBalanceTheBooksId(String fkBalanceTheBooksId) {
        set(F_FK_BALANCE_THE_BOOKS_ID, fkBalanceTheBooksId);
        return this;
    }
    /**
     * @return receipt_surname to receiptSurname 姓<BR/>
     */
    public String getReceiptSurname() {
        return getTypedValue(F_RECEIPT_SURNAME, String.class);
    }
    /**
     * @param receiptSurname to receipt_surname 姓 set
     */
    public OrdInvoiceAddress setReceiptSurname(String receiptSurname) {
        set(F_RECEIPT_SURNAME, receiptSurname);
        return this;
    }
    /**
     * @return receipt_name to receiptName 名<BR/>
     */
    public String getReceiptName() {
        return getTypedValue(F_RECEIPT_NAME, String.class);
    }
    /**
     * @param receiptName to receipt_name 名 set
     */
    public OrdInvoiceAddress setReceiptName(String receiptName) {
        set(F_RECEIPT_NAME, receiptName);
        return this;
    }
    /**
     * @return receipt_email to receiptEmail 电子邮件<BR/>
     */
    public String getReceiptEmail() {
        return getTypedValue(F_RECEIPT_EMAIL, String.class);
    }
    /**
     * @param receiptEmail to receipt_email 电子邮件 set
     */
    public OrdInvoiceAddress setReceiptEmail(String receiptEmail) {
        set(F_RECEIPT_EMAIL, receiptEmail);
        return this;
    }
    /**
     * @return receipt_phone to receiptPhone 手机<BR/>
     */
    public String getReceiptPhone() {
        return getTypedValue(F_RECEIPT_PHONE, String.class);
    }
    /**
     * @param receiptPhone to receipt_phone 手机 set
     */
    public OrdInvoiceAddress setReceiptPhone(String receiptPhone) {
        set(F_RECEIPT_PHONE, receiptPhone);
        return this;
    }
    /**
     * @return receipt_country to receiptCountry 国家<BR/>
     */
    public String getReceiptCountry() {
        return getTypedValue(F_RECEIPT_COUNTRY, String.class);
    }
    /**
     * @param receiptCountry to receipt_country 国家 set
     */
    public OrdInvoiceAddress setReceiptCountry(String receiptCountry) {
        set(F_RECEIPT_COUNTRY, receiptCountry);
        return this;
    }
    /**
     * @return receipt_province to receiptProvince 省<BR/>
     */
    public String getReceiptProvince() {
        return getTypedValue(F_RECEIPT_PROVINCE, String.class);
    }
    /**
     * @param receiptProvince to receipt_province 省 set
     */
    public OrdInvoiceAddress setReceiptProvince(String receiptProvince) {
        set(F_RECEIPT_PROVINCE, receiptProvince);
        return this;
    }
    /**
     * @return receipt_city to receiptCity 城市<BR/>
     */
    public String getReceiptCity() {
        return getTypedValue(F_RECEIPT_CITY, String.class);
    }
    /**
     * @param receiptCity to receipt_city 城市 set
     */
    public OrdInvoiceAddress setReceiptCity(String receiptCity) {
        set(F_RECEIPT_CITY, receiptCity);
        return this;
    }
    /**
     * @return receipt_sheet to receiptSheet 街道<BR/>
     */
    public String getReceiptSheet() {
        return getTypedValue(F_RECEIPT_SHEET, String.class);
    }
    /**
     * @param receiptSheet to receipt_sheet 街道 set
     */
    public OrdInvoiceAddress setReceiptSheet(String receiptSheet) {
        set(F_RECEIPT_SHEET, receiptSheet);
        return this;
    }
    /**
     * @return receipt_address to receiptAddress 地址<BR/>
     */
    public String getReceiptAddress() {
        return getTypedValue(F_RECEIPT_ADDRESS, String.class);
    }
    /**
     * @param receiptAddress to receipt_address 地址 set
     */
    public OrdInvoiceAddress setReceiptAddress(String receiptAddress) {
        set(F_RECEIPT_ADDRESS, receiptAddress);
        return this;
    }
    /**
     * @return receipt_post to receiptPost 邮编<BR/>
     */
    public String getReceiptPost() {
        return getTypedValue(F_RECEIPT_POST, String.class);
    }
    /**
     * @param receiptPost to receipt_post 邮编 set
     */
    public OrdInvoiceAddress setReceiptPost(String receiptPost) {
        set(F_RECEIPT_POST, receiptPost);
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
    public OrdInvoiceAddress setRemark(String remark) {
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
    public OrdInvoiceAddress setAddTime(Long addTime) {
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
    public OrdInvoiceAddress setAddUserId(String addUserId) {
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
    public OrdInvoiceAddress setAddUserName(String addUserName) {
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
    public OrdInvoiceAddress setOperationTime(Long operationTime) {
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
    public OrdInvoiceAddress setOperationUserId(String operationUserId) {
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
    public OrdInvoiceAddress setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdInvoiceAddress setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdInvoiceAddress me(){
        return new OrdInvoiceAddress();
    }

    private static class Mapper implements RowMapper<OrdInvoiceAddress> {

        private Supplier<OrdInvoiceAddress> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdInvoiceAddress mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdInvoiceAddress bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setFkBalanceTheBooksId(Utils.toCast(rs.getObject(F_FK_BALANCE_THE_BOOKS_ID), String.class));
            bean.setReceiptSurname(Utils.toCast(rs.getObject(F_RECEIPT_SURNAME), String.class));
            bean.setReceiptName(Utils.toCast(rs.getObject(F_RECEIPT_NAME), String.class));
            bean.setReceiptEmail(Utils.toCast(rs.getObject(F_RECEIPT_EMAIL), String.class));
            bean.setReceiptPhone(Utils.toCast(rs.getObject(F_RECEIPT_PHONE), String.class));
            bean.setReceiptCountry(Utils.toCast(rs.getObject(F_RECEIPT_COUNTRY), String.class));
            bean.setReceiptProvince(Utils.toCast(rs.getObject(F_RECEIPT_PROVINCE), String.class));
            bean.setReceiptCity(Utils.toCast(rs.getObject(F_RECEIPT_CITY), String.class));
            bean.setReceiptSheet(Utils.toCast(rs.getObject(F_RECEIPT_SHEET), String.class));
            bean.setReceiptAddress(Utils.toCast(rs.getObject(F_RECEIPT_ADDRESS), String.class));
            bean.setReceiptPost(Utils.toCast(rs.getObject(F_RECEIPT_POST), String.class));
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
    public RowMapper<OrdInvoiceAddress> newMapper(){
        return newMapper(OrdInvoiceAddress::new);
    }

    public RowMapper<OrdInvoiceAddress> newMapper(Supplier<OrdInvoiceAddress> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdInvoiceAddress {
        @Override
        public abstract RowMapper<OrdInvoiceAddress> newMapper();
    }
}
