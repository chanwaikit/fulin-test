package com.mazentop.modules.skin.service;

import com.mazentop.entity.SkinCountry;
import com.mztframework.data.R;
import com.mztframework.exception.ToastException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkinCountryService {


    public R save (SkinCountry skinCountry) {
        Optional skinCountryOpt = Optional.ofNullable(SkinCountry.me().setCountryCode(skinCountry.getCountryCode()).get());
        if (skinCountryOpt.isPresent()) {
            throw new ToastException("该支持国家已存在");
        }
        skinCountry.insert();
        return R.ok();
    }




    public R update (SkinCountry skinCountry) {
        skinCountry.update();
        return R.ok();
    }


}
