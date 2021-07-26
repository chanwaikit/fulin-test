package com.mazentop.modules.evaluation.service;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.EvaOrdOrder;
import com.mazentop.entity.EvaUserRecommendation;
import com.mazentop.model.EvaOrdOrderStatusEnum;
import com.mazentop.modules.evaluation.commond.EvaCommissionCommond;
import com.mazentop.modules.evaluation.commond.InvitedCommond;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zhoumei
 * @date: 2021/1/8
 * @description: 佣金明细
 */

@Service
public class EvaCommissionDetailsService {

    @Autowired
    BaseDao baseDao;

    public Page getPage(EvaCommissionCommond commond){
        StringBuilder sql =new StringBuilder();
        Map<String,Object> param=new HashMap<>();
        sql.append(" select c.id,c.email, c.client_surname,c.client_name, b.commission_balance,b.total_commission");
        sql.append(" ,(select count(1) from  eva_user_recommendation where referrer_id=c.id) as inviteNumber ");
        sql.append("  from cli_clientele_info c left join eva_user_bill b on   c.id= b.fk_clientele_id  where 1=1 ");
        if (StrUtil.isNotBlank(commond.getEmail())){
            param.put("email","%"+commond.getEmail()+"%");
            sql.append(" and c.email like :email ");
        }
        if (StrUtil.isNotBlank(commond.getPhone())){
            param.put("phone","%"+commond.getPhone()+"%");
            sql.append(" and c.phone like :email ");
        }
        if (StrUtil.isBlank(commond.getO())){
            sql.append(" order by b.commission_balance desc ");
        }
        List<Map<String, Object>> list = baseDao.paginate(sql.toString(), param, commond);

        for (Map<String, Object> map : list) {
            map.put("commission_balance", Helper.transformF2Y(map.get("commission_balance")));
            map.put("total_commission", Helper.transformF2Y(map.get("total_commission")));

        }
        return new Page(list,commond);
    }

    public Page getBeInvitedByUser(InvitedCommond commond){
        StringBuilder sql =new StringBuilder();
        Map<String,Object> param=new HashMap<>();
        sql.append(" select c.id, c.email,c.phone from cli_clientele_info c, eva_user_recommendation r where c.id =r.user_id   ");
        if (StrUtil.isNotBlank(commond.getUserId())){
            param.put("userId",commond.getUserId());
            sql.append(" and r.referrer_id=:userId ");
        }
        if (StrUtil.isNotBlank(commond.getEmail())){
            param.put("email","%"+commond.getEmail()+"%");
            sql.append(" and c.email like :email ");
        }
        if (StrUtil.isNotBlank(commond.getPhone())){
            param.put("phone","%"+commond.getPhone()+"%");
            sql.append(" and c.phone like :email ");
        }
        List<Map<String, Object>> list = baseDao.paginate(sql.toString(), param, commond);
        for (Map<String, Object> map : list) {
            Long count = EvaOrdOrder.me().setAddUserId(map.get("id").toString()).setStatus(EvaOrdOrderStatusEnum.CASHBACK_ADOPT.getStatus()).findCount();
            map.put("isBuy",false);
            if (count>0){
                map.put("isBuy",true);
            }
        }
        return new Page(list,commond);
    }


}
