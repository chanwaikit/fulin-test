package com.mazentop.modules.skin.block.dto;

import com.alibaba.fastjson.JSON;
import com.mazentop.modules.skin.dto.ImageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品专辑块
 *
 * @author dengy
 * @version 1.0
 * @date 2020/6/19 18:41
 */
@Data
public class Collection  implements Serializable {

    private String title;

    private String subtitle;

    private List<Row> collections;


    @Data
    public static class Row {

        private String id;

        private String title;

    }
}
