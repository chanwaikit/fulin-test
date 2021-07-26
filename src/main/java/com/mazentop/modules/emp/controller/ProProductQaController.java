package com.mazentop.modules.emp.controller;

import com.mazentop.entity.ProProductQa;
import com.mazentop.modules.emp.commond.ProProductQaCommond;
import com.mazentop.modules.emp.service.ProProductQaService;
import com.mztframework.annotation.ApiVersion;
import com.mztframework.data.Result;
import com.mztframework.logging.annotation.Log;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@ApiVersion
@RestController
@RequestMapping("/option/{api_version}/product/qa")
@Api(value = "/option/v1", tags = "商品: 问答管理",  produces = MediaType.APPLICATION_JSON_VALUE)
public class ProProductQaController {

    @Autowired
    ProProductQaService qaService;


    /**
     * 问题回答
     * @return
     */
    @Log("问题回答")
    @ApiOperation("问题回答")
    @PostMapping(value = "/saveAnswer")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ProProductQa", value = "保存参数", required = true, dataType = "ProProductQa")
    })
    public Result saveAnswer(@RequestBody ProProductQa dto) {
        return Result.build(() -> qaService.saveAnswer(dto));
    }


    /**
     * 分页
     * @return
     */
    @Log("商品问答管理列表")
    @GetMapping(value = "/getPage")
    @ApiOperation(value = "商品问答列表", notes = "入参:查询条件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ProProductQaCommond", value = "查询条件", required = true, dataType = "ProProductQaCommond")
    })
    public Result getPage(ProProductQaCommond commond) {
        return Result.build(() -> qaService.getPage(commond));
    }

    /**
     * 商品提问详情
     * @return
     */
    @Log("查询单个商品问答")
    @GetMapping(value = "/get/{id}")
    @ApiOperation(value = "商品问答详情")
    public Result get(@PathVariable("id") String id) {
        return Result.build(() ->  qaService.get(id));
    }


    /**
     * 删除问答
     * @return
     */
    @Log("删除商品问答")
    @PostMapping(value = "/delete")
    @ApiOperation(value = "删除商品问答", notes = "入参:问答主键")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "查询条件", required = true, dataType = "List<String>")
    })
    public Result delete(@RequestBody List<String> ids) {
        return Result.build(() -> qaService.doDelete(ids));
    }

    /**
     * 修改是否可見
     * @return
     */
    @Log("修改商品问答是否可見")
    @PostMapping(value = "/updateQa")
    @ApiOperation(value = "修改商品问答是否可見")
    public Result updateQa(@RequestBody ProProductQa productQa) {
        return Result.build(() -> qaService.updateQa(productQa));
    }
}
