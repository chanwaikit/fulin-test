package com.mazentop.plugins.event;


import com.mztframework.spring.SpringContextHolder;

/**
 * @author zhaoqt
 * @title: EventHolder
 * @description: 事件
 * @date 2019/5/1001:00
 */
public class EventHolder {

    /**
     * 时间发布
     *
     * @param message
     */
    public static void publishEvent(Message message) {
        SpringContextHolder.getBean(MessagePublisher.class).publishEvent(message);
    }
}
