package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProProductCommon;
import com.mazentop.model.BooleanEnum;
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
public class SkinProductCommonTag extends Tag {


    @Override
    public void execute() {

        setVariable("commonList", ProProductCommon.me().setIsEnable(BooleanEnum.TRUE.getValue()).setOrderByFields("sort asc").find());
        renderBody();

    }

    @Override
    public String name() {
        return "skin_product_common";
    }
}
