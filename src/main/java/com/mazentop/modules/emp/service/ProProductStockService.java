package com.mazentop.modules.emp.service;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.Status;
import com.mazentop.modules.emp.commond.ProProductStockCommond;
import com.mazentop.modules.emp.dto.ProProductSpecDto;
import com.mazentop.modules.emp.dto.ProProductStockDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Maps;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ProProductStockService {

    @Autowired
    BaseDao baseDao;


    /**
     * 分页获取sku
     * @param commond
     * @return
     */
    public Page getProProductStockPage(ProProductStockCommond commond) {
        commond.setIsEnable(Status.YES);
        List<ProProductStock> list = ProProductStock.me().find(commond);
        list.forEach(stock->{
            ProProductMaster product = ProProductMaster.me().setId(stock.getFkProductId()).get();
            if(Objects.nonNull(product)){
                stock.addExten("productName", product.getProductName());
            }
            stock.addExten("productMallPrice",Helper.transformF2Y(stock.getProductMallPrice()).toString());
            stock.addExten("productMarketPrice",Helper.transformF2Y(stock.getProductMarketPrice()).toString());
            stock.addExten("stockOption","1");
            stock.addExten("number",0);
            stock.addExten("isSave",true);
            // 查sku属性
            List<Map<String,Object>> map = getSpecInStockId(stock.getId());
            stock.addExten("spec", map);
        });
        return new Page(list, commond);
    }


    /**
     * 获取库存列表，不分页
     * @param commond
     * @return
     */
    public List<ProProductStock> getProProductStockList(ProProductStockCommond commond) {
        commond.setIsEnable(Status.YES);
        commond.setPage(Status.NO);
        List<ProProductStock> list = ProProductStock.me().find(commond);
        list.forEach(stock->{
            ProProductMaster product = ProProductMaster.me().setId(stock.getFkProductId()).get();
            if(Objects.nonNull(product)){
                stock.addExten("productName", product.getProductName());
            }
            stock.addExten("productMallPrice",Helper.transformF2Y(stock.getProductMallPrice()).toString());
            stock.addExten("productMarketPrice",Helper.transformF2Y(stock.getProductMarketPrice()).toString());
            // 查sku属性
            List<Map<String,Object>> specs = getSpecInStockId(stock.getId());
            String spec = "";
            for (Map map : specs) {
                spec += map.get("parent_spec_name").toString() + " - " + map.get("spec_name") + "\n";
            }
            stock.addExten("spec",spec);
        });
        return list;
    }


    /**
     * 根据skuid查商品属性
     * @param stockId
     * @return
     */
    public List<Map<String,Object>> getSpecInStockId(String stockId){
        if (Objects.nonNull(stockId)){
            String sql="select s.*," +
                    "(select spec_name from pro_product_spec where id=i.fk_parent_spec_id )" +
                    " as parent_spec_name,(select spec_name from pro_product_spec where id=i.fk_spec_id )" +
                    " as spec_name " +
                    "from pro_product_sec_info i " +
                    "left join pro_product_stock  s on s.id=i.fk_stock_id where i.fk_stock_id =:stockId";
            return baseDao.queryForList(sql, Maps.of("stockId", stockId));
        }
        return null;
    }


    /**
     * sku详情
     * @param id
     * @return
     */
    public ProProductStock getProProductStock(String id){
        ProProductStock proProductStock = ProProductStock.me().setId(id).get();
        proProductStock.addExten("productMallPrice",Helper.transformF2Y(proProductStock.getProductMallPrice()).toString());
        proProductStock.addExten("productMarketPrice",Helper.transformF2Y(proProductStock.getProductMarketPrice()).toString());
        return proProductStock;
    }

    /**
     * 编辑商品库存sku
     * @param stockDtoList
     * @param productId
     * @return
     */
    public Integer editProProductStock(List<ProProductStockDto> stockDtoList, String productId) {
        if (null == stockDtoList || stockDtoList.size() == 0) {
            return null;
        }
        if (Helper.isEmpty(productId)) {
            productId = stockDtoList.get(0).getFkProductId();
        }
        List<ProProductSpecDto> specDtoList = new ArrayList<>();
        for (ProProductStockDto proProductStockDto : stockDtoList) {
            specDtoList.addAll(proProductStockDto.getList());
        }
        // 获取商品属性
        List<ProProductSpecDto> proProductSpecList = getProProductSpec(specDtoList);

        Map<String, String> map = new HashMap<>(1);
        Integer stockNumber = 0;
        // 清理商品属性
        ProProductStock.me().setFkProductId(productId).delete();
        ProProductSecInfo.me().setFkProductMasterId(productId).delete();

        for (ProProductStockDto proProductStockDto : stockDtoList) {
            stockNumber += proProductStockDto.getProductStockNumber();

            ProProductStock proProductStock = new ProProductStock();
            BeanUtils.copyProperties(proProductStockDto, proProductStock);

            proProductStock.setProductMallPrice(Helper.transformY2F(new BigDecimal(proProductStockDto.getProductMallPriceStr())));
            proProductStock.setProductMarketPrice(Helper.transformY2F(new BigDecimal(proProductStockDto.getProductMarketPriceStr())));
            proProductStock.setFkProductId(productId);
            proProductStock.setIsEnable(BooleanEnum.TRUE.getValue());
            proProductStock.setAddTime(Utils.currentTimeSecond());
            proProductStock.setAddUserId(Subject.id());
            proProductStock.insert();

            for (ProProductSpecDto proProductSpecDto : proProductSpecList) {
                map.put(proProductSpecDto.getProductSpec().getId(), proProductSpecDto.getId());
                proProductStockDto.getList().forEach(specs -> {
                    if (specs.getSpecName().equals(proProductSpecDto.getSpecName())) {
                        specs.setId(proProductSpecDto.getId());
                    }
                    if (specs.getProductSpec().getSpecName().equals(proProductSpecDto.getProductSpec().getSpecName())) {
                        specs.getProductSpec().setParentId(proProductSpecDto.getId());
                        specs.getProductSpec().setId(proProductSpecDto.getProductSpec().getId());
                    }
                });
            }

            for (ProProductSpec proProductSpec : proProductStockDto.getList()) {
                ProProductSecInfo proProductSecInfo = new ProProductSecInfo();
                proProductSecInfo.setFkStockId(proProductStock.getId());
                proProductSecInfo.setFkProductMasterId(proProductStock.getFkProductId());
                proProductSecInfo.setFkSpecId(((ProProductSpecDto) proProductSpec).getProductSpec().getId());
                proProductSecInfo.setFkParentSpecId(map.get(((ProProductSpecDto) proProductSpec).getProductSpec().getId()));
                editProProductSecInfo(proProductSecInfo);
            }

        }
        return stockNumber;
    }


    /**
     * 编辑库存信息
     * @param productStock
     * @return
     */
    public R doEditProductStock(ProProductStock productStock) {
        if(Helper.isEmpty(productStock.getId())){
            return R.toast("库存id不能为空");
        }
        productStock.update();
        return R.ok();
    }


    public Result doEditProProductSpec(ProProductSpec proProductSpec) {
        if (proProductSpec.equals("0")) {
            Long count = ProProductSpec.me().setParentId("0").setSpecName(proProductSpec.getSpecName()).findCount();
            if (count >= 1) {
                return Result.toast("规格类已存在！");
            }
        } else {
            Long count = ProProductSpec.me().setParentId(proProductSpec.getParentId()).setSpecName(proProductSpec.getSpecName()).findCount();
            if (count >= 1) {
                return Result.toast("规格已存在！");
            }
        }
        if (Helper.isNotEmpty(proProductSpec.getId())) {
            proProductSpec.update();
        } else {
            proProductSpec.insert();
        }
        return Result.success();
    }


    public Result delProProductSpec(String id) {
        List<ProProductSpec> proProductSpecs = ProProductSpec.me().setParentId(id).find();
        if (proProductSpecs.size() > 0) {
            StringBuilder str = new StringBuilder();
            str.append("select count(*) from pro_product_sec_info where 1=1");
            str.append(" and fk_spec_id in (:ids)");
            List<String> list = proProductSpecs.stream().map(ProProductSpec::getId).collect(Collectors.toList());
            Map<String, Object> param = new HashMap<>();
            param.put("ids", list);
            Long count = baseDao.queryForLong(str.toString(), param);
            if (count > 0) {
                return Result.toast("当前规格类存在商品使用，删除失败！");
            }
            ProProductSpec.me().setId(id).delete();
        }
        return Result.success();
    }



    public Result delProProductSpecItem(String id) {
        ProProductSpec proProductSpec = ProProductSpec.me().setId(id).get();
        if (Helper.isEmpty(proProductSpec)) {
            return Result.toast("删除失败 暂无此数据！");
        }
        if (proProductSpec.getParentId().equals("0")) {
            return Result.toast("删除失败 不只能直接规格类！");
        }
        Long count = ProProductSecInfo.me().setFkSpecId(id).findCount();
        if (count > 0) {
            return Result.toast("当前规格存在商品使用，删除失败！");
        }
        ProProductSpec.me().setId(id).delete();
        return Result.success();
    }

    /**
     * 根据商品id获取库存sku信息
     * @param productId
     * @return
     */
    public List<ProProductStockDto> getSku(String productId) {
        List<ProProductStockDto> skuList = new ArrayList<>();
        List<ProProductStock> proProductStocks = ProProductStock.me().setFkProductId(productId).setIsEnable(BooleanEnum.TRUE.getValue()).find();
        proProductStocks.forEach(sku -> {
            ProProductStockDto dto = new ProProductStockDto();
            BeanUtils.copyProperties(sku, dto);

            List<Map<String, Object>> specs = getSpecAndParent(sku.getId());
            List<ProProductSpecDto> specList = new ArrayList<>();
            for (Map<String, Object> stringObjectMap : specs) {
                ProProductSpecDto specDto = new ProProductSpecDto();

                specDto.setSpecName(stringObjectMap.get("name").toString());

                ProProductSpec spec = new ProProductSpec();
                spec.setSpecName(stringObjectMap.get("spec_name").toString());
                specDto.setProductSpec(spec);

                specList.add(specDto);
            }
            dto.setList(specList);
            skuList.add(dto);
        });
        return skuList;
    }



    public List<Map<String, Object>> getSpec(String productId){
        String sql = "SELECT fk_spec_id, ( SELECT spec_name FROM pro_product_spec WHERE id = fk_parent_spec_id ) AS NAME from pro_product_sec_info WHERE fk_product_master_id =(:productId) GROUP BY NAME";
        Map<String, Object> param = new HashMap<>();
        param.put("productId", productId);
        List<Map<String, Object>> maps = baseDao.queryForList(sql, param);
        return maps;

    }

    public List<Map<String, Object>> getSpecAndParent(String stockId) {
        String sql = "SELECT fk_spec_id, fk_parent_spec_id,(SELECT spec_name FROM pro_product_spec WHERE id = fk_parent_spec_id ) AS name,(SELECT spec_name FROM pro_product_spec WHERE id = fk_spec_id ) AS spec_name FROM  pro_product_sec_info WHERE fk_stock_id=(:stockId)";
        Map<String, Object> param = new HashMap<>();
        param.put("stockId", stockId);
        List<Map<String, Object>> maps = baseDao.queryForList(sql, param);
        return maps;
    }

    public Result editProProductSecInfo(ProProductSecInfo proProductSecInfo) {
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return Result.toast("登录信息获取失败!");
        }
        if (Helper.isEmpty(proProductSecInfo.getFkProductMasterId())) {
            return Result.toast("商品id不能为空！");
        }
        if (Helper.isEmpty(proProductSecInfo.getFkSpecId())) {
            return Result.toast("规格id不能为空");
        }
        if (Helper.isEmpty(proProductSecInfo.getFkStockId())) {
            return Result.toast("库存id不能为空");
        }
        if (Helper.isEmpty(proProductSecInfo.getId())) {
            proProductSecInfo.setAddTime(Utils.currentTimeSecond());
            proProductSecInfo.setAddUserId(curUserId);
        }
        proProductSecInfo.insertOrUpdate();
        return Result.success();
    }

    /**
     * 获取父类子类ID
     *
     * @param list
     * @return
     */
    private List<ProProductSpecDto> getProProductSpec(List<ProProductSpecDto> list) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> specNames = list.stream().map(ProProductSpecDto::getSpecName).collect(Collectors.toList());
        String sql = "select * from pro_product_spec where parent_id=0 and spec_name in (:specName)";
        Map<String, Object> param = new HashMap<>(1);
        param.put("specName", specNames);
        List<ProProductSpec> specList = baseDao.queryForBeanList(sql, param, new ProProductSpec());
        Map<String, String> specMap = specList.stream().collect(Collectors.toMap(ProProductSpec::getSpecName, ProProductSpec::getId));
        for (ProProductSpecDto proProductSpecDto : list) {
            if (specMap.containsKey(proProductSpecDto.getSpecName())) {
                proProductSpecDto.setId(specMap.get(proProductSpecDto.getSpecName()));
            } else {
                ProProductSpec proProductSpec = new ProProductSpec();
                BeanUtils.copyProperties(proProductSpecDto, proProductSpec);
                proProductSpec.setFkProductId(null);
                proProductSpec.setParentId("0");
                proProductSpec.insert();
                proProductSpecDto.setId(proProductSpec.getId());
                specMap.put(proProductSpec.getSpecName(), proProductSpec.getId());
            }
        }

        List<String> parentId = new ArrayList<>(specMap.values());
        String sqlSpec = "select * from pro_product_spec where parent_id in (:parentId)";
        param.put("parentId", parentId);
        List<ProProductSpec> proProductSpecs = baseDao.queryForBeanList(sqlSpec, param, new ProProductSpec());
        Map<String, Map<String, String>> parentMap = new HashMap<>();
        proProductSpecs.forEach(spec -> {
            setParentMap(spec, parentMap);
        });
        for (ProProductSpecDto proProductSpecDto : list) {
            if (parentMap.containsKey(proProductSpecDto.getId())) {
                Map<String, String> map = parentMap.get(proProductSpecDto.getId());
                if (map.containsKey(proProductSpecDto.getProductSpec().getSpecName())) {
                    proProductSpecDto.getProductSpec().setId(map.get(proProductSpecDto.getProductSpec().getSpecName()));
                    proProductSpecDto.getProductSpec().setParentId(proProductSpecDto.getId());
                } else {
                    insertProductSpec(proProductSpecDto.getProductSpec(), parentMap, proProductSpecDto.getId());
                }
            } else {
                insertProductSpec(proProductSpecDto.getProductSpec(), parentMap, proProductSpecDto.getId());
            }
        }
        return list;
    }



    private void setParentMap(ProProductSpec spec, Map<String, Map<String, String>> parentMap) {
        if (parentMap.containsKey(spec.getParentId())) {
            Map<String, String> stringStringMap = parentMap.get(spec.getParentId());
            stringStringMap.put(spec.getSpecName(), spec.getId());
        } else {
            Map<String, String> sonMap = new HashMap<>();
            sonMap.put(spec.getSpecName(), spec.getId());
            parentMap.put(spec.getParentId(), sonMap);
        }
    }



    private void insertProductSpec(ProProductSpec proProductSpecDto, Map<String, Map<String, String>> parentMap, String parentId) {
        ProProductSpec proProductSpec = new ProProductSpec();
        BeanUtils.copyProperties(proProductSpecDto, proProductSpec);
        proProductSpec.setParentId(parentId);
        proProductSpec.insert();
        setParentMap(proProductSpec, parentMap);
        proProductSpecDto.setId(proProductSpec.getId());
        proProductSpecDto.setParentId(proProductSpecDto.getId());
    }
}
