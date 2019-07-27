package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.dao.ISysTaskDao;
import com.bfly.cms.system.entity.SysTask;
import com.bfly.cms.system.service.ISysTaskService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:57
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysTaskServiceImpl extends BaseServiceImpl<SysTask, Integer> implements ISysTaskService {

    @Autowired
    private ISysTaskDao taskDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startTask(String name) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean stopTask(String name) {
        return false;
    }

    @Override
    public SysTask getTask(String name) {
        return taskDao.getTaskByName(name);
    }
}
