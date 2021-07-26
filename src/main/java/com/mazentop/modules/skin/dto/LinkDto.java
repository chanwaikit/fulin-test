package com.mazentop.modules.skin.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaoqt
 */
@Data
public class LinkDto implements Serializable {

    private String id;

    private String title;

    private String url;
}
