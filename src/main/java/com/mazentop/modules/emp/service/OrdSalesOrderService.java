package com.mazentop.modules.emp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.*;
import com.mazentop.excel.entity.OrdSalesOrderDetailEntity;
import com.mazentop.excel.entity.ProductEntity;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.EmailTemplateTypeEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.model.OrdSalesOrderTypeEnum;
import com.mazentop.modules.emp.commond.OrdSalesOrderCommond;
import com.mazentop.modules.emp.dto.OrdSalesOrderDto;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.dao.page.Page;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.email.Email;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * @author: dengy
 * @date: 2020/3/14
 * @description:
 */
@Service
public class OrdSalesOrderService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    EmailService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 获取线上订单状态数量
     * @return
     */
    public HashMap<String, Object> getOrderStatus() {
        HashMap<String, Object> map = new HashMap<>(6);

        OrdSalesOrderCommond commond = new OrdSalesOrderCommond();
        ArrayList<String> typePays = new ArrayList<String>();
        typePays.add("0");
        typePays.add("1");
        commond.setPlatformTypes(typePays);

        commond.setSalesOrderStatus(OrdSalesOrderStatusEnum.DELIVER_ING_COMPLETE.status());
        map.put("sendGoodsCount", OrdSalesOrder.me().findCount(commond));

        commond.setSalesOrderStatus(OrdSalesOrderStatusEnum.DELIVER_COMPLETE.status());
        map.put("sendDeliveredCount", OrdSalesOrder.me().findCount(commond));

        commond.setSalesOrderStatus(OrdSalesOrderStatusEnum.COMPLETE.status());
        map.put("offTheStocksCount", OrdSalesOrder.me().setSalesOrderStatus(OrdSalesOrderStatusEnum.COMPLETE.status()).findCount(commond));

        commond.setSalesOrderStatus(OrdSalesOrderStatusEnum.REFUND_ING.status());
        map.put("refundApplicationCount", OrdSalesOrder.me().findCount(commond));

        commond.setSalesOrderStatus(OrdSalesOrderStatusEnum.CANCEL_COMPLETE.status());
        map.put("canceledCount", OrdSalesOrder.me().findCount(commond));

        List<String> salesOrderStatusList = new ArrayList<String>();
        salesOrderStatusList.add("03");
        salesOrderStatusList.add("04");
        salesOrderStatusList.add("05");
        salesOrderStatusList.add("06");
        salesOrderStatusList.add("99");
        commond.setSalesOrderStatusList(salesOrderStatusList);
        commond.setSalesOrderStatus(null);

        map.put("allCount", OrdSalesOrder.me().findCount(commond));

        return map;
    }



    public Page OrdSalesOrderList(OrdSalesOrderCommond ordSalesOrderCommond) {
        StringBuilder sql = new StringBuilder(" select * from ord_sales_order where 1=1 ");
        Map<String, Object> params = new HashMap<>();
        if (ordSalesOrderCommond.getStartTime() != null) {
            sql.append(" and payment_time >= :startTime ");
            params.put("startTime", ordSalesOrderCommond.getStartTime());
        }
        if (ordSalesOrderCommond.getEndTime() != null) {
            sql.append(" and payment_time <= :endTime ");
            params.put("endTime", ordSalesOrderCommond.getEndTime());
        }
        if (StringUtils.isNotEmpty(ordSalesOrderCommond.getQuery())) {
            sql.append(" and (sales_order_no like :query or client_name like :query or localhost_sn like :query) ");
            params.put("query", ordSalesOrderCommond.getQuery()+"%");
        }
        if (StringUtils.isNotEmpty(ordSalesOrderCommond.getSalesOrderStatus())) {
            sql.append(" and sales_order_status = :status ");
            params.put("status", ordSalesOrderCommond.getSalesOrderStatus());
        } else {
            if (ordSalesOrderCommond.getPlatformType().equals(OrdSalesOrderTypeEnum.TYPE_SIGN.status())) {
                sql.append(" and sales_order_status in ('01','03','04','05','06','99')");
            } else {
                sql.append(" and sales_order_status in ('03','04','05','06','99')");
            }

        }
        if (StringUtils.isNotEmpty(ordSalesOrderCommond.getCompanyId())) {
            sql.append(" and company_id = :companyId ");
            params.put("companyId", ordSalesOrderCommond.getCompanyId());
        }
        if (StringUtils.isNotEmpty(ordSalesOrderCommond.getFkTagId())) {
            sql.append(" and fk_tag_id = :tagId ");
            params.put("tagId", ordSalesOrderCommond.getFkTagId());
        }
        if (StringUtils.isNotEmpty(ordSalesOrderCommond.getId())) {
            sql.append(" and id = :id ");
            params.put("id", ordSalesOrderCommond.getId());
        }
        if (ordSalesOrderCommond.getPlatformType().equals("1")) {
            sql.append(" and payment_platform_type_id in ('0','1')");
        } else if (ordSalesOrderCommond.getPlatformType().equals("2")) {
            sql.append(" and payment_platform_type_id in (2)");
        }
        ordSalesOrderCommond.setOrderBy("add_time desc");
        List<Map<String, Object>> list = baseDao.paginate(sql.toString(), params, ordSalesOrderCommond);
        if (!list.isEmpty()) {
            list.forEach(map -> {
                if (Objects.isNull(map.get("total_price"))) {
                    map.put("total_price", 0);
                } else {
                    map.put("total_price", Helper.transformF2Y(map.get("total_price")));
                }
                List<OrdSalesOrderDetail> details = OrdSalesOrderDetail.me().setFkSalesOrderId(map.get("id").toString()).find();
                optionOrderDetails(details);
                map.put("detailsList", details);
                map.put("productCount",details.size());
                if(!Objects.isNull(map.get("fk_tag_id"))) {
                    if(StringUtils.isNotBlank(map.get("fk_tag_id").toString())) {
                        SysDictionary sysDictionary = SysDictionary.me().setId(map.get("fk_tag_id").toString()).get();
                        map.put("name", sysDictionary.getName());
                        map.put("value", sysDictionary.getValue());
                    }else{
                        map.put("name","");
                        map.put("value","");
                    }
                }else{
                    map.put("name","");
                    map.put("value","");
                }
            });

        }
        return new Page(list, ordSalesOrderCommond);
    }


    public Page getOrderList(OrdSalesOrderCommond commond) {
        List<OrdSalesOrder> list = OrdSalesOrder.me().find(commond);
        for (OrdSalesOrder ordSalesOrder : list) {
            ordSalesOrder.addExten("totalPrice", Helper.transformF2Y(ordSalesOrder.getTotalPrice()));
        }
        return new Page<>(list,commond);

    }

    public R doOrdSalesOrderAdd(OrdSalesOrderDto ordSalesOrderDto) {
        String curUserId = Subject.id();
        if (StringUtils.isEmpty(curUserId)) {
            return R.error("登录信息获取失败!");
        }
        EmpEmployeeInfo curEmployee = EmpEmployeeInfo.me().setId(curUserId).get();
        if (Objects.isNull(curEmployee)) {
            return R.error("数据获取失败!");
        }
        if (Objects.isNull(ordSalesOrderDto)) {
            return R.error("订单信息获取失败");
        }
        if (ordSalesOrderDto.getDetailList().isEmpty()) {
            return R.error("订单详情获取失败!");
        }
        String salesOrderNo = "PR" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss");
        List<OrdSalesOrderDetail> list = ordSalesOrderDto.getDetailList();
        ordSalesOrderDto.setSalesOrderNo(salesOrderNo);
        //新增收货人信息
        if (StringUtils.isNotEmpty(ordSalesOrderDto.getFkClienteleId())) {
            CliClienteleInfo cliClienteleInfo = CliClienteleInfo.me().setId(ordSalesOrderDto.getFkClienteleId()).get();
            if (!Objects.isNull(cliClienteleInfo)) {
                ordSalesOrderDto.setReceiverId(cliClienteleInfo.getId());
                CliClienteleReceiverAddress cliClienteleReceiverAddress = CliClienteleReceiverAddress.me().setFkClienteleId(ordSalesOrderDto.getFkClienteleId()).get();
                if (!Objects.isNull(cliClienteleReceiverAddress)) {
                    ordSalesOrderDto.setFkClienteleAddressId(cliClienteleReceiverAddress.getId());
                    ordSalesOrderDto.setReceiverPhone(cliClienteleReceiverAddress.getPhone());
                    ordSalesOrderDto.setReceiverAddress(cliClienteleReceiverAddress.getAddress());
                    ordSalesOrderDto.setReceiverCity(cliClienteleReceiverAddress.getCity());
                    ordSalesOrderDto.setReceiverEmail(cliClienteleReceiverAddress.getEmail());
                    ordSalesOrderDto.setReceiverSheet(cliClienteleReceiverAddress.getSheet());
                    ordSalesOrderDto.setReceiverName(cliClienteleReceiverAddress.getClientSurname() + cliClienteleReceiverAddress.getClientName());
                }
            }
        }
        ordSalesOrderDto.setAddTime(Utils.currentTimeSecond());
        ordSalesOrderDto.setAddUserName(curEmployee.getEmployeeName());
        ordSalesOrderDto.setAddUserId(curEmployee.getId());
        SysCompany sysCompany = SysCompany.me().setId(Subject.group()).get();
        ordSalesOrderDto.setCompanyId(sysCompany.getId());
        ordSalesOrderDto.setCompanyName(sysCompany.getName());
        ordSalesOrderDto.insert();
        if (!list.isEmpty()) {
            list.forEach(detail -> {
                detail.setFkSalesOrderId(ordSalesOrderDto.getId());
                detail.setSalesOrderNo(salesOrderNo);
                detail.setAddTime(Utils.currentTimeSecond());
                detail.setAddUserName(curEmployee.getEmployeeName());
                detail.setAddUserId(curEmployee.getId());
                if (ordSalesOrderDto.getTotalProductNumber() == null) {
                    ordSalesOrderDto.setTotalProductNumber(detail.getProductNum());
                } else {
                    ordSalesOrderDto.setTotalProductNumber(ordSalesOrderDto.getTotalProductNumber() + detail.getProductNum());
                }
                detail.insert();
            });
        }
        return R.ok();
    }

    public List<OrdSalesOrder> exportOrder(OrdSalesOrderCommond commond) {
        StringBuilder sql = new StringBuilder(" select * from ord_sales_order where 1=1 ");
        Map<String,Object> param = new HashMap<>(5);
        if (Helper.isNotEmpty(commond)) {
            if (commond.getIds()!= null && commond.getIds().size() > 0) {
                sql.append(" and id in (:ids)");
                param.put("ids",commond.getIds());
            }
            if (Helper.isNotEmpty(commond.getSalesOrderNo())) {
                sql.append("and (sales_order_no like :salesOrderNo or client_name like :salesOrderNo ) ");
                param.put("salesOrderNo","%"+commond.getSalesOrderNo()+"%");
            }
            if (Helper.isNotEmpty(commond.getStartTime())) {
                sql.append(" and payment_time >=:startTime ");
                param.put("startTime",commond.getStartTime());
            }
            if (Helper.isNotEmpty(commond.getEndTime())) {
                sql.append(" and payment_time  <=:endTime ");
                param.put("endTime",commond.getStartTime());
            }
            if (Helper.isNotEmpty(commond.getSalesOrderStatus())) {
                sql.append(" and sales_order_status in(:salesOrderStatus)");
                param.put("salesOrderStatus",commond.getSalesOrderStatus());
            }
            if (Helper.isEmpty(commond.getSize())){
                commond.setPageSize(10000);
            }else {
                commond.setPageSize(commond.getSize());
            }
        }
        List<OrdSalesOrder> list = baseDao.find(sql.toString(),commond,OrdSalesOrder.me().newMapper());
        return list;
    }

    public List<OrdSalesOrderDetailEntity> exportOrderDetail(OrdSalesOrderCommond commond) {
        StringBuilder sql = new StringBuilder(" select * from ord_sales_order where 1=1 ");
        Map<String,Object> param=new HashMap<>(5);
        if (Helper.isNotEmpty(commond)) {
            if (commond.getIds()!= null && commond.getIds().size() > 0) {
                sql.append(" and id in (:ids)");
                param.put("ids",commond.getIds());
            }
            if (Helper.isNotEmpty(commond.getSalesOrderNo())) {
                sql.append("and (sales_order_no like :salesOrderNo or client_name like :salesOrderNo ) ");
                param.put("salesOrderNo","%"+commond.getSalesOrderNo()+"%");
            }
            if (Helper.isNotEmpty(commond.getStartTime())) {
                sql.append(" and payment_time >=:startTime ");
                param.put("startTime",commond.getStartTime());
            }
            if (Helper.isNotEmpty(commond.getEndTime())) {
                sql.append(" and payment_time  <=:endTime ");
                param.put("endTime",commond.getStartTime());
            }
            if (Helper.isNotEmpty(commond.getSalesOrderStatus())) {
                sql.append(" and sales_order_status in(:salesOrderStatus)");
                param.put("salesOrderStatus",commond.getSalesOrderStatus());
            }
            if (Helper.isEmpty(commond.getSize())){
                commond.setPageSize(10000);
            }else {
                commond.setPageSize(commond.getSize());
            }
        }
        List<OrdSalesOrder> orderList = baseDao.find(sql.toString(),commond,OrdSalesOrder.me().newMapper());
        List<OrdSalesOrderDetailEntity> list=new ArrayList<>();
        orderList.forEach(order -> {
            OrdSalesOrderDetailEntity detail=new OrdSalesOrderDetailEntity();
            BeanUtils.copyProperties(order,detail);
            detail.setTotalPrice(Helper.transformF2Y(order.getTotalPrice()).toString());
            detail.setTotalPaymentFree(Helper.transformF2Y(order.getTotalPaymentFree()).toString());
            detail.setTotalTransportsFree(Helper.transformF2Y(order.getTotalTransportsFree()).toString());
            detail.setPaymentTime(Helper.timestampToDate(order.getPaymentTime(),Helper.DATE_PATTERN1));
            List<OrdSalesOrderDetail> detailList = OrdSalesOrderDetail.me().setFkSalesOrderId(order.getId()).find();
            List<ProductEntity> product=new ArrayList<>();
            for (OrdSalesOrderDetail ordSalesOrderDetail : detailList) {
                ProductEntity master = JSONObject.parseObject(JSON.toJSONString(ordSalesOrderDetail), ProductEntity.class);
                if (!master.getProductImageUrl().contains("http:")){
                    master.setProductImageUrl("http:"+master.getProductImageUrl());
                }
                master.setProductActivityPrice(Helper.transformF2Y(ordSalesOrderDetail.getProductActivityPrice()).toString());
                master.setProductMallPrice(Helper.transformF2Y(ordSalesOrderDetail.getProductMarketPrice()).toString());
                product.add(master);
            }
            detail.setProduct(product);
            list.add(detail);
        });
        return list;
    }

    //处理订单详情信息
    public void optionOrderDetails(List<OrdSalesOrderDetail> list) {
        if (!list.isEmpty()) {
            list.forEach(detail -> {
                if (detail.getProductActivityPrice() != null) {
                    detail.addExten("productActivityPrice", Helper.transformF2Y(detail.getProductActivityPrice()));
                } else {
                    detail.addExten("productActivityPrice", 0);
                }

                if (detail.getProductMallPrice() != null) {
                    detail.addExten("productMallPrice", Helper.transformF2Y(detail.getProductMallPrice()));
                } else {
                    detail.addExten("productMallPrice", 0);
                }

                if (detail.getProductMarketPrice() != null) {
                    detail.addExten("productMarketPrice", Helper.transformF2Y(detail.getProductMarketPrice()));
                } else {
                    detail.addExten("productMarketPrice", 0);
                }

            });
        }
    }

    public Result orderRecoverySendEmail(OrdRecallRecord record,SysOptions sysOptions) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>(1);
        String url = sysOptions.getOptionValue();

        //封装跳转链接
        map.put("booksId",record.getFkBalanceTheBooksId());
        map.put("userId",record.getFkClienteleId());
        String param= JSON.toJSONString(map);
        //加密请求参数
        BASE64Encoder base64Encoder = new BASE64Encoder();
        byte[] textByte = param.getBytes("UTF-8");
        String params = base64Encoder.encode(textByte);
        url +="/shoppingCart/recallJumpSettlement/"+params;

        map.put("record", record);
        map.put("url",url);
        SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setEmailSendMoment(EmailTemplateTypeEnum.TYPE_RECOVERY_ORDER.type()).get();
        String theme= DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
        if (Helper.isNotEmpty(sysEmailTemplate.getTheme())){
            theme= sysEmailTemplate.getTheme();
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
            return Result.success();
        }else {
            emailSendRecord.setIsSuccess(BooleanEnum.FALSE.getValue());
        }
        emailSendRecord.insert();
        return Result.toast(send.getMessage());
    }


    public String getIp() throws UnknownHostException {
        InetAddress ia = InetAddress.getLocalHost();
        String localip = ia.getHostAddress();
        return localip;
    }

}
