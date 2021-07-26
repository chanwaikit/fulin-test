package com.mazentop.modules.freemarker.tag;

import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.impl.PageBlockView;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mztframework.render.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhaoqt
 * @title: BlockTag
 * @description: freemarker 标签
 * @date 2019/4/2912:08
 */
@Component
public class SkinBlockDataTag extends Tag {

    @Autowired
    Map<String, IBlockView> blockViewMap;

    @Override
    public void execute() {
        String view = getParam("view");
        String handle = getParam("handle");
        String pid = getParam("pid");
        String id = getParam("id");
        String type = getParam("type");
        if(!blockViewMap.containsKey(type)) {
            type = PageBlockView.MAPPING;
        }
        SkinPageBlockCommond skinPageBlockCommond = new SkinPageBlockCommond();
        skinPageBlockCommond.setView(view);
        skinPageBlockCommond.setPid(pid);
        skinPageBlockCommond.setId(id);
        skinPageBlockCommond.setHandle(handle);
        skinPageBlockCommond.setIsEnable(BooleanEnum.TRUE.getValue());
        setVariable("blockData", blockViewMap.get(type).query(skinPageBlockCommond));
        renderBody();
    }

    @Override
    public String name() {
        return "skin_block_data";
    }
}
