package com.mazentop.plugins.event;

/**
 * 命令执行接口
 *
 * @author zhaoqt
 * @version 1.0
 * @date 2019/12/20 02:19
 */
public interface Command<T> {

    /**
     * 接口执行任务
     * @return
     * @throws RuntimeException
     */
    T exec() throws RuntimeException;
}
