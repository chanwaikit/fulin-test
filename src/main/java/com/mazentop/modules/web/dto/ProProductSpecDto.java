package com.mazentop.modules.web.dto;

import com.mazentop.entity.ProProductSpec;
import lombok.Data;

import java.util.List;

@Data
public class ProProductSpecDto extends ProProductSpec {
    private List<ProProductSpec> specList;

    private Integer colorFlag;

}
