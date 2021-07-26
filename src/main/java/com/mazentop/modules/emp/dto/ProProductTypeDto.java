package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ProProductType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: wangzy
 * @date: 2020/3/17
 * @description:
 */

@Data
public class ProProductTypeDto implements Serializable {

    private static final long serialVersionUID = -3008491461193571705L;

    private String id;

    private String productTypeName;

    private String parentProductTypeId;

    private String parentProductTypeName;

    private String productTypeProImage;

    private Integer isRootType;

    private Integer isShowIndex;

    private Integer isShowProductMenu;

    private String description;

    private String content;

    private Integer sort;

    private String seoTitle;

    private String seoKeywords;

    private String seoDescription;

    private String seoAddress;

    private String remark;

    private Integer addTime;

    private String addUserId;

    private String addUserName;

    private Integer operationTime;

    private String operationUserId;

    private String operationUserName;

    private String companyId;

    private String companyName;

    private Boolean child = false;

    private List<ProProductTypeDto> children;

}
