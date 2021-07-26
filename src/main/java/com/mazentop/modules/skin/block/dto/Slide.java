package com.mazentop.modules.skin.block.dto;

import com.alibaba.fastjson.JSON;
import com.mazentop.modules.skin.dto.ImageDto;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 幻灯片
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/14 03:38
 */
@Data
public class Slide implements Serializable {

    private Integer autoplay;

    private Integer delay;

    private List<Row> slides;

    @Data
    public static class Row {

        private String title;

        private String url;

        private String type;

        private String target = "_blank";

        private ImageDto pcImage;

        private ImageDto mobileImage;

        public String toInlineData() {
            Map<String, ImageDto> map = new HashMap<>(2);
            map.put("pcImage", this.pcImage);
            map.put("mobileImage", this.mobileImage);
            return JSON.toJSONString(map);
        }
    }

    public String toSettings() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("autoplay", this.autoplay);
        map.put("delay", this.delay);
        return JSON.toJSONString(map);
    }


}
