package com.mazentop.modules.freemarker.fun;

import cn.hutool.core.util.URLUtil;
import com.mazentop.model.ProSeoTypeEnum;
import com.mazentop.modules.web.User;
import com.mazentop.plugins.seo.Seo;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

@Component
public class FacebookShareUrlFun extends Function {

    private static String facebookShareUrl = "https://www.facebook.com/sharer/sharer.php?u=%ssrc=sdkpreparse";

    @Override
    public Object exec() {
        String type = getToString(1, ProSeoTypeEnum.TYPE_PRODUCT.type());
        String id = getToString(0);
        String shareUrl = Seo.getSeoUrlDetail(id, type);
        if(User.isAuth()) {
            // 当前用户登录状态拼接 cid
            shareUrl += "?cid=" + User.cid();
        }
        return URLUtil.decode(String.format(facebookShareUrl, shareUrl));
    }

    @Override
    public String name() {
        return "facebookShareUrlFun";
    }
}
