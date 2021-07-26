package com.mazentop.modules.skin.block;

import com.mazentop.entity.SkinPageBlock;
import com.mazentop.entity.SkinPageBlockNav;
import com.mazentop.model.Status;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinPageBlockDto;
import com.mztframework.dao.annotation.Order;
import com.mztframework.data.R;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.Objects;

/**
 * @author zhaoqt
 * @title: IBlockView
 * @description: 显示块元素接口
 *
 *      提供块级数据布局、数据存储
 * @date 2019/4/2910:47
 */
public interface IBlockView {

    String VIEW_MENU = "menu";

    String VIEW_PAGE = "page";

    String VIEW_BLOCK = "block";

    String VIEW_NAV = "nav";

    String VIEW_PAGE_NODE = "node";


    /**
     * 区域存储 顶级可直接调用
     *
     * @param block
     * @return
     */
    default R saveOrUpdate(SkinPageBlockDto block) {
        if(Objects.isNull(block.getIsEnable())) {
            block.setIsEnable(Status.NO);
        }
        block.insertOrUpdate();
        return R.ok();
    }

    /**
     * 接口提供默认查询块级元素数据方法
     *
     * @param skinPageBlockCommond
     * @return
     */
    default List<SkinPageBlock> queryBlocks(SkinPageBlockCommond skinPageBlockCommond) {
        return  SkinPageBlock.me().setOrderByFields(Order.asc(SkinPageBlockDto.F_SORT)).find(skinPageBlockCommond);
    }

    default SkinPageBlock getBlock(SkinPageBlockCommond skinPageBlockCommond) {
        List<SkinPageBlock> skinPageBlocks =  queryBlocks(skinPageBlockCommond);
        return skinPageBlocks.isEmpty() ? null : skinPageBlocks.get(0);
    }

    /**
     * 数据转换 LibBlock to  View
     *
     * @param blocks
     * @return
     */
    default List<BlockData> transform(List<SkinPageBlock> blocks) {
        List<BlockData> views = Lists.newArrayList();
        blocks.forEach(block -> {
            BlockData blockView = new BlockData();
            blockView.setBlock(block);
            views.add(blockView);
        });
        return views;
    }

    /**
     * list 结果放入 BlockView
     * @param list
     * @return
     */
    default BlockData setViewList(List<?> list, SkinPageBlock block) {
        BlockData blockView = BlockData.me();
        if(!Objects.isNull(block)) {
            blockView.setBlock(block);
        }
        blockView.setList(list);
        return blockView;
    }


    default BlockData setViewData(Object data, SkinPageBlock block) {
        BlockData blockView = BlockData.me();
        if(!Objects.isNull(block)) {
            blockView.setBlock(block);
        }
        blockView.setData(data);
        return blockView;
    }

    /**
     * 清空所有菜单 判断页面参数 为空执行
     */
    default void clearAllNavChildren(SkinPageBlockDto block) {
        SkinPageBlockNav.me().setBlockId(block.getId()).delete();
    }

    /**
     * 清除商品专辑块
     */
    default void clearAllCollection(SkinPageBlockDto block){
        SkinPageBlock.me().setPid(block.getId()).delete();
    }

    default List<SkinPageBlockNav> queryBlockNavs(SkinPageBlock block, String pid) {
       return SkinPageBlockNav.me().setBlockId(block.getId()).setPid(pid).list(Order.asc(SkinPageBlockNav.F_SORT));
    }


    /**
     * 块级元素查询
     *
     * @param condition  查询条件
     * @return 返回查询结果，包含块元素基本信息，以及携带数据信息
     */
    BlockData query(SkinPageBlockCommond condition);
}
