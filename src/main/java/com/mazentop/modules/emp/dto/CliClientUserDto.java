package com.mazentop.modules.emp.dto;

import com.mazentop.entity.CliClienteleInfo;
import lombok.Data;

@Data
public class CliClientUserDto extends CliClienteleInfo {

    private String code;

    private String oldPassword;

    /**
     * 推荐人邀请码
     */
    private String sourceUserId;

    private String confirmPassword;

}
