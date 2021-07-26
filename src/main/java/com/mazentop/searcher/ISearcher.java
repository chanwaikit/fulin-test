package com.mazentop.searcher;


import com.mztframework.dao.page.Page;

/**
 * @author zhaoqt
 * @title: ISearcher
 * @description: 查询检索接口
 * @date 2019/5/918:28
 */
public interface ISearcher {

    /**
     * 初始化
     */
    void init();

    /**
     * 添加快照索引
     *
     * @param snapshot
     * @param module
     */
    void save(Snapshot snapshot, String module);

    /**
     * 删除快照索引
     *
     * @param snapshot
     * @param module
     */
    void delete(Snapshot snapshot, String module);

    /**
     * 更新快照索引
     *
     * @param snapshot
     * @param module
     */
    void update(Snapshot snapshot, String module);

    /**
     * 索引重建
     *
     *  module 可为null 重建全部
     *
     * @param module
     * @return
     */
    boolean rebuild(String module);

    /**
     * 检索
     *
     * @param keyword
     * @param module
     * @return
     */
    Page<Snapshot> search(Keyword keyword, String module);

    /**
     * 总列数计算
     *
     * @param keyword
     * @param module
     * @return
     */
    int totalRow(Keyword keyword, String module);


}
