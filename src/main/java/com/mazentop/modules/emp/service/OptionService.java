package com.mazentop.modules.emp.service;

import com.mazentop.entity.SysCompany;
import com.mazentop.entity.SysOptions;
import com.mazentop.util.Helper;
import com.mztframework.cache.Options;
import com.mztframework.commons.Maps;
import com.mztframework.data.R;
import com.mztframework.data.Result;
import com.mztframework.jwt.security.Subject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OptionService {

    public R doOption(List<SysOptions> options) {

        for (SysOptions option : options) {
            if (StringUtils.isBlank(option.getId())) {
                option.setCompanyId(Subject.group())
                      .setCompanyName(SysCompany.me().setId(Subject.group()).get().getName())
                      .insert();
            } else {
                option.update();
            }
            Options.set(option.getOptionKey(), option.getOptionValue());
        }
        return R.ok();
    }

    public void doOptionNew(Map<String, String> optionMap) {
        optionMap.forEach((key, value) -> {
            SysOptions option = new SysOptions().setOptionKey(key).get();
            if (option == null) {
                new SysOptions()
                        .setOptionKey(key)
                        .setOptionValue(value)
                        .insert();
            } else {
                option.setOptionValue(value)
                        .update();
            }
            Options.set(key, value);
        });
    }

    public Map<String, String> mapOption(List<SysOptions> options) {
        Map<String, String> optionMap = Maps.newHashMap();
        options.forEach(option -> optionMap.put(option.getOptionKey(), option.getOptionValue()));
        return optionMap;
    }

    public Map<String, String> getOptions(String optionKey) {
        SysOptions sysOptions = SysOptions.me();

        if (optionKey != null && !"".equals(optionKey)) {
            sysOptions.setOptionKey(optionKey);
        }
        return mapOption(sysOptions.find());
    }


    /**
     * 通过key获取属性值
     *
     * @param key
     * @return
     */
    public String getOptionsByKey(String key) {
        return SysOptions.me().setOptionKey(key).get().getOptionValue();
    }

    /***
     * 通过key更新value
     * @param key
     * @param values
     * @return
     */
    public Result editOptionsByKey(String key, String values) {
        if (Helper.isEmpty(key)){
            return null;
        }
        SysOptions options = SysOptions.me().setOptionKey(key).get();
        options.setOptionValue(values);
        if (!options.getOptionValue().equals(values)){
            options.update();
        }
        return Result.success();
    }
}
