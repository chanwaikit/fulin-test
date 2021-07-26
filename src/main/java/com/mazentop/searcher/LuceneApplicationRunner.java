package com.mazentop.searcher;

import com.mazentop.CmsConfig;
import com.mazentop.entity.EvaProProduct;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.listener.MessageSource;
import com.mazentop.model.BooleanEnum;
import com.mazentop.plugins.event.EventHolder;
import com.mazentop.plugins.event.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LuceneApplicationRunner implements ApplicationRunner {

    @Autowired
    private CmsConfig cmsConfig;
    /**
     * 开发环境初始化 lucene
     * @param args
     */
    @Override
    public void run(ApplicationArguments args) {
        try {
            if (cmsConfig.getLucene().isDebug()){
                List<EvaProProduct> productList = EvaProProduct.me().setIsShelve(BooleanEnum.TRUE.getValue()).find();
                List<String> ids = productList.stream().map(EvaProProduct::getId).collect(Collectors.toList());
                if (ids.size()>0){
                    EventHolder.publishEvent(new Message(MessageSource.PRODUCTS_SELL, ids));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
