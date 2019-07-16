package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.entity.MemberConfig;
import com.bfly.cms.member.service.IMemberConfigService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:41
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class MemberConfigServiceImpl extends BaseServiceImpl<MemberConfig, Integer> implements IMemberConfigService {

    @Override
    public MemberConfig getMemberConfig() {
        List<MemberConfig> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(MemberConfig memberConfig) {
        MemberConfig config = getMemberConfig();
        if (config == null) {
            return super.save(memberConfig);
        }
        memberConfig.setId(config.getId());
        return super.edit(memberConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MemberConfig memberConfig) {
        return edit(memberConfig);
    }
}
