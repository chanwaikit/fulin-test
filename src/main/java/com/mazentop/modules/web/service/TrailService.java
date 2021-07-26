package com.mazentop.modules.web.service;

import com.mazentop.entity.CliClienteleGoodsTrail;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrailService {

    /**
     * 添加客户轨迹记录
     * @return
     */
    public R addClientTrail(Subject subject){


        return R.ok();
    }

}
