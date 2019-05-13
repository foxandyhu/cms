package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.SysConfig;
import com.bfly.cms.system.service.ISysConfigService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:49
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig, Integer> implements ISysConfigService {


    @Override
    public SysConfig getSysConfig() {
        List<SysConfig> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysConfig sysConfig) {
        SysConfig config = getSysConfig();
        if (config == null) {
            return super.save(sysConfig);
        }
        sysConfig.setId(config.getId());
        return super.edit(sysConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysConfig sysConfig) {
        return edit(sysConfig);
    }
}
