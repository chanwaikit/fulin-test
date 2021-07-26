package com.mazentop.modules.skin.block;

import com.mazentop.modules.skin.block.impl.DefaultBlockView;
import com.mztframework.spring.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 块view工厂
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/14 04:04
 */
@Component
public class BlockViewFactory {

    @Autowired
    Map<String, IBlockView> blockViewMap;

    private IBlockView getBlockView(String key) {
        if(!blockViewMap.containsKey(key)) {
            return blockViewMap.get(DefaultBlockView.MAPPING);
        }
       return blockViewMap.get(key);
    }

    public static IBlockView blockView(String key) {
        return SpringContextHolder.getBean(BlockViewFactory.class).getBlockView(key);
    }
}
