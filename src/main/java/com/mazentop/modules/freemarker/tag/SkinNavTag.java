package com.mazentop.modules.freemarker.tag;

import com.mazentop.model.BooleanEnum;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.block.impl.DefaultBlockView;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinBlockMenuDto;
import com.mazentop.modules.skin.service.SkinPageBlockMenuService;
import com.mztframework.commons.Utils;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/7/12 00:09
 */
@Component
@Slf4j
public class SkinNavTag extends Tag {

    @Autowired
    SkinPageBlockMenuService skinPageBlockMenuService;

    @Autowired
    Map<String, IBlockView> blockViewMap;

    @Override
    public void execute() {
        String view = getParam("view");
        String handle = getParam("handle");
        if(!Utils.isBlank(view) || !Utils.isBlank(handle)) {

            SkinPageBlockCommond skinPageBlockCommond = new SkinPageBlockCommond();
            skinPageBlockCommond.setView(view);
            skinPageBlockCommond.setHandle(handle);
            skinPageBlockCommond.setIsEnable(BooleanEnum.TRUE.getValue());

            BlockData blockData = blockViewMap.get(DefaultBlockView.MAPPING).query(skinPageBlockCommond);
            List<BlockData> blockDataList =  blockData.getChilds();
            if(!blockDataList.isEmpty()) {
                String blockId = blockDataList.get(0).getDataId();
                SkinBlockMenuDto skinBlockMenuDto = skinPageBlockMenuService.getSkinPageBlockMenu(blockId);
                setVariable("navs", skinBlockMenuDto.getMenus());
            } else {
                setVariable("navs", new ArrayList<>(0));
            }
            renderBody();
        } else {
            log.error("[@skin_nav /] 缺少必要参数 handle，例如：[@skin_nav handle=\"调用句柄\" /]");
        }
    }

    @Override
    public String name() {
        return "skin_nav";
    }
}
