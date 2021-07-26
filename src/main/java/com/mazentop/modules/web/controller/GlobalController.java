package com.mazentop.modules.web.controller;

import com.google.code.kaptcha.Constants;
import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.Constant;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.modules.skin.block.IBlockView;
import com.mazentop.modules.skin.block.dto.BlockData;
import com.mazentop.modules.skin.block.impl.CollectionBlockView;
import com.mazentop.modules.skin.commond.SkinPageBlockCommond;
import com.mazentop.modules.skin.dto.SimpleProductDto;
import com.mazentop.modules.skin.service.ProductSpecialService;
import com.mazentop.modules.user.service.MallHomeService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.commond.BlogCommond;
import com.mazentop.modules.web.seodetail.ISeoDetails;
import com.mazentop.plugins.session.RequestCount;
import com.mazentop.plugins.session.SessionUtil;
import com.mazentop.plugins.theme.ThemeUtil;
import com.mazentop.searcher.keyword.ProductKeyword;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.annotation.Order;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.model.Status;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.security.provider.certpath.CertId;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author mzt
 */
@Controller
@RequestMapping(GlobalController.MODEL_PATH)
public class GlobalController extends BaseController {

    public static final String MODEL_PATH = "/";

    @Autowired
    Map<String, ISeoDetails> iSeoDetailsMap;

    @Autowired
    private MallHomeService mallHomeService;

    @Autowired
    Map<String, IBlockView> blockViewMap;

    @Autowired
    ProductSpecialService productSpecialService;

    @Autowired
    BaseDao baseDao;

    /**
     * 首页
     * @param map
     * @return
     */
    @GetMapping("/")
    public String home(ModelMap map, HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        Helper.setDefaultCurrency(request,response);
        setGlobeSeoAttrs(modelMap);
        return toPageBlock(HOME_PAGE, map);
    }

    @GetMapping("/pages/{slug}")
    public String pages(@PathVariable("slug")  String slug, ModelMap modelMap) {
        return details(ProSeoTypeEnum.TYPE_PAGE.type(), slug, modelMap);
    }

    /**
     * 帮助页面
     * @param map
     * @return
     */
    @GetMapping("/userGuide")
    public String userGuide(ModelMap map) {
        map.put("userGuides",CmsArticle.me().setModule("help").setStatus("normal").setOrderByFields(Order.asc(CmsArticle.F_ADD_TIME)).find());
        return templatePath("userGuide");
    }

    /**
     * FAQ页面
     * @param map
     * @return
     */
    @GetMapping("/faq")
    public String faq(ModelMap map) {
        map.put("faqTypes",CmsTaxonomy.me().setIsEnable(BooleanEnum.TRUE.getValue()).setModule("faq").setOrderByFields(" add_time").find());
        // 获取排序前4的FAQ
        map.put("faqTop",CmsArticle.me().setModule("faq").setOrderByFields(" sort ").setLimit(4).find());
        return templatePath("faq");
    }


    /**
     * FAQ页面
     * @param map
     * @return
     */
    @GetMapping("/faqContent")
    public String faqContent(ModelMap map) {
        map.put("faqTypes",CmsTaxonomy.me().setIsEnable(BooleanEnum.TRUE.getValue()).setModule("faq").setOrderByFields(" add_time").find());
        return templatePath("faqContent");
    }

    /**
     * 产品 seo 重新编码地址
     * @param slug
     * @return
     */
    @GetMapping("/product/{slug}")
    public String productDetails(@PathVariable("slug")  String slug, ModelMap modelMap) {
        return details(ProSeoTypeEnum.TYPE_PRODUCT.type(), slug, modelMap);
    }

    /**
     * 分类重新编码地址
     * @param slug
     * @param modelMap
     * @return
     */
    @GetMapping("/category/{slug}")
    public String categoryDetails(@PathVariable("slug")  String slug, ModelMap modelMap, ProductKeyword keyword) {
        return details(ProSeoTypeEnum.TYPE_CATEGORY.type(),slug,modelMap, keyword);
    }

