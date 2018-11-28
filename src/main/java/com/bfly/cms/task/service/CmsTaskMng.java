package com.bfly.cms.task.service;

import com.bfly.cms.task.entity.CmsTask;
import com.bfly.common.page.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 定时任务业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/26 14:47
 */
public interface CmsTaskMng {

    Pagination getPage(Integer siteId, int pageNo, int pageSize);

    List<CmsTask> getList();

    CmsTask findById(Integer id);

    CmsTask save(CmsTask bean);

    CmsTask update(CmsTask bean, Map<String, String> attr);

    CmsTask deleteById(Integer id);

    CmsTask[] deleteByIds(Integer[] ids);

    String getCronExpressionFromDB(Integer taskId);
}