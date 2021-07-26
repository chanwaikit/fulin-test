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
* Date:        14:00 2020/12/14
* Version:     1.0
* Description: ProComment实体
*/
@SuppressWarnings("all")
public class ProComment extends BaseBean<ProComment> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_comment";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 商品编号
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 顾客编号
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 评论图片
    */
    public static final String F_PRODUCT_PRO_IMAGE = "product_pro_image";
    /**
    * 商品名称
    */
    public static final String F_PRODUCT_NAME = "product_name";
    /**
    * 评价内容
    */
    public static final String F_CONTENT = "content";
    /**
    * 商品评论分数
    */
    public static final String F_RANGE_NUM = "range_num";
    /**
    * 售后服务评论分数
    */
    public static final String F_SERVICE_RANGE_NUM = "service_range_num";
    /**
    * 质量评论分数
    */
    public static final String F_QUALITY_RANGE_NUM = "quality_range_num";
    /**
    * 是否审核通过
    */
    public static final String F_IS_AUDIT_PASS = "is_audit_pass";
    /**
    * 审核通过时间
    */
    public static final String F_AUDIT_TIME = "audit_time";
    /**
    * 审核人
    */
    public static final String F_AUDITER = "auditer";
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
    * 是否显示评论
    */
    public static final String F_IS_DISPLAY = "is_display";
    /**
    * 点赞数
    */
    public static final String F_LIKE_NUM = "like_num";
    /**
    * 国家id
    */
    public static final String F_FK_COUNTRY_ID = "fk_country_id";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_PRODUCT_PRO_IMAGE, null);
        put(F_PRODUCT_NAME, null);
        put(F_CONTENT, null);
        put(F_RANGE_NUM, null);
        put(F_SERVICE_RANGE_NUM, null);
        put(F_QUALITY_RANGE_NUM, null);
        put(F_IS_AUDIT_PASS, null);
        put(F_AUDIT_TIME, null);
        put(F_AUDITER, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_ADD_USER_NAME, null);
        put(F_OPERATION_TIME, null);
        put(F_OPERATION_USER_ID, null);
        put(F_OPERATION_USER_NAME, null);
        put(F_IS_DISPLAY, null);
        put(F_LIKE_NUM, null);
        put(F_FK_COUNTRY_ID, null);
    }

    public ProComment() {
        super();
    }

    public ProComment(Map<String, Object> map) {
        super(map);
    }

    public ProComment(String id) {
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
    public ProComment setId(String id) {
        set(F_ID, id);
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
    public ProComment setFkProductId(String fkProductId) {
        set(F_FK_PRODUCT_ID, fkProductId);
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
    public ProComment setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return product_pro_image to productProImage 评论图片<BR/>
    */
    public String getProductProImage() {
        return getTypedValue(F_PRODUCT_PRO_IMAGE, String.class);
    }
    /**
    * @param productProImage to product_pro_image 评论图片 set
    */
    public ProComment setProductProImage(String productProImage) {
        set(F_PRODUCT_PRO_IMAGE, productProImage);
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
    public ProComment setProductName(String productName) {
        set(F_PRODUCT_NAME, productName);
        return this;
    }
    /**
    * @return content to content 评价内容<BR/>
    */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
    * @param content to content 评价内容 set
    */
    public ProComment setContent(String content) {
        set(F_CONTENT, content);
        return this;
    }
    /**
    * @return range_num to rangeNum 商品评论分数<BR/>
    */
    public Integer getRangeNum() {
        return getTypedValue(F_RANGE_NUM, Integer.class);
    }
    /**
    * @param rangeNum to range_num 商品评论分数 set
    */
    public ProComment setRangeNum(Integer rangeNum) {
        set(F_RANGE_NUM, rangeNum);
        return this;
    }
    /**
    * @return service_range_num to serviceRangeNum 售后服务评论分数<BR/>
    */
    public Integer getServiceRangeNum() {
        return getTypedValue(F_SERVICE_RANGE_NUM, Integer.class);
    }
    /**
    * @param serviceRangeNum to service_range_num 售后服务评论分数 set
    */
    public ProComment setServiceRangeNum(Integer serviceRangeNum) {
        set(F_SERVICE_RANGE_NUM, serviceRangeNum);
        return this;
    }
    /**
    * @return quality_range_num to qualityRangeNum 质量评论分数<BR/>
    */
    public Integer getQualityRangeNum() {
        return getTypedValue(F_QUALITY_RANGE_NUM, Integer.class);
    }
    /**
    * @param qualityRangeNum to quality_range_num 质量评论分数 set
    */
    public ProComment setQualityRangeNum(Integer qualityRangeNum) {
        set(F_QUALITY_RANGE_NUM, qualityRangeNum);
        return this;
    }
    /**
    * @return is_audit_pass to isAuditPass 是否审核通过<BR/>
    */
    public Integer getIsAuditPass() {
        return getTypedValue(F_IS_AUDIT_PASS, Integer.class);
    }
    /**
    * @param isAuditPass to is_audit_pass 是否审核通过 set
    */
    public ProComment setIsAuditPass(Integer isAuditPass) {
        set(F_IS_AUDIT_PASS, isAuditPass);
        return this;
    }
    /**
    * @return audit_time to auditTime 审核通过时间<BR/>
    */
    public Long getAuditTime() {
        return getTypedValue(F_AUDIT_TIME, Long.class);
    }
    /**
    * @param auditTime to audit_time 审核通过时间 set
    */
    public ProComment setAuditTime(Long auditTime) {
        set(F_AUDIT_TIME, auditTime);
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
    public ProComment setAuditer(String auditer) {
        set(F_AUDITER, auditer);
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
    public ProComment setRemark(String remark) {
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
    public ProComment setAddTime(Long addTime) {
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
    public ProComment setAddUserId(String addUserId) {
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
    public ProComment setAddUserName(String addUserName) {
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
    public ProComment setOperationTime(Long operationTime) {
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
    public ProComment setOperationUserId(String operationUserId) {
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
    public ProComment setOperationUserName(String operationUserName) {
        set(F_OPERATION_USER_NAME, operationUserName);
        return this;
    }
    /**
    * @return is_display to isDisplay 是否显示评论<BR/>
    */
    public Integer getIsDisplay() {
        return getTypedValue(F_IS_DISPLAY, Integer.class);
    }
    /**
    * @param isDisplay to is_display 是否显示评论 set
    */
    public ProComment setIsDisplay(Integer isDisplay) {
        set(F_IS_DISPLAY, isDisplay);
        return this;
    }
    /**
    * @return like_num to likeNum 点赞数<BR/>
    */
    public Integer getLikeNum() {
        return getTypedValue(F_LIKE_NUM, Integer.class);
    }
    /**
    * @param likeNum to like_num 点赞数 set
    */
    public ProComment setLikeNum(Integer likeNum) {
        set(F_LIKE_NUM, likeNum);
        return this;
    }
    /**
    * @return fk_country_id to fkCountryId 国家id<BR/>
    */
    public String getFkCountryId() {
        return getTypedValue(F_FK_COUNTRY_ID, String.class);
    }
    /**
    * @param fkCountryId to fk_country_id 国家id set
    */
    public ProComment setFkCountryId(String fkCountryId) {
        set(F_FK_COUNTRY_ID, fkCountryId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProComment setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProComment me(){
        return new ProComment();
    }

    private static class Mapper implements RowMapper<ProComment> {

        private Supplier<ProComment> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProComment mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProComment bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setProductProImage(Utils.toCast(columnsName.contains(F_PRODUCT_PRO_IMAGE) ? rs.getObject(F_PRODUCT_PRO_IMAGE) : null, String.class));
            bean.setProductName(Utils.toCast(columnsName.contains(F_PRODUCT_NAME) ? rs.getObject(F_PRODUCT_NAME) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setRangeNum(Utils.toCast(columnsName.contains(F_RANGE_NUM) ? rs.getObject(F_RANGE_NUM) : null, Integer.class));
            bean.setServiceRangeNum(Utils.toCast(columnsName.contains(F_SERVICE_RANGE_NUM) ? rs.getObject(F_SERVICE_RANGE_NUM) : null, Integer.class));
            bean.setQualityRangeNum(Utils.toCast(columnsName.contains(F_QUALITY_RANGE_NUM) ? rs.getObject(F_QUALITY_RANGE_NUM) : null, Integer.class));
            bean.setIsAuditPass(Utils.toCast(columnsName.contains(F_IS_AUDIT_PASS) ? rs.getObject(F_IS_AUDIT_PASS) : null, Integer.class));
            bean.setAuditTime(Utils.toCast(columnsName.contains(F_AUDIT_TIME) ? rs.getObject(F_AUDIT_TIME) : null, Long.class));
            bean.setAuditer(Utils.toCast(columnsName.contains(F_AUDITER) ? rs.getObject(F_AUDITER) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setAddUserName(Utils.toCast(columnsName.contains(F_ADD_USER_NAME) ? rs.getObject(F_ADD_USER_NAME) : null, String.class));
            bean.setOperationTime(Utils.toCast(columnsName.contains(F_OPERATION_TIME) ? rs.getObject(F_OPERATION_TIME) : null, Long.class));
            bean.setOperationUserId(Utils.toCast(columnsName.contains(F_OPERATION_USER_ID) ? rs.getObject(F_OPERATION_USER_ID) : null, String.class));
            bean.setOperationUserName(Utils.toCast(columnsName.contains(F_OPERATION_USER_NAME) ? rs.getObject(F_OPERATION_USER_NAME) : null, String.class));
            bean.setIsDisplay(Utils.toCast(columnsName.contains(F_IS_DISPLAY) ? rs.getObject(F_IS_DISPLAY) : null, Integer.class));
            bean.setLikeNum(Utils.toCast(columnsName.contains(F_LIKE_NUM) ? rs.getObject(F_LIKE_NUM) : null, Integer.class));
            bean.setFkCountryId(Utils.toCast(columnsName.contains(F_FK_COUNTRY_ID) ? rs.getObject(F_FK_COUNTRY_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProComment> newMapper(){
        return newMapper(ProComment::new);
    }

    public RowMapper<ProComment> newMapper(Supplier<ProComment> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProComment {
        @Override
        public abstract RowMapper<ProComment> newMapper();
    }
}
