package com.mazentop.modules.web.controller.advice;

import com.mztframework.dao.jdbc.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

/**
 * dao 增强控制器
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/7/4 02:32
 */
@ControllerAdvice
public class DBControllerAdvice {

    @Autowired
    private BaseDao dao;

    @ModelAttribute("DB")
    public DBControllerAdvice userControllerAdvice() {
        return this;
    }

    public Map<String, Object> read(String sql, Object ...args) {
       return dao.queryForMap(sql, args);
    }

    public List<Map<String, Object>> list(String sql, Object ...args) {
        return dao.queryForList(sql, args);
    }
}
