package com.mazentop.modules.skin.block.impl;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.block.dto.MenuTree;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinPageBlockDto;
import com.mazentop.modules.skin.service.SkinPageBlockMenuService;
import com.mztframework.data.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author zhaoqt
 * @title: MenuBlockView
 * @description: 菜单
 * @date 2019/4/2911:33
 */
@Component(MenuBlockView.MAPPING)
public class MenuBlockView implements IBlockView {

    public static final String MAPPING = "nav";

    @Autowired
    SkinPageBlockMenuService skinPageBlockMenuService;

    @Override
    public BlockData query(SkinPageBlockCommond condition) {
        SkinPageBlock block = this.getBlock(condition);
        MenuTree menuTrees = new MenuTree();
        if(!Objects.isNull(block)) {
            SkinPageBlock skinPageBlock = SkinPageBlock.me().setPid(block.getId()).get();
            if(!Objects.isNull(skinPageBlock)) {
                SkinPageBlock blocks = SkinPageBlock.me().setId(skinPageBlock.getDataId()).get();
                menuTrees.setCollection(skinPageBlock.getDataId());
                menuTrees.setTitle(block.getTitle());
                menuTrees.setNavTitle(blocks.getTitle());
            }
        }
        return setViewData(menuTrees, block);
    }


    @Override
    public R saveOrUpdate(SkinPageBlockDto block) {
        MenuTree menu = JSON.parseObject(block.getContent(), MenuTree.class);
        if(!Objects.isNull(menu)){
            SkinPageBlock skinPageBlock = SkinPageBlock.me().setId(block.getId()).get();
            if(!Objects.isNull(skinPageBlock)){
                clearAllCollection(block);
                SkinPageBlock pageBlock = new SkinPageBlock();
                pageBlock.setPid(block.getId());
                pageBlock.setTitle(menu.getNavTitle());
                pageBlock.setDataId(menu.getCollection());
                pageBlock.setIsEnable(1);
                pageBlock.insert();
            }
            skinPageBlock.setTitle(menu.getNavTitle());
            skinPageBlock.setCoverUrl(block.getCover().getUrl());
            skinPageBlock.setRemarks(block.getRemarks());
            skinPageBlock.update();
        } else {
            clearAllCollection(block);
        }
        return R.ok();
    }

}