    /**
     * 博客首页
     * @param map
     * @return
     */
    @GetMapping("/blog")
    public String blog(BlogCommond commond, ModelMap map) {
        commond.setPageSize(9);
        map.put("blogs", CmsArticle.me().find(commond));
        map.put("commond", commond);
        return templatePath("blog/index");
    }


    /**
     * 博客 seo 重新编码地址
     *
     * @param slug
     * @return
     */
    @GetMapping("/blog/{slug}")
    public String blogDetails(@PathVariable("slug") String slug, ModelMap modelMap) {
        return details(ProSeoTypeEnum.TYPE_BLOG.type(), slug, modelMap);
    }

    @GetMapping("/collections/{slug}")
    public String collectionsDetails(@PathVariable("slug") String slug, ModelMap modelMap) {
        return details(ProSeoTypeEnum.TYPE_SKIN.type(), slug, modelMap);
    }


    private String details(String type, String slug, ModelMap modelMap) {
        return details( type,  slug, modelMap, null);
    }

    private String details(String type, String slug, ModelMap modelMap,ProductKeyword keyword) {
        if(!iSeoDetailsMap.containsKey(ThemeUtil.seoDetailsRegistrationName(type))) {
            return error404();
        }
        ISeoDetails seoDetails = iSeoDetailsMap.get(ThemeUtil.seoDetailsRegistrationName(type));
        ProSeo proSeo = this.retrievalSeo(type, slug);

        // seo里面地址 查询结果如果找不到尝试 内容slug
        if(Objects.isNull(proSeo)) {
            proSeo = seoDetails.retrievalSeo(slug);
        }

        modelMap.put("sourceType", type);

        if(Objects.isNull(proSeo)) {
            return errorNull();
        }

        setGlobeSeoAttrs(modelMap, proSeo);
        if (Objects.isNull(keyword)) {
           return iSeoDetailsMap.get(ThemeUtil.seoDetailsRegistrationName(type)).builder(modelMap, proSeo);
        }
        return iSeoDetailsMap.get(ThemeUtil.seoDetailsRegistrationName(type)).builder(keyword,modelMap, proSeo);
    }

    /**
     * 获取FAQ分类列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getFaqTypeList")
    public List<CmsTaxonomy> getFaqTypeList() {
        return CmsTaxonomy.me().setIsEnable(BooleanEnum.TRUE.getValue()).setModule("faq").setOrderByFields(" add_time").find();
    }


    /**
     * 根据FAQ分类获取FAQ列表
     * @param faqType
     * @return
     */
    @ResponseBody
    @GetMapping("/getFaqList/{faqType}")
    public List<CmsArticle> getFaqList(@PathVariable String faqType) {
        String sql = "select ar.* from cms_article as ar where module = 'faq' and status = 'normal' and id in (select fk_article_id from cms_article_link li where li.fk_taxonomy_id = :faqType) order by ar.sort";
        Map<String,String> param = new HashMap<>(1);
        param.put("faqType",faqType);
        return baseDao.queryForBeanList(sql,param,CmsArticle.me());
    }


    /**
     * 获取支持国家列表
     * @return
     */
    @ResponseBody
    @GetMapping("/getCountryList")
    public List<SkinCountry> getCountryList() {
        return SkinCountry.me().setIsEnable(Status.YES).find();
    }


    /**
     * 将切换国家存入session
     * @param code
     */
    @ResponseBody
    @GetMapping("/setCountrySession/{code}")
    public void setCountrySession(@PathVariable String code) {
        SkinCountry skinCountry = SkinCountry.me().setCountryCode(code).get();
        SessionUtil.setSessionCountry(skinCountry);
    }


    /**
     * 从session获取国家
     * @return
     */
    @ResponseBody
    @GetMapping("/getCountrySession")
    public SkinCountry getCountrySession() {
        return SessionUtil.getSessionCountry();
    }




