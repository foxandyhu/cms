package com.bfly.cms.message.service.impl;

import com.bfly.cms.message.dao.IGuestBookConfigDao;
import com.bfly.cms.message.entity.GuestBookConfig;
import com.bfly.cms.message.service.IGuestBookConfigService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/9/15 13:30
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class GuestBookConfigServiceImpl extends BaseServiceImpl<GuestBookConfig, Integer> implements IGuestBookConfigService {

    @Autowired
    private IGuestBookConfigDao configDao;

    @Override
    public GuestBookConfig getConfig() {
        List<GuestBookConfig> list = configDao.findAll();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(GuestBookConfig guestBookConfig) {
        GuestBookConfig config = getConfig();
        if (config != null) {
            guestBookConfig.setId(config.getId());
        }
        configDao.save(guestBookConfig);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(GuestBookConfig guestBookConfig) {
        return save(guestBookConfig);
    }
}
