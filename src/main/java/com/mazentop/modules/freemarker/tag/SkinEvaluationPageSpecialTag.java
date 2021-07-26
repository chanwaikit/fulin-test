package com.mazentop.modules.freemarker.tag;

import com.mazentop.model.Constant;
import com.mazentop.modules.skin.service.EvaProductSpecialService;
import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mazentop.modules.web.User;
import com.mazentop.plugins.session.SessionUtil;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 测评-专辑数据
 *
 * @author zhoumei
 * @version 1.0
 * @date 2021/1/25 15:25
 */
@Component
@Slf4j
public class SkinEvaluationPageSpecialTag extends Tag {

    @Autowired
    EvaProductSpecialService evaProductSpecialService;

    @Override
    public void execute() {
        String id = getParam("id");
        Integer limit = getParamToInt("limit", 20);
        if(StringUtils.isNotBlank(id)){
            setVariable("goodsList", evaProductSpecialService.getEvaGoodsDtoList(id, limit));
            setVariable("currentCountry", SessionUtil.getSessionCountry());
            renderBody();
        }else {
            log.error("[@skin_eva_block_special_goods /] 缺少必要参数 id，例如：[@skin_eva_block_special_goods id=\"块ID\" /]");
        }

    }

    @Override
    public String name() {
        return "skin_eva_block_special_goods";
    }
}
