package com.mazentop.modules.web.controller;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.TreeEnum;
import com.mazentop.modules.emp.commond.ProCommentCommond;
import com.mazentop.modules.emp.commond.ProProductQaCommond;
import com.mazentop.modules.emp.service.ProProductMasterService;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.annotation.Authorize;
import com.mazentop.modules.web.dto.ProCommentDto;
import com.mazentop.modules.web.service.ProductService;
import com.mztframework.commons.Utils;
import com.mztframework.dao.page.Page;
import com.mztframework.data.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {


    @Autowired
    ProductService productService;

    @Autowired
    ProProductMasterService proProductMasterService;

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    /**
     * 新增商品评论
     */

    @PostMapping("/comment")
    @ResponseBody
    public Result addProductCommentRecording(ProCommentDto proComment) {
        return ordShoppingCartService.productComment(proComment);
    }


    /**
     * 评论分页查询
     *
     * @return
     */
    @GetMapping("/comment")
    public String findCommentRecording(ProCommentCommond commond, ModelMap map) {
        commond.setPageSize(10);
        if(!Utils.isBlank(commond.getProductId())) {
            map.put("data", ordShoppingCartService.findCommentRecording(commond));
            map.put("productId", commond.getProductId());
        } else {
            map.put("data", new HashMap<>(0));
            map.put("productId", "productId");
        }
        map.put("commond", commond);
        return templatePath("product/lib/comment");
    }


    /**
     * 新增商品提问
     */
    @Authorize
    @PostMapping("/saveQuestion")
    @ResponseBody
    public Result addProductQuestion(ProProductQa dto) {
        return ordShoppingCartService.saveQuestion(dto);
    }


    /**
     * 问答分页查询
     *
     * @return
     */
    @GetMapping("/productQa")
    public String getProductQuestion(ProProductQaCommond commond, ModelMap map) {
        commond.setPageSize(10);
        if(!Utils.isBlank(commond.getProductId())) {
            map.put("data", ordShoppingCartService.getProductQuestion(commond));
            map.put("productId", commond.getProductId());
        } else {
            map.put("data", new HashMap<>(0));
            map.put("productId", "productId");
        }
        map.put("commond", commond);
        return templatePath("product/lib/faq");
    }

    /**
     * 问答分页查询
     *
     * @return
     */
    public Page<ProProductQa> getProductQuestion(ProProductQaCommond commond) {
        commond.setOrderBy("-" + ProProductQa.F_PROBLEM_TIME);
        commond.setIsDisplay(1);
        List<ProProductQa> list = ProProductQa.me().find(commond);
        list.forEach(p -> {
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(p.getFkClienteleId()).get();
            p.addExten("userName", cliClienteleInfo.getClientSurname() + cliClienteleInfo.getClientName());
        });

        return new Page<>(list, commond);
    }


    @Authorize
    @GetMapping("/{productId}/isCollection")
    public String isCollection(@PathVariable String productId, ModelMap map) {
        map.put("productId",productId);
        Long count = CliCollectorItem.me().setFkProductId(productId).setFkClienteleId(User.id()).findCount();
        if(count>0){
            CliCollectorItem.me().setFkProductId(productId).setFkClienteleId(User.id()).delete();
        }else{
            CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
            EvaProProduct master = EvaProProduct.me().setId(productId).get();
            CliCollectorItem collectorItem = new CliCollectorItem();
            collectorItem.setFkClienteleId(clienteleInfo.getId());
            collectorItem.setClientName(clienteleInfo.getClientName());
            collectorItem.setFkCollectorItemTypeId("2");
            collectorItem.setCollectorItemTypeName("商品");
            collectorItem.setFkProductId(master.getId());
            collectorItem.setProductName(master.getProductName());
            collectorItem.setProductImageUrl(master.getProductPicImageUrl());
            collectorItem.setIsEnable(1);
            collectorItem.setAddTime(Utils.currentTimeSecond());
            collectorItem.insert();
        }
        return "/web/pages/_inc/collection";
    }


    @Authorize
    @PostMapping("/{productId}/addCart")
    @ResponseBody
    public Result addCart(@PathVariable String productId, ModelMap map) {
        map.put("productId",productId);
        CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(User.id()).get();
        ProProductStock productStock = ProProductStock.me().setFkProductId(productId).setIsEnable(BooleanEnum.TRUE.getValue()).findFirst();
        return Result.build(() -> ordShoppingCartService.doAddOrdShopping(productId, 1, cliClienteleInfo, productStock.getProductSubSku()));
    }


    /**
     *  查购物车单个详情
     */
    @GetMapping("/cart_confirm_data")
    public String getShoppingCartBySku(ModelMap map, HttpServletRequest request, String productId, String productSubSku) {
        OrdShoppingCart shoppingCart = ordShoppingCartService.getShoppingCartBySku(productId, productSubSku,request);
        map.put("shoppingCart", shoppingCart);
        return templatePath("product/lib/_cart_confirm_data");
    }


    /**
     *  获取商品一级分类列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getProductTypes")
    public List<ProProductType> getProductTypes() {
            return ProProductType.me().setIsEnable(BooleanEnum.TRUE.getValue()).setParentProductTypeId(TreeEnum.EVALUATION.getId()).setOrderByFields(" add_time").find();
    }


    /**
     *  获取商品标签列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getProductTagList")
    public List<SysDictionary> getProductTagList() {
            return SysDictionary.me().setPcode("productTag").setIsEnable(1).setOrderByFields(SysDictionary.F_SORTING).find();
    }


}
