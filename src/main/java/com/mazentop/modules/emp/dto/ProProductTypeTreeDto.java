package com.mazentop.modules.emp.dto;

import com.mazentop.entity.ProProductType;
import lombok.Data;

import java.util.List;

@Data
public class ProProductTypeTreeDto extends ProProductType {

    private Boolean isLeaf;

    private List<ProProductType> children;

}
