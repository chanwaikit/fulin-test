package com.mazentop.modules.skin.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImageDto implements Serializable {

    private String url;

    private String alt;
}
