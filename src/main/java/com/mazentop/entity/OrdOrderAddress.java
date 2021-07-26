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
* Date:        16:56 2020/11/10
* Version:     1.0
* Description: OrdOrderAddress实体
*/
@SuppressWarnings("all")
public class OrdOrderAddress extends BaseBean<OrdOrderAddress> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "ord_order_address";

    /**
    * 主键
    */
    public static final String F_ID = "id";
    /**
    * 发票 invoice 收货地址 shipping
    */
    public static final String F_TYPE = "type";
    /**
    * 姓
    */
    public static final String F_SURNAME = "surname";
    /**
    * 名
    */
    public static final String F_NAME = "name";
    /**
    * 电子邮件
    */
    public static final String F_EMAIL = "email";
    /**
    * 手机
    */
    public static final String F_PHONE = "phone";
    /**
    * 国家
    */
    public static final String F_COUNTRY = "country";
    /**
    * 省
    */
    public static final String F_PROVINCE = "province";
    /**
    * 城市
    */
    public static final String F_CITY = "city";
    /**
    * 街道
    */
    public static final String F_SHEET = "sheet";
    /**
    * 地址
    */
    public static final String F_ADDRESS = "address";
    /**
    * 邮编
    */
    public static final String F_POST = "post";
    /**
    * 备注
    */
    public static final String F_REMARK = "remark";
    /**
    * 添加时间
    */
    public static final String F_ADD_TIME = "add_time";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_TYPE, null);
        put(F_SURNAME, null);
        put(F_NAME, null);
        put(F_EMAIL, null);
        put(F_PHONE, null);
        put(F_COUNTRY, null);
        put(F_PROVINCE, null);
        put(F_CITY, null);
        put(F_SHEET, null);
        put(F_ADDRESS, null);
        put(F_POST, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
    }

    public OrdOrderAddress() {
        super();
    }

    public OrdOrderAddress(Map<String, Object> map) {
        super(map);
    }

    public OrdOrderAddress(String id) {
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
    public OrdOrderAddress setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return type to type 发票 invoice 收货地址 shipping<BR/>
    */
    public String getType() {
        return getTypedValue(F_TYPE, String.class);
    }
    /**
    * @param type to type 发票 invoice 收货地址 shipping set
    */
    public OrdOrderAddress setType(String type) {
        set(F_TYPE, type);
        return this;
    }
    /**
    * @return surname to surname 姓<BR/>
    */
    public String getSurname() {
        return getTypedValue(F_SURNAME, String.class);
    }
    /**
    * @param surname to surname 姓 set
    */
    public OrdOrderAddress setSurname(String surname) {
        set(F_SURNAME, surname);
        return this;
    }
    /**
    * @return name to name 名<BR/>
    */
    public String getName() {
        return getTypedValue(F_NAME, String.class);
    }
    /**
    * @param name to name 名 set
    */
    public OrdOrderAddress setName(String name) {
        set(F_NAME, name);
        return this;
    }
    /**
    * @return email to email 电子邮件<BR/>
    */
    public String getEmail() {
        return getTypedValue(F_EMAIL, String.class);
    }
    /**
    * @param email to email 电子邮件 set
    */
    public OrdOrderAddress setEmail(String email) {
        set(F_EMAIL, email);
        return this;
    }
    /**
    * @return phone to phone 手机<BR/>
    */
    public String getPhone() {
        return getTypedValue(F_PHONE, String.class);
    }
    /**
    * @param phone to phone 手机 set
    */
    public OrdOrderAddress setPhone(String phone) {
        set(F_PHONE, phone);
        return this;
    }
    /**
    * @return country to country 国家<BR/>
    */
    public String getCountry() {
        return getTypedValue(F_COUNTRY, String.class);
    }
    /**
    * @param country to country 国家 set
    */
    public OrdOrderAddress setCountry(String country) {
        set(F_COUNTRY, country);
        return this;
    }
    /**
    * @return province to province 省<BR/>
    */
    public String getProvince() {
        return getTypedValue(F_PROVINCE, String.class);
    }
    /**
    * @param province to province 省 set
    */
    public OrdOrderAddress setProvince(String province) {
        set(F_PROVINCE, province);
        return this;
    }
    /**
    * @return city to city 城市<BR/>
    */
    public String getCity() {
        return getTypedValue(F_CITY, String.class);
    }
    /**
    * @param city to city 城市 set
    */
    public OrdOrderAddress setCity(String city) {
        set(F_CITY, city);
        return this;
    }
    /**
    * @return sheet to sheet 街道<BR/>
    */
    public String getSheet() {
        return getTypedValue(F_SHEET, String.class);
    }
    /**
    * @param sheet to sheet 街道 set
    */
    public OrdOrderAddress setSheet(String sheet) {
        set(F_SHEET, sheet);
        return this;
    }
    /**
    * @return address to address 地址<BR/>
    */
    public String getAddress() {
        return getTypedValue(F_ADDRESS, String.class);
    }
    /**
    * @param address to address 地址 set
    */
    public OrdOrderAddress setAddress(String address) {
        set(F_ADDRESS, address);
        return this;
    }
    /**
    * @return post to post 邮编<BR/>
    */
    public String getPost() {
        return getTypedValue(F_POST, String.class);
    }
    /**
    * @param post to post 邮编 set
    */
    public OrdOrderAddress setPost(String post) {
        set(F_POST, post);
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
    public OrdOrderAddress setRemark(String remark) {
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
    public OrdOrderAddress setAddTime(Long addTime) {
        set(F_ADD_TIME, addTime);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public OrdOrderAddress setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static OrdOrderAddress me(){
        return new OrdOrderAddress();
    }

    private static class Mapper implements RowMapper<OrdOrderAddress> {

        private Supplier<OrdOrderAddress> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public OrdOrderAddress mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            OrdOrderAddress bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setType(Utils.toCast(columnsName.contains(F_TYPE) ? rs.getObject(F_TYPE) : null, String.class));
            bean.setSurname(Utils.toCast(columnsName.contains(F_SURNAME) ? rs.getObject(F_SURNAME) : null, String.class));
            bean.setName(Utils.toCast(columnsName.contains(F_NAME) ? rs.getObject(F_NAME) : null, String.class));
            bean.setEmail(Utils.toCast(columnsName.contains(F_EMAIL) ? rs.getObject(F_EMAIL) : null, String.class));
            bean.setPhone(Utils.toCast(columnsName.contains(F_PHONE) ? rs.getObject(F_PHONE) : null, String.class));
            bean.setCountry(Utils.toCast(columnsName.contains(F_COUNTRY) ? rs.getObject(F_COUNTRY) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setCity(Utils.toCast(columnsName.contains(F_CITY) ? rs.getObject(F_CITY) : null, String.class));
            bean.setSheet(Utils.toCast(columnsName.contains(F_SHEET) ? rs.getObject(F_SHEET) : null, String.class));
            bean.setAddress(Utils.toCast(columnsName.contains(F_ADDRESS) ? rs.getObject(F_ADDRESS) : null, String.class));
            bean.setPost(Utils.toCast(columnsName.contains(F_POST) ? rs.getObject(F_POST) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<OrdOrderAddress> newMapper(){
        return newMapper(OrdOrderAddress::new);
    }

    public RowMapper<OrdOrderAddress> newMapper(Supplier<OrdOrderAddress> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends OrdOrderAddress {
        @Override
        public abstract RowMapper<OrdOrderAddress> newMapper();
    }
}
