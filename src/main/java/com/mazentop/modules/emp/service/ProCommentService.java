package com.mazentop.modules.emp.service;

import com.mazentop.entity.*;
import com.mazentop.listener.MessageSource;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.ProCommentCommond;
import com.mazentop.plugins.event.EventHolder;
import com.mazentop.plugins.event.Message;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;

import com.mztframework.snowflake.IDSnowflake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author: dengy
 * @date: 2020/3/12
 * @description:
 */
@Service
public class ProCommentService {

    @Autowired
    BaseDao baseDao;


    /**
     * 评论分页
     * @param commond
     * @return
     */
    public Page proCommentList(ProCommentCommond commond) {
        commond.setOrderBy("operation_time desc");
        List<ProComment> list = ProComment.me().find(commond);
        for (ProComment proComment : list) {
            // 国家
            if(StringUtils.isNotEmpty(proComment.getFkCountryId())){
                SysCountry country = SysCountry.me().setId(proComment.getFkCountryId()).get();
                proComment.addExten("countryName",country.getName());
            }
            // 商品名称
            ProProductMaster productMaster = ProProductMaster.me().setId(proComment.getFkProductId()).get();
            if(Objects.nonNull(productMaster)){
                proComment.addExten("productName",productMaster.getProductName());
            }

        }
        return new Page(list, commond);
    }

    /**
     * 获取商品评论数量
     * @return
     */
    public HashMap<String, Object> getCommentStatus() {
        HashMap<String, Object> map = new HashMap<>(2);

        map.put("productCount", ProProductMaster.me().setIsEnable(Status.YES).findCount());
        map.put("commentCount", ProComment.me().findCount());

        return map;
    }


