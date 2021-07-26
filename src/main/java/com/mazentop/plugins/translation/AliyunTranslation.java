package com.mazentop.plugins.translation;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alimt.model.v20181012.TranslateECommerceRequest;
import com.aliyuncs.alimt.model.v20181012.TranslateECommerceResponse;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.mazentop.CmsConfig;
import com.mztframework.commons.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.net.URLEncoder;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/6/29 17:12
 */
public class AliyunTranslation implements ITranslation {


    @Autowired
    CmsConfig cmsConfig;



    @Override
    public String translate(String sourceText, String sourceLanguage, String targetLanguage, String scene) {
        if(Utils.isBlank(sourceText)) {
            return "";
        }
        if(Utils.isBlank(sourceLanguage)) {
            sourceLanguage = "auto";
        }
        if(Utils.isBlank(targetLanguage)) {
            targetLanguage = "zh";
        }

        try {
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", cmsConfig.getAliyun().getAccessKeyId(), cmsConfig.getAliyun().getAccessKeySecret());
            IAcsClient client = new DefaultAcsClient(profile);
            // 创建API请求并设置参数
            TranslateECommerceRequest eCommerceRequest = new TranslateECommerceRequest();
            eCommerceRequest.setScene(scene);
            eCommerceRequest.setFormatType("html");
            eCommerceRequest.setActionName("TranslateGeneral");
            //源语言
            eCommerceRequest.setSourceLanguage(sourceLanguage);
            //原文
            eCommerceRequest.setSourceText(URLEncoder.encode(sourceText,"UTF-8"));
            //目标语言
            eCommerceRequest.setTargetLanguage(targetLanguage);
            eCommerceRequest.setSysMethod(MethodType.POST);
            TranslateECommerceResponse eCommerceResponse = client.getAcsResponse(eCommerceRequest);
            return HtmlUtils.htmlUnescape(eCommerceResponse.getData().getTranslated());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  "";
    }

}
