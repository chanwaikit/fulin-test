package com.mazentop.plugins.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * @author zhaoqt
 * @title: Message
 * @description: 事件消息
 * @date 2019/5/1001:31
 */
@Data
public class Message extends ApplicationEvent {

    private static final long serialVersionUID = 7566039995268891088L;

    private Object data;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public Message(Object source, Object data) {
        super(source);
        this.data = data;
    }

    @SuppressWarnings("unchecked")
    public <M> M getData() {
        return (M) data;
    }
}
