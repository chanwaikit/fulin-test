package com.mazentop.modules.emp.service;

import com.mazentop.entity.*;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.EmailTemplateTypeEnum;
import com.mazentop.model.OptionsEnum;
import com.mazentop.model.OrdSalesOrderStatusEnum;
import com.mazentop.util.Helper;
import com.mztframework.commons.Utils;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.email.Email;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
public class OrdOrderLogisticsStatusService {
    @Autowired
    EmailService service;
    @Autowired
    OrderManageService orderManageService;

    public Result doEditOrdOrderLogisticsStatus(OrdOrderLogisticsStatus ordOrderLogisticsStatus){
        String curUserId = Subject.id();
        EmpEmployeeInfo curEmployee =  EmpEmployeeInfo.me().setId(curUserId).get();
        ordOrderLogisticsStatus.setSalesOrderStatus(OrdSalesOrderStatusEnum.DELIVER_COMPLETE.desc());
        if (Helper.isNotEmpty(ordOrderLogisticsStatus.getId())) {
            ordOrderLogisticsStatus.setOperationTime(Utils.currentTimeSecond());
            ordOrderLogisticsStatus.setOperationUserId(curEmployee.getId());
            ordOrderLogisticsStatus.setOperationUserName(curEmployee.getEmployeeName());
            ordOrderLogisticsStatus.update();
        } else {
            ordOrderLogisticsStatus.setAddTime(Utils.currentTimeSecond());
            ordOrderLogisticsStatus.setAddUserName(curEmployee.getEmployeeName());
            ordOrderLogisticsStatus.setAddUserId(curEmployee.getId());
            ordOrderLogisticsStatus.insert();
        }
        ordOrderLogisticsStatus.setIsSign(BooleanEnum.FALSE.getValue());
        OrdSalesOrder salesOrder = OrdSalesOrder.me().setId(ordOrderLogisticsStatus.getFkSalesOrderId()).get();
        salesOrder.setTransportsNo(ordOrderLogisticsStatus.getTransportsNo());
        salesOrder.setTransportsChannelName(ordOrderLogisticsStatus.getTransportsChannelName());
        salesOrder.setTransportsChannelId(ordOrderLogisticsStatus.getTransportsChannelId());
        salesOrder.setOperationTime(Utils.currentTimeSecond());
        salesOrder.setOperationUserId(curEmployee.getId());
        salesOrder.setOperationUserName(curEmployee.getEmployeeName());
        salesOrder.setSalesOrderStatus(OrdSalesOrderStatusEnum.DELIVER_COMPLETE.status());
        salesOrder.update();
        SysOptions options=SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_DOMAIN_NAME.key()).get();
        String detailsUrl = orderManageService.orderDetailsUrl(salesOrder.getId(), salesOrder.getFkClienteleId(), options);
        SysOptions sysOptionsEmail = SysOptions.me().setOptionKey(OptionsEnum.SITE_SETTING_EMAIL.key()).get();
        CompletableFuture.runAsync(()-> {
            List<OrdSalesOrderDetail> detailList = OrdSalesOrderDetail.me().setFkSalesOrderId(salesOrder.getId()).find();
            Map<String,Object> map=new HashMap<>();
            map.put("transportsNo",ordOrderLogisticsStatus.getTransportsNo());
            map.put("transportsChannelName",ordOrderLogisticsStatus.getTransportsChannelName());
            map.put("url",detailsUrl);
            map.put("orderNo",salesOrder.getSalesOrderNo());
            map.put("contactEmail",sysOptionsEmail.getOptionValue());
            map.put("detailList",detailList);
            map.put("domain",options.getOptionValue());
            SysEmailTemplate sysEmailTemplate = SysEmailTemplate.me().setEmailSendMoment(EmailTemplateTypeEnum.TYPE_DELIVERY_ORDER.type()).get();
            String theme= DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss");
            if (Helper.isNotEmpty(sysEmailTemplate.getTheme())){
                theme= sysEmailTemplate.getTheme();
            }
            Email email = Email.create().to(salesOrder.getClientEmail()).subject(theme);
            R sendMail = service.sendMail(map, email, sysEmailTemplate.getTemplateShowContent());
            SysEmailSendRecord emailSendRecord = new SysEmailSendRecord();
            emailSendRecord.setAddTime(Utils.currentTimeSecond());
            emailSendRecord.setFkEmailTemplateId(sysEmailTemplate.getId());
            emailSendRecord.setEmailTemplateName(sysEmailTemplate.getEmailTemplateName());
            List<String> list = new ArrayList<>();
            list.add(salesOrder.getFkClienteleId());
            emailSendRecord.setSendPersonList(Helper.toJson((list)));
            emailSendRecord.setSendTime(Utils.currentTimeSecond());
            if (sendMail.getState() == 200) {
                emailSendRecord.setIsSuccess(BooleanEnum.TRUE.getValue());
            } else {

                emailSendRecord.setIsSuccess(BooleanEnum.FALSE.getValue());
            }
            emailSendRecord.insert();
        });
        return Result.success();
    }

    public Result doOrderSign(OrdSalesOrder ordSalesOrder){
        OrdOrderLogisticsStatus ordOrderLogisticsStatus = new OrdOrderLogisticsStatus();
        ordOrderLogisticsStatus.setIsSign(BooleanEnum.TRUE.getValue());
        ordOrderLogisticsStatus.setAddUserId(ordSalesOrder.getAddUserId());
        ordOrderLogisticsStatus.setAddUserName(ordSalesOrder.getAddUserName());
        ordOrderLogisticsStatus.setAddTime(Utils.currentTimeSecond());
        ordOrderLogisticsStatus.setFkSalesOrderId(ordSalesOrder.getId());
        ordOrderLogisticsStatus.setSalesOrderStatus("已签收");
        ordOrderLogisticsStatus.setTransportsChannelId(ordSalesOrder.getTransportsChannelId());
        ordOrderLogisticsStatus.setTransportsChannelName(ordSalesOrder.getTransportsChannelName());
        ordOrderLogisticsStatus.setTransportsNo(ordSalesOrder.getTransportsNo());
        ordOrderLogisticsStatus.insert();
        ordSalesOrder.setSalesOrderStatus(OrdSalesOrderStatusEnum.COMPLETE.status());
        ordSalesOrder.setOperationTime(Utils.currentTimeSecond());
        ordSalesOrder.update();
        return Result.success();
    }
}
