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
* Date:        14:08 2021/05/17
* Version:     1.0
* Description: CliClienteleInfo实体
*/
@SuppressWarnings("all")
public class CliClienteleInfo extends BaseBean<CliClienteleInfo> {

    /**
    * 表名
    */
    public static final String TABLE_NAME = "cli_clientele_info";

    /**
    * 顾客信息表主键
    */
    public static final String F_ID = "id";
    /**
    * 姓
    */
    public static final String F_CLIENT_SURNAME = "client_surname";
    /**
    * 名
    */
    public static final String F_CLIENT_NAME = "client_name";
    /**
    * 登录名
    */
    public static final String F_LOGIN_NAME = "login_name";
    /**
    * 密码
    */
    public static final String F_PASSWORD = "password";
    /**
    * 电子邮件
    */
    public static final String F_EMAIL = "email";
    /**
    * 电话
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
    * 等级
    */
    public static final String F_LEVEL = "level";
    /**
    * 购买次数
    */
    public static final String F_BUY_TIME = "buy_time";
    /**
    * 购买金额
    */
    public static final String F_BUY_SUM = "buy_sum";
    /**
    * 顾客类型编号
    */
    public static final String F_CLIENT_TYPE_ID = "client_type_id";
    /**
    * 顾客类型名称
    */
    public static final String F_CLIENT_TYPE_NAME = "client_type_name";
    /**
    * 谷歌账号
    */
    public static final String F_GOOGLE_ACCOUNT = "google_account";
    /**
    * fb账号
    */
    public static final String F_FACEBOOK_ACCOUNT = "facebook_account";
    /**
    * 推特账号
    */
    public static final String F_TWITTER_ACCOUNT = "twitter_account";
    /**
    * paypal账号
    */
    public static final String F_PAYPAL_ACCOUNT = "paypal_account";
    /**
    * Whatsapp 账号
    */
    public static final String F_WHATS_APP = "whats_app";
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
    * 0.否1.是（是否启用）
    */
    public static final String F_IS_ENABLE = "is_enable";
    /**
    * 首次购买时间
    */
    public static final String F_FIRST_BUY_TIME = "first_buy_time";
    /**
    * 购买商品总数
    */
    public static final String F_BUY_GOODS_NUMBER = "buy_goods_number";
    /**
    * 币种
    */
    public static final String F_CURRENCY = "currency";
    /**
    * 是否订阅
    */
    public static final String F_IS_SUBSCRIBER = "is_subscriber";
    /**
    * 是否购买
    */
    public static final String F_IS_BUYER = "is_buyer";
    /**
    * 是否已重复购买
    */
    public static final String F_IS_MORE_BUYER = "is_more_buyer";
    /**
    * 公司编号
    */
    public static final String F_COMPANY_ID = "company_id";
    /**
    * 公司名称
    */
    public static final String F_COMPANY_NAME = "company_name";
    /**
    * 用户头像地址
    */
    public static final String F_ICON_IMAGE_URL = "icon_image_url";
    /**
    * 0.系统创建 1.用户注册
    */
    public static final String F_IS_FLAG = "is_Flag";
    /**
    * 邀请码
    */
    public static final String F_INVITATION_CODE = "invitation_code";
    /**
    * 其他联系人
    */
    public static final String F_OTHER_CONTACTS = "other_contacts";
    /**
    * 亚马逊Profile的Url
    */
    public static final String F_AMAZON_PROFILE_URL = "amazon_profile_url";
    /**
    * 亚马逊Profile的截图
    */
    public static final String F_AMAZON_PROFILE_SCREENSHOT = "amazon_profile_screenshot";
    /**
    * 账户是否已认证unauthorized、Authentication、rejected
    */
    public static final String F_ACCOUNT_CERTIFICATION = "account_certification";
    /**
    * 认证备注
    */
    public static final String F_CERTIFICATION_REMARK = "certification_remark";

