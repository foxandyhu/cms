package com.bfly.cms.task.dao;

import com.bfly.cms.task.entity.CmsTask;
import com.bfly.common.hibernate4.Updater;
import com.bfly.common.page.Pagination;

import java.util.List;

/**
 * 定时任务数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:48
 */
public interface CmsTaskDao {
    Pagination getPage(Integer siteId, int pageNo, int pageSize);

    List<CmsTask> getList();

    CmsTask findById(Integer id);

    CmsTask save(CmsTask bean);

    CmsTask updateByUpdater(Updater<CmsTask> updater);

    CmsTask deleteById(Integer id);
}