package com.mazentop.modules.freemarker.tag;

import com.mazentop.entity.SysAdvertisementPop;
import com.mazentop.model.BooleanEnum;
import com.mazentop.plugins.session.SessionUtil;
import com.mztframework.commons.WebUtil;
import com.mztframework.render.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class SkinPopupTag extends Tag {

    private static final String FIRST = "session_first_open";

    @Override
    public void execute() {
        String position = getParam("position");

        SysAdvertisementPop sysAdvertisementPop = SysAdvertisementPop.me().setPosition(position).get();
        Map<String, Object> map = new HashMap<>(2);
        map.put("show", !Objects.isNull(sysAdvertisementPop)
                && sysAdvertisementPop.getIsEnable().equals(BooleanEnum.TRUE.getValue())
                && !this.getFirstOpenState());
        setFirstOpenState();
        map.put("data", sysAdvertisementPop);
        setVariable("popup", map);
        renderBody();
    }

    private boolean getFirstOpenState() {
        return !Objects.isNull(SessionUtil.getSessionAttribute(FIRST)) && (Boolean) SessionUtil.getSessionAttribute(FIRST);
    }

    private void setFirstOpenState() {
        SessionUtil.setSessionAttribute(FIRST, true);
    }

    @Override
    public String name() {
        return "skin_popup";
    }
}
