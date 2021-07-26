package com.mazentop.modules.skin.block.dto;

import com.mazentop.entity.SkinPage;
import com.mazentop.entity.SkinPageBlock;
import com.mazentop.entity.SkinPageLayout;
import com.mazentop.model.Status;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 块元素 数据显示
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/14 02:42
 */
@Data
public class BlockData {

    private transient SkinPageBlock block;

    private List<?> list;

    private Object data;

    public BlockData() {
        this.block = new SkinPageBlock();
    }

    public String getId() {
        return block.getId();
    }

    public String getPid() {
        return block.getPid();
    }

    public String getTemplateId() {
        return block.getTemplateId();
    }

    public String getTitle() {
        return block.getTitle();
    }

    public SkinPageLayout getLayout() {
        if(Utils.isBlank(block.getLayoutId())) {
            return SkinPageLayout.me();
        }
        return SkinPageLayout.me().setId(block.getLayoutId()).get();
    }

    public SkinPage getPage() {
        if(Utils.isBlank(block.getDataId())) {
            return SkinPage.me();
        }
        return SkinPage.me().setId(block.getDataId()).get();
    }

    public List<BlockData> getChilds() {
        if(Utils.isBlank(block.getId())) {
            return new ArrayList<>();
        }
        List<SkinPageBlock> skinPageBlocks = SkinPageBlock.me().setPid(block.getId())
                .setIsEnable(Status.YES)
                .setOrderByFields(Order.asc(SkinPageBlock.F_SORT)).find();
        return skinPageBlocks
                .stream().map(skinPageBlock -> {
                    BlockData blockData = new BlockData();
                    blockData.setBlock(skinPageBlock);
                    return blockData;
                }).collect(Collectors.toList());
    }

    public String getView() {
        return block.getView();
    }


    public String getCoverUrl() {
        return block.getCoverUrl();
    }


    public Integer getSort() {
        return block.getSort();
    }

    public Integer getIsEnable() {
        return block.getIsEnable();
    }

    public String getRemarks() {
        return block.getRemarks();
    }

    public static BlockData me() {
        return new BlockData();
    }

    public String getDataId() {
        return block.getDataId();
    }
}
