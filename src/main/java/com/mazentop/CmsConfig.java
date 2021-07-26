package com.mazentop;

import com.mztframework.spring.SpringContextHolder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Title: ErpConfig
 * @Description: 全局配置
 * @Author liuq
 * @Date 2019/9/26 22:46
 */
@Data
@Component
@ConfigurationProperties(prefix = CmsConfig.PREFIX)
public class CmsConfig {

    public static final String PREFIX = "cms";

    /**
     * 导入 excel 表格中图片 存储磁盘路径位置，绝对路径
     */
    public static final String EXCEL_FILE_PATH = "";

    private Lucene lucene;

    private String payNotifyDomain;

    private boolean debug;

    private Aliyun aliyun;

    private String ctxPath;

    private String ehcacheDiskStore = "java.io.tmpdir";

    /**
     * lucene索引目录
     */
    @Data
    public static class Lucene {

        private String indexDir;

        private boolean debug;
    }

    @Data
    public static class Aliyun {

        private String accessKeyId;

        private String accessKeySecret;
    }

    public static CmsConfig me() {
        return SpringContextHolder.getBean(CmsConfig.class);
    }

}