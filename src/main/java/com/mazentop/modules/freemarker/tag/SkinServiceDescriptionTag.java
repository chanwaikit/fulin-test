package com.mazentop.modules.freemarker.tag;

import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.SysDictionary;
import com.mazentop.modules.emp.commond.DictionaryCommond;
import com.mazentop.util.Helper;
import com.mztframework.render.Tag;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 获取产品服务说明
 *
 * @author dengy
 * @version 1.0
 * @date 2020/7/9 18:04
 */
public class SkinServiceDescriptionTag extends Tag {

    private ProProductMaster productMaster;

    public SkinServiceDescriptionTag(ProProductMaster productMaster){
        this.productMaster = productMaster;
    }

    @Override
    public void execute() {
            if(Helper.isNotEmpty(productMaster)) {
                Map<String,Object> map = new HashMap<>();
                List<SysDictionary>dictionaryList =new ArrayList<>();
                if(StringUtils.isNoneBlank(productMaster.getExplainData())){
                    List<String> ids =  JSONObject.parseObject(productMaster.getExplainData(), List.class);
                    if(!ids.isEmpty()) {
                        DictionaryCommond dictionaryCommond = new DictionaryCommond();
                        dictionaryCommond.setIds(ids);
                        dictionaryList = SysDictionary.me().find(dictionaryCommond);
                    }

                }
                map.put("explain",dictionaryList);
                setVariable("description", map);
                renderBody();
            }
    }

    @Override
    public String name() {
        return "skin_service_description";
    }
}
