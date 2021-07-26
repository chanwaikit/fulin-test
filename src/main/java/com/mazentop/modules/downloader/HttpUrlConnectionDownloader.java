package com.mazentop.modules.downloader;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.mztframework.FileProperties;
import com.mztframework.snowflake.IDSnowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 *
 * @author zhaoqt
 * @date 2016-12-17 上午4:09
 */
@Component
public class HttpUrlConnectionDownloader {

    @Autowired
    FileProperties fileProperties;

    public String download(String url) {
        String filePath =  File.separator + DateUtil.today() + File.separator + IDSnowflake.id() + ".jpeg";
        File file =FileUtil.file(fileProperties.getLocalPath(), filePath);
        try {
            HttpUtil.downloadFile(url, file);
        } catch (Exception e) {
            return url;
        }
        return fileProperties.getBaseUrl() + filePath;
    }



}
