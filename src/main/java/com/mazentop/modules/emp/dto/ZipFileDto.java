package com.mazentop.modules.emp.dto;

import com.mztframework.commons.Utils;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.File;

/**
 * @Title: ZipFile
 * @Description: TODO
 * @Author liuq
 * @Date 2020/1/16 10:17
 */
@Data
public class ZipFileDto {
    private String name;

    private File file;

    public ZipFileDto() {
        super();
    }

    public ZipFileDto(@NotNull File file) {
        this(file, null);
    }

    public ZipFileDto(@NotNull File file, String name) {
        this.file = file;
        this.name = name;
        if( Utils.isBlank(name)) {
            this.name = file.getName();
        }
    }
}
