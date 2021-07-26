package com.mazentop.modules.emp.service;

import com.mazentop.entity.ProProductMaster;
import com.mazentop.entity.ProProductType;
import com.mazentop.model.TreeEnum;
import com.mazentop.modules.emp.commond.ProProductTypeCoommond;
import com.mazentop.modules.emp.dto.ProProductTypeDto;
import com.mazentop.modules.emp.dto.ProProductTypeTreeDto;
import com.mazentop.modules.emp.dto.ProductTypeDto;
import com.mazentop.modules.emp.dto.TreeDto;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.dto.FolderTreeDto;
import com.mztframework.jwt.security.Subject;
import com.mztframework.model.Status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: wangzy
 * @date: 2020/3/13
 * @description:
 */
@Service
public class ProProductTypeService {

    @Autowired
    ProSeoService proSeoService;


    /**
     * 新增或编辑商品类型
     * @param proProductType
     * @return
     */
    public R doProProductTypeAddOrUpdate(ProductTypeDto proProductType) {
        String curUserId = Subject.id();
        if (StringUtils.isBlank(curUserId)) {
            R.error("当前用户信息为空");
        }
        if (StringUtils.isBlank(proProductType.getId())) {
            proProductType.setParentProductTypeName(ProProductType.me().setId(proProductType.getParentProductTypeId()).get().getProductTypeName())
                    .setAddTime(Utils.currentTimeSecond())
                    .setAddUserId(curUserId)
                    .insert();
        } else {
            if (proProductType.getIsRootType().equals(1)) {
                ProProductType rootType = ProProductType.me().setProductTypeName("root").get();
                proProductType.setParentProductTypeId(rootType.getId()).setParentProductTypeName(rootType.getProductTypeName());
            }
            proProductType.update();
        }
        // seo
        if (Helper.isNotEmpty(proProductType.getSeo())) {
            proProductType.getSeo().setSource(proProductType.getId());
            proSeoService.editCategorySeo(proProductType.getSeo());
        }
        return R.ok();
    }

    /**
     * 获取商品分类详情
     * @param id - 商品分类id
     * @return
     */
    public ProProductType getProProductType(String id) {
        ProProductType proProductType = ProProductType.me().setId(id).get();
        if(Objects.nonNull(proProductType)){
            proProductType.addExten("parentIds", ThemeUtil.getParentTypeIds(id));
            return proProductType;
        }
        return null;
    }



    public List<ProProductType> findProProductTypeRootList(ProProductTypeCoommond proProductTypeCoommond) {
        return ProProductType.me().find(proProductTypeCoommond);
    }


    /**
     * 商品分类，树形返回
     * @return
     */
    public List<TreeDto> findProProductTypeTreeList(String treeType) {

        final String root = TreeEnum.valueOf(treeType).getId();

        List<ProProductType> productTypes = ProProductType.me().find();
        ProProductType productType = ProProductType.me().setProductTypeName(root).get();


        if (Objects.isNull(productType)) {
            addRoot(root);
            productType = ProProductType.me().setProductTypeName(root).get();
        }

        // 排序
        sort(productTypes);

        TreeDto treeDto = new TreeDto();
        treeDto.setId(productType.getId());
        treeDto.setName(productType.getProductTypeName());
        treeDto.setPid(productType.getParentProductTypeId());

        // 转换对象
        List<TreeDto> treeDtos = new ArrayList<>();
        for (ProProductType type : productTypes) {
            treeDtos.add(new TreeDto(type.getId(), type.getParentProductTypeId(), type.getProductTypeName()));
        }

        // FIXME 迭代需要优化
        transferTree(treeDto, treeDtos);

        return Collections.singletonList(new TreeDto(TreeEnum.valueOf(treeType).getId(), "", TreeEnum.valueOf(treeType).getName(), treeDto.getChildren()));

    }

    // 递归查询
    public TreeDto transferTree(TreeDto parent, List<TreeDto> childrens) {
        for (TreeDto treeDto : childrens) {
            if (parent.getId().equals(treeDto.getPid())) {
                treeDto = transferTree(treeDto, childrens);
                if (Objects.isNull(parent.getChildren())) {
                    List<TreeDto> children = new ArrayList<>();
                    children.add(treeDto);
                    parent.setChildren(children);
                } else {
                    parent.getChildren().add(treeDto);
                }
            }
        }
        return parent;
    }

