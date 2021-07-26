package com.mazentop.modules.web.controller;

import com.mztframework.FileProperties;
import com.mztframework.SimpleFile;
import com.mztframework.commons.DateUtil;
import com.mztframework.commons.Utils;
import com.mztframework.data.R;
import com.mztframework.file.FileBuilder;
import com.mztframework.file.controller.UploadController;
import com.mztframework.file.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 文件上传
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/4 15:10
 */
@Controller
@RequestMapping("/webFinder")
public class WebFinderController implements UploadController {

    @Autowired
    IUploadService uploadService;

    @Autowired
    FileProperties fileProperties;

    /**
     * 文件上传
     * @param request
     * @param middle
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ResponseBody
    public R upload(HttpServletRequest request, String middle, String folder) throws IOException {
        R result = valid(request);
        if(result.isSuccess()) {
            if(Utils.isBlank(middle)) {
                middle = DateUtil.getDay();
            }
            SimpleFile simpleFile = up(request,
                    FileBuilder.builder()
                            .middle(middle)
                            .folder(folder)
                            .owner("anonymous")
                           .build());
            return result.put("data", simpleFile);
        } else {
            return result;
        }
    }

    /**
     * 文件上传
     * @param request
     * @param middle
     * @return
     * @throws IOException
     */
    @PostMapping("/uploadNon")
    @ResponseBody
    public R uploadNon(HttpServletRequest request, String middle) throws IOException {
        R result = valid(request);
        if(result.isSuccess()) {
            if(Utils.isBlank(middle)) {
                middle = DateUtil.getDay();
            }
            SimpleFile simpleFile = upNonPersistent(request, middle);
            return result.put("data", simpleFile);
        } else {
            return result;
        }
    }

    @Override
    public IUploadService uploadService() {
        return uploadService;
    }

    @Override
    public int maxFileSize() {
        return fileProperties.getMaxFileSize();
    }
}
