package com.mazentop.modules.web.dto;

import com.mazentop.entity.ProComment;
import lombok.Data;

import java.util.List;

/**
 * 评论
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/4 16:08
 */
@Data
public class ProCommentDto  extends ProComment {

    private List<String> slideShows;
}
