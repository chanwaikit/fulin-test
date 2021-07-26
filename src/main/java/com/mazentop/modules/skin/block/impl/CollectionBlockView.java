package com.mazentop.modules.skin.block.impl;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.block.dto.Collection;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinPageBlockDto;
import com.mazentop.util.Helper;
import com.mztframework.data.R;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;


/**
 * @author dengyan
 * @title: CollectionBlockView
 * @description: 商品专辑模块
 * @date 2020/6/19 19:34
 */
@Component(CollectionBlockView.MAPPING)
public class CollectionBlockView implements IBlockView{

    public static final String MAPPING = "collection";

    @Override
    public R saveOrUpdate(SkinPageBlockDto block) {
        Collection collection = JSON.parseObject(block.getContent(), Collection.class);
        if(!Objects.isNull(collection)){
            clearAllCollection(block);
            if(!Objects.isNull(collection.getCollections()) && !collection.getCollections().isEmpty()) {
                Helper.forEach(collection.getCollections(), (index, row) -> {
                    SkinPageBlock skinPageBlock = new SkinPageBlock();
                    skinPageBlock.setPid(block.getId());
                    skinPageBlock.setTitle(row.getTitle());
                    skinPageBlock.setDataId(row.getId());
                    skinPageBlock.setIsEnable(1);
                    skinPageBlock.setSort(index);
                    skinPageBlock.insert();
                });
            }
            block.setCoverUrl(block.getCover().getUrl());
            block.setTitle(collection.getTitle());
            block.setSubTitle(collection.getSubtitle());
            block.update();
        }else{
            clearAllCollection(block);
        }
        return R.ok();
    }

    @Override
    public BlockData query(SkinPageBlockCommond condition) {
        SkinPageBlock block = this.getBlock(condition);

        Collection collection = new Collection();
        List<Collection.Row> rows = Lists.newArrayList();

        if(!Objects.isNull(block)) {
            collection.setSubtitle(block.getSubTitle());
            collection.setTitle(block.getTitle());
            SkinPageBlockCommond skinPageBlockCommond = new SkinPageBlockCommond();
            skinPageBlockCommond.setPid(block.getId());
            List<SkinPageBlock> skinPageBlock = queryBlocks(skinPageBlockCommond);
            skinPageBlock.forEach(blocks -> {
                Collection.Row row = new Collection.Row();
                row.setId(blocks.getDataId());
                row.setTitle(blocks.getTitle());
                rows.add(row);
            });
        }
        if(!rows.isEmpty()) {
            collection.setCollections(rows);
        }
        return setViewData(collection, block);
    }
}
