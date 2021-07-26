package com.mazentop.modules.skin.block.impl;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.entity.SkinPageBlockNav;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.block.dto.Slide;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinPageBlockDto;
import com.mazentop.util.Helper;
import com.mztframework.data.R;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author zhaoqt
 * @title: SlideBlockView
 * @description: 幻灯片模块
 * @date 2019/5/613:54
 */
@Component(SlideBlockView.MAPPING)
public class SlideBlockView implements IBlockView {

    public static final String MAPPING = "slide";

    @Override
    public R saveOrUpdate(SkinPageBlockDto block) {
        Slide slide = JSON.parseObject(block.getContent(), Slide.class);
        if(!Objects.isNull(slide)) {
            clearAllNavChildren(block);
            if(!Objects.isNull(slide.getSlides()) && !slide.getSlides().isEmpty()) {
                Helper.forEach(slide.getSlides(), (index, row) -> {
                    SkinPageBlockNav skinPageBlockNav = new SkinPageBlockNav();
                    skinPageBlockNav.setBlockId(block.getId());
                    skinPageBlockNav.setPid(MAPPING);
                    skinPageBlockNav.setUrl(row.getUrl());
                    skinPageBlockNav.setTarget(row.getTarget());
                    skinPageBlockNav.setTitle(row.getTitle());
                    skinPageBlockNav.setType(row.getType());
                    skinPageBlockNav.setInlineData(row.toInlineData());
                    skinPageBlockNav.setSort(index);
                    skinPageBlockNav.insert();
                });
            }
            block.setCoverUrl(block.getCover().getUrl());
            block.setSettings(slide.toSettings());
            block.update();
        } else {
            clearAllNavChildren(block);
        }
        return R.ok();
    }

    @Override
    public BlockData query(SkinPageBlockCommond condition) {
        SkinPageBlock block = this.getBlock(condition);

        Slide slide = new Slide();
        List<Slide.Row> slides = Lists.newArrayList();

        if(!Objects.isNull(block)) {
            slide = JSON.parseObject(block.getSettings(), Slide.class);
            List<SkinPageBlockNav> skinPageBlockNavs = queryBlockNavs(block, MAPPING);
            skinPageBlockNavs.forEach(skinPageBlockNav -> {
                Slide.Row row = new Slide.Row();
                BeanUtils.copyProperties(skinPageBlockNav, row);
                Slide.Row inlineDataRow = JSON.parseObject(skinPageBlockNav.getInlineData(), Slide.Row.class);
                if(!Objects.isNull(inlineDataRow.getPcImage())){
                    row.setPcImage(inlineDataRow.getPcImage());
                }
                if(!Objects.isNull(inlineDataRow.getMobileImage())){
                    row.setMobileImage(inlineDataRow.getMobileImage());
                }
                slides.add(row);
            });
        }
        if(!slides.isEmpty()) {
            slide.setSlides(slides);
        }
        return setViewData(slide, block);
    }
}
