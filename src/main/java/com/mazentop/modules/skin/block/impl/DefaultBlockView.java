package com.mazentop.modules.skin.block.impl;

import com.mazentop.entity.SkinPageBlock;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zhaoqt
 * @title: DefaultBlockVIew
 * @description: 默认会计元素提供支持，
 *
 * 主要查询一级 节点数据显示
 *
 * @date 2019/4/2911:21
 */
@Component(DefaultBlockView.MAPPING)
public class DefaultBlockView implements IBlockView {

    public static final String MAPPING = "defaultblockview";


    @Override
    public BlockData query(SkinPageBlockCommond condition) {
        List<SkinPageBlock> skinPageBlocks = this.queryBlocks(condition);
        if(!skinPageBlocks.isEmpty()) {
            return setViewList(transform(skinPageBlocks), skinPageBlocks.get(0));
        }
        return new BlockData();
    }
}
