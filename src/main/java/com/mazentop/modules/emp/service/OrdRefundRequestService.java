package com.mazentop.modules.emp.service;

import com.mazentop.modules.emp.commond.OrdRefundRequestCommond;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.jwt.security.Subject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: dengy
 * @date: 2020/3/16
 * @description:
 */
@Service
public class OrdRefundRequestService {

    @Autowired
    BaseDao baseDao;

    public Page ordSalesOrderList(OrdRefundRequestCommond ordRefundRequestCommond){
        StringBuilder sql = new StringBuilder(" select * from ord_refund_request where 1=1 ");
        Map<String,Object> params = new HashMap<>();
        if (ordRefundRequestCommond.getStartTime() != null) {
            sql.append(" and add_time >= :startTime ");
            params.put("startTime",ordRefundRequestCommond.getStartTime());
        }
        if (ordRefundRequestCommond.getEndTime() != null) {
            sql.append(" and add_time <= :endTime ");
            params.put("endTime",ordRefundRequestCommond.getEndTime());
        }
        if(StringUtils.isNotEmpty(ordRefundRequestCommond.getQuery())){
            sql.append(" and (refund_stream_no like :query or fk_sales_order_id like :query or client_name like :query) ");
            params.put("query",ordRefundRequestCommond.getQuery());
        }
        if(StringUtils.isNotEmpty(ordRefundRequestCommond.getRefundRequestStatus())){
            sql.append(" and refund_request_status = :status ");
            params.put("status",ordRefundRequestCommond.getRefundRequestStatus());
        }
        sql.append(" and company_id = :companyId order by add_time desc");
        params.put("companyId", Subject.group());
        List<Map<String,Object>> list = baseDao.paginate(sql.toString(),params,ordRefundRequestCommond);
        if(!list.isEmpty()){
            list.forEach(map->{
                if(Objects.isNull(map.get("refund_amount"))){
                    map.put("refund_amount",0);
                }else{
                    map.put("refund_amount", Helper.transformF2Y(map.get("refund_amount")));
                }
            });
        }
        return new Page(list,ordRefundRequestCommond);
    }
}
