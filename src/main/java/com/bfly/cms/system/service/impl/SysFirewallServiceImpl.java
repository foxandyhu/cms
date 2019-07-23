package com.bfly.cms.system.service.impl;

import com.bfly.cms.system.entity.SysFirewall;
import com.bfly.cms.system.service.ISysFirewallService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/22 10:18
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysFirewallServiceImpl extends BaseServiceImpl<SysFirewall, Integer> implements ISysFirewallService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SysFirewall firewall) {
        SysFirewall sysFirewall = getSysFirewall();
        //不存在则新增 存在则修改
        if (sysFirewall == null) {
            return super.save(firewall);
        }
        firewall.setId(sysFirewall.getId());
        return super.edit(firewall);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SysFirewall firewall) {
        return edit(firewall);
    }

    @Override
    public SysFirewall getSysFirewall() {
        List<SysFirewall> list = getList();
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }
}
