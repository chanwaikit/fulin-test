package com.mazentop.modules.evaluation.commond;

import com.mztframework.commond.PageCommond;
import lombok.Data;

/**
 *
 * @author zhoumei
 * @title: EvaCommissionDetailsCommond
 * @description: 佣金明细
 * @date 2021/1/8
 */
@Data
public class EvaCommissionCommond extends PageCommond {
    private String email;

    private String phone;
}
