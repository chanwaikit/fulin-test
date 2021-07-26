package com.mazentop.plugins.resservlet.util;


import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoqt
 * @title: BlobFormat
 * @description: 文件格式
 * @date 2019/4/2014:07
 */
public class BlobFormat {

    private static List<String> imageSuffix = Lists.newArrayList(".jpg", ".jpeg", ".png", ".bmp", ".gif", ".webp");
    private static List<String> docSuffix = Lists.newArrayList(".doc", ".docx", ".pages", ".dot", ".dotx", ".docm", ".dotm");
    private static List<String> xlsSuffix = Lists.newArrayList(".xls", ".xlsx", ".xltx", ".numbers", ".csv");
    private static List<String> pptSuffix = Lists.newArrayList(".ppt", ".pptx", ".key", ".ppsx", ".potx");
    private static List<String> txtSuffix = Lists.newArrayList(".txt", ".log", ".xml", ".json", ".js", ".css", ".html", ".sql", ".md");
    private static List<String> zipSuffix = Lists.newArrayList(".zip", ".tar", ".gz", ".jar", ".war", ".bz2", ".z", ".rar");
    private static List<String> exeSuffix = Lists.newArrayList(".exe", ".pkg", ".dmg");
    private static List<String> pdfSuffix = Lists.newArrayList(".pdf");
    private static List<String> audioSuffix = Lists.newArrayList(".mp3", ".au", ".cd", ".wma", ".ogg", ".ape", ".flac", ".aiff");
    private static List<String> videoSuffix = Lists.newArrayList(".mp4", ".avi", ".wmv", ".mpeg", ".mov", ".mkv", ".flv", ".rmvb", ".rm", ".3gp", ".ts", ".vob", ".m3u8");
    private static final String suffixBlob = "blob";

    public static Map<String, String> fileTypeMap = new HashMap<>();
    private static Map<String, List<String>> fileSuffixMap = new HashMap<>();

    static {
        fileTypeMap.put("image", "图片");
        fileTypeMap.put("doc", "文稿");
        fileTypeMap.put("xls", "表格");
        fileTypeMap.put("ppt", "PPT");
        fileTypeMap.put("txt", "文本");
        fileTypeMap.put("zip", "压缩包");
        fileTypeMap.put("exe", "可执行文件");
        fileTypeMap.put("pdf", "PDF");
        fileTypeMap.put("audio", "音频");
        fileTypeMap.put("video", "视频");
        fileTypeMap.put("other", "其他");

        fileSuffixMap.put("image", imageSuffix);
        fileSuffixMap.put("video", videoSuffix);
//        fileSuffixMap.put("doc", docSuffix);
//        fileSuffixMap.put("xls", xlsSuffix);
//        fileSuffixMap.put("ppt", pptSuffix);
//        fileSuffixMap.put("txt", txtSuffix);
//        fileSuffixMap.put("zip", zipSuffix);
//        fileSuffixMap.put("exe", exeSuffix);
//        fileSuffixMap.put("pdf", pdfSuffix);
//        fileSuffixMap.put("audio", audioSuffix);

    }

    public static String getFormat(String suffix){
        // 若文件后缀名 为 blob 直接转换为 jpg格式 暂时实现方案，可根据文件流获取头文件二进制判断文件类型
        if(suffixBlob.equalsIgnoreCase(suffix)) {
            return "jpg";
        }
        return suffix;
    }

    public static boolean isImage(String suffix) {
        return suffixIn(BlobFormat.imageSuffix, suffix);
    }

    public static boolean isDoc(String suffix) {
        return suffixIn(BlobFormat.docSuffix, suffix);
    }

    public static boolean isXls(String suffix) {
        return suffixIn(BlobFormat.xlsSuffix, suffix);
    }

    public static boolean isTxt(String suffix) {
        return suffixIn(BlobFormat.txtSuffix, suffix);
    }

    public static boolean isPpt(String suffix) {
        return suffixIn(BlobFormat.pptSuffix, suffix);
    }

    public static boolean isZip(String suffix) {
        return suffixIn(BlobFormat.zipSuffix, suffix);
    }

    public static boolean isExe(String suffix) {
        return suffixIn(BlobFormat.exeSuffix, suffix);
    }

    public static boolean isPdf(String suffix) {
        return suffixIn(BlobFormat.pdfSuffix, suffix);
    }

    public static boolean isAudio(String suffix) {
        return suffixIn(BlobFormat.audioSuffix, suffix);
    }

    public static boolean isVideo(String suffix) {
        return suffixIn(BlobFormat.videoSuffix, suffix);
    }

    public static String type(String suffix) {
        for(String key : fileSuffixMap.keySet()) {
            if(suffixIn(fileSuffixMap.get(key), suffix)) {
                return key;
            }
        }
        return "other";
    }

    private static boolean suffixIn(List<String> suffixList, String suffix) {
        return suffix != null && suffixList.contains("."+suffix.toLowerCase());
    }


    static List<String> unSafeFilesSuffix = new ArrayList<String>();

    static {
        unSafeFilesSuffix.add(".jsp");
        unSafeFilesSuffix.add(".jspx");
        unSafeFilesSuffix.add(".php");
        unSafeFilesSuffix.add(".html");
        unSafeFilesSuffix.add(".htm");
        unSafeFilesSuffix.add(".css");
        unSafeFilesSuffix.add(".js");
        unSafeFilesSuffix.add(".exe");
        unSafeFilesSuffix.add(".sh");
        unSafeFilesSuffix.add(".bat");
        unSafeFilesSuffix.add(".jar");
        unSafeFilesSuffix.add(".war");
    }

}
