package com.mazentop.modules.emp.service;

import com.alibaba.fastjson.JSON;
import com.mazentop.entity.*;
import com.mazentop.model.*;
import com.mazentop.modules.emp.commond.OrdRecallRecordCommond;
import com.mazentop.modules.emp.commond.OrdShoppingCartCommond;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.dao.page.SearchTime;
import com.mztframework.data.R;
import com.mztframework.email.Email;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 召回订单业务类
 * @author dengy
 */
@Service
public class OrdRecallRecordService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    OrdSalesOrderService ordSalesOrderService;

    @Autowired
    EmailService service;


    public Page ordRecallRecordList(OrdRecallRecordCommond ordRecallRecordCommond){
        SearchTime searchTime = new SearchTime();
        if(ordRecallRecordCommond.getStartTime() != null){
            searchTime.setStart(ordRecallRecordCommond.getStartTime());
        }
        if(ordRecallRecordCommond.getEndTime() != null){
            searchTime.setEnd(ordRecallRecordCommond.getEndTime());
        }
        ordRecallRecordCommond.setAddTime(searchTime);
        List<OrdRecallRecord> list = OrdRecallRecord.me().find(ordRecallRecordCommond);
        return new Page(list,ordRecallRecordCommond);
    }


    /**
     * 获取召回订单状态数量
     * @return
     */
    public HashMap<String, Object> getRecallOrderStatus() {
        HashMap<String, Object> map = new HashMap<>(3);

        map.put("allCount", OrdRecallRecord.me().findCount());
        map.put("canRecallCount", OrdRecallRecord.me().setRecallStatus(BooleanEnum.FALSE.getValue()+"").findCount());
        map.put("recallSuccessCount", OrdRecallRecord.me().setRecallStatus(BooleanEnum.TRUE.getValue()+"").findCount());

        return map;
    }




    public OrdRecallRecord getRecallRecordDetails(String id){
        OrdRecallRecord ordRecallRecord = OrdRecallRecord.me().setId(id).get();
        if(!Objects.isNull(ordRecallRecord)){
            //根据结算单号查询召回结算单信息
            OrdBalanceTheBooks ordBalanceTheBooks = OrdBalanceTheBooks.me().setId(ordRecallRecord.getFkBalanceTheBooksId()).get();
            //获取结算单购物车集合
            String cat = ordBalanceTheBooks.getShoppingCartList();
            List<String> idArray = new ArrayList<>();
            if(!cat.contains(",")){
                idArray.add(cat);
            }else{
                idArray =  Arrays.asList(cat.split(","));
            }
            StringBuilder sql = new StringBuilder(" select * from ord_shopping_cart where 1=1 and id in (:id)");
            Map<String,Object> map = new HashMap<>();
            map.put("id",idArray);
            List<Map<String,Object>> list = baseDao.queryForList(sql.toString(),map);
            if(!list.isEmpty()){
                list.forEach(catMap->{
                    BigDecimal countPrice = new BigDecimal(0);
                    Integer num = Integer.parseInt(catMap.get("total_product_number").toString());
                    if (!Objects.isNull(catMap.get("product_mall_price"))) {
                        catMap.put("product_mall_price", Helper.transformF2Y(catMap.get("product_mall_price")));
                    } else {
                        catMap.put("product_mall_price", 0);
                    }
                    if (!Objects.isNull(catMap.get("product_market_price"))) {
                        catMap.put("product_market_price", Helper.transformF2Y(catMap.get("product_market_price")));
                    } else {
                        catMap.put("product_market_price", 0);
                    }
                    if (!Objects.isNull(catMap.get("product_promotion_price"))) {
                        BigDecimal promotionPrice = Helper.transformF2Y(catMap.get("product_promotion_price"));
                        countPrice = promotionPrice.multiply(new BigDecimal(num));
                        catMap.put("product_promotion_price", promotionPrice);
                    } else {
                        catMap.put("product_promotion_price", 0);
                    }
                    if (!Objects.isNull(catMap.get("discount_value"))) {
                        catMap.put("discount_value", Helper.transformF2Y(catMap.get("discount_value")));
                    }
                    if(countPrice.compareTo(new BigDecimal(0))<1){
                        BigDecimal price = new BigDecimal(catMap.get("product_mall_price").toString());
                        countPrice = price.multiply(new BigDecimal(num));
                    }
                    catMap.put("totalPrice",countPrice);
                });
            }
            ordRecallRecord.addExten("catList",list);
        }
        return ordRecallRecord;
    }

    public void doTimingRecallEmail(){
        List<OrdRecallRecord> recordList = OrdRecallRecord.me().setIsSendSuccess(0).setRecallStatus("0").find();
        SysOptions sysOptions = SysOptions.me().setOptionKey("site_setting_domain_name").get();
        recordList.forEach(record->{
            try {
                orderRecoverySendEmail(record,sysOptions);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }


    public void orderRecoverySendEmail(OrdRecallRecord record, SysOptions sysOptions) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>(2);
        String url = sysOptions.getOptionValue();

        //封装跳转链接
        map.put("booksId", record.getFkBalanceTheBooksId());
        map.put("userId", record.getFkClienteleId());
        String param = JSON.toJSONString(map);
        //加密请求参数
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] textByte = param.getBytes("UTF-8");
        String params = base64Encoder.encode(textByte);
        url += "/shoppingCart/recallJumpSettlement/" + params;

        if(Helper.isNotEmpty(record.getFkBalanceTheBooksId())){
            OrdBalanceTheBooks ordBalanceTheBooks = OrdBalanceTheBooks.me().setId(record.getFkBalanceTheBooksId());
            if (Objects.nonNull(ordBalanceTheBooks) && Helper.isNotEmpty(ordBalanceTheBooks.getShoppingCartList())) {


                String[] split = ordBalanceTheBooks.getShoppingCartList().split(",");
                OrdShoppingCartCommond cartCommond = new OrdShoppingCartCommond();
                cartCommond.setIds(Arrays.asList(split));
                List<OrdShoppingCart> ordShoppingCarts = OrdShoppingCart.me().find(cartCommond);
                SysOptions options = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get();
                map.put("record", record);
                map.put("ordShoppingCarts", ordShoppingCarts);
                map.put("url", url);
                map.put("domain", options.getOptionValue());
                SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setEmailSendMoment(EmailTemplateTypeEnum.TYPE_RECOVERY_ORDER.type()).get();
                String theme = DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
                if (Helper.isNotEmpty(sysEmailTemplate.getTheme())) {
                    theme = sysEmailTemplate.getTheme();
                }
                Email email = Email.create().to(record.getClientEmail()).subject(theme);
                R send = service.sendMail(map, email, sysEmailTemplate.getTemplateShowContent());
                SysEmailSendRecord emailSendRecord = new SysEmailSendRecord();
                emailSendRecord.setAddTime(Utils.currentTimeSecond());
                emailSendRecord.setFkEmailTemplateId(sysEmailTemplate.getId());
                emailSendRecord.setEmailTemplateName(sysEmailTemplate.getEmailTemplateName());
                List<String> list = new ArrayList<>();
                list.add(record.getFkClienteleId());
                emailSendRecord.setSendPersonList(Helper.toJson((list)));
                emailSendRecord.setSendTime(Utils.currentTimeSecond());
                if (send.getState() == 200) {
                    emailSendRecord.setIsSuccess(BooleanEnum.TRUE.getValue());
                    record.setIsSendSuccess(BooleanEnum.TRUE.getValue());
                    record.setSendRecordJson(send.toString());
                    record.update();
                } else {
                    emailSendRecord.setIsSuccess(BooleanEnum.FALSE.getValue());
                }
                emailSendRecord.insert();
            }
        }
    }
}
