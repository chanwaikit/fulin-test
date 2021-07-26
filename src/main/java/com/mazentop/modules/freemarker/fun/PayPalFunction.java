package com.mazentop.modules.freemarker.fun;

import com.mazentop.entity.SysOptions;
import com.mztframework.render.Function;
import org.springframework.stereotype.Component;

@Component
public class PayPalFunction extends Function {


    @Override
    public Object exec() {
        String clientId = SysOptions.me().setOptionKey("paypal_client_id").get().getOptionValue();
        return clientId;
    }

    @Override
    public String name() {
        return "getClienId";
    }
}
