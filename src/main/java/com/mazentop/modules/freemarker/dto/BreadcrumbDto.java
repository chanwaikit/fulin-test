package com.mazentop.modules.freemarker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 面包屑
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/5 23:39
 */
@Data
@AllArgsConstructor
public class BreadcrumbDto implements Serializable {

    private String id;

    private String text;

    private String hidden;

    private String url;

}
