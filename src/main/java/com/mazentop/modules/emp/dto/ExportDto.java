package com.mazentop.modules.emp.dto;


import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

/**
 * @author: wangzy
 * @date: 2020/3/16
 * @description:
 */
@Data
public class ExportDto <D extends BaseCommond> {

    /**
     * 导出范围 select 选择
     */
    private String status;

    /**
     * 邮箱地址
     */
    private String email;

    /**
     * 当前选择的ID
     */
    private List<String> ids;

    /**
     * 查询条件
     */
    private D params;

}
