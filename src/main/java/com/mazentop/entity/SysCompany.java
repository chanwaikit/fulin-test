package com.mazentop.entity;
import com.alibaba.fastjson.annotation.JSONField;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
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
* Date:        11:34 2020/03/16
* Company:     美赞拓
* Version:     1.0
* Description: SysCompany实体
*/
@SuppressWarnings("all")
public class SysCompany extends BaseBean<SysCompany> {

    /**
     * 表名
     */
    public static final String TABLE_NAME = "sys_company";

    /**
     * 编号
     */
    public static final String F_ID = "id";
    /**
     * 公司名称
     */
    public static final String F_NAME = "name";
    /**
     * 公司简称
     */
    public static final String F_SHORT_NAME = "short_name";
    /**
     * 公司统一编码
     */
    public static final String F_BASE_CODE = "base_code";
    /**
     * 公司地址
     */
    public static final String F_ADDRESS = "address";
    /**
     * 公司营业执照
     */
    public static final String F_BUSINESS_LICENSE_IMAGE_URL = "business_license_image_url";
    /**
     * 公司法人
     */
    public static final String F_COMPANY_OWNER = "company_owner";
    /**
     * 公司成立时间
     */
    public static final String F_COMPANY_CREATE_TIME = "company_create_time";
    /**
     * 公司联系人
     */
    public static final String F_COMPANY_CONTACT = "company_contact";
    /**
     * 公司联系地址
     */
    public static final String F_COMPANY_CONTACT_ADDRESS = "company_contact_address";
    /**
     * 联系人电话
     */
    public static final String F_CONTACT_PHONE = "contact_phone";
    /**
     * 联系人邮件
     */
    public static final String F_CONTACT_MAIL = "contact_mail";
    /**
     * 传真
     */
    public static final String F_FAX = "fax";
    /**
     * 公司说明
     */
    public static final String F_COMPANY_CONTENT = "company_content";
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

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_NAME, null);
        put(F_SHORT_NAME, null);
        put(F_BASE_CODE, null);
        put(F_ADDRESS, null);
        put(F_BUSINESS_LICENSE_IMAGE_URL, null);
        put(F_COMPANY_OWNER, null);
        put(F_COMPANY_CREATE_TIME, null);
        put(F_COMPANY_CONTACT, null);
        put(F_COMPANY_CONTACT_ADDRESS, null);
        put(F_CONTACT_PHONE, null);
        put(F_CONTACT_MAIL, null);
        put(F_FAX, null);
        put(F_COMPANY_CONTENT, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
    }

    public SysCompany() {
        super();
    }

    public SysCompany(Map<String, Object> map) {
        super(map);
    }

    public SysCompany(String id) {
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
    public SysCompany setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
     * @return name to name 公司名称<BR/>
     */
    public String getName() {
        return getTypedValue(F_NAME, String.class);
    }
    /**
     * @param name to name 公司名称 set
     */
    public SysCompany setName(String name) {
        set(F_NAME, name);
        return this;
    }
    /**
     * @return short_name to shortName 公司简称<BR/>
     */
    public String getShortName() {
        return getTypedValue(F_SHORT_NAME, String.class);
    }
    /**
     * @param shortName to short_name 公司简称 set
     */
    public SysCompany setShortName(String shortName) {
        set(F_SHORT_NAME, shortName);
        return this;
    }
    /**
     * @return base_code to baseCode 公司统一编码<BR/>
     */
    public String getBaseCode() {
        return getTypedValue(F_BASE_CODE, String.class);
    }
    /**
     * @param baseCode to base_code 公司统一编码 set
     */
    public SysCompany setBaseCode(String baseCode) {
        set(F_BASE_CODE, baseCode);
        return this;
    }
    /**
     * @return address to address 公司地址<BR/>
     */
    public String getAddress() {
        return getTypedValue(F_ADDRESS, String.class);
    }
    /**
     * @param address to address 公司地址 set
     */
    public SysCompany setAddress(String address) {
        set(F_ADDRESS, address);
        return this;
    }
    /**
     * @return business_license_image_url to businessLicenseImageUrl 公司营业执照<BR/>
     */
    public String getBusinessLicenseImageUrl() {
        return getTypedValue(F_BUSINESS_LICENSE_IMAGE_URL, String.class);
    }
    /**
     * @param businessLicenseImageUrl to business_license_image_url 公司营业执照 set
     */
    public SysCompany setBusinessLicenseImageUrl(String businessLicenseImageUrl) {
        set(F_BUSINESS_LICENSE_IMAGE_URL, businessLicenseImageUrl);
        return this;
    }
    /**
     * @return company_owner to companyOwner 公司法人<BR/>
     */
    public String getCompanyOwner() {
        return getTypedValue(F_COMPANY_OWNER, String.class);
    }
    /**
     * @param companyOwner to company_owner 公司法人 set
     */
    public SysCompany setCompanyOwner(String companyOwner) {
        set(F_COMPANY_OWNER, companyOwner);
        return this;
    }
    /**
     * @return company_create_time to companyCreateTime 公司成立时间<BR/>
     */
    public Long getCompanyCreateTime() {
        return getTypedValue(F_COMPANY_CREATE_TIME, Long.class);
    }
    /**
     * @param companyCreateTime to company_create_time 公司成立时间 set
     */
    public SysCompany setCompanyCreateTime(Long companyCreateTime) {
        set(F_COMPANY_CREATE_TIME, companyCreateTime);
        return this;
    }
    /**
     * @return company_contact to companyContact 公司联系人<BR/>
     */
    public String getCompanyContact() {
        return getTypedValue(F_COMPANY_CONTACT, String.class);
    }
    /**
     * @param companyContact to company_contact 公司联系人 set
     */
    public SysCompany setCompanyContact(String companyContact) {
        set(F_COMPANY_CONTACT, companyContact);
        return this;
    }
    /**
     * @return company_contact_address to companyContactAddress 公司联系地址<BR/>
     */
    public String getCompanyContactAddress() {
        return getTypedValue(F_COMPANY_CONTACT_ADDRESS, String.class);
    }
    /**
     * @param companyContactAddress to company_contact_address 公司联系地址 set
     */
    public SysCompany setCompanyContactAddress(String companyContactAddress) {
        set(F_COMPANY_CONTACT_ADDRESS, companyContactAddress);
        return this;
    }
    /**
     * @return contact_phone to contactPhone 联系人电话<BR/>
     */
    public String getContactPhone() {
        return getTypedValue(F_CONTACT_PHONE, String.class);
    }
    /**
     * @param contactPhone to contact_phone 联系人电话 set
     */
    public SysCompany setContactPhone(String contactPhone) {
        set(F_CONTACT_PHONE, contactPhone);
        return this;
    }
    /**
     * @return contact_mail to contactMail 联系人邮件<BR/>
     */
    public String getContactMail() {
        return getTypedValue(F_CONTACT_MAIL, String.class);
    }
    /**
     * @param contactMail to contact_mail 联系人邮件 set
     */
    public SysCompany setContactMail(String contactMail) {
        set(F_CONTACT_MAIL, contactMail);
        return this;
    }
    /**
     * @return fax to fax 传真<BR/>
     */
    public String getFax() {
        return getTypedValue(F_FAX, String.class);
    }
    /**
     * @param fax to fax 传真 set
     */
    public SysCompany setFax(String fax) {
        set(F_FAX, fax);
        return this;
    }
    /**
     * @return company_content to companyContent 公司说明<BR/>
     */
    public String getCompanyContent() {
        return getTypedValue(F_COMPANY_CONTENT, String.class);
    }
    /**
     * @param companyContent to company_content 公司说明 set
     */
    public SysCompany setCompanyContent(String companyContent) {
        set(F_COMPANY_CONTENT, companyContent);
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
    public SysCompany setRemark(String remark) {
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
    public SysCompany setAddTime(Long addTime) {
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
    public SysCompany setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public SysCompany setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static SysCompany me(){
        return new SysCompany();
    }

    private static class Mapper implements RowMapper<SysCompany> {

        private Supplier<SysCompany> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public SysCompany mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            SysCompany bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setName(Utils.toCast(columnsName.contains(F_NAME) ? rs.getObject(F_NAME) : null, String.class));
            bean.setShortName(Utils.toCast(columnsName.contains(F_SHORT_NAME) ? rs.getObject(F_SHORT_NAME) : null, String.class));
            bean.setBaseCode(Utils.toCast(columnsName.contains(F_BASE_CODE) ? rs.getObject(F_BASE_CODE) : null, String.class));
            bean.setAddress(Utils.toCast(columnsName.contains(F_ADDRESS) ? rs.getObject(F_ADDRESS) : null, String.class));
            bean.setBusinessLicenseImageUrl(Utils.toCast(columnsName.contains(F_BUSINESS_LICENSE_IMAGE_URL) ? rs.getObject(F_BUSINESS_LICENSE_IMAGE_URL) : null, String.class));
            bean.setCompanyOwner(Utils.toCast(columnsName.contains(F_COMPANY_OWNER) ? rs.getObject(F_COMPANY_OWNER) : null, String.class));
            bean.setCompanyCreateTime(Utils.toCast(columnsName.contains(F_COMPANY_CREATE_TIME) ? rs.getObject(F_COMPANY_CREATE_TIME) : null, Long.class));
            bean.setCompanyContact(Utils.toCast(columnsName.contains(F_COMPANY_CONTACT) ? rs.getObject(F_COMPANY_CONTACT) : null, String.class));
            bean.setCompanyContactAddress(Utils.toCast(columnsName.contains(F_COMPANY_CONTACT_ADDRESS) ? rs.getObject(F_COMPANY_CONTACT_ADDRESS) : null, String.class));
            bean.setContactPhone(Utils.toCast(columnsName.contains(F_CONTACT_PHONE) ? rs.getObject(F_CONTACT_PHONE) : null, String.class));
            bean.setContactMail(Utils.toCast(columnsName.contains(F_CONTACT_MAIL) ? rs.getObject(F_CONTACT_MAIL) : null, String.class));
            bean.setFax(Utils.toCast(columnsName.contains(F_FAX) ? rs.getObject(F_FAX) : null, String.class));
            bean.setCompanyContent(Utils.toCast(columnsName.contains(F_COMPANY_CONTENT) ? rs.getObject(F_COMPANY_CONTENT) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<SysCompany> newMapper(){
        return newMapper(SysCompany::new);
    }

    public RowMapper<SysCompany> newMapper(Supplier<SysCompany> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends SysCompany {
        @Override
        public abstract RowMapper<SysCompany> newMapper();
    }
}
