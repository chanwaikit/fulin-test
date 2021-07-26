package com.mazentop.plugins.theme;

import com.mazentop.entity.ProProductType;
import com.mazentop.entity.SysTemplate;
import com.mazentop.model.Status;
import com.mazentop.modules.skin.dto.LinkDto;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.interceptor.ThemeInterceptor;
import com.mazentop.plugins.filter.ThreadContent;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2020/4/27 10:55
 */
public class ThemeUtil {

    public static String templatePath(String templateName) {
        SysTemplate sysTemplate = getTemplate();
        if(!Objects.isNull(sysTemplate)) {
            return String.format("%s/templates/%s", sysTemplate.getTemplatePath(), templateName);
        }
        return String.format("/web/templates/%s", templateName);
    }

    public static String getTemplatPath() {
        SysTemplate sysTemplate = getTemplate();
        if(!Objects.isNull(sysTemplate)) {
            return String.format("%s/templates/", sysTemplate.getTemplatePath());
        }
        return "/web/templates/";
    }

    public static String getTemplatName() {
        SysTemplate sysTemplate = getTemplate();
        if(!Objects.isNull(sysTemplate)) {
            return sysTemplate.getTemplatePath();
        }
        return "/web";
    }

    public static SysTemplate getTemplate() {
        if(Objects.isNull(User.theme())) {
            SysTemplate sysTemplate = SysTemplate.me().setIsEnable(1).get();
            sysTemplate.setTemplatePath(ThreadContent.getData(ThemeInterceptor.THEME_CUR));
            return sysTemplate;
        }
        return User.theme();
    }

    public static String excelRegistrationName(String prefix) {
        return String.format("%s-excel", prefix);
    }

    public static String seoDetailsRegistrationName(String prefix) {
        return String.format("%s-details", prefix);
    }

    public static List<ProProductType> getTypeList(List<ProProductType> productTypes, ProProductType root, ProProductType productType){
        List<ProProductType> types = findCurTypePath(productTypes, root, productType);
        Collections.reverse(types);
        return types;
    }

    public static List<LinkDto> getTypePath(ProProductType productType){
        List<ProProductType> productTypes = new ArrayList<>();
        productTypes.add(productType);
        List<ProProductType> types = findCurTypePath(productTypes, ProProductType.me().setId(Status.TREE_EVALUATION_NODE), productType);
        Collections.reverse(types);
        List<LinkDto> linkDtos = new ArrayList<>(types.size());
        types.forEach(item -> {
            LinkDto linkDto = new LinkDto();
            linkDto.setId(item.getId());
            linkDto.setTitle(item.getProductTypeName());
            linkDto.setUrl(Seo.getSeoUrlForProductType(item.getId()));
            linkDtos.add(linkDto);
        });
        return linkDtos;
    }

    private static List<ProProductType> findCurTypePath(List<ProProductType> productTypes, ProProductType root, ProProductType productType) {
        if (productType==null||productType.getParentProductTypeId().equals(root.getId())) {
            return productTypes;
        } else {
            ProProductType proProductType = ProProductType.me().setId(productType.getParentProductTypeId()).get();
            if (proProductType!=null){
                productTypes.add(proProductType);
            }
            return findCurTypePath(productTypes, root, proProductType);
        }
    }


    public static String getTypeById(String id){
        List<ProProductType> list=new ArrayList<>();
        getParentType(id, list);

        List<String> typeNameList =
                list.stream().map(ProProductType::getProductTypeName).collect(Collectors.toList());
        Collections.reverse(typeNameList);
        return String.join("/", typeNameList);
    }


    public static List<String> getParentTypeIds(String typeId) {
        List<ProProductType> list = new ArrayList<>();
        getParentType(ProProductType.me().setId(typeId).get().getParentProductTypeId(), list);
        List<String> typeIds = list.stream().map(ProProductType::getId).collect(Collectors.toList());
        Collections.reverse(typeIds);
        return typeIds;
    }


    private static ProProductType getParentType(String typeId, List<ProProductType> list){
        if (Helper.isEmpty(typeId)){
            return null;
        }
        ProProductType proProductType = ProProductType.me().setId(typeId).get();
        if (Helper.isEmpty(proProductType)){
            return null;
        }
        if (proProductType.getProductTypeName().equals(Status.TREE_EVALUATION_NODE)) {
            return proProductType;
        }
        list.add(proProductType);
        proProductType = getParentType(proProductType.getParentProductTypeId(),list);
        return proProductType;
    }


}
