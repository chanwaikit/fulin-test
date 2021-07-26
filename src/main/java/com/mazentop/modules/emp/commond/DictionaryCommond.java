package com.mazentop.modules.emp.commond;

import com.mazentop.entity.SysDictionary;
import com.mztframework.dao.annotation.Criteria;
import com.mztframework.dao.annotation.Expression;
import com.mztframework.dao.page.BaseCommond;
import lombok.Data;

import java.util.List;

@Data
public class DictionaryCommond extends BaseCommond {

    @Criteria(expression = Expression.IN,property = SysDictionary.F_ID, alias = SysDictionary.TABLE_NAME)
    private List<String> ids;
}
