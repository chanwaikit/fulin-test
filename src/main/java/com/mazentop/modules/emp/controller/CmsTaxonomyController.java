package com.mazentop.modules.emp.controller;

import com.mazentop.entity.CmsTaxonomy;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.warpper.Taxonomy;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * cms 文章分类
 *
 * @author dengy
 * @version 1.0
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/cms")
@Api(value = "/option/v1", tags = "文章分类", description = "CmsTaxonomyController", produces = MediaType.APPLICATION_JSON_VALUE)
public class CmsTaxonomyController {

    @GetMapping(value = "/{module}/taxonomy/tree")
    @ApiOperation(value="查询当前模块下分类树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String")
    })
    public Result tree(@PathVariable String module) {
        List<CmsTaxonomy> taxonomies =
                new CmsTaxonomy()
                        .setModule(module)
                        .setPid(Status.TREE_ROOT_NODE)
                        .setOrderByFields(Order.asc(CmsTaxonomy.F_SORT))
                        .find();
        List<Taxonomy> list = new ArrayList<>(taxonomies.size());
        for(CmsTaxonomy taxonomy : taxonomies) {
            list.add(new Taxonomy().warpper(taxonomy));
        }
        return Result.build(()->{
            return list;
        });
    }

    @PostMapping("/{module}/taxonomy/saveOrUpdate")
    @ApiOperation(value="保存当前模块下分类树")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String"),
            @ApiImplicitParam(name = "taxonomyForm", value = "分类表单", required = true, dataType = "TaxonomyForm")
    })
    public Result saveOrUpdate(@PathVariable String module, @RequestBody TaxonomyForm taxonomyForm) {
        Db.tx(() -> {
            for(String id : taxonomyForm.ids) {
                new CmsTaxonomy().setId(id).delete();
            }
            Helper.forEach(taxonomyForm.taxonomys, (index, taxonomy) -> {
                taxonomy.setModule(module);
                taxonomy.setSort(index);
                taxonomy.saveTaxonomy();
            });
            return true;
        });
        return Result.success();
    }

    @Data
    public static class TaxonomyForm implements Serializable {
        private static final long serialVersionUID = -3482587710241592541L;
        private List<Taxonomy> taxonomys;
        private List<String> ids;
    }

}
