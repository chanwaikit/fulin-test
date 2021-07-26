package com.mazentop.modules.emp.dto;

import com.mazentop.entity.SysEmailTemplate;
import com.mazentop.entity.SysEmailTemplateType;
import lombok.Data;

import java.util.List;

@Data
public class SysEmailTemplateTypeDto extends SysEmailTemplateType {
    private List<SysEmailTemplate> list;
}
