package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.ProComment;
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
public class SkinProductScorePercentageTag extends Tag{

    @Override
    public void execute() {
        String productId = getParam("productId");
        if("show".equals(Options.get("comment_star_display"))) {
            if(Helper.isNotEmpty(productId)) {
                Map<String, Object> map = new HashMap<>(2);
                List<ProComment> comments = ProComment.me().setFkProductId(productId).setIsAuditPass(1).find();
                Integer sumCount = comments.size();
                if (sumCount > 0) {
                    OptionalDouble average = comments.stream().mapToInt(ProComment::getRangeNum).average();
                    BigDecimal avg = new BigDecimal(average.getAsDouble());
                    map.put("value", avg.setScale(2,BigDecimal.ROUND_DOWN));
                    map.put("num", sumCount);
                    // 计算星际占比

                    //一星评论数量
                    Long rate1 = ProComment.me().setFkProductId(productId).setRangeNum(1).findCount();

                    //二星评论数量
                    Long rate2 = ProComment.me().setFkProductId(productId).setRangeNum(2).findCount();

                    //三星评论数量
                    Long rate3 = ProComment.me().setFkProductId(productId).setRangeNum(3).findCount();

                    //四星评论数量
                    Long rate4 = ProComment.me().setFkProductId(productId).setRangeNum(4).findCount();

                    //五星评论数量
                    Long rate5 = ProComment.me().setFkProductId(productId).setRangeNum(5).findCount();

                    // 计算星占的百分率
                    double rise1 = (double) rate1/sumCount;
                    map.put("rate1_percentage", rise1 * 100);

                    double rise2 = (double) rate2/sumCount;
                    map.put("rate2_percentage",rise2 * 100);

                    double rise3 = (double) rate3/sumCount;
                    map.put("rate3_percentage", rise3 * 100);

                    double rise4 = (double) rate4/sumCount;
                    map.put("rate4_percentage", rise4 * 100);

                    double rise5 = (double) rate5/sumCount;
                    map.put("rate5_percentage", rise5 * 100);

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
        return "skin_product_score_percentage";
    }
}
