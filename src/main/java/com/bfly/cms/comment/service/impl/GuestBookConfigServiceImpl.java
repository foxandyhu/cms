package com.bfly.cms.comment.service.impl;

import com.bfly.cms.comment.entity.GuestBookConfig;
import com.bfly.cms.comment.service.IGuestBookConfigService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:04
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class GuestBookConfigServiceImpl extends BaseServiceImpl<GuestBookConfig, Integer> implements IGuestBookConfigService {


    @Override
    public GuestBookConfig getGuestBookConfig() {
        List<GuestBookConfig> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(GuestBookConfig guestBookConfig) {
        GuestBookConfig config = getGuestBookConfig();
        if (config == null) {
            return super.save(guestBookConfig);
        }
        guestBookConfig.setId(config.getId());
        return super.edit(guestBookConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(GuestBookConfig guestBookConfig) {
        return edit(guestBookConfig);
    }
}