    /**
     * 树形结构结果集排序，升序，子类可重写本方法自定义排序规则
     *
     * @param treeDtos
     * @return
     */
    public List<ProProductType> sort(List<ProProductType> treeDtos) {
        //通过集合提供的方法实现
        treeDtos.sort(Comparator.comparingInt(ProProductType::getSort));
        return treeDtos;
    }

    ;

    /**
     * 添加当前根节点记录
     */
    public void addRoot(String curRoot) {
        ProProductType.me()
                .setParentProductTypeId("")
                .setProductTypeName(curRoot)
                .setProductTypeProImage("")
                .setIsRootType(Status.YES)
                .setIsShowIndex(Status.NO)
                .setIsShowProductMenu(Status.NO)
                .setDescription("分类根目录")
                .setContent("分类根目录")
                .setSort(0)
                .setRemark("分类根目录")
                .insert();
    }

    public Page<ProProductType> findProProductTypeList(ProProductTypeCoommond proProductTypeCoommond) {
        List<ProProductType> proProductTypes = ProProductType.me().find(proProductTypeCoommond).stream().filter(type -> !type.getProductTypeName().equals("root")).collect(Collectors.toList());
        Page<ProProductType> proProductTypePage = new Page<>(proProductTypes, proProductTypeCoommond);
        if (proProductTypePage.getTotalRow() > 0) {
            proProductTypePage.setTotalRow(proProductTypePage.getTotalRow() - 1);
        }
        return proProductTypePage;
    }


    /**
     * 删除商品类型
     * @param ids
     * @return
     */
    public Result doDeleteProductType(List<String> ids) {

        for (String id : ids) {
            List<ProProductType> productTypes = ProProductType.me().setParentProductTypeId(id).find();
            if (productTypes.size() > 0) {
                for (ProProductType productType : productTypes) {
                    if (ProProductMaster.me().setFkProductTypeId(productType.getId()).findCount() > 0) {
                        return Result.toast("当前商品分类或子分类存在商品，无法删除");
                    }
                }
            } else if (ProProductMaster.me().setFkProductTypeId(id).findCount() > 0) {
                return Result.toast("当前商品分类存在商品，无法删除");
            }
        }

        Db.tx(() -> {
            for (String id : ids) {
                deleteTree(id);
            }
            return true;
        });
        return Result.build(() -> "删除成功");
    }


    // 递归删除
    public void deleteTree(String id) {
        List<ProProductType> productTypes = ProProductType.me().setParentProductTypeId(id).find();
        // 有子集，递归删除
        if (productTypes.size() > 0) {
            ProProductType.me().setId(id).delete();
            for (ProProductType productType : productTypes) {
                deleteTree(productType.getId());
            }
        } else {
            if (ProProductType.me().setId(id).findCount() > 0) {
                ProProductType.me().setId(id).delete();
            }
        }

    }

    public List<ProProductTypeTreeDto> lazyProTreeList(String pid) {
        List<ProProductType> typeList = new ArrayList<>();
        if ("rootTree".equals(pid)) {
            typeList = ProProductType.me().setId("root").find();
        } else {
            typeList = ProProductType.me().setParentProductTypeId(pid).find();
        }
        List<ProProductTypeTreeDto> proTreeList = new ArrayList<>();
        if (!typeList.isEmpty()) {
            typeList.forEach(type -> {
                ProProductTypeTreeDto typeTreeDto = new ProProductTypeTreeDto();
                BeanUtils.copyProperties(type, typeTreeDto);
                List<ProProductType> children = ProProductType.me().setParentProductTypeId(type.getId()).find();
                if (children.size() == 0) {
                    typeTreeDto.setIsLeaf(true);
                } else {
                    typeTreeDto.setChildren(children);
                    typeTreeDto.setIsLeaf(false);
                }
                proTreeList.add(typeTreeDto);
            });
        }
        return proTreeList;
    }

}
