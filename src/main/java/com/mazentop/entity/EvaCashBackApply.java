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
 * Date:        17:04 2021/04/27
 * Version:     1.0
 * Description: EvaCashBackApply实体
 */
@SuppressWarnings("all")
public class EvaCashBackApply extends BaseBean<EvaCashBackApply> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "eva_cash_back_apply";

    /**
     * id
     */
    public static final String F_ID = "id";
    /**
     * 订单id
     */
    public static final String F_EVA_ORDER_ID = "eva_order_id";
    /**
     * 用户id
     */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
     * paypal账号
     */
    public static final String F_PAYPAL_ACCOUNT = "paypal_account";
    /**
     * paypal流水号
     */
    public static final String F_PAYPAL_SERIAL_NO = "paypal_serial_no";
    /**
     * 亚马逊评论链接
     */
    public static final String F_AMAZON_COMMENT_LINK = "amazon_comment_link";
    /**
     * 亚马逊订单号
     */
    public static final String F_AMAZON_ORDER_NO = "amazon_order_no";
    /**
     * 评论截图
     */
    public static final String F_COMMENT_IMG = "comment_img";
    /**
     * 申请备注
     */
    public static final String F_APPLY_REMARKS = "apply_remarks";
    /**
     * 审核人
     */
    public static final String F_REVIEWER_ID = "reviewer_id";
    /**
     * 审核时间
     */
    public static final String F_REVIEWER_TIME = "reviewer_time";
    /**
     * 状态（1-待审核 2-审核通过 3-审核不通过）
     */
    public static final String F_STATUS = "status";
    /**
     * 审核备注
     */
    public static final String F_REVIEWER_REMARKS = "reviewer_remarks";
    /**
     * 转账凭证
     */
    public static final String F_TRANSFER_VOUCHER = "transfer_voucher";
    /**
     * 添加人编号
     */
    public static final String F_ADD_USER_ID = "add_user_id";
    /**
     * 添加时间
     */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_EVA_ORDER_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_PAYPAL_ACCOUNT, null);
        put(F_PAYPAL_SERIAL_NO, null);
        put(F_AMAZON_COMMENT_LINK, null);
        put(F_AMAZON_ORDER_NO, null);
        put(F_COMMENT_IMG, null);
        put(F_APPLY_REMARKS, null);
        put(F_REVIEWER_ID, null);
        put(F_REVIEWER_TIME, null);
        put(F_STATUS, null);
        put(F_REVIEWER_REMARKS, null);
        put(F_TRANSFER_VOUCHER, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_TIME, null);
    }

    public EvaCashBackApply() {
        super();
    }

    public EvaCashBackApply(Map<String, Object> map) {
        super(map);
    }

    public EvaCashBackApply(String id) {
        super();
        setId(id);
    }

    /**
     * @return id to id id<BR/>
     */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
     * @param id to id id set
     */
    public EvaCashBackApply setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return eva_order_id to evaOrderId 订单id<BR/>
     */
    public String getEvaOrderId() {
        return getTypedValue(F_EVA_ORDER_ID, String.class);
    }
    /**
     * @param evaOrderId to eva_order_id 订单id set
     */
    public EvaCashBackApply setEvaOrderId(String evaOrderId) {
        set(F_EVA_ORDER_ID, evaOrderId);
        return this;
    }
    /**
     * @return fk_clientele_id to fkClienteleId 用户id<BR/>
     */
    public String getFkClienteleId() {
        return getTypedValue(F_FK_CLIENTELE_ID, String.class);
    }
    /**
     * @param fkClienteleId to fk_clientele_id 用户id set
     */
    public EvaCashBackApply setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
     * @return paypal_account to paypalAccount paypal账号<BR/>
     */
    public String getPaypalAccount() {
        return getTypedValue(F_PAYPAL_ACCOUNT, String.class);
    }
    /**
     * @param paypalAccount to paypal_account paypal账号 set
     */
    public EvaCashBackApply setPaypalAccount(String paypalAccount) {
        set(F_PAYPAL_ACCOUNT, paypalAccount);
        return this;
    }
    /**
     * @return paypal_serial_no to paypalSerialNo paypal流水号<BR/>
     */
    public String getPaypalSerialNo() {
        return getTypedValue(F_PAYPAL_SERIAL_NO, String.class);
    }
    /**
     * @param paypalSerialNo to paypal_serial_no paypal流水号 set
     */
    public EvaCashBackApply setPaypalSerialNo(String paypalSerialNo) {
        set(F_PAYPAL_SERIAL_NO, paypalSerialNo);
        return this;
    }
    /**
     * @return amazon_comment_link to amazonCommentLink 亚马逊评论链接<BR/>
     */
    public String getAmazonCommentLink() {
        return getTypedValue(F_AMAZON_COMMENT_LINK, String.class);
    }
    /**
     * @param amazonCommentLink to amazon_comment_link 亚马逊评论链接 set
     */
    public EvaCashBackApply setAmazonCommentLink(String amazonCommentLink) {
        set(F_AMAZON_COMMENT_LINK, amazonCommentLink);
        return this;
    }
    /**
     * @return amazon_order_no to amazonOrderNo 亚马逊订单号<BR/>
     */
    public String getAmazonOrderNo() {
        return getTypedValue(F_AMAZON_ORDER_NO, String.class);
    }
    /**
     * @param amazonOrderNo to amazon_order_no 亚马逊订单号 set
     */
    public EvaCashBackApply setAmazonOrderNo(String amazonOrderNo) {
        set(F_AMAZON_ORDER_NO, amazonOrderNo);
        return this;
    }
    /**
     * @return comment_img to commentImg 评论截图<BR/>
     */
    public String getCommentImg() {
        return getTypedValue(F_COMMENT_IMG, String.class);
    }
    /**
     * @param commentImg to comment_img 评论截图 set
     */
    public EvaCashBackApply setCommentImg(String commentImg) {
        set(F_COMMENT_IMG, commentImg);
        return this;
    }
    /**
     * @return apply_remarks to applyRemarks 申请备注<BR/>
     */
    public String getApplyRemarks() {
        return getTypedValue(F_APPLY_REMARKS, String.class);
    }
    /**
     * @param applyRemarks to apply_remarks 申请备注 set
     */
    public EvaCashBackApply setApplyRemarks(String applyRemarks) {
        set(F_APPLY_REMARKS, applyRemarks);
        return this;
    }
    /**
     * @return reviewer_id to reviewerId 审核人<BR/>
     */
    public String getReviewerId() {
        return getTypedValue(F_REVIEWER_ID, String.class);
    }
    /**
     * @param reviewerId to reviewer_id 审核人 set
     */
    public EvaCashBackApply setReviewerId(String reviewerId) {
        set(F_REVIEWER_ID, reviewerId);
        return this;
    }
    /**
     * @return reviewer_time to reviewerTime 审核时间<BR/>
     */
    public Long getReviewerTime() {
        return getTypedValue(F_REVIEWER_TIME, Long.class);
    }
    /**
     * @param reviewerTime to reviewer_time 审核时间 set
     */
    public EvaCashBackApply setReviewerTime(Long reviewerTime) {
        set(F_REVIEWER_TIME, reviewerTime);
        return this;
    }
    /**
     * @return status to status 状态（1-待审核 2-审核通过 3-审核不通过）<BR/>
     */
    public Integer getStatus() {
        return getTypedValue(F_STATUS, Integer.class);
    }
    /**
     * @param status to status 状态（1-待审核 2-审核通过 3-审核不通过） set
     */
    public EvaCashBackApply setStatus(Integer status) {
        set(F_STATUS, status);
        return this;
    }
    /**
     * @return reviewer_remarks to reviewerRemarks 审核备注<BR/>
     */
    public String getReviewerRemarks() {
        return getTypedValue(F_REVIEWER_REMARKS, String.class);
    }
    /**
     * @param reviewerRemarks to reviewer_remarks 审核备注 set
     */
    public EvaCashBackApply setReviewerRemarks(String reviewerRemarks) {
        set(F_REVIEWER_REMARKS, reviewerRemarks);
        return this;
    }
    /**
     * @return transfer_voucher to transferVoucher 转账凭证<BR/>
     */
    public String getTransferVoucher() {
        return getTypedValue(F_TRANSFER_VOUCHER, String.class);
    }
    /**
     * @param transferVoucher to transfer_voucher 转账凭证 set
     */
    public EvaCashBackApply setTransferVoucher(String transferVoucher) {
        set(F_TRANSFER_VOUCHER, transferVoucher);
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
    public EvaCashBackApply setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
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
    public EvaCashBackApply setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public EvaCashBackApply setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static EvaCashBackApply me(){
        return new EvaCashBackApply();
    }

    private static class Mapper implements RowMapper<EvaCashBackApply> {

        private Supplier<EvaCashBackApply> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public EvaCashBackApply mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            EvaCashBackApply bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setEvaOrderId(Utils.toCast(columnsName.contains(F_EVA_ORDER_ID) ? rs.getObject(F_EVA_ORDER_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setPaypalAccount(Utils.toCast(columnsName.contains(F_PAYPAL_ACCOUNT) ? rs.getObject(F_PAYPAL_ACCOUNT) : null, String.class));
            bean.setPaypalSerialNo(Utils.toCast(columnsName.contains(F_PAYPAL_SERIAL_NO) ? rs.getObject(F_PAYPAL_SERIAL_NO) : null, String.class));
            bean.setAmazonCommentLink(Utils.toCast(columnsName.contains(F_AMAZON_COMMENT_LINK) ? rs.getObject(F_AMAZON_COMMENT_LINK) : null, String.class));
            bean.setAmazonOrderNo(Utils.toCast(columnsName.contains(F_AMAZON_ORDER_NO) ? rs.getObject(F_AMAZON_ORDER_NO) : null, String.class));
            bean.setCommentImg(Utils.toCast(columnsName.contains(F_COMMENT_IMG) ? rs.getObject(F_COMMENT_IMG) : null, String.class));
            bean.setApplyRemarks(Utils.toCast(columnsName.contains(F_APPLY_REMARKS) ? rs.getObject(F_APPLY_REMARKS) : null, String.class));
            bean.setReviewerId(Utils.toCast(columnsName.contains(F_REVIEWER_ID) ? rs.getObject(F_REVIEWER_ID) : null, String.class));
            bean.setReviewerTime(Utils.toCast(columnsName.contains(F_REVIEWER_TIME) ? rs.getObject(F_REVIEWER_TIME) : null, Long.class));
            bean.setStatus(Utils.toCast(columnsName.contains(F_STATUS) ? rs.getObject(F_STATUS) : null, Integer.class));
            bean.setReviewerRemarks(Utils.toCast(columnsName.contains(F_REVIEWER_REMARKS) ? rs.getObject(F_REVIEWER_REMARKS) : null, String.class));
            bean.setTransferVoucher(Utils.toCast(columnsName.contains(F_TRANSFER_VOUCHER) ? rs.getObject(F_TRANSFER_VOUCHER) : null, String.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<EvaCashBackApply> newMapper(){
        return newMapper(EvaCashBackApply::new);
    }

    public RowMapper<EvaCashBackApply> newMapper(Supplier<EvaCashBackApply> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends EvaCashBackApply {
        @Override
        public abstract RowMapper<EvaCashBackApply> newMapper();
    }
}
