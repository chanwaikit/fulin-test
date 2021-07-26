package com.mazentop.modules.emp.service;

import com.mazentop.entity.SysAdvertisementPop;
import com.mazentop.modules.emp.commond.SysAdvertisementPopCommond;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysAdvertisementPopService {
    @Autowired
    BaseDao baseDao;

    public Page sysAdvertisementPopList(SysAdvertisementPopCommond commond){
        List<SysAdvertisementPop> list = SysAdvertisementPop.me().find(commond);
        return new Page(list,commond);
    }
}
