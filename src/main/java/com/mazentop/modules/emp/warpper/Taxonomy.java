package com.mazentop.modules.emp.warpper;

import com.mazentop.entity.CmsTaxonomy;
import com.mazentop.model.Status;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * cms 分类
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/6/9 01:26
 */
@Data
public class Taxonomy implements Serializable {

    private static final long serialVersionUID = -1647579995590299131L;

    private String id;

    private String pid;

    private String title;

    private Integer sort;

    private String slug;

    private boolean child;

    private String module;

    private String icon;

    private List<Taxonomy> childs;

    private boolean enable;

    public void saveTaxonomy() {
        CmsTaxonomy taxonomy = new CmsTaxonomy();
        BeanUtils.copyProperties(this, taxonomy, "enable");
        taxonomy.setIsEnable(enable ? Status.YES : Status.NO);
        if(Utils.isBlank(taxonomy.getPid())) {
            taxonomy.setPid(Status.TREE_ROOT_NODE);
        }
        taxonomy.insertOrUpdate();
        if(child) {
            Helper.forEach(childs, (index, child) -> {
                child.setModule(module);
                child.setPid(taxonomy.getId());
                child.setSort(index);
                child.saveTaxonomy();
            });
        }
    }

    public Taxonomy warpper(CmsTaxonomy taxonomy) {
        BeanUtils.copyProperties(taxonomy, this, "enable");
        setEnable(Status.YES.equals(taxonomy.getIsEnable()));
        setChilds(Collections.emptyList());
        if(Status.TREE_ROOT_NODE.equals(taxonomy.getPid())) {
             List<CmsTaxonomy> cmsTaxonomies =
                     new CmsTaxonomy().setPid(taxonomy.getId()).setOrderByFields(Order.asc(CmsTaxonomy.F_SORT)).find();
             List<Taxonomy> childs = new ArrayList<>(cmsTaxonomies.size());
             for(CmsTaxonomy cmsTaxonomy : cmsTaxonomies) {
                 childs.add(new Taxonomy().warpper(cmsTaxonomy));
             }
             setChild(!childs.isEmpty());
             setChilds(childs);
        }
        return this;
    }

}
