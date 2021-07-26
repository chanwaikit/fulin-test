package com.mazentop.searcher;

import com.mazentop.searcher.lucene.SortFieldType;
import com.mztframework.commons.Utils;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

/**
 * @author zhaoqt
 * @title: Keyword
 * @description: 查询关键词
 * @date 2019/5/920:23
 */

public class Keyword {

    private static final String ORDER_FIELD_PREFIX = "-";

    @Getter
    @Setter
    private String value;

    @Getter
    @Setter
    private boolean pagination = false;

    @Getter
    @Setter
    private int pageIndex = 1;

    @Getter
    @Setter
    private int pageSize = 30;

    /**
     * 排序字段参数
     *
     * 字段名前加- 倒序否则正序
     */
    @Setter
    @Getter
    private String o;

    @Getter
    private int p;

    /**
     * lucene 排序 默认按照索引排序
     */
    private Sort sort = new Sort(SortField.FIELD_SCORE);


    public Keyword() {}

    public Keyword(String value) {
        this.value = value;
    }

    /**
     * 前端关键词 查询可传 k
     * @param k
     */
    public void setK(String k) {
        this.value = k;
    }

    public void setP(int p) {
        this.pageIndex = p;
        this.p = p;
    }

    public int getP() {
        if(this.p <= 0) {
            this.p = 1;
        }
       return this.p;
    }

    public Sort getSort() {
        if(Utils.isBlank(o)) {
            return sort;
        }
        if(o.startsWith(ORDER_FIELD_PREFIX)) {
            o = o.replace(ORDER_FIELD_PREFIX, "");
            return new Sort(new SortField(o, SortFieldType.get(o),  Boolean.TRUE));
        }
        return new Sort(new SortField(o, SortFieldType.get(o)));
    }


}
