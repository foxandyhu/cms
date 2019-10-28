package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.entity.RegistConfig;
import com.bfly.cms.member.service.IMemberRegistConfigService;
import com.bfly.cms.system.entity.EmailProvider;
import com.bfly.cms.system.service.IEmailProviderService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:41
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class MemberRegistConfigServiceImpl extends BaseServiceImpl<RegistConfig, Integer> implements IMemberRegistConfigService {

    @Autowired
    private IEmailProviderService emailProviderService;

    @Override
    @Cacheable(value = "beanCache", key = "'register_config'")
    public RegistConfig getRegistConfig() {
        List<RegistConfig> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'register_config'")
    public boolean editMemberRegisterConfig(RegistConfig config) {
        Assert.notNull(config, "注册配置信息为空!");

        Assert.isTrue(config.getMinLengthUserName() >= 6, "用户名最小长度为6!");
        Assert.isTrue(config.getMinLengthPassword() >= 6, "密码最小长度为6!");

        Assert.notNull(config.getEmailProvider(), "未指定注册邮件服务器!");

        EmailProvider emailProvider = emailProviderService.get(config.getEmailProvider().getId());
        Assert.notNull(emailProvider, "未指定注册邮件服务器!");

        Assert.hasLength(config.getRegisteTitle(), "会员注册标题不能为空!");
        Assert.hasLength(config.getRegisteText(), "会员注册内容不能为空!");

        RegistConfig registConfig = getRegistConfig();
        //新增
        if (registConfig == null) {
            super.save(config);
        } else {
            config.setId(registConfig.getId());
            super.edit(config);
        }
        return false;
    }
}