    @Override
    protected void initBeanValues(){
        put(F_ID, null);
        put(F_CLIENT_SURNAME, null);
        put(F_CLIENT_NAME, null);
        put(F_LOGIN_NAME, null);
        put(F_PASSWORD, null);
        put(F_EMAIL, null);
        put(F_PHONE, null);
        put(F_COUNTRY, null);
        put(F_PROVINCE, null);
        put(F_LEVEL, null);
        put(F_BUY_TIME, null);
        put(F_BUY_SUM, null);
        put(F_CLIENT_TYPE_ID, null);
        put(F_CLIENT_TYPE_NAME, null);
        put(F_GOOGLE_ACCOUNT, null);
        put(F_FACEBOOK_ACCOUNT, null);
        put(F_TWITTER_ACCOUNT, null);
        put(F_PAYPAL_ACCOUNT, null);
        put(F_WHATS_APP, null);
        put(F_REMARK, null);
        put(F_ADD_TIME, null);
        put(F_ADD_USER_ID, null);
        put(F_IS_ENABLE, null);
        put(F_FIRST_BUY_TIME, null);
        put(F_BUY_GOODS_NUMBER, null);
        put(F_CURRENCY, null);
        put(F_IS_SUBSCRIBER, null);
        put(F_IS_BUYER, null);
        put(F_IS_MORE_BUYER, null);
        put(F_COMPANY_ID, null);
        put(F_COMPANY_NAME, null);
        put(F_ICON_IMAGE_URL, null);
        put(F_IS_FLAG, null);
        put(F_INVITATION_CODE, null);
        put(F_OTHER_CONTACTS, null);
        put(F_AMAZON_PROFILE_URL, null);
        put(F_AMAZON_PROFILE_SCREENSHOT, null);
        put(F_ACCOUNT_CERTIFICATION, null);
        put(F_CERTIFICATION_REMARK, null);
    }

    public CliClienteleInfo() {
        super();
    }

    public CliClienteleInfo(Map<String, Object> map) {
        super(map);
    }

    public CliClienteleInfo(String id) {
        super();
        setId(id);
    }

