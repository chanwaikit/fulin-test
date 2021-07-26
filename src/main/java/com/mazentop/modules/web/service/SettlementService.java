package com.mazentop.modules.web.service;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.*;
import com.mazentop.modules.user.dto.SettlementConfirmDto;
import com.mazentop.modules.user.service.OrdShoppingCartService;
import com.mazentop.modules.web.User;
import com.mazentop.modules.web.dto.SettlementDto;
import com.mazentop.util.Helper;
import com.mztframework.dao.jdbc.BaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SettlementService {

    @Autowired
    OrdShoppingCartService ordShoppingCartService;

    @Autowired
    BaseDao baseDao;

    @Autowired
    TouristService touristService;

    public SettlementDto getSettlementDto(String id,HttpServletRequest request) {
        OrdSettlementConfirm settlementConfirm = OrdSettlementConfirm.me().setId(id).get();
        SettlementDto settlementDto = new SettlementDto();
        if(!Objects.isNull(settlementConfirm)){
            SettlementConfirmDto settlementConfirmDto = JSON.parseObject(settlementConfirm.getContent(), SettlementConfirmDto.class);
            BeanUtils.copyProperties(settlementConfirmDto,settlementDto);
            //获取购物车
            List<OrdShoppingCart> cartList = getCarts(settlementConfirmDto.getCarts(),request);
            ordShoppingCartService.addExchangeId(cartList, null);
            ordShoppingCartService.optProductPrice(cartList);
            BigDecimal totalPrice = new BigDecimal(0);
            BigDecimal totalCountVol = new BigDecimal(0);
            //构建结算数据信息
            if (!cartList.isEmpty()) {
                settlementDto.setCartNum(cartList.size());
                for (OrdShoppingCart cart : cartList) {
                    BigDecimal totalAmount = new BigDecimal(cart.getExten().get("totalAmount").toString());
                    BigDecimal discountAmount = new BigDecimal(cart.getExten().get("discountAmount").toString());
                    totalPrice = totalPrice.add(totalAmount.subtract(discountAmount));
                    ProProductMaster proProductMaster = ProProductMaster.me().setId(cart.getId()).get();
                    if (Objects.nonNull(proProductMaster)) {
                        if (proProductMaster.getIsTransFree() == 0) {
                            totalCountVol = totalCountVol.add(Helper.transformF2Y(proProductMaster.getNetWeight()));
                        }
                    }
                }
                settlementDto.setCarts(cartList);
            }
            settlementDto.setSubTotalPrice(totalPrice.toString());
            settlementDto.setShippingPrice("0");
            settlementDto.setTotalPrice(totalPrice.toString());
            //运费计算
            if(isOptionLogistics(settlementDto)) {
                getAddressOrCountry(totalPrice, totalCountVol, settlementDto);
            }
            settlementDto.setSettlementId(settlementConfirm.getId());
        }
        return settlementDto;
    }

    public String doSaveSettlement(List<String> cartId,boolean cartFlag) {
        SettlementConfirmDto settlementConfirmDto = new SettlementConfirmDto();
        OrdSettlementConfirm settlementConfirm = new OrdSettlementConfirm();
        if(User.isAuth()) {
            //判断是否保存当前地址
            CliClienteleReceiverAddress cliAddress = CliClienteleReceiverAddress.me().setFkClienteleId(User.id()).setIsDefault(1).get();
            //构建收货地址信息
            if (!Objects.isNull(cliAddress)) {
                SettlementConfirmDto.Address address = new SettlementConfirmDto.Address();
                BeanUtils.copyProperties(cliAddress, address);
                address.setSurname(cliAddress.getClientSurname());
                address.setName(cliAddress.getClientName());
                settlementConfirmDto.setShippingAddress(address);
            }
        }
        settlementConfirmDto.setCarts(cartId);
        settlementConfirmDto.setIsSaveAddress(true);
        settlementConfirmDto.setShippingAndInvoice(true);
        settlementConfirmDto.setCartFlag(cartFlag);
        settlementConfirm.setContent(JSON.toJSONString(settlementConfirmDto));
        settlementConfirm.insert();
        return settlementConfirm.getId();
    }


    public SettlementDto doUpdateSettlement(SettlementDto dto,HttpServletRequest request){
        OrdSettlementConfirm settlementConfirm = OrdSettlementConfirm.me().setId(dto.getSettlementId()).get();
        SettlementConfirmDto settlementConfirmDto = new SettlementConfirmDto();
        BeanUtils.copyProperties(dto,settlementConfirmDto);
        settlementConfirmDto.setCarts(dto.getCarts().stream().map(OrdShoppingCart::getId).collect(Collectors.toList()));
        if(dto.getIsSaveAddress() && User.isAuth()){
            CliClienteleReceiverAddress receiverAddress = new CliClienteleReceiverAddress();
            if(StringUtils.isNotBlank(dto.getAddressId())){
                receiverAddress = CliClienteleReceiverAddress.me().setId(dto.getAddressId()).get();
            }
            BeanUtils.copyProperties(settlementConfirmDto.getShippingAddress(),receiverAddress);
            if(StringUtils.isBlank(receiverAddress.getId())){
                receiverAddress.setIsDefault(0);
            }
            receiverAddress.insertOrUpdate();
            settlementConfirmDto.setAddressId(receiverAddress.getId());
        }
        settlementConfirm.setContent(JSON.toJSONString(settlementConfirmDto));
        settlementConfirm.update();
        return getSettlementDto(settlementConfirm.getId(),request);
    }

    public List<OrdShoppingCart> getCarts(List<String> ids, HttpServletRequest request){
        List<OrdShoppingCart> carts = new ArrayList<>(1);
        ids.forEach(id-> {
            if(User.isAuth()){
                carts.add(OrdShoppingCart.me().setId(id).get());
            }else{
                try {
                    carts.add(touristService.getCartCookieData(request,id));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        return carts;
    }

    public void getAddressOrCountry(BigDecimal totalCountValue, BigDecimal totalCountVol,
                                    SettlementDto settlementDto) {
        if(!Objects.isNull(settlementDto.getShippingAddress())) {
            SysCountry sysCountry = SysCountry.me().setAlpha3(settlementDto.getShippingAddress().getCountry()).get();
            settlementDto.setCountryName(sysCountry.getNameCn());
            List<OrdFreeSchemeCountry> countryList;
            String provinceCityId;
            //获取省/洲编号
            provinceCityId = getProvinceId(settlementDto.getShippingAddress());
            //为空直接根据国家获取方案
            if (!Helper.isEmpty(provinceCityId)) {
                List<OrdFreeSchemeCity> ordFreeSchemeCityList = OrdFreeSchemeCity.me().setFkProvinceCityId(provinceCityId).find();
                if (!ordFreeSchemeCityList.isEmpty()) {
                    countryList = getOrdFreeSchemeCountryList(ordFreeSchemeCityList);
                } else {
                    countryList = OrdFreeSchemeCountry.me().setCountryId(sysCountry.getId()).find();
                }
            } else {
                countryList = OrdFreeSchemeCountry.me().setCountryId(sysCountry.getId()).find();
            }
            //若根据国家获取方案为空则跳过
            if (!countryList.isEmpty()) {
                List<OrdFreeScheme> ordFreeSchemeList = getOrdFreeSchemeList(countryList);
                if (!ordFreeSchemeList.isEmpty()) {
                    freeScheme(ordFreeSchemeList, totalCountVol, totalCountValue ,settlementDto);
                }
            }
        }
    }

    /**
     * 获取省编号
     */
    private String getProvinceId(SettlementConfirmDto.Address address) {
        String proinveId = "";
        if (StringUtils.isNotBlank(address.getProvince()) && StringUtils.isNotBlank(address.getCity())) {
            String sql = "select * from sys_country_province_city where province = :province and (city = '' or city is null)";
            Map<String, Object> param = new HashMap<>(1);
            param.put("province", address.getProvince());
            List<Map<String, Object>> cityList = baseDao.queryForList(sql, param);
            if (!cityList.isEmpty()) {
                proinveId = cityList.get(0).get("id").toString();
            }
        } else {
            SysCountryProvinceCity city;
            if (StringUtils.isNotBlank(address.getProvince())) {
                city = SysCountryProvinceCity.me().setProvince(address.getProvince()).get();
                if (!Objects.isNull(city)) {
                    proinveId = city.getId();
                }

            }
            if (StringUtils.isNotBlank(address.getCity())) {
                city = SysCountryProvinceCity.me().setCity(address.getCity()).get();
                if (!Objects.isNull(city)) {
                    proinveId = city.getId();
                }
            }
        }
        return proinveId;
    }
    public List<OrdFreeSchemeCountry> getOrdFreeSchemeCountryList(List<OrdFreeSchemeCity> ordFreeSchemeCityList){
        List<OrdFreeSchemeCountry> countryList = new ArrayList<>();
        ordFreeSchemeCityList.forEach(city->{
            OrdFreeSchemeCountry country = OrdFreeSchemeCountry.me().setId(city.getFkSchemeCountId()).get();
            countryList.add(country);
        });
        return countryList;
    }

    public List<OrdFreeScheme> getOrdFreeSchemeList(List<OrdFreeSchemeCountry> countryList){
        List<OrdFreeScheme> schemeList = new ArrayList<>();
        countryList.forEach(country->{
            OrdFreeScheme ordFreeScheme = OrdFreeScheme.me().setId(country.getFkSchemeId()).get();
            schemeList.add(ordFreeScheme);
        });
        return schemeList;
    }
    /**
     * 运费计算
     *
     */
    private void freeScheme(List<OrdFreeScheme> ordFreeSchemeList, BigDecimal totalCountVol, BigDecimal totalCountValue, SettlementDto settlementDto) {
        //方案集合
        List<SettlementDto.FreeSchemes> freeList = new ArrayList<>(1);
        for (OrdFreeScheme ordFreeScheme : ordFreeSchemeList) {
            List<OrdFreeSchemeDetails> details = OrdFreeSchemeDetails.me().setFkOrdFreeSchemeId(ordFreeScheme.getId()).find();
            //判断该方案是否是固定运费方案
            if (!details.isEmpty()) {
                for (OrdFreeSchemeDetails freeSchemeDetails : details) {
                    SettlementDto.FreeSchemes freeSchemes = new SettlementDto.FreeSchemes();
                    if (Helper.isEmpty(settlementDto.getFreeSchemes())) {
                        settlementDto.setFreeSchemes(details.get(0).getId());
                        settlementDto.setFreeSchemesName(details.get(0).getSchemeInsideName());
                    }
                    if (freeSchemeDetails.getIsFixedFree() == 1) {
                        BigDecimal fixedFreeValue =  Helper.transformF2Y(freeSchemeDetails.getFixedFreeValue());
                        if (freeSchemeDetails.getId().equals(settlementDto.getFreeSchemes())) {
                            settlementDto.setFreeSchemesName(freeSchemeDetails.getSchemeInsideName());
                            settlementDto.setShippingPrice(fixedFreeValue.toString());
                            totalCountValue = totalCountValue.add(fixedFreeValue);
                            settlementDto.setTotalPrice(totalCountValue.toString());
                            optionFreeMaps(freeSchemeDetails.getId(), fixedFreeValue, freeSchemeDetails.getSchemeInsideName(), freeSchemes, 1);
                        } else {
                            optionFreeMaps(freeSchemeDetails.getId(), fixedFreeValue, freeSchemeDetails.getSchemeInsideName(), freeSchemes, 0);
                        }
                    } else {
                        //获取首重、续重运费
                        BigDecimal ykgFree = Helper.transformF2Y(freeSchemeDetails.getYkgFree());
                        BigDecimal kgFree =  Helper.transformF2Y(freeSchemeDetails.getKgFree());
                        //获取首重值、续重值
                        BigDecimal ykgValue = new BigDecimal(freeSchemeDetails.getYkgValue());
                        BigDecimal kgValue = new BigDecimal(freeSchemeDetails.getKgValue());
                        if ("kg".equals(freeSchemeDetails.getYkgUnit())) {
                            //换算kg
                            totalCountVol = totalCountVol.divide(new BigDecimal(1000));
                        } else {
                            totalCountVol = totalCountVol.divide(new BigDecimal(1000));
                            ykgValue = ykgValue.divide(new BigDecimal(1000));
                            kgValue = kgValue.divide(new BigDecimal(1000));
                        }
                        //如果首重值小于商品总重量则取首重运费
                        if (totalCountVol.compareTo(ykgValue) < 1) {
                            freeCalculation( totalCountValue, freeSchemeDetails, freeSchemes,settlementDto, ykgFree);
                        } else {
                            ykgValue = totalCountVol.subtract(ykgValue);
                            kgValue = ykgValue.divide(kgValue);
                            //向下取整加一
                            Long kgValues = kgValue.setScale(0, BigDecimal.ROUND_DOWN).longValue() + 1;
                            //首重费用+取整后的续重数
                            ykgFree = ykgFree.add(new BigDecimal(kgValues));
                            //续重费用*换算后的续重数 = 总价格
                            kgFree = kgFree.multiply(ykgFree);
                            freeCalculation(totalCountValue, freeSchemeDetails, freeSchemes,settlementDto, kgFree);
                        }
                    }
                    freeList.add(freeSchemes);
                }
            }
        }
        settlementDto.setFreeSchemeList(freeList);
    }
    private void freeCalculation(BigDecimal totalCountValue, OrdFreeSchemeDetails freeSchemeDetails,SettlementDto.FreeSchemes freeSchemes ,SettlementDto settlementDto, BigDecimal kgFree) {
        if (freeSchemeDetails.getId().equals(settlementDto.getFreeSchemes())) {
            settlementDto.setFreeSchemesName(freeSchemeDetails.getSchemeInsideName());
            settlementDto.setShippingPrice(format(kgFree).toString());
            settlementDto.setTotalPrice(format(totalCountValue.add(kgFree)).toString());
            optionFreeMaps(freeSchemeDetails.getId(), kgFree, freeSchemeDetails.getSchemeInsideName(), freeSchemes, 1);
        } else {
            optionFreeMaps(freeSchemeDetails.getId(), kgFree, freeSchemeDetails.getSchemeInsideName(), freeSchemes, 0);
        }
    }
    public Double format(BigDecimal pic) {
        Double df1 = pic.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df1;
    }

    private void optionFreeMaps(String id, BigDecimal freeValue, String schemeOutsideName, SettlementDto.FreeSchemes freeSchemes, int flag) {
        freeSchemes.setId(id);
        freeSchemes.setName(schemeOutsideName);
        freeSchemes.setFreeValue(format(freeValue).toString());
        freeSchemes.setFlag(flag);
    }
    public boolean isOptionLogistics(SettlementDto settlementDto){
        settlementDto.setIsOptionLogistics(false);
        SysOptions sysOptions = SysOptions.me().setOptionKey("sys_is_option_logistics").get();
        if("1".equals(sysOptions.getOptionValue())){
            settlementDto.setIsOptionLogistics(true);
            return true;
        }
        return false;
    }
}
