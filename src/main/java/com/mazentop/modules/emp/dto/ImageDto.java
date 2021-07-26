package com.mazentop.modules.emp.dto;

import lombok.Data;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/4/26 18:22
 */
@Data
public class ImageDto {

    private String url;

    private String alt;

    public ImageDto() {}

    public ImageDto(String url, String alt) {
        this.url = url;
        this.alt = alt;
    }


}
