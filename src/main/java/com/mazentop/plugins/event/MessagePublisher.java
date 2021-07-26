package com.mazentop.plugins.event;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author zhaoqt
 * @title: EventPublisher
 * @description: 时间发布者
 * @date 2019/5/1001:11
 */
@Component
public class MessagePublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void setApplicationEventPublisher(@Qualifier ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(Message message) {
        applicationEventPublisher.publishEvent(message);
    }
}
