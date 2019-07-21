package com.bfly.cms.member.service.impl;

import com.bfly.cms.member.entity.MemberConfig;
import com.bfly.cms.member.service.IMemberConfigService;
import com.bfly.cms.system.entity.EmailProvider;
import com.bfly.cms.system.service.IEmailProviderService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MemberConfigServiceImpl extends BaseServiceImpl<MemberConfig, Integer> implements IMemberConfigService {

    @Autowired
    private IEmailProviderService emailProviderService;

    @Override
    public MemberConfig getMemberConfig() {
        List<MemberConfig> list = getList();
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editMemberLoginConfig(MemberConfig config) {
        Assert.notNull(config, "登录配置信息为空!");
        Assert.notNull(config.getLoginConfig(), "登录配置信息为空!");

        Assert.isTrue(config.getLoginConfig().getLoginError() >= 0, "登录错误次数需大于0!");
        Assert.isTrue(config.getLoginConfig().getLoginErrorTimeOut() >= 0, "登录错误时间需大于0!");

        Assert.notNull(config.getLoginConfig().getEmailProvider(), "未指定登录邮件服务器!");

        EmailProvider emailProvider = emailProviderService.get(config.getLoginConfig().getEmailProvider().getId());
        Assert.notNull(emailProvider, "未指定登录邮件服务器!");

        Assert.hasLength(config.getLoginConfig().getRetrievePwdTitle(), "找回密码标题不能为空!");
        Assert.hasLength(config.getLoginConfig().getRetrievePwdText(), "找回密码内容不能为空!");

        MemberConfig memberConfig = getMemberConfig();
        //新增
        if (memberConfig == null) {
            super.save(config);
        } else {
            memberConfig.setLoginConfig(config.getLoginConfig());
            super.edit(memberConfig);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editMemberRegistConfig(MemberConfig config) {
        Assert.notNull(config, "注册配置信息为空!");
        Assert.notNull(config.getRegistConfig(), "注册配置信息为空!");

        Assert.isTrue(config.getRegistConfig().getMinLengthUserName() >= 6, "用户名最小长度为6!");
        Assert.isTrue(config.getRegistConfig().getMinLengthPassword() >= 6, "密码最小长度为6!");

        Assert.notNull(config.getRegistConfig().getEmailProvider(), "未指定注册邮件服务器!");

        EmailProvider emailProvider = emailProviderService.get(config.getRegistConfig().getEmailProvider().getId());
        Assert.notNull(emailProvider, "未指定注册邮件服务器!");

        Assert.hasLength(config.getRegistConfig().getRegisteTitle(), "会员注册标题不能为空!");
        Assert.hasLength(config.getRegistConfig().getRegisteText(), "会员注册内容不能为空!");

        MemberConfig memberConfig = getMemberConfig();
        //新增
        if (memberConfig == null) {
            super.save(config);
        } else {
            memberConfig.setRegistConfig(config.getRegistConfig());
            super.edit(memberConfig);
        }
        return false;
    }
}
