package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.entity.LoginConfig;
import com.bfly.cms.member.service.IMemberLoginConfigService;
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
public class MemberLoginConfigServiceImpl extends BaseServiceImpl<LoginConfig, Integer> implements IMemberLoginConfigService {

    @Autowired
    private IEmailProviderService emailProviderService;

    @Override
    @Cacheable(value = "beanCache", key = "'login_config'")
    public LoginConfig getLoginConfig() {
        List<LoginConfig> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "beanCache", key = "'login_config'")
    public boolean editMemberLoginConfig(LoginConfig config) {
        Assert.notNull(config, "登录配置信息为空!");

        Assert.isTrue(config.getLoginError() >= 0, "登录错误次数需大于0!");
        Assert.isTrue(config.getLoginErrorTimeOut() >= 0, "登录错误时间需大于0!");

        Assert.notNull(config.getEmailProvider(), "未指定登录邮件服务器!");

        EmailProvider emailProvider = emailProviderService.get(config.getEmailProvider().getId());
        Assert.notNull(emailProvider, "未指定登录邮件服务器!");

        Assert.hasLength(config.getRetrievePwdTitle(), "找回密码标题不能为空!");
        Assert.hasLength(config.getRetrievePwdText(), "找回密码内容不能为空!");

        LoginConfig loginConfig = getLoginConfig();
        //新增
        if (loginConfig == null) {
            super.save(config);
        } else {
            config.setId(loginConfig.getId());
            super.edit(config);
        }
        return false;
    }
}
