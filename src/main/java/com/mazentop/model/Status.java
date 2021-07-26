package com.mazentop.model;

import com.mztframework.Constants;

/**
 * @author zhaoqt
 * @title: Status
 * @description: 状态位
 *
 * @date 2019/4/1200:17
 */
public class Status extends Constants {


    public static final String DATA_STATION_INIT = "init";

    public static final String TREE_ROOT_NODE = "evaluation";

    public static final String TREE_EVALUATION_NODE = "evaluation";

    public static final String TREE_LEVEL_MULT = "mult";

    public static final String SUCCESS = "success";

    public static final String ERROR = "error";

    public static final String OK = "ok";

    public static final String ATTR_GLOBAL_WEB_TITLE = "meta_title";
    public static final String ATTR_GLOBAL_META_KEYWORDS = "meta_keywords";
    public static final String ATTR_GLOBAL_META_DESCRIPTION = "meta_description";


    public class ExporStatus {

        public static final String SELECT = "select";

        public static final String SEARCH = "search";

        public static final String ALl = "all";
    }

    /**
     * 产品购买模式
     */
    public class ProductBuyingPatterns{
        public static final String KEYWORD = "keyword";

        public static final String LINK = "link";
    }
}
