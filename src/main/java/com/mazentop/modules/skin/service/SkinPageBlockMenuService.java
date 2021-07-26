package com.mazentop.modules.skin.service;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.entity.SkinPageBlockNav;
import com.mazentop.model.Status;
import com.mazentop.modules.skin.block.dto.MenuTree;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinBlockMenuDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoqt
 */
@Service
public class SkinPageBlockMenuService {

    @Autowired
    BaseDao baseDao;


    public Page<SkinPageBlock> findSkinPageMenuList(SkinPageBlockCommond commond) {
        return new Page<>(SkinPageBlock.me().find(commond), commond);
    }

    public SkinBlockMenuDto getSkinPageBlockMenu(String blockId) {
        SkinBlockMenuDto skinBlockMenuDto = new SkinBlockMenuDto();
        SkinPageBlock skinPageBlock = SkinPageBlock.me().setId(blockId).get();
        List<MenuTree> menuTrees = new ArrayList<>();
        queryTrees(menuTrees, skinPageBlock, Status.TREE_ROOT_NODE);
        skinBlockMenuDto.setMenus(menuTrees);
        skinBlockMenuDto.setTitle(skinPageBlock.getTitle());
        skinBlockMenuDto.setId(skinPageBlock.getId());
        return skinBlockMenuDto;
    }

    public R saveSkinPageMenu(SkinBlockMenuDto skinBlockMenuDto) {
        SkinPageBlock skinPageBlock = SkinPageBlock.me();
        skinPageBlock.setView("menu");
        skinPageBlock.setTitle(skinBlockMenuDto.getTitle());
        skinPageBlock.setId(skinBlockMenuDto.getId());

        if(!Utils.isBlank(skinBlockMenuDto.getId())) {
            skinPageBlock.update();
        } else {
            skinPageBlock.insert();
        }
        skinBlockMenuDto.setId(skinPageBlock.getId());

        SkinPageBlockNav.me().setBlockId(skinPageBlock.getId()).delete();

        insertTrees(skinBlockMenuDto.getMenus(), skinPageBlock, Status.TREE_ROOT_NODE);
        return R.ok();
    }

    private void insertTrees(List<MenuTree> menuTrees, SkinPageBlock block, String pid) {
        Helper.forEach(menuTrees, (index, menuTree) -> {
            SkinPageBlockNav skinPageBlockNav = new SkinPageBlockNav();
            BeanUtils.copyProperties(menuTree, skinPageBlockNav, "inlineData");
            if (MenuTree.TYPE_CLASSIFICATIONS.equals(menuTree.getType())) {
                skinPageBlockNav.setInlineData(JSON.toJSONString(menuTree.getInlineData()));
            }
            skinPageBlockNav.setPid(pid);
            skinPageBlockNav.setBlockId(block.getId());
            skinPageBlockNav.setSort(index);
            skinPageBlockNav.setIsParent(menuTree.isChild() ? Status.YES : Status.NO);
            skinPageBlockNav.insert();
            if(menuTree.isChild()) {
                insertTrees(menuTree.getChildren(), block, skinPageBlockNav.getId());
            }
        });
    }

    public void queryTrees(List<MenuTree> menuTrees,  SkinPageBlock block, String pid) {
        List<SkinPageBlockNav> skinPageBlockNavs = SkinPageBlockNav.me().setBlockId(block.getId()).setPid(pid).setOrderByFields(Order.asc(SkinPageBlockNav.F_SORT)).list();

        for(SkinPageBlockNav skinPageBlockNav : skinPageBlockNavs) {
            MenuTree menuTree = new MenuTree();
            BeanUtils.copyProperties(skinPageBlockNav, menuTree, "inlineData");
            if (MenuTree.TYPE_CLASSIFICATIONS.equals(menuTree.getType())) {
                menuTree.setInlineData(JSON.parseArray(skinPageBlockNav.getInlineData(), ArrayList.class));
            }
            menuTrees.add(menuTree);
            // 当前节点判断是否父节点，若是父节点 必然包含子节点 进行一次查询
            if(Status.YES.equals(skinPageBlockNav.getIsParent())) {
                List<MenuTree> children = Lists.newArrayList();
                menuTree.setChildren(children);
                queryTrees(children, block, skinPageBlockNav.getId());
            }
        }
    }

    public R delSkinPageBlockMenu(String blockId) {
        SkinPageBlock.me().setId(blockId).delete();
        SkinPageBlockNav.me().setBlockId(blockId).delete();
        return R.ok();
    }
}
