package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;
import java.util.function.Supplier;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseBean;
import org.springframework.jdbc.core.RowMapper;
import java.math.BigDecimal;

/**
* Author:      zhaoqt
* Mail:        zhaoqt@mazentop.com
* Date:        16:21 2020/04/13
* Company:     美赞拓
* Version:     1.0
* Description: SysExchangeRate实体
*/
@SuppressWarnings("all")
public class SysExchangeRate extends BaseBean<SysExchangeRate> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "sys_exchange_rate";

    /**
    * 
    */
    public static final String F_ID = "id";
    /**
    * 换算币种
    */
    public static final String F_SCUR = "scur";
    /**
    * 被换算币种
    */
    public static final String F_TCUR = "tcur";
    /**
    * 换算比率
    */
    public static final String F_RATIO = "ratio";
    /**
    * 手动录入比率
    */
    public static final String F_TAG_RATIO = "tag_ratio";
    /**
    * 换算更新时间
    */
    public static final String F_EDIT_TIME = "edit_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_SCUR, null);
        put(F_TCUR, null);
        put(F_RATIO, null);
        put(F_TAG_RATIO, null);
        put(F_EDIT_TIME, null);
    }

    public SysExchangeRate() {
        super();
    }

    public SysExchangeRate(Map<String, Object> map) {
        super(map);
    }

    public SysExchangeRate(String id) {
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
    public SysExchangeRate setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return scur to scur 换算币种<BR/>
    */
    public String getScur() {
        return getTypedValue(F_SCUR, String.class);
    }
    /**
    * @param scur to scur 换算币种 set
    */
    public SysExchangeRate setScur(String scur) {
        set(F_SCUR, scur);
        return this;
    }
    /**
    * @return tcur to tcur 被换算币种<BR/>
    */
    public String getTcur() {
        return getTypedValue(F_TCUR, String.class);
    }
    /**
    * @param tcur to tcur 被换算币种 set
    */
    public SysExchangeRate setTcur(String tcur) {
        set(F_TCUR, tcur);
        return this;
    }
    /**
    * @return ratio to ratio 换算比率<BR/>
    */
    public BigDecimal getRatio() {
        return getTypedValue(F_RATIO, BigDecimal.class);
    }
    /**
    * @param ratio to ratio 换算比率 set
    */
    public SysExchangeRate setRatio(BigDecimal ratio) {
        set(F_RATIO, ratio);
        return this;
    }
    /**
    * @return tag_ratio to tagRatio 手动录入比率<BR/>
    */
    public BigDecimal getTagRatio() {
        return getTypedValue(F_TAG_RATIO, BigDecimal.class);
    }
    /**
    * @param tagRatio to tag_ratio 手动录入比率 set
    */
    public SysExchangeRate setTagRatio(BigDecimal tagRatio) {
        set(F_TAG_RATIO, tagRatio);
        return this;
    }
    /**
    * @return edit_time to editTime 换算更新时间<BR/>
    */
    public Long getEditTime() {
        return getTypedValue(F_EDIT_TIME, Long.class);
    }
    /**
    * @param editTime to edit_time 换算更新时间 set
    */
    public SysExchangeRate setEditTime(Long editTime) {
        set(F_EDIT_TIME, editTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysExchangeRate setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysExchangeRate me(){
        return new SysExchangeRate();
    }

    private static class Mapper implements RowMapper<SysExchangeRate> {

        private Supplier<SysExchangeRate> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysExchangeRate mapRow(ResultSet rs, int rownum) throws SQLException {
            SysExchangeRate bean = supplier.get();
            bean.setId(Utils.toCast(rs.getObject(F_ID), String.class));
            bean.setScur(Utils.toCast(rs.getObject(F_SCUR), String.class));
            bean.setTcur(Utils.toCast(rs.getObject(F_TCUR), String.class));
            bean.setRatio(Utils.toCast(rs.getObject(F_RATIO), BigDecimal.class));
            bean.setTagRatio(Utils.toCast(rs.getObject(F_TAG_RATIO), BigDecimal.class));
            bean.setEditTime(Utils.toCast(rs.getObject(F_EDIT_TIME), Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysExchangeRate> newMapper(){
        return newMapper(SysExchangeRate::new);
    }

    public RowMapper<SysExchangeRate> newMapper(Supplier<SysExchangeRate> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysExchangeRate {
        @Override
        public abstract RowMapper<SysExchangeRate> newMapper();
    }
}
