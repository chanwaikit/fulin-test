package com.mazentop.modules.evaluation.commond;

import com.mztframework.commond.PageCommond;
import lombok.Data;

/**
 * @author: SRC
 * @create: 2021-04-29 10:40
 **/
@Data
public class InvitedCommond extends PageCommond {
    private String userId;

    private String email;

    private String phone;
}
