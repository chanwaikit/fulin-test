package com.mazentop.modules.user.service;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.mazentop.entity.CliClienteleInfo;
import com.mazentop.entity.EvaUserRecommendation;
import com.mazentop.entity.MztAuthorize;
import com.mazentop.model.BooleanEnum;
import com.mazentop.model.ClienteleCertificationEnum;
import com.mazentop.modules.web.User;
import com.mztframework.FileProperties;
import com.mztframework.cache.Options;
import com.mztframework.commons.Utils;
import com.mztframework.dao.jdbc.BaseDao;
import com.mztframework.snowflake.IDSnowflake;
import me.zhyd.oauth.model.AuthUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import java.util.Objects;

@Service
public class WebAuthorizationService {

    @Autowired
    BaseDao baseDao;

    @Autowired
    FileProperties fileProperties;

    @Autowired
    UserCenterService userCenterService;

    public CliClienteleInfo doAddAuthorize(AuthUser user, String source){
        Long count = MztAuthorize.me().setSource(source).setUserToken(user.getUuid()).findCount();
        if(count == 0) {
            MztAuthorize authorize = new MztAuthorize();
            authorize.setEmail(user.getEmail());
            authorize.setSource(source);
            authorize.setContent(JSONObject.toJSON(user).toString());
            authorize.setUserToken(user.getUuid());
            authorize.insert();
        }
        CliClienteleInfo clienteleInfo = CliClienteleInfo.me().setEmail(user.getEmail()).get();
        if(Objects.isNull(clienteleInfo)){
            clienteleInfo = new CliClienteleInfo();
            clienteleInfo.setId(IDSnowflake.id());
            clienteleInfo.setAddTime(Utils.currentTimeSecond());
            clienteleInfo.setAddUserId(clienteleInfo.getId());
            clienteleInfo.setLevel(0);
            clienteleInfo.setBuySum(0L);
            clienteleInfo.setBuyTime(0);
            clienteleInfo.setIsEnable(1);
            clienteleInfo.setIsBuyer(0);
            clienteleInfo.setIsMoreBuyer(0);
            clienteleInfo.setIsSubscriber(0);
            clienteleInfo.setBuyGoodsNumber(0);

            clienteleInfo.setEmail(user.getEmail());
            clienteleInfo.setLoginName(user.getEmail());
            clienteleInfo.setClientName(user.getUsername());
            clienteleInfo.setIsFlag(1);
            // 给用户一个默认地址
            clienteleInfo.setIconImageUrl(fileProperties.getBaseUrl()+"/2021-05-17/5414129601746280475.png");
            String certification=ClienteleCertificationEnum.AUTHENTICATION.status();
            if (Convert.toBool(Options.get("site_setting_user_oauth"))){
                certification =  ClienteleCertificationEnum.UNAUTHORIZED.status();
            }
            clienteleInfo.setAccountCertification(certification);
            clienteleInfo.setInvitationCode(userCenterService.getCode());
            clienteleInfo.insert();

            if(StringUtils.isNotBlank(User.pcid())){
                CliClienteleInfo userInfo = CliClienteleInfo.me().setInvitationCode(User.pcid()).get();
                if(Objects.nonNull(userInfo)){
                    EvaUserRecommendation recommendation = new EvaUserRecommendation();
                    //推荐人id
                    recommendation.setReferrerId(userInfo.getId());
                    recommendation.setAddUserId(clienteleInfo.getId());
                    //被推荐人id
                    recommendation.setUserId(clienteleInfo.getId());
                    // 默认为没有发放佣金
                    recommendation.setIsPaid(BooleanEnum.FALSE.getValue());
                    recommendation.insert();
                }
            }
        }
        User.auth(clienteleInfo);
        return clienteleInfo;
    }
}
