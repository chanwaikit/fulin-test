package com.mazentop.modules.skin.block.impl;

import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author zhaoqt
 * @title: PageBlockView
 * @description: 页面区块类型视图
 * @date 2019/5/322:22
 */
@Component(PageBlockView.MAPPING)
public class PageBlockView implements IBlockView {

    @Autowired
    BaseDao baseDao;

    public static final String MAPPING = "block";

    @Override
    public BlockData query(SkinPageBlockCommond condition) {
        return setViewList(transform(this.queryBlocks(condition)), null);
    }
}
