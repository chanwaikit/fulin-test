package com.mazentop.modules.skin.block.impl;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.SkinPage;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.block.dto.CollectionGoods;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinPageBlockDto;
import com.mazentop.modules.skin.service.SkinPageService;
import com.mztframework.data.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author dengyan
 * @title: CollectionGoodsView
 * @description: 专辑商品模块
 * @date 2020/6/19 19:34
 */
@Component(CollectionGoodsView.MAPPING)
public class CollectionGoodsView implements IBlockView {

    public static final String MAPPING = "collection-goods";

    @Autowired
    SkinPageService skinPageService;

    @Override
    public R saveOrUpdate(SkinPageBlockDto block) {
        CollectionGoods goods = JSON.parseObject(block.getContent(), CollectionGoods.class);
        if(!Objects.isNull(goods)){
             SkinPageBlock skinPageBlock = SkinPageBlock.me().setId(block.getId()).get();
             skinPageBlock.setTitle(goods.getTitle());
             skinPageBlock.setDataId(goods.getCollection());
             skinPageBlock.setRemarks(block.getRemarks());
             skinPageBlock.setCoverUrl(block.getCover().getUrl());
             skinPageBlock.update();
        }
        return R.ok();
    }

    @Override
    public BlockData query(SkinPageBlockCommond condition) {
        SkinPageBlock block = this.getBlock(condition);

        CollectionGoods collection = new CollectionGoods();
        if(!Objects.isNull(block)) {
            SkinPage skinPage = SkinPage.me().setId(block.getDataId()).get();
            if(!Objects.isNull(skinPage)) {
                collection.setCollection(block.getDataId());
                collection.setTitle(block.getTitle());
                collection.setSpecialTitle(skinPage.getTitle());
            }
        }
        return setViewData(collection, block);
    }
}
