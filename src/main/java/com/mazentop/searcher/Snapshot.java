package com.mazentop.searcher;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoqt
 * @title: Snapshot
 * @description: 检索对象
 * @date 2019/5/918:29
 */
@Data
public class Snapshot implements Serializable {

    private static final long serialVersionUID = 1694272371845268846L;

    public static final String F_ID = "sid";

    public static final String F_TITLE = "title";

    public static final String F_THUMBNAIL = "thumbnail";

    public static final String F_KEYWORD = "websearch";

    public static final String F_TAXONOMY = "taxonomy";

    public static final String F_TAXONOMY_VALUE= "taxonomy.value";

    public static final String F_SUMMARY = "summary";

    public static final String F_CONTENT = "content";

    public static final String F_URL = "url";

    public static final String F_CREATED = "created";

    public static final String F_DATE_INDEX = "created.index";

    public static final String F_DATA = "data";

    public static final String F_MODULE = "module";

    public static final String F_SOLD_NUM = "soldNum";


    private String id;

    private String title;

    private String thumbnail;

    private String keyword;

    private String taxonomy;

    private String summary;

    private String content;

    private String url;

    private String created;

    private Object data;

    public Snapshot () {}

    public Snapshot(String id) {
        this.id = id;
    }
}
