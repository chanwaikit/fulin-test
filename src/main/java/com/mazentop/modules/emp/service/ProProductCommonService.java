package com.mazentop.modules.emp.service;

import com.mazentop.entity.ProProductCommon;
import com.mazentop.entity.SysTemplate;
import com.mazentop.modules.emp.commond.productCommonCommond;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProProductCommonService {

    @Autowired
    BaseDao baseDao;

    /**
     * 分頁
     * @param commond
     * @return
     */
    public Page getPage(productCommonCommond commond){
        List<ProProductCommon> list = ProProductCommon.me().find(commond);
        return new Page(list,commond);
    }


    public ProProductCommon getProductCommon(String id){
        return ProProductCommon.me().setId(id).get();
    }


    public R addOrUpdate(ProProductCommon productCommon){
        productCommon.insertOrUpdate();
        return R.ok();
    }


    public R delProductCommon(List<String> ids){
        ProProductCommon.me().delete(ids);
        return R.ok();
    }


}
