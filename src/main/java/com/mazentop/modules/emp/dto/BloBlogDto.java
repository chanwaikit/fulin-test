package com.mazentop.modules.emp.dto;

import com.mazentop.entity.BloBlog;
import com.mazentop.entity.ProSeo;
import com.mazentop.plugins.seo.Seo;
import lombok.Data;

@Data
public class BloBlogDto extends BloBlog {
    private ProSeo seo;
}
