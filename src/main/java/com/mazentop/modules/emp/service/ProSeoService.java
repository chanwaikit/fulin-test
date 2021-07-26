package com.mazentop.modules.emp.service;

import cn.hutool.core.util.StrUtil;
import com.mazentop.entity.ProSeo;
import com.mazentop.model.ProSeoTypeEnum;
import com.mztframework.data.R;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ProSeoService {

    private static String regex = "[\n`~!@#$%^&*()+=|{}%':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。， 、？]";

    public ProSeo getProSeo(String source) {
        return ProSeo.me().setSource(source).get();
    }

    /**
     * 新增或编辑商品seo
     * @param seo
     * @return
     */
    public R editProProductSeo(ProSeo seo) {
        optionSeo(seo);
        return editProModuleSeo(seo, ProSeoTypeEnum.TYPE_PRODUCT);
    }




    public R editProModuleSeo(ProSeo seo, ProSeoTypeEnum seoTypeEnum) {
        if(Objects.isNull(seo)) {
            return R.ok();

        }
        ProSeo proSeo = ProSeo.me().setType(seoTypeEnum.type()).setSeoAddress(seo.getSeoAddress()).get();
        if (proSeo != null && !seo.getSource().equals(proSeo.getSource())) {
            String address = String.format(seo.getSeoAddress() + "%s", RandomStringUtils.randomAlphanumeric(5));
            seo.setSeoAddress(address);
        }else if (proSeo != null) {
            seo.setId(proSeo.getId());
        }
        ProSeo sourc = ProSeo.me().setSource(seo.getSource()).get();
        if (sourc!=null){
            seo.setId(sourc.getId());
        }
        seo.setSeoAddress(seo.getSeoAddress().replaceAll(regex," "));
        optionSeo(seo);
        seo.setType(ProSeoTypeEnum.getProSeoTypeEnumByType(seoTypeEnum.type()).type());
        seo.insertOrUpdate();
        return R.ok();
    }



    public R editCollectionsSeo(ProSeo seo) {
        return editProModuleSeo(seo, ProSeoTypeEnum.TYPE_SKIN);
    }


    /**
     * 新增或编辑商品分类seo
     * @param seo
     * @return
     */
    public R editCategorySeo(ProSeo seo) {
        return editProModuleSeo(seo, ProSeoTypeEnum.TYPE_CATEGORY);
    }


    public R editPagesSeo(ProSeo seo) {
        return editProModuleSeo(seo, ProSeoTypeEnum.TYPE_PAGE);
    }


    public R delPagesSeo(String source) {
        ProSeo.me().setSource(source).delete();
        return R.ok();
    }

    public void optionSeo(ProSeo seo){
        if(!Objects.isNull(seo)) {
            StrUtil.containsBlank(seo.getSeoAddress());
            seo.setSeoAddress(seo.getSeoAddress()
                    .replace(" ","-")
                    .replace("\"", "")
                    .replace("'", "")
                    .replace("&", "")
                    .replace("?", ""));
        }
    }

}
