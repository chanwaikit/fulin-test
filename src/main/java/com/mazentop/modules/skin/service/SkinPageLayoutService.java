package com.mazentop.modules.skin.service;

import com.mazentop.entity.SkinPage;
import com.mazentop.entity.SkinPageLayout;
import com.mazentop.entity.SysTemplate;
import com.mazentop.modules.skin.commond.SkinPageCommond;
import com.mazentop.modules.skin.commond.SkinPageLayoutCommond;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SkinPageLayoutService {

    @Autowired
    BaseDao baseDao;

    public Page findSkinPageLayoutList(SkinPageLayoutCommond commond){
        List<SkinPageLayout> skinPageLayouts = SkinPageLayout.me().find(commond);
        skinPageLayouts.forEach(layout -> {
            SysTemplate sysTemplate = getTemplateName(layout.getTemplateId());
            if(!Objects.isNull(sysTemplate)){
                layout.addExten("templateName", sysTemplate.getTemplateName());
            }else{
                layout.addExten("templateName", "");
            }
        });
        return new Page(skinPageLayouts,commond);
    }
    public R doDeletePageLayoutInfo(List<String> ids) {
        Db.tx(() -> {
            for (String id : ids) {
                SkinPageLayout.me().setId(id).delete();
            }
            return true;
        });
        return  R.ok();
    }
    public SysTemplate getTemplateName(String templateId){
        SysTemplate sysTemplate = SysTemplate.me().setId(templateId).get();
        return sysTemplate;
    }
}
