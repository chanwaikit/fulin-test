package com.mazentop.modules.emp.service;

import com.mazentop.entity.SysOptions;
import com.mazentop.util.Helper;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderManageService {

    public String orderDetailsUrl(String orderId, String userId, SysOptions sysOptions) {

        Map<String, Object> map = new HashMap<>();
        String url = sysOptions.getOptionValue();

        //封装跳转链接
        map.put("orderId",orderId);
        map.put("userId",userId);
        String param= Helper.toJson(map);
        //加密请求参数
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] textByte = new byte[0];
        try {
            textByte = param.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String params = base64Encoder.encode(textByte);
        url +="/client/redirect/order/detail/"+params;
        return url;
    }
}
