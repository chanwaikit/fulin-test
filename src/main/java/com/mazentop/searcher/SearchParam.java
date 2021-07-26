package com.mazentop.searcher;

import lombok.Data;

/**
 * @Auther: Wangzy
 * @Date: 2019/7/2 16:34
 * @Description:
 */
@Data
public class SearchParam {

    private String keywords;

    private String times;

    private Integer totalRow;

    public SearchParam(String keywords) {
        this.keywords = keywords;
    }

    public SearchParam(String keywords, String times) {
        this.keywords = keywords;
        this.times = times;
    }

    @Override
    public String toString() {
        return "SearchParam{" +
                "keywords='" + keywords + '\'' +
                ", times='" + times + '\'' +
                '}';
    }
}
