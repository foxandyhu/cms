package com.bfly.cms.system.dao;

import com.bfly.cms.system.entity.SysTask;
import com.bfly.core.base.dao.IBaseDao;
import org.springframework.data.jpa.repository.Query;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:58
 */
public interface ISysTaskDao extends IBaseDao<SysTask, Integer> {

    /**
     * 根据任务名称查找任务
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 19:27
     */
    @Query("from SysTask where name=:name")
    SysTask getTaskByName(String name);
}
