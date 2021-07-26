package com.mazentop.modules.emp.commond;

import com.mazentop.entity.ProComment;
import com.mazentop.modules.emp.service.ProCommentService;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chen quan
 * @date 2020/4/26 16:43
 **/
@RestController
@RequestMapping("/comment")
public class ProductCommentController {

    @Autowired
    private ProCommentService proCommentService;

    /**
     * 编辑评论信息
     * @param proComment
     * @return
     */
    @PostMapping("/editComment")
    public Result editCommentInformation(@RequestBody ProComment proComment){
        return Result.build(()-> proComment.update());
    }

}