    /**
    * @return id to id 顾客信息表主键<BR/>
    */
    public String getId() {
        return getTypedValue(F_ID, String.class);
    }
    /**
    * @param id to id 顾客信息表主键 set
    */
    public CliClienteleInfo setId(String id) {
        set(F_ID, id);
        return this;
    }
    /**
    * @return client_surname to clientSurname 姓<BR/>
    */
    public String getClientSurname() {
        return getTypedValue(F_CLIENT_SURNAME, String.class);
    }
    /**
    * @param clientSurname to client_surname 姓 set
    */
    public CliClienteleInfo setClientSurname(String clientSurname) {
        set(F_CLIENT_SURNAME, clientSurname);
        return this;
    }
    /**
    * @return client_name to clientName 名<BR/>
    */
    public String getClientName() {
        return getTypedValue(F_CLIENT_NAME, String.class);
    }
    /**
    * @param clientName to client_name 名 set
    */
    public CliClienteleInfo setClientName(String clientName) {
        set(F_CLIENT_NAME, clientName);
        return this;
    }
    /**
    * @return login_name to loginName 登录名<BR/>
    */
    public String getLoginName() {
        return getTypedValue(F_LOGIN_NAME, String.class);
    }
    /**
    * @param loginName to login_name 登录名 set
    */
    public CliClienteleInfo setLoginName(String loginName) {
        set(F_LOGIN_NAME, loginName);
        return this;
    }
    /**
    * @return password to password 密码<BR/>
    */
    public String getPassword() {
        return getTypedValue(F_PASSWORD, String.class);
    }
    /**
    * @param password to password 密码 set
    */
    public CliClienteleInfo setPassword(String password) {
        set(F_PASSWORD, password);
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
    public CliClienteleInfo setEmail(String email) {
        set(F_EMAIL, email);
        return this;
    }
    /**
    * @return phone to phone 电话<BR/>
    */
    public String getPhone() {
        return getTypedValue(F_PHONE, String.class);
    }
    /**
    * @param phone to phone 电话 set
    */
    public CliClienteleInfo setPhone(String phone) {
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
    public CliClienteleInfo setCountry(String country) {
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
    public CliClienteleInfo setProvince(String province) {
        set(F_PROVINCE, province);
        return this;
    }
    /**
    * @return level to level 等级<BR/>
    */
    public Integer getLevel() {
        return getTypedValue(F_LEVEL, Integer.class);
    }
    /**
    * @param level to level 等级 set
    */
    public CliClienteleInfo setLevel(Integer level) {
        set(F_LEVEL, level);
        return this;
    }
    /**
    * @return buy_time to buyTime 购买次数<BR/>
    */
    public Integer getBuyTime() {
        return getTypedValue(F_BUY_TIME, Integer.class);
    }
    /**
    * @param buyTime to buy_time 购买次数 set
    */
    public CliClienteleInfo setBuyTime(Integer buyTime) {
        set(F_BUY_TIME, buyTime);
        return this;
    }
    /**
    * @return buy_sum to buySum 购买金额<BR/>
    */
    public Long getBuySum() {
        return getTypedValue(F_BUY_SUM, Long.class);
    }
    /**
    * @param buySum to buy_sum 购买金额 set
    */
    public CliClienteleInfo setBuySum(Long buySum) {
        set(F_BUY_SUM, buySum);
        return this;
    }
    /**
    * @return client_type_id to clientTypeId 顾客类型编号<BR/>
    */
    public String getClientTypeId() {
        return getTypedValue(F_CLIENT_TYPE_ID, String.class);
    }
    /**
    * @param clientTypeId to client_type_id 顾客类型编号 set
    */
    public CliClienteleInfo setClientTypeId(String clientTypeId) {
        set(F_CLIENT_TYPE_ID, clientTypeId);
        return this;
    }
    /**
    * @return client_type_name to clientTypeName 顾客类型名称<BR/>
    */
    public String getClientTypeName() {
        return getTypedValue(F_CLIENT_TYPE_NAME, String.class);
    }
    /**
    * @param clientTypeName to client_type_name 顾客类型名称 set
    */
    public CliClienteleInfo setClientTypeName(String clientTypeName) {
        set(F_CLIENT_TYPE_NAME, clientTypeName);
        return this;
    }
    /**
    * @return google_account to googleAccount 谷歌账号<BR/>
    */
    public String getGoogleAccount() {
        return getTypedValue(F_GOOGLE_ACCOUNT, String.class);
    }
    /**
    * @param googleAccount to google_account 谷歌账号 set
    */
    public CliClienteleInfo setGoogleAccount(String googleAccount) {
        set(F_GOOGLE_ACCOUNT, googleAccount);
        return this;
    }
    /**
    * @return facebook_account to facebookAccount fb账号<BR/>
    */
    public String getFacebookAccount() {
        return getTypedValue(F_FACEBOOK_ACCOUNT, String.class);
    }
    /**
    * @param facebookAccount to facebook_account fb账号 set
    */
    public CliClienteleInfo setFacebookAccount(String facebookAccount) {
        set(F_FACEBOOK_ACCOUNT, facebookAccount);
        return this;
    }
    /**
    * @return twitter_account to twitterAccount 推特账号<BR/>
    */
    public String getTwitterAccount() {
        return getTypedValue(F_TWITTER_ACCOUNT, String.class);
    }
    /**
    * @param twitterAccount to twitter_account 推特账号 set
    */
    public CliClienteleInfo setTwitterAccount(String twitterAccount) {
        set(F_TWITTER_ACCOUNT, twitterAccount);
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
    public CliClienteleInfo setPaypalAccount(String paypalAccount) {
        set(F_PAYPAL_ACCOUNT, paypalAccount);
        return this;
    }
    /**
    * @return whats_app to whatsApp Whatsapp 账号<BR/>
    */
    public String getWhatsApp() {
        return getTypedValue(F_WHATS_APP, String.class);
    }
    /**
    * @param whatsApp to whats_app Whatsapp 账号 set
    */
    public CliClienteleInfo setWhatsApp(String whatsApp) {
        set(F_WHATS_APP, whatsApp);
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
    public CliClienteleInfo setRemark(String remark) {
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
    public CliClienteleInfo setAddTime(Long addTime) {
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
    public CliClienteleInfo setAddUserId(String addUserId) {
        set(F_ADD_USER_ID, addUserId);
        return this;
    }
    /**
    * @return is_enable to isEnable 0.否1.是（是否启用）<BR/>
    */
    public Integer getIsEnable() {
        return getTypedValue(F_IS_ENABLE, Integer.class);
    }
    /**
    * @param isEnable to is_enable 0.否1.是（是否启用） set
    */
    public CliClienteleInfo setIsEnable(Integer isEnable) {
        set(F_IS_ENABLE, isEnable);
        return this;
    }
    /**
    * @return first_buy_time to firstBuyTime 首次购买时间<BR/>
    */
    public Long getFirstBuyTime() {
        return getTypedValue(F_FIRST_BUY_TIME, Long.class);
    }
    /**
    * @param firstBuyTime to first_buy_time 首次购买时间 set
    */
    public CliClienteleInfo setFirstBuyTime(Long firstBuyTime) {
        set(F_FIRST_BUY_TIME, firstBuyTime);
        return this;
    }
    /**
    * @return buy_goods_number to buyGoodsNumber 购买商品总数<BR/>
    */
    public Integer getBuyGoodsNumber() {
        return getTypedValue(F_BUY_GOODS_NUMBER, Integer.class);
    }
    /**
    * @param buyGoodsNumber to buy_goods_number 购买商品总数 set
    */
    public CliClienteleInfo setBuyGoodsNumber(Integer buyGoodsNumber) {
        set(F_BUY_GOODS_NUMBER, buyGoodsNumber);
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
    public CliClienteleInfo setCurrency(String currency) {
        set(F_CURRENCY, currency);
        return this;
    }
    /**
    * @return is_subscriber to isSubscriber 是否订阅<BR/>
    */
    public Integer getIsSubscriber() {
        return getTypedValue(F_IS_SUBSCRIBER, Integer.class);
    }
    /**
    * @param isSubscriber to is_subscriber 是否订阅 set
    */
    public CliClienteleInfo setIsSubscriber(Integer isSubscriber) {
        set(F_IS_SUBSCRIBER, isSubscriber);
        return this;
    }
    /**
    * @return is_buyer to isBuyer 是否购买<BR/>
    */
    public Integer getIsBuyer() {
        return getTypedValue(F_IS_BUYER, Integer.class);
    }
    /**
    * @param isBuyer to is_buyer 是否购买 set
    */
    public CliClienteleInfo setIsBuyer(Integer isBuyer) {
        set(F_IS_BUYER, isBuyer);
        return this;
    }
    /**
    * @return is_more_buyer to isMoreBuyer 是否已重复购买<BR/>
    */
    public Integer getIsMoreBuyer() {
        return getTypedValue(F_IS_MORE_BUYER, Integer.class);
    }
    /**
    * @param isMoreBuyer to is_more_buyer 是否已重复购买 set
    */
    public CliClienteleInfo setIsMoreBuyer(Integer isMoreBuyer) {
        set(F_IS_MORE_BUYER, isMoreBuyer);
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
    public CliClienteleInfo setCompanyId(String companyId) {
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
    public CliClienteleInfo setCompanyName(String companyName) {
        set(F_COMPANY_NAME, companyName);
        return this;
    }
    /**
    * @return icon_image_url to iconImageUrl 用户头像地址<BR/>
    */
    public String getIconImageUrl() {
        return getTypedValue(F_ICON_IMAGE_URL, String.class);
    }
    /**
    * @param iconImageUrl to icon_image_url 用户头像地址 set
    */
    public CliClienteleInfo setIconImageUrl(String iconImageUrl) {
        set(F_ICON_IMAGE_URL, iconImageUrl);
        return this;
    }
    /**
    * @return is_Flag to isFlag 0.系统创建 1.用户注册<BR/>
    */
    public Integer getIsFlag() {
        return getTypedValue(F_IS_FLAG, Integer.class);
    }
    /**
    * @param isFlag to is_Flag 0.系统创建 1.用户注册 set
    */
    public CliClienteleInfo setIsFlag(Integer isFlag) {
        set(F_IS_FLAG, isFlag);
        return this;
    }
    /**
    * @return invitation_code to invitationCode 邀请码<BR/>
    */
    public String getInvitationCode() {
        return getTypedValue(F_INVITATION_CODE, String.class);
    }
    /**
    * @param invitationCode to invitation_code 邀请码 set
    */
    public CliClienteleInfo setInvitationCode(String invitationCode) {
        set(F_INVITATION_CODE, invitationCode);
        return this;
    }
    /**
    * @return other_contacts to otherContacts 其他联系人<BR/>
    */
    public String getOtherContacts() {
        return getTypedValue(F_OTHER_CONTACTS, String.class);
    }
    /**
    * @param otherContacts to other_contacts 其他联系人 set
    */
    public CliClienteleInfo setOtherContacts(String otherContacts) {
        set(F_OTHER_CONTACTS, otherContacts);
        return this;
    }
    /**
    * @return amazon_profile_url to amazonProfileUrl 亚马逊Profile的Url<BR/>
    */
    public String getAmazonProfileUrl() {
        return getTypedValue(F_AMAZON_PROFILE_URL, String.class);
    }
    /**
    * @param amazonProfileUrl to amazon_profile_url 亚马逊Profile的Url set
    */
    public CliClienteleInfo setAmazonProfileUrl(String amazonProfileUrl) {
        set(F_AMAZON_PROFILE_URL, amazonProfileUrl);
        return this;
    }
    /**
    * @return amazon_profile_screenshot to amazonProfileScreenshot 亚马逊Profile的截图<BR/>
    */
    public String getAmazonProfileScreenshot() {
        return getTypedValue(F_AMAZON_PROFILE_SCREENSHOT, String.class);
    }
    /**
    * @param amazonProfileScreenshot to amazon_profile_screenshot 亚马逊Profile的截图 set
    */
    public CliClienteleInfo setAmazonProfileScreenshot(String amazonProfileScreenshot) {
        set(F_AMAZON_PROFILE_SCREENSHOT, amazonProfileScreenshot);
        return this;
    }
    /**
    * @return account_certification to accountCertification 账户是否已认证unauthorized、Authentication、rejected<BR/>
    */
    public String getAccountCertification() {
        return getTypedValue(F_ACCOUNT_CERTIFICATION, String.class);
    }
    /**
    * @param accountCertification to account_certification 账户是否已认证unauthorized、Authentication、rejected set
    */
    public CliClienteleInfo setAccountCertification(String accountCertification) {
        set(F_ACCOUNT_CERTIFICATION, accountCertification);
        return this;
    }
    /**
    * @return certification_remark to certificationRemark 认证备注<BR/>
    */
    public String getCertificationRemark() {
        return getTypedValue(F_CERTIFICATION_REMARK, String.class);
    }
    /**
    * @param certificationRemark to certification_remark 认证备注 set
    */
    public CliClienteleInfo setCertificationRemark(String certificationRemark) {
        set(F_CERTIFICATION_REMARK, certificationRemark);
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public Object getPrimaryKey() {
        return getId();
    }

    @Override
    public CliClienteleInfo setPrimaryKey(Object key) {
        setId(Utils.toCast(key, String.class));
        return this;
    }

    @JSONField(serialize = false)
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public static CliClienteleInfo me(){
        return new CliClienteleInfo();
    }

    private static class Mapper implements RowMapper<CliClienteleInfo> {

        private Supplier<CliClienteleInfo> supplier;

        public Mapper(Supplier supplier) {
            this.supplier = supplier;
        }

        @Override
        public CliClienteleInfo mapRow(ResultSet rs, int rownum) throws SQLException {

            ResultSetMetaData rsmd = rs.getMetaData();
            int cc = rsmd.getColumnCount();
            Set<String> columnsName = new HashSet<>(cc);
            for(int i = 1; i<= cc; i++) {
                columnsName.add(rsmd.getColumnName(i));
            }

            CliClienteleInfo bean = supplier.get();
            bean.setId(Utils.toCast(columnsName.contains(F_ID) ? rs.getObject(F_ID) : null, String.class));
            bean.setClientSurname(Utils.toCast(columnsName.contains(F_CLIENT_SURNAME) ? rs.getObject(F_CLIENT_SURNAME) : null, String.class));
            bean.setClientName(Utils.toCast(columnsName.contains(F_CLIENT_NAME) ? rs.getObject(F_CLIENT_NAME) : null, String.class));
            bean.setLoginName(Utils.toCast(columnsName.contains(F_LOGIN_NAME) ? rs.getObject(F_LOGIN_NAME) : null, String.class));
            bean.setPassword(Utils.toCast(columnsName.contains(F_PASSWORD) ? rs.getObject(F_PASSWORD) : null, String.class));
            bean.setEmail(Utils.toCast(columnsName.contains(F_EMAIL) ? rs.getObject(F_EMAIL) : null, String.class));
            bean.setPhone(Utils.toCast(columnsName.contains(F_PHONE) ? rs.getObject(F_PHONE) : null, String.class));
            bean.setCountry(Utils.toCast(columnsName.contains(F_COUNTRY) ? rs.getObject(F_COUNTRY) : null, String.class));
            bean.setProvince(Utils.toCast(columnsName.contains(F_PROVINCE) ? rs.getObject(F_PROVINCE) : null, String.class));
            bean.setLevel(Utils.toCast(columnsName.contains(F_LEVEL) ? rs.getObject(F_LEVEL) : null, Integer.class));
            bean.setBuyTime(Utils.toCast(columnsName.contains(F_BUY_TIME) ? rs.getObject(F_BUY_TIME) : null, Integer.class));
            bean.setBuySum(Utils.toCast(columnsName.contains(F_BUY_SUM) ? rs.getObject(F_BUY_SUM) : null, Long.class));
            bean.setClientTypeId(Utils.toCast(columnsName.contains(F_CLIENT_TYPE_ID) ? rs.getObject(F_CLIENT_TYPE_ID) : null, String.class));
            bean.setClientTypeName(Utils.toCast(columnsName.contains(F_CLIENT_TYPE_NAME) ? rs.getObject(F_CLIENT_TYPE_NAME) : null, String.class));
            bean.setGoogleAccount(Utils.toCast(columnsName.contains(F_GOOGLE_ACCOUNT) ? rs.getObject(F_GOOGLE_ACCOUNT) : null, String.class));
            bean.setFacebookAccount(Utils.toCast(columnsName.contains(F_FACEBOOK_ACCOUNT) ? rs.getObject(F_FACEBOOK_ACCOUNT) : null, String.class));
            bean.setTwitterAccount(Utils.toCast(columnsName.contains(F_TWITTER_ACCOUNT) ? rs.getObject(F_TWITTER_ACCOUNT) : null, String.class));
            bean.setPaypalAccount(Utils.toCast(columnsName.contains(F_PAYPAL_ACCOUNT) ? rs.getObject(F_PAYPAL_ACCOUNT) : null, String.class));
            bean.setWhatsApp(Utils.toCast(columnsName.contains(F_WHATS_APP) ? rs.getObject(F_WHATS_APP) : null, String.class));
            bean.setRemark(Utils.toCast(columnsName.contains(F_REMARK) ? rs.getObject(F_REMARK) : null, String.class));
            bean.setAddTime(Utils.toCast(columnsName.contains(F_ADD_TIME) ? rs.getObject(F_ADD_TIME) : null, Long.class));
            bean.setAddUserId(Utils.toCast(columnsName.contains(F_ADD_USER_ID) ? rs.getObject(F_ADD_USER_ID) : null, String.class));
            bean.setIsEnable(Utils.toCast(columnsName.contains(F_IS_ENABLE) ? rs.getObject(F_IS_ENABLE) : null, Integer.class));
            bean.setFirstBuyTime(Utils.toCast(columnsName.contains(F_FIRST_BUY_TIME) ? rs.getObject(F_FIRST_BUY_TIME) : null, Long.class));
            bean.setBuyGoodsNumber(Utils.toCast(columnsName.contains(F_BUY_GOODS_NUMBER) ? rs.getObject(F_BUY_GOODS_NUMBER) : null, Integer.class));
            bean.setCurrency(Utils.toCast(columnsName.contains(F_CURRENCY) ? rs.getObject(F_CURRENCY) : null, String.class));
            bean.setIsSubscriber(Utils.toCast(columnsName.contains(F_IS_SUBSCRIBER) ? rs.getObject(F_IS_SUBSCRIBER) : null, Integer.class));
            bean.setIsBuyer(Utils.toCast(columnsName.contains(F_IS_BUYER) ? rs.getObject(F_IS_BUYER) : null, Integer.class));
            bean.setIsMoreBuyer(Utils.toCast(columnsName.contains(F_IS_MORE_BUYER) ? rs.getObject(F_IS_MORE_BUYER) : null, Integer.class));
            bean.setCompanyId(Utils.toCast(columnsName.contains(F_COMPANY_ID) ? rs.getObject(F_COMPANY_ID) : null, String.class));
            bean.setCompanyName(Utils.toCast(columnsName.contains(F_COMPANY_NAME) ? rs.getObject(F_COMPANY_NAME) : null, String.class));
            bean.setIconImageUrl(Utils.toCast(columnsName.contains(F_ICON_IMAGE_URL) ? rs.getObject(F_ICON_IMAGE_URL) : null, String.class));
            bean.setIsFlag(Utils.toCast(columnsName.contains(F_IS_FLAG) ? rs.getObject(F_IS_FLAG) : null, Integer.class));
            bean.setInvitationCode(Utils.toCast(columnsName.contains(F_INVITATION_CODE) ? rs.getObject(F_INVITATION_CODE) : null, String.class));
            bean.setOtherContacts(Utils.toCast(columnsName.contains(F_OTHER_CONTACTS) ? rs.getObject(F_OTHER_CONTACTS) : null, String.class));
            bean.setAmazonProfileUrl(Utils.toCast(columnsName.contains(F_AMAZON_PROFILE_URL) ? rs.getObject(F_AMAZON_PROFILE_URL) : null, String.class));
            bean.setAmazonProfileScreenshot(Utils.toCast(columnsName.contains(F_AMAZON_PROFILE_SCREENSHOT) ? rs.getObject(F_AMAZON_PROFILE_SCREENSHOT) : null, String.class));
            bean.setAccountCertification(Utils.toCast(columnsName.contains(F_ACCOUNT_CERTIFICATION) ? rs.getObject(F_ACCOUNT_CERTIFICATION) : null, String.class));
            bean.setCertificationRemark(Utils.toCast(columnsName.contains(F_CERTIFICATION_REMARK) ? rs.getObject(F_CERTIFICATION_REMARK) : null, String.class));
            bean.clearModifyKeys();
            return bean;
        }
    }

    @Override
    public RowMapper<CliClienteleInfo> newMapper(){
        return newMapper(CliClienteleInfo::new);
    }

    public RowMapper<CliClienteleInfo> newMapper(Supplier<CliClienteleInfo> supplier){
        return new Mapper(supplier);
    }

    public static abstract class Sub extends CliClienteleInfo {
        @Override
        public abstract RowMapper<CliClienteleInfo> newMapper();
    }
}
