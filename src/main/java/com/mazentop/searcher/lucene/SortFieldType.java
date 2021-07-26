package com.mazentop.searcher.lucene;

import com.google.common.collect.Maps;
import com.mazentop.searcher.index.snapshot.ProductSnapshot;
import org.apache.lucene.search.SortField;

import java.util.Map;

/**
 * @author zhaoqt
 * @title: SortFieldType
 * @description: 排序字段类型映射
 * @date 2019/5/1022:59
 */
public class SortFieldType {

    private static final Map<String, SortField.Type> map;

    static {
        map = Maps.newHashMap();
//        map.put(ProductSnapshot.F_PRICE, SortField.Type.LONG);
        map.put(ProductSnapshot.F_OLD_PRICE, SortField.Type.LONG);
        map.put(ProductSnapshot.F_CREATED, SortField.Type.LONG);
        map.put(ProductSnapshot.F_TIMES, SortField.Type.LONG);
        map.put(ProductSnapshot.F_SOLD_NUM, SortField.Type.INT);
        map.put(ProductSnapshot.F_SALE_NUM, SortField.Type.INT);
//        map.put(ProductSnapshot.F_SCORE_VALUE, SortField.Type.DOUBLE);
    }

    /**
     * 根据字段名称获取字段排序字段类型 若找不到返回 stirng类型
     *
     * @param field
     * @return
     */
    public static SortField.Type get(String field) {
        if(map.containsKey(field)) {
            return map.get(field);
        }
        return SortField.Type.STRING;
    }

}
