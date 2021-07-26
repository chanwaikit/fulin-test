package com.mazentop.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Supplier;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        17:47 2020/06/06
* Company:     美赞拓
* Version:     1.0
* Description: OrdRefundRequest实体
*/
@SuppressWarnings("all")
public class OrdRefundRequest extends BaseBean<OrdRefundRequest> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_refund_request";

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
    * 销售单编号
    */
    public static final String F_FK_SALES_ORDER_ID = "fk_sales_order_id";
    /**
    * 客户编号
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 客户名称
    */
    public static final String F_CLIENT_NAME = "client_name";
    /**
    * 客户电子邮件
    */
    public static final String F_CLIENT_EMAIL = "client_email";
    /**
    * 客户联系电话
    */
    public static final String F_CLIENT_PHONE = "client_phone";
    /**
    * 退款单流水号
    */
    public static final String F_REFUND_STREAM_NO = "refund_stream_no";
    /**
    * 退款单截图地址
    */
    public static final String F_REFUND_STREAM_IMAGE_URL = "refund_stream_image_url";
    /**
    * 同意退款说明
    */
    public static final String F_REFUND_AGREE_CONTENT = "refund_agree_content";
    /**
    * 币种
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 退款金额
    */
    public static final String F_REFUND_AMOUNT = "refund_amount";
    /**
    * 退款卡号
    */
    public static final String F_REFUND_CARD_NO = "refund_card_no";
    /**
    * 退款申请说明
    */
    public static final String F_REFUND_CONTENT = "refund_content";
    /**
    * 退款理由
    */
    public static final String F_REFUND_REASON = "refund_reason";
    /**
    * 审核人
    */
    public static final String F_AUDITER = "auditer";
    /**
    * 审核时间
    */
    public static final String F_AUDIT_TIME = "audit_time";
    /**
    * 退款申请单状态
    */
    public static final String F_REFUND_REQUEST_STATUS = "refund_request_status";
    /**
    * 拒绝说明
    */
    public static final String F_REJECT_CONTENT = "reject_content";
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
    * 销售单号
    */
    public static final String F_SALES_ORDER_NO = "sales_order_no";
    /**
    * 
    */
    public static final String F_LOCALHOST_SN = "localhost_sn";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_FK_SALES_ORDER_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_CLIENT_NAME, null);
        put(F_CLIENT_EMAIL, null);
        put(F_CLIENT_PHONE, null);
        put(F_REFUND_STREAM_NO, null);
        put(F_REFUND_STREAM_IMAGE_URL, null);
        put(F_REFUND_AGREE_CONTENT, null);
        put(F_CURRENCY, null);
        put(F_REFUND_AMOUNT, null);
        put(F_REFUND_CARD_NO, null);
        put(F_REFUND_CONTENT, null);
        put(F_REFUND_REASON, null);
        put(F_AUDITER, null);
        put(F_AUDIT_TIME, null);
        put(F_REFUND_REQUEST_STATUS, null);
        put(F_REJECT_CONTENT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_SALES_ORDER_NO, null);
        put(F_LOCALHOST_SN, null);
    }

    public OrdRefundRequest() {
        super();
    }

    public OrdRefundRequest(Map<String, Object> map) {
        super(map);
    }

    public OrdRefundRequest(String id) {
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
    public OrdRefundRequest setId(String id) {
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
    public OrdRefundRequest setCompanyId(String companyId) {
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
    public OrdRefundRequest setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
    * @return fk_sales_order_id to fkSalesOrderId 销售单编号<BR/>
    */
    public String getFkSalesOrderId() {
        return getTypedValue(F_FK_SALES_ORDER_ID, String.class);
    }
    /**
    * @param fkSalesOrderId to fk_sales_order_id 销售单编号 set
    */
    public OrdRefundRequest setFkSalesOrderId(String fkSalesOrderId) {
        set(F_FK_SALES_ORDER_ID, fkSalesOrderId);
        return this;
    }
    /**
    * @return fk_clientele_id to fkClienteleId 客户编号<BR/>
    */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
    * @param fkClienteleId to fk_clientele_id 客户编号 set
    */
    public OrdRefundRequest setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return client_name to clientName 客户名称<BR/>
    */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
    * @param clientName to client_name 客户名称 set
    */
    public OrdRefundRequest setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
    * @return client_email to clientEmail 客户电子邮件<BR/>
    */
    public String getClientEmail() {
        return getTypedValue(F_CLIENT_EMAIL, String.class);
    }
    /**
    * @param clientEmail to client_email 客户电子邮件 set
    */
    public OrdRefundRequest setClientEmail(String clientEmail) {
        set(F_CLIENT_EMAIL, clientEmail);
        return this;
    }
    /**
    * @return client_phone to clientPhone 客户联系电话<BR/>
    */
    public String getClientPhone() {
        return getTypedValue(F_CLIENT_PHONE, String.class);
    }
    /**
    * @param clientPhone to client_phone 客户联系电话 set
    */
    public OrdRefundRequest setClientPhone(String clientPhone) {
        set(F_CLIENT_PHONE, clientPhone);
        return this;
    }
    /**
    * @return refund_stream_no to refundStreamNo 退款单流水号<BR/>
    */
    public String getRefundStreamNo() {
        return getTypedValue(F_REFUND_STREAM_NO, String.class);
    }
    /**
    * @param refundStreamNo to refund_stream_no 退款单流水号 set
    */
    public OrdRefundRequest setRefundStreamNo(String refundStreamNo) {
        set(F_REFUND_STREAM_NO, refundStreamNo);
        return this;
    }
    /**
    * @return refund_stream_image_url to refundStreamImageUrl 退款单截图地址<BR/>
    */
    public String getRefundStreamImageUrl() {
        return getTypedValue(F_REFUND_STREAM_IMAGE_URL, String.class);
    }
    /**
    * @param refundStreamImageUrl to refund_stream_image_url 退款单截图地址 set
    */
    public OrdRefundRequest setRefundStreamImageUrl(String refundStreamImageUrl) {
        set(F_REFUND_STREAM_IMAGE_URL, refundStreamImageUrl);
        return this;
    }
    /**
    * @return refund_agree_content to refundAgreeContent 同意退款说明<BR/>
    */
    public String getRefundAgreeContent() {
        return getTypedValue(F_REFUND_AGREE_CONTENT, String.class);
    }
    /**
    * @param refundAgreeContent to refund_agree_content 同意退款说明 set
    */
    public OrdRefundRequest setRefundAgreeContent(String refundAgreeContent) {
        set(F_REFUND_AGREE_CONTENT, refundAgreeContent);
        return this;
    }
    /**
    * @return currency to currency 币种<BR/>
    */
    public String getCurrency() {
        return getTypedValue(F_CURRENCY, String.class);
    }
    /**
    * @param currency to currency 币种 set
    */
    public OrdRefundRequest setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
    * @return refund_amount to refundAmount 退款金额<BR/>
    */
    public Long getRefundAmount() {
        return getTypedValue(F_REFUND_AMOUNT, Long.class);
    }
    /**
    * @param refundAmount to refund_amount 退款金额 set
    */
    public OrdRefundRequest setRefundAmount(Long refundAmount) {
        set(F_REFUND_AMOUNT, refundAmount);
        return this;
    }
    /**
    * @return refund_card_no to refundCardNo 退款卡号<BR/>
    */
    public String getRefundCardNo() {
        return getTypedValue(F_REFUND_CARD_NO, String.class);
    }
    /**
    * @param refundCardNo to refund_card_no 退款卡号 set
    */
    public OrdRefundRequest setRefundCardNo(String refundCardNo) {
        set(F_REFUND_CARD_NO, refundCardNo);
        return this;
    }
    /**
    * @return refund_content to refundContent 退款申请说明<BR/>
    */
    public String getRefundContent() {
        return getTypedValue(F_REFUND_CONTENT, String.class);
    }
    /**
    * @param refundContent to refund_content 退款申请说明 set
    */
    public OrdRefundRequest setRefundContent(String refundContent) {
        set(F_REFUND_CONTENT, refundContent);
        return this;
    }
    /**
    * @return refund_reason to refundReason 退款理由<BR/>
    */
    public String getRefundReason() {
        return getTypedValue(F_REFUND_REASON, String.class);
    }
    /**
    * @param refundReason to refund_reason 退款理由 set
    */
    public OrdRefundRequest setRefundReason(String refundReason) {
        set(F_REFUND_REASON, refundReason);
        return this;
    }
    /**
    * @return auditer to auditer 审核人<BR/>
    */
    public String getAuditer() {
        return getTypedValue(F_AUDITER, String.class);
    }
    /**
    * @param auditer to auditer 审核人 set
    */
    public OrdRefundRequest setAuditer(String auditer) {
        set(F_AUDITER, auditer);
        return this;
    }
    /**
    * @return audit_time to auditTime 审核时间<BR/>
    */
    public Long getAuditTime() {
        return getTypedValue(F_AUDIT_TIME, Long.class);
    }
    /**
    * @param auditTime to audit_time 审核时间 set
    */
    public OrdRefundRequest setAuditTime(Long auditTime) {
        set(F_AUDIT_TIME, auditTime);
        return this;
    }
    /**
    * @return refund_request_status to refundRequestStatus 退款申请单状态<BR/>
    */
    public String getRefundRequestStatus() {
        return getTypedValue(F_REFUND_REQUEST_STATUS, String.class);
    }
    /**
    * @param refundRequestStatus to refund_request_status 退款申请单状态 set
    */
    public OrdRefundRequest setRefundRequestStatus(String refundRequestStatus) {
        set(F_REFUND_REQUEST_STATUS, refundRequestStatus);
        return this;
    }
    /**
    * @return reject_content to rejectContent 拒绝说明<BR/>
    */
    public String getRejectContent() {
        return getTypedValue(F_REJECT_CONTENT, String.class);
    }
    /**
    * @param rejectContent to reject_content 拒绝说明 set
    */
    public OrdRefundRequest setRejectContent(String rejectContent) {
        set(F_REJECT_CONTENT, rejectContent);
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
    public OrdRefundRequest setRemark(String remark) {
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
    public OrdRefundRequest setAddTime(Long addTime) {
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
    public OrdRefundRequest setAddUserId(String addUserId) {
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
    public OrdRefundRequest setAddUserName(String addUserName) {
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
    public OrdRefundRequest setOperationTime(Long operationTime) {
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
    public OrdRefundRequest setOperationUserId(String operationUserId) {
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
    public OrdRefundRequest setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
    * @return sales_order_no to salesOrderNo 销售单号<BR/>
    */
    public String getSalesOrderNo() {
        return getTypedValue(F_SALES_ORDER_NO, String.class);
    }
    /**
    * @param salesOrderNo to sales_order_no 销售单号 set
    */
    public OrdRefundRequest setSalesOrderNo(String salesOrderNo) {
        set(F_SALES_ORDER_NO, salesOrderNo);
        return this;
    }
    /**
    * @return localhost_sn to localhostSn <BR/>
    */
    public Long getLocalhostSn() {
        return getTypedValue(F_LOCALHOST_SN, Long.class);
    }
    /**
    * @param localhostSn to localhost_sn  set
    */
    public OrdRefundRequest setLocalhostSn(Long localhostSn) {
        set(F_LOCALHOST_SN, localhostSn);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdRefundRequest setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdRefundRequest me(){
        return new OrdRefundRequest();
    }

    private static class Mapper implements RowMapper<OrdRefundRequest> {

        private Supplier<OrdRefundRequest> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdRefundRequest mapRow(ResultSet rs, int rownum) throws SQLException {
            OrdRefundRequest bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setCompanyId(Utils.toCast(rs.getObject(F_COMPANY_ID), String.class));
            bean.setCompanyName(Utils.toCast(rs.getObject(F_COMPANY_NAME), String.class));
            bean.setFkSalesOrderId(Utils.toCast(rs.getObject(F_FK_SALES_ORDER_ID), String.class));
            bean.setFkClienteleId(Utils.toCast(rs.getObject(F_FK_CLIENTELE_ID), String.class));
            bean.setClientName(Utils.toCast(rs.getObject(F_CLIENT_NAME), String.class));
            bean.setClientEmail(Utils.toCast(rs.getObject(F_CLIENT_EMAIL), String.class));
            bean.setClientPhone(Utils.toCast(rs.getObject(F_CLIENT_PHONE), String.class));
            bean.setRefundStreamNo(Utils.toCast(rs.getObject(F_REFUND_STREAM_NO), String.class));
            bean.setRefundStreamImageUrl(Utils.toCast(rs.getObject(F_REFUND_STREAM_IMAGE_URL), String.class));
            bean.setRefundAgreeContent(Utils.toCast(rs.getObject(F_REFUND_AGREE_CONTENT), String.class));
            bean.setCurrency(Utils.toCast(rs.getObject(F_CURRENCY), String.class));
            bean.setRefundAmount(Utils.toCast(rs.getObject(F_REFUND_AMOUNT), Long.class));
            bean.setRefundCardNo(Utils.toCast(rs.getObject(F_REFUND_CARD_NO), String.class));
            bean.setRefundContent(Utils.toCast(rs.getObject(F_REFUND_CONTENT), String.class));
            bean.setRefundReason(Utils.toCast(rs.getObject(F_REFUND_REASON), String.class));
            bean.setAuditer(Utils.toCast(rs.getObject(F_AUDITER), String.class));
            bean.setAuditTime(Utils.toCast(rs.getObject(F_AUDIT_TIME), Long.class));
            bean.setRefundRequestStatus(Utils.toCast(rs.getObject(F_REFUND_REQUEST_STATUS), String.class));
            bean.setRejectContent(Utils.toCast(rs.getObject(F_REJECT_CONTENT), String.class));
            bean.setRemark(Utils.toCast(rs.getObject(F_REMARK), String.class));
            bean.setAddTime(Utils.toCast(rs.getObject(F_ADD_TIME), Long.class));
            bean.setAddUserId(Utils.toCast(rs.getObject(F_ADD_USER_ID), String.class));
            bean.setAddUserName(Utils.toCast(rs.getObject(F_ADD_USER_NAME), String.class));
            bean.setOperationTime(Utils.toCast(rs.getObject(F_OPERATION_TIME), Long.class));
            bean.setOperationUserId(Utils.toCast(rs.getObject(F_OPERATION_USER_ID), String.class));
            bean.setOperationUserName(Utils.toCast(rs.getObject(F_OPERATION_USER_NAME), String.class));
            bean.setSalesOrderNo(Utils.toCast(rs.getObject(F_SALES_ORDER_NO), String.class));
            bean.setLocalhostSn(Utils.toCast(rs.getObject(F_LOCALHOST_SN), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdRefundRequest> newMapper(){
        return newMapper(OrdRefundRequest::new);
    }

    public RowMapper<OrdRefundRequest> newMapper(Supplier<OrdRefundRequest> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdRefundRequest {
        @Override
        public abstract RowMapper<OrdRefundRequest> newMapper();
    }
}
