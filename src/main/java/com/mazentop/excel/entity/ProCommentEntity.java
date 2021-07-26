package com.mazentop.excel.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProCommentEntity implements Serializable {

    @Excel(name = "编号", width = 30)
    private String id;

    @Excel(name = "商品编号", width = 30)
    private String fkProductId;

//    @Excel(name = "顾客编号", width = 30)
//    private String fkClienteleId;

    @Excel(name = "评论图片", width = 60)
    private String productProImage;

    @Excel(name = "商品名称", width = 30)
    private String productNme;

    @Excel(name = "评价内容", width = 30)
    private String content;

    @Excel(name = "评论分数", width = 30)
    private Integer rangeNum;

    @Excel(name = "评论人", width = 30)
    private String addUserName;

    @Excel(name = "点赞数", width = 30)
    private String likeNum;

    @Excel(name = "评论时间", width = 50)
    private String addTime;

//    @Excel(name = "审核通过时间", width = 50)
//    private String auditTime;

    @Excel(name = "错误信息", height = 20, width = 30)
    private String error;

}
