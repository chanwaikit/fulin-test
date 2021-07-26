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
* Author:      dengy
* Mail:        dengy@mazentop.com
* Date:        15:55 2020/11/10
* Version:     1.0
* Description: OrdSettlementConfirm实体
*/
@SuppressWarnings("all")
public class OrdSettlementConfirm extends BaseBean<OrdSettlementConfirm> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_settlement_confirm";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 订单信息
    */
    public static final String F_CONTENT = "content";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_CONTENT, null);
        put(F_ADD_TIME, null);
    }

    public OrdSettlementConfirm() {
        super();
    }

    public OrdSettlementConfirm(Map<String, Object> map) {
        super(map);
    }

    public OrdSettlementConfirm(String id) {
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
    public OrdSettlementConfirm setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return content to content 订单信息<BR/>
    */
    public String getContent() {
        return getTypedValue(F_CONTENT, String.class);
    }
    /**
    * @param content to content 订单信息 set
    */
    public OrdSettlementConfirm setContent(String content) {
        set(F_CONTENT, content);
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
    public OrdSettlementConfirm setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdSettlementConfirm setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdSettlementConfirm me(){
        return new OrdSettlementConfirm();
    }

    private static class Mapper implements RowMapper<OrdSettlementConfirm> {

        private Supplier<OrdSettlementConfirm> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdSettlementConfirm mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            OrdSettlementConfirm bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setContent(Utils.toCast(columnsName.contains(F_CONTENT) ? rs.getObject(F_CONTENT) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdSettlementConfirm> newMapper(){
        return newMapper(OrdSettlementConfirm::new);
    }

    public RowMapper<OrdSettlementConfirm> newMapper(Supplier<OrdSettlementConfirm> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdSettlementConfirm {
        @Override
        public abstract RowMapper<OrdSettlementConfirm> newMapper();
    }
}
