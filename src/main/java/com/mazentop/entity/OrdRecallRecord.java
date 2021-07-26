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
* Date:        16:45 2020/04/13
* Company:     美赞拓
* Version:     1.0
* Description: OrdRecallRecord实体
*/
@SuppressWarnings("all")
public class OrdRecallRecord extends BaseBean<OrdRecallRecord> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "ord_recall_record";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 公司编号
     */
    public static final String F_COMPANY_ID = "company_id";
    /**
     * 公司名称
     */
    public static final String F_COMPANY_NAME = "company_name";
    /**
     * 下单客户编号
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * 下单客户名称
     */
    public static final String F_CLIENT_NAME = "client_name";
    /**
     * 下单客户电子邮件
     */
    public static final String F_CLIENT_EMAIL = "client_email";
    /**
     * 下单客户联系电话
     */
    public static final String F_CLIENT_PHONE = "client_phone";
    /**
     * 收货人名称
     */
    public static final String F_RECEIVER_NAME = "receiver_name";
    /**
     * 收货人编号
     */
    public static final String F_RECEIVER_ID = "receiver_id";
    /**
     * 收货地址编号
     */
    public static final String F_FK_CLIENTELE_ADDRESS_ID = "fk_clientele_address_id";
    /**
     * 收货人电话
     */
    public static final String F_RECEIVER_PHONE = "receiver_phone";
    /**
     * 收货人电子邮件
     */
    public static final String F_RECEIVER_EMAIL = "receiver_email";
    /**
     * 收货人国家
     */
    public static final String F_RECEIVER_COUNTRY = "receiver_country";
    /**
     * 收货人省
     */
    public static final String F_RECEIVER_PROVINCE = "receiver_province";
    /**
     * 收货人城市
     */
    public static final String F_RECEIVER_CITY = "receiver_city";
    /**
     * 收货人街道
     */
    public static final String F_RECEIVER_SHEET = "receiver_sheet";
    /**
     * 收货人详细地址
     */
    public static final String F_RECEIVER_ADDRESS = "receiver_address";
    /**
     * 商品编号
     */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
     * 商品名称
     */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
     * 商品预览图
     */
    public static final String F_DEFAULT_IMAGE_URL = "default_image_url";
    /**
     * 邮件是否已发送
     */
    public static final String F_IS_SEND_SUCCESS = "is_send_success";
    /**
     * 召回状态
     */
    public static final String F_RECALL_STATUS = "recall_status";
    /**
     * 发送记录Json
     */
    public static final String F_SEND_RECORD_JSON = "send_record_json";
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
    /**
     * 结算单编号
     */
    public static final String F_FK_BALANCE_THE_BOOKS_ID = "fk_balance_the_books_id";
    /**
     * 下单时间
     */
    public static final String F_FK_BALANCE_THE_BOOKS_TIME = "fk_balance_the_books_time";
    /**
     *
     */
    public static final String F_CAT_LIST = "cat_list";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_CLIENT_EMAIL, null);
        put(F_CLIENT_PHONE, null);
        put(F_RECEIVER_NAME, null);
        put(F_RECEIVER_ID, null);
        put(F_FK_CLIENTELE_ADDRESS_ID, null);
        put(F_RECEIVER_PHONE, null);
        put(F_RECEIVER_EMAIL, null);
        put(F_RECEIVER_COUNTRY, null);
        put(F_RECEIVER_PROVINCE, null);
        put(F_RECEIVER_CITY, null);
        put(F_RECEIVER_SHEET, null);
        put(F_RECEIVER_ADDRESS, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_PRODUCT_NAME, null);
        put(F_DEFAULT_IMAGE_URL, null);
        put(F_IS_SEND_SUCCESS, null);
        put(F_RECALL_STATUS, null);
        put(F_SEND_RECORD_JSON, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_FK_BALANCE_THE_BOOKS_ID, null);
        put(F_FK_BALANCE_THE_BOOKS_TIME, null);
        put(F_CAT_LIST, null);
    }

    public OrdRecallRecord() {
        super();
    }

    public OrdRecallRecord(Map<String, Object> map) {
        super(map);
    }

    public OrdRecallRecord(String id) {
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
    public OrdRecallRecord setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return company_id to companyId 公司编号<BR/>
     */
    public String getCompanyId() {
        return getTypedValue(F_COMPANY_ID, String.class);
    }
    /**
     * @param companyId to company_id 公司编号 set
     */
    public OrdRecallRecord setCompanyId(String companyId) {
        set(F_COMPANY_ID, companyId);
        return this;
    }
    /**
     * @return company_name to companyName 公司名称<BR/>
     */
    public String getCompanyName() {
        return getTypedValue(F_COMPANY_NAME, String.class);
    }
    /**
     * @param companyName to company_name 公司名称 set
     */
    public OrdRecallRecord setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 下单客户编号<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 下单客户编号 set
     */
    public OrdRecallRecord setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return client_name to clientName 下单客户名称<BR/>
     */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
     * @param clientName to client_name 下单客户名称 set
     */
    public OrdRecallRecord setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
     * @return client_email to clientEmail 下单客户电子邮件<BR/>
     */
    public String getClientEmail() {
        return getTypedValue(F_CLIENT_EMAIL, String.class);
    }
    /**
     * @param clientEmail to client_email 下单客户电子邮件 set
     */
    public OrdRecallRecord setClientEmail(String clientEmail) {
        set(F_CLIENT_EMAIL, clientEmail);
        return this;
    }
    /**
     * @return client_phone to clientPhone 下单客户联系电话<BR/>
     */
    public String getClientPhone() {
        return getTypedValue(F_CLIENT_PHONE, String.class);
    }
    /**
     * @param clientPhone to client_phone 下单客户联系电话 set
     */
    public OrdRecallRecord setClientPhone(String clientPhone) {
        set(F_CLIENT_PHONE, clientPhone);
        return this;
    }
    /**
     * @return receiver_name to receiverName 收货人名称<BR/>
     */
    public String getReceiverName() {
        return getTypedValue(F_RECEIVER_NAME, String.class);
    }
    /**
     * @param receiverName to receiver_name 收货人名称 set
     */
    public OrdRecallRecord setReceiverName(String receiverName) {
        set(F_RECEIVER_NAME, receiverName);
        return this;
    }
    /**
     * @return receiver_id to receiverId 收货人编号<BR/>
     */
    public String getReceiverId() {
        return getTypedValue(F_RECEIVER_ID, String.class);
    }
    /**
     * @param receiverId to receiver_id 收货人编号 set
     */
    public OrdRecallRecord setReceiverId(String receiverId) {
        set(F_RECEIVER_ID, receiverId);
        return this;
    }
    /**
     * @return fk_clientele_address_id to fkClienteleAddressId 收货地址编号<BR/>
     */
    public String getFkClienteleAddressId() {
        return getTypedValue(F_FK_CLIENTELE_ADDRESS_ID, String.class);
    }
    /**
     * @param fkClienteleAddressId to fk_clientele_address_id 收货地址编号 set
     */
    public OrdRecallRecord setFkClienteleAddressId(String fkClienteleAddressId) {
        set(F_FK_CLIENTELE_ADDRESS_ID, fkClienteleAddressId);
        return this;
    }
    /**
     * @return receiver_phone to receiverPhone 收货人电话<BR/>
     */
    public String getReceiverPhone() {
        return getTypedValue(F_RECEIVER_PHONE, String.class);
    }
    /**
     * @param receiverPhone to receiver_phone 收货人电话 set
     */
    public OrdRecallRecord setReceiverPhone(String receiverPhone) {
        set(F_RECEIVER_PHONE, receiverPhone);
        return this;
    }
    /**
     * @return receiver_email to receiverEmail 收货人电子邮件<BR/>
     */
    public String getReceiverEmail() {
        return getTypedValue(F_RECEIVER_EMAIL, String.class);
    }
    /**
     * @param receiverEmail to receiver_email 收货人电子邮件 set
     */
    public OrdRecallRecord setReceiverEmail(String receiverEmail) {
        set(F_RECEIVER_EMAIL, receiverEmail);
        return this;
    }
    /**
     * @return receiver_country to receiverCountry 收货人国家<BR/>
     */
    public String getReceiverCountry() {
        return getTypedValue(F_RECEIVER_COUNTRY, String.class);
    }
    /**
     * @param receiverCountry to receiver_country 收货人国家 set
     */
    public OrdRecallRecord setReceiverCountry(String receiverCountry) {
        set(F_RECEIVER_COUNTRY, receiverCountry);
        return this;
    }
    /**
     * @return receiver_province to receiverProvince 收货人省<BR/>
     */
    public String getReceiverProvince() {
        return getTypedValue(F_RECEIVER_PROVINCE, String.class);
    }
    /**
     * @param receiverProvince to receiver_province 收货人省 set
     */
    public OrdRecallRecord setReceiverProvince(String receiverProvince) {
        set(F_RECEIVER_PROVINCE, receiverProvince);
        return this;
    }
    /**
     * @return receiver_city to receiverCity 收货人城市<BR/>
     */
    public String getReceiverCity() {
        return getTypedValue(F_RECEIVER_CITY, String.class);
    }
    /**
     * @param receiverCity to receiver_city 收货人城市 set
     */
    public OrdRecallRecord setReceiverCity(String receiverCity) {
        set(F_RECEIVER_CITY, receiverCity);
        return this;
    }
    /**
     * @return receiver_sheet to receiverSheet 收货人街道<BR/>
     */
    public String getReceiverSheet() {
        return getTypedValue(F_RECEIVER_SHEET, String.class);
    }
    /**
     * @param receiverSheet to receiver_sheet 收货人街道 set
     */
    public OrdRecallRecord setReceiverSheet(String receiverSheet) {
        set(F_RECEIVER_SHEET, receiverSheet);
        return this;
    }
    /**
     * @return receiver_address to receiverAddress 收货人详细地址<BR/>
     */
    public String getReceiverAddress() {
        return getTypedValue(F_RECEIVER_ADDRESS, String.class);
    }
    /**
     * @param receiverAddress to receiver_address 收货人详细地址 set
     */
    public OrdRecallRecord setReceiverAddress(String receiverAddress) {
        set(F_RECEIVER_ADDRESS, receiverAddress);
        return this;
    }
    /**
     * @return fk_product_id to fkProductId 商品编号<BR/>
     */
    public String getFkProductId() {
        return getTypedValue(F_FK_PRODUCT_ID, String.class);
    }
    /**
     * @param fkProductId to fk_product_id 商品编号 set
     */
    public OrdRecallRecord setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
        return this;
    }
    /**
     * @return product_name to productName 商品名称<BR/>
     */
    public String getProductName() {
        return getTypedValue(F_PRODUCT_NAME, String.class);
    }
    /**
     * @param productName to product_name 商品名称 set
     */
    public OrdRecallRecord setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
        return this;
    }
    /**
     * @return default_image_url to defaultImageUrl 商品预览图<BR/>
     */
    public String getDefaultImageUrl() {
        return getTypedValue(F_DEFAULT_IMAGE_URL, String.class);
    }
    /**
     * @param defaultImageUrl to default_image_url 商品预览图 set
     */
    public OrdRecallRecord setDefaultImageUrl(String defaultImageUrl) {
        set(F_DEFAULT_IMAGE_URL, defaultImageUrl);
        return this;
    }
    /**
     * @return is_send_success to isSendSuccess 邮件是否已发送<BR/>
     */
    public Integer getIsSendSuccess() {
        return getTypedValue(F_IS_SEND_SUCCESS, Integer.class);
    }
    /**
     * @param isSendSuccess to is_send_success 邮件是否已发送 set
     */
    public OrdRecallRecord setIsSendSuccess(Integer isSendSuccess) {
        set(F_IS_SEND_SUCCESS, isSendSuccess);
        return this;
    }
    /**
     * @return recall_status to recallStatus 召回状态<BR/>
     */
    public String getRecallStatus() {
        return getTypedValue(F_RECALL_STATUS, String.class);
    }
    /**
     * @param recallStatus to recall_status 召回状态 set
     */
    public OrdRecallRecord setRecallStatus(String recallStatus) {
        set(F_RECALL_STATUS, recallStatus);
        return this;
    }
    /**
     * @return send_record_json to sendRecordJson 发送记录Json<BR/>
     */
    public String getSendRecordJson() {
        return getTypedValue(F_SEND_RECORD_JSON, String.class);
    }
    /**
     * @param sendRecordJson to send_record_json 发送记录Json set
     */
    public OrdRecallRecord setSendRecordJson(String sendRecordJson) {
        set(F_SEND_RECORD_JSON, sendRecordJson);
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
    public OrdRecallRecord setRemark(String remark) {
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
    public OrdRecallRecord setAddTime(Long addTime) {
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
    public OrdRecallRecord setAddUserId(String addUserId) {
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
    public OrdRecallRecord setAddUserName(String addUserName) {
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
    public OrdRecallRecord setOperationTime(Long operationTime) {
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
    public OrdRecallRecord setOperationUserId(String operationUserId) {
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
    public OrdRecallRecord setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
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
    public OrdRecallRecord setFkBalanceTheBooksId(String fkBalanceTheBooksId) {
        set(F_FK_BALANCE_THE_BOOKS_ID, fkBalanceTheBooksId);
        return this;
    }
    /**
     * @return fk_balance_the_books_time to fkBalanceTheBooksTime 下单时间<BR/>
     */
    public Long getFkBalanceTheBooksTime() {
        return getTypedValue(F_FK_BALANCE_THE_BOOKS_TIME, Long.class);
    }
    /**
     * @param fkBalanceTheBooksTime to fk_balance_the_books_time 下单时间 set
     */
    public OrdRecallRecord setFkBalanceTheBooksTime(Long fkBalanceTheBooksTime) {
        set(F_FK_BALANCE_THE_BOOKS_TIME, fkBalanceTheBooksTime);
        return this;
    }
    /**
     * @return cat_list to catList <BR/>
     */
    public String getCatList() {
        return getTypedValue(F_CAT_LIST, String.class);
    }
    /**
     * @param catList to cat_list  set
     */
    public OrdRecallRecord setCatList(String catList) {
        set(F_CAT_LIST, catList);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdRecallRecord setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdRecallRecord me(){
        return new OrdRecallRecord();
    }

    private static class Mapper implements RowMapper<OrdRecallRecord> {

        private Supplier<OrdRecallRecord> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdRecallRecord mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdRecallRecord bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setClientName(Utils.toCast(rs.getObject(F_CLIENT_NAME), String.class));
            bean.setClientEmail(Utils.toCast(rs.getObject(F_CLIENT_EMAIL), String.class));
            bean.setClientPhone(Utils.toCast(rs.getObject(F_CLIENT_PHONE), String.class));
            bean.setReceiverName(Utils.toCast(rs.getObject(F_RECEIVER_NAME), String.class));
            bean.setReceiverId(Utils.toCast(rs.getObject(F_RECEIVER_ID), String.class));
            bean.setFkClienteleAddressId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ADDRESS_ID), String.class));
            bean.setReceiverPhone(Utils.toCast(rs.getObject(F_RECEIVER_PHONE), String.class));
            bean.setReceiverEmail(Utils.toCast(rs.getObject(F_RECEIVER_EMAIL), String.class));
            bean.setReceiverCountry(Utils.toCast(rs.getObject(F_RECEIVER_COUNTRY), String.class));
            bean.setReceiverProvince(Utils.toCast(rs.getObject(F_RECEIVER_PROVINCE), String.class));
            bean.setReceiverCity(Utils.toCast(rs.getObject(F_RECEIVER_CITY), String.class));
            bean.setReceiverSheet(Utils.toCast(rs.getObject(F_RECEIVER_SHEET), String.class));
            bean.setReceiverAddress(Utils.toCast(rs.getObject(F_RECEIVER_ADDRESS), String.class));
            bean.setFkProductId(Utils.toCast(rs.getObject(F_FK_PRODUCT_ID), String.class));
            bean.setProductName(Utils.toCast(rs.getObject(F_PRODUCT_NAME), String.class));
            bean.setDefaultImageUrl(Utils.toCast(rs.getObject(F_DEFAULT_IMAGE_URL), String.class));
            bean.setIsSendSuccess(Utils.toCast(rs.getObject(F_IS_SEND_SUCCESS), Integer.class));
            bean.setRecallStatus(Utils.toCast(rs.getObject(F_RECALL_STATUS), String.class));
            bean.setSendRecordJson(Utils.toCast(rs.getObject(F_SEND_RECORD_JSON), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.setFkBalanceTheBooksId(Utils.toCast(rs.getObject(F_FK_BALANCE_THE_BOOKS_ID), String.class));
            bean.setFkBalanceTheBooksTime(Utils.toCast(rs.getObject(F_FK_BALANCE_THE_BOOKS_TIME), Long.class));
            bean.setCatList(Utils.toCast(rs.getObject(F_CAT_LIST), String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdRecallRecord> newMapper(){
        return newMapper(OrdRecallRecord::new);
    }

    public RowMapper<OrdRecallRecord> newMapper(Supplier<OrdRecallRecord> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdRecallRecord {
        @Override
        public abstract RowMapper<OrdRecallRecord> newMapper();
    }
}