    /**
     * 编辑评论
     * @param proComment
     * @return
     */
    public R doProCommentUpdate(ProComment proComment) {
        if (StringUtils.isEmpty(proComment.getId())) {
            return R.toast("评论信息获取失败!");
        }
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return R.toast("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();

        if(!Helper.isEmpty(proComment.getAddTime())){
            proComment.setAddTime(proComment.getAddTime() / 1000L);
        }
        if(Status.YES.equals(proComment.getIsDisplay())){
            proComment.setAuditTime(Utils.currentTimeSecond());
            proComment.setAuditer(curEmployee.getEmployeeName());
            proComment.setIsAuditPass(Status.YES);
        }else if(Status.NO.equals(proComment.getIsDisplay())){
            proComment.setAuditTime(null);
            proComment.setAuditer(null);
            proComment.setIsAuditPass(Status.NO);
        }

        proComment.setOperationTime(Utils.currentTimeSecond());
        proComment.setOperationUserId(curEmployee.getId());
        proComment.setOperationUserName(curEmployee.getEmployeeName());
        proComment.update();
        // 如果商品是上架状态，需要发布一下，首页要看评论数量
        ProComment comment = ProComment.me().setId(proComment.getId()).get();
        ProProductMaster product = ProProductMaster.me().setId(comment.getFkProductId()).get();
        if (product.getIsShelve() == 1) {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, comment.getFkProductId()));
        }
        return R.ok();
    }

    /**
     * 删除评论
     * @param ids
     * @return
     */
    public R doDeleteProComment(List<String> ids) {
        if (ids.isEmpty()) {
            return R.toast("id获取失败!");
        }
        for (String id : ids) {
            ProComment comment = ProComment.me().setId(id).get();
            ProComment.me().setId(id).delete();

            // 如果商品是上架状态，需要发布一下，首页要看评论数量
            ProProductMaster product = ProProductMaster.me().setId(comment.getFkProductId()).get();
            if (product.getIsShelve() == 1) {
                EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, comment.getFkProductId()));
            }
        }
        return R.ok();
    }

    /**
     *  新增评论
      * @param comments
     *  @param productId -商品id
     * @return
     */
    public R doProCommentSave(String productId , List<ProComment> comments) {
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return R.toast("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        for (ProComment comment : comments ) {
            Long addTime = comment.getAddTime();

            String commentId = IDSnowflake.id();

            comment.setId(commentId);
            comment.setOperationTime(Utils.currentTimeSecond());
            comment.setOperationUserId(curEmployee.getId());
            comment.setOperationUserName(curEmployee.getEmployeeName());
            comment.setIsAuditPass(Status.YES);
            comment.setIsDisplay(Status.YES);
            comment.setFkProductId(productId);
            comment.insert();

            ProComment proComment = ProComment.me().setId(commentId).get();
            if(!Helper.isEmpty(addTime)){
                proComment.setAddTime(addTime / 1000L);
                proComment.update();
            }
    }
        // 如果商品是上架状态，需要发布一下，首页要看评论数量
        ProProductMaster product = ProProductMaster.me().setId(productId).get();
        if (product.getIsShelve() == 1) {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, productId));
        }
        return R.ok();
    }


    /**
     * 复制评论
     * @param commentId-评论id
     * @return
     */
    public R doCopy(String commentId) {
        if (StringUtils.isEmpty(commentId)) {
            return R.toast("评论信息获取失败!");
        }
        ProComment proComment = ProComment.me().setId(commentId).get();
        if(Objects.isNull(proComment)){
            return R.toast("评论信息获取失败!");
        }

        ProComment copyComment = new ProComment();

        BeanUtils.copyProperties(proComment,copyComment);
        copyComment.setId(null);
        copyComment.insert();

        // 如果商品是上架状态，需要发布一下，首页要看评论数量
        ProProductMaster product = ProProductMaster.me().setId(proComment.getFkProductId()).get();
        if (product.getIsShelve() == 1) {
            EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, proComment.getFkProductId()));
        }
        return R.ok();
    }


    /**
     * 查商品评论情况
     * @param productId
     * @return
     */
    public Map<String,Object> getProductCommentDetails(String productId) {
        Map<String,Object> map = new HashMap<>(15);

        // 商品的平均评星
        String sql = "select sum(range_num) from pro_comment WHERE fk_product_id = :productId";
        map.put("productId",productId);
        long rangeNum = baseDao.queryForLong(sql, map);

        map.clear();

        // 已发布评论数量
        Long publishedCount = ProComment.me().setFkProductId(productId).setIsDisplay(Status.YES).findCount();
        map.put("publishedCount",publishedCount);

        // 未发布评论数量
        Long unpublishedCount = ProComment.me().setFkProductId(productId).setIsDisplay(Status.NO).findCount();
        map.put("unpublishedCount",unpublishedCount);

        //商品总评论数量
        Long sumCount = publishedCount + unpublishedCount;

        if(sumCount > 0){
            map.put("avgRate",rangeNum/sumCount);
        }else{
            map.put("avgRate",0);
        }

        //一星评论数量
        Long rate1 = ProComment.me().setFkProductId(productId).setRangeNum(1).findCount();
        map.put("rate1_num",rate1);

        //二星评论数量
        Long rate2 = ProComment.me().setFkProductId(productId).setRangeNum(2).findCount();
        map.put("rate2_num",rate2);

        //三星评论数量
        Long rate3 = ProComment.me().setFkProductId(productId).setRangeNum(3).findCount();
        map.put("rate3_num",rate3);

        //四星评论数量
        Long rate4 = ProComment.me().setFkProductId(productId).setRangeNum(4).findCount();
        map.put("rate4_num",rate4);

        //五星评论数量
        Long rate5 = ProComment.me().setFkProductId(productId).setRangeNum(5).findCount();
        map.put("rate5_num",rate5);

        // 计算星占的百分率
        if(sumCount > 0){
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
        }else{
            map.put("rate1_percentage",0);
            map.put("rate2_percentage",0);
            map.put("rate3_percentage",0);
            map.put("rate4_percentage",0);
            map.put("rate5_percentage",0);
        }

        ProProductMaster product = ProProductMaster.me().setId(productId).get();
        map.put("product",product);

        return map;
    }


    /**
     * 评论导出数据
     * @param commentCommond
     * @return
     */
    public List<ProComment> findExportProComment(ProCommentCommond commentCommond) {
        commentCommond.setPage(Status.NO);
        commentCommond.setOrderBy("operation_time desc");
        return ProComment.me().find(commentCommond);
    }


    /**
     *  批量导入评论
     * @param list
     * @return
     */
    public Result doImportProComment(List<ProComment> list) {
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(Subject.id()).get();
        list.forEach(proComment -> {
            proComment.setIsAuditPass(1);
            proComment.setAddTime(Utils.currentTimeSecond());
            proComment.setAuditTime(Utils.currentTimeSecond());
            proComment.setAuditer(curEmployee.getId());
            proComment.setOperationUserName(curEmployee.getEmployeeName());
            proComment.setOperationUserId(curEmployee.getId());
            proComment.setIsDisplay(Status.YES);
            proComment.insertOrUpdate();
            // 如果商品是上架状态，需要发布一下，首页要看评论数量
            ProProductMaster product = ProProductMaster.me().setId(proComment.getFkProductId()).get();
            if (product.getIsShelve() == 1) {
                EventHolder.publishEvent(new Message(MessageSource.PRODUCT_SELL, proComment.getFkProductId()));
            }
        });
        return Result.success();
    }

}
