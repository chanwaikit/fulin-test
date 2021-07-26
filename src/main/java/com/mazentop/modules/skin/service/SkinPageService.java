package com.mazentop.modules.skin.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.modules.emp.dto.SelectDto;
import com.mazentop.modules.emp.service.ProSeoService;
import com.mazentop.modules.skin.commond.SkinPageCommond;
import com.mazentop.modules.skin.dto.SkinPageDto;
import com.mazentop.modules.skin.warpper.ConditionWarpper;
import com.mazentop.plugins.seo.Seo;
import com.mazentop.util.Helper;
import com.mztframework.cache.Options;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dengy
 * 页面布局块逻辑类
 */
@Service
public class SkinPageService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    SkinPageLayoutService skinPageLayoutService;

    @Autowired
    ProSeoService proSeoService;


    public Page findSkinPageList(SkinPageCommond commond){
        List<SkinPage> skinPageList = SkinPage.me().find(commond);
        skinPageList.forEach(skinPage -> {
            SysTemplate sysTemplate =  skinPageLayoutService.getTemplateName(skinPage.getTemplateId());
            if(!Objects.isNull(sysTemplate)){
                skinPage.addExten("templateName", sysTemplate.getTemplateName());
            }else{
                skinPage.addExten("templateName", "");
            }
        });
        return new Page(skinPageList,commond);
    }



    public R deleteSkinPageInfo(List<String> ids) {
        for (String id : ids) {
            SkinPage.me().setId(id).delete();
        }
        return  R.ok();
    }

    /**
     * 商品专辑列表
     * @param commond
     * @return
     */
    public Page findGoodSpecialList(SkinPageCommond commond){
        //查询商品专辑信息
        Map<String,Object> param =new HashMap<>();
        StringBuilder sql = new StringBuilder(" SELECT page.*,pageData.data_options,pageData.max_limit,pageData.data_type,pageData.data_source ");
        sql.append(" FROM skin_page page,skin_page_data pageData WHERE 1=1 and page.id = pageData.id");
        if(Helper.isNotEmpty(commond.getTitle())){
            sql.append(" and page.title like :title ");
            param.put("title","%"+commond.getTitle()+"%");
        }
        if(Helper.isNotEmpty(commond.getType())){
            sql.append(" and page.type = :type ");
            param.put("type", commond.getType());
        }
        if(Helper.isNotEmpty(commond.getTemplateId())){
            sql.append(" and page.template_id = :templateId ");
            param.put("templateId",commond.getTemplateId());
        }
        List<Map<String,Object>> goodSpecialList = baseDao.paginate(sql.toString(),param,commond);
        optionSpecialData(goodSpecialList);
        return new Page(goodSpecialList,commond);
    }



    public List<Map<String,Object>> getConditionGoodsData(SkinPageDto skinPageDto){
        if(!skinPageDto.getConditionData().isEmpty()){
            List<Map<String,Object>> data = new ArrayList<>();
            StringBuilder sql = new StringBuilder("SELECT pro.*,stock.product_mall_price,stock.product_market_price,stock.product_stock_number ");
            sql.append(" FROM pro_product_master pro LEFT JOIN pro_product_stock stock ON pro.id = stock.fk_product_id ");
            sql.append(" WHERE 1 = 1 AND pro.is_enable = 1 ");
            String splicing = " and ";
            if(skinPageDto.getConditionFlag().equals(1)){
                splicing=" or ";
            }
            List<ConditionWarpper> conditionData =  skinPageDto.getConditionData();
            int i = 0;
            Map<String,Object> param = new HashMap<>();
            for (ConditionWarpper conditionWarpper : conditionData){
                //条件拼接符
                if(i == 0) {
                    sql.append("and ");
                }else{
                    sql.append(splicing);
                }
                //条件字段
                if("product_mall_price".equals(conditionWarpper.getConditionField()) || "product_stock_number".equals(conditionWarpper.getConditionField())){
                    sql.append(" stock."+conditionWarpper.getConditionField());
                    sql.append(" "+conditionWarpper.getConditionkey()+" :"+conditionWarpper.getConditionField());
                    if("product_mall_price".equals(conditionWarpper.getConditionField())) {
                        param.put(conditionWarpper.getConditionField(), Helper.transformY2F(new BigDecimal(conditionWarpper.getConditionValue())));
                    }else{
                        param.put(conditionWarpper.getConditionField(), conditionWarpper.getConditionValue());
                    }
                }else{
                    sql.append("pro."+conditionWarpper.getConditionField());
                    if(!"like".equals(conditionWarpper.getConditionkey()) && !"not".equals(conditionWarpper.getConditionkey())){
                        param.put(conditionWarpper.getConditionField(), conditionWarpper.getConditionValue());
                    }
                    if("like".equals(conditionWarpper.getConditionkey()) || "not".equals(conditionWarpper.getConditionkey())){
                        param.put(conditionWarpper.getConditionField(), "%"+conditionWarpper.getConditionValue()+"%");
                    }
                    if("not".equals(conditionWarpper.getConditionkey())){
                        sql.append(" "+conditionWarpper.getConditionkey()+"like :"+conditionWarpper.getConditionField());
                    }else{
                        sql.append(" "+conditionWarpper.getConditionkey()+" :"+conditionWarpper.getConditionField());
                    }
                }
                i++;
            }
            sql.append(" GROUP BY stock.fk_product_id");
            data = baseDao.queryForList(sql.toString(),param);
            optionGoods(data);
            return data;
        }
        return null;

    }

    /**
     *
     * @param skinPage
     * @return
     */
    public Result doAddOrUpdateSpecial(SkinPageDto skinPage){
        skinPage.setUrl(skinPage.getSeo().getSeoAddress());
        if(Helper.isNotEmpty(skinPage.getId())){
            SkinPageData skinPageData = SkinPageData.me().setId(skinPage.getId()).get();
            getPageData(skinPage, skinPageData);
            skinPage.update();
            skinPageData.update();
        }else{
//            skinPage.setType("special");
            skinPage.insert();
            SkinPageData skinPageData = new SkinPageData();
            skinPageData.setDataType("collections");
            skinPageData.setId(skinPage.getId());
            getPageData(skinPage, skinPageData);
            skinPageData.insert();
        }
        if (Helper.isNotEmpty(skinPage.getSeo())) {
            skinPage.getSeo().setSource(skinPage.getId());
            proSeoService.editCollectionsSeo(skinPage.getSeo());
        }
        return Result.success();
    }



    private void getPageData(SkinPageDto skinPage, SkinPageData skinPageData) {
        skinPageData.setDataSource(skinPage.getDataSource());
        skinPageData.setMaxLimit(skinPage.getMaxLimit());
        if ("automatic".equals(skinPage.getDataSource())) {
            Map<String, Object> map = new HashMap<>();
            map.put("conditionData", skinPage.getConditionData());
            map.put("conditionFlag", skinPage.getConditionFlag());
            JSONObject jsonObject = new JSONObject(map);
            skinPageData.setDataOptions(jsonObject.toString());
        } else {
            SkinPageDataSnapshot.me().setPageId(skinPage.getId()).delete();
            if (!skinPage.getProId().isEmpty()) {
                skinPage.getProId().forEach(pro -> {
                    SkinPageDataSnapshot skinPageDataSnapshot = new SkinPageDataSnapshot();
                    skinPageDataSnapshot.setPageId(skinPage.getId());
                    skinPageDataSnapshot.setDataId(pro);
                    skinPageDataSnapshot.insert();
                });
            }
        }
    }



    public R deleteSpecialPageInfo(List<String> ids) {
        for (String id : ids) {
            SkinPage.me().setId(id).delete();
            SkinPageData.me().setId(id).delete();
            SkinPageDataSnapshot.me().setPageId(id).delete();
        }
        return  R.ok();
    }


    public long getPageCustomCount() {
        return SkinPage.me().setType("custom").findCount();
    }

    /**
     * 测评-专辑详情
     * @param id
     * @return
     */
    public Map<String,Object> getEvaluationSpecialPageInfo(String id){
        Map<String,Object> map = new HashMap<>(5);
        SkinPageData pageData = SkinPageData.me().setId(id).get();
        map.put("pageData",pageData);
        map.put("pageInfo",SkinPage.me().setId(id).get());

        String sql = "select pro.product_name as productName,pro.product_pic_image_url as productPicImageUrl,pro.id,pro.price,pro.original_price   from eva_pro_product pro, skin_page_data_snapshot dataSnapshot where 1=1 and pro.id = dataSnapshot.data_id and dataSnapshot.page_id =:id";
        Map<String,Object> param = new HashMap<>(1);
        param.put("id",id);
        ;
        List<Map<String, Object>> list = baseDao.queryForList(sql,param);
        list.forEach(product-> {
            if (null != product.get("price")) {
                product.put("price", Helper.transformF2Y(product.get("price")));
            } else {
                product.put("price", 0);
            }
            if (null != product.get("original_price")) {
                product.put("originalPrice", Helper.transformF2Y(product.get("original_price")));
            } else {
                product.put("originalPrice", 0);
            }
        });
        map.put("productList",list);
        return map;
    }


    public Map<String,Object> getSpecialPageInfo(String id){
        Map<String,Object> map = new HashMap<>(4);
        List<Map<String, Object>> list = new ArrayList<>();
        SkinPageData pageData = SkinPageData.me().setId(id).get();
        map.put("pageData",pageData);
        map.put("pageInfo",SkinPage.me().setId(id).get());
        map.put("conditionData",null);
        map.put("conditionFlag",0);
        if("automatic".equals(pageData.getDataSource())) {
            Map<String,Object> maps = (Map<String, Object>) JSONObject.parse(pageData.getDataOptions());
            List<ConditionWarpper> conditionData = JSONArray.parseArray(maps.get("conditionData").toString(), ConditionWarpper.class);
            Integer conditionFlag = Integer.parseInt(maps.get("conditionFlag").toString());
            SkinPageDto skinPageDto = new SkinPageDto();
            skinPageDto.setConditionData(conditionData);
            skinPageDto.setConditionFlag(conditionFlag);
            list = getConditionGoodsData(skinPageDto);
            map.put("conditionData",conditionData);
            map.put("conditionFlag",conditionFlag);
        }else{
            String sql = "select pro.* from pro_product_master pro, skin_page_data_snapshot dataSnapshot where 1=1 and pro.id = dataSnapshot.data_id and dataSnapshot.page_id =:id";
            Map<String,Object>param = new HashMap<>(1);
            param.put("id",id);
            list = baseDao.queryForList(sql,param);
            optionGoods(list);
        }
        map.put("productList",list);
        return map;
    }


    /**
     * 查商品专辑
     * @return
     */
    public List<Map<String,Object>> getGoodSpecialList(String type){
        //查询商品专辑信息
        Map<String,Object> param = new HashMap<>(1);
        StringBuilder sql = new StringBuilder(" SELECT page.*,pageData.data_options,pageData.max_limit,pageData.data_type,pageData.data_source ");
        sql.append(" FROM skin_page page,skin_page_data pageData WHERE page.type = :type and page.id = pageData.id ");
        param.put("type", type);
        List<Map<String,Object>> goodSpecialList = baseDao.queryForList(sql.toString(),param);
        optionSpecialData(goodSpecialList);
        return goodSpecialList;
    }


    /**
     * 获取自定义列表
     * @return
     */
    public Page getPagesList(SkinPageCommond commond){
        List<SkinPage> list = SkinPage.me().ignoreSelectFields(SkinPage.F_DESCRIPTION).find(commond);
        String webDomain = Options.get("site_setting_domain_name");
        list.forEach(item -> {
            item.setUrl(Seo.getSeoUrlForPageType(item.getId()));
            item.setSlug(String.format(ProSeoTypeEnum.TYPE_PAGE.getUri()+"%s",webDomain, item.getSlug()));
        });
        return new Page<>(list, commond);
    }





    private void optionSpecialData(List<Map<String, Object>> goodSpecialList) {
        if (!goodSpecialList.isEmpty()) {
            goodSpecialList.forEach(skinPage -> {
                //获取商品专辑数量
                Long count;
                String dataSource = skinPage.get("data_source").toString();
                if (dataSource.equals("automatic")) {
                    Map<String, Object> map =  JSON.parseObject(skinPage.get("data_options").toString());
                    List<ConditionWarpper> conditionData = JSON.parseArray(map.get("conditionData").toString(), ConditionWarpper.class);
                    Integer conditionFlag = Integer.parseInt(map.get("conditionFlag").toString());
                    SkinPageDto skinPageDto = new SkinPageDto();
                    skinPageDto.setConditionData(conditionData);
                    skinPageDto.setConditionFlag(conditionFlag);
                    List<Map<String, Object>> list = getConditionGoodsData(skinPageDto);
                    count = (long) list.size();
                } else {
                    count = SkinPageDataSnapshot.me().setPageId(skinPage.get("id").toString()).findCount();
                }
                skinPage.put("productCount", count);
            });
        }
    }

    public List<Map<String,Object>> getGoodsList(String id){
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isNotBlank(id)) {
            SkinPageData pageData = SkinPageData.me().setId(id).get();
            if ("automatic".equals(pageData.getDataSource())) {
                Map<String, Object> maps = JSON.parseObject(pageData.getDataOptions());
                List<ConditionWarpper> conditionData = JSON.parseArray(maps.get("conditionData").toString(), ConditionWarpper.class);
                Integer conditionFlag = Integer.parseInt(maps.get("conditionFlag").toString());
                SkinPageDto skinPageDto = new SkinPageDto();
                skinPageDto.setConditionData(conditionData);
                skinPageDto.setConditionFlag(conditionFlag);
                list = getConditionGoodsData(skinPageDto);
            } else {
                String sql = "select pro.* from pro_product_master pro, skin_page_data_snapshot dataSnapshot where 1=1 and pro.id = dataSnapshot.data_id and dataSnapshot.page_id =:id";
                Map<String, Object> param = new HashMap<>();
                param.put("id", id);
                list = baseDao.queryForList(sql, param);
                optionGoods(list);
            }
        }
        return list;
    }


    /**
     * 处理商品价格
     * @param data
     */
    private  void optionGoods(List<Map<String,Object>> data){
        if(!data.isEmpty()){
            data.forEach(map-> {
                List<ProProductStock> stockList = ProProductStock.me().setFkProductId(map.get("id").toString()).find();
                List<Long> price = new ArrayList<>();
                AtomicInteger stockNumber = new AtomicInteger();
                stockList.forEach(stock->{
                    price.add(stock.getProductMallPrice());
                    if (stock.getProductStockNumber() != null) {
                        stockNumber.addAndGet(stock.getProductStockNumber());
                    }
                });
                map.put("product_stock_number", stockNumber);
                if(!price.isEmpty()){
                    map.put("product_mall_price_max",Helper.transformF2Y(Collections.max(price)));
                    map.put("product_mall_price_min",Helper.transformF2Y(Collections.max(price)));
                }else{
                    map.put("product_mall_price_max",0);
                    map.put("product_mall_price_min",0);
                }
            });
        }
    }

    public R savePages(SkinPageDto skinPageDto) {
        skinPageDto.insertOrUpdate();
        if(!Objects.isNull(skinPageDto.getSeo())) {
            skinPageDto.getSeo().setSource(skinPageDto.getId());
            proSeoService.editPagesSeo(skinPageDto.getSeo());
        }
        return R.ok();
    }

    /**
     * 删除自定义页面
     * @param ids
     * @return
     */
    public R deletePages(List<String> ids) {
        for (String id : ids) {
            SkinPage.me().setId(id).delete();
            proSeoService.delPagesSeo(id);
        }
        return  R.ok();
    }

    public SkinPageDto getPages(String id) {
        SkinPage skinPage = SkinPage.me().setId(id).get();
        SkinPageDto skinPageDto = new SkinPageDto();
        BeanUtils.copyProperties(skinPage, skinPageDto);
        skinPageDto.setSeo(proSeoService.getProSeo(skinPage.getId()));
        return skinPageDto;
    }



    public List<SelectDto> findSpecialPageToSelect() {
        String sql = "select skin_page.id, skin_page.title from skin_page, skin_page_data " +
                "where skin_page.id = skin_page_data.id " +
                "and skin_page.type = :type " +
                "order by skin_page.add_time asc ";
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("type", "special");
        List<Map<String, Object>> mapLists =  baseDao.queryForList(sql, paramMap);
        List<SelectDto> rList = new ArrayList<>(mapLists.size());
        mapLists.forEach(item -> {
            String title = Utils.toCast(item.get("title"), String.class);
            String id = Utils.toCast(item.get("id"), String.class);
            SelectDto selectDto = new SelectDto();
            selectDto.setLabel(title);
            selectDto.setValue(Seo.getSeoUrlForCollection(id));
            rList.add(selectDto);
        });
        return rList;
    }


    public List<SelectDto> findCustomPageToSelect() {
        String sql = "select skin_page.id, skin_page.title from skin_page" +
                " where skin_page.type = :type " +
                " order by skin_page.add_time asc ";
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("type", "custom");
        List<Map<String, Object>> mapLists =  baseDao.queryForList(sql, paramMap);
        List<SelectDto> rList = new ArrayList<>(mapLists.size());
        mapLists.forEach(item -> {
            String title = Utils.toCast(item.get("title"), String.class);
            String id = Utils.toCast(item.get("id"), String.class);
            SelectDto selectDto = new SelectDto();
            selectDto.setLabel(title);
            selectDto.setValue(Seo.getSeoUrlForPageType(id));
            rList.add(selectDto);
        });
        return rList;
    }
}
