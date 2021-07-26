package com.mazentop.modules.skin.service;


import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.mazentop.entity.SkinPage;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.entity.SkinPageLayout;
import com.mazentop.modules.skin.block.BlockViewFactory;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SkinPageBlockDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author dengy
 * 页面块逻辑类
 */
@Service
public class SkinPageBlockService {

    @Autowired
    BaseDao baseDao;


    public List<Map<String,Object>> getParentSkinPageBlockInfo(SkinPageBlockCommond commond){
        String sql = " select id,title from skin_page_block block where 1=1  and (block.pid IS NULL or block.pid = '')";
        Map<String,Object> param = new HashMap<>();
        if(Helper.isNotEmpty(commond.getView())){
            sql+= " and block.view = :pageId ";
            param.put("pageId",commond.getView());
        }
        sql += "order by sort asc";
        return baseDao.queryForList(sql,param);
    }
    public R doAddOrUpdateBlockInfo(SkinPageBlockDto skinPageBlock){
        if(Helper.isEmpty(skinPageBlock.getId())) {
            SkinPage skinPage = SkinPage.me().setId(skinPageBlock.getView()).get();
            skinPageBlock.setTemplateId(skinPage.getTemplateId());
            List<SkinPageBlock> skinPageBlockList  = SkinPageBlock.me().setView(skinPageBlock.getView()).find();
            int sort = 0;
            if(!skinPageBlockList.isEmpty()){
                List<Integer>maxSort = new ArrayList<>();
                skinPageBlockList.forEach(sorts->{
                        maxSort.add(sorts.getSort());
                });
                sort = Collections.max(maxSort)+1;
            }
            skinPageBlock.setHandle(skinPage.getId());
            skinPageBlock.setSort(sort);
            skinPageBlock.insert();
            return R.ok();
        }else{
            skinPageBlock.update();
            return R.ok();
        }
    }

    public List<SkinPageBlock> findSkinPageBlockByPage(String pageId) {
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("views", Lists.newArrayList(pageId, "footer","header"));
        SkinPage skinPage = SkinPage.me().setId(pageId).get();
        paramMap.put("templateId", skinPage.getTemplateId());
        List<SkinPageBlock> pageBlockList = baseDao.queryForList(
                "select * from skin_page_block where `view` in (:views) and template_id=:templateId order by sort asc, add_time desc ",
                SkinPageBlock.me().newMapper(), paramMap);

        pageBlockList.forEach(this::skinPageBlockExten);

        return pageBlockList;
    }
    public List<SkinPageBlock> findSkinPageBlockByOrder(String pageId) {
        Map<String, Object> paramMap = new HashMap<>(1);
        paramMap.put("view", pageId);
        SkinPage skinPage = SkinPage.me().setId(pageId).get();
        paramMap.put("templateId", skinPage.getTemplateId());
        List<SkinPageBlock> pageBlockList = baseDao.queryForList(
                "select * from skin_page_block where `view` =:view and template_id=:templateId order by sort asc, add_time desc ",
                SkinPageBlock.me().newMapper(), paramMap);

        pageBlockList.forEach(this::skinPageBlockExten);

        return pageBlockList;
    }

    public SkinPageBlock getSkinPageBlockInfo(String blockId){
        SkinPageBlock skinPageBlock = SkinPageBlock.me().setId(blockId).get();
        skinPageBlockExten(skinPageBlock);
        return skinPageBlock;
    }

    private void skinPageBlockExten(SkinPageBlock skinPageBlock) {
        skinPageBlock.addExten("layout", SkinPageLayout.me().setId(skinPageBlock.getLayoutId()).get());
    }

    public R addOrUpdateBlockData(SkinPageBlockDto skinPageBlock) {
        BlockViewFactory.blockView(skinPageBlock.getViewType()).saveOrUpdate(skinPageBlock);
        return R.ok();
    }

    public BlockData getBlockData(String viewType, SkinPageBlockCommond skinPageBlockCommond) {
       return BlockViewFactory.blockView(viewType).query(skinPageBlockCommond);
    }

    public R updateBlockHandle(SkinPageBlockDto skinPageBlock) {
        if(!Utils.isBlank(skinPageBlock.getHandle()) && !Utils.isBlank(skinPageBlock.getId())) {
            SkinPageBlock skinPageBlockDb = new SkinPageBlock().setId(skinPageBlock.getId()).get();
            if(!Objects.isNull(skinPageBlockDb)) {
                skinPageBlockDb.setHandle(
                        generateNoRepeatHandle(skinPageBlockDb.getTemplateId(),
                                skinPageBlock.getHandle(), skinPageBlock.getHandle()));
                skinPageBlockDb.update();
            }
        }
        return R.ok();
    }

    private String generateNoRepeatHandle(String templateId, String originalHandle, String newHandle) {
        if(!SkinPageBlock.me().setTemplateId(templateId).setHandle(newHandle).exist()) {
            return newHandle;
        } else {
           return generateNoRepeatHandle(templateId, originalHandle,
                    String.format("%s%s", originalHandle, RandomUtil.randomString(6)));
        }
    }
}
