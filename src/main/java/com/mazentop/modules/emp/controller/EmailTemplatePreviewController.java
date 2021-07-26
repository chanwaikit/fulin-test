package com.mazentop.modules.emp.controller;

import com.mazentop.entity.*;
import com.mazentop.model.EmailTemplateTypeEnum;
import com.mazentop.model.OptionsEnum;
import com.mazentop.modules.emp.dto.PreviewDto;
import com.mazentop.modules.user.dto.OrdOrderDetailSendEmailDto;
import com.mazentop.modules.user.dto.OrdOrderSendEmailDto;
import com.mazentop.plugins.freemarker.FreemarkerUtil;
import com.mztframework.data.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/option/{api_version}/template")
public class EmailTemplatePreviewController {

    @PostMapping("/preview")
    public Result productDetails(@RequestBody PreviewDto previewDto) {
        Map<String, Object> map = new HashMap<>();
        SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setId(previewDto.getId()).get();
        String optionValue = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get().getOptionValue();
        String optionEmail = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_EMAIL.key()).get().getOptionValue();
        if (sysEmailTemplate.getEmailSendMoment().equals(EmailTemplateTypeEnum.TYPE_CONFIRM_ORDER.type())) {
            OrdOrderSendEmailDto email = new OrdOrderSendEmailDto();
            setSendEmailDto(email);
            email.getList().get(0).setImageUrl("http://imgs.mazentop.com/default/4274819535694989708.jpg!465x282");
            map.put("order", email);
        } else if (sysEmailTemplate.getEmailSendMoment().equals(EmailTemplateTypeEnum.TYPE_RECOVERY_ORDER.type())) {
            List<OrdShoppingCart> list = new ArrayList<>();
            OrdShoppingCart ordShoppingCart = new OrdShoppingCart();
            ordShoppingCart.setTotalProductNumber(1);
            ordShoppingCart.setPrductPicImageUrl("http://imgs.mazentop.com/default/4274819535694989708.jpg!465x282");
            ordShoppingCart.setProductName("测试数据");
            list.add(ordShoppingCart);
            OrdRecallRecord record = new OrdRecallRecord();
            record.setClientName("测试数据");
            map.put("record", record);
            map.put("url", "测试数据");
            map.put("ordShoppingCarts", list);
            map.put("domain", optionValue);
        } else if (sysEmailTemplate.getEmailSendMoment().equals(EmailTemplateTypeEnum.TYPE_CANCEL_ORDER.type())) {
            map.put("orderNo", "测试数据");
            map.put("time", "测试数据");
        } else if (sysEmailTemplate.getEmailSendMoment().equals(EmailTemplateTypeEnum.TYPE_REFUND_ORDER.type())) {
            map.put("orderNo", "测试数据");
            map.put("time", "测试数据");
            map.put("refundNo", "测试数据");
        } else if (sysEmailTemplate.getEmailSendMoment().equals(EmailTemplateTypeEnum.TYPE_DELIVERY_ORDER.type())) {
            List<OrdSalesOrderDetail> detailList = OrdSalesOrderDetail.me().setOrderByFields("id desc").setLimit(1).find();
            map.put("transportsNo", "测试数据");
            map.put("transportsChannelName", "测试数据");
            map.put("url", "测试数据");
            map.put("orderNo", detailList.get(0).getSalesOrderNo());
            map.put("contactEmail", optionEmail);
            map.put("detailList", detailList);
            map.put("domain", optionValue);
        } else if (sysEmailTemplate.getEmailSendMoment().equals(EmailTemplateTypeEnum.TYPE_WELCOME_USER.type())) {
            map.put("domain", optionValue);
            map.put("contactEmail", optionEmail);
        } else if (sysEmailTemplate.getEmailSendMoment().equals(EmailTemplateTypeEnum.TYPE_RESET_PWD.type())) {
            map.put("code", "测试数据");
            map.put("domain", optionValue);
            map.put("contactEmail", optionEmail);
        }
        String make = null;
        try {
            make = FreemarkerUtil.make(map, previewDto.getContent());
        } catch (Exception e) {
            return Result.build(() ->
                    "<b><font color='red'>Error: </font></b> Syntax error in template "
            );
        }
        String finalMake = make;
        return Result.build(() ->
                finalMake
        );
    }

    private void setSendEmailDto(OrdOrderSendEmailDto dto) {
        Field[] field = dto.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < field.length; i++) {
                String name = field[i].getName();
                name = name.substring(0, 1).toUpperCase() + name.substring(1);
                String type = field[i].getGenericType().toString();
                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method m = dto.getClass().getMethod("get" + name);
                    String value = (String) m.invoke(dto); // 调用getter方法获取属性值
                    if (value == null) {
                        m = dto.getClass().getMethod("set" + name, String.class);
                        m.invoke(dto, "测试数据");
                    }
                } else if (type.equals("java.util.List<com.mazentop.modules.user.dto.OrdOrderDetailSendEmailDto>")) {
                    List<OrdOrderDetailSendEmailDto> list = new ArrayList<>();
                    OrdOrderDetailSendEmailDto emailDto = new OrdOrderDetailSendEmailDto();
                    Field[] fieldName = emailDto.getClass().getDeclaredFields();
                    for (int j = 0; j < fieldName.length; j++) {
                        String fieldNames = fieldName[j].getName();
                        fieldNames = fieldNames.substring(0, 1).toUpperCase() + fieldNames.substring(1);
                        String types = fieldName[j].getGenericType().toString();
                        if (types.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                            Method method = emailDto.getClass().getMethod("get" + fieldNames);
                            String value = (String) method.invoke(emailDto); // 调用getter方法获取属性值
                            if (value == null) {
                                method = emailDto.getClass().getMethod("set" + fieldNames, String.class);
                                method.invoke(emailDto, "测试数据");
                            }
                        }
                    }
                    list.add(emailDto);
                    Method m = dto.getClass().getMethod("get" + name);
                    List value = (List) m.invoke(dto); // 调用getter方法获取属性值
                    if (value == null) {
                        m = dto.getClass().getMethod("set" + name, List.class);
                        m.invoke(dto, list);
                    }
                }
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
