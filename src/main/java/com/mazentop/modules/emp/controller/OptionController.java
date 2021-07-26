package com.mazentop.modules.emp.controller;


import com.mazentop.entity.SysOptions;
import com.mazentop.model.OptionsEnum;
import com.mazentop.modules.emp.dto.SiteSettingDto;
import com.mazentop.modules.emp.service.OptionService;
import com.mztframework.SimpleFile;
import com.mztframework.cache.Options;
import com.mztframework.commons.DateUtil;
import com.mztframework.commons.Maps;
import com.mztframework.commons.Utils;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.exception.ToastException;
import com.mztframework.file.service.IUploadService;
import com.mztframework.logging.annotation.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/rest/{api_version}/system/option")
public class OptionController  {

    @Autowired
    OptionService optionService;

    @Autowired
    IUploadService uploadService;

    @PostMapping("/new")
    public Result doOptionNew(HttpServletRequest request) {
        return Result.build(() -> {
            Map<String, String> filesMap = getUploadFilesMap(request);
            Map<String, String> optionMap = new HashMap<>(1);
            for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
                if (Utils.isNotEmpty(entry.getValue())) {
                    String value = null;
                    for (String v : entry.getValue()) {
                        if (Utils.isNotBlank(v)) {
                            value = v;
                            break;
                        }
                    }
                    optionMap.put(entry.getKey(), value);
                }
            }

            if (!filesMap.isEmpty()) {
                optionMap.putAll(filesMap);
            }
            optionService.doOptionNew(optionMap);
            return R.ok();
        });
    }

    public Map<String, String> getUploadFilesMap(HttpServletRequest request) {
        Map<String, String> filesMap = new HashMap<>(1);
        if(request instanceof MultipartHttpServletRequest) {
            try {
                Map<String, SimpleFile> simpleFileMap = uploadService.mult(request, DateUtil.getDay());
                for (String key : simpleFileMap.keySet()) {
                    filesMap.put(key, simpleFileMap.get(key).getUrl());
                }
            } catch (IOException e) {
                throw new ToastException("文件上传错误");
            }
        }
        return filesMap;
    }

    @PostMapping
    public Result doOption(@RequestBody List<SysOptions> options) {
        return Result.build(() -> optionService.doOption(options));
    }

    @GetMapping
    public Result getOptions(@RequestParam(value = "optionKey", required = false) String optionKey) {
        return Result.build(() -> optionService.getOptions(optionKey));
    }

    @GetMapping("/paymentSettings")
    public Result paymentSettings() {
        Map<String, Object> data = new HashMap<>();
        data.put("paypalSendboxAccount", SysOptions.me().setOptionKey("paypal_sendbox_account").get());
        data.put("paypalClientId", SysOptions.me().setOptionKey("paypal_client_id").get());
        data.put("paypalSecret", SysOptions.me().setOptionKey("paypal_secret").get());
        data.put("paypalBnCode",SysOptions.me().setOptionKey("paypal_BnCode").get());
        data.put("asiabillMerNo", SysOptions.me().setOptionKey("asiabill_mer_no").get());
        data.put("asiabillGatewayNo", SysOptions.me().setOptionKey("asiabill_gateway_no").get());
        data.put("asiabillSignKey", SysOptions.me().setOptionKey("asiabill_sign_key").get());
        data.put("cashOnDeliveryPaymentName", SysOptions.me().setOptionKey("cash_on_delivery_payment_name").get());
        data.put("cashOnDeliveryContent", SysOptions.me().setOptionKey("cash_on_delivery_content").get());
        data.put("cashOnDeliveryPaymentInstructions", SysOptions.me().setOptionKey("cash_on_delivery_payment_instructions").get());
        data.put("isCashOnDelivery", SysOptions.me().setOptionKey("is_cash_on_delivery").get());
        return Result.build(() -> data);
    }

    @GetMapping("/seoSettings")
    public Result seoSettings() {
        Map<String, Object> data = new HashMap<>();
        data.put("siteSeoTitle", SysOptions.me().setOptionKey("site_seo_title").get());
        data.put("siteSeoKeyword", SysOptions.me().setOptionKey("site_seo_keyword").get());
        data.put("siteSeoDescription", SysOptions.me().setOptionKey("site_seo_description").get());
        data.put("siteSeoSelfLocation", SysOptions.me().setOptionKey("site_seo_self_location").get());
        return Result.build(() -> data);
    }

    @GetMapping("/thirdPartySettings")
    public Result thirdPartySettings() {
        Map<String, Object> data = new HashMap<>();
        data.put("thirdPartyCodeTitle", SysOptions.me().setOptionKey("third_party_code_title").get());
        data.put("thirdPartyCodeContent", SysOptions.me().setOptionKey("third_party_code_content").get());
        data.put("thirdPartyCodeTypeName", SysOptions.me().setOptionKey("third_party_code_type_name").get());
        data.put("thirdPartyCodeInHead", SysOptions.me().setOptionKey("third_party_code_in_head").get());
        data.put("thirdPartyCodeInBody", SysOptions.me().setOptionKey("third_party_code_in_body").get());
        return Result.build(() -> data);
    }

    @GetMapping("/tradeRulesSettings")
    public Result tradeRulesSettings() {
        Map<String, Object> data = new HashMap<>(5);
        data.put("tradeRulesNoticeCheckOut", SysOptions.me().setOptionKey("trade_rules_notice_check_out").get());
        data.put("tradeRulesLoginCheckOut", SysOptions.me().setOptionKey("trade_rules_login_check_out").get());
        data.put("tradeRulesPostcodeCheck", SysOptions.me().setOptionKey("trade_rules_postcode_check").get());
        data.put("tradeRulesStockTip", SysOptions.me().setOptionKey("trade_rules_stock_tip").get());
        data.put("tradeRulesInnerCode", SysOptions.me().setOptionKey("trade_rules_inner_code").get());
        return Result.build(() -> data);
    }



    @GetMapping("/siteSettingInfo")
    public Result siteSettingInfo() {
        Map<String, Object> data = new HashMap<>(10);
        data.put("siteSettingSiteName", Options.get(OptionsEnum.SITE_SEO_TITLE.key()));
        data.put("siteSettingLogo", Options.get(OptionsEnum.SITE_SEO_LOGO.key()));
        data.put("siteSettingFavicon", Options.get(OptionsEnum.SITE_SEO_FAVICON.key()));
        data.put("siteSettingKeyword", Options.get(OptionsEnum.SITE_SEO_KEYWORD.key()));
        data.put("siteSettingDescription", Options.get(OptionsEnum.SITE_SEO_DESCRIPTION.key()));
        data.put("siteSettingCurrency", Options.get("site_setting_currency"));
        data.put("siteSettingLanguage", Options.get("site_setting_language"));
        return Result.build(() -> data);
    }



    @PostMapping("/site/setting")
    public Result siteSettingInfo(@RequestBody SiteSettingDto dto) {
        optionService.editOptionsByKey("site_setting_site_name", dto.getSiteSettingSiteName());
        optionService.editOptionsByKey("site_setting_site_email", dto.getSiteSettingSiteEmail());
        optionService.editOptionsByKey("site_setting_domain_name", dto.getSiteSettingDomainName());
        optionService.editOptionsByKey("site_setting_copyright", dto.getSiteSettingCopyright());
        optionService.editOptionsByKey("site_setting_logo", dto.getSiteSettingLogo());
        optionService.editOptionsByKey("site_setting_favicon", dto.getSiteSettingFavicon());
        optionService.editOptionsByKey("site_setting_currency", dto.getSiteSettingCurrency());
        optionService.editOptionsByKey("site_setting_language", dto.getSiteSettingLanguage());
        return Result.success();
    }


    @Log("查询邮件设置")
    @GetMapping("/emailSettingInfo")
    public Result emailSettingInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("notify_email_username", SysOptions.me().setOptionKey("notify_email_username").get());
        data.put("notify_email_ssl", SysOptions.me().setOptionKey("notify_email_ssl").get());
        data.put("notify_email_port", SysOptions.me().setOptionKey("notify_email_port").get());
        data.put("notify_email_password", SysOptions.me().setOptionKey("notify_email_password").get());
        data.put("notify_email_host", SysOptions.me().setOptionKey("notify_email_host").get());
        return Result.build(() -> data);
    }


    @Log("设置佣金规则")
    @GetMapping("/commissionRulesInfo")
    public Result commissionRulesInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("site_commission_rules", SysOptions.me().setOptionKey("site_commission_rules").get());
        return Result.build(() -> data);
    }


    @Log("设置订单定时")
    @GetMapping("/orderTimingInfo")
    public Result orderTimingInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("site_order_timing", SysOptions.me().setOptionKey("site_order_timing").get());
        return Result.build(() -> data);
    }


    @GetMapping("/promotionInfo")
    public Result promotionAccountInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("site_account_facebook", SysOptions.me().setOptionKey("site_account_facebook").get());
        data.put("site_account_twitter", SysOptions.me().setOptionKey("site_account_twitter").get());
        data.put("site_account_youtube", SysOptions.me().setOptionKey("site_account_youtube").get());
        data.put("site_account_instagram", SysOptions.me().setOptionKey("site_account_instagram").get());
        return Result.build(() -> data);
    }

    @GetMapping("/taskManage")
    public Result taskManage() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("site_task_order_recall", SysOptions.me().setOptionKey("site_task_order_recall").get());
        return Result.build(() -> data);
    }


    @GetMapping("/commentSettingInfo")
    public Result commentSettingInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("comment_switch", SysOptions.me().setOptionKey("comment_switch").get());
        data.put("comment_fraction_filter", SysOptions.me().setOptionKey("comment_fraction_filter").get());
        data.put("comment_fraction_switch", SysOptions.me().setOptionKey("comment_fraction_switch").get());
        data.put("comment_star_display", SysOptions.me().setOptionKey("comment_star_display").get());
        data.put("comment_details_display", SysOptions.me().setOptionKey("comment_details_display").get());
        return Result.build(() -> data);
    }

    @GetMapping("/logisticsSettingInfo")
    public Result logisticsSettingInfo() {
        Map<String, Object> data = Maps.newHashMap();
        data.put("logisticsConsignor", SysOptions.me().setOptionKey("logistics_consignor").get());
        data.put("logisticsShippingAddress", SysOptions.me().setOptionKey("logistics_shipping_address").get());
        data.put("logisticsCountry", SysOptions.me().setOptionKey("logistics_country").get());
        data.put("logisticsContact", SysOptions.me().setOptionKey("logistics_contact").get());
        return Result.build(() -> data);
    }

    @PostMapping("/setting/bank")
    public Result siteSettingInfo(@RequestParam("cardNo") String cardNo,@RequestParam("cardName") String cardName) {
        optionService.editOptionsByKey("payment_receivables_card_no", cardNo);
        optionService.editOptionsByKey("payment_receivables_card_name", cardName);
        return Result.success();
    }
}
