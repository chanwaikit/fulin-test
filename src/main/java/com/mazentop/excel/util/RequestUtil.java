package com.mazentop.excel.util;

import com.alibaba.fastjson.JSON;
import com.mazentop.util.Helper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
    public static Map<String,Object> getValue(HttpServletRequest request) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(request.getInputStream(), "UTF-8");

        BufferedReader streamReader = new BufferedReader( inputStreamReader);
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        if (Helper.isNotEmpty(responseStrBuilder)){
            Map<String ,Object> map = JSON.parseObject(responseStrBuilder.toString(),Map.class);
            inputStreamReader.close();
            streamReader.close();
            return map;
        }
        return null;
    }

    public static Map<String,Object> getFromValue(HttpServletRequest request) throws IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String,Object> map=new HashMap<>();
        for (Map.Entry<String, String[]> stringEntry : parameterMap.entrySet()) {
            map.put(stringEntry.getKey(), StringUtils.join(stringEntry.getValue()));
        }
        return map;
    }

    public static MultipartFile getFile(HttpServletRequest request){
        MultipartResolver mr = new CommonsMultipartResolver(request.getSession().getServletContext());

        //MultipartResolver mr = new CommonsMultipartResolver(request.getServletContext());
        MultipartHttpServletRequest multipartHttpServletRequest = mr.resolveMultipart(request);
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        return multipartFile;
    }
}
