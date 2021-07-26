package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProProductImage;
import com.mztframework.dao.annotation.Order;
import com.mztframework.render.Tag;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 主题获取切换图片
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/3 17:24
 */
@Component
public class SkinSwitchImageTag extends Tag {

    @Override
    public void execute() {
        String id = getParam("id");
        String type = getParam("type");
        Map<String, Object> map = new HashMap<>(2);
        map.put("exist", false);
        List<ProProductImage> proProductImageList = ProProductImage.me().setFkProductId(id).setOrderByFields(Order.asc(ProProductImage.F_SORT)).setLimit(2).list();
        if(proProductImageList.size() > 1) {
            map.put("url", proProductImageList.get(1).getProductImageUrl());
            map.put("exist", true);
        }
        setVariable("skinSwitch", map);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_switch_image";
    }
}
