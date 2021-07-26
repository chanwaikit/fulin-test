package com.mazentop.searcher.index.snapshot;

import com.mazentop.searcher.Snapshot;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 产品参数分组
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/5 17:27
 */
@Data
public class ProductParamGroupSnapshot extends Snapshot {

    private List<ProductParamSnapshot.Brand> brands;

    private List<ProductParamSnapshot.Type> types;

    private List<ProductParamSnapshot.Color> colors;

    private Map<String, List<String>> specMap;


}
