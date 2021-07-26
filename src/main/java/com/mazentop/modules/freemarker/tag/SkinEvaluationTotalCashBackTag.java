package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.entity.EvaUserBill;
import com.mazentop.modules.web.User;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class SkinEvaluationTotalCashBackTag extends Tag{

    @Autowired
    BaseDao baseDao;

    @Override
    public void execute() {

        Map<String, Object> map = new HashMap<>(1);
        if(User.isAuth()){
            // 总提现金额
            EvaUserBill evaUserBill = EvaUserBill.me().setFkClienteleId(User.id()).get();
            if(Objects.nonNull(evaUserBill)){
                map.put("totalCashBack", Helper.transformF2Y(evaUserBill.getTotalCashBack()));
            }else{
                map.put("totalCashBack", 0);
            }
            // 待提现金额
            String sql = "SELECT sum(rebate) as rebate from  eva_ord_order WHERE status in (1,2,3,4) AND fk_clientele_id =:userId AND is_enable = 1";
            Map<String, Object> param = new HashMap<>(1);
            param.put("userId",User.id());
            Long totalRebate = baseDao.queryForLong(sql,param);
            map.put("pendingCashBack", Helper.transformF2Y(totalRebate));
        }
        setVariable("cashBack", map);
        renderBody();
    }

    @Override
    public String name() {
        return "skin_evaluation_total_cash_back";
    }
}
