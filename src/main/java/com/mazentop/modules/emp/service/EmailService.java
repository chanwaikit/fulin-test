package com.mazentop.modules.emp.service;

import com.mazentop.plugins.freemarker.FreemarkerUtil;
import com.mztframework.data.R;
import com.mztframework.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.Map;

/**
 * @author: wangzy
 * @date: 2020/3/16
 * @description:
 */
@Service
public class EmailService {

    //发送邮件的模板引擎
    @Autowired
    private FreeMarkerConfigurer configurer;

    /**
     * 引用freemark模板填写邮件内容，发送
     *
     * @param model
     * @param templateName
     * @param email
     * @return
     */
    public R sendMail(Map<String, Object> model, Email email, String templateName) {

        String make = FreemarkerUtil.make(model, templateName);
        R send = email.content(make).send();

        return send;
    }

    public R sendMail(String model, Email email) {
        R send = email.content(model).send();

        return send;
    }

    public R sendMail(Email email) {
        R send = email.send();

        return send;
    }

}
