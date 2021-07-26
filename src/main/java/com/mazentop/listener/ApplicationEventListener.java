package com.mazentop.listener;

import com.mazentop.plugins.event.Message;
import com.mazentop.searcher.ISearcher;
import com.mazentop.searcher.Snapshot;
import com.mazentop.searcher.index.IndexProvider;
import com.mazentop.searcher.index.impl.ProductParamIndexProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *  事件监听
 *
 * @author zhaoqt
 * @date 2019/5/1001:41
 */
@Component
@Slf4j
public class ApplicationEventListener {

    @Autowired
    ISearcher searcher;


    // 产品审核通过 发布消息
    //    EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, id));

    /**
     * 监听产品审核通过事件
     *
     * @param message
     */
    @Async
    @EventListener(condition="#message.source == T(com.mazentop.listener.MessageSource).PRODUCT_SELL")
    public void productSell(Message message) {
        // 创建商品检索索引
        searcher.update(new Snapshot(message.getData()), IndexProvider.M_PRODUCT);
        // 创建商品参数索引
        searcher.update(
                new Snapshot(
                        String.format("%s%s",
                                message.getData(), ProductParamIndexProvider.SID_SUFFIX)), IndexProvider.M_PRODUCT_PARAM);

    }

    /**
     * 监听批量产品审核通过事件
     *
     * @param message
     */
    @Async
    @EventListener(condition="#message.source == T(com.mazentop.listener.MessageSource).PRODUCTS_SELL")
    public void productsSell(Message message) {
        List<String> ids = message.getData();
        for (String id: ids) {
            // 创建商品检索索引
            searcher.update(new Snapshot(id), IndexProvider.M_PRODUCT);
            // 创建商品参数索引
            searcher.update(
                    new Snapshot(
                            String.format("%s%s",
                                    id, ProductParamIndexProvider.SID_SUFFIX)), IndexProvider.M_PRODUCT_PARAM);
        }
    }



    @Async
    @EventListener(condition="#message.source == T(com.mazentop.listener.MessageSource).PRODUCT_DEL")
    public void productDel(Message message) {
        // 删除商品检索索引
        searcher.delete(new Snapshot(message.getData()), IndexProvider.M_PRODUCT);
    }

    @Async
    @EventListener(condition="#message.source == T(com.mazentop.listener.MessageSource).PRODUCTS_DEL")
    public void productsDel(Message message) {
        List<String> ids = message.getData();
        for (String id: ids) {
            // 批量删除商品检索索引
            searcher.delete(new Snapshot(id), IndexProvider.M_PRODUCT);
        }
    }


    @EventListener(condition="#message.source == T(com.mazentop.listener.MessageSource).CMS_ARTICLE_RELEASE")
    public void cmsArticleRelease(Message message) {
        System.out.println("事假监听了 文章");
    }
}
