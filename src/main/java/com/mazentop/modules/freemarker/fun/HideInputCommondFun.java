package com.mazentop.modules.freemarker.fun;

import com.mztframework.commons.ClassUtil;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * 请求参数自动生成隐藏域
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/6/27 12:08
 */
@Component
public class HideInputCommondFun extends Function  {
    @Override
    public Object exec() {
        BaseCommond commond = (BaseCommond) get(0);
        if(Objects.isNull(commond)) {
            return "";
        }
        Class<?> localClass = commond.getClass();

        String hideInputTemplate = "<input name=\"%s\" value=\"%s\" type=\"hidden\" />";
        // 获取类 属性 包含父类属性
        List<Field> fieldList = ClassUtil.getSuperClassDeclaredField(localClass, BaseCommond.class);
        StringBuilder html = new StringBuilder();
        // 配置固定隐藏域属性，分页页码,排序字段
        html.append(String.format(hideInputTemplate, "p", commond.getP()));
        if(!Objects.isNull(commond.getO())) {
            html.append(String.format(hideInputTemplate, "o", commond.getO()));
        }
        for(Field field : fieldList) {
            Criteria criteria = field.getAnnotation(Criteria.class);
            if(!Objects.isNull(criteria)) {
                String fieldName = field.getName();
                Object fieldValue = ClassUtil.getFieldValueObj(commond, field);
                if(Utils.isEmpty(fieldValue)) {
                    continue;
                }
                // 时间选择器
                if(Expression.BETWEEN == criteria.expression()) {
                    if(fieldValue instanceof SearchTime) {
                        SearchTime searchTime = (SearchTime) fieldValue;
                        if(!Utils.isEmpty(searchTime.getStart())) {
                            html.append(String.format(hideInputTemplate, fieldName + ".start", searchTime.getStart()));
                        }
                        if(!Utils.isEmpty(searchTime.getEnd())) {
                            html.append(String.format(hideInputTemplate, fieldName + ".end", searchTime.getEnd()));
                        }
                    }
                    continue;
                }
                html.append(String.format(hideInputTemplate, fieldName, fieldValue));
            }
        }
        return html.toString();
    }

    @Override
    public String name() {
        return "hideInputCommond";
    }
}
