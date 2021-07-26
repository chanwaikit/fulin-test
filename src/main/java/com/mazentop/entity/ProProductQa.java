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
* Date:        20:07 2020/11/20
* Version:     1.0
* Description: ProProductQa实体
*/
@SuppressWarnings("all")
public class ProProductQa extends BaseBean<ProProductQa> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "pro_product_qa";

    /**
    * 编号
    */
    public static final String F_ID = "id";
    /**
    * 店铺主键（冗余字段，方便查询）
    */
    public static final String F_SHOP_ID = "shop_id";
    /**
    * 商品编号
    */
    public static final String F_FK_PRODUCT_ID = "fk_product_id";
    /**
    * 顾客编号
    */
    public static final String F_FK_CLIENTELE_ID = "fk_clientele_id";
    /**
    * 问题描述
    */
    public static final String F_PROBLEM_DESC = "problem_desc";
    /**
    * 提问时间
    */
    public static final String F_PROBLEM_TIME = "problem_time";
    /**
    * 回答人id（商户id）
    */
    public static final String F_USER_ID = "user_id";
    /**
    * 回答内容
    */
    public static final String F_ANSWER_CONTENT = "answer_content";
    /**
    * 回答时间
    */
    public static final String F_ANSWER_TIME = "answer_time";
    /**
    * 是否显示 0:不显示；1：显示
    */
    public static final String F_IS_DISPLAY = "is_display";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SHOP_ID, null);
        put(F_FK_PRODUCT_ID, null);
        put(F_FK_CLIENTELE_ID, null);
        put(F_PROBLEM_DESC, null);
        put(F_PROBLEM_TIME, null);
        put(F_USER_ID, null);
        put(F_ANSWER_CONTENT, null);
        put(F_ANSWER_TIME, null);
        put(F_IS_DISPLAY, null);
    }

    public ProProductQa() {
        super();
    }

    public ProProductQa(Map<String, Object> map) {
        super(map);
    }

    public ProProductQa(String id) {
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
    public ProProductQa setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return shop_id to shopId 店铺主键（冗余字段，方便查询）<BR/>
    */
    public String getShopId() {
        return getTypedValue(F_SHOP_ID, String.class);
    }
    /**
    * @param shopId to shop_id 店铺主键（冗余字段，方便查询） set
    */
    public ProProductQa setShopId(String shopId) {
        set(F_SHOP_ID, shopId);
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
    public ProProductQa setFkProductId(String fkProductId) {
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
    public ProProductQa setFkClienteleId(String fkClienteleId) {
        set(F_FK_CLIENTELE_ID, fkClienteleId);
        return this;
    }
    /**
    * @return problem_desc to problemDesc 问题描述<BR/>
    */
    public String getProblemDesc() {
        return getTypedValue(F_PROBLEM_DESC, String.class);
    }
    /**
    * @param problemDesc to problem_desc 问题描述 set
    */
    public ProProductQa setProblemDesc(String problemDesc) {
        set(F_PROBLEM_DESC, problemDesc);
        return this;
    }
    /**
    * @return problem_time to problemTime 提问时间<BR/>
    */
    public Long getProblemTime() {
        return getTypedValue(F_PROBLEM_TIME, Long.class);
    }
    /**
    * @param problemTime to problem_time 提问时间 set
    */
    public ProProductQa setProblemTime(Long problemTime) {
        set(F_PROBLEM_TIME, problemTime);
        return this;
    }
    /**
    * @return user_id to userId 回答人id（商户id）<BR/>
    */
    public String getUserId() {
        return getTypedValue(F_USER_ID, String.class);
    }
    /**
    * @param userId to user_id 回答人id（商户id） set
    */
    public ProProductQa setUserId(String userId) {
        set(F_USER_ID, userId);
        return this;
    }
    /**
    * @return answer_content to answerContent 回答内容<BR/>
    */
    public String getAnswerContent() {
        return getTypedValue(F_ANSWER_CONTENT, String.class);
    }
    /**
    * @param answerContent to answer_content 回答内容 set
    */
    public ProProductQa setAnswerContent(String answerContent) {
        set(F_ANSWER_CONTENT, answerContent);
        return this;
    }
    /**
    * @return answer_time to answerTime 回答时间<BR/>
    */
    public Long getAnswerTime() {
        return getTypedValue(F_ANSWER_TIME, Long.class);
    }
    /**
    * @param answerTime to answer_time 回答时间 set
    */
    public ProProductQa setAnswerTime(Long answerTime) {
        set(F_ANSWER_TIME, answerTime);
        return this;
    }
    /**
    * @return is_display to isDisplay 是否显示 0:不显示；1：显示<BR/>
    */
    public Integer getIsDisplay() {
        return getTypedValue(F_IS_DISPLAY, Integer.class);
    }
    /**
    * @param isDisplay to is_display 是否显示 0:不显示；1：显示 set
    */
    public ProProductQa setIsDisplay(Integer isDisplay) {
        set(F_IS_DISPLAY, isDisplay);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public ProProductQa setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static ProProductQa me(){
        return new ProProductQa();
    }

    private static class Mapper implements RowMapper<ProProductQa> {

        private Supplier<ProProductQa> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public ProProductQa mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            ProProductQa bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setShopId(Utils.toCast(columnsName.contains(F_SHOP_ID) ? rs.getObject(F_SHOP_ID) : null, String.class));
            bean.setFkProductId(Utils.toCast(columnsName.contains(F_FK_PRODUCT_ID) ? rs.getObject(F_FK_PRODUCT_ID) : null, String.class));
            bean.setFkClienteleId(Utils.toCast(columnsName.contains(F_FK_CLIENTELE_ID) ? rs.getObject(F_FK_CLIENTELE_ID) : null, String.class));
            bean.setProblemDesc(Utils.toCast(columnsName.contains(F_PROBLEM_DESC) ? rs.getObject(F_PROBLEM_DESC) : null, String.class));
            bean.setProblemTime(Utils.toCast(columnsName.contains(F_PROBLEM_TIME) ? rs.getObject(F_PROBLEM_TIME) : null, Long.class));
            bean.setUserId(Utils.toCast(columnsName.contains(F_USER_ID) ? rs.getObject(F_USER_ID) : null, String.class));
            bean.setAnswerContent(Utils.toCast(columnsName.contains(F_ANSWER_CONTENT) ? rs.getObject(F_ANSWER_CONTENT) : null, String.class));
            bean.setAnswerTime(Utils.toCast(columnsName.contains(F_ANSWER_TIME) ? rs.getObject(F_ANSWER_TIME) : null, Long.class));
            bean.setIsDisplay(Utils.toCast(columnsName.contains(F_IS_DISPLAY) ? rs.getObject(F_IS_DISPLAY) : null, Integer.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<ProProductQa> newMapper(){
        return newMapper(ProProductQa::new);
    }

    public RowMapper<ProProductQa> newMapper(Supplier<ProProductQa> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends ProProductQa {
        @Override
        public abstract RowMapper<ProProductQa> newMapper();
    }
}
