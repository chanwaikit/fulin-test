package com.mazentop.modules.freemarker.tag;

import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mazentop.modules.web.User;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 查询商品专辑块
 *
 * @author dengy
 * @version 1.0
 * @date 2020/6/20 17:25
 */
@Component
@Slf4j
public class SkinPageSpecialTag extends Tag {

    @Autowired
    ProductSpecialService productSpecialService;

    @Override
    public void execute() {
        String id = getParam("id");
        Integer limit = getParamToInt("limit", 20);
        if(StringUtils.isNotBlank(id)){
            setVariable("goodsList", productSpecialService.getGoodsDtoList(id, limit));
            renderBody();
        }else {
            log.error("[@skin_block_special_goods /] 缺少必要参数 id，例如：[@skin_block_special_goods id=\"块ID\" /]");
        }

    }

    @Override
    public String name() {
        return "skin_block_special_goods";
    }
}
