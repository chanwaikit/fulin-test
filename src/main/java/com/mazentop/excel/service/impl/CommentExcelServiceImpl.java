package com.mazentop.excel.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.alibaba.fastjson.JSONArray;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.ProComment;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProProductStock;
import com.mazentop.excel.entity.ProCommentEntity;
import com.mazentop.excel.entity.StockEntity;
import com.mazentop.excel.service.ExcelService;
import com.mazentop.excel.service.SendEmail;
import com.mazentop.excel.util.RequestUtil;
import com.mazentop.modules.emp.commond.ProCommentCommond;
import com.mazentop.modules.emp.dto.query.ProCommentQueryDto;
import com.mazentop.modules.emp.service.ProCommentService;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.plugins.cache.LFUCache;
import com.mazentop.util.Helper;
import com.mztframework.data.Result;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Pattern;

@Service("comment-excel")
public class CommentExcelServiceImpl implements ExcelService {

    @Autowired
    SendEmail sendEmail;

    @Autowired
    ProCommentService proCommentService;

    @Autowired
    ProProductMasterService productService;


    /**
     * 评论导入
     * @param request
     * @param file
     * @return
     * @throws Exception
     */
    @Override
    public Result importExcel(HttpServletRequest request, MultipartFile file) throws Exception {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        List<ProCommentEntity> list = ExcelImportUtil.importExcel(file.getInputStream(), ProCommentEntity.class, importParams);
        if (null == list || list.size() == 0) {
            return Result.toast("文件格式有误！");
        } else {
            if (list.size() == 0) {
                return Result.toast("文件格式有误！");
            }
        }
        List<ProComment> commentList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(2);
        cache(list, commentList,map);
        // 导入数据
        proCommentService.doImportProComment(commentList);

        return Result.build(() -> map);
    }


    private void cache(List<ProCommentEntity> list,List<ProComment> commentList,Map<String, Object> map){
        List<ProCommentEntity> errorList = new ArrayList<ProCommentEntity>();
        String error = null;
        int errorNum = 0;
        for (ProCommentEntity entity : list) {
            ProComment proComment = new ProComment();
//            if (Objects.isNull(entity.getFkClienteleId())){
//                error = " (用户编号)列不能为空";
//                errorNum++;
//                setError(errorList, error, entity);
//                continue;
//            }else {
//                Long count = CliClienteleInfo.me().setId(entity.getFkClienteleId()).findCount();
//                if (count == 0){
//                    error = " (用户编号)列不存在";
//                    errorNum++;
//                    setError(errorList, error, entity);
//                    continue;
//                }
//                proComment.setFkClienteleId(entity.getFkClienteleId());
//            }
            if (Helper.isNotEmpty(entity.getId())){
                proComment.setId(entity.getId());
            }
            if (Objects.isNull(entity.getFkProductId())){
                error = " (商品编号)列不能为空";
                errorNum++;
                setError(errorList, error, entity);
                continue;
            }else {
                Long count = ProProductMaster.me().setId(entity.getFkProductId()).findCount();
                if (count == 0){
                    error = " (商品编号)列不存在";
                    errorNum++;
                    setError(errorList, error, entity);
                    continue;
                }
                proComment.setFkProductId(entity.getFkProductId());
            }
            if (Objects.isNull(entity.getContent())){
                error = " (评论内容)列不能为空";
                errorNum++;
                setError(errorList, error, entity);
                continue;
            }else {
                proComment.setContent(entity.getContent());
            }
            if (Objects.isNull(entity.getRangeNum())){
                error = " (评论分数)列不能为空";
                errorNum++;
                setError(errorList, error, entity);
                continue;
            }else {
                if (entity.getRangeNum() < 0 && entity.getRangeNum() > 5){
                    error = " (评论分数)列,评论分数只能是1到5";
                    errorNum++;
                    setError(errorList, error, entity);
                }
                proComment.setRangeNum(entity.getRangeNum());
            }
            if (Objects.isNull(entity.getAddUserName())){
                error = " (评论人)列不能为空";
                errorNum++;
                setError(errorList, error, entity);
                continue;
            }else {
                proComment.setAddUserName(entity.getAddUserName());
            }
            if (Helper.isNotEmpty(entity.getLikeNum())){
                if (Helper.isInteger(entity.getLikeNum())) {
                    proComment.setLikeNum(Helper.toIt(entity.getLikeNum(), Integer.class));
                } else {
                    errorNum++;
                    error = "(点赞数)列只能输入数字";
                    setError(errorList, error, entity);
                    continue;
                }
            }
            commentList.add(proComment);
        }
        if (errorList.size() > 0) {
            String key = RandomStringUtils.randomAlphanumeric(20);
            LFUCache.put(key, Helper.toJson(errorList));
            map.put("errorKey", key);
        }
        map.put("error", errorNum);
        map.put("success", commentList.size());
    }


    /**
     * 添加错误信息
     * @param errorList
     * @param error
     * @param dto
     */
    private void setError(List<ProCommentEntity> errorList, String error, ProCommentEntity dto) {
        dto.setError(error);
        errorList.add(dto);
    }



    /**
     * 评论导出
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = RequestUtil.getValue(request);
        if (Helper.isNotEmpty(map)) {
            String scopes = map.get("scope").toString();
            ProCommentCommond queryDto = new ProCommentCommond();
            if (scopes.equals("1")) {
                queryDto.setIds(Helper.toList(map.get("selections").toString(), String.class));
            } else if (scopes.equals("2") || scopes.equals("3")) {
                ProCommentCommond condition = JSONArray.parseObject(map.get("condition").toString(), ProCommentCommond.class);
                BeanUtils.copyProperties(condition, queryDto);
            }

            List<ProComment> exportCommentList = proCommentService.findExportProComment(queryDto);

            List<ProCommentEntity> entityList = new ArrayList<ProCommentEntity>();

            for (ProComment proComment : exportCommentList) {
                ProCommentEntity proCommentEntity = new ProCommentEntity();

                BeanUtils.copyProperties(proComment, proCommentEntity);

                ProProductMaster ProProductMaster = productService.getProductMaster(proComment.getFkProductId());
                if(Objects.nonNull(ProProductMaster)){
                    proCommentEntity.setProductNme(ProProductMaster.getProductName());
                }

                proCommentEntity.setAddTime(Helper.timestampToDate(proComment.getAddTime(),Helper.DATE_PATTERN1));
//                proCommentEntity.setAuditTime(Helper.timestampToDate(proComment.getAuditTime(),Helper.DATE_PATTERN1));
                entityList.add(proCommentEntity);
            }
            ByteArrayOutputStream baos;
            try (Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, "评论", ExcelType.XSSF), ProCommentEntity.class, entityList)) {
                baos = new ByteArrayOutputStream();
                workbook.write(baos);
                workbook.close();
            }

            String title = "评论导出信息 " + Helper.format(new Date(), Helper.DATE_PATTERN1);
            if(!Objects.isNull(map.get("mail"))) {
                sendEmail.sendEmail(title, map.get("mail").toString(), baos.toByteArray(), "评论信息" + Helper.format(new Date(), Helper.DATE_PATTERN3) + ".xlsx");
            }
            response.setContentType("application/vnd.ms-excel");
            response.getOutputStream().write(baos.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }

    }

}
