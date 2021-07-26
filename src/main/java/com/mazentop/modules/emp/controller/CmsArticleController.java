package com.mazentop.modules.emp.controller;

import com.mazentop.entity.CmsArticle;
import com.mazentop.entity.CmsArticleLink;

import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.modules.emp.commond.CmsArticleCommond;
import com.mazentop.modules.emp.domain.CmsArticleDomain;
import com.mazentop.modules.emp.service.CmsArticleService;
import com.mazentop.modules.emp.service.ProSeoService;
import com.mazentop.modules.emp.warpper.Article;
import com.mazentop.util.Helper;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * cms 文章
 *
 * @author dengy
 * @version 1.0
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/cms")
@Api(value = "/option/v1", tags = "文章分类", description = "CmsArticleController", produces = MediaType.APPLICATION_JSON_VALUE)
public class CmsArticleController {

    @Autowired
    private CmsArticleService cmsArticleService;

    @Autowired
    private ProSeoService proSeoService;

    @GetMapping("/{module}")
    @ApiOperation(value = "查询当前模块文章列表(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String"),
            @ApiImplicitParam(name = "commond", value = "文章查询条件", required = true, dataType = "CmsArticleCommond")
    })
    public Result articlea(@PathVariable String module, CmsArticleCommond commond) {
        return Result.build(() -> {
            // 获取文章数据
            commond.setModule(module);
            commond.setOrderBy("sort");

            return new Page<>(new CmsArticleDomain().includeSelectFields(CmsArticleDomain.F_ID,CmsArticleDomain.F_TITLE,CmsArticleDomain.F_MODULE,CmsArticleDomain.F_STATUS,CmsArticleDomain.F_ADD_TIME).find(commond), commond);
        });
    }

    @GetMapping("/status/{module}")
    @ApiOperation(value = "查询当前模块文章列表(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String")
    })
    public Result articleaStatus(@PathVariable String module) {
        return Result.build(() -> {
            HashMap<String, Object> map = new HashMap<>();
            // 获取当前模块下文章列表
            map.put("allCount", new CmsArticle().setModule(module).findCount());
            map.put("normalCount", new CmsArticle().setStatus(Article.STATUS_NORMAL).setModule(module).findCount());
            map.put("draftCount", new CmsArticle().setStatus(Article.STATUS_DRAFT).setModule(module).findCount());
            map.put("trashCount", new CmsArticle().setStatus(Article.STATUS_TRASH).setModule(module).findCount());

            return map;
        });
    }

    @Log("查询单个文章")
    @GetMapping("/{module}/data/{id}")
    @ApiOperation(value = "查询单个文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String"),
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "String")
    })
    public Result data(@PathVariable String module, @PathVariable String id) {

        Article article = new Article().warpper(CmsArticle.me().setModule(module).setId(id).get());

        return Result.build(() -> article);
    }

    @Log("删除文章")
    @DeleteMapping("/{module}/delete")
    @ApiOperation(value = "删除文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "文章主键集合", required = true, dataType = "List<String>")
    })
    public Result delete(@PathVariable String module, String id) {
        Db.tx(() -> {
            // 解除分类绑定分类
            CmsArticle.me().setModule(module).setId(id).delete();
            CmsArticleLink.me().setFkArticleId(id).delete();
            return true;
        });
        return Result.success();
    }

    @PostMapping("/{module}/saveOrUpdate")
    @ApiOperation(value = "编辑文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String"),
            @ApiImplicitParam(name = "article", value = "文章内容", required = true, dataType = "Article")
    })
    public Result saveOrUpdate(@PathVariable String module, @RequestBody Article article) {
        article.setModule(module);
        Db.tx(() -> {
            CmsArticle cmsArticle = article.saveArticle();
            if (Helper.isNotEmpty(article.getSeo())) {
                article.getSeo().setSource(cmsArticle.getId());
                proSeoService.editProModuleSeo(article.getSeo(), ProSeoTypeEnum.getProSeoTypeEnumByType(module));
            }
            return true;
        });
        return Result.success();
    }

    @GetMapping("/{module}/sortable")
    @ApiOperation(value = "获取排序信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String")
    })
    public Result sortable(@PathVariable String module) {
        Map<String, List<Map<String, Object>>> sortableList = cmsArticleService.sortable(module);
        return Result.build(() -> sortableList);
    }

    @PostMapping("/{module}/sortable")
    @ApiOperation(value = "保存排序信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "文章主键集合", required = true, dataType = "List<String>")
    })
    public Result sortable(@PathVariable String module, @RequestBody List<String> ids) {
        Db.tx(() -> {
            for (int i = 0; i < ids.size(); i++) {
                CmsArticle.me().setId(ids.get(i)).setModule(module).setSort(i).update();
            }
            return true;
        });
        return Result.success();
    }
}