    /**
     * 404
     *
     * @return
     */
    @GetMapping("/404")
    public String index() {
        return error404();
    }

    /**
     * 注册
     *
     * @return
     */
    @GetMapping("/registered")
    public String registered(String cId, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        Helper.setDefaultCurrency(request,response);
        modelMap.put("cId",cId);
        setGlobeSeoAttrs(modelMap);
        return "/web/registered";
    }

    /**
     * 登录
     *
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
        if(User.isAuth()){
            return User.redirect();
        }
        setGlobeSeoAttrs(modelMap);
        Helper.setDefaultCurrency(request,response);
        return "/web/login";
    }

    /**
     * 用户订阅
     *
     * @param email
     * @param request
     * @return
     */
    @PostMapping("/subscription")
    @ResponseBody
    public Result clientSubscription(String email, String code, HttpServletRequest request) {
        if(RequestCount.isCurrentLimiting(RequestCount.CurrentLimiting.WEBFORM_SUBSCRIPTION)) {
            if(Utils.isBlank(code) || !code.equals(SessionUtil.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY))) {
                 R r = R.toast("验证码输入错误");
                r.put("viewCode", true);
                return Result.build(r);
            }
        }
        RequestCount.setSessionAttributeCount(RequestCount.CurrentLimiting.WEBFORM_SUBSCRIPTION);
        String ip = Helper.getIpAddress(request);
        R r = mallHomeService.saveSubscription(email, ip);
        r.put("viewCode", RequestCount.isViewCode(RequestCount.CurrentLimiting.WEBFORM_SUBSCRIPTION));
        return Result.build(r);
    }


    @GetMapping("/resetPassword")
    public String resetPassword(){
        return "/web/forgetpassword";
    }

    @GetMapping("/contact")
    public String contact(ModelMap modelMap){
        setGlobeSeoAttrs(modelMap);
        return templatePath("page/contact");
    }

    @GetMapping("/about")
    public String about(ModelMap modelMap){
        setGlobeSeoAttrs(modelMap);
        return templatePath("page/about");
    }

    @GetMapping("/succeed")
    public String succeed(){
        return "/web/payment/succeed";
    }

    @GetMapping("/failure")
    public String failure(){
        return "/web/payment/failure";
    }

    @GetMapping("/sitemap")
    public String sitemap(){
        return templatePath("sitemap");
    }

    @GetMapping("/isLogin")
    @ResponseBody
    public Result isLogin(){
        Map<String,Object> map =new HashMap<>();

        return Result.build(() -> {
            if(!StringUtils.isBlank(User.isLogin())){
                map.put("code",403);
            }else{
                map.put("code",200);
            }
            return map;
        });
    }


    @GetMapping("/getProductRecommends")
    @ResponseBody
    public Result<List<SimpleProductDto>> getProductRecommends(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        SkinPageBlockCommond skinPageBlockCommond = new SkinPageBlockCommond();
        skinPageBlockCommond.setHandle("infinite-scroll");
        skinPageBlockCommond.setIsEnable(BooleanEnum.TRUE.getValue());
        BlockData blockData = blockViewMap.get(CollectionBlockView.MAPPING).query(skinPageBlockCommond);
        if(User.isAuth()){
            return Result.build(() ->productSpecialService.getProductSpecial(blockData.getDataId(), page, pageSize));
        }else{
            return Result.build(() ->productSpecialService.getProductSpecialNoLogin(blockData.getDataId(), page, pageSize));
        }
    }

    @GetMapping("/findCollectionsProduct")
    @ResponseBody
    public Result<List<SimpleProductDto>> findCollectionsProduct(
            @RequestParam(value = "collection", defaultValue = "") String collection,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
        return Result.build(() ->productSpecialService.getProductSpecial(collection, page, pageSize));
    }
}