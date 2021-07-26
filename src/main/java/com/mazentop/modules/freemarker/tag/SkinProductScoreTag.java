package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProComment;
import com.mazentop.model.BooleanEnum;
import com.mazentop.util.Helper;
import com.mztframework.cache.Options;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

@Component
@Slf4j
public class SkinProductScoreTag extends Tag{

    @Override
    public void execute() {
        String productId = getParam("productId");
        if("show".equals(Options.get("comment_star_display"))) {
            if(Helper.isNotEmpty(productId)) {
                Map<String, Object> map = new HashMap<>(2);
                List<ProComment> comments = ProComment.me().setIsAuditPass(BooleanEnum.TRUE.getValue()).setFkProductId(productId).find();
                if (comments.size() > 0){
                    OptionalDouble average = comments.stream().mapToInt(ProComment::getRangeNum).average();
                    BigDecimal avg = new BigDecimal(average.getAsDouble());
                    map.put("value", avg.setScale(1,BigDecimal.ROUND_DOWN));
                    map.put("num", comments.size());
                    setVariable("score", map);
                }
                renderBody();
            }else{
                log.error("[@skin_product_score /] 缺少必要参数 productId，例如：[@skin_product_score productId=\"产品ID\" /]");
            }
        }
    }

    @Override
    public String name() {
        return "skin_product_score";
    }
}
