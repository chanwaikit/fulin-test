package com.mazentop.modules.emp.service;

import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProProductQa;
import com.mazentop.modules.emp.commond.ProProductQaCommond;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.data.R;
import com.mztframework.jwt.security.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author: zhoumei
 * @date: 2020/8/20
 * @description: 商品问答
 */
@Service
public class ProProductQaService {

    @Autowired
    BaseDao baseDao;

    /**
     * 分页
     * @return
     */
    public Page getPage(ProProductQaCommond commond) {
        commond.setOrderBy("problem_time desc");
        SearchTime searchTime = new SearchTime();
        if (commond.getStartTime() != null) {
            searchTime.setStart(commond.getStartTime());
        }
        if (commond.getEndTime() != null) {
            searchTime.setEnd(commond.getEndTime());
        }
        commond.setProblemTime(searchTime);
        if(!Utils.isBlank(commond.getProductName())) {
            commond.setProductName("%" + commond.getProductName() + "%");
        }
        List<ProProductQa> list = ProProductQa.me().find(commond);
        list.forEach(p -> {
            // 商品详情
            ProProductMaster proProductMaster = ProProductMaster.me().setId(p.getFkProductId()).get();
            p.addExten("productProImage",proProductMaster.getPrductPicImageUrl());
            p.addExten("productName",proProductMaster.getProductName());

            //提问人
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(p.getFkClienteleId()).get();
            p.addExten("questioner", cliClienteleInfo.getLoginName());

            //回答人
//            if(Utils.isNotBlank(p.getUserId())){
//                EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setId(p.getUserId()).get();
//                p.addExten("answer",empEmployeeInfo.getEmployeeName());
//            }
        });
        return new Page<>(list, commond);
    }


    /**
     * 不分页
     * @return
     */
    public List<ProProductQa> getList(ProProductQaCommond commond) {
        commond.setOrderBy("problem_time desc");
        List<ProProductQa> list = ProProductQa.me().find(commond);
        return list;
    }


    /**
     * 单个数据获取
     * @return
     */
    public ProProductQa get(String id) {
        ProProductQa proProductQa = ProProductQa.me().setId(id).get();

        // 商品详情
        ProProductMaster proProductMaster = ProProductMaster.me().setId(proProductQa.getFkProductId()).get();
        proProductQa.addExten("productProImage",proProductMaster.getPrductPicImageUrl());
        proProductQa.addExten("productName",proProductMaster.getProductName());

        //提问人
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(proProductQa.getFkClienteleId()).get();
        proProductQa.addExten("questioner", cliClienteleInfo.getClientSurname()+cliClienteleInfo.getClientName());

        //回答人
//        if(Utils.isNotBlank(proProductQa.getUserId())){
//            EmpEmployeeInfo empEmployeeInfo = EmpEmployeeInfo.me().setId(proProductQa.getUserId()).get();
//            proProductQa.addExten("answer",empEmployeeInfo.getEmployeeName());
//        }
        return proProductQa;
    }



    /**
     * 问题回答
     * @return
     */
    public R saveAnswer(ProProductQa dto) {
        dto.setUserId(Subject.id());
        dto.setAnswerTime(Utils.currentTimeSecond());
        dto.update();
        return R.ok();
    }

    /**
     * 刪除
     * @return
     */
    public R doDelete(List<String> ids) {
        ProProductQa.me().delete(ids);
        return R.ok();
    }

    /**
     * 修改是否可見
     * @return
     */
    public R updateQa(ProProductQa productQa) {
        productQa.update();
        return R.ok();
    }


    /**
     * 移动端提问
     * @param dto
     * @param userId
     * @return
     */
    public R saveMQa(ProProductQa dto, String userId) {
        // 默认不显示出来
//        ProProductMaster proProductMaster = ProProductMaster.me().setId(dto.getFkProductId()).get();
//        dto.setShopId(proProductMaster.getShopId());
        dto.setFkClienteleId(userId);
        dto.setIsDisplay(0);
        dto.setProblemTime(Utils.currentTimeSecond());
        dto.insert();
        return R.ok();
    }








}
