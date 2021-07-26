package com.mazentop.modules.emp.controller;

import com.mazentop.entity.SysDictionary;
import com.mazentop.modules.emp.commond.SysDictionaryCommond;
import com.mazentop.modules.emp.service.SysDictionaryService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.dao.jdbc.Db;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * dictionary 字典列表
 *
 * @author dengy
 * @version 1.0
 */
@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/dictionary")
@Api(value = "/option/v1", tags = "字典列表", description = "SysDictionaryController", produces = MediaType.APPLICATION_JSON_VALUE)
public class SysDictionaryController {

    @Autowired
    private SysDictionaryService sysDictionaryService;


    @GetMapping("/{module}")
    public Result findSysDictionaryList(@PathVariable String module, SysDictionaryCommond commond) {
        return Result.build(() -> {
            commond.setModel(module);
            commond.setOrderBy(SysDictionary.F_SORTING);
            List<SysDictionary> dictionaries = SysDictionary.me().find(commond);
            SysDictionary sysDictionary = SysDictionary.me().setCode(module).setPcode("root").get();
            dictionaries.forEach(dct->{
                dct.addExten("type",sysDictionary.getType());
            });
            return new Page<>(dictionaries , commond);
        });
    }
    @GetMapping("/{module}/data/{id}")
    public Result data(@PathVariable String module, @PathVariable String id) {

        SysDictionary sysDictionary = SysDictionary.me().setPcode(module).setId(id).get();

        return Result.build(() -> sysDictionary);
    }

    @DeleteMapping("/{module}/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "字典code", required = true, dataType = "String"),
            @ApiImplicitParam(name = "ids", value = "文章主键集合", required = true, dataType = "List<String>")
    })
    public Result delete(@PathVariable String module, String id) {
        Db.tx(() -> {
            // 解除分类绑定分类
            SysDictionary.me().setPcode(module).setId(id).delete();
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
        SysDictionary sysDictionary = SysDictionary.me().setCode(module).setPcode("root").get();
        List<SysDictionary> sortableList = SysDictionary.me().setPcode(module).setOrderByFields(SysDictionary.F_SORTING).find();
        sortableList.forEach(dct->{
            dct.addExten("type",sysDictionary.getType());
        });
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
                SysDictionary.me().setId(ids.get(i)).setPcode(module).setSorting(i).update();
            }
            return true;
        });
        return Result.success();
    }


    @PostMapping("/{module}/saveOrUpdate")
    @ApiOperation(value = "编辑字典")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "module", value = "模块", required = true, dataType = "String"),
            @ApiImplicitParam(name = "article", value = "文章内容", required = true, dataType = "Article")
    })
    public Result saveOrUpdate(@PathVariable String module, @RequestBody SysDictionary sysDictionary) {
        sysDictionary.setPcode(module);
        Db.tx(() -> {
            if(StringUtils.isBlank(sysDictionary.getId())){
                Long count = SysDictionary.me().setPcode(module).findCount();
                int sort = 0;
                if(count>0){
                    sort = (int) (count+1);
                }
                sysDictionary.setSorting(sort);
            }
            sysDictionary.insertOrUpdate();
            return true;
        });
        return Result.success();
    }



    @GetMapping("/{module}/getRootDictionary")
    public Result getRootDictionary(@PathVariable String module) {

        SysDictionary sysDictionary = SysDictionary.me().setCode(module).setPcode("root").get();

        return Result.build(() -> sysDictionary);
    }



    @GetMapping("/{module}/getDictionaryList")
    public Result getDictionaryList(@PathVariable String module) {
        Map<String,Object> map = new HashMap<>();
        SysDictionary dictionary = SysDictionary.me().setCode(module).setPcode("root").get();
        List<SysDictionary> sysDictionary = SysDictionary.me().setPcode(module).setIsEnable(1).setOrderByFields(SysDictionary.F_SORTING).find();
        map.put("sysDictionaryList",sysDictionary);
        map.put("type",dictionary.getType());
        return Result.build(() -> map);
    }
}
